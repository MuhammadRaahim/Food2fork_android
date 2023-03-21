package com.instances.food2fork.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.instances.food2fork.data.model.response.Results


@Database(entities = [Results::class],version = 1,exportSchema = false)
abstract class RecipeDatabase: RoomDatabase() {

    companion object{
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase{
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "user_database"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().
                build()
                INSTANCE = instance
                return instance
            }
        }
    }

    abstract fun recipeDao():RecipeDao
}