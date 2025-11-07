package com.example.week9_98733

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.week9_98733.ui.theme.OnBackgroundItemText
import com.example.week9_98733.ui.theme.OnBackgroundTitleText
import com.example.week9_98733.ui.theme.PrimaryTextButton
import com.example.week9_98733.ui.theme.Week9_98733Theme
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@JsonClass(generateAdapter = true)
data class Student(
    var name: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week9_98733Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    App(navController = navController)
                }
            }
        }
    }
}

@Composable
fun App(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            Home { listData ->
                navController.navigate("resultContent/?listData=$listData")
            }
        }
        composable(
            "resultContent/?listData={listData}",
            arguments = listOf(navArgument("listData") {
                type = NavType.StringType
            })
        ) {
            ResultContent(it.arguments?.getString("listData").orEmpty())
        }
    }
}

@Composable
fun Home(
    navigateFromHomeToResult: (String) -> Unit
) {
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }
    val inputField = remember { mutableStateOf(Student("")) }
    val isButtonEnabled = inputField.value.name.isNotBlank()

    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val listType = Types.newParameterizedType(List::class.java, Student::class.java)
    val adapter = moshi.adapter<List<Student>>(listType)

    HomeContent(
        listData = listData,
        inputField = inputField.value,
        onInputValueChange = { input -> inputField.value = inputField.value.copy(name = input) },
        onButtonClick = {
            if (isButtonEnabled) {
                listData.add(inputField.value)
                inputField.value = Student("")
            }
        },
        isButtonEnabled = isButtonEnabled,
        navigateFromHomeToResult = {
            val json = adapter.toJson(listData.toList())
            val encodedJson = URLEncoder.encode(json, StandardCharsets.UTF_8.name())
            navigateFromHomeToResult(encodedJson)
        }
    )
}

@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    isButtonEnabled: Boolean,
    navigateFromHomeToResult: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OnBackgroundTitleText(
                    text = stringResource(
                        id = R.string.enter_item
                    )
                )

                TextField(
                    value = inputField.name,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = {
                        onInputValueChange(it)
                    }
                )

                Row {
                    PrimaryTextButton(
                        text = stringResource(id = R.string.button_click),
                        enabled = isButtonEnabled
                    ) {
                        onButtonClick()
                    }
                    PrimaryTextButton(
                        text = stringResource(id = R.string.button_navigate)
                    ) {
                        navigateFromHomeToResult()
                    }
                }
            }
        }
        items(listData) { item ->
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OnBackgroundItemText(text = item.name)
            }
        }
    }
}

@Composable
fun ResultContent(listData: String) {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val listType = Types.newParameterizedType(List::class.java, Student::class.java)
    val adapter = moshi.adapter<List<Student>>(listType)
    val studentList = if (listData.isNotBlank()) adapter.fromJson(listData) else emptyList()

    LazyColumn(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(studentList.orEmpty()) { student ->
            OnBackgroundItemText(text = student.name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    Week9_98733Theme {
        Home {}
    }
}
