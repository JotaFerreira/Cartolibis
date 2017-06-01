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
    @SerializedName("SG")
    @Expose
    private Integer sG;
    @SerializedName("A")
    @Expose
    private Integer a;
    @SerializedName("CA")
    @Expose
    private Integer cA;
    @SerializedName("FT")
    @Expose
    private Integer fT;
    @SerializedName("G")
    @Expose
    private Integer g;
    @SerializedName("CV")
    @Expose
    private Integer cV;
    @SerializedName("DD")
    @Expose
    private Integer dD;
    @SerializedName("GS")
    @Expose
    private Integer gS;
    @SerializedName("PP")
    @Expose
    private Integer pP;
    @SerializedName("DP")
    @Expose
    private Integer dP;
    @SerializedName("GC")
    @Expose
    private Integer gC;

    // getters and setters

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

    public Integer getSG() {
        return sG;
    }

    public void setSG(Integer sG) {
        this.sG = sG;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Integer getCA() { return cA; }

    public void setCA(Integer cA) { this.cA = cA; }

    public Integer getFT() {
        return fT;
    }

    public void setFT(Integer ft) {
        this.fT = ft;
    }

    public Integer getG() {
        return g;
    }

    public void setG(Integer g) {
        this.g = g;
    }

    public Integer getCV() {
        return cV;
    }

    public void setCV(Integer cv) {
        this.cV = cv;
    }

    public Integer getDD() {
        return dD;
    }

    public void setDD(Integer dD) {
        this.dD = dD;
    }

    public Integer getGS() {
        return gS;
    }

    public void setGS(Integer gS) {
        this.gS = gS;
    }

    public Integer getPP() {
        return pP;
    }

    public void setPP(Integer pP) { this.pP = pP; }

    public Integer getDP() { return dP; }

    public void setDP(Integer dP) { this.dP = dP; }

    public Integer getGC() {
        return gC;
    }

    public void setGC(Integer gC) { this.gC = gC; }

    // descrições

    public String getDesFC(){
        return "Falta Cometida";
    }

    public String getDesFD(){
        return "Finalização Defendida";
    }

    public String getDesFF(){
        return "Finalização pra Fora";
    }

    public String getDesFS(){
        return "Falta Sofrida";
    }

    public String getDesSG(){
        return "Jogos sem Sofrer Gols";
    }

    public String getDesI(){
        return "Impedimento";
    }

    public String getDesPE(){
        return "Passe Errado";
    }

    public String getDesRB(){
        return "Roubada de Bola";
    }

    public String getDesA(){
        return "Assistência";
    }

    public String getDesCA(){
        return "Cartão Amarelo";
    }

    public String getDesFT(){
        return "Finalização na Trave";
    }

    public String getDesG(){
        return "Gol";
    }

    public String getDesCV(){ return "Cartão Vermelho"; }

    public String getDesDD(){
        return "Defesa Difícil";
    }

    public String getDesGS(){
        return "Gol Sofrido";
    }

    public String getDesPP(){
        return "Pênalti Perdido";
    }

    public String getDesDP(){
        return "Defesa de Pênalti";
    }

    public String getDesGC(){
        return "Gol Contra";
    }

}
