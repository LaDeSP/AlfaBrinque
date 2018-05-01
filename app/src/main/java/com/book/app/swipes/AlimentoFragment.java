package com.book.app.swipes;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.book.app.R;
import com.book.app.ResponderQuiz;


public class AlimentoFragment extends Fragment implements View.OnClickListener {

    private TextView txtAlimentos;

    public AlimentoFragment() {
    }

    public static AlimentoFragment newInstance() {
        return new AlimentoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_categoria_alimento, container, false);

        ImageView imgAlimentos = (ImageView) view.findViewById(R.id.img_alimentos);
        txtAlimentos = (TextView) view.findViewById(R.id.txt_alimentos);

        imgAlimentos.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ResponderQuiz.class);
        intent.putExtra(ResponderQuiz.CATEGORIA_EXTRA, 2);
        startActivity(intent);

        MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.default_click_sound);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();

            }
        });
    }
}
