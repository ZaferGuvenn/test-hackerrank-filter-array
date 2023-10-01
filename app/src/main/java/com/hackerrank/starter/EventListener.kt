package com.hackerrank.starter

import com.hackerrank.starter.options.FiltersEnum

interface EventListener {
    fun deleteEmployee(id: Int)
    fun chooseOption(filtersEnum: FiltersEnum?)
}
