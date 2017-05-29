package club.cartoleirosfutebol.cartomitos.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joao.oliveira on 29/05/2017.
 */

public class PosicaoEsquema {

    @SerializedName("ata")
    @Expose
    private Integer ata;
    @SerializedName("gol")
    @Expose
    private Integer gol;
    @SerializedName("lat")
    @Expose
    private Integer lat;
    @SerializedName("mei")
    @Expose
    private Integer mei;
    @SerializedName("tec")
    @Expose
    private Integer tec;
    @SerializedName("zag")
    @Expose
    private Integer zag;

    public Integer getAta() {
        return ata;
    }

    public void setAta(Integer ata) {
        this.ata = ata;
    }

    public Integer getGol() {
        return gol;
    }

    public void setGol(Integer gol) {
        this.gol = gol;
    }

    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public Integer getMei() {
        return mei;
    }

    public void setMei(Integer mei) {
        this.mei = mei;
    }

    public Integer getTec() {
        return tec;
    }

    public void setTec(Integer tec) {
        this.tec = tec;
    }

    public Integer getZag() {
        return zag;
    }

    public void setZag(Integer zag) {
        this.zag = zag;
    }

}
