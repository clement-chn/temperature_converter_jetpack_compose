package com.example.temperature_converter_jetpack_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.temperature_converter_jetpack_compose.ui.theme.Temperature_converter_jetpack_composeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Temperature_converter_jetpack_composeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MainContent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var imageResource by remember { mutableStateOf(R.drawable.hot) }

    val handleClick = {
        val temperatureCelsius = text.toDoubleOrNull()
        if (temperatureCelsius != null) {
            val temperatureFahrenheit = (temperatureCelsius * 9 / 5) + 32
            result = "$temperatureFahrenheit"

            imageResource = if (temperatureCelsius < 14) {
                R.drawable.cold
            } else {
                R.drawable.hot
            }
        } else {
            result = "Entrée invalide"
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        TempImage(imageResource = imageResource)

        Spacer(modifier = Modifier.height(2.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TempInput(onValueChange = { text = it }, value = text)
            TempBtn(onClick = handleClick)
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextResult()
            Result(temperatureFahrenheit = result)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun TempImage(imageResource: Int) {
    Image(
        painter = painterResource(id = imageResource),
        contentDescription = "Icone de la température",
        modifier = Modifier.size(86.dp)
    )
}

@Composable
fun TempInput(onValueChange: (String) -> Unit, value: String) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Tapez une température") }
    )
}

@Composable
fun TempBtn(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = "Convertir en Fahrenheit")
    }
}

@Composable
fun TextResult() {
    Text(
        text = "Résultat :",
        style = TextStyle(fontSize = 24.sp)
    )
}

@Composable
fun Result(temperatureFahrenheit: String) {
    Text(
        text = "$temperatureFahrenheit °F",
        style = TextStyle(fontSize = 40.sp)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Temperature_converter_jetpack_composeTheme {
        MainContent(modifier = Modifier)
    }
}
