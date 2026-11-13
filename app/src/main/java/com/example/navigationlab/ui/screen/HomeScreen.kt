package com.example.navigationlab.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.navigationlab.ui.home.HomeUiState

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onOpenDetail: (Int) -> Unit,
    onOpenProfile: () -> Unit,
    onOpenAbout: () -> Unit,
    onRetry: () -> Unit,
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Daftar Mahasiswa",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Cari profil") },
            placeholder = { Text("Masukkan nama atau email") },
            singleLine = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(onClick = onOpenProfile) {
                Text("Profile")
            }

            OutlinedButton(onClick = onOpenAbout) {
                Text("About")
            }

            Button(
                onClick = onRefresh,
                enabled = !uiState.isLoading
            ) {
                Text("Refresh")
            }
        }

        Text(
            text = "Hasil Mahasiswa",
            style = MaterialTheme.typography.titleMedium
        )

        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
                Text("Memuat data mahasiswa...")
            }

            uiState.error != null -> {
                Text("Terjadi kesalahan: ${uiState.error}")
                Button(onClick = onRetry) {
                    Text("Coba Lagi")
                }
            }

            uiState.students.isEmpty() -> {
                Text("Mahasiswa tidak ditemukan.")
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 450.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.students) { student ->
                        Button(
                            onClick = { onOpenDetail(student.id) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("ID: ${student.id} - ${student.name}")
                        }
                    }
                }
            }
        }
    }
}