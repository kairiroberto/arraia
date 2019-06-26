package com.arraiax.kairi.arraiax;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WebAppInterface {

    Context mContext;

    /** Instantiate the interface and set the context */
    public WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void saveUsuario(String usuario) {
        try {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences("ARRAIA", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
        /*
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nome", "roberto");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String usu = gson.toJson(jsonObject);
        */
            editor.putString("USUARIO", usuario);
            editor.commit();
            Toast.makeText(mContext, "Dados salvo com sucesso.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @JavascriptInterface
    public String getUsuario() {
        String result = null;
        try {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences("ARRAIA", Context.MODE_PRIVATE);
            result = sharedPreferences.getString("USUARIO", "");
            /*
            Gson gson = new Gson();
            Type type = new TypeToken<String>(){}.getType();
            result = gson.fromJson(result, type);
            */
            if (result == "") {
                Toast.makeText(mContext, "Usuário não existe.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Usuário existe.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    @JavascriptInterface
    public String saveFilho(String filho) {
        try {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences("ARRAIA", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("FILHO", filho);
            editor.commit();

            HorarioSQlite qlite = new HorarioSQlite(mContext);
            List<Horario> horarios = new ArrayList<Horario>();
            JSONObject jsonObject = new JSONObject(filho);
            String sObject = String.valueOf(jsonObject.get("horario"));
            JSONObject partsData = new JSONObject(sObject);
            Iterator<String> key = partsData.keys();
            while (key.hasNext()) {
                String sKey = key.next();
                JSONObject object = partsData.getJSONObject(sKey);
                Horario h = new Horario();

                h.setIdKey(sKey);
                h.setDescricao(object.getString("descricao"));
                h.setEscola(object.getString("escola"));
                h.setHorariofin(object.getString("horariofin"));
                h.setHorarioini(object.getString("horarioini"));
                h.setLatitude(Double.parseDouble(object.getString("latitude")));
                h.setLongitude(Double.parseDouble(object.getString("longitude")));
                h.setSala(object.getString("sala"));
                horarios.add(h);

                qlite.save(h);
            }
            //Toast.makeText(mContext, "Dados salvo com sucesso.", Toast.LENGTH_SHORT).show();
            iniciarServico();
            return "Quantidade de horários: " + qlite.countId(horarios.get(0));
        } catch (Exception e) {
            Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
            return e.toString();
        }
    }

    private void iniciarServico() {
        try {
            //Intent i = new Intent(mContext, HorarioReceiver.class);
            //mContext.startService(i);
        } catch (Exception e) {
            Log.i("servico", "ERRO" + e);
        }
    }

    @JavascriptInterface
    public Boolean existFilho() {
        Boolean result = false;
        try {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences("ARRAIA", Context.MODE_PRIVATE);
            result = sharedPreferences.getString("FILHO", "") != "" && sharedPreferences.getString("FILHO", "") != null;
        } catch (Exception e) {
            Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    @JavascriptInterface
    public String getFilho() {
        String result = "";
        try {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences("ARRAIA", Context.MODE_PRIVATE);
            result = sharedPreferences.getString("FILHO", "");
        } catch (Exception e) {
            Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    @JavascriptInterface
    public String getImei() {
        String result = WebUsuarioActivity.getImei(mContext);
        if (result == "" || result == null) {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences("ARRAIA", Context.MODE_PRIVATE);
            result = sharedPreferences.getString("IMEI", "");
        }
        return result;
    }

    @JavascriptInterface
    public void setImei(String imei) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("ARRAIA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("IMEI", imei);
        editor.commit();
    }

}
