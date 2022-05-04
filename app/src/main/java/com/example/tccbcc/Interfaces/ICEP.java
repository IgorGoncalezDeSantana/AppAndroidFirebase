package com.example.tccbcc.Interfaces;

import com.example.tccbcc.models.CEP;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ICEP {
    @GET("ws/{cep}/json/")
    Call<CEP> Find(@Path("cep") String CEP);
}
