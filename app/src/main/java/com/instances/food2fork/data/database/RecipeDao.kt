package com.instances.food2fork.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.instances.food2fork.data.model.response.Results

@Dao
interface RecipeDao {
    @Query("SELECT * FROM Results")
    fun getQuotes(): PagingSource<Int, Results>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(result: List<Results>)

    @Query("DELETE FROM Results")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM Results WHERE pk < :id")
    fun getPosition(id: Int): Int

}
