package com.soldatov.vkino.presentation.ui.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.soldatov.domain.models.Category
import com.soldatov.domain.models.result.GenresResult
import com.soldatov.domain.models.result.YearsResult
import com.soldatov.domain.models.Genre
import com.soldatov.domain.models.Years
import com.soldatov.domain.models.result.CategoriesResult
import com.soldatov.domain.usecase.filter.GetCategoriesUseCase
import com.soldatov.domain.usecase.filter.GetGenresUseCase
import com.soldatov.domain.usecase.filter.GetYearsUseCase
import kotlinx.coroutines.Dispatchers
import kotlin.Exception

class FilterFragmentViewModel(
    private val getGenresUseCase: GetGenresUseCase,
    private val getYearsUseCase: GetYearsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {

    val chosenGenres = MutableLiveData<ArrayList<Genre>>(arrayListOf())
    val chosenCategories = MutableLiveData<ArrayList<Category>>(arrayListOf())

    val genres = liveData(Dispatchers.IO) {
        emit(GenresResult.Loading)
        try {
            emit(GenresResult.Success(data = getGenresUseCase.execute()))
        } catch (exception: Exception) {
            emit(GenresResult.Error(message = exception.message ?: "Error Occurred"))
        }
    }

    val years = liveData(Dispatchers.IO) {
        emit(YearsResult.Loading)
        try {
            emit(YearsResult.Success(data = getYearsUseCase.execute()))
        } catch (exception: Exception){
            emit(YearsResult.Error(message = exception.message ?: "Error Occurred"))
        }
    }

    val categories = liveData(Dispatchers.IO){
        emit(CategoriesResult.Loading)
        try{
            emit(CategoriesResult.Success(data = getCategoriesUseCase.execute()))
        } catch (exception: Exception){
            emit(CategoriesResult.Error(message = exception.message ?: "Error Occurred"))
        }
    }

    fun getYears(): Years{
        return (years.value as YearsResult.Success).data
    }

    fun setChosenGenres(genres: List<Genre>){
        chosenGenres.value = genres as ArrayList<Genre>
    }

    fun getChosenGenres(): List<Genre>{
        return chosenGenres.value ?: emptyList()
    }

    fun removeChosenGenre(genre: Genre){
        val currentGenres = chosenGenres.value
        currentGenres?.remove(genre)
        chosenGenres.postValue(currentGenres ?: arrayListOf())
    }

    fun setChosenCategories(categories: List<Category>){
        chosenCategories.value = categories as ArrayList<Category>
    }

    fun getChosenCategories(): List<Category>{
        return chosenCategories.value ?: emptyList()
    }

    fun removeChosenCategories(category: Category){
        val currentCategories = chosenCategories.value
        currentCategories?.remove(category)
        chosenCategories.postValue(currentCategories ?: arrayListOf())
    }
}