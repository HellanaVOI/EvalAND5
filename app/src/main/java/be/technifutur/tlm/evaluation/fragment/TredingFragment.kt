package be.technifutur.tlm.evaluation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import be.technifutur.tlm.evaluation.ViewHolder.MovieAdapter
import be.technifutur.tlm.evaluation.ViewHolder.TredingAdapter
import be.technifutur.tlm.evaluation.databinding.FragmentTredingBinding
import be.technifutur.tlm.evaluation.network.model.MovieListResponse
import be.technifutur.tlm.evaluation.network.service.MovieServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class TredingFragment : Fragment() {

    lateinit var binding: FragmentTredingBinding
    private val service by lazy { MovieServiceImpl() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTredingBinding.inflate(layoutInflater)
        getTredingResult()
        return binding.root
    }

    private fun setupRecyclerView(movieList: MovieListResponse) {
        val recyclerView = binding!!.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = TredingAdapter(movieList.list){ movie ->
            val direction = TredingFragmentDirections.actionTredingFragmentToMovieDetailFragment(movie)
            findNavController().navigate(direction)
        }
    }

    private fun getTredingResult() {
        CoroutineScope(Dispatchers.IO).launch {

            val response = service.getTreding()

            withContext(Dispatchers.Main) {
                try {
                    response.body()?.let {
                        setupRecyclerView(it)
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