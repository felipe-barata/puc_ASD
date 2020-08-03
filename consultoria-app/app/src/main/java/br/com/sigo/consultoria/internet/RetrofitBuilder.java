package br.com.sigo.consultoria.internet;


import java.util.concurrent.TimeUnit;

import br.com.sigo.consultoria.domain.Configuration;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    public Retrofit getRetrofit(Configuration configuration) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(configuration.getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(configuration.getReadTimeout(), TimeUnit.SECONDS)
                .writeTimeout(configuration.getWriteTimeout(), TimeUnit.SECONDS)
                .build();

        String baseUrl = configuration.getBaseUrl();
        if (!baseUrl.startsWith("http")) {
            baseUrl = "http://" + baseUrl + "/";
        }

        if (!baseUrl.endsWith("/")) {
            baseUrl = baseUrl + "/";
        }

        Retrofit retrofit = new Retrofit.
                Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }


}
