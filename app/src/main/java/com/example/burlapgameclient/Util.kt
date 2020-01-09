package com.example.burlapgameclient

import api.exception.MinefieldConst
import api.model.FieldType

fun generateEmptyMinefield(): List<List<FieldType>> {
    val minefield: MutableList<List<FieldType>> =
        ArrayList()
    for (i in 0 until MinefieldConst.MINEFIELD_HEIGHT) {
        val row: MutableList<FieldType> = ArrayList()
        for (j in 0 until MinefieldConst.MINEFIELD_WIDTH) {
            row.add(FieldType())
        }
        minefield.add(row)
    }
    return minefield
}
//object MinesweeperUtils {
//    fun generateEmptyMinefield(): List<List<FieldType>> {
//        val minefield: MutableList<List<FieldType>> =
//            ArrayList()
//        for (i in 0 until MinefieldConst.MINEFIELD_HEIGHT) {
//            val row: MutableList<FieldType> = ArrayList()
//            for (j in 0 until MinefieldConst.MINEFIELD_WIDTH) {
//                row.add(FieldType())
//            }
//            minefield.add(row)
//        }
//        return minefield
//    }
//}