package com.instances.food2fork.data.model.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class RecipesResponse (
    @SerializedName("count"    ) var count    : Int?               = null,
    @SerializedName("next"     ) var next     : String?            = null,
    @SerializedName("previous" ) var previous : String?            = null,
    @SerializedName("results"  ) var results  : ArrayList<Results> = arrayListOf()
): Serializable

@Entity(tableName = "Results")
data class Results (
    @PrimaryKey(autoGenerate = false)
    @SerializedName("pk"                   ) var pk                  : Int?              = null,
    @SerializedName("title"                ) var title               : String?           = null,
    @SerializedName("publisher"            ) var publisher           : String?           = null,
    @SerializedName("featured_image"       ) var featuredImage       : String?           = null,
    @SerializedName("rating"               ) var rating              : Int?              = null,
    @SerializedName("source_url"           ) var sourceUrl           : String?           = null,
    @SerializedName("description"          ) var description         : String?           = null,
    @SerializedName("cooking_instructions" ) var cookingInstructions : String?           = null,
    @SerializedName("ingredients"          ) var ingredients         : ArrayList<String> = arrayListOf(),
    @SerializedName("date_added"           ) var dateAdded           : String?           = null,
    @SerializedName("date_updated"         ) var dateUpdated         : String?           = null,
    @SerializedName("long_date_added"      ) var longDateAdded       : Int?              = null,
    @SerializedName("long_date_updated"    ) var longDateUpdated     : Int?              = null
): Serializable