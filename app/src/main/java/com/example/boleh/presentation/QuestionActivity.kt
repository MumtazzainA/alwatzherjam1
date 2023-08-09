package com.example.boleh.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.boleh.presentation.theme.BolehTheme


class QuestionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BolehTheme {
                QuestionPage()
            }
        }
    }
}

@Composable
fun QuestionPage() {
    // Sample questions and answers
    val questionsAndAnswers = listOf(
        "Berapa umur ku?" to "Umurku 47 tahun",
        "dimana rumah ku?" to "Jln panjaitandijahit"
    )

    // Create a list to track the visibility of the dropdown for each question
    val isDropdownExpandedList = remember { MutableList(questionsAndAnswers.size) { mutableStateOf(false) } }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp)
    ) {
        items(questionsAndAnswers.size) { index ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(1.dp, Color.White),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(8.dp) // Add padding to the entire item
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isDropdownExpandedList[index].value = !isDropdownExpandedList[index].value },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = questionsAndAnswers[index].first,
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.padding(16.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Icon",
                        tint = MaterialTheme.colors.secondary,
                        modifier = Modifier.padding(8.dp).rotate(if (isDropdownExpandedList[index].value) 180f else 0f)
                    )
                }

                if (isDropdownExpandedList[index].value) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp) // Add padding to the answer container
                            .background(Color.White) // Optional: Add background color to the answer container
                    ) {
                        Text(
                            text = questionsAndAnswers[index].second,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(1.dp))
        }
    }
}








