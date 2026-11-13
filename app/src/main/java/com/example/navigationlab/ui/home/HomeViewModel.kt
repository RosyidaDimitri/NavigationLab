
package com.example.navigationlab.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.navigationlab.data.repository.StudentRepository

class HomeViewModel(
    private val repository: StudentRepository = StudentRepository()
) : ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        loadStudents()
    }

    fun updateSearchQuery(query: String) {
        uiState = uiState.copy(
            searchQuery = query
        )
    }

    fun loadStudents() {
        uiState = uiState.copy(
            isLoading = true,
            error = null
        )

        repository.getStudents { result ->
            result.onSuccess { students ->
                uiState = uiState.copy(
                    students = students,
                    isLoading = false,
                    error = null
                )
            }

            result.onFailure { error ->
                uiState = uiState.copy(
                    isLoading = false,
                    error = error.message ?: "Terjadi kesalahan"
                )
            }
        }
    }

    fun searchStudents() {
        val query = uiState.searchQuery.trim()

        if (query.isEmpty()) {
            loadStudents()
            return
        }

        uiState = uiState.copy(
            isLoading = true,
            error = null
        )

        repository.searchStudents(query) { result ->
            result.onSuccess { students ->
                uiState = uiState.copy(
                    students = students,
                    isLoading = false,
                    error = null
                )
            }

            result.onFailure { error ->
                uiState = uiState.copy(
                    isLoading = false,
                    error = error.message ?: "Pencarian gagal"
                )
            }
        }
    }
}