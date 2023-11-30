package com.example.template.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.contact.R
import com.contact.databinding.FragmentContactsBinding
import com.example.template.constants.Constants
import com.example.template.models.ContactsDAO
import com.example.template.utils.Alerts
import com.example.template.utils.Interfaces
import com.example.template.views.adapters.ContactsAdapter
import com.example.template.views.fragments.viewmodel.ContactViewModel
import kotlinx.coroutines.launch

class ContactsFragment : Fragment(), OnClickListener, Interfaces.ClickItemSelect, Interfaces.SelectedAction {

    private lateinit var binding: FragmentContactsBinding
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var alerts: Alerts
    private lateinit var adapter: ContactsAdapter
    private lateinit var listContacts: MutableList<ContactsDAO>
    private lateinit var contactSelected: ContactsDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        contactViewModel = ContactViewModel(requireContext())
        contactViewModel.getContacts()
        alerts = Alerts(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        initUI()
        initListeners()
        initObserve()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        contactViewModel.getContacts()
    }

    private fun initUI() {
        binding.rvListContacts.layoutManager = LinearLayoutManager(context)
        adapter = ContactsAdapter(this)
        binding.rvListContacts.adapter = adapter
    }

    private fun initListeners(){
        binding.btnAddContact.setOnClickListener(this)
    }

    private fun initObserve(){
        lifecycleScope.launch {
            contactViewModel.contactStatus.collect{ dataStatus ->
                when(dataStatus.status){

                    Constants.LOAD ->{
                        binding.progressBar.visibility = VISIBLE
                    }

                    Constants.SUCCESS -> {
                        when(dataStatus.operation){
                            Constants.GET -> {
                                if(dataStatus.contacts!!.isNotEmpty()){
                                    binding.tvEmptyList.visibility = View.GONE
                                    adapter.submitList(dataStatus.contacts)
                                }
                                else{
                                    binding.tvEmptyList.visibility = View.VISIBLE
                                    adapter.submitList(dataStatus.contacts)
                                }
                            }
                            Constants.DELETE -> {
                                contactViewModel.getContacts()
                            }
                        }
                        binding.progressBar.visibility = View.GONE
                    }

                    Constants.FAIL -> {
                        binding.progressBar.visibility = View.GONE
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnAddContact -> {
                findNavController().navigate(R.id.action_contactsFragment_to_addContactFragment)
            }
        }
    }

    override fun itemSelect(value: Any) {
        contactSelected = value as ContactsDAO
        alerts.alertValidateAction(this, requireContext().getString(R.string.text_what_operation))
    }

    override fun onSelectedAction(option: Any) {
        val action = option.toString()
        when(action){
            Constants.UPDATE -> {
                val bundle = bundleOf(Constants.CONTACT to contactSelected)
                findNavController().navigate(R.id.action_contactsFragment_to_addContactFragment, bundle)
            }
            Constants.DELETE -> {
                contactViewModel.deleteContact(contactSelected)
            }
        }
    }

}