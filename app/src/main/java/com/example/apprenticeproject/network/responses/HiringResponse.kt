package com.example.apprenticeproject.network.responses

import com.google.gson.annotations.SerializedName

data class HiringResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("listId") var listId: Int,
    @SerializedName("name") var name: String? = null
)