package no3ratii.mohammad.dev.app.notebook.base.helper

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.popup_dialog.*
import no3ratii.mohammad.dev.app.notebook.R
import no3ratii.mohammad.dev.app.notebook.model.intrface.IPopupClick


class PopupDialog:DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.popup_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            txtTitle.text = arguments?.getString(title).toString()
            txtDesc.text = arguments?.getString(description).toString()
            txtYes.text = arguments?.getString(yesButton).toString()
            txtNo.text = arguments?.getString(noButton).toString()
        }
        txtExit.setOnClickListener {
            dismiss()
        }
        layYes.setOnClickListener {
            iPopupClick.onYesClick()
            dismiss()
        }
        layNo.setOnClickListener {
            iPopupClick.onNoClick()
            dismiss()
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        dismiss()
    }

    companion object {

        private lateinit var iPopupClick : IPopupClick

        private const val iconRes = "icon"
        private const val title = "title"
        private const val description = "desc"
        private const val yesButton = "yesText"
        private const val noButton = "noText"

        fun setClicked(iPopupClick: IPopupClick){
            this.iPopupClick = iPopupClick
        }

        fun newInstance(title: String, description: String, yesButton: String, noButton: String): PopupDialog {
            val frag = PopupDialog()
            val args = Bundle()
            args.putString(this.iconRes, iconRes)
            args.putString(this.title, title)
            args.putString(this.description, description)
            args.putString(this.yesButton, yesButton)
            args.putString(this.noButton, noButton)
            frag.arguments = args
            return frag
        }

        fun newInstanceBox(iconRes: String, description: String, yesButton: String, noButton: String, boxId: String): PopupDialog {
            val frag = PopupDialog()
            val args = Bundle()
            //
            frag.arguments = args
            return frag
        }


    }
}