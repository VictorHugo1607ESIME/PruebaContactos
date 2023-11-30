package com.example.template.views.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.contact.R
import com.example.template.constants.Constants
import com.contact.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {
        object : CountDownTimer(Constants.SPLASH_TIME_OUT.toLong(), Constants.SPLASH_TIME_OUT.toLong()) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                goToNextFragment()
            }
        }.start()
    }

    fun goToNextFragment(){
        findNavController().navigate(R.id.action_splashScreenFragment_to_contactsFragment)
    }

}