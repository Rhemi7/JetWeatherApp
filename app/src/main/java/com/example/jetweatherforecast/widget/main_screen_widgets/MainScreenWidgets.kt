package com.example.jetweatherforecast.widget.main_screen_widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.model.WeatherItem
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDateTime
import com.example.jetweatherforecast.utils.formatDecimals

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
                            Text(text = formatDecimals(item.temp.max) +"º", color = Color(0xFF6439B3), fontWeight = FontWeight.Bold)
                            Text(text = formatDecimals(item.temp.min) +"º", color = Color.LightGray, fontWeight = FontWeight.Bold)

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
fun HumidityWindPressureRow(weather: WeatherItem, isImperial: Boolean) {
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
            Text(text = "${formatDecimals(weather.speed)} " + if (isImperial) "mph" else "m/s",
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