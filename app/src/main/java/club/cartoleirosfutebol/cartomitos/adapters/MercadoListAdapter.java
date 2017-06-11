package club.cartoleirosfutebol.cartomitos.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import club.cartoleirosfutebol.cartomitos.EscalacaoActivity;
import club.cartoleirosfutebol.cartomitos.R;
import club.cartoleirosfutebol.cartomitos.data.Atleta;
import club.cartoleirosfutebol.cartomitos.data.Clube;
import club.cartoleirosfutebol.cartomitos.data.Const;
import club.cartoleirosfutebol.cartomitos.data.Partida;
import club.cartoleirosfutebol.cartomitos.data.PartidaClube;
import club.cartoleirosfutebol.cartomitos.data.Posicao;
import club.cartoleirosfutebol.cartomitos.data.Status;
import club.cartoleirosfutebol.cartomitos.util.Helpers;

/**
 * Created by joao.oliveira on 29/05/2017.
 */

public class MercadoListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<Atleta> _listDataHeader;
    private HashMap<Atleta, List<String>> _listDataChild;
    private Map<String, Clube> _clubes;
    PartidaClube _partidaClube;
    int escalacaoId;

    public MercadoListAdapter(Context context, List<Atleta> listDataHeader,
                              HashMap<Atleta, List<String>> listChildData, Map<String, Clube> clubes, PartidaClube partidaClube, int escalacaoId) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this._clubes = clubes;
        this._partidaClube = partidaClube;
        this.escalacaoId = escalacaoId;
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

        if (_clubes == null || jogador.getClubeId() == null || jogador.getClubeId() == 0) {
            imgEscudo.setImageResource(R.drawable.ic_escudo);
        } else {

            String urlImagemMandante = "";
            String urlImagemVisitante = "";

            for (Clube c : _clubes.values()) {

                if (c.getId().intValue() == jogador.getClubeId()) {
                    String urlImagem = getUrlImagemClube(jogador.getClubeId());
                    jogador.setUrlEscudo(urlImagem);
                    if (urlImagem != null) {
                        Glide.with(_context).load(urlImagem).into(imgEscudo);
                    } else {
                        imgEscudo.setImageResource(R.drawable.ic_escudo);
                    }

                    if (_partidaClube != null && _partidaClube.getPartidas() != null) {
                        for (Partida p : _partidaClube.getPartidas()) {
                            if (p.getClubeCasaId().intValue() == c.getId().intValue()) {
                                urlImagemMandante = urlImagem;
                                jogador.setUrlMandante(urlImagem);
                                String urlClubeJogadorVisitante = getUrlImagemClube(p.getClubeVisitanteId());
                                urlImagemVisitante = urlClubeJogadorVisitante;
                                jogador.setUrlVisitante(urlClubeJogadorVisitante);
                                break;
                            }

                            if (p.getClubeVisitanteId().intValue() == c.getId().intValue()) {
                                String urlClubeJogadorMandante = getUrlImagemClube(p.getClubeCasaId());
                                urlImagemMandante = urlClubeJogadorMandante;
                                jogador.setUrlMandante(urlClubeJogadorMandante);
                                urlImagemVisitante = urlImagem;
                                jogador.setUrlVisitante(urlImagem);
                                break;
                            }

                        }
                    }

                    break;

                }
            }

            btnJogador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String jsonJogador = new Gson().toJson(jogador);
                    Helpers.putSharedPreference(_context, Const.prefixKeyEscalacao + escalacaoId + "_" + jogador.getPosicaoId(), jsonJogador);
                    Intent intent = new Intent(_context, EscalacaoActivity.class);
                    _context.startActivity(intent);
                }
            });

            if (urlImagemMandante != null && urlImagemMandante != "") {
                Glide.with(_context).load(urlImagemMandante).into(imgMandante);
            } else {
                imgMandante.setImageResource(R.drawable.ic_escudo);
            }

            if (urlImagemVisitante != null && urlImagemVisitante != "") {
                Glide.with(_context).load(urlImagemVisitante).into(imgVisitante);
            } else {
                imgVisitante.setImageResource(R.drawable.ic_escudo);
            }

            imgMandante.setVisibility(View.VISIBLE);
            lblVersus.setVisibility(View.VISIBLE);
            imgVisitante.setVisibility(View.VISIBLE);

        }

        // define o apelido do jogador
        if (jogador.getApelido() == null) {
            lblUltima.setVisibility(View.GONE);
        } else {
            lblNome.setVisibility(View.VISIBLE);
            lblNome.setText(jogador.getApelido());
        }

        // define a posição do jogador
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

        // define o status do jogador
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
                lblValorizacao.setTextColor(Color.GREEN);
                indicador = "+";
            } else {
                lblValorizacao.setTextColor(Color.RED);
            }
            lblValorizacao.setVisibility(View.VISIBLE);
            lblValorizacao.setText("C$: " + indicador + jogador.getVariacaoNum());
        }

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

    public String getUrlImagemClube(int clubeId) {
        if (_clubes != null) {
            for (Clube c : _clubes.values()) {
                if (c.getId().intValue() == clubeId) {
                    if (c.getEscudos() != null) {
                        if (c.getEscudos().get60x60() != null) {
                            return c.getEscudos().get60x60();
                        }
                        if (c.getEscudos().get45x45() != null) {
                            return c.getEscudos().get45x45();
                        }
                        if (c.getEscudos().get30x30() != null) {
                            return c.getEscudos().get30x30();
                        }
                    }
                }
            }

        } else {
            return null;
        }

        return null;
    }

}
