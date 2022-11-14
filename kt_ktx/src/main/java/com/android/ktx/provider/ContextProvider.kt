package com.android.ktx.provider

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri

object ContextProvider  {

//    override fun onCreate(): Boolean {
//        applicationContext = context
//        return false
//    }
//
//    override fun query(
//        uri: Uri, projection: Array<String>?, selection: String?,
//        selectionArgs: Array<String>?, sortOrder: String?
//    ): Cursor? {
//        return null
//    }
//
//    override fun getType(uri: Uri): String? {
//        return null
//    }
//
//    override fun insert(uri: Uri, values: ContentValues?): Uri? {
//        return null
//    }
//
//    override fun delete(
//        uri: Uri, selection: String?,
//        selectionArgs: Array<String>?
//    ): Int {
//        return 0
//    }
//
//    override fun update(
//        uri: Uri, values: ContentValues?, selection: String?,
//        selectionArgs: Array<String>?
//    ): Int {
//        return 0
//    }

//    companion object {
        @SuppressLint("StaticFieldLeak")
        private var applicationContext: Context? = null

        fun initCotext(context: Context) {
            applicationContext = context
        }

        fun getContext(): Context {
            return applicationContext!!
        }
//    }
}
