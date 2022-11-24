package com.wagarcdev.der.utils

import android.content.Context
import com.wagarcdev.der.utils.Constants.IMAGES_FOLDER
import java.io.File

/**
 * Extension to get a [File] image on app [IMAGES_FOLDER].
 */
fun Context.imageFile(
    fileName: String
) = File("$filesDir/$IMAGES_FOLDER/$fileName")