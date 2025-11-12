package com.example.app.ui.theme

import android.app.Activity
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = OrangePrimary,         // Naranja principal
    onPrimary = Color.White,          // Texto sobre color principal
    secondary = BlueDark,             // Azul petróleo (secundario)
    onSecondary = Color.White,
    tertiary = BlueInfo,              // Azul informativo
    background = LightBackground,     // Fondo general claro
    onBackground = TextPrimary,       // Texto principal
    surface = Color.White,            // Tarjetas, contenedores
    onSurface = TextPrimary,          // Texto sobre tarjetas
    error = RedError,                 // Rojo de error
    onError = Color.White
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    // --- Fijamos el esquema claro como único ---
    val colorScheme = LightColorScheme

    // --- Ajuste del color de la barra de estado ---
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = true
            window.statusBarColor = colorScheme.primary.toArgb()
        }
    }

    // --- Aplicamos la paleta global ---
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // puedes definir o mantener tus fuentes
        shapes = Shapes,
        content = content
    )
}
