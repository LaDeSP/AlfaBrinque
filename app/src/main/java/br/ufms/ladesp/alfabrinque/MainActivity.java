package br.ufms.ladesp.alfabrinque;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import br.ufms.ladesp.alfabrinque.data.AppDAO;
import br.ufms.ladesp.alfabrinque.util.UtilitarioUI;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(br.ufms.ladesp.alfabrinque.R.layout.activity_main);

        UtilitarioUI.hideSystemUI(getWindow());

        AppDAO.newInstance(this);

    /*    btnIniciar = (ImageButton) findViewById(R.id.btn_iniciar);

        new MaterialShowcaseView.Builder(this)
                .setTarget(findViewById())
                .setDismissText("GOT IT")
                .setContentText("This is some amazing feature you should know about")
                .setDelay(2000) // optional but starting animations immediately in onCreate can make them choppy
                .singleUse("ShowcaseIS") // provide a unique ID used to ensure it is only shown once
                .show();*/
    }

    public void iniciarJogo(View view) {
        Intent intent = new Intent(this, EscolherUsuario.class);

        //Intent intent = new Intent(this, QuestoesRespondidasSucesso.class);
        startActivity(intent);

        MediaPlayer mp = MediaPlayer.create(this, br.ufms.ladesp.alfabrinque.R.raw.default_click_sound);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();

            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle(getString(br.ufms.ladesp.alfabrinque.R.string.dialog_sair_title))
                .setMessage(getString(br.ufms.ladesp.alfabrinque.R.string.dialog_sair_texto))
                .setIcon(br.ufms.ladesp.alfabrinque.R.drawable.ic_close_black_24dp_dialog)
                .setPositiveButton(getString(br.ufms.ladesp.alfabrinque.R.string.txt_dialog_sim), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                })
                .setNegativeButton(getString(br.ufms.ladesp.alfabrinque.R.string.txt_cancelar), null).show();
    }


}
