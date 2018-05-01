package com.book.app.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.book.app.EscolherUsuario;
import com.book.app.R;
import com.book.app.data.AppDAO;
import com.book.app.pojo.Usuario;

public class TelaAdicionarUsuario extends AppCompatActivity {

    private static final String UNDERSCORE_SEP = "_";
    private EditText edtNovoUsuario;
    private ImageView imgAvatar;
    private int userCodeExtra;
    private String avatar;
    private int avatarUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adicionar_usuario);

        edtNovoUsuario = (EditText) findViewById(R.id.edt_username);
        imgAvatar = (ImageView) findViewById(R.id.img_user_avatar);

        userCodeExtra = getIntent().getIntExtra(EscolherUsuario.NEW_PLAYER_EXTRA, -1);

        avatar = "avatar" + UNDERSCORE_SEP + userCodeExtra;

        avatarUrl = getResources().getIdentifier(avatar, "drawable", getPackageName());

        imgAvatar.setImageResource(avatarUrl);


    }

    public void salvarUsuario(View view) {


        if (edtNovoUsuario.getText().toString().isEmpty()) {
            edtNovoUsuario.setError("Apelido!");
        } else {
            AppDAO.newInstance(this).novoUsuario(new Usuario(edtNovoUsuario.getText().toString().trim(), avatarUrl));
            setResult(RESULT_OK);
            finish();
        }

    }
}
