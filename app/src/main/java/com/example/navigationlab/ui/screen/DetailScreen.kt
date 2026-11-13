
package com.example.navigationlab.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.navigationlab.ui.detail.DetailUiState

@Composable
fun DetailScreen(
    uiState: DetailUiState,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Detail Student",
            style = MaterialTheme.typography.headlineMedium
        )

        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
                Text("Memuat detail mahasiswa...")
            }

            uiState.error != null -> {
                Text("Terjadi kesalahan: ${uiState.error}")
            }

            uiState.student != null -> {
                val student = uiState.student

                Text("ID: ${student.id}")
                Text("Nama: ${student.name}")
                Text("Email: ${student.email}")
                Text("Universitas: ${student.university}")
            }

            else -> {
                Text("Data mahasiswa tidak ditemukan.")
            }
        }

        Button(onClick = onBack) {
            Text("Kembali")
        }
    }
}