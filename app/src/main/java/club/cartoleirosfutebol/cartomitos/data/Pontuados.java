package club.cartoleirosfutebol.cartomitos.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by JP on 27/05/2017.
 */

public class Pontuados {
    @SerializedName("apelido")
    @Expose
    private String apelido;
    @SerializedName("pontuacao")
    @Expose
    private Integer pontuacao;
    @SerializedName("scout")
    @Expose
    private Scout scout;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("posicao_id")
    @Expose
    private Integer posicaoId;
    @SerializedName("clube_id")
    @Expose
    private Integer clubeId;

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public Scout getScout() {
        return scout;
    }

    public void setScout(Scout scout) {
        this.scout = scout;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getPosicaoId() {
        return posicaoId;
    }

    public void setPosicaoId(Integer posicaoId) {
        this.posicaoId = posicaoId;
    }

    public Integer getClubeId() {
        return clubeId;
    }

    public void setClubeId(Integer clubeId) {
        this.clubeId = clubeId;
    }
}
