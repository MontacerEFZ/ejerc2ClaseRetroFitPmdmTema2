package montacer.elfazazi.ejerc2claseretrofitpmdmtema2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
    }

    private void cargarDatosIniciales() {
        Call<Respuesta> doGetCharacters = api.getCharacters();

        doGetCharacters.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK){
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