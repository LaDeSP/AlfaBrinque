
package com.book.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class UsuarioEscolhido {

    private SharedPreferences prefs;

    private Editor editor;

    final int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "UsuarioEscolhido";
    private static final String USER_APELIDO = "apelido";
    private static final String USER_ID = "id";

    Context mContext;

    public UsuarioEscolhido(Context context) {
        this.mContext = context;
        prefs = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }


    public String getName() {
        return prefs.getString(USER_APELIDO, "");
    }

    public int getId() {
        return prefs.getInt(USER_ID, -1);
    }

    public void setName(String apelido) {
        editor = prefs.edit();
        editor.putString(USER_APELIDO, apelido);
        editor.apply();
    }

    public void setId(int id) {
        editor = prefs.edit();
        editor.putInt(USER_ID, id);
        editor.apply();
    }

}
