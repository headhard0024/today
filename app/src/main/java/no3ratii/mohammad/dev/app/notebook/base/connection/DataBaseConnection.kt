package no3ratii.mohammad.dev.app.notebook.base.connection

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import no3ratii.mohammad.dev.app.notebook.model.Dao.ListDao
import no3ratii.mohammad.dev.app.notebook.model.Dao.NoteDao
import no3ratii.mohammad.dev.app.notebook.model.Note
import no3ratii.mohammad.dev.app.notebook.model.User

/*
create database for use in application
* can use more a class for initialize db
* befor set class data and use room values
* */
@Database(entities = [User::class , Note::class], version = 5, exportSchema = false)
abstract class DataBaseConnection: RoomDatabase() {

    //requst value for use database after create db
    abstract fun userDao(): ListDao
    abstract fun noteDao(): NoteDao

    //create singleton design pattern for use db
    companion object {
        @Volatile
        private var INSTANCE: DataBaseConnection? = null
        fun getDatabase(context: Context): DataBaseConnection {
            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataBaseConnection::class.java,
                        "user_database"
                    )
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                    instance
                }
        }
    }

}