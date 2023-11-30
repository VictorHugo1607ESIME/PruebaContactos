package com.example.template.views.adapters

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.contact.databinding.ItemBinding
import com.example.template.models.ContactsDAO
import com.example.template.utils.Interfaces
import com.squareup.picasso.Picasso

class ContactsAdapter(private val cardSelected: Interfaces.ClickItemSelect): ListAdapter<ContactsDAO, ContactsAdapter.ViewHolder>(DiffCallBack) {

    companion object DiffCallBack: DiffUtil.ItemCallback<ContactsDAO>(){
        override fun areItemsTheSame(oldItem: ContactsDAO, newItem: ContactsDAO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ContactsDAO, newItem: ContactsDAO): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder {
        val binding: ItemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ContactsDAO = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: ContactsDAO){
            var name = item.name
            if(!item.lastName.isNullOrEmpty())
                name += " " + item.lastName
            if(!item.lastNameM.isNullOrEmpty())
                name += " " + item.lastNameM
            if(!item.age.isNullOrEmpty())
                name += "   " + item.age
            binding.tvContactName.text = name

            var phone = item.phoneNumber
            if(!item.sex.isNullOrEmpty())
                phone += item.sex + " años"
            binding.tvContactPhone.text = phone

            if (!item.photo.isNullOrEmpty()){
                Picasso.get().load(item.photo).into(binding.ivProfile)
            }

            binding.contentContact.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(view: View): Boolean {
                    cardSelected.itemSelect(item)
                    return true
                }
            })
        }
    }

}