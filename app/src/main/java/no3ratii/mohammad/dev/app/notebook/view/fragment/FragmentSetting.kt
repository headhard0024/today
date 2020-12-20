package no3ratii.mohammad.dev.app.notebook.view.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.aminography.choosephotohelper.ChoosePhotoHelper
import com.aminography.choosephotohelper.callback.ChoosePhotoCallback
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*
import kotlinx.android.synthetic.main.fragment_setting.view.edtUserName
import kotlinx.android.synthetic.main.fragment_setting.view.imgUserImage
import no3ratii.mohammad.dev.app.notebook.R
import no3ratii.mohammad.dev.app.notebook.base.helper.DataStore
import no3ratii.mohammad.dev.app.notebook.base.helper.GlideHelper
import no3ratii.mohammad.dev.app.notebook.model.WordsElders
import no3ratii.mohammad.dev.app.notebook.viewModel.SettingFragmentViewModel

class FragmentSetting : Fragment() {

    private lateinit var settingViewModel: SettingFragmentViewModel
    private var choosePhotoHelper: ChoosePhotoHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingViewModel = ViewModelProvider(this).get(SettingFragmentViewModel::class.java)
        return inflater.inflate(R.layout.fragment_setting, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize(view, savedInstanceState)
    }

    private fun initialize(view: View, savedInstanceState: Bundle?) {
        //WordsElders a random class for get word
        view.bigText.text = WordsElders.word

        //for first use and set user image in edit itme
        setUserImage(view, savedInstanceState)

        //init srcollview logic
        initScrollViewLogic(view)

        // show user name in xml
        setUserNameLogic(view)

        // set user image from dataStore
        val imageUrl = DataStore.getValue("IMAGE_URL")
        imageUrl.asLiveData().observe(this as LifecycleOwner, Observer {
            if (!it.equals("")) {
                GlideHelper(view.imgUserImage, it!!, R.drawable.ic_account_circle).bilde()
                view.imgChooseImage.setImageResource(R.drawable.ic_edit)
                view.imgChooseImage.setPadding(15, 15, 15, 15)
            } else {
                view.imgUserImage.setImageResource(R.drawable.ic_account_circle)
                view.imgChooseImage.setImageResource(R.drawable.ic_add_white)
            }
        })
    }

    private fun initScrollViewLogic(view: View) {

    }

    private fun setUserNameLogic(view: View) {
        var userName = DataStore.getValue("USER_NAME")
        userName.asLiveData().observe(this as LifecycleOwner, Observer {
            if (it != null) {
                view.txtUserName.text = it
                view.edtUserName.setText(it)
            }
        })

        settingViewModel.userName.observe(viewLifecycleOwner, Observer {
            view.txtUserName.text = it
        })

        settingViewModel.userName.value = view.edtUserName.text.toString()
        view.edtUserName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                settingViewModel.userName.value = "" + p0
            }

        })
    }

    private fun setUserImage(view: View, savedInstanceState: Bundle?) {
        choosePhotoHelper = ChoosePhotoHelper.with(this)
            .asFilePath()
            .withState(savedInstanceState)
            .build(object : ChoosePhotoCallback<String> {
                override fun onChoose(photo: String?) {
                    if (photo.equals(null)) {
                        view.imgUserImage.setImageResource(R.drawable.ic_account_circle)
                    } else {
                        GlideHelper(
                            view.imgUserImage,
                            photo!!,
                            R.drawable.ic_account_circle
                        ).bilde()
                        DataStore.setValue(photo, "IMAGE_URL")
                        settingViewModel.imageUrl.value = photo
                    }
                }
            })
        view.imgChooseImage.setOnClickListener {
            choosePhotoHelper!!.showChooser()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        choosePhotoHelper!!.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        choosePhotoHelper!!.onRequestPermissionsResult(
            requestCode,
            permissions as Array<String>, grantResults
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        choosePhotoHelper!!.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        DataStore.setValue(edtUserName.text.toString(), "USER_NAME")
    }
}