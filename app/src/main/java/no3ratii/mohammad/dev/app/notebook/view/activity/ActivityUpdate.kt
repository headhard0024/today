package no3ratii.mohammad.dev.app.notebook.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_update.*
import no3ratii.mohammad.dev.app.notebook.R
import no3ratii.mohammad.dev.app.notebook.viewModel.ListViewModel
import no3ratii.mohammad.dev.app.notebook.base.G
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
        setContentView(R.layout.fragment_update)

        userViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        val userId = intent.getIntExtra("id", 1)
        user = userViewModel.singleUser(userId)

//        supportActionBar?.subtitle = user!!.title;

        imgDelete.setOnClickListener {
            deleteUser(user!!)
            finish()
        }

        timerInitialize(user!!)

        edtTitle.setText(user!!.title)
        edtDesc.setText(user!!.desc)

        val starthourtime = user!!.startTiem.split(":")
        val startmintime = user!!.endTime.split(":")
        hourStartTime.value = starthourtime[0].toInt()
        minutesStartTime.value = starthourtime[1].toInt()
        hourEndTime.value = startmintime[0].toInt()
        minutesEndTime.value = startmintime[1].toInt()

        mHourStartTime = starthourtime[0].toInt()
        mMinutesStartTime= starthourtime[1].toInt()
        mHourEndTime  = startmintime[0].toInt()
        mMinutesEndTime = startmintime[1].toInt()

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
    }

    private fun upDateUser(
        userId: Int,
        name: String,
        family: String,
        startTime: String,
        endTime: String,
        checked: Boolean
    ) {
        val user = User(userId, name, family, startTime, endTime , checked)
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