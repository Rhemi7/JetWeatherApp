 package com.example.jetweatherforecast.screens.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.navigation.WeatherScreens
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDecimals
import com.example.jetweatherforecast.widget.WeatherAppBar
import com.example.jetweatherforecast.widget.main_screen_widgets.HumidityWindPressureRow
import com.example.jetweatherforecast.widget.main_screen_widgets.SunSetRise
import com.example.jetweatherforecast.widget.main_screen_widgets.WeatherStateImage
import com.example.jetweatherforecast.widget.main_screen_widgets.WeekWeatherInfo

 @Composable
 fun WeatherMainScreen(
     navController: NavController,
     city: String?,
//     mainViewModel: MainViewModel = hiltViewModel()
 ) {
     val mainViewModel = hiltViewModel<MainViewModel>()

     Log.d("TAG", "WeatherMainScreen: $city")

     val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(initialValue = DataOrException(loading = true)) {
         value = mainViewModel.getWeatherData(city = city.toString())
     }.value

     if (weatherData.loading == true) {
         CircularProgressIndicator()
     } else if (weatherData.data != null) {
         MainScaffold(weather = weatherData.data!!, navController)
     }


 }

 @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
 @Composable
 fun MainScaffold(weather: Weather, navController: NavController) {

    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.city.name + " ,${weather.city.country}",
//            icon = Icons.Default.ArrowBack,
            navController = navController,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },
            elevation = 5.dp
        ) {
            Log.d("TAG", "MainScaffold: Button Clicked")
        }
    }) {
        MainContent(data = weather)
    }
 }

 @Composable
 fun MainContent(data: Weather) {
     val weatherItem = data.list[0]
//     val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"
     Column(
         Modifier
             .fillMaxWidth(),
         verticalArrangement = Arrangement.Center,
         horizontalAlignment = Alignment.CenterHorizontally
     ) {
         Text(
             text = formatDate(weatherItem.dt), //Wed Nov 30
             style = MaterialTheme.typography.caption,
             color = MaterialTheme.colors.onSecondary,
             fontWeight = FontWeight.SemiBold,
             modifier = Modifier.padding(6.dp)
         )
         Surface(modifier = Modifier
             .padding(4.dp)
             .size(200.dp), shape = CircleShape,
             color = Color(0xffffc400)
         ) {
             Column(
                 verticalArrangement = Arrangement.Center,
                 horizontalAlignment = Alignment.CenterHorizontally
             ) {
                 WeatherStateImage(icon =  weatherItem.weather[0].icon)
                 Text(
                     text = formatDecimals(weatherItem.temp.day) + "º",
                     style = MaterialTheme.typography.h4,
                     fontWeight = FontWeight.ExtraBold
                 )
                 Text(
                     text = weatherItem.weather[0].main,
                     fontStyle = FontStyle.Italic
                 )
             }
         }
         HumidityWindPressureRow(weather= weatherItem)
         Divider()
         SunSetRise(weather = weatherItem)
         Text(
             text = "This Week",
             style = TextStyle(
                 fontWeight = FontWeight.ExtraBold,
                 fontSize = 16.sp
             ),
             modifier = Modifier
                 .align(Alignment.CenterHorizontally)
                 .padding(top = 8.dp),

             )
         WeekWeatherInfo(data.list)
     }
 }


