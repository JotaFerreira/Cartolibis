package club.cartoleirosfutebol.cartomitos.data;

/**
 * Created by JP on 24/05/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Scout {
    @SerializedName("FC")
    @Expose
    private Integer fC;
    @SerializedName("FD")
    @Expose
    private Integer fD;
    @SerializedName("FF")
    @Expose
    private Integer fF;
    @SerializedName("FS")
    @Expose
    private Integer fS;
    @SerializedName("I")
    @Expose
    private Integer i;
    @SerializedName("PE")
    @Expose
    private Integer pE;
    @SerializedName("RB")
    @Expose
    private Integer rB;

    public Integer getFC() {
        return fC;
    }

    public void setFC(Integer fC) {
        this.fC = fC;
    }

    public Integer getFD() {
        return fD;
    }

    public void setFD(Integer fD) {
        this.fD = fD;
    }

    public Integer getFF() {
        return fF;
    }

    public void setFF(Integer fF) {
        this.fF = fF;
    }

    public Integer getFS() {
        return fS;
    }

    public void setFS(Integer fS) {
        this.fS = fS;
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public Integer getPE() {
        return pE;
    }

    public void setPE(Integer pE) {
        this.pE = pE;
    }

    public Integer getRB() {
        return rB;
    }

    public void setRB(Integer rB) {
        this.rB = rB;
    }
}
