package com.thoughtworks.androidtrain.util

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import java.net.URI

private fun Cursor.getColumnStringOrNull(contact: String): String? {
    return this.getColumnIndex(contact).let { index ->
        if (index == -1) null else this.getString(index)
    }
}

fun ContentResolver.fetchContact(uri: Uri): Pair<String, String>? {
    val cursor = this.query(uri, null, null, null)
    cursor?.let {
        if (it.moveToFirst()) {
            val name = cursor.getColumnStringOrNull(ContactsContract.Contacts.DISPLAY_NAME)
            val id = cursor.getColumnStringOrNull(ContactsContract.Contacts._ID)
            val hasPhone =
                (cursor.getColumnStringOrNull(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                    ?: "-1").toInt() > 0
            if (hasPhone) {
                val phones = this.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                    null,
                    null
                )
                while (phones!!.moveToNext()) {
                    val phoneNumber =
                        phones.getColumnStringOrNull(ContactsContract.CommonDataKinds.Phone.NUMBER)

                    return if (name != null && phoneNumber != null) name to phoneNumber
                    else null
                }
                phones.close()
            }
        }
        it.close()
    }
    return null
}