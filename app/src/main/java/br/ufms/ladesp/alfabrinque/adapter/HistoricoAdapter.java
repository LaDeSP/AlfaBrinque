package br.ufms.ladesp.alfabrinque.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import br.ufms.ladesp.alfabrinque.R;
import br.ufms.ladesp.alfabrinque.pojo.HistoricoUsuario;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.HistoricoViewHolder> implements View.OnClickListener {

    HistoricoUsuario historicoUsuario;
    LayoutInflater inflater;
    View itemView;
    private ArrayList<HistoricoUsuario> mUsuariosList;
    private int lastPosition = -1;
    private OnEventClickListener mListener;
    private DateFormat df = new SimpleDateFormat("dd MMM", new Locale("pt", "br"));
    private Context context;

    public HistoricoAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setListener(OnEventClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return mUsuariosList.size();
    }

    @Override
    public HistoricoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.historico_adapter_row, viewGroup, false);
        HistoricoViewHolder vh = new HistoricoViewHolder(itemView);
        itemView.setTag(vh);

        itemView.setOnClickListener(this);

        return vh;
    }

    public void setHistoricoUsuarioList(ArrayList<HistoricoUsuario> mUsuariosList) {
        this.mUsuariosList = mUsuariosList;
        notifyItemRangeChanged(0, mUsuariosList.size());
    }


    @Override
    public void onBindViewHolder(HistoricoViewHolder historicoViewHolder, int i) {
        historicoUsuario = mUsuariosList.get(i);

        String categoria = "";
        String acessoDescricao;

        switch (historicoUsuario.getCategoria()) {
            case 1:
                categoria = "Animais";
                break;
            case 2:
                categoria = "Alimentos";
                break;
            case 3:
                categoria = "Objetos";
                break;
        }

        if (historicoUsuario.getHistoricoId() == 1) {
            acessoDescricao = "Primeiro Acesso";
        } else {
            acessoDescricao = "Último Acesso";
        }


        historicoViewHolder.acessoDescricao.setText(acessoDescricao);
        historicoViewHolder.dataAcesso.setText(df.format(historicoUsuario.getDataCriacao()));
        historicoViewHolder.categoria.setText(categoria);
        historicoViewHolder.questoesCertasCount.setText(String.valueOf(historicoUsuario.getNumeroAcertos()));
        historicoViewHolder.questoesErradasCount.setText(String.valueOf(historicoUsuario.getNumeroErros()));


    }

    @Override
    public void onClick(final View view) {
        if (mListener != null) {
            HistoricoViewHolder vh = (HistoricoViewHolder) view.getTag();
            int position = vh.getAdapterPosition();
            mListener.onEventClick(view, position, mUsuariosList.get(position));
        }

    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    public interface OnEventClickListener {
        void onEventClick(View v, int position, HistoricoUsuario historicoUsuario);
    }

    public static class HistoricoViewHolder extends RecyclerView.ViewHolder {
        protected TextView acessoDescricao;
        protected TextView dataAcesso;
        // protected TextView dataUltimoAcesso;
        protected TextView questoesCertasCount;
        protected TextView questoesErradasCount;
        //  protected TextView questoesCertasUltimoAcessoAcessoCount;
        //protected TextView questoesErradasUltimoAcessoCount;
        protected TextView categoria;


        public HistoricoViewHolder(View v) {
            super(v);
            acessoDescricao = (TextView) v.findViewById(R.id.acesso_description);
            dataAcesso = (TextView) v.findViewById(R.id.data_acesso_feedback);
            //dataUltimoAcesso = (TextView) v.findViewById(R.id.data_ultimo_acesso_feedback);
            questoesCertasCount = (TextView) v.findViewById(R.id.count_certas_acesso);
            questoesErradasCount = (TextView) v.findViewById(R.id.count_erradas_acesso);
            // questoesCertasUltimoAcessoAcessoCount = (TextView) v.findViewById(R.id.count_certas_ultimo_acesso);
            //  questoesErradasUltimoAcessoCount = (TextView) v.findViewById(R.id.count_erradas_ultimo_acesso);
            categoria = (TextView) v.findViewById(R.id.feedback_categoria);

            // ViewCompat.setTransitionName(eventoTitle, SHARED_TRANSITION_TITLE_KEY);

        }
    }

}
