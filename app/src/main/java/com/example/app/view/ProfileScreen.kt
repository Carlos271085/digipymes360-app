package com.example.app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.R
import com.example.app.viewmodel.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController,user: String, email: String) {
    Text("Bienvenido a la tienda digital", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.height(16.dp))

    Image(
        painter = painterResource(id = R.drawable.gato_naranja),
        contentDescription = "Logo Pymes 360",
        modifier = Modifier
            .size(256.dp)
            .clip(CircleShape)
        //.align(Alignment.CenterHorizontally)


    )

    Text("Usuario: $user", style = MaterialTheme.typography.bodyLarge)
    Text("Email: $email", style = MaterialTheme.typography.bodyLarge)
}