package com.soldatov.vkino.presentation.utils

object Helper {

    fun listToString(list: List<String>): String{
        var result = ""
        list.forEach{
            result += it
            result += ", "
        }
        return if (result.length> 2) {
            result.substring(0, result.length - 2)
        } else {
            ""
        }
    }
}