package club.cartoleirosfutebol.cartomitos.data;

/**
 * Created by JP on 25/05/2017.
 */

public class JogadorItem {

    private String Nome;
    private String Posicao;
    private Double Preco;
    private Double Ultima;
    private Double Media;
    private Double Valorizacao;
    private int Status;
    private int TimeId;
    private Double Pontos;
    private int Jogos;
    private Scout Scout;

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getPosicao() {
        return Posicao;
    }

    public void setPosicao(String posicao) {
        Posicao = posicao;
    }

    public Double getPreco() {
        return Preco;
    }

    public void setPreco(Double preco) {
        Preco = preco;
    }

    public Double getUltima() {
        return Ultima;
    }

    public void setUltima(Double ultima) {
        Ultima = ultima;
    }

    public Double getMedia() {
        return Media;
    }

    public void setMedia(Double media) {
        Media = media;
    }

    public Double getValorizacao() {
        return Valorizacao;
    }

    public void setValorizacao(Double valorizacao) {
        Valorizacao = valorizacao;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getTimeId() {
        return TimeId;
    }

    public void setTimeId(int timeId) {
        TimeId = timeId;
    }

    public Double getPontos() {
        return Pontos;
    }

    public void setPontos(Double pontos) {
        Pontos = pontos;
    }

    public int getJogos() {
        return Jogos;
    }

    public void setJogos(int jogos) {
        Jogos = jogos;
    }

    public club.cartoleirosfutebol.cartomitos.data.Scout getScout() {
        return Scout;
    }

    public void setScout(club.cartoleirosfutebol.cartomitos.data.Scout scout) {
        Scout = scout;
    }

}
