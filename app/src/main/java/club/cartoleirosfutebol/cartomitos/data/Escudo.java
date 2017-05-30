package club.cartoleirosfutebol.cartomitos.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joao.oliveira on 30/05/2017.
 */

public class Escudo {
    @SerializedName("60x60")
    @Expose
    private String _60x60;
    @SerializedName("45x45")
    @Expose
    private String _45x45;
    @SerializedName("30x30")
    @Expose
    private String _30x30;

    public String get60x60() {
        return _60x60;
    }

    public void set60x60(String _60x60) {
        this._60x60 = _60x60;
    }

    public String get45x45() {
        return _45x45;
    }

    public void set45x45(String _45x45) {
        this._45x45 = _45x45;
    }

    public String get30x30() {
        return _30x30;
    }

    public void set30x30(String _30x30) {
        this._30x30 = _30x30;
    }
}
