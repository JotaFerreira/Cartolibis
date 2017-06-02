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
import android.widget.ProgressBar;
import android.widget.Toast;

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
import club.cartoleirosfutebol.cartomitos.data.Scout;
import club.cartoleirosfutebol.cartomitos.util.MercadoDeserializer;
import club.cartoleirosfutebol.cartomitos.util.PartidasDeserializer;
import dmax.dialog.SpotsDialog;
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
    SpotsDialog dialog;

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
        dialog = new SpotsDialog(this, R.style.ProgressStyle);
        expListView = (ExpandableListView) findViewById(R.id.lvExpMercado);

    }

    @Override
    public void onResume() {
        super.onResume();
        dialog.show();
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

                                if (_filtro != 0) {

                                    for (Atleta a : _mercado.getAtletas()) {
                                        HashMap<Atleta, List<String>> scoutsAtleta = new HashMap<Atleta, List<String>>();
                                        List<String> textosScout = new ArrayList<String>();

                                        if (a.getPosicaoId() == _filtro) {
                                            Scout scout = a.getScout();
                                            if(scout != null){
                                                if(scout.getA() != null){
                                                    textosScout.add("<b>" + scout.getDesA() + " : </b>" + scout.getA());
                                                }
                                                if(scout.getFC() != null){
                                                    textosScout.add("<b>" + scout.getDesFC() + " : </b>" + scout.getFC());
                                                }
                                                if(scout.getFD() != null){
                                                    textosScout.add("<b>" + scout.getDesFD() + " : </b>" + scout.getFD());
                                                }
                                                if(scout.getFF() != null){
                                                    textosScout.add("<b>" + scout.getDesFF() + " : </b>" + scout.getFF());
                                                }
                                                if(scout.getFS() != null){
                                                    textosScout.add("<b>" + scout.getDesFS() + " : </b>" + scout.getFS());
                                                }
                                                if(scout.getI() != null){
                                                    textosScout.add("<b>" + scout.getDesI() + " : </b>" + scout.getI());
                                                }
                                                if(scout.getPE() != null){
                                                    textosScout.add("<b>" + scout.getDesPE() + " : </b>" + scout.getPE());
                                                }
                                                if(scout.getRB() != null){
                                                    textosScout.add("<b>" + scout.getDesRB() + " : </b>" + scout.getRB());
                                                }
                                                if(scout.getSG() != null){
                                                    textosScout.add("<b>" + scout.getDesSG() + " : </b>" + scout.getSG());
                                                }
                                                if(scout.getCA() != null){
                                                    textosScout.add("<b>" + scout.getDesCA() + " : </b>" + scout.getCA());
                                                }
                                                if(scout.getFT() != null){
                                                    textosScout.add("<b>" + scout.getDesFT() + " :</b> " + scout.getFT());
                                                }
                                                if(scout.getG() != null){
                                                    textosScout.add("<b>" + scout.getDesG() + " : </b>" + scout.getG());
                                                }
                                                if(scout.getCV() != null){
                                                    textosScout.add("<b>" + scout.getDesCV() + " : </b>" + scout.getCV());
                                                }
                                                if(scout.getDD() != null){
                                                    textosScout.add("<b>" + scout.getDesDD() + " : </b>" + scout.getDD());
                                                }
                                                if(scout.getGS() != null){
                                                    textosScout.add("<b>" + scout.getDesGS() + " : </b>" + scout.getGS());
                                                }
                                                if(scout.getPP() != null){
                                                    textosScout.add("<b>" + scout.getDesPP() + " : </b>" + scout.getPP());
                                                }
                                                if(scout.getDP() != null){
                                                    textosScout.add("<b>" + scout.getDesDP() + " : </b>" + scout.getDP());
                                                }
                                                if(scout.getGC() != null){
                                                    textosScout.add("<b>" + scout.getDesGC() + " : </b>" + scout.getGC());
                                                }
                                            }

                                            if(textosScout.size() == 0){
                                                textosScout.add("<b>Sem informações para exibir</b>");
                                            }

                                            atletas.add(a);
                                            listDataScout.put(a, textosScout);
                                        }
                                    }

                                    mercadoListAdapter = new MercadoListAdapter(MercadoActivity.this, atletas, listDataScout, _mercado.getClubes(), _partidas);
                                    expListView.setAdapter(mercadoListAdapter);
                                    dialog.dismiss();
                                }

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<PartidaClube> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(MercadoActivity.this,t.getMessage(),Toast.LENGTH_LONG);
                        Log.e(TAG, t.getMessage());
                    }
                });

            }

            @Override
            public void onFailure(Call<Mercado> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(MercadoActivity.this,t.getMessage(),Toast.LENGTH_LONG);
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
