package com.sindu.mybuckeylistapp.retrofit;

import com.sindu.mybuckeylistapp.model.BucketList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIServiceInterface {


  @GET("/hiring.json")
   Call<List<BucketList>> fetchRecords();

}
