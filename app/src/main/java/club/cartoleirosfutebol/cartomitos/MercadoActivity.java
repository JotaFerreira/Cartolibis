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
import club.cartoleirosfutebol.cartomitos.data.Atleta;
import club.cartoleirosfutebol.cartomitos.data.Mercado;
import club.cartoleirosfutebol.cartomitos.util.MercadoDeserializer;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent = getIntent();
        _filtro = intent.getIntExtra("filtro", 0);
        toolbar.setTitle("Mercado - " + _filtro);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        expListView = (ExpandableListView) findViewById(R.id.lvExpMercado);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new GsonBuilder().registerTypeAdapter(Mercado.class, new MercadoDeserializer()).create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(APIConstraints.API_CARTOLA_BASE)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                AtletasAPI atletasAPI = retrofit.create(AtletasAPI.class);
                Call<Mercado> call = atletasAPI.getMercado();

                call.enqueue(new Callback<Mercado>() {
                    @Override
                    public void onResponse(Call<Mercado> call, Response<Mercado> response) {

                        Mercado mercado = response.body();

                        if (mercado != null) {

                            if (mercado.getAtletas() != null) {

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

                                    for (Atleta a : mercado.getAtletas()) {
                                        if (a.getPosicaoId() == _filtro) {
                                            atletas.add(a);
                                            listDataScout.put(a, scouts);
                                        }
                                    }

                                    mercadoListAdapter = new MercadoListAdapter(MercadoActivity.this, atletas, listDataScout);
                                    expListView.setAdapter(mercadoListAdapter);
                                }

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<Mercado> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });
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
