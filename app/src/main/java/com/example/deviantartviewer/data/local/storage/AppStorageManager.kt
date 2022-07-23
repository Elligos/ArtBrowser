package com.example.deviantartviewer.data.local.storage

import android.content.Context
import android.graphics.Bitmap
import com.example.deviantartviewer.utils.log.Logger
import io.reactivex.Single
import java.io.File
import java.io.FileOutputStream
import javax.inject.Singleton

@Singleton
class AppStorageManager(private val context: Context) {

    companion object {
        private const val TAG = "StorageManager"
    }

    fun saveBitmapToInternalStorage(bitmap : Bitmap) : Single<String> {
//        var file = context.getDir("Images", Context.MODE_PRIVATE)
//        file = File(file, "img.jpg")
//        val out = FileOutputStream(file)
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out)
//        out.flush()
//        out.close()
//        Logger.d(TAG, "Image saved to the : ${file.canonicalPath}")
//        return file.canonicalPath
        return Single.fromCallable { storeBitmapToInternalStorage(bitmap) }
    }

    fun storeBitmapToInternalStorage(bitmap : Bitmap) : String{
        var file = context.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "img.jpg")
        val out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out)
        out.flush()
        out.close()
        Logger.d(TAG, "Image saved to the : ${file.canonicalPath}")
        return file.canonicalPath
    }
}