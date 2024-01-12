package montacer.elfazazi.ejerc2claseretrofitpmdmtema2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import javax.net.ssl.HttpsURLConnection;

import montacer.elfazazi.ejerc2claseretrofitpmdmtema2.conexiones.ApiConexiones;
import montacer.elfazazi.ejerc2claseretrofitpmdmtema2.conexiones.RetrofitObject;
import montacer.elfazazi.ejerc2claseretrofitpmdmtema2.databinding.ActivityCharacterBinding;
import montacer.elfazazi.ejerc2claseretrofitpmdmtema2.modelos.Character;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterActivity extends AppCompatActivity {
    private ActivityCharacterBinding binding;
    private ApiConexiones api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCharacterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        api = RetrofitObject.getConexion().create(ApiConexiones.class);

        Intent intent = getIntent(); //es get porq vamos a recogerlo, no crearlo

       if (intent.getExtras() != null){
           int id = intent.getExtras().getInt("ID");

           Call<Character> getCharacter = api.getCharacter(id);
           getCharacter.enqueue(new Callback<Character>() {
               @Override
               public void onResponse(Call<Character> call, Response<Character> response) {
                   if (response.code() == HttpsURLConnection.HTTP_OK){
                       Character c = response.body();
                       binding.lbNameCharacter.setText(c.getName());
                       binding.lbEspecieCharacter.setText(c.getSpecies());
                       binding.lbGenderCharacter.setText(c.getGender());

                       Picasso.get()
                               .load(c.getImage())
                               .into(binding.imgPhotoCharacter);
                   }
               }

               @Override
               public void onFailure(Call<Character> call, Throwable t) {
                   Toast.makeText(CharacterActivity.this, "error al cargar", Toast.LENGTH_SHORT).show();
               }
           });
       }
    }
}