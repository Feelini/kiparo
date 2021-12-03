package com.soldatov.domain.models.result

import com.soldatov.domain.models.ActorsList

sealed class ActorsResult{
    class Success(val data: ActorsList): ActorsResult()
    class Error(val message: String): ActorsResult()
    object Loading: ActorsResult()
}
