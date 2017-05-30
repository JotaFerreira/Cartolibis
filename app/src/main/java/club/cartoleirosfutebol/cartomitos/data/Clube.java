package club.cartoleirosfutebol.cartomitos.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by JP on 28/05/2017.
 */

public class Clube {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("abreviacao")
    @Expose
    private String abreviacao;
    @SerializedName("posicao")
    @Expose
    private Integer posicao;
    @SerializedName("escudos")
    @Expose
    private Escudo escudos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public Escudo getEscudos() {
        return escudos;
    }

    public void setEscudos(Escudo escudos) {
        this.escudos = escudos;
    }

}
