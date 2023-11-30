package com.example.template.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.template.constants.Constants
import com.example.template.models.ContactsDAO

@Dao
interface DBContacts {

    @Query("SELECT * FROM ${Constants.TABLE_CONTACT} ORDER BY name ASC")
    fun getListContact(): MutableList<ContactsDAO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewContact(contact: ContactsDAO): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateContact(contact: ContactsDAO): Int

    @Delete
    fun deleteContact(contact: ContactsDAO): Int
}