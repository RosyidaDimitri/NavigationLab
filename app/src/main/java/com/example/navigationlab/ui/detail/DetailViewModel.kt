
package com.example.navigationlab.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.navigationlab.data.repository.StudentRepository

class DetailViewModel(
    private val studentId: Int,
    private val repository: StudentRepository = StudentRepository()
) : ViewModel() {

    var uiState by mutableStateOf(DetailUiState())
        private set

    init {
        loadStudent()
    }

    fun loadStudent() {
        uiState = uiState.copy(
            isLoading = true,
            error = null
        )

        repository.getStudent(studentId) { result ->
            result.onSuccess { student ->
                uiState = DetailUiState(
                    student = student,
                    isLoading = false
                )
            }

            result.onFailure { error ->
                uiState = DetailUiState(
                    isLoading = false,
                    error = error.message ?: "Terjadi kesalahan"
                )
            }
        }
    }
}