package com.arraiax.kairi.arraiax;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class HorarioSQlite extends SQLiteOpenHelper {

    private Context context;
    private static final String TAG = "sql_arraia";
    private static final String NOME_BANCO = "arria";
    private static final String TABLE = "horario";
    private static final int VERSAO = 1;

    public HorarioSQlite(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Criando a tabela");
        db.execSQL(
                "CREATE TABLE " + TABLE + " (" +
                        "  idhorario INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "  idkey VARCHAR(50) NOT NULL," +
                        "  descricao VARCHAR(200)," +
                        "  escola VARCHAR(200) NOT NULL," +
                        "  horariofin VARCHAR(10) NOT NULL," +
                        "  horarioini VARCHAR(10) NOT NULL," +
                        "  latitude double NOT NULL," +
                        "  longitude double NOT NULL," +
                        "  sala VARCHAR(100) NOT NULL" +
                        ");"
        );
    }

    public void save(Horario horario) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("idkey", horario.getIdKey());
            values.put("descricao", horario.getDescricao());
            values.put("escola", horario.getEscola());
            values.put("horariofin", horario.getHorariofin());
            values.put("horarioini", horario.getHorarioini());
            values.put("latitude", horario.getLatitude());
            values.put("longitude", horario.getLongitude());
            values.put("sala", horario.getSala());
            if (true) {
                db.delete(TABLE, null, null);
                db.insert(TABLE, "", values);
                Log.i(TAG, "insert ok");
                Log.i(TAG, "" + findAll().size());
            } else {
                String idhorario = String.valueOf(horario.getIdHorario());
                String[] _idhorario = new String[]{idhorario};
                db.update(TABLE, values, "idhorario=?", _idhorario);
                Log.i(TAG, "update ok");
            }
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        } finally {
            db.close();
        }
    }

    public int countId(Horario horario) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            Cursor c = db.query(TABLE,
                    null,
                    "escola='" + horario.getEscola() + "'"
                            + " AND sala='" + horario.getSala() + "'"
                            + " AND horarioini='" + horario.getHorarioini() + "'"
                            + " AND horariofin='" + horario.getHorariofin() + "'",
                    null,
                    null,
                    null,
                    null,
                    null);
            Log.i(TAG, "" + (c.getCount() < 1));
            return c.getCount();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return 0;
        } finally {
            //db.close();
        }
    }

    public Horario find(int idhorario) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            Cursor c = db.query(TABLE,
                    null,
                    "idhorario=" + idhorario,
                    null,
                    null,
                    null,
                    null,
                    null);
            Log.i(TAG, "find()");
            return toList(c).get(0);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        } finally {
            db.close();
        }
    }

    public List<Horario> findAll() {
        SQLiteDatabase db = getWritableDatabase();
        try {
            Cursor c = db.query(TABLE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
            Log.i(TAG, "find()");
            return toList(c);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        } finally {
            //db.close();
        }
    }

    public void delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, null, null);
        db.close();
    }

    private List<Horario> toList(Cursor c) {
        Log.i(TAG, "" + c.getCount());
        List<Horario> list = new ArrayList<Horario>();
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                Horario horario = new Horario();
                horario.setIdHorario(c.getInt(c.getColumnIndex("idhorario")));
                horario.setEscola(c.getString(c.getColumnIndex("escola")));
                horario.setHorariofin(c.getString(c.getColumnIndex("horariofin")));
                horario.setHorarioini(c.getString(c.getColumnIndex("horarioini")));
                horario.setLatitude(c.getDouble(c.getColumnIndex("latitude")));
                horario.setLongitude(c.getDouble(c.getColumnIndex("longitude")));
                horario.setSala(c.getString(c.getColumnIndex("sala")));
                list.add(horario);
            } while (c.moveToNext());
        }
        return list;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
