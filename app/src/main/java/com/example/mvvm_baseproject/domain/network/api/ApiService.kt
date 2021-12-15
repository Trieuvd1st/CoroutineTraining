package com.example.mvvm_baseproject.domain.network.api

import com.example.mvvm_baseproject.domain.network.pojo.response.Product
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/products")
    fun getListProduct(): Observable<List<Product>>

    @GET("/products/{id}")
    fun getProductById(
        @Path("id") id: Int
    ): Observable<Product>
}