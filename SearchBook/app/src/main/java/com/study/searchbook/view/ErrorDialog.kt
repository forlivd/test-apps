package com.study.searchbook.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.study.searchbook.databinding.DialogErrorBinding

class ErrorDialog(val msg: String?) : DialogFragment() {

    private lateinit var binding: DialogErrorBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogErrorBinding.inflate(inflater, container, false)

        //Display 크기 조절
        initDialogDisplay()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvError.text = msg

            btnConfirm.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun initDialogDisplay() {
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestFeature(Window.FEATURE_NO_TITLE)
        }
    }

}