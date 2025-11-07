package com.example.week9_98733

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.week9_98733.ui.theme.Week9_98733Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Here, we use setContent instead of setContentView
        setContent {
            //Here, we wrap our content with the theme
            //You can check out the Week9_98733Theme inside Theme.kt
            Week9_98733Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //We use Modifier.fillMaxSize() to make the surface fill the whole screen
                    modifier = Modifier.fillMaxSize(),
                    //We use MaterialTheme.colorScheme.background to get the background color
                    //and set it as the color of the surface
                    color = MaterialTheme.colorScheme.background
                ) {
                    val list = listOf("Tanu", "Tina", "Tono")
                    //Here, we call the Home composable
                    Home(list)
                }
            }
        }
    }
}

// @Composable is used to tell the compiler that this is a composable function
// It's a way of defining a composable
@Composable
fun Home(
    //Here, we define a parameter called items
    items: List<String>,
) {
    //Here, we use Column to display items vertically and center them.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.enter_item))

        Spacer(modifier = Modifier.height(8.dp))

        //Here, we use TextField to display a text input field
        TextField(
            //Set the value of the input field
            value = "",
            //Set what happens when the value of the input field changes
            onValueChange = {
            },
            //Set the keyboard type of the input field
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        //Here, we use Button to display a button
        //the onClick parameter is used to set what happens when the button is clicked
        Button(onClick = { }) {
            //Set the text of the button
            Text(text = stringResource(id = R.string.button_click))
        }

        Spacer(modifier = Modifier.height(16.dp))

        items.forEach { item ->
            Text(text = item, modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    Week9_98733Theme {
        Home(listOf("Tanu", "Tina", "Tono"))
    }
}
