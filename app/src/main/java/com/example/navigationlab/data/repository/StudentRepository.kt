
package com.example.navigationlab.data.repository

import com.example.navigationlab.data.model.Student
import com.example.navigationlab.data.source.StudentRemoteDataSource

class StudentRepository(
    private val remoteDataSource: StudentRemoteDataSource =
        StudentRemoteDataSource()
) {

    fun getStudents(
        onResult: (Result<List<Student>>) -> Unit
    ) {
        remoteDataSource.getStudents(onResult)
    }

    fun getStudent(
        id: Int,
        onResult: (Result<Student>) -> Unit
    ) {
        remoteDataSource.getStudent(id, onResult)
    }

    fun searchStudents(
        query: String,
        onResult: (Result<List<Student>>) -> Unit
    ) {
        remoteDataSource.searchStudents(query, onResult)
    }
}