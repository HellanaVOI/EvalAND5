package be.technifutur.tlm.evaluation.ViewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.tlm.evaluation.databinding.CellMovieBinding
import be.technifutur.tlm.evaluation.isNote
import be.technifutur.tlm.evaluation.network.model.MovieResponse
import be.technifutur.tlm.evaluation.toCustomDate
import com.squareup.picasso.Picasso

class MovieViewHolder(private var viewBinding: CellMovieBinding, val onClick: (MovieResponse) -> Unit) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(item: MovieResponse) {
        //TODO: Arrondir le fond de la note (cardboard ?)

        viewBinding.titleMovie.text = item.name
        viewBinding.noteMovie.text = item.note?.isNote()
        viewBinding.dateMovie.text = item.date?.toCustomDate()

        Picasso.get()
            .load("https://image.tmdb.org/t/p/original${item.poster}")
            .into(viewBinding.imgMovie)

        viewBinding.cardboardMovie.setOnClickListener{
            onClick(item)
        }


    }
}

class MovieAdapter(private var ext: MutableList<MovieResponse>, val onClick: (MovieResponse) -> Unit) :

    RecyclerView.Adapter<MovieViewHolder>() {
    private lateinit var binding: CellMovieBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        //charge le layout de la cellule
        binding = CellMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(ext[position])
    }

    //retourne le nbr d'élément à afficher
    override fun getItemCount(): Int {
        return ext.size
    }
}