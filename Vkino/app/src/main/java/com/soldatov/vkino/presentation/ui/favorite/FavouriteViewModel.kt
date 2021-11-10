package com.soldatov.vkino.presentation.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.soldatov.domain.models.favourite.Categories
import com.soldatov.domain.usecase.favourite.GetFavByCatUseCase
import com.soldatov.domain.usecase.favourite.GetFavCatsUseCase
import kotlinx.coroutines.Dispatchers

class FavouriteViewModel(
    getFavCatsUseCase: GetFavCatsUseCase,
    getFavByCatUseCase: GetFavByCatUseCase
) : ViewModel() {

    private val favFilmsPage = MutableLiveData(1)
    private var favFilmsLoading = true

    val favCats = liveData(Dispatchers.IO) {
        emit(getFavCatsUseCase.execute())
    }

    val favFilms = Transformations.switchMap(favFilmsPage) { page ->
        liveData(Dispatchers.IO) {
            emit(getFavByCatUseCase.execute(Categories.FILMS.id, page))
        }
    }

    fun filmsNextPage(){
        val currentPage = favFilmsPage.value
        favFilmsPage.value = currentPage?.plus(1)
    }

    fun setFavFilmsLoading(value: Boolean){
        favFilmsLoading = value
    }

    fun getFavFilmsLoading(): Boolean{
        return favFilmsLoading
    }


    private val favAnimePage = MutableLiveData(1)
    private var favAnimeLoading = true

    val favAnime = Transformations.switchMap(favAnimePage) { page ->
        liveData(Dispatchers.IO) {
            emit(getFavByCatUseCase.execute(Categories.ANIME.id, page))
        }
    }

    fun animeNextPage(){
        val currentPage = favAnimePage.value
        favAnimePage.value = currentPage?.plus(1)
    }

    fun setFavAnimeLoading(value: Boolean){
        favAnimeLoading = value
    }

    fun getFavAnimeLoading(): Boolean{
        return favAnimeLoading
    }


    private val favSerialsPage = MutableLiveData(1)
    private var favSerialsLoading = true

    val favSerials = Transformations.switchMap(favSerialsPage) { page ->
        liveData(Dispatchers.IO) {
            emit(getFavByCatUseCase.execute(Categories.SERIALS.id, page))
        }
    }

    fun serialsNextPage(){
        val currentPage = favSerialsPage.value
        favSerialsPage.value = currentPage?.plus(1)
    }

    fun setFavSerialsLoading(value: Boolean){
        favSerialsLoading = value
    }

    fun getFavSerialsLoading(): Boolean{
        return favSerialsLoading
    }


    private val favAnimeSerialsPage = MutableLiveData(1)
    private var favAnimeSerialsLoading = true

    val favAnimeSerials = Transformations.switchMap(favAnimeSerialsPage) { page ->
        liveData(Dispatchers.IO) {
            emit(getFavByCatUseCase.execute(Categories.ANIME_SERIALS.id, page))
        }
    }

    fun animeSerialsNextPage(){
        val currentPage = favAnimeSerialsPage.value
        favAnimeSerialsPage.value = currentPage?.plus(1)
    }

    fun setFavAnimeSerialsLoading(value: Boolean){
        favAnimeSerialsLoading = value
    }

    fun getFavAnimeSerialsLoading(): Boolean{
        return favAnimeSerialsLoading
    }


    private val favTvPage = MutableLiveData(1)
    private var favTvLoading = true

    val favTv = Transformations.switchMap(favTvPage) { page ->
        liveData(Dispatchers.IO) {
            emit(getFavByCatUseCase.execute(Categories.TV.id, page))
        }
    }

    fun tvNextPage(){
        val currentPage = favTvPage.value
        favTvPage.value = currentPage?.plus(1)
    }

    fun setFavTvLoading(value: Boolean){
        favTvLoading = value
    }

    fun getFavTvLoading(): Boolean{
        return favTvLoading
    }
}