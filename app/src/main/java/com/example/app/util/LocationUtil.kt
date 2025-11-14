package com.example.app.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

@SuppressLint("MissingPermission")
fun getUserAddress(
    context: Context,
    onSuccess: (String) -> Unit,
    onError: (String) -> Unit
) {
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
    ) {
        onError("No se han otorgado los permisos de ubicación")
        return
    }

    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val direcciones = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val direccion = direcciones?.firstOrNull()?.getAddressLine(0)
                if (direccion != null) {
                    onSuccess(direccion)
                } else {
                    onError("No se pudo obtener la dirección exacta")
                }
            } catch (e: Exception) {
                onError("Error al obtener dirección: ${e.message}")
            }
        } else {
            onError("No se pudo obtener la ubicación actual")
        }
    }.addOnFailureListener {
        onError("Error al obtener ubicación: ${it.message}")
    }
}