package no3ratii.mohammad.dev.app.notebook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SettingFragmentViewModel(application: Application) : AndroidViewModel(application) {

    var userName = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()


}