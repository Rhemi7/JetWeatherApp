package com.example.jetweatherforecast.data

import androidx.room.*
import com.example.jetweatherforecast.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query(value = "SELECT * from fav_tbl")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * from fav_tbl where city = :city")
    suspend fun getFavById(city: String) : Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE from fav_tbl")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query(value = "SELECT * from settings_tbl")
    fun getUnit(): Flow<List<com.example.jetweatherforecast.model.Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: com.example.jetweatherforecast.model.Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: com.example.jetweatherforecast.model.Unit)

    @Delete
    suspend fun deleteUnit(unit: com.example.jetweatherforecast.model.Unit)

    @Query("DELETE from settings_tbl")
    suspend fun deleteAllUnits()
}