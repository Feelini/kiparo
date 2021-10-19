package com.soldatov.domain.models.result

import com.soldatov.domain.models.Quality

sealed class QualitiesResult{
    class Success(val data: List<Quality>): QualitiesResult()
    class Error(val message: String): QualitiesResult()
    object Loading: QualitiesResult()
}
