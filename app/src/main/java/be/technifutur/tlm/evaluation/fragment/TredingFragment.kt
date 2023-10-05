package be.technifutur.tlm.evaluation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.technifutur.tlm.evaluation.databinding.FragmentTredingBinding
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
        getSearchResult()
        return binding.root
    }


    private fun getSearchResult() {
        CoroutineScope(Dispatchers.IO).launch {

            val response = service.getTreding()

            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.list?.forEach {
                            Log.d("DEBUGG", it.name)
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