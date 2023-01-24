package com.wagarcdev.der.presentation.screens.screen_create_or_edit_report.steps

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachFile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.wagarcdev.der.utils.Constants
import com.wagarcdev.der.utils.imageFile

@Composable
fun SecondStep(
    modifier: Modifier = Modifier,
    attachedImageNames: List<String>,
    onAddImage: (Uri) -> Unit,
    onRemoveImage: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    onPreviousStep: () -> Unit,
    onFinishSteps: () -> Unit,
    onUpdateStep: () -> Unit,
    textButton: String
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let { onAddImage(it) }
        }
    )

    val imageSize = 120.dp

    BackHandler(onBack = onNavigateBack)

    Column(modifier = modifier.verticalScroll(state = scrollState)) {
        Text(
            modifier = Modifier
                .padding(all = 16.dp)
                .align(alignment = Alignment.CenterHorizontally),
            text = "Attach images",
            style = MaterialTheme.typography.h6
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            mainAxisAlignment = MainAxisAlignment.Center,
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp
        ) {
            attachedImageNames.forEachIndexed { index, imageName ->
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    elevation = 2.dp
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(size = imageSize)
                            .clickable { onRemoveImage(index) },
                        model = context.imageFile(fileName = imageName),
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
                            imagePickerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
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

        Spacer(modifier = Modifier.weight(weight = 1F))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier.weight(weight = 0.5F),
                onClick = onPreviousStep,
                shape = RoundedCornerShape(size = 15.dp)
            ) {
                Text(text = "Previous")
            }

            Button(
                modifier = Modifier.weight(weight = 0.5F),
                onClick = onFinishSteps,
                shape = RoundedCornerShape(size = 15.dp)
            ) {
                Text(text = textButton)
            }

            if (textButton == Constants.SHARE_PDF){
                Button(
                    modifier = Modifier.weight(weight = 0.5F),
                    onClick = onUpdateStep,
                    shape = RoundedCornerShape(size = 15.dp)
                ) {
                    Text(text = "Update")
                }
            }
        }


    }
}