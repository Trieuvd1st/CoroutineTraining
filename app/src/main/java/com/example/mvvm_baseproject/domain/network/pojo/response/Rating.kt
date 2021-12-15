package com.example.mvvm_baseproject.domain.network.pojo.response

import com.google.gson.annotations.SerializedName

class Rating (
    @SerializedName("rate") var rate: Double,
    @SerializedName("count") var count: Int,
)