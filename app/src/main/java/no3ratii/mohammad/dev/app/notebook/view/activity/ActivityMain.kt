package no3ratii.mohammad.dev.app.notebook.view.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import no3ratii.mohammad.dev.app.notebook.R
import no3ratii.mohammad.dev.app.notebook.base.helper.DataStore
import no3ratii.mohammad.dev.app.notebook.base.helper.GlideHelper
import no3ratii.mohammad.dev.app.notebook.base.helper.ReplaceFragment
import no3ratii.mohammad.dev.app.notebook.model.intrface.IRecyclerPosition
import no3ratii.mohammad.dev.app.notebook.view.fragment.FragmentList
import no3ratii.mohammad.dev.app.notebook.view.fragment.FragmentNote
import no3ratii.mohammad.dev.app.notebook.view.fragment.FragmentSetting
import no3ratii.mohammad.dev.app.notebook.viewModel.ListViewModel
import no3ratii.mohammad.dev.app.notebook.viewModel.NoteViewModel
import no3ratii.mohammad.dev.app.notebook.viewModel.SettingFragmentViewModel


class ActivityMain : AppCompatActivity() {

    var state = 1
    private var viewVisible = true
    private var viewVisible1 = false
    private lateinit var userViewModel: ListViewModel
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var settingViewModel: SettingFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        settingViewModel = ViewModelProvider(this).get(SettingFragmentViewModel::class.java)
        val actionBar: android.app.ActionBar? = actionBar
        initUserImage()

        /*initialize bottom navigation*/
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.userList -> {
                    setStatusBarColor("userList", toolbar, R.color.colorPrimaryDark)
                    txtTitle.text = "یادداشت"
                    ReplaceFragment(
                        FragmentList(),
                        this.supportFragmentManager,
                        R.id.layRoot
                    ).init()
                    state = 1
                    true
                }
                R.id.noteList -> {
                    setStatusBarColor("noteList", toolbar, R.color.colorPrimaryDark)
                    txtTitle.text = "برنامه روزانه"
                    ReplaceFragment(
                        FragmentNote(),
                        this.supportFragmentManager,
                        R.id.layRoot
                    ).init()
                    state = 2
                    true
                }
                R.id.setting -> {
                    setStatusBarColor("setting", toolbar, R.color.colorAccent)
                    ReplaceFragment(
                        FragmentSetting(),
                        this.supportFragmentManager,
                        R.id.layRoot
                    ).init()
                    true
                }
                else -> false
            }
        }

        when (state) {
            1 -> {
                imgDelete.setOnClickListener { view ->
                    showDialog(view)
                    true
                }
            }
            2 -> {
                imgDelete.setOnClickListener { view ->
                    showDialog(view)
                    true
                }
            }
        }

        FragmentList.setContactOnBackPress(object : IRecyclerPosition {
            override fun onBackPress(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int,
                fab: FloatingActionButton
            ) {
                val layoutManager = LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                Log.i("MONO", "onBackPress: " + layoutManager.findFirstVisibleItemPosition())
                if (dy > 0 && fab.visibility === View.VISIBLE) {
                    fab.hide()
                    setAnimation(R.anim.slide_in_top, toolbar)
                    setAnimation(R.anim.slide_in_top_to_bottom, bottom_navigation)
                    toolbar.visibility = View.GONE
                    bottom_navigation.visibility = View.GONE
                } else if (dy < 0 && fab.visibility !== View.VISIBLE) {
                    fab.show()
                    setAnimation(R.anim.slide_in_bottom, toolbar)
                    setAnimation(R.anim.slide_in_bottom_to_top, bottom_navigation)
                    toolbar.visibility = View.VISIBLE
                    bottom_navigation.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setStatusBarColor(fragmentName: String, toolbar: Toolbar?, colorPrimaryDark: Int) {
        if (fragmentName.equals("userList") || fragmentName.equals("noteList")) {
            toolbar?.visibility = View.VISIBLE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = ContextCompat.getColor(this, colorPrimaryDark)
            }
        } else {
            toolbar?.visibility = View.GONE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = ContextCompat.getColor(this, colorPrimaryDark)
            }
        }
    }

    private fun initUserImage() {
        val imageUrl = DataStore.getValue("IMAGE_URL")
        imageUrl.asLiveData().observe(this as LifecycleOwner, Observer {
            if (it != null) {
                GlideHelper(imgUserImage, it, R.drawable.ic_account_circle).bilde()
            }
        })

        settingViewModel.imageUrl.observe(this, Observer {
            GlideHelper(imgUserImage, it, R.drawable.ic_account_circle).bilde()
        })
    }

    private fun setAnimation(slideInTop: Int, view: View?) {
        val scaleAnim: Animation =
            AnimationUtils.loadAnimation(this@ActivityMain, slideInTop)
        view?.startAnimation(scaleAnim)
    }


    private fun showDialog(view: View) {
        val builder =
            AlertDialog.Builder(view.context)
        builder.setTitle("هشدار")
        builder.setMessage("آیا میخواهید همه لیست ها حذف شوند ؟")
        builder.setPositiveButton(
            "بله"
        ) { dialog, which ->
            when (state) {
                1 -> {
                    userViewModel.deleteAll()
                    true
                }
                2 -> {
                    noteViewModel.deleteAllNote()
                    true
                }
            }
            dialog.dismiss()
        }
        builder.setNegativeButton(
            "نه"
        ) { dialog, which -> // Do nothing
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }
}