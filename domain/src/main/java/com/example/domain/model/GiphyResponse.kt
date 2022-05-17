package com.example.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class GiphySearchResponse(
    @SerializedName("data") var data: List<GiphyItem> = listOf()
)

data class GiphyTreadingResponse(
    @SerializedName("data") var data: List<GiphyItem> = listOf(),
    @SerializedName("pagination") var pagination: Pagination
)

data class GiphyItem(
    @SerializedName("id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("images") val images: GiphyImages
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        TODO("images")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GiphyItem> {
        override fun createFromParcel(parcel: Parcel): GiphyItem {
            return GiphyItem(parcel)
        }

        override fun newArray(size: Int): Array<GiphyItem?> {
            return arrayOfNulls(size)
        }
    }
}

class Pagination(
    @SerializedName("total_count") val total_count: Int,
    @SerializedName("count") val count: Int?,
    @SerializedName("offset") val offset: Int
)

class GiphyImages(
    @SerializedName("preview_gif") val previewGif: GiphyImage,
    @SerializedName("original") val original: GiphyImage
)

class GiphyImage(
    @SerializedName("height") val height: String,
    @SerializedName("url") val url: String = "",
    @SerializedName("width") val width: String,
    @SerializedName("size") val size: Int
)

//FOR ROOM
//@Entity
//data class GiphyEntity(
//    @PrimaryKey
//    val id: String = "-1",
//    @ColumnInfo(name = "title")
//    val title: String = "",
//    @ColumnInfo(name = "preview_image_url")
//    val previewImageUrl: String = "",
//    @ColumnInfo(name = "original_image_url")
//    val originalImageUrl: String = ""
//)
//
//fun GiphyItem.toGiphyEntity():GiphyEntity{
//    return GiphyEntity(
//        this.id,
//        this.title.toString(),
//        this.images.previewGif.url,
//        this.images.original.url
//    )
//}