package com.example.template.views.fragments.repository

import com.example.template.database.Database
import com.example.template.models.ContactsDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactRepository(private val database: Database) {

    suspend fun getListContacts(): MutableList<ContactsDAO> {
        return withContext(Dispatchers.IO){
            database.contactDAO.getListContact()
        }
    }

    suspend fun insertContact(contact: ContactsDAO): Long{
        return withContext(Dispatchers.IO){
            database.contactDAO.insertNewContact(contact)
        }
    }

    suspend fun updateContact(contact: ContactsDAO): Int{
        return withContext(Dispatchers.IO){
            database.contactDAO.updateContact(contact)
        }
    }

    suspend fun deleteContact(contact: ContactsDAO): Int{
        return withContext(Dispatchers.IO){
            database.contactDAO.deleteContact(contact)
        }
    }

}