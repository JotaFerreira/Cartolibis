package club.cartoleirosfutebol.cartomitos;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import club.cartoleirosfutebol.cartomitos.adapters.MercadoListAdapter;
import club.cartoleirosfutebol.cartomitos.api.APIConstraints;
import club.cartoleirosfutebol.cartomitos.api.AtletasAPI;
import club.cartoleirosfutebol.cartomitos.api.PartidasAPI;
import club.cartoleirosfutebol.cartomitos.data.Atleta;
import club.cartoleirosfutebol.cartomitos.data.Clube;
import club.cartoleirosfutebol.cartomitos.data.Mercado;
import club.cartoleirosfutebol.cartomitos.data.Partida;
import club.cartoleirosfutebol.cartomitos.data.PartidaClube;
import club.cartoleirosfutebol.cartomitos.data.Scout;
import club.cartoleirosfutebol.cartomitos.data.Status;
import club.cartoleirosfutebol.cartomitos.util.Helpers;
import club.cartoleirosfutebol.cartomitos.util.MercadoDeserializer;
import club.cartoleirosfutebol.cartomitos.util.PartidasDeserializer;
import dmax.dialog.SpotsDialog;
import info.hoang8f.android.segmented.SegmentedGroup;
//import me.drakeet.materialdialog.MaterialDialog;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MercadoActivity extends AppCompatActivity {

    private int _posicao;
    private int _escalacaoId;
    private final String TAG = "MercadoActivity";
    MercadoListAdapter mercadoListAdapter;
    HashMap<Atleta, List<String>> listDataScout;
    ExpandableListView expListView;
    Mercado _mercado;
    PartidaClube _partidas;
    SpotsDialog dialog;
    Intent _intentActivity;
    List<Atleta> _atletas;
    List<Atleta> _atletasTodos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercado);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                handleUncaughtException(thread, e);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        _intentActivity = getIntent();
        _posicao = _intentActivity.getIntExtra("posicao", 0);
        _escalacaoId = _intentActivity.getIntExtra("id", 0);
        toolbar.setTitle("Mercado");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dialog = new SpotsDialog(this, R.style.ProgressStyle);
        expListView = (ExpandableListView) findViewById(R.id.lvExpMercado);
        fetchData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                moveBack();
                return true;
            case R.id.action_refresh_mercado:
                fetchData();
                return true;

            case R.id.action_sort_mercado:
                new MaterialDialog.Builder(this)
                        .title("Classificar Por")
                        .customView(R.layout.list_sort_mercado, false)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                SegmentedGroup seg = (SegmentedGroup) dialog.findViewById(R.id.segmentedRadio);
                                RadioButton radio = (RadioButton) seg.findViewById(seg.getCheckedRadioButtonId());
                                if (radio != null) {
                                    sortMercadoResults(radio.getText().toString());
                                }
                                dialog.dismiss();
                            }
                        })
                        .positiveText("APLICAR")
                        .negativeText("CANCELAR")
                        .show();
                return true;

            case R.id.action_filter_mercado:

                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                final String labelTodosClubes = "Todos os Clubes";
                final String labelTodosStatus = "Todos os Status";

                final MaterialSpinner spinnerStatus = new MaterialSpinner(this);

                String statusJson = getResources().getString(R.string.status_json);
                final Type listStatusType = new TypeToken<ArrayList<Status>>() {
                }.getType();
                final List<Status> statusList = new Gson().fromJson(statusJson, listStatusType);

                final List<String> statusValues = new ArrayList<>();
                statusValues.add(labelTodosStatus);

                for (Status s : statusList) {
                    statusValues.add(s.getNome());
                }

                spinnerStatus.setItems(statusValues);

                final MaterialSpinner spinnerClubes = new MaterialSpinner(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(5,20,5,200);
                spinnerClubes.setLayoutParams(params);

                final List<String> clubesValues = new ArrayList<>();
                clubesValues.add(labelTodosClubes);

                for (Clube c : _mercado.getClubes().values()) {
                    clubesValues.add(c.getNome());
                }

                spinnerClubes.setItems(clubesValues);

                layout.addView(spinnerStatus);
                layout.addView(spinnerClubes);

                new MaterialDialog.Builder(this)
                        .title("Filtrar Por")
                        .customView(layout, false)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                String statusNome = statusValues.get(spinnerStatus.getSelectedIndex());
                                String clubeNome = clubesValues.get(spinnerClubes.getSelectedIndex());
                                int idStatus = 0;
                                int idClube = 0;

                                if (!statusNome.equals(labelTodosStatus)) {
                                    for (Status s : statusList) {
                                        if (s.getNome().equals(statusNome)) {
                                            idStatus = s.getId();
                                            break;
                                        }
                                    }
                                }

                                if (!clubeNome.equals(labelTodosClubes)) {
                                    for (Clube c : _mercado.getClubes().values()) {
                                        if (c.getNome().equals(clubeNome)) {
                                            idClube = c.getId();
                                            break;
                                        }
                                    }
                                }

                                filterMercadoResults(idClube, idStatus);

                            }
                        })
                        .positiveText("APLICAR")
                        .negativeText("CANCELAR")
                        .show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fetchData() {
        if (!Helpers.isConnected(this)) {
            noInternetAlert();
            return;
        }
        dialog.show();
        int cacheSize = 100;
        Cache cache = new Cache(getCacheDir(), cacheSize);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

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
                        .client(okHttpClient)
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
                                _atletas = new ArrayList<Atleta>();
                                _atletasTodos = new ArrayList<Atleta>();

                                if (_posicao != 0) {

                                    for (Atleta a : _mercado.getAtletas()) {
                                        HashMap<Atleta, List<String>> scoutsAtleta = new HashMap<Atleta, List<String>>();
                                        List<String> textosScout = new ArrayList<String>();

                                        if (a.getPosicaoId() == _posicao) {
                                            _atletasTodos.add(a);
                                            if (a.getStatusId() == 7 || a.getStatusId() == 2) {

                                                Scout scout = a.getScout();
                                                if (scout != null) {
                                                    if (scout.getA() != null) {
                                                        textosScout.add("<b>" + scout.getDesA() + ": </b>" + scout.getA());
                                                    }
                                                    if (scout.getFC() != null) {
                                                        textosScout.add("<b>" + scout.getDesFC() + ": </b>" + scout.getFC());
                                                    }
                                                    if (scout.getFD() != null) {
                                                        textosScout.add("<b>" + scout.getDesFD() + ": </b>" + scout.getFD());
                                                    }
                                                    if (scout.getFF() != null) {
                                                        textosScout.add("<b>" + scout.getDesFF() + ": </b>" + scout.getFF());
                                                    }
                                                    if (scout.getFS() != null) {
                                                        textosScout.add("<b>" + scout.getDesFS() + ": </b>" + scout.getFS());
                                                    }
                                                    if (scout.getI() != null) {
                                                        textosScout.add("<b>" + scout.getDesI() + ": </b>" + scout.getI());
                                                    }
                                                    if (scout.getPE() != null) {
                                                        textosScout.add("<b>" + scout.getDesPE() + ": </b>" + scout.getPE());
                                                    }
                                                    if (scout.getRB() != null) {
                                                        textosScout.add("<b>" + scout.getDesRB() + ": </b>" + scout.getRB());
                                                    }
                                                    if (scout.getSG() != null) {
                                                        textosScout.add("<b>" + scout.getDesSG() + ": </b>" + scout.getSG());
                                                    }
                                                    if (scout.getCA() != null) {
                                                        textosScout.add("<b>" + scout.getDesCA() + ": </b>" + scout.getCA());
                                                    }
                                                    if (scout.getFT() != null) {
                                                        textosScout.add("<b>" + scout.getDesFT() + ":</b> " + scout.getFT());
                                                    }
                                                    if (scout.getG() != null) {
                                                        textosScout.add("<b>" + scout.getDesG() + ": </b>" + scout.getG());
                                                    }
                                                    if (scout.getCV() != null) {
                                                        textosScout.add("<b>" + scout.getDesCV() + ": </b>" + scout.getCV());
                                                    }
                                                    if (scout.getDD() != null) {
                                                        textosScout.add("<b>" + scout.getDesDD() + ": </b>" + scout.getDD());
                                                    }
                                                    if (scout.getGS() != null) {
                                                        textosScout.add("<b>" + scout.getDesGS() + ": </b>" + scout.getGS());
                                                    }
                                                    if (scout.getPP() != null) {
                                                        textosScout.add("<b>" + scout.getDesPP() + ": </b>" + scout.getPP());
                                                    }
                                                    if (scout.getDP() != null) {
                                                        textosScout.add("<b>" + scout.getDesDP() + ": </b>" + scout.getDP());
                                                    }
                                                    if (scout.getGC() != null) {
                                                        textosScout.add("<b>" + scout.getDesGC() + ": </b>" + scout.getGC());
                                                    }
                                                }

                                                if (textosScout.size() == 0) {
                                                    textosScout.add("<b>Sem informações para exibir</b>");
                                                }

                                                _atletas.add(a);
                                                listDataScout.put(a, textosScout);
                                            }
                                        }
                                    }
                                    mercadoListAdapter = new MercadoListAdapter(MercadoActivity.this, _atletas, listDataScout,
                                            _mercado.getClubes(), _partidas, _escalacaoId);
                                    expListView.setAdapter(mercadoListAdapter);
                                    dialog.dismiss();
                                }

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<PartidaClube> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(MercadoActivity.this, t.getMessage(), Toast.LENGTH_LONG);
                        Log.e(TAG, t.getMessage());
                    }
                });

            }

            @Override
            public void onFailure(Call<Mercado> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(MercadoActivity.this, t.getMessage(), Toast.LENGTH_LONG);
                Log.e(TAG, t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mercado, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        moveBack();
    }

    public void handleUncaughtException(Thread thread, Throwable e) {
        Drawable d = getResources().getDrawable(R.drawable.dialog_fausto_errou);
        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(this)
                .setImageDrawable(d)
                .setTextTitle("Deu ruim!")
                .setTextSubTitle("Ocorreu um erro inesperado!")
                .setBody("Acontece nos melhores apps, zé gotinha, O Desenvolvedor irá trabalhar nisso!")
                .setNegativeColor(R.color.colorRedDark)
                .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButtonText("TA CERTO")
                .setPositiveColor(R.color.colorPrimary)
                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        Intent intent = new Intent(MercadoActivity.this, EscalacaoActivity.class);
                        startActivity(intent);
                    }
                })
                .build();
        alert.show();
    }

    private void noInternetAlert() {
        Drawable d = getResources().getDrawable(R.drawable.dialog_fausto_errou);
        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(this)
                .setImageDrawable(d)
                .setTextTitle("Deu ruim!")
                .setTextSubTitle("Sem conexão com a internet")
                .setBody("Sem internet não vai funcionar esse bagulho")
                .setNegativeColor(R.color.colorRedDark)
                .setPositiveButtonText("TENTAR NOVAMENTE!")
                .setPositiveColor(R.color.colorPrimary)
                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();
                        fetchData();
                    }
                })
                .build();
        alert.show();
    }

    private void moveBack() {
        finish();
        Intent intent = new Intent(this, EscalacaoActivity.class);
        startActivity(intent);
    }

    private void sortMercadoResults(String item) {
        dialog.show();
        if (_atletas != null) {
            switch (item.toLowerCase()) {
                case "nome":
                    Collections.sort(_atletas, new Comparator<Atleta>() {
                        @Override
                        public int compare(Atleta o1, Atleta o2) {
                            return o1.getApelido().compareTo(o2.getApelido());
                        }
                    });
                    break;
                case "preço":
                    Collections.sort(_atletas, new Comparator<Atleta>() {
                        @Override
                        public int compare(Atleta o1, Atleta o2) {
                            return o2.getPrecoNum().compareTo(o1.getPrecoNum());
                        }
                    });
                    break;
                case "média":
                    Collections.sort(_atletas, new Comparator<Atleta>() {
                        @Override
                        public int compare(Atleta o1, Atleta o2) {
                            return o2.getMediaNum().compareTo(o1.getMediaNum());
                        }
                    });
                    break;
                case "valorização":
                    Collections.sort(_atletas, new Comparator<Atleta>() {
                        @Override
                        public int compare(Atleta o1, Atleta o2) {
                            return o2.getVariacaoNum().compareTo(o1.getVariacaoNum());
                        }
                    });
                    break;
                case "desvalorização":
                    Collections.sort(_atletas, new Comparator<Atleta>() {
                        @Override
                        public int compare(Atleta o1, Atleta o2) {
                            return o1.getVariacaoNum().compareTo(o2.getVariacaoNum());
                        }
                    });
                    break;
                case "pontuação":
                    Collections.sort(_atletas, new Comparator<Atleta>() {
                        @Override
                        public int compare(Atleta o1, Atleta o2) {
                            return o2.getPontosNum().compareTo(o1.getPontosNum());
                        }
                    });
                    break;
            }
            mercadoListAdapter = new MercadoListAdapter(MercadoActivity.this, _atletas, listDataScout,
                    _mercado.getClubes(), _partidas, _escalacaoId);
            expListView.setAdapter(mercadoListAdapter);

        }
        dialog.dismiss();
    }

    private void filterMercadoResults(int clubeId, int statusId) {

        if (_atletasTodos != null) {

            List<Atleta> filterAtletas = new ArrayList<Atleta>();

            for (Atleta a : _atletasTodos) {

                if (clubeId > 0 && statusId > 0) {
                    if (a.getClubeId().intValue() == clubeId && a.getStatusId() == statusId) { // se um clube especifico e um status especifico
                        filterAtletas.add(a);
                    }
                } else if (clubeId == 0 && statusId > 0) { // se todos os clubes e status especifico
                    if (a.getStatusId() == statusId) {
                        filterAtletas.add(a);
                    }
                } else if (clubeId > 0 && statusId == 0) {
                    if (a.getClubeId().intValue() == clubeId) { // se todos os status e um clube especifico
                        filterAtletas.add(a);
                    }
                } else {
                    carregarMercadoCompleto();
                    return;
                }
            }
            _atletas = filterAtletas;
            mercadoListAdapter = new MercadoListAdapter(MercadoActivity.this, filterAtletas, listDataScout,
                    _mercado.getClubes(), _partidas, _escalacaoId);
            expListView.setAdapter(mercadoListAdapter);
        }

    }

    private void carregarMercadoCompleto() {
        _atletas = _atletasTodos;
        mercadoListAdapter = new MercadoListAdapter(MercadoActivity.this, _atletasTodos, listDataScout,
                _mercado.getClubes(), _partidas, _escalacaoId);
        expListView.setAdapter(mercadoListAdapter);
    }

}
