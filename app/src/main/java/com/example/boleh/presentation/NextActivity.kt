package com.example.boleh.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.boleh.R
import com.google.android.gms.maps.MapView
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow

class NextActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NextPageContent()
        }
    }
}

@Composable
fun NextPageContent() {
    val currentTime = MutableLiveData(Date())
    val context = LocalContext.current
    var mapView: MapView? by remember { mutableStateOf(null) }
    // Start the coroutine to update the time in real-time
    LaunchedEffect(Unit) {
        updateTimeInRealTime(currentTime)
    }

    // The current time as LiveData
    val currentLiveDataTime: LiveData<Date> = currentTime

    val currentDate = remember { mutableStateOf(Date()) }
    val currentTemperature = remember { mutableStateOf(0.0) } // Placeholder for the temperature

    // Simulated temperature fetching. Replace this with real data from an API.
    LaunchedEffect(true) {
        currentDate.value = Date()
        currentTemperature.value = fetchTemperature() // Replace fetchTemperature() with API call
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Observe the LiveData to automatically recompose the UI when the time changes
        Text(
            text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(currentLiveDataTime.observeAsState().value),
            fontSize = 48.sp,
            color = MaterialTheme.colors.secondary
        )
        Spacer(modifier = Modifier.height(1.dp))
        Text(
            text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentDate.value),
            fontSize = 24.sp,
            color = MaterialTheme.colors.secondary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${currentTemperature.value.roundTo(1)}°C",
            fontSize = 24.sp,
            color = MaterialTheme.colors.secondary
        )
        Spacer(modifier = Modifier.height(45.dp))

        // Align buttons in a row
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularButton(
                image = painterResource(R.drawable.tele),
                onClick = { navigateToCallingActivity(context) },
                buttonColor = Color.Red // Set the color to red for Emergency Call button
            )
            CircularButton(
                image = painterResource(R.drawable.maps),
                onClick = { navigateToMapActivity(context) },
                buttonColor = Color.Blue // Set the color to blue for Go back button
            )
            CircularButton(
                image = painterResource(R.drawable.menu),
                onClick = { navigateBackToMenu(context) },
                buttonColor = Color.Yellow // Set the color to yellow for Menu button
            )
        }
    }
}


// Function to update the time in real-time


@Composable
fun CircularButton(
    image: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonColor: Color = Color.White // Default button color is white
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(36.dp) // Fixed size for the circular buttons
            .border(
                width = 1.dp,
                color = buttonColor, // Use the provided buttonColor for the border color
                shape = CircleShape,
            )
            .background(buttonColor)
            .clickable { onClick() }
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.size(15.dp) // Set an appropriate size for the image
        )
    }
}

// Simulated temperature fetching function. Replace this with a real API call.
fun fetchTemperature(): Double {
    return (Math.random() * 40)
}
private suspend fun updateTimeInRealTime(currentTime: MutableLiveData<Date>) {
    while (true) {
        currentTime.postValue(Date())
        delay(1000) // Delay 1 second to update the time
    }
}
private fun Double.roundTo(decimalPlaces: Int): Double {
    val factor = 10.0.pow(decimalPlaces.toDouble())
    return (this * factor).toLong() / factor
}