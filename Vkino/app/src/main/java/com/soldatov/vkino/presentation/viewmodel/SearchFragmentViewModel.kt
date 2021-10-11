package com.soldatov.vkino.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.soldatov.data.api.request_status.FilmResult
import com.soldatov.data.api.request_status.FilmsSliderResult
import com.soldatov.data.api.request_status.SearchResult
import com.soldatov.domain.usecase.GetSearchFilmsUseCase
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.util.*

class SearchFragmentViewModel(
    private val getSearchFilmsUseCase: GetSearchFilmsUseCase
): ViewModel()  {

    private val searchParams = MutableLiveData<SearchParams>()

    val searchFilms = Transformations.switchMap(searchParams) { params ->
        liveData(Dispatchers.IO) {
            try {
                emit(SearchResult.Success(data = getSearchFilmsUseCase.execute(params.getSearchQuery())))
            } catch (exception: Exception) {
                emit(FilmResult.Error(message = exception.message ?: "Error Occurred"))
            }
        }
    }

    fun setSearchQuery(query: String){
        val update = SearchParams()
        update.setSearchQuery(query)
        if (Objects.equals(searchParams.value, update)) {
            return
        }
        searchParams.setValue(update)
    }

    fun getSearchQuery(): String{
        return if (searchParams.value != null){
            searchParams.value!!.getSearchQuery()
        } else {
            ""
        }
    }

    private class SearchParams{
        private var searchQuery: String? = null
        private var page: Int = 1

        fun setSearchQuery(newQuery: String){
            searchQuery = newQuery
        }

        fun getSearchQuery(): String{
            return if (searchQuery != null){
                searchQuery!!
            } else {
                ""
            }
        }

        fun setPage(newPage: Int){
            page = newPage
        }

        fun getPage(): Int{
            return page
        }
    }
}