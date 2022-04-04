package com.example.dogapp.viewmodel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import com.example.dogapp.model.DogBread;
import retrofit2.http.GET;

public interface DogsApi {

    @GET("DevTides/DogsApi/master/dogs.json")
    public Single<List<DogBread>> getDogs();
}
