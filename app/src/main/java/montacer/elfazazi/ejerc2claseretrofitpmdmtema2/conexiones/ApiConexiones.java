package montacer.elfazazi.ejerc2claseretrofitpmdmtema2.conexiones;

import montacer.elfazazi.ejerc2claseretrofitpmdmtema2.modelos.Respuesta;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiConexiones {

    //para obtener la lista inicial de personajes(primera pagina)
    @GET("/api/character")
    Call<Respuesta> getCharacters();
}
