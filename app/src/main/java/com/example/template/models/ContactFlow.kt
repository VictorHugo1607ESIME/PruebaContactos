package com.example.template.models

import com.example.template.constants.Constants
import java.io.Serializable

data class ContactFlow(

    var contacts: List<ContactsDAO>? = ArrayList(),
    var status: Int? = 0,
    var operation: String? = Constants.STRING_EMPTY,
    var message: String? = Constants.STRING_EMPTY

): Serializable