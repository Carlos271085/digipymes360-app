package com.example.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.app.ui.screens.BiometricScreen
import com.example.app.ui.theme.AppTheme

import androidx.fragment.app.FragmentActivity



class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    BiometricScreen(this) // 👈 Muestra la pantalla biométrica
                }
            }
        }
    }
}

