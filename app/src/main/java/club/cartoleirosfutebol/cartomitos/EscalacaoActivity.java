package club.cartoleirosfutebol.cartomitos;

import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import club.cartoleirosfutebol.cartomitos.Adapters.ExpandableListAdapter;

public class EscalacaoActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escalacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

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

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Drogba");
        listDataHeader.add("Estádio do Vasco");
        listDataHeader.add("R. Gaúcho");
        listDataHeader.add("Borja");
        listDataHeader.add("Jô");
        listDataHeader.add("Ricardo Oliveira");
        listDataHeader.add("Pratto");

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
    }

}
