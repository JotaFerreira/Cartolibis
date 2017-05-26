package club.cartoleirosfutebol.cartomitos;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

public class EsquemaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esquema);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final ImageView imageEsquema = (ImageView) findViewById(R.id.imageEsquema);
        MaterialSpinner esquemasSpinner = (MaterialSpinner) findViewById(R.id.spinner);
        final String[] esquemas = getResources().getStringArray(R.array.esquemas);
        esquemasSpinner.setItems(esquemas);

        esquemasSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
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
                Helpers.PutSharedPreference(EsquemaActivity.this,"user_scheme",item);
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


}
