package com.yao.shizhong

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yao.shizhong.ui.theme.YaoShizhongTheme
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YaoShizhongTheme {
                ClockScreen()
            }
        }
    }
}

@Composable
fun ClockScreen() {
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }
    
    // Update time every second
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000L)
            currentTime = System.currentTimeMillis()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TimeDisplay(currentTime)
            Spacer(modifier = Modifier.height(16.dp))
            DateDisplay(currentTime)
        }
    }
}

@Composable
fun TimeDisplay(timeMillis: Long) {
    val dateFormat = remember { SimpleDateFormat("HH:mm:ss", Locale.CHINA) }
    dateFormat.timeZone = TimeZone.getTimeZone("Asia/Shanghai")
    
    Text(
        text = dateFormat.format(Date(timeMillis)),
        fontSize = 72.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun DateDisplay(timeMillis: Long) {
    val dateFormat = remember { SimpleDateFormat("yyyy年MM月dd日 EEEE", Locale.CHINA) }
    dateFormat.timeZone = TimeZone.getTimeZone("Asia/Shanghai")
    
    Text(
        text = dateFormat.format(Date(timeMillis)),
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
    )
}
