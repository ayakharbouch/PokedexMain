package com.aya.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aya.pokedex.models.Stat;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import com.aya.pokedex.models.Pokemon;
import com.aya.pokedex.pokeapi.PokeapiService;

public class MainActivity2 extends AppCompatActivity {
    private ProgressBar progressBar3;
    private TextView progressText3;

    private ProgressBar progressBar4;
    private TextView progressText4;

    private ProgressBar progressBar5;
    private TextView progressText5;

    private ProgressBar progressBar1;
    private TextView progressText1;

    private ProgressBar progressBar6;
    private TextView progressText6;

    private ProgressBar progressBar2;
    private TextView progressText2;
private TextView textView1 ;
    private TextView textView2 ;
    private TextView textView3 ;
    private ImageView img1;
    private static final String TAG = "ss";
    private Retrofit retrofit;
    private String index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        progressBar1 = findViewById(R.id.progressBar1);
        progressText1 = findViewById(R.id.progressText1);

        progressBar2 = findViewById(R.id.progressBar2);
        progressText2 = findViewById(R.id.progressText2);

        progressBar3 = findViewById(R.id.progressBar3);
        progressText3 = findViewById(R.id.progressText3);

        progressBar4 = findViewById(R.id.progressBar4);
        progressText4 = findViewById(R.id.progressText4);

        progressBar5 = findViewById(R.id.progressBar5);
        progressText5 = findViewById(R.id.progressText5);

        progressBar6 = findViewById(R.id.progressBar6);
        progressText6 = findViewById(R.id.progressText6);
        index = getIntent().getStringExtra("index");

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/pokemon/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        data();
    }
    private void data (){
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<Pokemon> pokemonRequestCall = service.getPokemon(index);
        pokemonRequestCall.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                //img1.setVisibility(View.VISIBLE);
                //progressBar00 = findViewById(R.id.progressBar7);
                //progressBar00.setVisibility(View.GONE);
                textView1 = findViewById(R.id.textView);
                img1 = findViewById(R.id.imageView1);
                textView2 = findViewById(R.id.textView2);
                textView3 = findViewById(R.id.textView3);


                Pokemon pokemonRequest = response.body();
                textView1.setText(pokemonRequest.getName());
                textView2.setText(pokemonRequest.getWeight() + " FT");
                textView3.setText(pokemonRequest.getHeight() + " KG");
                Glide.with(MainActivity2.this)
                        .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+index+".png")
                        .into(img1);
                for (Stat stat : pokemonRequest.getStats()) {
                    switch (stat.getStat().getName()) {
                        case "hp":
                            createLoadingAnimation(progressBar1, progressText1, stat.getBaseStat(), 100);
                            break;
                        case "attack":
                            createLoadingAnimation(progressBar2, progressText2, stat.getBaseStat(), 100);
                            break;
                        case "defense":
                            createLoadingAnimation(progressBar3, progressText3, stat.getBaseStat(), 100);
                            break;
                        case "special-attack":
                            createLoadingAnimation(progressBar4, progressText4, stat.getBaseStat(), 100);
                            break;
                        case "special-defense":
                            createLoadingAnimation(progressBar5, progressText5, stat.getBaseStat(), 100);
                            break;
                        case "speed":
                            createLoadingAnimation(progressBar6, progressText6, stat.getBaseStat(), 100);
                            break;
                    }
                }






            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e(TAG," not working: ");
            }
        });
    }
    private void createLoadingAnimation(ProgressBar progressBar, TextView progressText, int baseStat, int effort) {
        int maxWidth = progressBar.getWidth() - progressBar.getPaddingLeft() - progressBar.getPaddingRight();
        int maxProgress = (int) ((baseStat * maxWidth) / (float) progressBar.getWidth());
        int progress = effort > maxProgress ? maxProgress : effort;
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, progress);
        progressAnimator.setDuration(1000);
        progressAnimator.start();
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int progress = (int) valueAnimator.getAnimatedValue() - 3;
                int width = progressBar.getWidth();
                float position = (progress / (float) progressBar.getMax() * width);
                progressText.setX(progressBar.getX() + position - progressText.getWidth() / 2f);
            }
        });
        progressText.setText(baseStat + "/" + effort);
    }
}