package com.example.test_lab_week_12

import com.example.test_lab_week_12.api.MovieService

class MovieRepository(private val movieService: MovieService) {
    private val apiKey = "6c82dfdb947ee27e7548d99c7e801bc7"

    // LiveData that contains a list of movies
    private val movieLiveData = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = movieLiveData

    // LiveData that contains an error message
    private val errorLiveData = MutableLiveData<String>()
    val error: LiveData<String>
        get() = errorLiveData
    suspend fun fetchMovies() {
        try {
            // get the list of popular movies from the API
            val popularMovies = movieService.getPopularMovies(apiKey)
            movieLiveData.postValue(popularMovies.results)
        } catch (exception: Exception) {
            // if an error occurs, post the error message to the
            errorLiveData
            errorLiveData.postValue(
                "An error occurred: ${exception.message}")
        }
    }
}
