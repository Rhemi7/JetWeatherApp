package com.example.jetweatherforecast.screens.settings

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.model.Unit
import com.example.jetweatherforecast.widget.WeatherAppBar
import javax.annotation.RegEx

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController, settingsViewModel: SettingsViewModel = hiltViewModel()) {

    var unitToggleState by remember {
        mutableStateOf(false)
    }

    val measurementUnits = listOf( "Metric (C)", "Imperial (F)")

    val choiceFromDB = settingsViewModel.unitList.collectAsState().value

    val defaultChoice = if (choiceFromDB.isEmpty()) measurementUnits[0] else choiceFromDB[0].unit

    var choiceState by remember {
        mutableStateOf(defaultChoice)
    }
    
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Settings",
            icon = Icons.Default.ArrowBack,
            isMainScreen = false,
            navController = navController
        ) {
            navController.popBackStack()
        }
    }) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Change Unit of Measurement",
                    modifier = Modifier.padding(15.dp))

                IconToggleButton(
                    checked = !unitToggleState,
                    onCheckedChange = {
                        unitToggleState = !it
                        if (unitToggleState) {
                            choiceState = "Imperial (F)"
                        } else {
                            choiceState = "Metric (C)"
                        }
                        Log.d("ChoiceState", "SettingsScreen: ${choiceState}")
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(
                            Color.Magenta.copy(alpha = 0.4f)
                        )
                ) {
                    Text(text = if (unitToggleState) "Fahrenheit ºF" else "Celsius ºC")
                }
                
                Button(onClick = {
                                 settingsViewModel.deleteAllUnit().run {
                                     Log.d("ChoiceStateSave", "SettingsScreen: ${choiceState}")

                                     settingsViewModel.insertUnit(Unit(choiceState))

                                 }

                }, modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.CenterHorizontally), shape = RoundedCornerShape(34.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xffEFBE42))) {
                    Text(
                        text = "Save",
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 17.sp
                    )

                }
            }
            
        }
    }

}