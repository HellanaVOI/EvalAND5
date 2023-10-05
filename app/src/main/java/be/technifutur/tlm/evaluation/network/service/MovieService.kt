package be.technifutur.tlm.evaluation.network.service

import be.technifutur.tlm.evaluation.network.model.MovieListResponse
import be.technifutur.tlm.evaluation.network.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieService {

    //TODO: Implementer le passage des pages
    @Headers(
        "Accept: Content-type: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1NTUzMDMxMjA3NTk3MmE0MjVmNWZhMTNlMjFiMjE4ZiIsInN1YiI6IjY1MWU1ZDEyM2QzNTU3MDBlMjNkYjY3NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.1iCRc3NpRaxTfAXaLutiEzGqHbRQAD1xa9hGFW9tAbY"
        )
    @GET("search/movie")
    suspend fun searchByName(
        @Query("query", encoded = true) name: String,
        @Query("include_adult", encoded = false) isAdult: Boolean = false,
        @Query("language", encoded = false) language: String? = "fr-FR",
        @Query("page", encoded = false) page: Int = 1
    ): Response<MovieListResponse>

}