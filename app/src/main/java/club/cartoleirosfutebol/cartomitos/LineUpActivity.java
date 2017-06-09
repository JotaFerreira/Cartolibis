package club.cartoleirosfutebol.cartomitos;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import club.cartoleirosfutebol.cartomitos.data.Atleta;
import club.cartoleirosfutebol.cartomitos.data.AtletaLineUp;
import club.cartoleirosfutebol.cartomitos.data.Esquema;
import club.cartoleirosfutebol.cartomitos.util.Helpers;

public class LineUpActivity extends AppCompatActivity {

    List<Esquema> _esquemas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buildLineUp();
    }

    private void buildLineUp() {

        String esquema = Helpers.GetStringSharedPreference(this, "user_scheme");

        if (esquema != null && !esquema.equals("")) {

            String esquemasJson = getResources().getString(R.string.esquemas_json);
            Type listEsquemaType = new TypeToken<ArrayList<Esquema>>() {
            }.getType();
            _esquemas = new Gson().fromJson(esquemasJson, listEsquemaType);
            Esquema esquemaPosicao = new Esquema();

            for (Esquema e : _esquemas) {
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
                String jsonTec = Helpers.GetStringSharedPreference(this, "j11_6");

                int qtdJogadores = 0;

                if (jsonGol != null && !jsonGol.equals("")) {
                    ImageView imgAtleta = (ImageView) findViewById(R.id.gol1);
                    TextView txtAtleta = (TextView) findViewById(R.id.nmGo1);
                    loadPlayer(jsonGol, imgAtleta, txtAtleta, "Goleiro");
                    qtdJogadores++;
                }

                for (int i = 1; i < zagueiros + 1; i++) {
                    int posicaoId = 3;
                    String chave = "j" + qtdJogadores + "_" + posicaoId;
                    String json = Helpers.GetStringSharedPreference(this, chave);

                    switch (i) {
                        case 1:
                            if (json != null && !json.equals("")) {
                                ImageView img = (ImageView) findViewById(R.id.zag1);
                                TextView txt = (TextView) findViewById(R.id.nmZag1);
                                loadPlayer(json, img, txt, "Zagueiro");
                            } else {
                                ImageView img = (ImageView) findViewById(R.id.zag1);
                                TextView txt = (TextView) findViewById(R.id.nmZag1);
                                loadDefaultPlayer(img, txt, "Zagueiro");
                            }
                            break;
                        case 2:
                            if (json != null && !json.equals("")) {
                                ImageView img = (ImageView) findViewById(R.id.zag2);
                                TextView txt = (TextView) findViewById(R.id.nmZag2);
                                loadPlayer(json, img, txt, "Zagueiro");
                            } else {
                                ImageView img = (ImageView) findViewById(R.id.zag2);
                                TextView txt = (TextView) findViewById(R.id.nmZag2);
                                loadDefaultPlayer(img, txt, "Zagueiro");
                            }
                            break;
                        case 3:
                            if (json != null && !json.equals("")) {
                                ImageView img = (ImageView) findViewById(R.id.zag3);
                                TextView txt = (TextView) findViewById(R.id.nmZag3);
                                loadPlayer(json, img, txt, "Zagueiro");
                            } else {
                                ImageView img = (ImageView) findViewById(R.id.zag3);
                                TextView txt = (TextView) findViewById(R.id.nmZag3);
                                loadDefaultPlayer(img, txt, "Zagueiro");
                            }
                            break;
                        default:

                            break;
                    }

                    qtdJogadores++;
                }

                for (int i = 1; i < laterais + 1; i++) {
                    int posicaoId = 2;
                    String chave = "j" + qtdJogadores + "_" + posicaoId;
                    String json = Helpers.GetStringSharedPreference(this, chave);

                    switch (i) {
                        case 1:
                            if (json != null && !json.equals("")) {
                                ImageView img = (ImageView) findViewById(R.id.lat1);
                                TextView txt = (TextView) findViewById(R.id.nmLat1);
                                loadPlayer(json, img, txt, "Lateral");
                            } else {
                                ImageView img = (ImageView) findViewById(R.id.lat1);
                                TextView txt = (TextView) findViewById(R.id.nmLat1);
                                loadDefaultPlayer(img, txt, "Lateral");
                            }
                            break;
                        case 2:
                            if (json != null && !json.equals("")) {
                                ImageView img = (ImageView) findViewById(R.id.lat2);
                                TextView txt = (TextView) findViewById(R.id.nmLat2);
                                loadPlayer(json, img, txt, "Lateral");
                            } else {
                                ImageView img = (ImageView) findViewById(R.id.lat2);
                                TextView txt = (TextView) findViewById(R.id.nmLat2);
                                loadDefaultPlayer(img, txt, "Lateral");
                            }
                            break;
                    }

                    qtdJogadores++;
                }

                for (int i = 1; i < meias + 1; i++) {
                    int posicaoId = 4;
                    String chave = "j" + qtdJogadores + "_" + posicaoId;
                    String json = Helpers.GetStringSharedPreference(this, chave);

                    switch (i) {
                        case 1:
                            if (json != null && !json.equals("")) {
                                ImageView img = (ImageView) findViewById(R.id.mei1);
                                TextView txt = (TextView) findViewById(R.id.nmMei1);
                                loadPlayer(json, img, txt, "Meia");
                            } else {
                                ImageView img = (ImageView) findViewById(R.id.mei1);
                                TextView txt = (TextView) findViewById(R.id.nmMei1);
                                loadDefaultPlayer(img, txt, "Meia");
                            }
                            break;
                        case 2:
                            if (json != null && !json.equals("")) {
                                ImageView img = (ImageView) findViewById(R.id.mei2);
                                TextView txt = (TextView) findViewById(R.id.nmMei2);
                                loadPlayer(json, img, txt, "Meia");
                            } else {
                                ImageView img = (ImageView) findViewById(R.id.mei2);
                                TextView txt = (TextView) findViewById(R.id.nmMei2);
                                loadDefaultPlayer(img, txt, "Meia");
                            }
                            break;
                        case 3:
                            if (json != null && !json.equals("")) {
                                ImageView img = (ImageView) findViewById(R.id.mei3);
                                TextView txt = (TextView) findViewById(R.id.nmMei3);
                                loadPlayer(json, img, txt, "Meia");
                            } else {
                                ImageView img = (ImageView) findViewById(R.id.mei3);
                                TextView txt = (TextView) findViewById(R.id.nmMei3);
                                loadDefaultPlayer(img, txt, "Meia");
                            }
                            break;
                        case 4:
                            if (json != null && !json.equals("")) {
                                ImageView img = (ImageView) findViewById(R.id.mei4);
                                TextView txt = (TextView) findViewById(R.id.nmMei4);
                                loadPlayer(json, img, txt, "Meia");
                            } else {
                                ImageView img = (ImageView) findViewById(R.id.mei4);
                                TextView txt = (TextView) findViewById(R.id.nmMei4);
                                loadDefaultPlayer(img, txt, "Meia");
                            }
                            break;
                        case 5:
                            if (json != null && !json.equals("")) {
                                ImageView img = (ImageView) findViewById(R.id.mei5);
                                TextView txt = (TextView) findViewById(R.id.nmMei5);
                                loadPlayer(json, img, txt, "Meia");
                            } else {
                                ImageView img = (ImageView) findViewById(R.id.mei5);
                                TextView txt = (TextView) findViewById(R.id.nmMei5);
                                loadDefaultPlayer(img, txt, "Meia");
                            }
                            break;
                    }

                    qtdJogadores++;
                }

                for (int i = 1; i < atacantes + 1; i++) {
                    int posicaoId = 5;
                    String chave = "j" + qtdJogadores + "_" + posicaoId;
                    String json = Helpers.GetStringSharedPreference(this, chave);

                    switch (i) {
                        case 1:
                            if (json != null && !json.equals("")) {
                                ImageView img = (ImageView) findViewById(R.id.ata1);
                                TextView txt = (TextView) findViewById(R.id.nmAta1);
                                loadPlayer(json, img, txt, "Atacante");
                            } else {
                                ImageView img = (ImageView) findViewById(R.id.ata1);
                                TextView txt = (TextView) findViewById(R.id.nmAta1);
                                loadDefaultPlayer(img, txt, "Atacante");
                            }
                            break;
                        case 2:
                            if (json != null && !json.equals("")) {
                                ImageView img = (ImageView) findViewById(R.id.ata2);
                                TextView txt = (TextView) findViewById(R.id.nmAta2);
                                loadPlayer(json, img, txt, "Atacante");
                            } else {
                                ImageView img = (ImageView) findViewById(R.id.ata2);
                                TextView txt = (TextView) findViewById(R.id.nmAta2);
                                loadDefaultPlayer(img, txt, "Atacante");
                            }
                            break;
                        case 3:
                            if (json != null && !json.equals("")) {
                                ImageView img = (ImageView) findViewById(R.id.ata3);
                                TextView txt = (TextView) findViewById(R.id.nmAta3);
                                loadPlayer(json, img, txt, "Atacante");
                            } else {
                                ImageView img = (ImageView) findViewById(R.id.ata3);
                                TextView txt = (TextView) findViewById(R.id.nmAta3);
                                loadDefaultPlayer(img, txt, "Atacante");
                            }
                            break;
                        default:

                            break;
                    }

                    qtdJogadores++;
                }

                if (jsonTec != null && !jsonTec.equals("")) {
                    ImageView imgAtleta = (ImageView) findViewById(R.id.tec1);
                    TextView txtAtleta = (TextView) findViewById(R.id.nmTec);
                    loadPlayer(jsonTec, imgAtleta, txtAtleta, "Técnico");
                    qtdJogadores++;
                }


            }

        }

    }

    private void loadPlayer(String json, ImageView imgAtleta, TextView txtAtleta, String defaultNome) {
        Atleta atleta = new Gson().fromJson(json, Atleta.class);
        String urlImg = atleta.getUrlEscudo();
        String txtText = atleta.getApelido();

        if (urlImg != null && !urlImg.equals("")) {
            Glide.with(this).load(urlImg).into(imgAtleta);
        } else {
            imgAtleta.setImageResource(R.drawable.ic_escudo);
        }

        if (txtText != null && !txtText.equals("")) {
            txtAtleta.setText(txtText);
        } else {
            txtAtleta.setText(defaultNome);
        }

        imgAtleta.setVisibility(View.VISIBLE);
        txtAtleta.setVisibility(View.VISIBLE);

    }

    private void loadDefaultPlayer(ImageView imgAtleta, TextView txtAtleta, String defaultNome) {
        imgAtleta.setImageResource(R.drawable.ic_escudo);
        txtAtleta.setText(defaultNome);
        imgAtleta.setVisibility(View.VISIBLE);
        txtAtleta.setVisibility(View.VISIBLE);
    }

}
