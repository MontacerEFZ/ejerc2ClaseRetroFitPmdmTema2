package montacer.elfazazi.ejerc2claseretrofitpmdmtema2.conexiones;

import montacer.elfazazi.ejerc2claseretrofitpmdmtema2.modelos.Character;
import montacer.elfazazi.ejerc2claseretrofitpmdmtema2.modelos.Respuesta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiConexiones {

    //para obtener la lista inicial de personajes(primera pagina)
    @GET("/api/character")
    Call<Respuesta> getCharacters();

    @GET("/api/character/{idCharacter}")
    Call<Character> getCharacter(@Path("idCharacter") int id); //path porq no hay interrogante https://rickandmortyapi.com/api/character/2 solamente añadimos "/2/"

    @GET("/api/character?")
    Call<Respuesta> getPage(@Query("page")int pagina); //como hay un "?" se usa query y lo que pongamos entre "" en este caso "page" y tiene que coincidir con el page de la api q es la palabra q va despues del "?"
}
