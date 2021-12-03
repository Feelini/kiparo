package com.soldatov.vkino.presentation.ui.favorite

import android.util.Log
import androidx.lifecycle.*
import com.soldatov.domain.models.favorite.Categories
import com.soldatov.domain.models.favorite.FavoriteResult
import com.soldatov.domain.usecase.favourite.GetFavByCatUseCase
import com.soldatov.domain.usecase.favourite.GetFavCatsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavCatsUseCase: GetFavCatsUseCase,
    getFavByCatUseCase: GetFavByCatUseCase
) : ViewModel() {

    private val favCats = MutableLiveData<List<Categories>>()

    fun reloadCategory(category: String) {
        loadFavCats()

        when (category) {
            Categories.FILMS.value -> {
                favFilmsPage.postValue(1)
            }
            Categories.ANIME.value -> {
                favAnimePage.postValue(1)
            }
            Categories.SERIALS.value -> {
                favSerialsPage.postValue(1)
            }
            Categories.ANIME_SERIALS.value -> {
                favAnimePage.postValue(1)
            }
            Categories.TV.value -> {
                favTvPage.postValue(1)
            }
        }
    }

    private val favFilmsPage = MutableLiveData(1)
    private var favFilmsLoading = true

    fun getFavCats(): LiveData<List<Categories>> {
        if (favCats.value == null){
            loadFavCats()
        }
        return favCats
    }

    private fun loadFavCats(){
        viewModelScope.launch {
            favCats.postValue(getFavCatsUseCase.execute())
        }
    }

    val favFilms = Transformations.switchMap(favFilmsPage) { page ->
        liveData(Dispatchers.IO) {
            val films = getFavByCatUseCase.execute(Categories.FILMS.id, page)
            if (films is FavoriteResult.Success && !films.hasMore){
                favFilmsLoading = false
            }
            emit(films)
        }
    }

    fun filmsNextPage() {
        val currentPage = favFilmsPage.value
        favFilmsPage.value = currentPage?.plus(1)
    }

    fun setFavFilmsLoading(value: Boolean) {
        favFilmsLoading = value
    }

    fun getFavFilmsLoading(): Boolean {
        return favFilmsLoading
    }


    private val favAnimePage = MutableLiveData(1)
    private var favAnimeLoading = true

    val favAnime = Transformations.switchMap(favAnimePage) { page ->
        liveData(Dispatchers.IO) {
            val anime = getFavByCatUseCase.execute(Categories.ANIME.id, page)
            if (anime is FavoriteResult.Success && !anime.hasMore){
                favAnimeLoading = false
            }
            emit(anime)
        }
    }

    fun animeNextPage() {
        val currentPage = favAnimePage.value
        favAnimePage.value = currentPage?.plus(1)
    }

    fun setFavAnimeLoading(value: Boolean) {
        favAnimeLoading = value
    }

    fun getFavAnimeLoading(): Boolean {
        return favAnimeLoading
    }


    private val favSerialsPage = MutableLiveData(1)
    private var favSerialsLoading = true

    val favSerials = Transformations.switchMap(favSerialsPage) { page ->
        liveData(Dispatchers.IO) {
            val serials = getFavByCatUseCase.execute(Categories.SERIALS.id, page)
            if (serials is FavoriteResult.Success && !serials.hasMore){
                favSerialsLoading = false
            }
            emit(serials)
        }
    }

    fun serialsNextPage() {
        val currentPage = favSerialsPage.value
        favSerialsPage.value = currentPage?.plus(1)
    }

    fun setFavSerialsLoading(value: Boolean) {
        favSerialsLoading = value
    }

    fun getFavSerialsLoading(): Boolean {
        return favSerialsLoading
    }


    private val favAnimeSerialsPage = MutableLiveData(1)
    private var favAnimeSerialsLoading = true

    val favAnimeSerials = Transformations.switchMap(favAnimeSerialsPage) { page ->
        liveData(Dispatchers.IO) {
            val animeSerials = getFavByCatUseCase.execute(Categories.ANIME_SERIALS.id, page)
            if (animeSerials is FavoriteResult.Success && !animeSerials.hasMore){
                favAnimeSerialsLoading = false
            }
            emit(animeSerials)
        }
    }

    fun animeSerialsNextPage() {
        val currentPage = favAnimeSerialsPage.value
        favAnimeSerialsPage.value = currentPage?.plus(1)
    }

    fun setFavAnimeSerialsLoading(value: Boolean) {
        favAnimeSerialsLoading = value
    }

    fun getFavAnimeSerialsLoading(): Boolean {
        return favAnimeSerialsLoading
    }


    private val favTvPage = MutableLiveData(1)
    private var favTvLoading = true

    val favTv = Transformations.switchMap(favTvPage) { page ->
        liveData(Dispatchers.IO) {
            val tv = getFavByCatUseCase.execute(Categories.TV.id, page)
            if (tv is FavoriteResult.Success && !tv.hasMore){
                favTvLoading = false
            }
            emit(tv)
        }
    }

    fun tvNextPage() {
        val currentPage = favTvPage.value
        favTvPage.value = currentPage?.plus(1)
    }

    fun setFavTvLoading(value: Boolean) {
        favTvLoading = value
    }

    fun getFavTvLoading(): Boolean {
        return favTvLoading
    }
}