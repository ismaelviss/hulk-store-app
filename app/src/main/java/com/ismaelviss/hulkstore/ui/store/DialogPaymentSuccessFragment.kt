package com.ismaelviss.hulkstore.ui.store

import android.app.Activity
import android.app.Dialog
import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ismaelviss.hulkstore.R
import com.ismaelviss.hulkstore.utils.Tools.Companion.getFormatMoney
import javax.inject.Inject

class DialogPaymentSuccessFragment : DialogFragment() {

    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.dialog_payment_success, container, false)
        (rootView!!.findViewById<View>(R.id.fab) as FloatingActionButton).setOnClickListener {

            dismiss()

        }

        arguments?.apply {
            rootView?.findViewById<TextView>(R.id.date)?.text = this.getString("date")
            //rootView?.findViewById<TextView>(R.id.time)?.text = this.getString("time")
            rootView?.findViewById<TextView>(R.id.fullName)?.text = this.getString("name")
            rootView?.findViewById<TextView>(R.id.email)?.text = this.getString("email")
            rootView?.findViewById<TextView>(R.id.amountTotal)?.text = getFormatMoney(this.getDouble("amount"))
        }

        return rootView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()

        goStoreActivity()
    }

    private fun goStoreActivity() {
        val intent = Intent(requireContext(), StoreCartActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        requireActivity().finishActivity(Activity.RESULT_OK)
    }
}