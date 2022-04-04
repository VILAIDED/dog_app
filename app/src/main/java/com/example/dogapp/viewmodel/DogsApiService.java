package com.example.dogapp.viewmodel;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import com.example.dogapp.model.DogBread;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogsApiService {
    private static final String BASE_URL = "https://raw.githubusercontent.com/";
    private DogsApi api;
    private static DogsApiService instance;
    public DogsApiService(){
        if(instance == null){
            api = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build()
                    .create(DogsApi.class);
        }

       //singleton
    }
    public Single<List<DogBread>> getDogs(){
        return api.getDogs();
    }
}
