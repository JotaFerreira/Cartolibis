package club.cartoleirosfutebol.cartomitos.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by joao.oliveira on 30/05/2017.
 */

public class ClubeMercado {

    @SerializedName("clubes")
    @Expose
    private Map<String,Clube> clubes = null;

    public Map<String,Clube> getClubes() {
        return clubes;
    }

    public void setClubes(Map<String,Clube> clubes) {
        this.clubes = clubes;
    }
}
