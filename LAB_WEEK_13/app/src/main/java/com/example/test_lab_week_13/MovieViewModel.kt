package com.example.test_lab_week_13

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_lab_week_13.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.Calendar

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    init {
        fetchPopularMovies()
    }

    // define the StateFlow
    private val _popularMovies = MutableStateFlow(emptyList<Movie>())
    val popularMovies: StateFlow<List<Movie>> = _popularMovies

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    // fetch movies from the API
    private fun fetchPopularMovies() {
        // launch a coroutine in viewModelScope
        // Dispatchers.IO means that this coroutine will run on a shared pool of threads
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchMovies()
                .catch { e ->
                    // catch is a terminal operator that catches exceptions from the Flow
                    _error.value = "An exception occurred: ${e.message}"
                }
                .collect { movies ->
                    // collect is a terminal operator that collects the values from the Flow
                    // the results are emitted to the StateFlow
                    // Filter and sort as in original Activity
                    val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
                    val filteredMovies = movies
                        .filter { movie ->
                             movie.releaseDate?.startsWith(currentYear) == true
                        }
                        .sortedByDescending { it.popularity }
                    _popularMovies.value = filteredMovies
                }
        }
    }
}