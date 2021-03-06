package club.cartoleirosfutebol.cartomitos.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by JP on 28/05/2017.
 */

public class Mercado {
    @SerializedName("atletas")
    @Expose
    private List<Atleta> atletas = null;

    @SerializedName("clubes")
    @Expose
    private Map<String,Clube> clubes = null;

    public Map<String,Clube> getClubes() {
        return clubes;
    }

    public void setClubes(Map<String,Clube> clubes) {
        this.clubes = clubes;
    }

    public List<Atleta> getAtletas() {
        return atletas;
    }

    public void setAtletas(List<Atleta> atletas) {
        this.atletas = atletas;
    }

}
