package be.technifutur.tlm.evaluation.fragment

import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.tlm.evaluation.ViewHolder.TredingAdapter
import be.technifutur.tlm.evaluation.databinding.FragmentMovieDetailBinding
import be.technifutur.tlm.evaluation.isNote
import be.technifutur.tlm.evaluation.network.model.MovieListResponse
import be.technifutur.tlm.evaluation.network.service.MovieServiceImpl
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MovieDetailFragment : Fragment() {

    private val args: MovieDetailFragmentArgs by navArgs()
    lateinit var binding: FragmentMovieDetailBinding
    private val service by lazy { MovieServiceImpl() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailBinding.inflate(layoutInflater)
        getSearchResult()
        setupFragment()


        return binding.root
    }

    private fun setupFragment(){
        val movie = args.movie

        binding.titleMovie.text = movie.name
        binding.descMovie.text = movie.desc
        binding.noteMovie.text = movie.note?.isNote()

        Picasso.get()
            .load("https://image.tmdb.org/t/p/original${movie.poster}")
            .into(binding.imgDetail)

        Picasso.get()
            .load("https://image.tmdb.org/t/p/original${movie.backdrop}")
            .into(binding.headerDetail)
    }

    private fun setupRecyclerView(movieList: MovieListResponse) {
        val recyclerView = binding!!.recyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = TredingAdapter(movieList.list) { movie ->
            val direction = MovieDetailFragmentDirections.actionMovieDetailFragmentSelf(movie)
            findNavController().navigate(direction)
        }
    }

    private fun getSearchResult() {
        CoroutineScope(Dispatchers.IO).launch {

            val response = service.searchSimilar(args.movie.id)

            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            setupRecyclerView(it)
                        }
                    }
                } catch (e: HttpException) {
                    print(e)
                } catch (e: Throwable) {
                    print(e)
                }
            }
        }
    }
}