package montacer.elfazazi.ejerc2claseretrofitpmdmtema2.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import montacer.elfazazi.ejerc2claseretrofitpmdmtema2.CharacterActivity;
import montacer.elfazazi.ejerc2claseretrofitpmdmtema2.R;
import montacer.elfazazi.ejerc2claseretrofitpmdmtema2.modelos.Character;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterVH> {
    private List<Character> objects;
    private int resource;
    private Context context;

    public CharacterAdapter(List<Character> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public CharacterAdapter.CharacterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resource, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new CharacterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.CharacterVH holder, int position) {
    Character character = objects.get(position);
    holder.lbName.setText(character.getName());

        Picasso.get()
                .load(character.getImage())
                .into(holder.imgPhoto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CharacterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID", character.getId());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class CharacterVH extends RecyclerView.ViewHolder{

        ImageView imgPhoto;
        TextView lbName;
        public CharacterVH(@NonNull View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.imgPhotoCharacterCard);
            lbName = itemView.findViewById(R.id.lbNameCharacterCard);

        }
    }
}
