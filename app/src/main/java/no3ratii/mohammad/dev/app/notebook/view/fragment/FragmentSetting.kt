package no3ratii.mohammad.dev.app.notebook.view.fragment

//import kotlinx.android.synthetic.main.fragment_setting.view.edtUserName

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.aminography.choosephotohelper.ChoosePhotoHelper
import com.aminography.choosephotohelper.callback.ChoosePhotoCallback
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*
import no3ratii.mohammad.dev.app.notebook.R
import no3ratii.mohammad.dev.app.notebook.base.helper.CirculeRevealHelper
import no3ratii.mohammad.dev.app.notebook.base.helper.DataStore
import no3ratii.mohammad.dev.app.notebook.base.helper.GlideHelper
import no3ratii.mohammad.dev.app.notebook.base.helper.PublicBottomSheet
import no3ratii.mohammad.dev.app.notebook.model.WordsElders
import no3ratii.mohammad.dev.app.notebook.model.intrface.IBottomShotRespons
import no3ratii.mohammad.dev.app.notebook.viewModel.SettingFragmentViewModel
import kotlin.math.hypot


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
        setUserNameAndFantziLogic(view)

        //show name bottomShet
        view.layName.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CirculeRevealHelper(it, R.color.whiteLite, R.color.white)
                    .radius(10)
                    .init()
            }
            showNameBottomShet(view)
        }

        //show fantezi bottomSheet
        view.layFantzi.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CirculeRevealHelper(it, R.color.whiteLite, R.color.white)
                    .radius(10)
                    .init()
            }
            showFantziBottomShet(view)
        }

        // set user image from dataStore
        val imageUrl = DataStore.getValue("IMAGE_URL")
        imageUrl.asLiveData().observe(this as LifecycleOwner, Observer {
            if (!it.equals("")) {
                GlideHelper(view.imgUserImage, it!!, R.drawable.ic_account_circle).bilde()
                view.imgChooseImage.setImageResource(R.drawable.ic_edit_white)
                view.imgChooseImage.setPadding(15, 15, 15, 15)
            } else {
                view.imgUserImage.setImageResource(R.drawable.ic_account_circle)
                view.imgChooseImage.setImageResource(R.drawable.ic_add_white)
            }
        })
    }

    private fun showNameBottomShet(view: View) {
        //set bottom sheet manager
        val manager: FragmentManager =
            (view.context as AppCompatActivity).supportFragmentManager
        // call PublicBottomSheet class and set new instance
        PublicBottomSheet.newInstance("اسمت چیه ؟", txtName.text.toString(), "ثبت")
            .show(manager, PublicBottomSheet.TAG)
        // call PublicBottomSheet listener for return editText value
        PublicBottomSheet.setClicked(object : IBottomShotRespons {
            override fun onEditTextValue(value: String) {
                txtUserName.text = value
                txtName.text = value
                DataStore.setValue(value, "USER_NAME")
            }
        })
    }

    private fun showFantziBottomShet(view: View) {
        //set bottom sheet manager
        val manager: FragmentManager =
            (view.context as AppCompatActivity).supportFragmentManager
        // call PublicBottomSheet class and set new instance
        PublicBottomSheet.newInstance(
            "فانتزیت چیه ؟ مثلا سفر به ماه",
            txtFantezi.text.toString(),
            "ثبت"
        )
            .show(manager, PublicBottomSheet.TAG)
        // call PublicBottomSheet listener for return editText value
        PublicBottomSheet.setClicked(object : IBottomShotRespons {
            override fun onEditTextValue(value: String) {
                txtFantezi.text = value
                DataStore.setValue(value, "FANTZI")
            }
        })
    }


    private fun initScrollViewLogic(view: View) {

    }

    private fun setUserNameAndFantziLogic(view: View) {
        var userName = DataStore.getValue("USER_NAME")
        var fantzi = DataStore.getValue("FANTZI")
        userName.asLiveData().observe(this as LifecycleOwner, Observer {
            if (it != null) {
                view.txtUserName.text = it
                view.txtName.text = it
            }
        })
        fantzi.asLiveData().observe(this as LifecycleOwner, Observer {
            if (it != null) {
                view.txtFantezi.text = it
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
}