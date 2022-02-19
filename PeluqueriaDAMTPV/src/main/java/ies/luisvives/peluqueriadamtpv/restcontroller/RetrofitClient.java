package ies.luisvives.peluqueriadamtpv.restcontroller;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClient{
    private static Retrofit instance;

    private RetrofitClient() {

    }

    public static Retrofit getClient(String url) {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(url)
//                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}
