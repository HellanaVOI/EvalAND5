package be.technifutur.tlm.evaluation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.technifutur.tlm.evaluation.R
import be.technifutur.tlm.evaluation.ViewHolder.MovieAdapter
import be.technifutur.tlm.evaluation.databinding.FragmentSearchBinding
import be.technifutur.tlm.evaluation.network.model.MovieListResponse
import be.technifutur.tlm.evaluation.network.service.MovieServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.Date

class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    private val service by lazy { MovieServiceImpl() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        getSearchResult()
        return binding.root
    }

    private fun setupRecyclerView(movieList: MovieListResponse) {

        val recyclerView = binding!!.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = MovieAdapter(movieList.list){ CharId ->
            val direction = CharactersFragmentDirections.actionCharactersFragmentToComicsFragment(CharId)
            findNavController().navigate(direction)
        }

    }
    private fun getSearchResult() {
        CoroutineScope(Dispatchers.IO).launch {

            val response = service.searchByName("Dune")

            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.list?.forEach { item ->
                            Log.d("DEBUGG", item.name)
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