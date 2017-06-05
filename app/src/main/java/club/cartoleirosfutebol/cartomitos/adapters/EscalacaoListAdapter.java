package club.cartoleirosfutebol.cartomitos.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import club.cartoleirosfutebol.cartomitos.EscalacaoActivity;
import club.cartoleirosfutebol.cartomitos.MercadoActivity;
import club.cartoleirosfutebol.cartomitos.R;
import club.cartoleirosfutebol.cartomitos.data.Atleta;
import club.cartoleirosfutebol.cartomitos.data.JogadorItem;
import club.cartoleirosfutebol.cartomitos.data.Posicao;
import club.cartoleirosfutebol.cartomitos.data.Status;

/**
 * Created by JP on 21/05/2017.
 */

public class EscalacaoListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<Atleta> _listDataHeader;
    private HashMap<Atleta, List<String>> _listDataChild;

    public EscalacaoListAdapter(Context context, List<Atleta> listDataHeader,
                                HashMap<Atleta, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_escalacaodetalhes, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(Html.fromHtml(childText));
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final Atleta jogador = (Atleta) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_escalacao, null);
        }

        TextView lblNome = (TextView) convertView.findViewById(R.id.db_jog);
        TextView lblPosicao = (TextView) convertView.findViewById(R.id.db_pos);
        TextView lblUltima = (TextView) convertView.findViewById(R.id.db_ult);
        TextView lblMedia = (TextView) convertView.findViewById(R.id.db_med);
        TextView lblPreco = (TextView) convertView.findViewById(R.id.db_pre);
        TextView lblValorizacao = (TextView) convertView.findViewById(R.id.db_val);
        TextView lblJogos = (TextView) convertView.findViewById(R.id.db_par);
        TextView lblVersus = (TextView) convertView.findViewById(R.id.db_versus);
        ImageView imgEscudo = (ImageView) convertView.findViewById(R.id.db_escudo);
        ImageView imgStatus = (ImageView) convertView.findViewById(R.id.db_status);
        ImageView imgMandante = (ImageView) convertView.findViewById(R.id.db_mandante);
        ImageView imgVisitante = (ImageView) convertView.findViewById(R.id.db_visitante);
        ImageButton btnJogador = (ImageButton) convertView.findViewById(R.id.btnJogador);

        String posicoesEsquemaJson = _context.getResources().getString(R.string.posicoes_json);
        Type listEsquemaType = new TypeToken<ArrayList<Posicao>>() {
        }.getType();
        List<Posicao> posicoes = new Gson().fromJson(posicoesEsquemaJson, listEsquemaType);

        String statusJson = _context.getResources().getString(R.string.status_json);
        Type listStatusType = new TypeToken<ArrayList<Status>>() {
        }.getType();
        List<Status> statusList = new Gson().fromJson(statusJson, listStatusType);

        if (jogador.getApelido() == null) {
            lblNome.setVisibility(View.VISIBLE);
            lblNome.setText("Jogador");
        } else {
            lblNome.setVisibility(View.VISIBLE);
            lblNome.setText(jogador.getApelido());
        }

        if (jogador.getUrlEscudo() == null || jogador.getUrlEscudo().equals("")) {
            imgEscudo.setImageResource(R.drawable.ic_escudo);
            imgEscudo.setVisibility(View.VISIBLE);
        } else {
            Glide.with(_context).load(jogador.getUrlEscudo()).into(imgEscudo);
            imgEscudo.setVisibility(View.VISIBLE);
        }

        if (jogador.getUrlMandante() == null || jogador.getUrlMandante().equals("")) {
            imgMandante.setVisibility(View.GONE);
            lblVersus.setVisibility(View.GONE);
        } else {
            Glide.with(_context).load(jogador.getUrlMandante()).into(imgMandante);
            imgMandante.setVisibility(View.VISIBLE);
            lblVersus.setVisibility(View.VISIBLE);
        }

        if (jogador.getUrlVisitante() == null || jogador.getUrlVisitante().equals("")) {
            imgVisitante.setVisibility(View.GONE);
            lblVersus.setVisibility(View.GONE);
        } else {
            Glide.with(_context).load(jogador.getUrlVisitante()).into(imgVisitante);
            imgVisitante.setVisibility(View.VISIBLE);
            lblVersus.setVisibility(View.VISIBLE);
        }

        if (jogador.getPosicaoId() == null) {
            lblPosicao.setVisibility(View.GONE);
        } else {
            lblPosicao.setVisibility(View.VISIBLE);
            String posicaoNome = "";

            for (Posicao p : posicoes) {

                if (jogador.getPosicaoId() == p.getId()) {
                    posicaoNome = p.getNome();
                    break;
                }

            }
            lblPosicao.setText(posicaoNome);
        }

        if (jogador.getStatusId() == null) {
            imgStatus.setVisibility(View.GONE);
        } else {
            imgStatus.setVisibility(View.VISIBLE);

            switch (jogador.getStatusId()) {

                case 7:
                    imgStatus.setImageResource(R.drawable.ic_provavel);
                    break;
                case 5:
                    imgStatus.setImageResource(R.drawable.ic_contudido);
                    break;
                case 2:
                    imgStatus.setImageResource(R.drawable.ic_duvida);
                    break;
                case 3:
                    imgStatus.setImageResource(R.drawable.ic_suspenso);
                    break;
                case 6:
                    imgStatus.setVisibility(View.GONE);
                    break;
                default:
                    imgStatus.setVisibility(View.GONE);
            }
        }

        // define a media do jogador
        if (jogador.getMediaNum() == null) {
            lblMedia.setVisibility(View.GONE);
        } else {
            lblMedia.setVisibility(View.VISIBLE);
            lblMedia.setText("Média: " + jogador.getMediaNum());
        }

        // define o numero de jogos do jogador
        if (jogador.getJogosNum() == null) {
            lblJogos.setVisibility(View.GONE);
        } else {
            lblJogos.setVisibility(View.VISIBLE);
            lblJogos.setText("Jogos: " + jogador.getJogosNum());
        }

        // define a ultima pontuação do jogador
        if (jogador.getPontosNum() == null) {
            lblUltima.setVisibility(View.GONE);
        } else {
            lblUltima.setVisibility(View.VISIBLE);
            lblUltima.setText("Última: " + jogador.getPontosNum());
        }

        // define o preço do jogador
        if (jogador.getPrecoNum() == null) {
            lblPreco.setVisibility(View.GONE);
        } else {
            lblPreco.setVisibility(View.VISIBLE);
            lblPreco.setText("C$: " + jogador.getPrecoNum());
        }

        // define a variacao do jogador
        if (jogador.getVariacaoNum() == null) {
            lblValorizacao.setVisibility(View.GONE);
        } else {
            String indicador = "";

            if (jogador.getVariacaoNum() == 0) {
                lblValorizacao.setTextColor(Color.BLACK);
            } else if (jogador.getVariacaoNum() > 0) {
                lblValorizacao.setTextColor(Color.RED);
                indicador = "+";
            } else {
                lblValorizacao.setTextColor(Color.GREEN);
            }
            lblValorizacao.setVisibility(View.VISIBLE);
            lblValorizacao.setText("C$: " + indicador + jogador.getVariacaoNum());
        }
        btnJogador.setImageResource(R.drawable.ic_change);
        btnJogador.setBackgroundResource(R.drawable.rounded_button_escalacao);
        btnJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_context, MercadoActivity.class);
                intent.putExtra("posicao", jogador.getPosicaoId());
                intent.putExtra("id", groupPosition);
                _context.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
