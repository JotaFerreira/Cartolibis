package club.cartoleirosfutebol.cartomitos.api;

import java.util.List;

import club.cartoleirosfutebol.cartomitos.data.PartidaClube;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by joao.oliveira on 31/05/2017.
 */

public interface PartidasAPI {

    @GET("/partidas")
    Call <PartidaClube> getPartidas();

}
