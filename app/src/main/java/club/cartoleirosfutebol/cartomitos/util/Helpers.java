package club.cartoleirosfutebol.cartomitos.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import club.cartoleirosfutebol.cartomitos.data.Const;
import club.cartoleirosfutebol.cartomitos.data.Esquema;
import club.cartoleirosfutebol.cartomitos.data.Posicao;
import club.cartoleirosfutebol.cartomitos.data.PosicaoEsquema;

/**
 * Created by JP on 21/05/2017.
 */

public class Helpers {

    public static void putSharedPreference(Context context, String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringSharedPreference(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(key, null);
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected(); // isConnectedOrConnecting()
        return isConnected;
    }

    // Retorna as chaves do SharedPreferences referentes ao esquemas especificado
    public static List<String> getKeysByScheme(PosicaoEsquema esquema) {

        List<String> results = new ArrayList<>();
        int qtdJogadores = 0;

        if (esquema != null) {

            results.add(Const.prefixKeyEscalacao + qtdJogadores + "_" + Const.posicaoIdGoleiro);
            qtdJogadores++;

            for (int i = 0; i < esquema.getZag(); i++) {
                String chave = Const.prefixKeyEscalacao + qtdJogadores + "_" + Const.posicaoIdZagueiro;
                qtdJogadores++;
                results.add(chave);
            }

            for (int i = 0; i < esquema.getLat(); i++) {
                String chave = Const.prefixKeyEscalacao + qtdJogadores + "_" + Const.posicaoIdLateral;
                qtdJogadores++;
                results.add(chave);
            }

            for (int i = 0; i < esquema.getMei(); i++) {
                String chave = Const.prefixKeyEscalacao + qtdJogadores + "_" + Const.posicaoIdMeia;
                qtdJogadores++;
                results.add(chave);
            }

            for (int i = 0; i < esquema.getAta(); i++) {
                String chave = Const.prefixKeyEscalacao + qtdJogadores + "_" + Const.posicaoIdAtacante;
                qtdJogadores++;
                results.add(chave);
            }

            results.add(Const.prefixKeyEscalacao + qtdJogadores + "_" + Const.posicaoIdTecnico);
            qtdJogadores++;

        }

        return results;

    }

}
