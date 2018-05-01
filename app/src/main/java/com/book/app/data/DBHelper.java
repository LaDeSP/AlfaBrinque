package com.book.app.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.book.app.PopularQuestoesTask;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "app_banco";
    private static final int DB_VERSION = 48;
    private static final String SEPARADOR = ", ";
    private static final String TAG = "db_tag";
    private static final String SQL_DROP_TABLE_USUARIO = "DROP TABLE IF EXISTS " + DataContract.UserContract.NOME_TABELA_USUARIO;
    private static final String SQL_DROP_TABLE_CATEGORIA = "DROP TABLE IF EXISTS " + DataContract.CategoryContract.NOME_TABELA_CATEGORIA;
    private static final String SQL_DROP_TABLE_QUESTAO = "DROP TABLE IF EXISTS " + DataContract.QuestionContract.NOME_TABELA_QUESTAO;
    private static final String SQL_DROP_TABLE_ESTADO_USUARIO = "DROP TABLE IF EXISTS " + DataContract.UserStateContract.NOME_TABELA_ESTADO_USUARIO;
    private static final String SQL_DROP_TABLE_SESSAO = "DROP TABLE IF EXISTS " + DataContract.UserSessionContract.NOME_TABELA_SESSAO;
    private static final String SQL_CREATE_TABLE_USUARIO = "CREATE TABLE " + DataContract.UserContract.NOME_TABELA_USUARIO + "(" +
            DataContract.UserContract.USUARIO_COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + SEPARADOR +
            DataContract.UserContract.USUARIO_COLUNA_APELIDO + " TEXT NOT NULL" + SEPARADOR +
            DataContract.UserContract.USUARIO_AVATAR_URL + " INTEGER NOT NULL" + ");";
    private static final String SQL_CREATE_TABLE_CATEGORIA = "CREATE TABLE " + DataContract.CategoryContract.NOME_TABELA_CATEGORIA + "(" +
            DataContract.CategoryContract.CATEGORIA_COLUNA_ID + " INTEGER PRIMARY KEY" + SEPARADOR +
            DataContract.CategoryContract.CATEGORIA_COLUNA_DESCRICAO + " TEXT NOT NULL" + ");";
    private static final String SQL_CREATE_TABLE_QUESTAO = "CREATE TABLE " + DataContract.QuestionContract.NOME_TABELA_QUESTAO + "(" +
            DataContract.QuestionContract.QUESTAO_COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + SEPARADOR +
            DataContract.QuestionContract.QUESTAO_COLUNA_CHAVE_CATEGORIA + " INTEGER NOT NULL" + SEPARADOR +
            DataContract.QuestionContract.QUESTAO_IMAGEM_URL + " INTEGER NOT NULL" + SEPARADOR +
            DataContract.QuestionContract.QUESTAO_RESPOSTA_CERTA + " TEXT NOT NULL" + SEPARADOR +
            DataContract.QuestionContract.QUESTAO_ORDEM + " INTEGER NOT NULL" + SEPARADOR +
            DataContract.QuestionContract.QUESTAO_SOM_URL + " TEXT" + SEPARADOR +

            " FOREIGN KEY (" + DataContract.QuestionContract.QUESTAO_COLUNA_CHAVE_CATEGORIA + ") REFERENCES " +
            DataContract.CategoryContract.NOME_TABELA_CATEGORIA + " (" + DataContract.CategoryContract.CATEGORIA_COLUNA_ID +
            "))";
    private static final String SQL_CREATE_TABLE_ESTADO_USUARIO = "CREATE TABLE " + DataContract.UserStateContract.NOME_TABELA_ESTADO_USUARIO + "(" +
            DataContract.UserStateContract.ESTADO_USUARIO_COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + SEPARADOR +
            DataContract.UserStateContract.ESTADO_USUARIO_CHAVE_USUARIO + " INTEGER NOT NULL" + SEPARADOR +
            DataContract.UserStateContract.ESTADO_USUARIO_CHAVE_QUESTAO + " INTEGER NOT NULL" + SEPARADOR +
            DataContract.UserStateContract.ESTADO_USUARIO_RESPONDIDO + " INTEGER NOT NULL" + SEPARADOR +
            DataContract.UserStateContract.ESTADO_USUARIO_STATUS + " INTEGER NOT NULL" + SEPARADOR +

            "FOREIGN KEY (" + DataContract.UserStateContract.ESTADO_USUARIO_CHAVE_USUARIO + ") REFERENCES " +
            DataContract.UserContract.NOME_TABELA_USUARIO + " (" + DataContract.UserContract.USUARIO_COLUNA_ID +
            ")" + SEPARADOR +

            "FOREIGN KEY (" + DataContract.UserStateContract.ESTADO_USUARIO_CHAVE_QUESTAO + ") REFERENCES " +
            DataContract.QuestionContract.NOME_TABELA_QUESTAO + " (" + DataContract.QuestionContract.QUESTAO_COLUNA_ID + "));";
    private static final String SQL_CREATE_TABLE_SESSAO = "CREATE TABLE " + DataContract.UserSessionContract.NOME_TABELA_SESSAO + "(" +
            DataContract.UserSessionContract.SESSAO_COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + SEPARADOR +
            DataContract.UserSessionContract.SESSAO_DATA_CRIACAO + " INTEGER NOT NULL" + SEPARADOR +
            DataContract.UserSessionContract.SESSAO_NUMERO_QUESTOES + " INTEGER NOT NULL" + SEPARADOR +
            DataContract.UserSessionContract.SESSAO_NUMERO_ACERTOS + " INTEGER NOT NULL" + SEPARADOR +
            DataContract.UserSessionContract.SESSAO_NUMERO_ERROS + " INTEGER NOT NULL" + SEPARADOR +

            DataContract.UserSessionContract.SESSAO_CHAVE_CATEGORIA + " INTEGER NOT NULL" + SEPARADOR +
            DataContract.UserSessionContract.SESSAO_CHAVE_USUARIO + " INTEGER NOT NULL" + SEPARADOR +

            "FOREIGN KEY (" + DataContract.UserSessionContract.SESSAO_CHAVE_CATEGORIA + ") REFERENCES " +
            DataContract.CategoryContract.NOME_TABELA_CATEGORIA + " (" + DataContract.CategoryContract.CATEGORIA_COLUNA_ID +
            ")" + SEPARADOR +

            "FOREIGN KEY (" + DataContract.UserSessionContract.SESSAO_CHAVE_USUARIO + ") REFERENCES " +
            DataContract.UserContract.NOME_TABELA_USUARIO + " (" + DataContract.UserContract.USUARIO_COLUNA_ID + "));";
    private static DBHelper instance;
    private Context mContext;

    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    public static DBHelper newInstance(Context context) {

        if (instance == null) {
            instance = new DBHelper(context, DB_NAME, null, DB_VERSION);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USUARIO);
        db.execSQL(SQL_CREATE_TABLE_CATEGORIA);
        db.execSQL(SQL_CREATE_TABLE_QUESTAO);
        db.execSQL(SQL_CREATE_TABLE_ESTADO_USUARIO);
        db.execSQL(SQL_CREATE_TABLE_SESSAO);

        Log.i(TAG, "DB Created!");

        new PopularQuestoesTask().execute(mContext);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_USUARIO);
        db.execSQL(SQL_DROP_TABLE_CATEGORIA);
        db.execSQL(SQL_DROP_TABLE_QUESTAO);
        db.execSQL(SQL_DROP_TABLE_ESTADO_USUARIO);
        db.execSQL(SQL_DROP_TABLE_SESSAO);

        Log.i(TAG, "DB Dropped!");

        onCreate(db);
    }
}
