package com.book.app.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.book.app.R;
import com.book.app.pojo.Categoria;
import com.book.app.pojo.EstadoUsuario;
import com.book.app.pojo.HistoricoUsuario;
import com.book.app.pojo.Questao;
import com.book.app.pojo.Usuario;

import java.util.ArrayList;
import java.util.Date;

public class AppDAO {

    public static final String[] PROJECTION_USUARIO = {
            DataContract.UserContract.USUARIO_COLUNA_ID,
            DataContract.UserContract.USUARIO_AVATAR_URL,
            DataContract.UserContract.USUARIO_COLUNA_APELIDO
    };
    private static final String DB_TAG = "database";
    private static SQLiteDatabase mDatabase;
    private static AppDAO daoInstance;
    DBHelper mHelper;
    private Context mContext;

    private AppDAO(Context context) {
        this.mContext = context;
        mHelper = DBHelper.newInstance(context);
        mDatabase = mHelper.getWritableDatabase();
    }

    public static AppDAO newInstance(Context context) {
        if (daoInstance == null) {
            daoInstance = new AppDAO(context.getApplicationContext());
        }

        return daoInstance;
    }

    private static int novaCategoria(Categoria categoria) {

        ContentValues values = new ContentValues();
        int returnedId = -1;

        values.put(DataContract.CategoryContract.CATEGORIA_COLUNA_ID, categoria.getCategoriaId());
        values.put(DataContract.CategoryContract.CATEGORIA_COLUNA_DESCRICAO, categoria.getCategoria());

        returnedId = (int) mDatabase.insert(DataContract.CategoryContract.NOME_TABELA_CATEGORIA, null, values);

        return returnedId;
    }

    private static int adicionarQuestao(Questao questao) {

        ContentValues values = new ContentValues();
        int returnedId = -1;

        values.put(DataContract.QuestionContract.QUESTAO_COLUNA_CHAVE_CATEGORIA, questao.getChaveCatogoria());
        values.put(DataContract.QuestionContract.QUESTAO_IMAGEM_URL, questao.getImagemUrl());
        values.put(DataContract.QuestionContract.QUESTAO_RESPOSTA_CERTA, questao.getRespostaCerta());
        values.put(DataContract.QuestionContract.QUESTAO_ORDEM, questao.getOrdem());
        values.put(DataContract.QuestionContract.QUESTAO_SOM_URL, questao.getSomUrl());


        returnedId = (int) mDatabase.insert(DataContract.QuestionContract.NOME_TABELA_QUESTAO, null, values);
        questao.setQuestaoID(returnedId);
        return returnedId;
    }

    private static Questao questaoFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.QuestionContract.QUESTAO_COLUNA_ID));
        int categoria = cursor.getInt(cursor.getColumnIndex(DataContract.QuestionContract.QUESTAO_COLUNA_CHAVE_CATEGORIA));
        int imagemUrl = cursor.getInt(cursor.getColumnIndex(DataContract.QuestionContract.QUESTAO_IMAGEM_URL));
        String respostaCerta = cursor.getString(cursor.getColumnIndex(DataContract.QuestionContract.QUESTAO_RESPOSTA_CERTA));
        int ordem = cursor.getInt(cursor.getColumnIndex(DataContract.QuestionContract.QUESTAO_ORDEM));
        String somUrl = cursor.getString(cursor.getColumnIndex(DataContract.QuestionContract.QUESTAO_SOM_URL));


        return new Questao(id, categoria, imagemUrl, respostaCerta, ordem, somUrl);
    }

    private static HistoricoUsuario historicoUsuarioFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.UserSessionContract.SESSAO_COLUNA_ID));
        long dataCriacao = cursor.getLong(cursor.getColumnIndex(DataContract.UserSessionContract.SESSAO_DATA_CRIACAO));
        int numeroQuestoes = cursor.getInt(cursor.getColumnIndex(DataContract.UserSessionContract.SESSAO_NUMERO_QUESTOES));
        int numeroAcertos = cursor.getInt(cursor.getColumnIndex(DataContract.UserSessionContract.SESSAO_NUMERO_ACERTOS));
        int numeroErros = cursor.getInt(cursor.getColumnIndex(DataContract.UserSessionContract.SESSAO_NUMERO_ERROS));
        int chaveCategoria = cursor.getInt(cursor.getColumnIndex(DataContract.UserSessionContract.SESSAO_CHAVE_CATEGORIA));
        int chaveUsuario = cursor.getInt(cursor.getColumnIndex(DataContract.UserSessionContract.SESSAO_CHAVE_USUARIO));

        return new HistoricoUsuario(id, (dataCriacao != -1 ? new Date(dataCriacao) : null), numeroQuestoes, numeroAcertos, numeroErros, chaveCategoria, chaveUsuario);
    }

    private static EstadoUsuario estadoUsuarioFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.UserStateContract.ESTADO_USUARIO_COLUNA_ID));
        int chaveUsuario = cursor.getInt(cursor.getColumnIndex(DataContract.UserStateContract.ESTADO_USUARIO_CHAVE_USUARIO));
        int chaveQuestao = cursor.getInt(cursor.getColumnIndex(DataContract.UserStateContract.ESTADO_USUARIO_CHAVE_QUESTAO));
        int respondido = cursor.getInt(cursor.getColumnIndex(DataContract.UserStateContract.ESTADO_USUARIO_RESPONDIDO));
        int status = cursor.getInt(cursor.getColumnIndex(DataContract.UserStateContract.ESTADO_USUARIO_STATUS));

        return new EstadoUsuario(id, chaveUsuario, chaveQuestao, respondido, status);
    }

    private static Usuario usuarioFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.UserContract.USUARIO_COLUNA_ID));
        String apelido = cursor.getString(cursor.getColumnIndex(DataContract.UserContract.USUARIO_COLUNA_APELIDO));
        int avatarUrl = cursor.getInt(cursor.getColumnIndex(DataContract.UserContract.USUARIO_AVATAR_URL));

        return new Usuario(id, apelido, avatarUrl);
    }

    public int novoUsuario(Usuario usuario) {

        ContentValues values = new ContentValues();
        int returnedId = -1;

        values.put(DataContract.UserContract.USUARIO_COLUNA_APELIDO, usuario.getApelido());
        values.put(DataContract.UserContract.USUARIO_AVATAR_URL, usuario.getAvatarUrl());

        returnedId = (int) mDatabase.insert(DataContract.UserContract.NOME_TABELA_USUARIO, null, values);
        usuario.setUsuarioId(returnedId);
        return returnedId;
    }

    public Questao getQuestaoById(int categoria, int ordem) {

        String selectQuery =
                "SELECT * FROM " + DataContract.QuestionContract.NOME_TABELA_QUESTAO +
                        " WHERE " + "(" + DataContract.QuestionContract.QUESTAO_COLUNA_CHAVE_CATEGORIA + "=" + categoria + ")" +
                        " AND " + "(" + DataContract.QuestionContract.QUESTAO_ORDEM + "=" + ordem + ")";

        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

        Questao questao = null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    questao = AppDAO.questaoFromCursor(cursor);
                } while (cursor.moveToNext());
            }

            return questao;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public Usuario getUsuarioByPosition(int index) {

        String selectQuery =
                "SELECT * FROM " + DataContract.UserContract.NOME_TABELA_USUARIO + "";

        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

        boolean hasEntry;

        try {
            hasEntry = cursor.moveToPosition(index);
        } catch (IndexOutOfBoundsException e) {
            hasEntry = false;
        }


        Usuario usuario = null;
        if (hasEntry) {
            usuario = AppDAO.usuarioFromCursor(cursor);
        }

        return usuario;
    }

    private void popularCategorias() {
        Categoria categoria1 = new Categoria(1, "Animais");
        Categoria categoria2 = new Categoria(2, "Alimentos");
        Categoria categoria3 = new Categoria(3, "Objetos");

        if (novaCategoria(categoria1) != -1 && novaCategoria(categoria2) != -1 && novaCategoria(categoria3) != -1) {
            Log.i(DB_TAG, "CATEGORIAS ADICIONADAS COM SUCESSO!");
        }
    }

    public int popularQuestoesAnimais() {
        popularCategorias();

        int sucesso = 0;

        String somCachorroPath = "android.resource://" + mContext.getPackageName() + "/" + R.raw.latido;
        String somGatoPath = "android.resource://" + mContext.getPackageName() + "/" + R.raw.gato;
        String somJacarePath = "android.resource://" + mContext.getPackageName() + "/" + R.raw.crocodilo;
        String somLeaoPath = "android.resource://" + mContext.getPackageName() + "/" + R.raw.leao;
        String somVacaPath = "android.resource://" + mContext.getPackageName() + "/" + R.raw.vaca;

        int resIdCachorro = mContext.getResources().getIdentifier("cachorro", "drawable", mContext.getPackageName());
        int resIdGato = mContext.getResources().getIdentifier("gato", "drawable", mContext.getPackageName());
        int resIdJacare = mContext.getResources().getIdentifier("jacare", "drawable", mContext.getPackageName());
        int resIdLeao = mContext.getResources().getIdentifier("leao", "drawable", mContext.getPackageName());
        int resIdVaca = mContext.getResources().getIdentifier("vaca", "drawable", mContext.getPackageName());

        Questao animal1 = new Questao(1, resIdCachorro, "cachorro", 1, somCachorroPath);
        Questao animal2 = new Questao(1, resIdGato, "gato", 2, somGatoPath);
        Questao animal3 = new Questao(1, resIdJacare, "jacaré", 3, somJacarePath);
        Questao animal4 = new Questao(1, resIdLeao, "leão", 4, somLeaoPath);
        Questao animal5 = new Questao(1, resIdVaca, "vaca", 5, somVacaPath);

        if (adicionarQuestao(animal1) != -1 && adicionarQuestao(animal2) != -1 && adicionarQuestao(animal3) != -1 && adicionarQuestao(animal4) != -1 && adicionarQuestao(animal5) != -1) {
            popularQuestoesAlimentos();

            sucesso = 1;
        }

        return sucesso;
    }

    private void popularQuestoesAlimentos() {

        int resIdCenoura = mContext.getResources().getIdentifier("cenoura", "drawable", mContext.getPackageName());
        int resIdBanana = mContext.getResources().getIdentifier("banana", "drawable", mContext.getPackageName());
        int resIdSorvete = mContext.getResources().getIdentifier("sorvete", "drawable", mContext.getPackageName());
        int resIdOvo = mContext.getResources().getIdentifier("ovo", "drawable", mContext.getPackageName());
        int resIdPizza = mContext.getResources().getIdentifier("uva", "drawable", mContext.getPackageName());

        Questao alimento1 = new Questao(2, resIdCenoura, "cenoura", 1, null);
        Questao alimento2 = new Questao(2, resIdBanana, "banana", 2, null);
        Questao alimento3 = new Questao(2, resIdSorvete, "sorvete", 3, null);
        Questao alimento4 = new Questao(2, resIdOvo, "ovo", 4, null);
        Questao alimento5 = new Questao(2, resIdPizza, "uva", 5, null);

        if (adicionarQuestao(alimento1) != -1 && adicionarQuestao(alimento2) != -1 && adicionarQuestao(alimento3) != -1 && adicionarQuestao(alimento4) != -1 && adicionarQuestao(alimento5) != -1) {
            popularQuestoesObjetos();
        }
    }

    private int popularQuestoesObjetos() {

        int sucesso = 0;

        int resIdCamera = mContext.getResources().getIdentifier("camera", "drawable", mContext.getPackageName());
        int resIdBicicleta = mContext.getResources().getIdentifier("bicicleta", "drawable", mContext.getPackageName());
        int resIdLanterna = mContext.getResources().getIdentifier("lanterna", "drawable", mContext.getPackageName());
        int resIdMochila = mContext.getResources().getIdentifier("mochila", "drawable", mContext.getPackageName());
        int resIdPanela = mContext.getResources().getIdentifier("panela", "drawable", mContext.getPackageName());

        Questao objeto1 = new Questao(3, resIdCamera, "câmera", 1, null);
        Questao objeto2 = new Questao(3, resIdBicicleta, "bicicleta", 2, null);
        Questao objeto3 = new Questao(3, resIdLanterna, "lanterna", 3, null);
        Questao objeto4 = new Questao(3, resIdMochila, "mochila", 4, null);
        Questao objeto5 = new Questao(3, resIdPanela, "panela", 5, null);

        if (adicionarQuestao(objeto1) != -1 && adicionarQuestao(objeto2) != -1 && adicionarQuestao(objeto3) != -1 && adicionarQuestao(objeto4) != -1 && adicionarQuestao(objeto5) != -1) {
            sucesso = 1;
        }

        return sucesso;

    }

    public int countQuestao(int idUsuario, int idQuestao) {

        String countQuery =
                "SELECT COUNT(" + DataContract.UserStateContract.NOME_TABELA_ESTADO_USUARIO + "." + DataContract.UserStateContract.ESTADO_USUARIO_COLUNA_ID + ") FROM " + DataContract.UserStateContract.NOME_TABELA_ESTADO_USUARIO + "," + DataContract.UserContract.NOME_TABELA_USUARIO + ", " + DataContract.QuestionContract.NOME_TABELA_QUESTAO +
                        " WHERE " + "(" + DataContract.UserStateContract.ESTADO_USUARIO_CHAVE_USUARIO + "=" + DataContract.UserContract.NOME_TABELA_USUARIO +
                        "." + DataContract.UserContract.USUARIO_COLUNA_ID + ")" +
                        " AND " + "(" + DataContract.UserStateContract.ESTADO_USUARIO_CHAVE_QUESTAO + "=" + DataContract.QuestionContract.NOME_TABELA_QUESTAO + "." + DataContract.QuestionContract.QUESTAO_COLUNA_ID + ")" +
                        " AND " + "(" + DataContract.UserStateContract.ESTADO_USUARIO_CHAVE_USUARIO + " = " + idUsuario + ")" +
                        " AND " + "(" + DataContract.UserStateContract.ESTADO_USUARIO_CHAVE_QUESTAO + " = " + idQuestao + ");";

        Cursor cursor = mDatabase.rawQuery(countQuery, null);

        Log.i("COUNT", countQuery);

        cursor.moveToFirst();
        int count = cursor.getInt(0);

        cursor.close();

        return count;
    }

    public boolean updateResposta(EstadoUsuario estadoUsuario) {

        String updateQuery = "UPDATE " + DataContract.UserStateContract.NOME_TABELA_ESTADO_USUARIO + " SET " + DataContract.UserStateContract.ESTADO_USUARIO_STATUS +
                " = " + estadoUsuario.getStatusQuestao() + " WHERE (" + DataContract.UserStateContract.ESTADO_USUARIO_COLUNA_ID + " = " + estadoUsuario.getEstadoUsuarioID() + ");";

        Cursor cursor = mDatabase.rawQuery(updateQuery, null);

        boolean updated = cursor.moveToFirst();

        cursor.close();

        return updated;

    }

    public EstadoUsuario getEstadoUsuario(int idUsuario, int idQuestao) {

        String selectQuery =
                "SELECT " + DataContract.UserStateContract.NOME_TABELA_ESTADO_USUARIO + "." + DataContract.UserStateContract.ESTADO_USUARIO_COLUNA_ID + ", estado_usuario_chave_usuario, estado_usuario_chave_questao, estado_usuario_respondido, estado_usuario_status FROM " + DataContract.UserStateContract.NOME_TABELA_ESTADO_USUARIO + "," + DataContract.UserContract.NOME_TABELA_USUARIO + ", " + DataContract.QuestionContract.NOME_TABELA_QUESTAO +
                        " WHERE " + "(" + DataContract.UserStateContract.ESTADO_USUARIO_CHAVE_USUARIO + "=" + DataContract.UserContract.NOME_TABELA_USUARIO +
                        "." + DataContract.UserContract.USUARIO_COLUNA_ID + ")" +
                        " AND " + "(" + DataContract.UserStateContract.ESTADO_USUARIO_CHAVE_QUESTAO + "=" + DataContract.QuestionContract.NOME_TABELA_QUESTAO + "." + DataContract.QuestionContract.QUESTAO_COLUNA_ID + ")" +
                        " AND " + "(" + DataContract.UserStateContract.ESTADO_USUARIO_CHAVE_USUARIO + " = " + idUsuario + ")" +
                        " AND " + "(" + DataContract.UserStateContract.ESTADO_USUARIO_CHAVE_QUESTAO + " = " + idQuestao + ");";

        Log.i("SELECT", selectQuery);

        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

        EstadoUsuario estadoUsuario = null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    estadoUsuario = AppDAO.estadoUsuarioFromCursor(cursor);
                } while (cursor.moveToNext());
            }

            return estadoUsuario;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public int inserirEstadoUsuario(EstadoUsuario estadoUsuario) {

        ContentValues values = new ContentValues();
        int returnedId = -1;

        values.put(DataContract.UserStateContract.ESTADO_USUARIO_CHAVE_USUARIO, estadoUsuario.getChaveUsuario());
        values.put(DataContract.UserStateContract.ESTADO_USUARIO_CHAVE_QUESTAO, estadoUsuario.getChaveQuestao());
        values.put(DataContract.UserStateContract.ESTADO_USUARIO_RESPONDIDO, estadoUsuario.getQuestaoRespondida());
        values.put(DataContract.UserStateContract.ESTADO_USUARIO_STATUS, estadoUsuario.getStatusQuestao());


        returnedId = (int) mDatabase.insert(DataContract.UserStateContract.NOME_TABELA_ESTADO_USUARIO, null, values);
        estadoUsuario.setEstadoUsuarioID(returnedId);

        return returnedId;
    }

    public int inserirHistoricoUsuario(HistoricoUsuario historicoUsuario) {

        ContentValues values = new ContentValues();
        int returnedId = -1;

        values.put(DataContract.UserSessionContract.SESSAO_DATA_CRIACAO, historicoUsuario.getDataCriacao() == null ? -1 : historicoUsuario.getDataCriacao().getTime());
        values.put(DataContract.UserSessionContract.SESSAO_NUMERO_QUESTOES, historicoUsuario.getNumeroQuestoes());
        values.put(DataContract.UserSessionContract.SESSAO_NUMERO_ACERTOS, historicoUsuario.getNumeroAcertos());
        values.put(DataContract.UserSessionContract.SESSAO_NUMERO_ERROS, historicoUsuario.getNumeroErros());
        values.put(DataContract.UserSessionContract.SESSAO_CHAVE_CATEGORIA, historicoUsuario.getCategoria());
        values.put(DataContract.UserSessionContract.SESSAO_CHAVE_USUARIO, historicoUsuario.getUsuario());

        returnedId = (int) mDatabase.insert(DataContract.UserSessionContract.NOME_TABELA_SESSAO, null, values);
        historicoUsuario.setHistoricoId(returnedId);

        return returnedId;
    }

    public ArrayList<HistoricoUsuario> listarHistoricoUsuario(int userID) {

        ArrayList<HistoricoUsuario> sessionList = new ArrayList<>();

        String selectQuery =
                "SELECT * FROM " + DataContract.UserSessionContract.NOME_TABELA_SESSAO + "," + DataContract.CategoryContract.NOME_TABELA_CATEGORIA + "," + DataContract.UserContract.NOME_TABELA_USUARIO +
                        " WHERE " + "(" + DataContract.UserSessionContract.SESSAO_CHAVE_CATEGORIA + "=" + DataContract.CategoryContract.NOME_TABELA_CATEGORIA + "." + DataContract.CategoryContract.CATEGORIA_COLUNA_ID + ")" +
                        " AND " + "(" + DataContract.UserSessionContract.SESSAO_CHAVE_USUARIO + "=" + DataContract.UserContract.NOME_TABELA_USUARIO + "." + DataContract.UserContract.USUARIO_COLUNA_ID + ")" +
                        " AND " + "(" + DataContract.UserSessionContract.SESSAO_CHAVE_USUARIO + " = " + userID + ")";

        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

        Log.i("DB_TEST_QUERY", selectQuery);

        try {
            if (cursor.moveToFirst()) {
                do {
                    HistoricoUsuario historico = AppDAO.historicoUsuarioFromCursor(cursor);
                    sessionList.add(historico);
                } while (cursor.moveToNext());
            }

            return sessionList;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public HistoricoUsuario getAcessoData(String flag, int usuario) {

        String selectQuery =
                "SELECT * FROM " + DataContract.UserSessionContract.NOME_TABELA_SESSAO + "," + DataContract.UserContract.NOME_TABELA_USUARIO +
                        " WHERE " + DataContract.UserSessionContract.NOME_TABELA_SESSAO + "." + DataContract.UserSessionContract.SESSAO_CHAVE_USUARIO + "=" + DataContract.UserContract.NOME_TABELA_USUARIO +
                        "." + DataContract.UserContract.USUARIO_COLUNA_ID + " AND " + DataContract.UserSessionContract.SESSAO_CHAVE_USUARIO +
                        "=" + usuario + " ORDER BY " + DataContract.UserSessionContract.SESSAO_DATA_CRIACAO + " " + flag + " limit 1";

        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

        HistoricoUsuario historicoUsuario = null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    historicoUsuario = AppDAO.historicoUsuarioFromCursor(cursor);
                } while (cursor.moveToNext());
            }

            return historicoUsuario;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public HistoricoUsuario getHistoricoUsuarioPrimeiroUltimoAcesso(String flag, int categoria, int usuario) {

        String selectQuery =
                "SELECT * FROM " + DataContract.UserSessionContract.NOME_TABELA_SESSAO + "," + DataContract.CategoryContract.NOME_TABELA_CATEGORIA + "," + DataContract.UserContract.NOME_TABELA_USUARIO +
                        " WHERE " + DataContract.UserSessionContract.NOME_TABELA_SESSAO + "." +
                        DataContract.UserSessionContract.SESSAO_CHAVE_CATEGORIA + "=" + DataContract
                        .CategoryContract.NOME_TABELA_CATEGORIA + "." + DataContract.CategoryContract.CATEGORIA_COLUNA_ID + " AND " +
                        DataContract.UserSessionContract.SESSAO_CHAVE_CATEGORIA + " = " + categoria + " AND " +
                        DataContract.UserSessionContract.NOME_TABELA_SESSAO + "." +
                        DataContract.UserSessionContract.SESSAO_CHAVE_USUARIO + "=" + DataContract.UserContract.NOME_TABELA_USUARIO +
                        "." + DataContract.UserContract.USUARIO_COLUNA_ID + " AND " + DataContract.UserSessionContract.SESSAO_CHAVE_USUARIO +
                        "=" + usuario + " ORDER BY " + DataContract.UserSessionContract.SESSAO_DATA_CRIACAO + " " + flag + " limit 1";

        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

        HistoricoUsuario historicoUsuario = null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    historicoUsuario = AppDAO.historicoUsuarioFromCursor(cursor);
                } while (cursor.moveToNext());
            }

            return historicoUsuario;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public Usuario getUsuarioById(int usuarioId) {

        String selectQuery =
                "SELECT * FROM " + DataContract.UserContract.NOME_TABELA_USUARIO +
                        " WHERE " + "(" + DataContract.UserContract.USUARIO_COLUNA_ID + "=" + usuarioId + ")";

        Cursor cursor = mDatabase.rawQuery(selectQuery, null);

        Usuario usuario = null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    usuario = AppDAO.usuarioFromCursor(cursor);
                } while (cursor.moveToNext());
            }

            return usuario;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public ArrayList<Usuario> listarUsuarios() {

        ArrayList<Usuario> usuarios = new ArrayList<>();

        //Cursor cursor = mDatabase.query(DataContract.UserContract.NOME_TABELA_USUARIO, PROJECTION_USUARIO, null, null, null, null, DataContract.UserContract.USUARIO_COLUNA_APELIDO);
        Cursor cursor = mDatabase.query(DataContract.UserContract.NOME_TABELA_USUARIO, PROJECTION_USUARIO, null, null, null, null, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Usuario usuario = AppDAO.usuarioFromCursor(cursor);
                    usuarios.add(usuario);
                } while (cursor.moveToNext());
            }

            return usuarios;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }


}
