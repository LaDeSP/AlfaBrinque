package com.book.app;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.book.app.data.AppDAO;
import com.book.app.pojo.EstadoUsuario;
import com.book.app.pojo.HistoricoUsuario;
import com.book.app.pojo.Questao;
import com.book.app.ui.QuestoesRespondidasSucesso;
import com.book.app.util.Shuffle;
import com.book.app.util.UsuarioEscolhido;
import com.book.app.util.UtilitarioUI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ResponderQuiz extends AppCompatActivity implements View.OnClickListener {

    public static final String CATEGORIA_EXTRA = "categoriaExtra";
    private static int tileWidth = 0;
    private static TextView rootTextView;
    private String shuffledWord;
    private int length;
    private LinearLayout ll;
    private List<TextView> textViewsList = new ArrayList<>();
    private LinearLayout dropLayout;
    private ImageButton btnResponder;
    private Questao questao;
    private ImageView quizImage;
    private AppDAO dao;
    private int[] indexArray = new int[5];
    private int arrayLength;
    private int index = 1;
    private int categoria;
    private UsuarioEscolhido usuarioEscolhidoPreferences;
    private EstadoUsuario estadoUsuario;
    private LinearLayout rootLayoutImg;
    private LinearLayout linearLayout;
    private int acertosCount = 0;
    private int errosCount = 0;
    private int questoesCount = 0;
    private long time = System.currentTimeMillis();
    // private TextView txtTimer;
    private int count = 0;
    private boolean isTablet;
    private ImageButton btnApagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responder_quiz);

        UtilitarioUI.hideSystemUI(getWindow());

        dao = AppDAO.newInstance(this);

        ll = (LinearLayout) findViewById(R.id.quiz_layout);
        dropLayout = (LinearLayout) findViewById(R.id.drop_layout);
        btnResponder = (ImageButton) findViewById(R.id.btnResponder);
        btnApagar = (ImageButton) findViewById(R.id.btn_apagar);

        quizImage = (ImageView) findViewById(R.id.quiz_image);
        rootLayoutImg = (LinearLayout) findViewById(R.id.root_layout);
        //txtTimer = (TextView) findViewById(R.id.quiz_timer);

        usuarioEscolhidoPreferences = new UsuarioEscolhido(this);

        categoria = getIntent().getIntExtra(CATEGORIA_EXTRA, -1);

        indexArray[0] = 1;
        indexArray[1] = 2;
        indexArray[2] = 3;
        indexArray[3] = 4;
        indexArray[4] = 5;

        isTablet = getResources().getBoolean(R.bool.tablet);

        Log.i("DEVICE", "IS TABLET => " + isTablet);

        if (categoria != -1) {
            // questao = dao.getQuestaoById(categoria, index);
            if (categoria == 1) {
                rootLayoutImg.setBackgroundResource(R.drawable.teste_grama3);
            } else if (categoria == 2) {
                rootLayoutImg.setBackgroundResource(R.drawable.picnic_novo);
            } else if (categoria == 3) {
                rootLayoutImg.setBackgroundResource(R.drawable.fundo_objetos);
            }

            carregarQuestao(categoria, 1);

            //initTimer(5000);
        }

    }

    private void initTimer(long time) {

        Timer T = new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //txtTimer.setText(count);
                        count++;
                    }
                });
            }
        }, 1000, time);
    }

    private void carregarQuestao(final int categoria, int ordem) {
        if (btnResponder != null) {
            if (isTablet) {
                btnResponder.setImageResource(R.drawable.ic_play_circle_filled_black_24dp_quiz_tablet_disabled);
            } else {
                btnResponder.setImageResource(R.drawable.ic_play_circle_filled_black_24dp_quiz_disabled);
            }
            btnResponder.setEnabled(false);
        }

        if (btnApagar != null) {
            int count = 0;

            for (int i = 0; i < dropLayout.getChildCount(); i++) {
                LinearLayout lContainer = (LinearLayout) dropLayout.getChildAt(i);
                if (lContainer.getChildAt(0) != null) {
                    count++;
                }
            }

            if (count > 0) {
                btnApagar.setEnabled(true);
            } else {
                btnApagar.setEnabled(false);
            }
        }

        questao = dao.getQuestaoById(categoria, ordem);


        shuffledWord = Shuffle.shuffleWord(questao.getRespostaCerta().toUpperCase());

        quizImage.setImageResource(questao.getImagemUrl());

        length = shuffledWord.length();

        dropLayout.post(new Runnable() {
            @Override
            public void run() {
                tileWidth = dropLayout.getWidth() % length;
            }
        });

        Log.i("TEST", "WIDTH=>" + tileWidth);

        //final Random random = new Random();
        for (int i = 0; i < length; i++) {
            //final TextView rootTextView = new TextView(this);
            rootTextView = new TextView(this);
            rootTextView.setId(i);

            //rootTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.bgColor));
            rootTextView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);

            //rootTextView.setTextSize(30);
            rootTextView.setBackgroundResource(R.drawable.word_shape);
            rootTextView.setTextColor(ContextCompat.getColor(this, R.color.textViewTextColor));

            rootTextView.setGravity(View.TEXT_ALIGNMENT_CENTER);

            //rootTextView.setPadding(35, 20, 35, 20);

            if (isTablet) {
                rootTextView.setTextSize(25);
                rootTextView.setPadding(20, 10, 20, 10);
            } else {
                rootTextView.setTextSize(30);
                rootTextView.setPadding(35, 20, 35, 20);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT));

            params.setMargins(20, 10, 20, 10);

            rootTextView.setText(String.valueOf(shuffledWord.charAt(i)));

            rootTextView.setLayoutParams(params);


           /* rootLayout.post(new Runnable() {

                @Override
                public void run() {

                    int randomPosX = 10 + random.nextInt(((rootLayout.getWidth() - 100) - 10) + 1);
                    int randomPosY = 10 + random.nextInt(((rootLayout.getHeight() - 100) - 10) + 1);
                    Log.i("RANDOM", "Layout random posX: " + randomPosX + " RandomPOSY: " + randomPosY);

                    rootTextView.setX(randomPosX);
                    rootTextView.setY(randomPosY);

                }
            });*/

            //rootLayout.addView(rootTextView);
            //rootLayout.addView(rootTextView);
            ll.addView(rootTextView);

            rootTextView.setOnClickListener(this);
            rootTextView.setOnTouchListener(new MyTouchListener());

            //rootLayout.setOnDragListener(new MyDragListener());
            textViewsList.add(rootTextView);
        }


        //dropLayout.setOnDragListener(new MyDragListener());
        //ll.setOnDragListener(new MyDragListener());


        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                if (categoria == 1) {
                    if (questao.getSomUrl() != null) {
                        Uri som = Uri.parse(questao.getSomUrl());
                        MediaPlayer mp = MediaPlayer.create(ResponderQuiz.this, som);
                        mp.start();
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();

                            }
                        });
                    }
                }

            }
        }, 1000);

        initDropTiles();
    }

    private void initDropTiles() {

        for (int i = 0; i < length; i++) {
            linearLayout = new LinearLayout(this);
            linearLayout.setId(i + 10);

            linearLayout.setBackgroundResource(R.drawable.word_tiles_shape);
            //linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            // linearLayout.setPadding(40, 20, 40, 20);


            rootTextView.measure(0, 0);
            int width = rootTextView.getMeasuredWidth() + (int) (rootTextView.getMeasuredWidth() * 0.2);

            Log.i("SIZE", "WIDTH => " + width);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayoutCompat.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));

            // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayoutCompat.LayoutParams(180, ViewGroup.LayoutParams.MATCH_PARENT));
            //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayoutCompat.LayoutParams(160, ViewGroup.LayoutParams.MATCH_PARENT));
            //params.setMargins(10, 20, 10, 20);
            params.setMargins(20, 10, 20, 20);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setLayoutParams(params);

            linearLayout.setOnDragListener(new MyDragListener());
            dropLayout.addView(linearLayout);
        }
    }

    @Override
    public void onClick(View v) {
        // Toast.makeText(this, "TEXT => " + shuffledWord.charAt(v.getId()), Toast.LENGTH_LONG).show();
    }

    private void mostrarRespostaCerta() {
        acertosCount = acertosCount + 1;
        MediaPlayer mp = MediaPlayer.create(ResponderQuiz.this, R.raw.right_answer);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();

            }
        });

        Toast toast = new Toast(this);
        toast.setGravity(Gravity.BOTTOM | Gravity.START, 0, 0);
        ImageView result = new ImageView(this);

        result.setImageResource(R.drawable.ic_check_black_24dp);

        toast.setView(result);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    private void mostrarRespostaErrada() {
        errosCount = errosCount + 1;
        MediaPlayer mp = MediaPlayer.create(ResponderQuiz.this, R.raw.buzzer2);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();

            }
        });

        Toast toast = new Toast(this);
        toast.setGravity(Gravity.BOTTOM | Gravity.START, 0, 0);
        ImageView result = new ImageView(this);

        result.setImageResource(R.drawable.ic_close_black_24dp);

        toast.setView(result);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    private boolean updateOrCreateEstadoUsuario(Questao questao, int status) {

        estadoUsuario = dao.getEstadoUsuario(usuarioEscolhidoPreferences.getId(), questao.getQuestaoID());

        if (estadoUsuario != null) {
            boolean updated = dao.updateResposta(new EstadoUsuario(estadoUsuario.getEstadoUsuarioID(), usuarioEscolhidoPreferences.getId(), questao.getQuestaoID(), 1, status));

            if (updated) {
                Log.i("DB_TEST", "RESPOSTA -- ATUALIZADO COM SUCESSO");
            }
        } else {
            int inserted = dao.inserirEstadoUsuario(new EstadoUsuario(usuarioEscolhidoPreferences.getId(), questao.getQuestaoID(), 1, status));

            if (inserted != -1) {
                Log.i("DB_TEST", "RESPOSTA -- ATUALIZADO COM SUCESSO");
            }
        }

        return true;

    }

    public void responderQuestao(View view) {
        StringBuilder sb = new StringBuilder();

        int childCount = dropLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            LinearLayout lContainer = (LinearLayout) dropLayout.getChildAt(i);
            TextView tv = (TextView) lContainer.getChildAt(0);
            if (tv != null) {
                sb.append(tv.getText());
            }
        }

        arrayLength = indexArray.length;

        if (questao.getRespostaCerta().toUpperCase().equals(sb.toString())) {
            dropLayout.removeAllViews();
            mostrarRespostaCerta();

            proximaQuestao(1);

            /*if (index >= 0 && index <= 4) {
                if (categoria != -1) {
                    Questao questao = dao.getQuestaoById(categoria, indexArray[index]);
                    //questao = dao.getQuestaoById(categoria, indexArray[index]);

                    if (questaoCerta) {
                        //Log.i("DB_TEST", "RESPOSTA CERTA -- INSERIDO COM SUCESSO");

                        // questao = dao.getQuestaoById(categoria, indexArray[index]);

                        //estadoUsuario = dao.getEstadoUsuario(usuarioEscolhidoPreferences.getId(), questao.getQuestaoID());

                        //if (estadoUsuario.getChaveQuestao() == questao.getQuestaoID()) {
                        carregarQuestao(categoria, indexArray[index]);
                        //carregarQuestao(questao);

                        // }

                        //   carregarQuestao(categoria, indexArray[index]);
                    }
                    //index = index + 1;
                }
                //index = index + 1;
            } else {

                //Toast.makeText(this, "TODAS QUESTOES RESPONDIDAS NESTA CATEGORIA!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, QuestoesRespondidasSucesso.class);
                intent.putExtra(CATEGORIA_EXTRA, categoria);
                startActivity(intent);
                finish();*/
        }
        //index = index + 1;


        else {
            dropLayout.removeAllViews();
            mostrarRespostaErrada();

           /* estadoUsuario = dao.getEstadoUsuario(usuarioEscolhidoPreferences.getId(), questao.getQuestaoID());

            if (estadoUsuario != null) {
                boolean updated = dao.updateResposta(new EstadoUsuario(usuarioEscolhidoPreferences.getId(), questao.getQuestaoID(), 1, 0));

                if (updated) {
                    Log.i("DB_TEST", "RESPOSTA ERRADA -- ATUALIZADO COM SUCESSO");
                }
            } else {
                dao.inserirEstadoUsuario(new EstadoUsuario(usuarioEscolhidoPreferences.getId(), questao.getQuestaoID(), 1, 0));

                Log.i("DB_TEST", "RESPOSTA ERRADA -- INSERIDO COM SUCESSO");
            }*/

            //updateOrCreateEstadoUsuario(questao, 0);
            proximaQuestao(0);
        }

    }

    private void proximaQuestao(int status) {
        questoesCount = questoesCount + 1;
        if (index >= 0 && index <= 4) {
            if (categoria != -1) {
                Questao questao = dao.getQuestaoById(categoria, indexArray[index]);
                //questao = dao.getQuestaoById(categoria, indexArray[index]);

                boolean questaoCerta = updateOrCreateEstadoUsuario(questao, status);

                if (questaoCerta) {
                    //Log.i("DB_TEST", "RESPOSTA CERTA -- INSERIDO COM SUCESSO");

                    // questao = dao.getQuestaoById(categoria, indexArray[index]);

                    //estadoUsuario = dao.getEstadoUsuario(usuarioEscolhidoPreferences.getId(), questao.getQuestaoID());

                    //if (estadoUsuario.getChaveQuestao() == questao.getQuestaoID()) {
                    carregarQuestao(categoria, indexArray[index]);
                    //carregarQuestao(questao);

                    // }

                    //   carregarQuestao(categoria, indexArray[index]);
                }
                //index = index + 1;
            }
            index = index + 1;
        } else {

            //DateFormat df = new SimpleDateFormat("dd MMM", new Locale("pt", "br"));
           /* DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", new Locale("pt", "br"));
            Date date = new Date(time);

            Log.i("FEEDBACK", "DATA: " + df.format(date));
            Log.i("FEEDBACK", "N QUESTOES: " + questoesCount);
            Log.i("FEEDBACK", "ACERTOS: " + acertosCount);
            Log.i("FEEDBACK", "ERROS: " + errosCount);

            int inserido = dao.inserirHistoricoUsuario(new HistoricoUsuario((time != -1 ? new Date(time) : null), questoesCount, acertosCount, errosCount, categoria, usuarioEscolhidoPreferences.getId()));

            if (inserido != -1) {
                Log.i("FEEDBACK", "INSERIDO!!!");
            }*/

            gravarSessao();

            //Toast.makeText(this, "TODAS QUESTOES RESPONDIDAS NESTA CATEGORIA!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, QuestoesRespondidasSucesso.class);
            intent.putExtra(CATEGORIA_EXTRA, categoria);
            startActivity(intent);
            finish();

        }
    }

    private void gravarSessao() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", new Locale("pt", "br"));
        Date date = new Date(time);

        Log.i("FEEDBACK", "DATA: " + df.format(date));
        Log.i("FEEDBACK", "N QUESTOES: " + questoesCount);
        Log.i("FEEDBACK", "ACERTOS: " + acertosCount);
        Log.i("FEEDBACK", "ERROS: " + errosCount);

        int inserido = dao.inserirHistoricoUsuario(new HistoricoUsuario((time != -1 ? new Date(time) : null), questoesCount, acertosCount, errosCount, categoria, usuarioEscolhidoPreferences.getId()));

        if (inserido != -1) {
            Log.i("FEEDBACK", "INSERIDO!!!");
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.txt_dialog_encerrar_quiz_title))
                .setMessage(getString(R.string.txt_dialog_encerrar_quiz_text))
                .setIcon(R.drawable.ic_close_black_24dp_dialog)
                .setPositiveButton(getString(R.string.txt_dialog_sim), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        gravarSessao();
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.txt_cancelar), null).show();
    }

    public void apagarLetras(View view) {
        for (int i = 0; i < dropLayout.getChildCount(); i++) {
            LinearLayout lContainer = (LinearLayout) dropLayout.getChildAt(i);
            View v = lContainer.getChildAt(0);

            if (v != null) {
                //lContainer.removeView(v);
                lContainer.removeAllViews();
                LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                TextView tv = (TextView) v;
                viewParams.setMargins(20, 10, 20, 10);
                tv.setLayoutParams(viewParams);

                tv.setBackgroundResource(R.drawable.word_shape);
                tv.setTextColor(ContextCompat.getColor(this, R.color.textViewTextColor));

                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                tv.setGravity(Gravity.CENTER);
                ll.addView(tv);
            }
        }
    }


    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);

                MediaPlayer mp = MediaPlayer.create(ResponderQuiz.this, R.raw.start_drag_sound);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();

                    }
                });
                return true;
            } else {
                return false;
            }
        }
    }

    private class MyDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();

            View view = (View) event.getLocalState();

            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundResource(R.drawable.word_tiles_shape_enter);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundResource(R.drawable.word_tiles_shape);
                    break;
                case DragEvent.ACTION_DROP:

                    //View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);

//                    if (owner.getId() == R.id.quiz_layout) {
//                        owner.removeView(view);
//                        dropLayout.addView(view);
//                        //view.setVisibility(View.VISIBLE);
//                    } else if (owner.getId() == R.id.drop_layout) {
//                        owner.removeView(view);
//                        ll.addView(view);
//                        //view.setVisibility(View.VISIBLE);
//                    }

                    //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                    //params.setMargins(10, 20, 10, 20);
                    //params.setMargins(20, 10, 20, 10);
                    //view.setLayoutParams(params);
                    // view.setPadding(10, 10, 10, 10);

                    LinearLayout layout = (LinearLayout) findViewById(v.getId());

                    if (layout != null) {
                        if (layout.getChildCount() == 0) {
                            LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT));
                            TextView tv = (TextView) view;
                            tv.setLayoutParams(viewParams);

                            tv.setBackgroundColor(ContextCompat.getColor(ResponderQuiz.this, android.R.color.transparent));
                            //view.setPadding(35, 20, 35, 20);
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            tv.setGravity(Gravity.CENTER);

                            layout.addView(tv);

                            MediaPlayer mp = MediaPlayer.create(ResponderQuiz.this, R.raw.end_drag_sound);
                            mp.start();
                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                public void onCompletion(MediaPlayer mp) {
                                    mp.release();

                                }
                            });

                        } else {
                            owner.removeView(view);
                            LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT));
                            viewParams.setMargins(20, 10, 20, 10);
                            view.setLayoutParams(viewParams);
                            view.setBackgroundResource(R.drawable.word_shape);
                            ll.addView(view);
                        }
                    }

                    //linearLayout.addView(view);

                    view.setVisibility(View.VISIBLE);


                    int containerChildCount = ll.getChildCount();

                    Log.i("COUNT", "CHILD_COUNT => " + containerChildCount);

                    if (containerChildCount != 0) {
                        if (isTablet) {
                            btnResponder.setImageResource(R.drawable.ic_play_circle_filled_black_24dp_quiz_tablet_disabled);
                        } else {
                            btnResponder.setImageResource(R.drawable.ic_play_circle_filled_black_24dp_quiz_disabled);
                        }
                        btnResponder.setEnabled(false);
                    } else {
                        if (isTablet) {
                            btnResponder.setImageResource(R.drawable.ic_play_circle_filled_black_24dp_quiz_tablet);
                        } else {
                            btnResponder.setImageResource(R.drawable.ic_play_circle_filled_black_24dp_quiz);
                        }

                        btnResponder.setEnabled(true);
                    }

                    int count = 0;

                    for (int i = 0; i < dropLayout.getChildCount(); i++) {
                        LinearLayout lContainer = (LinearLayout) dropLayout.getChildAt(i);
                        if (lContainer.getChildAt(0) != null) {
                            count++;
                        }
                    }

                    if (count > 0) {
                        btnApagar.setEnabled(true);
                    } else {
                        btnApagar.setEnabled(false);
                    }

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundResource(R.drawable.word_tiles_shape);
                default:
                    break;
            }
            return true;
        }
    }

}
