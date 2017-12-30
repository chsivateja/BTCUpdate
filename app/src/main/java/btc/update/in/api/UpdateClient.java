package btc.update.in.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by subhashhardaha on 03/12/17.
 */

public class UpdateClient {

    private static Retrofit retrofit = null;

    private static Gson gson = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            gson= new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
