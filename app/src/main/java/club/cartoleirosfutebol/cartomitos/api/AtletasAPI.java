package club.cartoleirosfutebol.cartomitos.api;

import java.util.List;

import club.cartoleirosfutebol.cartomitos.data.Atleta;
import club.cartoleirosfutebol.cartomitos.data.Mercado;
import club.cartoleirosfutebol.cartomitos.data.Pontuados;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by JP on 27/05/2017.
 */

public interface AtletasAPI {

    @GET("atletas/mercado")
    Call<Mercado> getMercado();

    @GET("atletas/pontuados")
    Call<Pontuados> getPontuados();

}
