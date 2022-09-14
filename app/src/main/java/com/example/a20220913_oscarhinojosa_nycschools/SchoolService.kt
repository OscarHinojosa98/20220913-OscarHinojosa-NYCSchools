package com.example.a20220913_oscarhinojosa_nycschools

import com.example.a20220913_oscarhinojosa_nycschools.SchoolScores
import com.example.a20220913_oscarhinojosa_nycschools.Schools
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolService {
    //Get list of school
    @GET("/resource/s3k6-pzi2.json")
    suspend fun getSchools() : Response<Schools>

    //Get scores of selected school
    @GET("/resource/f9bf-2cp4.json")
    suspend fun getScores(@Query("school_name")school_name: String?) : Response<SchoolScores>
}