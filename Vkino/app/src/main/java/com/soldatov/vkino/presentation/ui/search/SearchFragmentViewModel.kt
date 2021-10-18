package com.soldatov.vkino.presentation.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.soldatov.domain.models.result.FilmResult
import com.soldatov.domain.models.result.FilmsResult
import com.soldatov.domain.usecase.GetFilmsForSearchUseCase
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.util.*

class SearchFragmentViewModel(
    private val getFilmsForSearchUseCase: GetFilmsForSearchUseCase
) : ViewModel() {

    private val searchParams = MutableLiveData<SearchParams>()

    val searchFilms = Transformations.switchMap(searchParams) { params ->
        liveData(Dispatchers.IO) {
            try {
                emit(
                    FilmsResult.Success(
                        data = getFilmsForSearchUseCase.execute(
                            params.getSearchQuery(),
                            params.getPage()
                        )
                    )
                )
            } catch (exception: Exception) {
                emit(FilmResult.Error(message = exception.message ?: "Error Occurred"))
            }
        }
    }

    fun setSearchQuery(query: String) {
        val update = SearchParams(query)
        if (Objects.equals(searchParams.value, update)) {
            return
        }
        searchParams.value = update
    }

    fun getSearchQuery(): String {
        return if (searchParams.value != null) {
            searchParams.value!!.getSearchQuery()
        } else {
            ""
        }
    }

    fun nextPage(){
        val update = searchParams.value
        update?.nextPage()

        searchParams.value = update!!
    }

    private class SearchParams(
        private var searchQuery: String,
        private var page: Int = 1
    ) {
        fun getSearchQuery(): String {
            return searchQuery
        }

        fun getPage(): Int {
            return page
        }

        fun nextPage(){
            page += 1
        }
    }
}