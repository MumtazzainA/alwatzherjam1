package com.example.boleh.presentation

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TimePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.lazy.LazyColumn
import com.example.boleh.AlarmReceiver
import com.example.boleh.ExerciseAlarmReceiver
import java.util.*

class AlarmSettingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumn {
                item {
                    AlarmSettingPage()
                }
                item {
                    ExerciseAlarmSettingPage()
                }
            }
        }
    }
}

@Composable
fun AlarmSettingPage() {
    var selectedTime by remember { mutableStateOf(Date()) }
    var isAlarmEnabled by remember { mutableStateOf(false) }

    val alarmManager = LocalContext.current.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(LocalContext.current, AlarmReceiver::class.java)
    val requestCode = 0
    val pendingIntent = PendingIntent.getBroadcast(
        LocalContext.current,
        requestCode,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = "Set Alarm Makan: ",
                fontSize = 18.sp,
                color = MaterialTheme.colors.secondary
            )
            Switch(
                checked = isAlarmEnabled,
                onCheckedChange = {
                    isAlarmEnabled = it
                    if (it) {
                        // Schedule the alarm
                        alarmManager.set(
                            AlarmManager.RTC_WAKEUP,
                            selectedTime.time,
                            pendingIntent
                        )
                    } else {
                        // Cancel the alarm
                        alarmManager.cancel(pendingIntent)
                    }
                }
            )
        }
        if (isAlarmEnabled) {
            AlarmTimePicker(
                context = LocalContext.current,
                initialTime = selectedTime,
                onTimeSelected = { selectedTime = it }
            )
        }
        Spacer(modifier = Modifier.height(1.dp))
    }
}
@Composable
fun AlarmTimePicker(
    context: Context,
    initialTime: Date,
    onTimeSelected: (Date) -> Unit
) {
    var selectedTime by remember { mutableStateOf(initialTime) }

    // Initialize the calendar with the initial time
    val calendar = Calendar.getInstance()
    calendar.time = selectedTime

    DisposableEffect(Unit) {
        onDispose {
            // Save the selected time when the composable is disposed
            onTimeSelected(selectedTime)
        }
    }

    // Show the CustomTimePickerDialog when the composable is first displayed
    LaunchedEffect(Unit) {
        val timePickerDialog = CustomTimePickerDialog(
            context,
            { _: TimePicker, hourOfDay: Int, minute: Int ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                selectedTime = calendar.time
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE)
        )
        timePickerDialog.show()
    }
}
class CustomTimePickerDialog(
    context: Context,
    private val listener: TimePickerDialog.OnTimeSetListener,
    hourOfDay: Int,
    minute: Int
) : TimePickerDialog(
    context,
    { view, hourOfDay, minute -> listener.onTimeSet(view, hourOfDay, minute) },
    hourOfDay,
    minute,
    false
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use the default device locale to display AM and PM labels in the desired language (English)
        val currentLocale = context.resources.configuration.locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.context.createConfigurationContext(context.resources.configuration)
                .resources
                .configuration.setLocale(currentLocale)
        } else {
            @Suppress("DEPRECATION")
            this.context.resources.configuration.locale = currentLocale
        }
    }
}
@Composable
fun ExerciseAlarmSettingPage() {
    var selectedTime by remember { mutableStateOf(Date()) }
    var isAlarmEnabled by remember { mutableStateOf(false) }

    val exerciseAlarmManager = LocalContext.current.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val exerciseIntent = Intent(LocalContext.current, ExerciseAlarmReceiver::class.java)
    val exerciseRequestCode = 1
    val exercisePendingIntent = PendingIntent.getBroadcast(
        LocalContext.current,
        exerciseRequestCode,
        exerciseIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = "Set Exercise Alarm: ",
                fontSize = 18.sp,
                color = MaterialTheme.colors.secondary
            )
            Switch(
                checked = isAlarmEnabled,
                onCheckedChange = {
                    isAlarmEnabled = it
                    if (it) {
                        // Schedule the exercise alarm
                        exerciseAlarmManager.set(
                            AlarmManager.RTC_WAKEUP,
                            selectedTime.time,
                            exercisePendingIntent
                        )
                    } else {
                        // Cancel the exercise alarm
                        exerciseAlarmManager.cancel(exercisePendingIntent)
                    }
                }
            )
        }
        if (isAlarmEnabled) {
            ExerciseAlarmTimePicker(
                context = LocalContext.current,
                initialTime = selectedTime,
                onTimeSelected = { selectedTime = it }
            )
        }
        Spacer(modifier = Modifier.height(1.dp))
    }
}
@Composable
fun ExerciseAlarmTimePicker(
    context: Context,
    initialTime: Date,
    onTimeSelected: (Date) -> Unit
) {
    var selectedTime by remember { mutableStateOf(initialTime) }

    // Initialize the calendar with the initial time
    val calendar = Calendar.getInstance()
    calendar.time = selectedTime

    DisposableEffect(Unit) {
        onDispose {
            // Save the selected time when the composable is disposed
            onTimeSelected(selectedTime)
        }
    }

    // Show the CustomTimePickerDialog when the composable is first displayed
    LaunchedEffect(Unit) {
        val timePickerDialog = CustomTimePickerDialog(
            context,
            { _: TimePicker, hourOfDay: Int, minute: Int ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                selectedTime = calendar.time
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE)
        )
        timePickerDialog.show()
    }
}


