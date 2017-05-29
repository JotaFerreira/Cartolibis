package club.cartoleirosfutebol.cartomitos.data;

/**
 * Created by JP on 24/05/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Esquema {
    @SerializedName("esquema_id")
    @Expose
    private Integer esquemaId;
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("posicoes")
    @Expose
    private PosicaoEsquema posicoes;

    public Integer getEsquemaId() {
        return esquemaId;
    }

    public void setEsquemaId(Integer esquemaId) {
        this.esquemaId = esquemaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public PosicaoEsquema getPosicoes() {
        return posicoes;
    }

    public void setPosicoes(PosicaoEsquema posicoes) {
        this.posicoes = posicoes;
    }
}
