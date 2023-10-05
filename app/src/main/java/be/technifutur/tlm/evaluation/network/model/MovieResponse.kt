package be.technifutur.tlm.evaluation.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class MovieResponse : Parcelable {

    @SerializedName("id")
    var id: String = "no_id"
    @SerializedName("original_title")
    var name: String= "not_title"
    @SerializedName("overview")
    var desc: String? = "not_found"
    @SerializedName("vote_average")
    var note: Double? = 0.0
    @SerializedName("release_date")
    var date: String? = "No_date"
    @SerializedName("backdrop_path")
    var backdrop: String? = "no_img"
    @SerializedName("poster_path")
    var poster: String? = "no_img"
}

class MovieListResponse(
    @SerializedName("results")
    var list: MutableList<MovieResponse>
)