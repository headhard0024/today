package no3ratii.mohammad.dev.app.notebook.model.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import no3ratii.mohammad.dev.app.notebook.model.User

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllUser(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE id = :itemId")
    fun singleItem(itemId: Int): User

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE  FROM user_table")
    suspend fun deleteAllItem()

}