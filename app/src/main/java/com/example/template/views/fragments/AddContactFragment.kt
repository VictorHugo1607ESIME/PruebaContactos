package com.example.template.views.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.contact.R
import com.contact.databinding.FragmentAddContactBinding
import com.example.template.constants.Constants
import com.example.template.models.ContactsDAO
import com.example.template.utils.Alerts
import com.example.template.utils.ValidateInputs
import com.example.template.views.fragments.viewmodel.ContactViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class AddContactFragment : Fragment(), OnClickListener {

    private lateinit var binding: FragmentAddContactBinding
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var validateInputs: ValidateInputs
    private lateinit var alerts: Alerts
    private lateinit var contact: ContactsDAO
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contact = ContactsDAO()
        if (arguments != null)
            contact = requireArguments().getSerializable(Constants.CONTACT) as ContactsDAO
        contactViewModel = ContactViewModel(requireContext())
        validateInputs = ValidateInputs(requireContext())
        alerts = Alerts(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddContactBinding.inflate(inflater, container, false)
        initUI()
        initListeners()
        initObserve()
        return binding.root
    }

    private fun initUI() {
        if (contact.id > 0){
            binding.tvTitle.text = requireContext().getString(R.string.text_update_contact)
            binding.btnAddContactToDB.text = requireContext().getString(R.string.text_update_contact)

            if(!contact.name.isNullOrEmpty())
                binding.etName.setText(contact.name)
            if(!contact.lastName.isNullOrEmpty())
                binding.etLastName.setText(contact.lastName)
            if(!contact.lastNameM.isNullOrEmpty())
                binding.etLastNameM.setText(contact.lastNameM)
            if(!contact.age.isNullOrEmpty())
                binding.etAge.setText(contact.age)
            if(!contact.phoneNumber.isNullOrEmpty())
                binding.etPhone.setText(contact.phoneNumber)
            if(!contact.sex.isNullOrEmpty())
                binding.etSex.setText(contact.sex)
            if (!contact.photo.isNullOrEmpty())
                Picasso.get().load(contact.photo).into(binding.ivPreviewPhoto)

        } else{
            binding.tvTitle.text = requireContext().getString(R.string.text_add_contact)
            binding.btnAddContactToDB.text = requireContext().getString(R.string.text_add_contact)
        }

    }

    private fun initListeners() {
        binding.btnAddContactToDB.setOnClickListener(this)
        binding.ivSelectPhoto.setOnClickListener(this)
    }

    private fun initObserve(){
        lifecycleScope.launch {
            contactViewModel.contactStatus.collect{ dataStatus ->
                when(dataStatus.status){

                    Constants.LOAD ->{

                    }

                    Constants.SUCCESS -> {
                        when(dataStatus.operation){
                            Constants.INSERT, Constants.UPDATE -> {
                                alerts.showSimpleLongToast(requireContext().getString(R.string.text_updated_contact))
                                findNavController().popBackStack()
                            }
                        }

                    }

                    Constants.FAIL -> {

                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnAddContactToDB -> {
                validateForm()
            }
            R.id.ivSelectPhoto -> {
                openGallery()
            }
        }
    }

    private fun validateForm() {
        val listInputs = listOf(binding.etName, binding.etPhone)
        if (validateInputs.validateEditTextList(listInputs)){
            contact.name = if(binding.etName.text.toString() != binding.etName.hint)  binding.etName.text.toString() else Constants.STRING_EMPTY
            contact.lastName = if(binding.etLastName.text.toString() != binding.etLastName.hint)  binding.etLastName.text.toString() else Constants.STRING_EMPTY
            contact.lastNameM = if(binding.etLastNameM.text.toString() != binding.etLastNameM.hint)  binding.etLastNameM.text.toString() else Constants.STRING_EMPTY
            contact.age = if(binding.etAge.text.toString() != binding.etAge.hint)  binding.etAge.text.toString() else Constants.STRING_EMPTY
            contact.phoneNumber = if(binding.etPhone.text.toString() != binding.etPhone.hint)  binding.etPhone.text.toString() else Constants.STRING_EMPTY
            contact.sex = if(binding.etSex.text.toString() != binding.etSex.hint)  binding.etSex.text.toString() else Constants.STRING_EMPTY
            if(contact.id >= 0){
                contactViewModel.updateContact(contact)
            }else{
                contactViewModel.insertContact(contact)
            }
           return
        }
        alerts.showSimpleLongToast(requireContext().getString(R.string.text_required_fields))
    }

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri = data.data!!
            contact.photo = selectedImageUri.toString()
            binding.ivPreviewPhoto.setImageURI(selectedImageUri)
        }
    }

}