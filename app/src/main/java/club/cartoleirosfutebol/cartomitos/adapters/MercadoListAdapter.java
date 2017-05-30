package club.cartoleirosfutebol.cartomitos.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import club.cartoleirosfutebol.cartomitos.MercadoActivity;
import club.cartoleirosfutebol.cartomitos.R;
import club.cartoleirosfutebol.cartomitos.data.Atleta;
import club.cartoleirosfutebol.cartomitos.data.Esquema;
import club.cartoleirosfutebol.cartomitos.data.JogadorItem;
import club.cartoleirosfutebol.cartomitos.data.Posicao;
import club.cartoleirosfutebol.cartomitos.data.PosicaoEsquema;
import club.cartoleirosfutebol.cartomitos.data.Status;

/**
 * Created by joao.oliveira on 29/05/2017.
 */

public class MercadoListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<Atleta> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<Atleta, List<String>> _listDataChild;

    public MercadoListAdapter(Context context, List<Atleta> listDataHeader,
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

        txtListChild.setText(childText);
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
    public View getGroupView(int groupPosition, boolean isExpanded,
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
        ImageView imgStatus = (ImageView) convertView.findViewById(R.id.db_status);
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
            lblUltima.setVisibility(View.GONE);
        } else {
            lblNome.setVisibility(View.VISIBLE);
            lblNome.setText(jogador.getApelido());
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

 /*       btnJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_context, MercadoActivity.class);
                intent.putExtra("filtro",jogador.getPosicao());
                _context.startActivity(intent);
                Log.i("ONCLICK",jogador.getPosicao());
            }
        });*/

        /*if(jogador.getUltima() == 0){
            lblUltima.setVisibility(View.GONE);
        } else {
            lblUltima.setVisibility(View.VISIBLE);
            lblUltima.setText("Últ: " + jogador.getUltima());
        }

        if(jogador.getMedia() == 0){
            lblMedia.setVisibility(View.GONE);
        } else {
            lblMedia.setVisibility(View.VISIBLE);
            lblMedia.setText("Méd: " + jogador.getMedia());
        }*/

        //lblPreco.setText("C$: " + jogador.getPreco());

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