package com.example.boleh.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Text
import com.example.boleh.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConnectPageContent()
        }
    }
}

@Composable
fun ConnectPageContent() {
    var isLoading by remember { mutableStateOf(false) }
    var serverAddress by remember { mutableStateOf("") }
    var connectingSuccess by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        // Handle the result here if needed
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Connect",
                style = androidx.compose.material.Typography().h5,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (isLoading) {
                LoadingIndicator() // Display loading indicator while isLoading is true
            }

            // Tampilkan pesan "Connecting Success" jika connectingSuccess true
            if (connectingSuccess) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Connecting Success",
                    color = Color.White
                )
                // Trigger navigation to MenuActivity after 1 second
                LaunchedEffect(connectingSuccess) {
                    delay(1000) // Wait for 1 second
                    navigateToMenuActivity(context)// Trigger navigation
                }
            }

            Spacer(modifier = Modifier.height(130.dp))

            ConnectButton(
                text = "Connect",
                onClick = {
                    isLoading = true
                    launchSimulateConnect(serverAddress) { // Call the suspend function from the lambda
                        isLoading = false
                        connectingSuccess = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Tampilkan loading ketika isLoading true

        }
    }
}

@Composable
fun ConnectButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.White // Set custom text color
        ), // Use CircleShape to create a circular button
        modifier = modifier
            .size(20.dp) // Set the size to create a circular button with a diameter of 64.dp
            .border(
                width = 1.dp,
                color = Color.White,
                shape = CircleShape, // Use CircleShape for border
            ) // Set the border (stroke)
    ) {
        Text(text)
    }
}

// Function to launch the com.example.boleh.presentation.simulateConnect function in a coroutine
private fun launchSimulateConnect(serverAddress: String, onSimulateComplete: () -> Unit) {
    // Launch a coroutine
    // You can use the GlobalScope.launch or define your own coroutine scope.
    // For simplicity, we'll use GlobalScope.launch in this example.
    GlobalScope.launch {
        simulateConnect(serverAddress) {
            onSimulateComplete()
        }
    }
}

// Simulate connection to the server
private suspend fun simulateConnect(serverAddress: String, onSimulateComplete: () -> Unit) {
    // Simulate connection here (e.g., delay, network call, etc.)
    delay(2000) // Simulate 2 seconds loading time
    onSimulateComplete() // Indicate that the loading is complete
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

fun navigateToMenuActivity(context: android.content.Context) {
    val intent = Intent(context, ConnectActivity::class.java)
    context.startActivity(intent)
}
