package br.ufms.ladesp.alfabrinque;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.ufms.ladesp.alfabrinque.data.AppDAO;
import br.ufms.ladesp.alfabrinque.pojo.Usuario;
import br.ufms.ladesp.alfabrinque.swipes.EscolherCategoria;
import br.ufms.ladesp.alfabrinque.ui.TelaAdicionarUsuario;
import br.ufms.ladesp.alfabrinque.ui.TelaFeedbackUsuario;
import br.ufms.ladesp.alfabrinque.util.UsuarioEscolhido;
import br.ufms.ladesp.alfabrinque.util.UtilitarioUI;

public class EscolherUsuario extends AppCompatActivity implements View.OnClickListener {

    public static final int ADD_USER_REQ_CODE = 100;
    public static final String NEW_PLAYER_EXTRA = "newPlayerExtra";

    private ImageButton btnNovoJogador1;
    private ImageButton btnNovoJogador2;
    private ImageButton btnNovoJogador3;

    private UsuarioEscolhido usuarioEscolhidoPreference;

    private LinearLayout containerJogador1;
    private LinearLayout containerJogador2;
    private LinearLayout containerJogador3;

    private LinearLayout containerAddJogador1;
    private LinearLayout containerAddJogador2;
    private LinearLayout containerAddJogador3;

    private TextView txtUsuario1;
    private TextView txtUsuario2;
    private TextView txtUsuario3;

    private ImageView imgAvatarUsuario1;
    private ImageView imgAvatarUsuario2;
    private ImageView imgAvatarUsuario3;

    private AppDAO dao;

    private Usuario usuario1;
    private Usuario usuario2;
    private Usuario usuario3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(br.ufms.ladesp.alfabrinque.R.layout.activity_escolher_usuario);
        UtilitarioUI.hideSystemUI(getWindow());

        btnNovoJogador1 = (ImageButton) findViewById(br.ufms.ladesp.alfabrinque.R.id.btn_novo_jogador_1);
        btnNovoJogador2 = (ImageButton) findViewById(br.ufms.ladesp.alfabrinque.R.id.btn_novo_jogador_2);
        btnNovoJogador3 = (ImageButton) findViewById(br.ufms.ladesp.alfabrinque.R.id.btn_novo_jogador_3);

        usuarioEscolhidoPreference = new UsuarioEscolhido(this);

        dao = AppDAO.newInstance(this);

        containerJogador1 = (LinearLayout) findViewById(br.ufms.ladesp.alfabrinque.R.id.container_jogador1);
        containerJogador2 = (LinearLayout) findViewById(br.ufms.ladesp.alfabrinque.R.id.container_jogador2);
        containerJogador3 = (LinearLayout) findViewById(br.ufms.ladesp.alfabrinque.R.id.container_jogador3);

        containerAddJogador1 = (LinearLayout) findViewById(br.ufms.ladesp.alfabrinque.R.id.container_add_jogador1);
        containerAddJogador2 = (LinearLayout) findViewById(br.ufms.ladesp.alfabrinque.R.id.container_add_jogador2);
        containerAddJogador3 = (LinearLayout) findViewById(br.ufms.ladesp.alfabrinque.R.id.container_add_jogador3);

        txtUsuario1 = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_usuario1);
        txtUsuario2 = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_usuario2);
        txtUsuario3 = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_usuario3);

        imgAvatarUsuario1 = (ImageView) findViewById(br.ufms.ladesp.alfabrinque.R.id.img_avatar_1);
        imgAvatarUsuario2 = (ImageView) findViewById(br.ufms.ladesp.alfabrinque.R.id.img_avatar_2);
        imgAvatarUsuario3 = (ImageView) findViewById(br.ufms.ladesp.alfabrinque.R.id.img_avatar_3);

        btnNovoJogador1.setOnClickListener(this);
        btnNovoJogador2.setOnClickListener(this);
        btnNovoJogador3.setOnClickListener(this);

        carregarUsuarios();
    }

    private void carregarUsuarios() {
        usuario1 = dao.getUsuarioByPosition(0);
        usuario2 = dao.getUsuarioByPosition(1);
        usuario3 = dao.getUsuarioByPosition(2);

        if (usuario1 != null) {
            containerJogador1.setVisibility(View.VISIBLE);
            containerAddJogador1.setVisibility(View.GONE);

            txtUsuario1.setText(usuario1.getApelido());
            imgAvatarUsuario1.setImageResource(usuario1.getAvatarUrl());
        } else {
            containerAddJogador1.setVisibility(View.VISIBLE);
            containerJogador1.setVisibility(View.GONE);
        }

        if (usuario2 != null) {
            containerJogador2.setVisibility(View.VISIBLE);
            containerAddJogador2.setVisibility(View.GONE);

            txtUsuario2.setText(usuario2.getApelido());
            imgAvatarUsuario2.setImageResource(usuario2.getAvatarUrl());
        } else {
            containerAddJogador2.setVisibility(View.VISIBLE);
            containerJogador2.setVisibility(View.GONE);
        }

        if (usuario3 != null) {
            containerJogador3.setVisibility(View.VISIBLE);
            containerAddJogador3.setVisibility(View.GONE);

            txtUsuario3.setText(usuario3.getApelido());
            imgAvatarUsuario3.setImageResource(usuario3.getAvatarUrl());
        } else {
            containerAddJogador3.setVisibility(View.VISIBLE);
            containerJogador3.setVisibility(View.GONE);
        }

    }

    public void setUsuario1(View view) {
        MediaPlayer mp = MediaPlayer.create(this, br.ufms.ladesp.alfabrinque.R.raw.default_click_sound);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();

            }
        });

        if (usuario1 != null) {
            usuarioEscolhidoPreference.setName(usuario1.getApelido());
            usuarioEscolhidoPreference.setId(usuario1.getUsuarioId());
        }
        startActivity(new Intent(this, EscolherCategoria.class));
    }

    public void setUsuario2(View view) {
        MediaPlayer mp = MediaPlayer.create(this, br.ufms.ladesp.alfabrinque.R.raw.default_click_sound);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();

            }
        });

        if (usuario2 != null) {
            usuarioEscolhidoPreference.setName(usuario2.getApelido());
            usuarioEscolhidoPreference.setId(usuario2.getUsuarioId());
        }

        startActivity(new Intent(this, EscolherCategoria.class));
    }

    public void setUsuario3(View view) {
        MediaPlayer mp = MediaPlayer.create(this, br.ufms.ladesp.alfabrinque.R.raw.default_click_sound);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();

            }
        });

        if (usuario3 != null) {
            usuarioEscolhidoPreference.setName(usuario3.getApelido());
            usuarioEscolhidoPreference.setId(usuario3.getUsuarioId());
        }

        startActivity(new Intent(this, EscolherCategoria.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == ADD_USER_REQ_CODE) {
            carregarUsuarios();

            MediaPlayer mp = MediaPlayer.create(this, br.ufms.ladesp.alfabrinque.R.raw.user_created_success);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();

                }
            });
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case br.ufms.ladesp.alfabrinque.R.id.btn_novo_jogador_1:
                Intent intent = new Intent(this, TelaAdicionarUsuario.class);
                intent.putExtra(NEW_PLAYER_EXTRA, 1);
                startActivityForResult(intent, ADD_USER_REQ_CODE);
                break;
            case br.ufms.ladesp.alfabrinque.R.id.btn_novo_jogador_2:
                Intent intent2 = new Intent(this, TelaAdicionarUsuario.class);
                intent2.putExtra(NEW_PLAYER_EXTRA, 2);
                startActivityForResult(intent2, ADD_USER_REQ_CODE);
                break;
            case br.ufms.ladesp.alfabrinque.R.id.btn_novo_jogador_3:
                Intent intent3 = new Intent(this, TelaAdicionarUsuario.class);
                intent3.putExtra(NEW_PLAYER_EXTRA, 3);
                startActivityForResult(intent3, ADD_USER_REQ_CODE);
                break;
        }

        MediaPlayer mp = MediaPlayer.create(this, br.ufms.ladesp.alfabrinque.R.raw.default_click_sound);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();

            }
        });
    }

    public void mostrarInstrucoes(View view) {
        new AlertDialog.Builder(this)
                .setTitle(getString(br.ufms.ladesp.alfabrinque.R.string.txt_instrucoes_title))
                .setMessage(Html.fromHtml(getString(br.ufms.ladesp.alfabrinque.R.string.txt_instrucoes_body)))
                .setIcon(br.ufms.ladesp.alfabrinque.R.drawable.ic_info_black_24dp_dialog)
                .setPositiveButton(getString(br.ufms.ladesp.alfabrinque.R.string.txt_dialog_fechar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }

    public void mostrarFeedback(View view) {

        MediaPlayer mp = MediaPlayer.create(this, br.ufms.ladesp.alfabrinque.R.raw.default_click_sound);
        if (!mp.isPlaying()) {
            mp.start();
        }

        startActivity(new Intent(this, TelaFeedbackUsuario.class));
    }

    public void mostrarVolume(View view) {
        MediaPlayer mp = MediaPlayer.create(this, br.ufms.ladesp.alfabrinque.R.raw.default_click_sound);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();

            }
        });

        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audio != null) {
            audio.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI);
        }
    }
}
