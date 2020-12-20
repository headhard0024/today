package no3ratii.mohammad.dev.app.notebook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import no3ratii.mohammad.dev.app.notebook.base.connection.DataBaseConnection
import no3ratii.mohammad.dev.app.notebook.model.User
import no3ratii.mohammad.dev.app.notebook.model.repository.ListRepository

class ListViewModel(application: Application) : AndroidViewModel(application) {

    val readAllUser: LiveData<List<User>>
    private val userRepository: ListRepository

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.insertUser(user)
        }
    }

    fun singleUser(id: Int): User {
        return userRepository.singleUser(id)
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateUser(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteUser(user)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteAll()
        }
    }

    init {
        val userDao = DataBaseConnection.getDatabase(application.applicationContext).userDao()
        userRepository =ListRepository(userDao)
        readAllUser = userRepository.readAllData
    }

}