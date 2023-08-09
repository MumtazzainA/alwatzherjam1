//package com.example.boleh.presentation
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalLifecycleOwner
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.LifecycleEventObserver
//
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.GoogleMapOptions
//import com.google.android.gms.maps.MapView
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//
//class MapActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MapContent()
//        }
//    }
//}
//
//@Composable
//fun MapContent() {
//    val context = LocalContext.current
//    var mapView: MapView? by remember { mutableStateOf(null) }
//
//    // Create the MapView and set options
//    DisposableEffect(Unit) {
//        mapView = MapView(
//            context,
//            GoogleMapOptions().apply {
//                // Set additional GoogleMapOptions if needed
//                // For example, you can enable or disable certain map features here.
//            }
//        )
//
//        onDispose {
//            // Clean up the MapView when it's no longer needed
//            mapView?.onDestroy()
//        }
//    }
//
//    val lifecycle = LocalLifecycleOwner.current.lifecycle
//
//    // Observe the lifecycle of the map view and handle its lifecycle
//    DisposableEffect(lifecycle) {
//        val observer = LifecycleEventObserver { _, event ->
//            when (event) {
//                Lifecycle.Event.ON_CREATE -> mapView?.onCreate(Bundle())
//                Lifecycle.Event.ON_START -> mapView?.onStart()
//                Lifecycle.Event.ON_RESUME -> mapView?.onResume()
//                Lifecycle.Event.ON_PAUSE -> mapView?.onPause()
//                Lifecycle.Event.ON_STOP -> mapView?.onStop()
//                Lifecycle.Event.ON_DESTROY -> mapView?.onDestroy()
//                else -> Unit
//            }
//        }
//
//        lifecycle.addObserver(observer)
//
//        onDispose {
//            // Remove the observer when the composable is disposed
//            lifecycle.removeObserver(observer)
//        }
//    }
//
//    mapView?.let { map ->
//        map.getMapAsync { googleMap ->
//            // Set the camera position to a specific location and add a marker
//            val location = LatLng(-6.2146, 106.8451) // Replace with your desired coordinates
//            val markerOptions = MarkerOptions().position(location).title("Your Location")
//            googleMap.addMarker(markerOptions)
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
//        }
//    }
//}
//
//@Preview
//@Composable
//fun MapContentPreview() {
//    MapContent()
//}
