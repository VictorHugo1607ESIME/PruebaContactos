package com.example.template.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.EditText
import com.contact.R
import com.google.android.material.textfield.TextInputEditText

class ValidateInputs(private val context: Context) {

    private var isPasswordVisible = false

    fun viewPass(list: List<TextInputEditText>){
        list.forEach {
            it.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    val drawableRight = 2 // La posición del ícono a la derecha

                    if (event.rawX >= (it.right - it.compoundDrawables[drawableRight].bounds.width())) {
                        // Se hizo clic en el ícono
                        togglePasswordVisibility(it)
                        return@setOnTouchListener true
                    }
                }
                false
            }
        }
    }

    fun validateMail(email: EditText): Boolean{
        val pattern = android.util.Patterns.EMAIL_ADDRESS
        if(!pattern.matcher(email.text).matches())
            email.error = context.getString(R.string.text_invalid_mail)
        return pattern.matcher(email.text).matches()
    }

    fun lowercaseText(editText: EditText){
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                val lowercaseText = text.toLowerCase()
                if (text != lowercaseText) {
                    editText.setText(lowercaseText)
                    editText.setSelection(lowercaseText.length)
                }
            }
        })
    }

    private fun togglePasswordVisibility(item: TextInputEditText) {
        if (isPasswordVisible) {
            item.transformationMethod = PasswordTransformationMethod.getInstance()
            item.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ojo_cerrado, 0)
        } else {
            item.transformationMethod = HideReturnsTransformationMethod.getInstance()
            item.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ojo_abierto, 0)
        }

        isPasswordVisible = !isPasswordVisible
    }

    fun validateEditTextList(etList: List<EditText>, viewError: Boolean? = null):Boolean{
        return etList.all { editText ->
            var isValid = true

            for (editText in etList) {
                val text = editText.text?.trim()

                if (text.isNullOrEmpty()) {
                    if(viewError != false)
                        editText.error = context.getString(R.string.text_obligatory_field)
                    isValid = false
                }
            }

            return isValid
        }
    }
}