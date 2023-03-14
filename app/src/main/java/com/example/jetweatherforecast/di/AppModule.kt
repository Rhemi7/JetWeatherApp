package com.example.jetweatherforecast.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jetweatherforecast.data.WeatherDao
import com.example.jetweatherforecast.data.WeatherDatabase
import com.example.jetweatherforecast.network.WeatherApi
import com.example.jetweatherforecast.repository.WeatherRepository
import com.example.jetweatherforecast.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(api: WeatherApi) = WeatherRepository(api)

    @Singleton
    @Provides
    fun provideOpenWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao =
        weatherDatabase.weatherDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(
            context = context,
            WeatherDatabase::class.java,
            name = "weather_database"
        ).fallbackToDestructiveMigration().build()

}
