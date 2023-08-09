package com.example.boleh.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.CircularProgressIndicator
import com.example.boleh.R

class CallingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CallingContent()

        }
    }
}

@Composable
fun CallingContent() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Ringing", // Gunakan string literal langsung
            style = MaterialTheme.typography.h5.copy(color = Color.White)
        )

        // Gambar telepon (ganti dengan gambar telepon yang sesuai)
        val telephoneIcon: Painter = painterResource(R.drawable.tele)
        Image(
            painter = telephoneIcon,
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )

        // Button lingkaran berwarna merah dengan icon telephone
        Surface(
            modifier = Modifier
                .size(50.dp)
                .padding(8.dp),
            shape = CircleShape,
            color = Color.Red,
            contentColor = Color.White,
            elevation = 4.dp
        ) {
            IconButton(
                onClick = { navigateToNextActivity(context) }
            ) {
                Icon(Icons.Default.Phone, contentDescription = "Phone Icon")
            }
        }
    }
}
fun navigateToNextActivity(context: android.content.Context) {
    val intent = Intent(context, NextActivity::class.java)
    context.startActivity(intent)
}

