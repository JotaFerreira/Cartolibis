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

import club.cartoleirosfutebol.cartomitos.adapters.ExpandableListAdapter;
import club.cartoleirosfutebol.cartomitos.data.Esquema;
import club.cartoleirosfutebol.cartomitos.data.JogadorItem;

public class EscalacaoActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<JogadorItem> listDataHeader;
    HashMap<JogadorItem, List<String>> listDataChild;
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

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

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

        listDataHeader = new ArrayList<JogadorItem>();
        listDataChild = new HashMap<JogadorItem, List<String>>();

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

            JogadorItem jGol = new JogadorItem();
            jGol.setNome("Jogador");
            jGol.setPosicao("Goleiro");
            listDataHeader.add(jGol);

            for(int i = 0; i < zagueiros; i++){
                JogadorItem j = new JogadorItem();
                j.setNome("Jogador");
                j.setPosicao("Zagueiro");
                listDataHeader.add(j);
            }

            for(int i = 0; i < laterais; i++){
                JogadorItem j = new JogadorItem();
                j.setNome("Jogador");
                j.setPosicao("Lateral");
                listDataHeader.add(j);
            }

            for(int i = 0; i < meias; i++){
                JogadorItem j = new JogadorItem();
                j.setNome("Jogador");
                j.setPosicao("Meia");
                listDataHeader.add(j);
            }

            for(int i = 0; i < atacantes; i++){
                JogadorItem j = new JogadorItem();
                j.setNome("Jogador");
                j.setPosicao("Atacante");
                listDataHeader.add(j);
            }

            JogadorItem jTec = new JogadorItem();
            jTec.setNome("Jogador");
            jTec.setPosicao("Técnico");
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
