package com.example.Сalculator.data

import com.example.Сalculator.R
import com.example.Сalculator.model.ButtonData

class DataSource {
    fun loadData(): List<ButtonData> {
        return listOf<ButtonData>(
                ButtonData(R.string.ac),
                ButtonData(R.string.ch),
                ButtonData(R.string.percentage),
                ButtonData(R.string.division),
                ButtonData(R.string._7),
                ButtonData(R.string._8),
                ButtonData(R.string._9),
                ButtonData(R.string.multiplication),
                ButtonData(R.string._4),
                ButtonData(R.string._5),
                ButtonData(R.string._6),
                ButtonData(R.string.subtraction),
                ButtonData(R.string._1),
                ButtonData(R.string._2),
                ButtonData(R.string._3),
                ButtonData(R.string.addition),
                ButtonData(R.string._0),
                ButtonData(R.string.empty),
                ButtonData(R.string.comma),
                ButtonData(R.string.equality),
        )
    }
}