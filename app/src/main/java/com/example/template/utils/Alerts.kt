package com.example.template.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.contact.R
import com.example.template.constants.Constants
import com.google.android.material.button.MaterialButton

class Alerts(private val context: Context) {

    fun showSimpleLongToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showSimpleShortToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showAlertInfo(message: String, status: Int = Constants.FAIL) {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.custom_alert_dialog, null)

        val messageTextView: TextView = view.findViewById(R.id.messageTextView)
        val positiveButton: Button = view.findViewById(R.id.actionButton)
        val iconStatus: ImageView = view.findViewById(R.id.ivIcon)

        if(status == Constants.SUCCESS){
            iconStatus.setImageResource(R.drawable.baseline_check_circle_outline_24)
            val drawable: Drawable = iconStatus.drawable
            val wrappedDrawable = DrawableCompat.wrap(drawable).mutate()
            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(context, R.color.secondary_color))
            iconStatus.setImageDrawable(wrappedDrawable)
            positiveButton.visibility = View.GONE
        }

        messageTextView.text = message

        val alertDialog = AlertDialog.Builder(context)
            .setView(view)
            .create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        positiveButton.setOnClickListener {
            alertDialog.dismiss()
        }

        val handler = Handler()
        handler.postDelayed({
            alertDialog.dismiss()
        }, 1500)

        alertDialog.show()
    }

    fun alertValidateAction(confirmAction: Interfaces.SelectedAction, message: String) {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.custom_alert_validate_dialog, null)

        val deleteMaterialButton: MaterialButton = view.findViewById(R.id.btnDeleteAction)
        val updateMaterialButton: MaterialButton = view.findViewById(R.id.btnUpdateAction)
        val tvMessage: TextView = view.findViewById(R.id.messageTextView)

        tvMessage.text = message
        val alertDialog = AlertDialog.Builder(context)
            .setView(view)
            .create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        deleteMaterialButton.setOnClickListener {
            confirmAction.onSelectedAction(Constants.DELETE)
            alertDialog.dismiss()
        }

        updateMaterialButton.setOnClickListener {
            confirmAction.onSelectedAction(Constants.UPDATE)
            alertDialog.dismiss()
        }

        alertDialog.show()
    }
}