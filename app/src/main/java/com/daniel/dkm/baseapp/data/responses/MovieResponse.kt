package com.daniel.dkm.baseapp.data.responses


import com.google.gson.annotations.SerializedName

class MovieResponse : ArrayList<MovieResponse.MovieResponseItem>() {
    data class MovieResponseItem(
        @SerializedName("category")
        var category: String?, // Latest
        @SerializedName("imageUrl")
        var imageUrl: String?, // https://howtodoandroid.com/images/coco.jpg
        @SerializedName("name")
        var name: String?, // Coco
        @SerializedName("desc")
        var desc: String? // Coco is a 2017 American 3D computer-animated musical fantasy adventure film produced by Pixar
    )
}