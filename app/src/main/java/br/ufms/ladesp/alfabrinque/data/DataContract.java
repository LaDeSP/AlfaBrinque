package br.ufms.ladesp.alfabrinque.data;

import android.provider.BaseColumns;

public class DataContract {

    public class UserContract implements BaseColumns {
        public static final String NOME_TABELA_USUARIO = "usuario";

        public static final String USUARIO_COLUNA_ID = "_id";

        public static final String USUARIO_COLUNA_APELIDO = "usuario_apelido";

        public static final String USUARIO_AVATAR_URL = "usuario_avatar";
    }

    public class QuestionContract implements BaseColumns {

        public static final String NOME_TABELA_QUESTAO = "questao";

        public static final String QUESTAO_COLUNA_ID = "_id";

        public static final String QUESTAO_COLUNA_CHAVE_CATEGORIA = "questao_chave_categoria";

        public static final String QUESTAO_IMAGEM_URL = "questao_imagem_url";

        public static final String QUESTAO_RESPOSTA_CERTA = "questao_resposta_certa";

        public static final String QUESTAO_ORDEM = "questao_ordem";

        public static final String QUESTAO_SOM_URL = "questao_som_url";
    }

    public class CategoryContract implements BaseColumns {
        public static final String NOME_TABELA_CATEGORIA = "categoria";

        public static final String CATEGORIA_COLUNA_ID = "_id";

        public static final String CATEGORIA_COLUNA_DESCRICAO = "categoria_descricao";
    }

    public class UserStateContract implements BaseColumns {
        public static final String NOME_TABELA_ESTADO_USUARIO = "estado_usuario";

        public static final String ESTADO_USUARIO_COLUNA_ID = "_id";

        public static final String ESTADO_USUARIO_CHAVE_USUARIO = "estado_usuario_chave_usuario";

        public static final String ESTADO_USUARIO_CHAVE_QUESTAO = "estado_usuario_chave_questao";

        public static final String ESTADO_USUARIO_RESPONDIDO = "estado_usuario_respondido";

        public static final String ESTADO_USUARIO_STATUS = "estado_usuario_status";
    }

    public class UserSessionContract implements BaseColumns {
        public static final String NOME_TABELA_SESSAO = "sessao_usuario";

        public static final String SESSAO_COLUNA_ID = "_id";

        public static final String SESSAO_DATA_CRIACAO = "sessao_usuario_data_criacao";

        public static final String SESSAO_NUMERO_QUESTOES = "sessao_usuario_numero_questoes";

        public static final String SESSAO_NUMERO_ACERTOS = "sessao_usuario_numero_acertos";

        public static final String SESSAO_NUMERO_ERROS = "sessao_usuario_numero_erros";

        public static final String SESSAO_CHAVE_CATEGORIA = "sessao_usuario_chave_categoria";

        public static final String SESSAO_CHAVE_USUARIO = "sessao_usuario_chave_usuario";
    }


}
