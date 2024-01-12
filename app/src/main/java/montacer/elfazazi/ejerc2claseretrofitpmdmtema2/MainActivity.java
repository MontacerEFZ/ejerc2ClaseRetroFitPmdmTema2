package montacer.elfazazi.ejerc2claseretrofitpmdmtema2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import montacer.elfazazi.ejerc2claseretrofitpmdmtema2.adapters.CharacterAdapter;
import montacer.elfazazi.ejerc2claseretrofitpmdmtema2.conexiones.ApiConexiones;
import montacer.elfazazi.ejerc2claseretrofitpmdmtema2.conexiones.RetrofitObject;
import montacer.elfazazi.ejerc2claseretrofitpmdmtema2.databinding.ActivityMainBinding;
import montacer.elfazazi.ejerc2claseretrofitpmdmtema2.modelos.Character;
import montacer.elfazazi.ejerc2claseretrofitpmdmtema2.modelos.Respuesta;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<Character> listCharacters;
    private RecyclerView.LayoutManager lm;
    private CharacterAdapter adapter;
    private Retrofit retrofit;
    private ApiConexiones api;

    private String nextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listCharacters = new ArrayList<>();

        adapter = new CharacterAdapter(listCharacters, R.layout.character_view_holder, this);
        lm = new GridLayoutManager(this, 2);

        binding.contenedorMain.setAdapter(adapter);
        binding.contenedorMain.setLayoutManager(lm);

        retrofit = RetrofitObject.getConexion();
        api = retrofit.create(ApiConexiones.class);

        cargarDatosIniciales();

        binding.contenedorMain.addOnScrollListener(new RecyclerView.OnScrollListener() { //al escribir "new" se abrira un cuadro para elegir, elegimos el segundo en este caso
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {//1 es vertical, 0 es horizontal
                    if (nextPage != null){
                        int num = Integer.parseInt(nextPage.split("=")[1]);
                        cargarMasDatos(num);
                    }

                }
            }
        });
    }

    private void cargarMasDatos(int num) {
        Call<Respuesta> cogerPagina = api.getPage(num);

        cogerPagina.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK){
                    int longitud = listCharacters.size();
                    listCharacters.addAll(response.body().getResults());
                    adapter.notifyItemRangeInserted(longitud, listCharacters.size());
                    nextPage = response.body().getInfo().getNext();
                }
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarDatosIniciales() {
        Call<Respuesta> doGetCharacters = api.getCharacters();

        doGetCharacters.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK){
                    nextPage = response.body().getInfo().getNext();
                    listCharacters.addAll(response.body().getResults());
                    adapter.notifyItemRangeInserted(0, listCharacters.size());
                }
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error al cargar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}