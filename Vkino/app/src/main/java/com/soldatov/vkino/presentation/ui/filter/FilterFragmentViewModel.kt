package com.soldatov.vkino.presentation.ui.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.soldatov.domain.models.*
import com.soldatov.domain.models.result.GenresResult
import com.soldatov.domain.models.result.YearsResult
import com.soldatov.domain.models.result.CategoriesResult
import com.soldatov.domain.models.result.CountriesResult
import com.soldatov.domain.usecase.filter.GetCategoriesUseCase
import com.soldatov.domain.usecase.filter.GetCountriesUseCase
import com.soldatov.domain.usecase.filter.GetGenresUseCase
import com.soldatov.domain.usecase.filter.GetYearsUseCase
import kotlinx.coroutines.Dispatchers
import java.util.*
import kotlin.Exception
import kotlin.collections.ArrayList

class FilterFragmentViewModel(
    private val getGenresUseCase: GetGenresUseCase,
    private val getYearsUseCase: GetYearsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getCountriesUseCase: GetCountriesUseCase
): ViewModel() {

    val chosenGenres = MutableLiveData<ArrayList<Genre>>(arrayListOf())
    val chosenCategories = MutableLiveData<ArrayList<Category>>(arrayListOf())
    val chosenYears = MutableLiveData<Years>()
    val chosenCountries = MutableLiveData<ArrayList<Country>>(arrayListOf())
    private val countriesSearchParams = MutableLiveData(SearchParams())

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

    val searchCounties = Transformations.switchMap(countriesSearchParams) { params ->
        liveData(Dispatchers.IO){
            emit(CountriesResult.Loading)
            try{
                emit(CountriesResult.Success(data = getCountriesUseCase.execute(
                    params.getSearchQuery(),
                    params.getPage()
                )))
            } catch (exception: Exception){
                emit(CountriesResult.Error(message = exception.message ?: "Error Occurred"))
            }
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

    fun setChosenYears(years: Years){
        chosenYears.value = years
    }

    fun setChosenCountries(countries: List<Country>){
        chosenCountries.value = ArrayList(countries)
    }

    fun getChosenCountries(): List<Country>{
        return chosenCountries.value ?: emptyList()
    }

    fun removeChosenCountries(country: Country){
        val currentCountries = chosenCountries.value
        currentCountries?.remove(country)
        chosenCountries.postValue(currentCountries ?: arrayListOf())
    }

    fun setCountriesSearchQuery(searchQuery: String){
        val update = SearchParams(searchQuery)
        if (Objects.equals(countriesSearchParams.value, update)) {
            return
        }
        countriesSearchParams.value = update
    }

    fun getCountriesSearchQuery(): String{
        return countriesSearchParams.value!!.getSearchQuery()
    }

    fun countriesNextPage(){
        val update = countriesSearchParams.value!!
        update.nextPage()
        countriesSearchParams.value = update
    }

    private class SearchParams(
        private var searchQuery: String = "",
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