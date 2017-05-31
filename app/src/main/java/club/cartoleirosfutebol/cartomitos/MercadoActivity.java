package club.cartoleirosfutebol.cartomitos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import club.cartoleirosfutebol.cartomitos.adapters.MercadoListAdapter;
import club.cartoleirosfutebol.cartomitos.api.APIConstraints;
import club.cartoleirosfutebol.cartomitos.api.AtletasAPI;
import club.cartoleirosfutebol.cartomitos.api.PartidasAPI;
import club.cartoleirosfutebol.cartomitos.data.Atleta;
import club.cartoleirosfutebol.cartomitos.data.Mercado;
import club.cartoleirosfutebol.cartomitos.data.Partida;
import club.cartoleirosfutebol.cartomitos.data.PartidaClube;
import club.cartoleirosfutebol.cartomitos.util.MercadoDeserializer;
import club.cartoleirosfutebol.cartomitos.util.PartidasDeserializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MercadoActivity extends AppCompatActivity {

    private int _filtro;
    private final String TAG = "MercadoActivity";
    MercadoListAdapter mercadoListAdapter;
    HashMap<Atleta, List<String>> listDataScout;
    ExpandableListView expListView;
    Mercado _mercado;
    PartidaClube _partidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent = getIntent();
        _filtro = intent.getIntExtra("filtro", 0);
        toolbar.setTitle("Mercado");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        expListView = (ExpandableListView) findViewById(R.id.lvExpMercado);

    }

    @Override
    public void onResume(){
        super.onResume();
        Gson gsonMercado = new GsonBuilder().registerTypeAdapter(Mercado.class, new MercadoDeserializer()).create();
        Retrofit retrofitAtletas = new Retrofit.Builder()
                .baseUrl(APIConstraints.API_CARTOLA_BASE)
                .addConverterFactory(GsonConverterFactory.create(gsonMercado))
                .build();
        AtletasAPI atletasAPI = retrofitAtletas.create(AtletasAPI.class);
        Call<Mercado> callAtletas = atletasAPI.getMercado();

        callAtletas.enqueue(new Callback<Mercado>() {
            @Override
            public void onResponse(Call<Mercado> call, Response<Mercado> response) {

                _mercado = response.body();

                Gson gsonPartidas = new GsonBuilder().registerTypeAdapter(Partida.class, new PartidasDeserializer()).create();
                Retrofit retrofitPartidas = new Retrofit.Builder()
                        .baseUrl(APIConstraints.API_CARTOLA_BASE)
                        .addConverterFactory(GsonConverterFactory.create(gsonPartidas))
                        .build();
                PartidasAPI partidasAPI = retrofitPartidas.create(PartidasAPI.class);
                Call<PartidaClube> callPartidas = partidasAPI.getPartidas();

                callPartidas.enqueue(new Callback<PartidaClube>() {
                    @Override
                    public void onResponse(Call<PartidaClube> call, Response<PartidaClube> response) {

                        _partidas = response.body();

                        if (_mercado != null) {

                            if (_mercado.getAtletas() != null) {

                                listDataScout = new HashMap<Atleta, List<String>>();
                                List<Atleta> atletas = new ArrayList<Atleta>();

                                List<String> scouts = new ArrayList<String>();
                                scouts.add("Gol");
                                scouts.add("Assistência");
                                scouts.add("Finalização");
                                scouts.add("Finalização Defendida");
                                scouts.add("Finalização Pra Fora");
                                scouts.add("Falta Sofrida");

                                if (_filtro != 0) {

                                    for (Atleta a : _mercado.getAtletas()) {
                                        if (a.getPosicaoId() == _filtro) {
                                            atletas.add(a);
                                            listDataScout.put(a, scouts);
                                        }
                                    }

                                    mercadoListAdapter = new MercadoListAdapter(MercadoActivity.this, atletas, listDataScout,_mercado.getClubes());
                                    expListView.setAdapter(mercadoListAdapter);
                                }

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<PartidaClube> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });



            }

            @Override
            public void onFailure(Call<Mercado> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
