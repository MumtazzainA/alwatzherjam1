package com.example.boleh.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.wear.compose.material.CircularProgressIndicator

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapContent()
        }
    }
}

@Composable
fun MapContent() {
    val context = LocalContext.current
    var mapView: MapView? by remember { mutableStateOf(null) }


    // Create the MapView and set options
    DisposableEffect(Unit) {
        mapView = MapView(
            context,
            GoogleMapOptions().apply {
                // Set additional GoogleMapOptions if needed
                // For example, you can enable or disable certain map features here.
            }
        )

        onDispose {
            // Clean up the MapView when it's no longer needed
            mapView?.onDestroy()
        }
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    // Observe the lifecycle of the map view and handle its lifecycle
    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView?.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView?.onStart()
                Lifecycle.Event.ON_RESUME -> mapView?.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView?.onPause()
                Lifecycle.Event.ON_STOP -> mapView?.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView?.onDestroy()
                else -> Unit
            }
        }

        lifecycle.addObserver(observer)

        onDispose {
            // Remove the observer when the composable is disposed
            lifecycle.removeObserver(observer)
        }
    }

    mapView?.let { map ->
        map.getMapAsync { googleMap ->
            // Set the camera position to a specific location and add a marker
            val location = LatLng(-7.2757, 112.7947) // Replace with your desired coordinates
            val markerOptions = MarkerOptions().position(location).title("Your Location")
            googleMap.addMarker(markerOptions)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        }
    }

    // Display loading indicator while MapView is being created
    if (mapView == null) {
        LoadingIndicators()
    } else {
        MapViewContainer(mapView!!)
    }
    Button(
        onClick = { navigateToPensSurabaya(mapView) },
        modifier = Modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text = "Go to Pens Surabaya")
    }
    // Add a back button to navigate back to MenuActivity
    BackButton()
}

@Composable
fun LoadingIndicators() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun MapViewContainer(mapView: MapView) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color.Black
    ) {
        AndroidView({ mapView }) { mapView ->
            // MapView is displayed here
        }
    }
}

@Composable
fun BackButton() {
    val context = LocalContext.current
    IconButton(onClick = { navigateToNextActivity(context) }) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
    }
}

fun navigateBackToMenu(context: android.content.Context) {

    val intent = Intent(context, MenuActivity::class.java)
    context.startActivity(intent)
}

@Preview
@Composable
fun MapContentPreview() {
    MapContent()
}

fun navigateToPensSurabaya(mapView: MapView?) {
    mapView?.getMapAsync { googleMap ->
        val destination = LatLng(-7.2792, 112.7877) // Pens Surabaya (ITS Stadium) coordinates

        // Get the current location (Replace this with your actual code to get the user's current location)
        val currentLocation = LatLng(-7.2757, 112.7947)

        // Move the camera to the current location and destination
        val builder = LatLngBounds.builder()
        builder.include(currentLocation)
        builder.include(destination)
        val bounds = builder.build()
        val padding = 100 // Padding in pixels to ensure both markers are visible
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        googleMap.moveCamera(cameraUpdate)

        // Add markers for the current location and the destination
        googleMap.addMarker(MarkerOptions().position(currentLocation).title("Current Location"))
        googleMap.addMarker(MarkerOptions().position(destination).title("Pens Surabaya (ITS Stadium)"))

        // Draw a polyline from the current location to the destination
        val polylineOptions = PolylineOptions()
            .add(currentLocation)
            .add(destination)
            .color(Color.Blue.toArgb())// Set the color of the polyline (you can choose any color you want)
            .width(8f) // Set the width of the polyline
        googleMap.addPolyline(polylineOptions)
    }
}


