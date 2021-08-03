package com.mani.todolist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase()  {

    abstract fun taskDao() : TaskDao

    private class TaskDatabaseCallback(private val scope : CoroutineScope) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let{
                    database->
                scope.launch {
                    populateDatabase(database.taskDao())
                }
            }
        }

        suspend fun populateDatabase(taskDao: TaskDao){
            taskDao.insert(Task("Wash Dished"))
            taskDao.insert(Task("Repair laptop"))
        }
    }


    companion object{
        @Volatile
        private var INSTANCE : TaskDatabase? = null

        fun getDatabase(context : Context, scope: CoroutineScope) : TaskDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                    )
                    .addCallback(TaskDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }






}