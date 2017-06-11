package club.cartoleirosfutebol.cartomitos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.jaredrummler.materialspinner.MaterialSpinner;

import club.cartoleirosfutebol.cartomitos.util.Helpers;

public class EsquemaActivity extends AppCompatActivity {

    ImageView imageEsquema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esquema);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imageEsquema = (ImageView) findViewById(R.id.imageEsquema);
        MaterialSpinner esquemasSpinner = (MaterialSpinner) findViewById(R.id.spinner);
        final String[] esquemas = getResources().getStringArray(R.array.esquemas);
        esquemasSpinner.setItems(esquemas);
        String userScheme = Helpers.getStringSharedPreference(this, "user_scheme");
        int indexSelectedEsquema = 0;

        if (userScheme == null || userScheme.equals("")) {
            Helpers.putSharedPreference(EsquemaActivity.this, "user_scheme", "3-4-3"); // esquema padrão
        } else {
            for (int i = 0; i < esquemas.length; i++) {
                if (esquemas[i].equals(userScheme)) {
                    indexSelectedEsquema = i;
                    break;
                }
            }
            esquemasSpinner.setSelectedIndex(indexSelectedEsquema);
            updateImageScheme(userScheme);
        }

        esquemasSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                updateImageScheme(item);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EsquemaActivity.this, EscalacaoActivity.class);
                startActivity(intent);
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

    private void updateImageScheme(String item) {
        switch (item) {
            case "3-4-3":
                imageEsquema.setImageResource(R.drawable.e343);
                break;
            case "3-5-2":
                imageEsquema.setImageResource(R.drawable.e352);
                break;
            case "4-3-3":
                imageEsquema.setImageResource(R.drawable.e433);
                break;
            case "4-4-2":
                imageEsquema.setImageResource(R.drawable.e442);
                break;
            case "4-5-1":
                imageEsquema.setImageResource(R.drawable.e451);
                break;
            case "5-3-2":
                imageEsquema.setImageResource(R.drawable.e532);
                break;
            case "5-4-1":
                imageEsquema.setImageResource(R.drawable.e541);
                break;
        }
        Helpers.putSharedPreference(EsquemaActivity.this, "user_scheme", item);
    }


}
