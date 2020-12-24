package no3ratii.mohammad.dev.app.notebook.base.helper

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.public_bottom_sheet.*
import no3ratii.mohammad.dev.app.notebook.R
import no3ratii.mohammad.dev.app.notebook.model.intrface.IBottomShotRespons


class PublicBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.public_bottom_sheet, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //show phisical keybord in start bottomsheet
        val imgr: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        imgr.showSoftInput(edtValue, 0)
        edtValue.requestFocus()

        if (arguments != null) {
            txtTitle.text = arguments?.getString(title).toString()
            edtValue.setText(arguments?.getString(editText).toString())
            button.text = arguments?.getString(okButton).toString()
        }
        button.setOnClickListener {
            iBottomShotRespons.onEditTextValue(edtValue.text.toString())
            dismiss()
        }
    }

    companion object {

        const val TAG = "CustomBottomSheetDialogFragment"

        private const val title = "title"
        private const val editText = "hint"
        private const val okButton = "okButton"

        private lateinit var iBottomShotRespons: IBottomShotRespons
        fun setClicked(iBottomShotRespons: IBottomShotRespons) {
            this.iBottomShotRespons = iBottomShotRespons
        }

        fun newInstance(title: String, editText: String, okButton: String): PublicBottomSheet {
            val frag = PublicBottomSheet()
            val args = Bundle()
            args.putString(this.title, title)
            args.putString(this.editText, editText)
            args.putString(this.okButton, okButton)
            frag.arguments = args
            return frag
        }
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bottomSheet = (requireView().parent as View)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
            bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        }
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
    }
}
