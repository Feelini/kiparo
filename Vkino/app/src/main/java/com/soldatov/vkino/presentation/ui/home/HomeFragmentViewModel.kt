package com.soldatov.vkino.presentation.ui.home

import androidx.lifecycle.*
import com.soldatov.domain.models.FilterParams
import com.soldatov.domain.models.Order
import com.soldatov.domain.models.OrderBy
import com.soldatov.domain.models.result.FilmsResult
import com.soldatov.domain.models.result.FilmsSliderResult
import com.soldatov.domain.usecase.GetHomeFilmsUseCase
import com.soldatov.domain.usecase.GetOrderByUseCase
import com.soldatov.domain.usecase.GetTopSliderUseCase
import com.soldatov.domain.usecase.favourite.AddFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class HomeFragmentViewModel(
    private val getTopSliderUseCase: GetTopSliderUseCase,
    private val getHomeFilmsUseCase: GetHomeFilmsUseCase,
    private val getOrderByUseCase: GetOrderByUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase
) : ViewModel() {

    private var filterParams = MutableLiveData(FilterParams())

    val topSliderInfo = liveData(Dispatchers.IO) {
        emit(FilmsSliderResult.Loading)
        try {
            emit(FilmsSliderResult.Success(data = getTopSliderUseCase.execute()))
        } catch (exception: Exception) {
            emit(FilmsSliderResult.Error(message = exception.message ?: "Error Occurred"))
        }
    }

    val homePageFilms = Transformations.switchMap(filterParams) { filterParams ->
        liveData(Dispatchers.IO) {
            emit(FilmsSliderResult.Loading)
            try {
                emit(FilmsResult.Success(data = getHomeFilmsUseCase.execute(filterParams)))
            } catch (exception: Exception) {
                emit(FilmsResult.Error(message = exception.message ?: "Error Occurred"))
            }
        }
    }

    fun getOrderByData(): List<String>{
        return getOrderByUseCase.execute()
    }

    fun nextPage(){
        val currentParams = filterParams.value
        currentParams?.page = currentParams?.page?.plus(1) ?: 1
        filterParams.postValue(currentParams)
    }

    fun setOrderBy(orderByName: String){
        val currentParams = filterParams.value
        currentParams?.orderBy = OrderBy.valueByOrderName(orderByName)
        filterParams.postValue(currentParams)
    }

    fun getCurrentOrderBy(): String{
        return OrderBy.valueOf(filterParams.value?.orderBy!!.uppercase()).orderName
    }

    fun switchOrder(){
        val currentParams = filterParams.value
        val currentOrder = currentParams?.order!!
        currentParams.order = Order.switchOrder(currentOrder)
        filterParams.postValue(currentParams)
    }

    fun getCurrentOrder(): String{
        return filterParams.value?.order!!
    }

    fun setFilterParams(newFilterParams: FilterParams){
        filterParams.value = newFilterParams
    }

    fun getFilterParams(): LiveData<FilterParams>{
        return filterParams
    }

    fun addToFavorite(filmId: Long): LiveData<Boolean>{
        return liveData(Dispatchers.IO) {
            emit(addFavoriteUseCase.execute(filmId))
        }
    }
}