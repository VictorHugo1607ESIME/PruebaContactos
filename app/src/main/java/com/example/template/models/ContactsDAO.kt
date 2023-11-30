package com.example.template.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.template.constants.Constants
import java.io.Serializable

@Entity(tableName = Constants.TABLE_CONTACT)
data class ContactsDAO(

    @PrimaryKey(autoGenerate = true     ) var id:           Long = -1,
    @ColumnInfo(name = "name"           ) var name:         String? = "",
    @ColumnInfo(name = "last_name"      ) var lastName:     String? = "",
    @ColumnInfo(name = "last_name_m"    ) var lastNameM:    String? = "",
    @ColumnInfo(name = "age"            ) var age:          String? = "",
    @ColumnInfo(name = "photo_number"   ) var phoneNumber:  String? = "",
    @ColumnInfo(name = "sex"            ) var sex:          String? = "",
    @ColumnInfo(name = "photo"          ) var photo:        String? = "",

): Serializable