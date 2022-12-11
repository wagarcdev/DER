package com.wagarcdev.der.presentation.screens.screen_login.google_one_tap_sign_in

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.wagarcdev.der.domain.model.User

// based on https://github.com/stevdza-san/OneTapCompose
@Composable
fun GoogleOneTapSignIn(
    state: OneTapSignInState,
    clientId: String,
    onUserReceived: (User) -> Unit,
    onDialogDismissed: (String) -> Unit
) {
    // todo maybe find a better way to improve this

    val activity = LocalContext.current as Activity

    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        try {
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    val oneTapClient = Identity.getSignInClient(activity)
                    val credentials = oneTapClient.getSignInCredentialFromIntent(result.data)

                    val user = User(
                        name = credentials.displayName ?: "Google User",
                        email = credentials.id,
                        password = credentials.password ?: ""
                    )

                    onUserReceived(user)
                    state.close()
                }
                else -> {
                    onDialogDismissed("Dialog closed")
                    state.close()
                }
            }
        } catch (exception: ApiException) {
            exception.printStackTrace()

            when (exception.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    onDialogDismissed("Dialog canceled")
                    state.close()
                }
                CommonStatusCodes.NETWORK_ERROR -> {
                    onDialogDismissed("Network error")
                    state.close()
                }
                else -> {
                    onDialogDismissed("Something went wrong")
                    state.close()
                }
            }
        }
    }

    LaunchedEffect(key1 = state.opened) {
        if (state.opened) {
            signIn(
                activity = activity,
                clientId = clientId,
                launchActivityResult = { intentSenderRequest ->
                    activityResultLauncher.launch(intentSenderRequest)
                },
                onError = { messageError ->
                    onDialogDismissed(messageError)
                    state.close()
                }
            )
        }
    }
}

private fun signIn(
    activity: Activity,
    clientId: String,
    launchActivityResult: (IntentSenderRequest) -> Unit,
    onError: (String) -> Unit
) {
    val signInClient = Identity.getSignInClient(activity)

    val idTokenRequestOptions = BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
        .setSupported(true)
        .setFilterByAuthorizedAccounts(true)
        .setServerClientId(clientId)
        .build()

    val passwordRequestOptions = BeginSignInRequest.PasswordRequestOptions.builder()
        .setSupported(true)
        .build()

    val signInRequest = BeginSignInRequest.builder()
        .setPasswordRequestOptions(passwordRequestOptions)
        .setGoogleIdTokenRequestOptions(idTokenRequestOptions)
        .setAutoSelectEnabled(true)
        .build()

    signInClient.beginSignIn(signInRequest)
        .addOnSuccessListener { result ->
            try {
                launchActivityResult(
                    IntentSenderRequest.Builder(
                        result.pendingIntent.intentSender
                    ).build()
                )
            } catch (exception: Exception) {
                exception.printStackTrace()
                onError("Something went wrong")
            }
        }
        .addOnFailureListener {
            signUp(
                activity = activity,
                clientId = clientId,
                launchActivityResult = launchActivityResult,
                onError = onError
            )
        }
}

private fun signUp(
    activity: Activity,
    clientId: String,
    launchActivityResult: (IntentSenderRequest) -> Unit,
    onError: (String) -> Unit
) {
    val signInClient = Identity.getSignInClient(activity)

    val passwordRequestOptions = BeginSignInRequest.PasswordRequestOptions.builder()
        .setSupported(true)
        .build()

    val idTokenRequestOptions = BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
        .setSupported(true)
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(clientId)
        .build()

    val signInRequest = BeginSignInRequest.builder()
        .setPasswordRequestOptions(passwordRequestOptions)
        .setGoogleIdTokenRequestOptions(idTokenRequestOptions)
        .build()

    signInClient.beginSignIn(signInRequest)
        .addOnSuccessListener { result ->
            try {
                launchActivityResult(
                    IntentSenderRequest.Builder(
                        result.pendingIntent.intentSender
                    ).build()
                )
            } catch (exception: Exception) {
                exception.printStackTrace()
                onError("Something went wrong")
            }
        }
        .addOnFailureListener {
            it.printStackTrace()
            onError("Something went wrong")
        }
}