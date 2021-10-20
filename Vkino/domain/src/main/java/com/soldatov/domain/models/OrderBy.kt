package com.soldatov.domain.models

enum class OrderBy(val value: String, val orderName: String) {
    ID("ID", "Дате обновления"),
    YEAR("year", "Дате выхода"),
    RATING("rating", "Рейтингу"),
    VIEWS("views", "Популярности");

    companion object{
        fun valueByOrderName(orderName: String): String {
            return when (orderName){
                ID.orderName -> ID.value
                YEAR.orderName -> YEAR.value
                RATING.orderName -> RATING.value
                else -> VIEWS.value
            }
        }
    }
}
