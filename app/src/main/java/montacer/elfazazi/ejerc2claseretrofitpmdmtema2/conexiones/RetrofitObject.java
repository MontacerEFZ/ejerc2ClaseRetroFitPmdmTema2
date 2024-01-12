package montacer.elfazazi.ejerc2claseretrofitpmdmtema2.conexiones;

import java.util.ResourceBundle;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitObject {
    public static final String  BASE_URL = "https://rickandmortyapi.com";
    public static Retrofit getConexion(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
