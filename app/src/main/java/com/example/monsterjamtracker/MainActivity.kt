package com.example.monsterjamtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.monsterjamtracker.ui.theme.MonsterJamTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
            setContent {
                MonsterJamTrackerTheme {
                    MonsterJamScreen()
                        }
                    }
            }
        }



@Preview(showBackground = true)
@Composable
fun MonsterJamPreview() {
    MonsterJamTrackerTheme {
        MonsterJamScreen()
    }
}