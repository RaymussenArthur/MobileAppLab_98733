package com.example.week10_98733.viewmodels

class TotalViewModel: ViewModel() {
    var total: Int = 0
    fun incrementTotal(): Int {
        total++
        return total
    }
}