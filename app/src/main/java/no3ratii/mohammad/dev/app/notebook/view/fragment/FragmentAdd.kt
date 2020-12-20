package no3ratii.mohammad.dev.app.notebook.view.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import no3ratii.mohammad.dev.app.notebook.R
import no3ratii.mohammad.dev.app.notebook.base.G
import no3ratii.mohammad.dev.app.notebook.base.helper.ReplaceFragment
import no3ratii.mohammad.dev.app.notebook.model.User
import no3ratii.mohammad.dev.app.notebook.viewModel.ListViewModel


class FragmentAdd : Fragment() {

    private lateinit var userViewModel: ListViewModel
    private var mHourStartTime: Int = 0
    private var mHourEndTime: Int = 0
    private var mMinutesStartTime: Int = 0
    private var mMinutesEndTime: Int = 0
    private var startTime: String = ""
    private var endTime: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        userViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        initialize(view)
        return view
    }

    private fun initialize(view: View) {

        timerInitialize(view)

        view.btnOk.setOnClickListener {
            startTime = "$mHourStartTime:$mMinutesStartTime"
            endTime = "$mHourEndTime:$mMinutesEndTime"
            insertUserToDataBase(
                edtTitle.text.toString(),
                edtDesc.text.toString(),
                startTime,
                endTime
            )
        }
    }

    private fun insertUserToDataBase(
        name: String,
        family: String,
        startTime: String,
        endTime: String
    ) {
        if (checkForEmpty(name, family)) {
            val user = User(0, name, family, startTime, endTime, false)
            userViewModel.addUser(user)
            ReplaceFragment(FragmentList(),childFragmentManager , R.id.layRootAdd)
                .init()
                .clearBackStack()

        } else {
            Toast.makeText(context, "لطفا متن را وارد کنید", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkForEmpty(name: String, family: String): Boolean {
        return name.isNotEmpty() || family.isNotEmpty()
    }

    private fun timerInitialize(view: View) {

        view.hourStartTime.maxValue = G.MAX_HOUR_TIME
        view.hourEndTime.maxValue = G.MAX_HOUR_TIME
        view.minutesStartTime.maxValue = G.MAX_MINUTES_TIME
        view.minutesEndTime.maxValue = G.MAX_MINUTES_TIME

        view.hourStartTime.setOnValueChangedListener { numberPicker, i, i2 ->
            mHourStartTime = i2
        }
        view.hourEndTime.setOnValueChangedListener { numberPicker, i, i2 ->
            mHourEndTime = i2
        }
        view.minutesStartTime.setOnValueChangedListener { numberPicker, i, i2 ->
            mMinutesStartTime = i2
        }
        view.minutesEndTime.setOnValueChangedListener { numberPicker, i, i2 ->
            mMinutesEndTime = i2
        }
    }
}