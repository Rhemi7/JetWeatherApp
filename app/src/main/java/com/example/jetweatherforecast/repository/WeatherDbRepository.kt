package com.example.jetweatherforecast.repository

import com.example.jetweatherforecast.data.WeatherDao
import com.example.jetweatherforecast.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao){

    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()

    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite = favorite)

    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()

    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite = favorite)

    suspend fun getFavById(city: String) = weatherDao.getFavById(city)

    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnit()

    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)


    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)
    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()

    suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit)



}