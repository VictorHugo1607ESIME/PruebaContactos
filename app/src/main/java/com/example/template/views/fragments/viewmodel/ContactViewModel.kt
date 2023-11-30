package com.example.template.views.fragments.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contact.R
import com.example.template.constants.Constants
import com.example.template.database.Database
import com.example.template.database.getDatabase
import com.example.template.models.ContactFlow
import com.example.template.models.ContactsDAO
import com.example.template.views.fragments.repository.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactViewModel(private val context: Context): ViewModel() {

    private val database: Database = getDatabase(context)
    private val contactRepository = ContactRepository(database)

    private val _contactStatus = MutableStateFlow(ContactFlow())
    val contactStatus: StateFlow<ContactFlow> get() = _contactStatus

    fun getContacts(){
        _contactStatus.update { it.copy(status = Constants.LOAD) }
        viewModelScope.launch {
            try {
                val request = contactRepository.getListContacts()
                if(request != null){
                    _contactStatus.update {
                        it.copy(
                            contacts = request,
                            status = Constants.SUCCESS,
                            operation = Constants.GET
                        )
                    }
                }else{
                    _contactStatus.update {
                        it.copy(
                            status = Constants.FAIL,
                            operation = Constants.GET,
                            message = context.getString(R.string.text_list_contacts_empty)
                        ) }
                }
            }catch (e: Exception){
                _contactStatus.update {
                    it.copy(
                        status = Constants.FAIL,
                        operation = Constants.GET,
                        message = e.message.toString()
                    ) }
            }
        }
    }

    fun insertContact(contact: ContactsDAO){
        _contactStatus.update { it.copy(status = Constants.LOAD) }
        contact.id = 0
        viewModelScope.launch {
            try {
                val request = contactRepository.insertContact(contact)
                if(request >= 0){
                    _contactStatus.update {
                        it.copy(
                            status = Constants.SUCCESS,
                            operation = Constants.INSERT
                        )
                    }
                }else{
                    _contactStatus.update {
                        it.copy(
                            status = Constants.FAIL,
                            operation = Constants.INSERT,
                            message = context.getString(R.string.text_list_contacts_empty)
                        ) }
                }
            }catch (e: Exception){
                _contactStatus.update {
                    it.copy(
                        status = Constants.FAIL,
                        operation = Constants.INSERT,
                        message = e.message.toString()
                    ) }
            }
        }
    }

    fun updateContact(contact: ContactsDAO){
        _contactStatus.update { it.copy(status = Constants.LOAD) }
        viewModelScope.launch {
            try {
                val request = contactRepository.updateContact(contact)
                if(request >= 0){
                    _contactStatus.update {
                        it.copy(
                            status = Constants.SUCCESS,
                            operation = Constants.UPDATE
                        )
                    }
                }else{
                    _contactStatus.update {
                        it.copy(
                            status = Constants.FAIL,
                            operation = Constants.UPDATE,
                            message = context.getString(R.string.text_list_contacts_empty)
                        ) }
                }
            }catch (e: Exception){
                _contactStatus.update {
                    it.copy(
                        status = Constants.FAIL,
                        operation = Constants.UPDATE,
                        message = e.message.toString()
                    ) }
            }
        }
    }

    fun deleteContact(contact: ContactsDAO){
        _contactStatus.update { it.copy(status = Constants.LOAD) }
        viewModelScope.launch {
            try {
                val request = contactRepository.deleteContact(contact)
                if(request >= 0){
                    _contactStatus.update {
                        it.copy(
                            status = Constants.SUCCESS,
                            operation = Constants.DELETE
                        )
                    }
                }else{
                    _contactStatus.update {
                        it.copy(
                            status = Constants.FAIL,
                            operation = Constants.DELETE,
                            message = context.getString(R.string.text_list_contacts_empty)
                        ) }
                }
            }catch (e: Exception){
                _contactStatus.update {
                    it.copy(
                        status = Constants.FAIL,
                        operation = Constants.DELETE,
                        message = e.message.toString()
                    ) }
            }
        }
    }
}