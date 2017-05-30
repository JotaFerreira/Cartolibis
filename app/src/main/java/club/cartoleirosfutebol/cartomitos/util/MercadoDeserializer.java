package club.cartoleirosfutebol.cartomitos.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import club.cartoleirosfutebol.cartomitos.data.ClubeMercado;
import club.cartoleirosfutebol.cartomitos.data.Mercado;

/**
 * Created by JP on 28/05/2017.
 */

public class MercadoDeserializer implements JsonDeserializer<Object> {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Get the "content" element from the parsed JSON
        try{

            /*JsonElement content = json.getAsJsonObject().get("atletas");
            JsonElement contentClubes = json.getAsJsonObject().get("clubes");
            Object s = new Gson().fromJson(content, Mercado.class);*/
            Object t = new Gson().fromJson(json, Mercado.class);

            // Deserialize it. You use a new instance of Gson to avoid infinite recursion
            // to this deserializer
            return new Gson().fromJson(json, Mercado.class);

        } catch (Exception e){
            Log.e("ERROR: ",e.getMessage());
            return null;
        }

    }
}
