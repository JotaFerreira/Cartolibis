package club.cartoleirosfutebol.cartomitos.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by joao.oliveira on 31/05/2017.
 */

public class PartidaClube {
    @SerializedName("partidas")
    @Expose
    private List<Partida> partidas = null;

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

}
