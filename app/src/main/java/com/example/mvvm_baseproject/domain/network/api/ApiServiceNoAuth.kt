package com.example.mvvm_baseproject.domain.network.api

import com.example.mvvm_baseproject.domain.network.pojo.response.Product
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceNoAuth {
    @GET("/products")
    fun getListProduct(): Observable<List<Product>>

    @GET("/products")
    fun getProductById(
        @Query("") id: Int
    ): Observable<Product>
}