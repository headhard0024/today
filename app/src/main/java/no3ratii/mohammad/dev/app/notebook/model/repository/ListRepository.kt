package no3ratii.mohammad.dev.app.notebook.model.repository

import androidx.lifecycle.LiveData
import no3ratii.mohammad.dev.app.notebook.model.Dao.ListDao
import no3ratii.mohammad.dev.app.notebook.model.User

class ListRepository(var userDao: ListDao) {

    var readAllData : LiveData<List<User>> = userDao.readAllUser()

    fun singleUser(id:Int):User{
        return userDao.singleItem(id)
    }

    suspend fun insertUser(user: User){
        userDao.insertUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    //delete all value in dataabase
    suspend fun deleteAll(){
        userDao.deleteAllItem()
    }
}