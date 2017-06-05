package club.cartoleirosfutebol.cartomitos;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.Type;

import club.cartoleirosfutebol.cartomitos.adapters.EscalacaoListAdapter;
import club.cartoleirosfutebol.cartomitos.adapters.MercadoListAdapter;
import club.cartoleirosfutebol.cartomitos.api.APIConstraints;
import club.cartoleirosfutebol.cartomitos.api.AtletasAPI;
import club.cartoleirosfutebol.cartomitos.api.PartidasAPI;
import club.cartoleirosfutebol.cartomitos.data.Atleta;
import club.cartoleirosfutebol.cartomitos.data.Esquema;
import club.cartoleirosfutebol.cartomitos.data.JogadorItem;
import club.cartoleirosfutebol.cartomitos.data.Mercado;
import club.cartoleirosfutebol.cartomitos.data.Partida;
import club.cartoleirosfutebol.cartomitos.data.PartidaClube;
import club.cartoleirosfutebol.cartomitos.data.Scout;
import club.cartoleirosfutebol.cartomitos.util.Helpers;
import club.cartoleirosfutebol.cartomitos.util.MercadoDeserializer;
import club.cartoleirosfutebol.cartomitos.util.PartidasDeserializer;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EscalacaoActivity extends AppCompatActivity {

    EscalacaoListAdapter listAdapter;
    ExpandableListView expListView;
    List<Atleta> listDataHeader;
    HashMap<Atleta, List<String>> listDataChild;
    List<Esquema> esquemas;
    Esquema esquemaUser;
    String esquemaIntent;
    private final String TAG = "EscalacaoActivity";
    SpotsDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escalacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        // preparing list data
        esquemaIntent = Helpers.GetStringSharedPreference(this, "user_scheme");
        toolbar.setTitle("Escalação - (" + esquemaIntent + ")");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        prepareListData(esquemaIntent);

        listAdapter = new EscalacaoListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                moveBack();
                return true;
            case R.id.action_delete:
                Drawable d = getResources().getDrawable(R.drawable.dialog_erro);
                FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(this)
                        .setImageDrawable(d)
                        .setTextTitle("Tem certeza?")
                        .setTextSubTitle("Deletar todo o time")
                        .setBody("Tem certeza que deseja remover todos os jogadores do time?")
                        .setNegativeColor(R.color.colorRedDark)
                        .setNegativeButtonText("Não, cliquei errado!")
                        .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButtonText("CLARO!")
                        .setPositiveColor(R.color.colorPrimary)
                        .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
                                Toast.makeText(EscalacaoActivity.this, "Updating", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build();
                alert.show();
                return true;
            case R.id.action_esquema:
                Intent intent = new Intent(this, EsquemaActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void prepareListData(String esquema) {

        listDataHeader = new ArrayList<Atleta>();
        listDataChild = new HashMap<Atleta, List<String>>();
        List<String> textoScoutVazio = new ArrayList<String>();
        textoScoutVazio.add("<b>Sem informações para exibir</b>");

        String esquemasJson = getResources().getString(R.string.esquemas_json);
        Type listEsquemaType = new TypeToken<ArrayList<Esquema>>() {
        }.getType();
        esquemas = new Gson().fromJson(esquemasJson, listEsquemaType);
        Esquema esquemaPosicao = new Esquema();

        for (Esquema e : esquemas) {
            if (e.getNome().equals(esquema)) {
                esquemaPosicao = e;
                break;
            }
        }

        if (esquemaPosicao != null) {

            int laterais = esquemaPosicao.getPosicoes().getLat();
            int zagueiros = esquemaPosicao.getPosicoes().getZag();
            int meias = esquemaPosicao.getPosicoes().getMei();
            int atacantes = esquemaPosicao.getPosicoes().getAta();

            String jsonGol = Helpers.GetStringSharedPreference(this, "j0_1");

            if (jsonGol != null && !jsonGol.equals("")) {
                try {
                    Atleta goleiro = new Gson().fromJson(jsonGol, Atleta.class);
                    if (goleiro != null) {
                        listDataHeader.add(goleiro);
                        listDataChild.put(goleiro, getScoutValues(goleiro.getScout()));
                    } else {
                        Helpers.PutSharedPreference(this, "j0_1", "");
                        Atleta jGol = new Atleta();
                        jGol.setNome("Jogador");
                        jGol.setPosicaoId(1);
                        listDataHeader.add(jGol);
                        listDataChild.put(jGol, textoScoutVazio);
                    }
                } catch (Exception e) {
                    Helpers.PutSharedPreference(this, "j0_1", "");
                    Atleta jGol = new Atleta();
                    jGol.setNome("Jogador");
                    jGol.setPosicaoId(1);
                    listDataHeader.add(jGol);
                    listDataChild.put(jGol, textoScoutVazio);
                }
            } else {
                Helpers.PutSharedPreference(this, "j0_1", "");
                Atleta jGol = new Atleta();
                jGol.setNome("Jogador");
                jGol.setPosicaoId(1);
                listDataHeader.add(jGol);
                listDataChild.put(jGol, textoScoutVazio);
            }

            for (int i = 0; i < zagueiros; i++) {
                int posicaoId = 3;
                String chave = "j" + listDataHeader.size() + "_" + posicaoId;
                String json = Helpers.GetStringSharedPreference(this, chave);
                if (json != null && !json.equals("")) {
                    try {
                        Atleta atleta = new Gson().fromJson(json, Atleta.class);
                        if (atleta != null) {
                            listDataHeader.add(atleta);
                            listDataChild.put(atleta, getScoutValues(atleta.getScout()));
                        } else {
                            Helpers.PutSharedPreference(this, chave, "");
                            Atleta jog = new Atleta();
                            jog.setNome("Jogador");
                            jog.setPosicaoId(posicaoId);
                            listDataHeader.add(jog);
                            listDataChild.put(jog, textoScoutVazio);
                        }
                    } catch (Exception e) {
                        Helpers.PutSharedPreference(this, chave, "");
                        Atleta jog = new Atleta();
                        jog.setNome("Jogador");
                        jog.setPosicaoId(posicaoId);
                        listDataHeader.add(jog);
                        listDataChild.put(jog, textoScoutVazio);
                    }
                } else {
                    Helpers.PutSharedPreference(this, chave, "");
                    Atleta j = new Atleta();
                    j.setNome("Jogador");
                    j.setPosicaoId(posicaoId);
                    listDataHeader.add(j);
                    listDataChild.put(j, textoScoutVazio);
                }
            }

            for (int i = 0; i < laterais; i++) {
                int posicaoId = 2;
                String chave = "j" + listDataHeader.size() + "_" + posicaoId;
                String json = Helpers.GetStringSharedPreference(this, chave);
                if (json != null && !json.equals("")) {
                    try {
                        Atleta atleta = new Gson().fromJson(json, Atleta.class);
                        if (atleta != null) {
                            listDataHeader.add(atleta);
                            listDataChild.put(atleta, getScoutValues(atleta.getScout()));
                        } else {
                            Helpers.PutSharedPreference(this, chave, "");
                            Atleta j = new Atleta();
                            j.setNome("Jogador");
                            j.setPosicaoId(posicaoId);
                            listDataHeader.add(j);
                            listDataChild.put(j, textoScoutVazio);
                        }
                    } catch (Exception e) {
                        Helpers.PutSharedPreference(this, chave, "");
                        Atleta jog = new Atleta();
                        jog.setNome("Jogador");
                        jog.setPosicaoId(posicaoId);
                        listDataHeader.add(jog);
                        listDataChild.put(jog, textoScoutVazio);
                    }
                } else {
                    Helpers.PutSharedPreference(this, chave, "");
                    Atleta j = new Atleta();
                    j.setNome("Jogador");
                    j.setPosicaoId(posicaoId);
                    listDataHeader.add(j);
                    listDataChild.put(j, textoScoutVazio);
                }
            }

            for (int i = 0; i < meias; i++) {
                int posicaoId = 4;
                String chave = "j" + listDataHeader.size() + "_" + posicaoId;
                String json = Helpers.GetStringSharedPreference(this, chave);
                if (json != null && !json.equals("")) {
                    try {
                        Atleta atleta = new Gson().fromJson(json, Atleta.class);
                        if (atleta != null) {
                            listDataHeader.add(atleta);
                            listDataChild.put(atleta, getScoutValues(atleta.getScout()));
                        } else {
                            Helpers.PutSharedPreference(this, chave, "");
                            Atleta jog = new Atleta();
                            jog.setNome("Jogador");
                            jog.setPosicaoId(posicaoId);
                            listDataHeader.add(jog);
                            listDataChild.put(jog, textoScoutVazio);
                        }
                    } catch (Exception e) {
                        Helpers.PutSharedPreference(this, chave, "");
                        Atleta jog = new Atleta();
                        jog.setNome("Jogador");
                        jog.setPosicaoId(posicaoId);
                        listDataHeader.add(jog);
                        listDataChild.put(jog, textoScoutVazio);
                    }
                } else {
                    Helpers.PutSharedPreference(this, chave, "");
                    Atleta j = new Atleta();
                    j.setNome("Jogador");
                    j.setPosicaoId(posicaoId);
                    listDataHeader.add(j);
                    listDataChild.put(j, textoScoutVazio);
                }
            }

            for (int i = 0; i < atacantes; i++) {
                int posicaoId = 5;
                String chave = "j" + listDataHeader.size() + "_" + posicaoId;
                String json = Helpers.GetStringSharedPreference(this, chave);
                if (json != null && !json.equals("")) {
                    try {
                        Atleta atleta = new Gson().fromJson(json, Atleta.class);
                        if (atleta != null) {
                            listDataHeader.add(atleta);
                            listDataChild.put(atleta, getScoutValues(atleta.getScout()));
                        } else {
                            Helpers.PutSharedPreference(this, chave, "");
                            Atleta j = new Atleta();
                            j.setNome("Jogador");
                            j.setPosicaoId(posicaoId);
                            listDataHeader.add(j);
                            listDataChild.put(j, textoScoutVazio);
                        }
                    } catch (Exception e) {
                        Helpers.PutSharedPreference(this, chave, "");
                        Atleta jog = new Atleta();
                        jog.setNome("Jogador");
                        jog.setPosicaoId(posicaoId);
                        listDataHeader.add(jog);
                        listDataChild.put(jog, textoScoutVazio);
                    }
                } else {
                    Helpers.PutSharedPreference(this, chave, "");
                    Atleta j = new Atleta();
                    j.setNome("Jogador");
                    j.setPosicaoId(posicaoId);
                    listDataHeader.add(j);
                    listDataChild.put(j, textoScoutVazio);
                }
            }

            int posicaoIdTecnico = 6;
            String chaveTec = "j" + listDataHeader.size() + "_" + posicaoIdTecnico;
            String jsonTec = Helpers.GetStringSharedPreference(this, chaveTec);

            if (jsonTec != null && !jsonTec.equals("")) {
                try {
                    Atleta tecnico = new Gson().fromJson(jsonTec, Atleta.class);
                    if (tecnico != null) {
                        listDataHeader.add(tecnico);
                        listDataChild.put(tecnico, getScoutValues(tecnico.getScout()));
                    } else {
                        Helpers.PutSharedPreference(this, chaveTec, "");
                        Atleta jTec = new Atleta();
                        jTec.setNome("Técnico");
                        jTec.setPosicaoId(posicaoIdTecnico);
                        listDataHeader.add(jTec);
                        listDataChild.put(jTec, textoScoutVazio);
                    }
                } catch (Exception e) {
                    Helpers.PutSharedPreference(this, chaveTec, "");
                    Atleta jTec = new Atleta();
                    jTec.setNome("Técnico");
                    jTec.setPosicaoId(posicaoIdTecnico);
                    listDataHeader.add(jTec);
                    listDataChild.put(jTec, textoScoutVazio);
                }
            } else {
                Helpers.PutSharedPreference(this, chaveTec, "");
                Atleta jTec = new Atleta();
                jTec.setNome("Técnico");
                jTec.setPosicaoId(posicaoIdTecnico);
                listDataHeader.add(jTec);
                listDataChild.put(jTec, textoScoutVazio);
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.escalacao, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        moveBack();
    }

    private void moveBack() {
        finish();
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    private List<String> getScoutValues(Scout scout) {
        List<String> textosScout = new ArrayList<String>();
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
        return textosScout;
    }
}
