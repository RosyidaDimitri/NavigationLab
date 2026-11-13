
package com.example.navigationlab.data.source

import com.example.navigationlab.data.model.Student
import com.example.navigationlab.data.remote.RetrofitClient
import com.example.navigationlab.data.remote.dto.UserDto
import com.example.navigationlab.data.remote.dto.UserResponse
import com.example.navigationlab.data.remote.dto.toStudent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentRemoteDataSource {

    private val apiService = RetrofitClient.instance

    fun getStudents(
        onResult: (Result<List<Student>>) -> Unit
    ) {
        apiService.getUsers().enqueue(
            object : Callback<UserResponse> {

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        val students = response.body()
                            ?.users
                            ?.map { it.toStudent() }
                            ?: emptyList()

                        onResult(Result.success(students))
                    } else {
                        onResult(
                            Result.failure(
                                Exception("Gagal mengambil data mahasiswa")
                            )
                        )
                    }
                }

                override fun onFailure(
                    call: Call<UserResponse>,
                    t: Throwable
                ) {
                    onResult(Result.failure(t))
                }
            }
        )
    }

    fun getStudent(
        id: Int,
        onResult: (Result<Student>) -> Unit
    ) {
        apiService.getUser(id).enqueue(
            object : Callback<UserDto> {

                override fun onResponse(
                    call: Call<UserDto>,
                    response: Response<UserDto>
                ) {
                    if (response.isSuccessful) {
                        val student = response.body()?.toStudent()

                        if (student != null) {
                            onResult(Result.success(student))
                        } else {
                            onResult(
                                Result.failure(
                                    Exception("Data mahasiswa kosong")
                                )
                            )
                        }
                    } else {
                        onResult(
                            Result.failure(
                                Exception("Gagal mengambil detail mahasiswa")
                            )
                        )
                    }
                }

                override fun onFailure(
                    call: Call<UserDto>,
                    t: Throwable
                ) {
                    onResult(Result.failure(t))
                }
            }
        )
    }

    fun searchStudents(
        query: String,
        onResult: (Result<List<Student>>) -> Unit
    ) {
        apiService.searchUsers(query).enqueue(
            object : Callback<UserResponse> {

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        val students = response.body()
                            ?.users
                            ?.map { it.toStudent() }
                            ?: emptyList()

                        onResult(Result.success(students))
                    } else {
                        onResult(
                            Result.failure(
                                Exception("Pencarian mahasiswa gagal")
                            )
                        )
                    }
                }

                override fun onFailure(
                    call: Call<UserResponse>,
                    t: Throwable
                ) {
                    onResult(Result.failure(t))
                }
            }
        )
    }
}