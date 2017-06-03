package club.cartoleirosfutebol.cartomitos.data;

/**
 * Created by JP on 24/05/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Atleta {
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("apelido")
    @Expose
    private String apelido;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("atleta_id")
    @Expose
    private Integer atletaId;
    @SerializedName("rodada_id")
    @Expose
    private Integer rodadaId;
    @SerializedName("clube_id")
    @Expose
    private Integer clubeId;
    @SerializedName("posicao_id")
    @Expose
    private Integer posicaoId;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("pontos_num")
    @Expose
    private Double pontosNum;
    @SerializedName("preco_num")
    @Expose
    private Double precoNum;
    @SerializedName("variacao_num")
    @Expose
    private Double variacaoNum;
    @SerializedName("media_num")
    @Expose
    private Double mediaNum;
    @SerializedName("jogos_num")
    @Expose
    private Integer jogosNum;
    @SerializedName("scout")
    @Expose
    private Scout scout;
    private String urlEscudo;
    private String urlMandante;
    private String urlVisitante;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getAtletaId() {
        return atletaId;
    }

    public void setAtletaId(Integer atletaId) {
        this.atletaId = atletaId;
    }

    public Integer getRodadaId() {
        return rodadaId;
    }

    public void setRodadaId(Integer rodadaId) {
        this.rodadaId = rodadaId;
    }

    public Integer getClubeId() {
        return clubeId;
    }

    public void setClubeId(Integer clubeId) {
        this.clubeId = clubeId;
    }

    public Integer getPosicaoId() {
        return posicaoId;
    }

    public void setPosicaoId(Integer posicaoId) {
        this.posicaoId = posicaoId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Double getPontosNum() {
        return pontosNum;
    }

    public void setPontosNum(Double pontosNum) {
        this.pontosNum = pontosNum;
    }

    public Double getPrecoNum() {
        return precoNum;
    }

    public void setPrecoNum(Double precoNum) {
        this.precoNum = precoNum;
    }

    public Double getVariacaoNum() {
        return variacaoNum;
    }

    public void setVariacaoNum(Double variacaoNum) {
        this.variacaoNum = variacaoNum;
    }

    public Double getMediaNum() {
        return mediaNum;
    }

    public void setMediaNum(Double mediaNum) {
        this.mediaNum = mediaNum;
    }

    public Integer getJogosNum() {
        return jogosNum;
    }

    public void setJogosNum(Integer jogosNum) {
        this.jogosNum = jogosNum;
    }

    public Scout getScout() {
        return scout;
    }

    public void setScout(Scout scout) {
        this.scout = scout;
    }

    public String getUrlEscudo() {
        return urlEscudo;
    }

    public void setUrlEscudo(String urlEscudo) {
        this.urlEscudo = urlEscudo;
    }

    public String getUrlMandante() {
        return urlMandante;
    }

    public void setUrlMandante(String urlMandante) {
        this.urlMandante = urlMandante;
    }

    public String getUrlVisitante() {
        return urlVisitante;
    }

    public void setUrlVisitante(String urlVisitante) {
        this.urlVisitante = urlVisitante;
    }

}
