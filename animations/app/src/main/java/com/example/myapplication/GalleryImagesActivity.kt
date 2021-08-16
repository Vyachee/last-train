package com.example.myapplication

import android.app.LoaderManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log

class GalleryImagesActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_images)
        loaderManager.initLoader(1, null, (this as LoaderManager.LoaderCallbacks<Cursor>))
    }

    override fun onCreateLoader(id: Int, args: Bundle?): android.content.Loader<Cursor>? {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val selection: String? = null
        val selectionArgs = arrayOf<String>()
        val sortOrder: String? = null

        return android.content.CursorLoader(
            baseContext,
            uri,
            projection,
            selection,
            selectionArgs,
            sortOrder)
    }

    override fun onLoadFinished(p0: android.content.Loader<Cursor>?, p1: Cursor?) {
        p1?.let {
            val columnIndexData = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

            while(it.moveToNext()) {
                val images = it.getString(columnIndexData)
                Log.e("DEBUG", "image: $images")
            }
        }
    }

    override fun onLoaderReset(p0: android.content.Loader<Cursor>?) {
    }
}