package club.cartoleirosfutebol.cartomitos.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import club.cartoleirosfutebol.cartomitos.data.Partida;
import club.cartoleirosfutebol.cartomitos.data.PartidaClube;

/**
 * Created by joao.oliveira on 31/05/2017.
 */

public class PartidasDeserializer implements JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Get the "content" element from the parsed JSON
        try {

            return new Gson().fromJson(json, Partida.class);

        } catch (Exception e) {
            Log.e("PartidasDeserializer: ", e.getMessage());
            return null;
        }

    }

}
