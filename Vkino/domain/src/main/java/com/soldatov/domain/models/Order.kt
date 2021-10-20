package com.soldatov.domain.models

enum class Order {
    DESC,
    ASC;

    companion object{
        fun switchOrder(currentName: String): String{
            return if (valueOf(currentName) == DESC) ASC.name else DESC.name
        }
    }
}