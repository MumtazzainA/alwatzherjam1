package com.example.boleh.presentation

import android.content.Intent
import androidx.compose.ui.graphics.Color as ComposeColor
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.example.boleh.R

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MenuPageContent()
        }
    }
}

@Composable
fun MenuPageContent() {
    // State to hold the activation status of the alarm
    var isAlarmActivated by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Set the background color to black
    ) {
        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            // Handle the result here if needed
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(26.dp)
        ) {
            item {
                CustomButton(
                    text = "Reminder",
                    onClick = {
                        // Toggle the alarm activation status
                        isAlarmActivated = !isAlarmActivated

                        // Show toast message when the alarm is activated
                        if (isAlarmActivated) {

                            val toast = Toast.makeText(context, "Waktunya minum obat", Toast.LENGTH_SHORT)

                            // Get the toast view
                            val toastView = toast.view

                            // Set background color of the toast using Color.parseColor() method
                             // This sets a shade of orange color

                            // Set text color of the toast message using Jetpack Compose's Color
                            val toastTextView = toastView?.findViewById<TextView>(android.R.id.message)
                            toastTextView?.setTextColor(androidx.compose.ui.graphics.Color.White.toArgb()) // Use toArgb() to convert ComposeColor to Android's Color

                            toast.show()
                        }
                    },
                    image = painterResource(R.drawable.alarm) // Replace with your image resource
                )
            }
            item {
                CustomButton(
                    text = "Ask Alwatzer",
                    onClick = { navigateToQuestionActivity(context) },
                    image = painterResource(R.drawable.question) // Replace with your image resource
                )
            }
        }
    }
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    image: Painter,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.White // Set custom text color
        ),
        modifier = modifier
            .fillMaxWidth() // Button fills the screen width
            .height(32.dp) // Adjust the height as needed
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(30.dp),
            ) // Set the border (stroke)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start, // Align content to the start (left)
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text, fontSize = 10.sp)
        }
    }
}

fun navigateToAlarmSetting(context: android.content.Context) {
    val intent = Intent(context, AlarmSettingActivity::class.java)
    context.startActivity(intent)
}
fun navigateToConnectActivity(context: android.content.Context) {
    val intent = Intent(context, ConnectActivity::class.java)
    context.startActivity(intent)
}
fun navigateToQuestionActivity(context: android.content.Context) {
    val intent = Intent(context, QuestionActivity::class.java)
    context.startActivity(intent)
}
fun navigateToMapActivity(context: android.content.Context) {
    val intent = Intent(context, MapActivity::class.java)
    context.startActivity(intent)
}
fun navigateToCallingActivity(context: android.content.Context) {
    val intent = Intent(context, CallingActivity::class.java)
    context.startActivity(intent)
}