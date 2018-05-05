package br.ufms.ladesp.alfabrinque.ui;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanks.htextview.HTextView;

import br.ufms.ladesp.alfabrinque.ResponderQuiz;
import br.ufms.ladesp.alfabrinque.util.UtilitarioUI;
import tyrantgit.explosionfield.ExplosionField;

public class QuestoesRespondidasSucesso extends AppCompatActivity {

    // private TextView txtCategoria;
    private ImageView sucessoIcone;
    private ImageView medalIcone;
    private ExplosionField explosionField;
    private LinearLayout historiaContainer;
    private HTextView hTextView;
    private TextView vencedorText;
    private TextView historiaTxt;
    private Typeface tf;
    private MediaPlayer mp;
    private MediaPlayer mp1;
    private int categoria;
    private ImageButton btnStop;
    private ImageButton btnPlay;
    private ImageButton closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(br.ufms.ladesp.alfabrinque.R.layout.activity_questoes_respondidas_sucesso);
        UtilitarioUI.hideSystemUI(getWindow());

        explosionField = ExplosionField.attach2Window(this);

        categoria = getIntent().getIntExtra(ResponderQuiz.CATEGORIA_EXTRA, -1);

        //txtCategoria = (TextView) findViewById(R.id.txt_categoria);
        sucessoIcone = (ImageView) findViewById(br.ufms.ladesp.alfabrinque.R.id.sucesso_icone);
        medalIcone = (ImageView) findViewById(br.ufms.ladesp.alfabrinque.R.id.medal_icone);
        historiaContainer = (LinearLayout) findViewById(br.ufms.ladesp.alfabrinque.R.id.play_historia_container);
        btnStop = (ImageButton) findViewById(br.ufms.ladesp.alfabrinque.R.id.btn_stop);
        btnPlay = (ImageButton) findViewById(br.ufms.ladesp.alfabrinque.R.id.btn_play_historia);
        closeButton = (ImageButton) findViewById(br.ufms.ladesp.alfabrinque.R.id.close_button);

        hTextView = (HTextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_parabens);
        vencedorText = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_vencedor);
        historiaTxt = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_historia);

        tf = Typeface.createFromAsset(getAssets(),
                "fonts/A_Bebedera.ttf");

        hTextView.setTypeface(tf);
        hTextView.animateText(getString(br.ufms.ladesp.alfabrinque.R.string.txt_parabens).toUpperCase());

        historiaTxt.setTypeface(tf);

        if (historiaContainer != null) {
            historiaContainer.setVisibility(View.GONE);
        }

        if (sucessoIcone != null) {
            sucessoIcone.setImageResource(br.ufms.ladesp.alfabrinque.R.drawable.estrela);
        }

        mp = MediaPlayer.create(this, br.ufms.ladesp.alfabrinque.R.raw.category_success);

        if (!mp.isPlaying()) {
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                    setAnimacaoViews();
                }
            });
        }

    }

    private void setAnimacaoViews() {
        final Handler handler = new Handler();
        final Animation fadeInAnimation = AnimationUtils.loadAnimation(this, br.ufms.ladesp.alfabrinque.R.anim.fade_in);

        explosionField.explode(sucessoIcone);
        explosionField.explode(hTextView);
        sucessoIcone.setVisibility(View.GONE);
        hTextView.setVisibility(View.GONE);

        medalIcone.setVisibility(View.VISIBLE);

        handler.postDelayed(new Runnable() {
            public void run() {
                vencedorText.setVisibility(View.VISIBLE);
                vencedorText.setTypeface(tf);
                vencedorText.setText(br.ufms.ladesp.alfabrinque.R.string.txt_venceu);

                vencedorText.setAnimation(fadeInAnimation);

                if (medalIcone != null) {
                    medalIcone.setImageResource(br.ufms.ladesp.alfabrinque.R.drawable.medalha);
                    medalIcone.startAnimation(fadeInAnimation);
                }

                historiaContainer.setVisibility(View.VISIBLE);
                historiaContainer.setAnimation(fadeInAnimation);
            }
        }, 1000);
    }

    private void tocarSomHistoria() {
        categoria = 2;
        if (categoria != -1) {

            switch (categoria) {
                case 1:
                    mp1 = MediaPlayer.create(QuestoesRespondidasSucesso.this, br.ufms.ladesp.alfabrinque.R.raw.plenarinho_jeca_o_tatu);
                    mp1.start();
                    mp1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp1) {
                            ajustarControles();
                            mp1.release();
                        }
                    });
                    break;
                case 2:
                    mp1 = MediaPlayer.create(QuestoesRespondidasSucesso.this, br.ufms.ladesp.alfabrinque.R.raw.category_success);
                    mp1.start();
                    mp1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp1) {
                            ajustarControles();
                            mp1.release();
                        }
                    });
                    break;
                case 3:
                    mp1 = MediaPlayer.create(QuestoesRespondidasSucesso.this, br.ufms.ladesp.alfabrinque.R.raw.user_created_success);
                    mp1.start();
                    mp1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp1) {
                            ajustarControles();
                            mp1.release();
                        }
                    });
                    break;
            }

            ajustarControles();
        }
    }

    private void ajustarControles() {
        if (mp1 != null) {
            if (mp1.isPlaying()) {
                closeButton.setVisibility(View.INVISIBLE);
                btnStop.setVisibility(View.VISIBLE);
                btnPlay.setVisibility(View.INVISIBLE);
                historiaTxt.setText(br.ufms.ladesp.alfabrinque.R.string.txt_tocando_historia);
            } else {
                closeButton.setVisibility(View.VISIBLE);
                btnStop.setVisibility(View.INVISIBLE);
                btnPlay.setVisibility(View.VISIBLE);
                historiaTxt.setText(br.ufms.ladesp.alfabrinque.R.string.txt_ouvir_historia);
            }
        }
    }


    public void tocarHistoria(View view) {
        if (mp1 != null) {
            try {
                if (!mp1.isPlaying()) {
                    mp1.start();
                    ajustarControles();
                    mp1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp1) {
                            ajustarControles();
                            mp1.release();
                        }
                    });
                }

            } catch (IllegalStateException e) {
                e.printStackTrace();
                tocarSomHistoria();
            }

        } else {
            tocarSomHistoria();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        pararSom();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pararSom();
    }

    @Override
    public void onBackPressed() {
        pararSom();
    }

    private void pararSom() {
        if (mp1 != null) {
            try {

                mp1.stop();
                ajustarControles();
                mp1.release();
                mp1 = null;


            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        pararSom();
    }

    public void closeCategoriaSucessoScreen(View view) {
        pararSom();
        finish();
    }

    public void pauseSound(View view) {
        if (mp1 != null) {
            if (mp1.isPlaying()) {
                mp1.pause();
                //mp1.release();
                ajustarControles();
            }

        }
    }
}
