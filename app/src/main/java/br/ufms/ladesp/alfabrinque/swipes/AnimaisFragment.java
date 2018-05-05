package br.ufms.ladesp.alfabrinque.swipes;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.ufms.ladesp.alfabrinque.R;
import br.ufms.ladesp.alfabrinque.ResponderQuiz;


public class AnimaisFragment extends Fragment implements View.OnClickListener {

    private TextView txtAnimais;

    public AnimaisFragment() {
    }

    public static AnimaisFragment newInstance() {
        return new AnimaisFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_categoria_animal, container, false);

        ImageView imgAnimais = (ImageView) view.findViewById(R.id.img_animais);
        txtAnimais = (TextView) view.findViewById(R.id.txt_animais);

        imgAnimais.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ResponderQuiz.class);
        intent.putExtra(ResponderQuiz.CATEGORIA_EXTRA, 1);
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
