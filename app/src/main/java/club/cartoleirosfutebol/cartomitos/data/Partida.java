package club.cartoleirosfutebol.cartomitos.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by joao.oliveira on 31/05/2017.
 */

public class Partida {
    @SerializedName("clube_casa_id")
    @Expose
    private Integer clubeCasaId;
    @SerializedName("clube_casa_posicao")
    @Expose
    private Integer clubeCasaPosicao;
    @SerializedName("clube_visitante_id")
    @Expose
    private Integer clubeVisitanteId;
    @SerializedName("aproveitamento_mandante")
    @Expose
    private List<String> aproveitamentoMandante = null;
    @SerializedName("aproveitamento_visitante")
    @Expose
    private List<String> aproveitamentoVisitante = null;
    @SerializedName("clube_visitante_posicao")
    @Expose
    private Integer clubeVisitantePosicao;
    @SerializedName("partida_data")
    @Expose
    private String partidaData;
    @SerializedName("local")
    @Expose
    private String local;
    @SerializedName("valida")
    @Expose
    private Boolean valida;
    @SerializedName("placar_oficial_mandante")
    @Expose
    private Object placarOficialMandante;
    @SerializedName("placar_oficial_visitante")
    @Expose
    private Object placarOficialVisitante;
    @SerializedName("url_confronto")
    @Expose
    private String urlConfronto;
    @SerializedName("url_transmissao")
    @Expose
    private String urlTransmissao;

    public Integer getClubeCasaId() {
        return clubeCasaId;
    }

    public void setClubeCasaId(Integer clubeCasaId) {
        this.clubeCasaId = clubeCasaId;
    }

    public Integer getClubeCasaPosicao() {
        return clubeCasaPosicao;
    }

    public void setClubeCasaPosicao(Integer clubeCasaPosicao) {
        this.clubeCasaPosicao = clubeCasaPosicao;
    }

    public Integer getClubeVisitanteId() {
        return clubeVisitanteId;
    }

    public void setClubeVisitanteId(Integer clubeVisitanteId) {
        this.clubeVisitanteId = clubeVisitanteId;
    }

    public List<String> getAproveitamentoMandante() {
        return aproveitamentoMandante;
    }

    public void setAproveitamentoMandante(List<String> aproveitamentoMandante) {
        this.aproveitamentoMandante = aproveitamentoMandante;
    }

    public List<String> getAproveitamentoVisitante() {
        return aproveitamentoVisitante;
    }

    public void setAproveitamentoVisitante(List<String> aproveitamentoVisitante) {
        this.aproveitamentoVisitante = aproveitamentoVisitante;
    }

    public Integer getClubeVisitantePosicao() {
        return clubeVisitantePosicao;
    }

    public void setClubeVisitantePosicao(Integer clubeVisitantePosicao) {
        this.clubeVisitantePosicao = clubeVisitantePosicao;
    }

    public String getPartidaData() {
        return partidaData;
    }

    public void setPartidaData(String partidaData) {
        this.partidaData = partidaData;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Boolean getValida() {
        return valida;
    }

    public void setValida(Boolean valida) {
        this.valida = valida;
    }

    public Object getPlacarOficialMandante() {
        return placarOficialMandante;
    }

    public void setPlacarOficialMandante(Object placarOficialMandante) {
        this.placarOficialMandante = placarOficialMandante;
    }

    public Object getPlacarOficialVisitante() {
        return placarOficialVisitante;
    }

    public void setPlacarOficialVisitante(Object placarOficialVisitante) {
        this.placarOficialVisitante = placarOficialVisitante;
    }

    public String getUrlConfronto() {
        return urlConfronto;
    }

    public void setUrlConfronto(String urlConfronto) {
        this.urlConfronto = urlConfronto;
    }

    public String getUrlTransmissao() {
        return urlTransmissao;
    }

    public void setUrlTransmissao(String urlTransmissao) {
        this.urlTransmissao = urlTransmissao;
    }
}
