package com.example.acronyms.repository

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AcromineApi {
    @GET("dictionary.py")
    suspend fun getMeanings(@Query("sf") sf: String): Response<List<AbbItem>>
}