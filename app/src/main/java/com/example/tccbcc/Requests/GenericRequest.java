package com.example.tccbcc.Requests;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class GenericRequest<T> {
    private T object;
    private String erro;

    public GenericRequest() {
        object = null;
        erro = "";
    }

    public T GenericResponse(Call<T> call, String msgErroPadrao) throws Exception {
        object = null;
        erro = "";

        Thread t = new Thread() {
            public void run() {
                try
                {
                    Response<T> response = call.execute();
                    if (!response.isSuccessful()) {
                        if (response.code() == 400) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                erro = jsonObject.getString("Error");
                            } catch (JSONException | IOException e) {
                                erro = msgErroPadrao + "\n\nErro:"+e.getMessage();
                            }
                        } else {
                            erro = msgErroPadrao+ "\n\nErro: "+ Integer.toString(response.code())+" - "+
                                    response.message();
                        }
                        return;
                    }
                    object = response.body();
                }
                catch (Exception e)
                {
                    erro = msgErroPadrao+ "\n\nErro:"+e.getMessage();
                }
            }
        };
        t.start();

        while (t.isAlive())
        {
            Thread.sleep(10);
        }

        if (!erro.equals(""))
            throw new Exception(erro);

        return object;
    }

}
