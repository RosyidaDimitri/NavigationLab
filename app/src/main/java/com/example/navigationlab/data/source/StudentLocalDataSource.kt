
package com.example.navigationlab.data.source

import com.example.navigationlab.data.model.Student

class StudentLocalDataSource {

    private val students = listOf(
        Student(
            id = 2026,
            name = "Alya Putri",
            email = "alya@example.com",
            university = "Universitas Brawijaya"
        ),
        Student(
            id = 2027,
            name = "Bima Pratama",
            email = "bima@example.com",
            university = "Universitas Indonesia"
        ),
        Student(
            id = 2028,
            name = "Citra Lestari",
            email = "citra@example.com",
            university = "Universitas Gadjah Mada"
        ),
        Student(
            id = 2029,
            name = "Dinda Maharani",
            email = "dinda@example.com",
            university = "Universitas Airlangga"
        ),
        Student(
            id = 2030,
            name = "Eka Saputra",
            email = "eka@example.com",
            university = "Institut Teknologi Bandung"
        )
    )

    fun getStudentById(id: Int): Student? =
        students.find { it.id == id }

    fun getStudents(): List<Student> =
        students
}