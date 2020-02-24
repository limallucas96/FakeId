package br.com.lucas.fatec

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import java.io.File
import java.io.FileOutputStream


object FileUtils {

    const val RESULT_GALLERY = 0

    fun openGallery(activity: Activity) {
        val intent = Intent(ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        activity.startActivityForResult(intent, RESULT_GALLERY)
    }

    fun getPath(uri: Uri?, context: Context): String {
        return try {
          return Utils.parseToFile(context, uri)
        } catch (ex: Exception) {
            ex.printStackTrace()
            ""
        }
    }

    fun toBitmap(path: String): Bitmap? {
        return try {
            BitmapFactory.decodeFile(path)
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

}