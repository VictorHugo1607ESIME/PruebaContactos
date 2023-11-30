package com.example.template.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.template.constants.Constants
import com.example.template.models.ContactsDAO

@androidx.room.Database(entities = [ContactsDAO::class], version = Constants.VERSION_DB)
abstract class Database: RoomDatabase() {
    abstract val contactDAO: DBContacts
}

private lateinit var INSTANCE: Database

fun getDatabase(context: Context): Database {
    synchronized(Database::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room
                .databaseBuilder(
                    context.applicationContext,
                    Database::class.java,
                    "contacts_db"
                ).build()
        }
        return  INSTANCE
    }
}