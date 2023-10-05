package be.technifutur.tlm.evaluation.ViewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.technifutur.tlm.evaluation.databinding.CellMiniatureBinding
import be.technifutur.tlm.evaluation.isNote
import be.technifutur.tlm.evaluation.network.model.MovieResponse
import com.squareup.picasso.Picasso


class TredingViewHolder(private var viewBinding: CellMiniatureBinding, val onClick: (MovieResponse) -> Unit) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(item: MovieResponse) {
        viewBinding.noteMovie.text = item.note?.isNote()

        Picasso.get()
            .load("https://image.tmdb.org/t/p/original${item.poster}")
            .into(viewBinding.imgMovie)

        viewBinding.cellMovie.setOnClickListener{
            onClick(item)
        }
    }
}

class TredingAdapter(private var ext: MutableList<MovieResponse>, val onClick: (MovieResponse) -> Unit) :

    RecyclerView.Adapter<TredingViewHolder>() {
    private lateinit var binding: CellMiniatureBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TredingViewHolder {
        //charge le layout de la cellule
        binding = CellMiniatureBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TredingViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: TredingViewHolder, position: Int) {
        holder.bind(ext[position])
    }

    //retourne le nbr d'élément à afficher
    override fun getItemCount(): Int {
        return ext.size
    }
}