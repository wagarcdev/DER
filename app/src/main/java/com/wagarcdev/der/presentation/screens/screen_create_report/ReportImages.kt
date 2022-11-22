package com.wagarcdev.der.presentation.screens.screen_create_report

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachFile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ReportImages(
    modifier: Modifier = Modifier,
    imagesBitmap: List<Bitmap>,
    onAddImage: (Uri) -> Unit,
    onRemoveImage: (Int) -> Unit
) {
    val readStoragePermissionState = rememberPermissionState(
        permission = Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val imageChooserLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { onAddImage(it) }
        }
    )

    val imageSize = 120.dp

    FlowRow(
        modifier = modifier,
        mainAxisAlignment = MainAxisAlignment.Center,
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 8.dp
    ) {
        imagesBitmap.forEachIndexed { index, bitmap ->
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 2.dp
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(size = imageSize)
                        .clickable { onRemoveImage(index) },
                    model = bitmap,
                    contentDescription = null
                )
            }
        }

        Surface(
            shape = MaterialTheme.shapes.medium,
            elevation = 2.dp
        ) {
            Box(
                modifier = Modifier
                    .size(size = imageSize)
                    .clickable {
                        readStoragePermissionState.status.handler(
                            granted = { imageChooserLauncher.launch("image/*") },
                            denied = readStoragePermissionState::launchPermissionRequest
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(size = 36.dp),
                    imageVector = Icons.Rounded.AttachFile,
                    contentDescription = "Add Image"
                )
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private fun PermissionStatus.handler(
    granted: () -> Unit,
    denied: () -> Unit
) = when (this) {
    is PermissionStatus.Granted -> granted()
    is PermissionStatus.Denied -> denied()
}