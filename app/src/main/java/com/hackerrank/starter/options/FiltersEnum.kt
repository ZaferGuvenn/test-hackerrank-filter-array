package com.hackerrank.starter.options

enum class FiltersEnum {
    ALL, NAME, PART_NAME, SALARY_LESS, SALARY_MORE;

    var employeeName: String = ""
    var minSalary: Float = 0f
    var maxSalary: Float = 0f

}