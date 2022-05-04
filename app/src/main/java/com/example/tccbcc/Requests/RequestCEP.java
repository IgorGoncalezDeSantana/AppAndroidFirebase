package com.example.tccbcc.Requests;

import com.example.tccbcc.Interfaces.ICEP;
import com.example.tccbcc.models.CEP;

import retrofit2.Call;
import retrofit2.Retrofit;

public class RequestCEP {
    private static String uri = "https://viacep.com.br/";

    public static CEP Find(String CEP) throws Exception {
        Retrofit retrofit = RetrofitBuilder.MakeBuilder(uri);

        ICEP service = retrofit.create(ICEP.class);
        Call<CEP> call = service.Find(CEP);
        GenericRequest<CEP> request = new GenericRequest<>();

        CEP value;
        try {
            value = request.GenericResponse(call, "CEP Preenchido incorretamente.");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return value;
    }
}
