package no3ratii.mohammad.dev.app.notebook.view.activity

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_update.*
import no3ratii.mohammad.dev.app.notebook.R
import no3ratii.mohammad.dev.app.notebook.viewModel.ListViewModel
import no3ratii.mohammad.dev.app.notebook.base.G
import no3ratii.mohammad.dev.app.notebook.base.helper.CirculeRevealHelper
import no3ratii.mohammad.dev.app.notebook.model.User


class ActivityUpdate : AppCompatActivity() {

    private var mHourStartTime: Int = 0
    private var mHourEndTime: Int = 0
    private var mMinutesStartTime: Int = 0
    private var mMinutesEndTime: Int = 0
    private lateinit var userViewModel: ListViewModel
    private var startTime: String = ""
    private var endTime: String = ""
    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        //init use view model and set use value from use id
        userViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        val userId = intent.getIntExtra("id", 1)
        user = userViewModel.singleUser(userId)

        //set onClicked values
        initListener(userId)
        layoutLogic(user!!)

        // update time picker value by defult time get in use set
        timerInitialize(user!!)
    }

    private fun layoutLogic(user: User) {
        edtTitle.setText(user.title)
        edtDesc.setText(user.desc)
        txtToolbarTitle.text = user.title
    }

    private fun initListener(userId: Int) {

        //set Time value to show and show
        setTimersValue()
        btnUpdate.setOnClickListener {
            startTime = "$mHourStartTime:$mMinutesStartTime"
            endTime = "$mHourEndTime:$mMinutesEndTime"
            upDateUser(
                userId,
                edtTitle.text.toString(),
                edtDesc.text.toString(),
                startTime,
                endTime,
                user!!.checked
            )
            finish()
        }

        imgDelete.setOnClickListener {
            deleteUser(user!!)
            finish()
        }

        imgBack.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CirculeRevealHelper(it, startcolor = R.color.whiteCc, defaultColor = R.color.whiteLite)
                    .init()
            }
            finish()
        }
    }

    private fun setTimersValue() {
        val starthourtime = user!!.startTiem.split(":")
        val startmintime = user!!.endTime.split(":")
        hourStartTime.value = starthourtime[0].toInt()
        minutesStartTime.value = starthourtime[1].toInt()
        hourEndTime.value = startmintime[0].toInt()
        minutesEndTime.value = startmintime[1].toInt()

        mHourStartTime = starthourtime[0].toInt()
        mMinutesStartTime = starthourtime[1].toInt()
        mHourEndTime = startmintime[0].toInt()
        mMinutesEndTime = startmintime[1].toInt()
    }

    private fun upDateUser(
        userId: Int,
        name: String,
        family: String,
        startTime: String,
        endTime: String,
        checked: Boolean
    ) {
        val user = User(userId, name, family, startTime, endTime, checked)
        userViewModel.updateUser(user)
    }

    private fun timerInitialize(user: User) {

        hourStartTime.maxValue = G.MAX_HOUR_TIME
        hourEndTime.maxValue = G.MAX_HOUR_TIME
        minutesStartTime.maxValue = G.MAX_MINUTES_TIME
        minutesEndTime.maxValue = G.MAX_MINUTES_TIME

        hourStartTime.setOnValueChangedListener { numberPicker, i, i2 ->
            mHourStartTime = i2
        }
        hourEndTime.setOnValueChangedListener { numberPicker, i, i2 ->
            mHourEndTime = i2
        }
        minutesStartTime.setOnValueChangedListener { numberPicker, i, i2 ->
            mMinutesStartTime = i2
        }
        minutesEndTime.setOnValueChangedListener { numberPicker, i, i2 ->
            mMinutesEndTime = i2
        }
    }

    private fun deleteUser(user: User) {
        userViewModel.deleteUser(user)
    }
}