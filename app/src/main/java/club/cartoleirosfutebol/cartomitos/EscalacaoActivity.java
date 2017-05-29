package club.cartoleirosfutebol.cartomitos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.Type;

import club.cartoleirosfutebol.cartomitos.adapters.EscalacaoListAdapter;
import club.cartoleirosfutebol.cartomitos.data.Atleta;
import club.cartoleirosfutebol.cartomitos.data.Esquema;
import club.cartoleirosfutebol.cartomitos.data.JogadorItem;

public class EscalacaoActivity extends AppCompatActivity {

    EscalacaoListAdapter listAdapter;
    ExpandableListView expListView;
    List<Atleta> listDataHeader;
    HashMap<Atleta, List<String>> listDataChild;
    List<Esquema> esquemas;
    Esquema esquemaUser;
    String esquemaIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escalacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        Intent intent = getIntent();
        // preparing list data
        esquemaIntent = intent.getStringExtra("esquema_user");
        prepareListData(esquemaIntent);

        listAdapter = new EscalacaoListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                Log.d("onGroupClick:","ops");
                 /*Toast.makeText(getApplicationContext(),
                 "Group Clicked " + listDataHeader.get(groupPosition),
                 Toast.LENGTH_SHORT).show();*/
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Log.d("onGroupExpand:","ops");
                /*Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Log.d("onGroupCollapse:","ops");
                /*Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();*/

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                /*Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();*/
                return false;
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

    private void prepareListData(String esquema) {

        listDataHeader = new ArrayList<Atleta>();
        listDataChild = new HashMap<Atleta, List<String>>();

        String esquemasJson = getResources().getString(R.string.esquemas_json);
        Type listEsquemaType = new TypeToken<ArrayList<Esquema>>(){}.getType();
        esquemas = new Gson().fromJson(esquemasJson,listEsquemaType);
        Esquema esquemaPosicao = new Esquema();

        for(Esquema e : esquemas){
            if(e.getNome().equals(esquema)){
                esquemaPosicao = e;
            }
        }

        if(esquemaPosicao != null){
            int laterais = esquemaPosicao.getPosicoes().getLat();
            int zagueiros = esquemaPosicao.getPosicoes().getZag();
            int meias = esquemaPosicao.getPosicoes().getMei();
            int atacantes = esquemaPosicao.getPosicoes().getAta();

            Atleta jGol = new Atleta();
            jGol.setNome("Jogador");
            jGol.setPosicaoId(1);
            listDataHeader.add(jGol);

            for(int i = 0; i < zagueiros; i++){
                Atleta j = new Atleta();
                j.setNome("Jogador");
                j.setPosicaoId(3);
                listDataHeader.add(j);
            }

            for(int i = 0; i < laterais; i++){
                Atleta j = new Atleta();
                j.setNome("Jogador");
                j.setPosicaoId(2);
                listDataHeader.add(j);
            }

            for(int i = 0; i < meias; i++){
                Atleta j = new Atleta();
                j.setNome("Jogador");
                j.setPosicaoId(4);
                listDataHeader.add(j);
            }

            for(int i = 0; i < atacantes; i++){
                Atleta j = new Atleta();
                j.setNome("Jogador");
                j.setPosicaoId(5);
                listDataHeader.add(j);
            }

            Atleta jTec = new Atleta();
            jTec.setNome("Técnico");
            jTec.setPosicaoId(6);
            listDataHeader.add(jTec);

        }

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("Gol");
        top250.add("Assistência");
        top250.add("Finalização");
        top250.add("Finalização Defendida");
        top250.add("Finalização Pra Fora");
        top250.add("Falta Sofrida");

        listDataChild.put(listDataHeader.get(0), top250);
        listDataChild.put(listDataHeader.get(1), top250);
        listDataChild.put(listDataHeader.get(2), top250);
        listDataChild.put(listDataHeader.get(3), top250);
        listDataChild.put(listDataHeader.get(4), top250);
        listDataChild.put(listDataHeader.get(5), top250);
        listDataChild.put(listDataHeader.get(6), top250);
        listDataChild.put(listDataHeader.get(7), top250);
        listDataChild.put(listDataHeader.get(8), top250);
        listDataChild.put(listDataHeader.get(9), top250);
        listDataChild.put(listDataHeader.get(10), top250);
        listDataChild.put(listDataHeader.get(11), top250);
    }

}
