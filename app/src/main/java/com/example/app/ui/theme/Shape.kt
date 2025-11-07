package com.example.app.ui.theme
//Shapes es un objeto que se pasa al tema Material para definir qué tan redondeados son los bordes por defecto.

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// --- Definición de formas base para tu tema ---
val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)
