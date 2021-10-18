package com.soldatov.domain.models.result

import com.soldatov.domain.models.Category

sealed class CategoriesResult{
    class Success(val data: List<Category>): CategoriesResult()
    class Error(val message: String): CategoriesResult()
    object Loading: CategoriesResult()
}