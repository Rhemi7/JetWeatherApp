 package com.example.jetweatherforecast.screens.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.model.WeatherItem
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDateTime
import com.example.jetweatherforecast.utils.formatDecimals
import com.example.jetweatherforecast.widget.WeatherAppBar

 @Composable
 fun WeatherMainScreen(
     navController: NavController,
//     mainViewModel: MainViewModel = hiltViewModel()
 ) {
     val mainViewModel = hiltViewModel<MainViewModel>()

     val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(initialValue = DataOrException(loading = true)) {
         value = mainViewModel.getWeatherData(city = "Seattle")
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

 @Composable
 fun WeekWeatherInfo(weatherList: List<WeatherItem>) {
     Surface(modifier = Modifier
         .fillMaxSize(), color = Color(0xFFEEF0F1)
     ){
        LazyColumn{
            items(weatherList) { item ->
                Surface(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            bottomStart = 30.dp,
                            topStart = 30.dp,
                            bottomEnd = 30.dp
                        )
                    ),
                    color = Color.White) {
                    Row(
                        modifier = Modifier
                            .padding(
                                horizontal = 5.dp,
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Text(text = formatDate(item.dt).split(",")[0])
                        WeatherStateImage(icon = item.weather[0].icon)
                        Surface(modifier = Modifier
                                .clip(RoundedCornerShape(corner = CornerSize(10.dp))),
                            color = Color(0xffffc400)
                        ) {
                            Text(text = item.weather[0].description, modifier = Modifier.padding(vertical = 2.dp, horizontal = 2.dp))
                        }
                        Row() {
                            Text(text = formatDecimals(item.temp.max)+"º", color = Color(0xFF6439B3), fontWeight = FontWeight.Bold)
                            Text(text = formatDecimals(item.temp.min)+"º", color = Color.LightGray, fontWeight = FontWeight.Bold)

                        }

                    }
                }
            }
        }
     }
 }

 @Composable
 fun SunSetRise(weather: WeatherItem) {
     Row(modifier = Modifier
         .padding(12.dp)
         .fillMaxWidth(),
         verticalAlignment = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.SpaceBetween
     ) {
         Row {
             Icon(
                 painter = painterResource(id = R.drawable.sunrise),
                 contentDescription = "SunRise Icon",
                 modifier = Modifier.size(30.dp)
             )
             Text(text = formatDateTime(weather.sunrise),
                 style = MaterialTheme.typography.caption)

         }
         Row {

             Text(text = formatDateTime(weather.sunset),
                 style = MaterialTheme.typography.caption)
             Icon(
                 painter = painterResource(id = R.drawable.sunset),
                 contentDescription = "SunSet Icon",
                 modifier = Modifier.size(30.dp)
             )

         }
     }
 }

 @Composable
 fun HumidityWindPressureRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "Humidity icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.humidity} %",
                style = MaterialTheme.typography.caption)

        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "Pressure icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.pressure} psi",
                style = MaterialTheme.typography.caption)

        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "Wind icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.humidity} mph",
                style = MaterialTheme.typography.caption)

        }
    }

 }


 @Composable
 fun WeatherStateImage(icon: String) {
     val imageUrl = "https://openweathermap.org/img/wn/${icon}.png"

     Image(
         painter = rememberAsyncImagePainter(imageUrl),
         contentDescription = "Icon Image",
         modifier = Modifier.size(
             80.dp
         )
     )
 }
