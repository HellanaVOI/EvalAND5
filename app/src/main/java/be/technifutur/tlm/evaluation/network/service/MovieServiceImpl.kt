package be.technifutur.tlm.evaluation.network.service

import be.technifutur.tlm.evaluation.network.model.MovieListResponse
import be.technifutur.tlm.evaluation.network.model.MovieResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MovieServiceImpl: MovieService {

    private fun getRetrofit(): Retrofit {
        val okBuilder = OkHttpClient().newBuilder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            callTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
        }
        /*
        https://api.themoviedb.org/3/movie/movie_id/similar?language=fr-FR&page=1
        https://api.themoviedb.org/3/search/movie?query=Dune&include_adult=false&language=en-US&page=1
        https://api.themoviedb.org/3/trending/all/day?language=en-US
        */

        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okBuilder.build())
            .build()
    }

    override suspend fun searchByName(
        name: String,
        isAdult: Boolean,
        language: String?,
        page: Int
    ): Response<MovieListResponse> {
        return getRetrofit().create(MovieService::class.java).searchByName(name = name)

    }

    override suspend fun getTreding(language: String?): Response<MovieListResponse> {
        return getRetrofit().create(MovieService::class.java).getTreding()
    }

    override suspend fun searchSimilar(id: String, language: String?): Response<MovieListResponse> {
        return getRetrofit().create(MovieService::class.java).searchSimilar(id)
    }

}