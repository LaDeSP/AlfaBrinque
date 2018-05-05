package br.ufms.ladesp.alfabrinque.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import br.ufms.ladesp.alfabrinque.adapter.CustomSpinnerAdapter;
import br.ufms.ladesp.alfabrinque.adapter.HistoricoAdapter;
import br.ufms.ladesp.alfabrinque.data.AppDAO;
import br.ufms.ladesp.alfabrinque.pojo.HistoricoUsuario;
import br.ufms.ladesp.alfabrinque.pojo.Usuario;
import br.ufms.ladesp.alfabrinque.util.UtilitarioUI;

public class TelaFeedbackUsuario extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spnUsuarios;
    private CustomSpinnerAdapter spinnerAdapter;
    private AppDAO mDao;
    private List<Usuario> mListUsuarios;
    //private ArrayList<HistoricoUsuario> mListHistoricoUsuario;
    //private RecyclerView mRecyclerListSessoes;
    private HistoricoAdapter historicoAdapter;

    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "br"));

    //private HistoricoUsuario primeiroAcesso;
    //private HistoricoUsuario ultimoAcesso;

    private TextView animaisErros;
    private TextView animaisAcertos;

    private TextView alimentosErros;
    private TextView alimentosAcertos;

    private TextView objetosErros;
    private TextView objetosAcertos;

    private TextView animaisErrosUltimoAcesso;
    private TextView animaisAcertosUltimoAcesso;

    private TextView alimentosErrosUltimoAcesso;
    private TextView alimentosAcertosUltimoAcesso;

    private TextView objetosErrosUltimoAcesso;
    private TextView objetosAcertosUltimoAcesso;

    private TextView txtPrimeiroAcessoData;
    private TextView txtUltimoAcessoData;

    private LinearLayout emptyContainer;
    private LinearLayout containerPrimeiraLinha;
    private LinearLayout containerSegundaLinha;
    private LinearLayout containerTerceiraLinha;
    private LinearLayout containerQuartaLinha;
    private LinearLayout containerQuintaLinha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(br.ufms.ladesp.alfabrinque.R.layout.activity_tela_feedback_usuario);
        UtilitarioUI.hideSystemUI(getWindow());

        initViews();

        mDao = AppDAO.newInstance(this);

        mListUsuarios = mDao.listarUsuarios();

        //historicoAdapter = new HistoricoAdapter(this);
        spinnerAdapter = new CustomSpinnerAdapter(this, mListUsuarios);

        if (!mListUsuarios.isEmpty()) {
            selectItem(mListUsuarios.get(0).getUsuarioId());
            setVisibility(false);
        } else {
            setVisibility(true);
        }

        spnUsuarios.setOnItemSelectedListener(this);

        if (spinnerAdapter != null) {
            spnUsuarios.setAdapter(spinnerAdapter);
        }
    }

    private void initViews() {

        //Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/EraserDust.ttf");

        spnUsuarios = (Spinner) findViewById(br.ufms.ladesp.alfabrinque.R.id.spinner_usuario);

        animaisErros = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_animais_erros);

        animaisAcertos = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_animais_acertos);

        alimentosErros = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_alimentos_erros);
        alimentosAcertos = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_alimentos_acertos);

        objetosErros = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_objetos_erros);
        objetosAcertos = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_objetos_acertos);

        animaisErrosUltimoAcesso = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_animais_erros_ultimo_acesso);
        animaisAcertosUltimoAcesso = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_animais_acertos_ultimo_acesso);

        alimentosErrosUltimoAcesso = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_alimentos_erros_ultimo_acesso);
        alimentosAcertosUltimoAcesso = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_alimentos_acertos_ultimo_acesso);

        objetosErrosUltimoAcesso = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_objetos_erros_ultimo_acesso);
        objetosAcertosUltimoAcesso = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_objetos_acertos_ultimo_acesso);

        txtPrimeiroAcessoData = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_primeiro_acesso);
        txtUltimoAcessoData = (TextView) findViewById(br.ufms.ladesp.alfabrinque.R.id.txt_ultimo_acesso);

        emptyContainer = (LinearLayout) findViewById(br.ufms.ladesp.alfabrinque.R.id.layout_empty);

        containerPrimeiraLinha = (LinearLayout) findViewById(br.ufms.ladesp.alfabrinque.R.id.container_primeira_linha);
        containerSegundaLinha = (LinearLayout) findViewById(br.ufms.ladesp.alfabrinque.R.id.container_segunda_linha);
        containerTerceiraLinha = (LinearLayout) findViewById(br.ufms.ladesp.alfabrinque.R.id.container_terceira_linha);
        containerQuartaLinha = (LinearLayout) findViewById(br.ufms.ladesp.alfabrinque.R.id.container_quarta_linha);
        containerQuintaLinha = (LinearLayout) findViewById(br.ufms.ladesp.alfabrinque.R.id.container_quinta_linha);
    }

    private void setVisibility(boolean isEmpty) {

        if (isEmpty) {
            containerPrimeiraLinha.setVisibility(View.GONE);
            containerSegundaLinha.setVisibility(View.GONE);
            containerTerceiraLinha.setVisibility(View.GONE);
            containerQuartaLinha.setVisibility(View.GONE);
            containerQuintaLinha.setVisibility(View.GONE);

            spnUsuarios.setVisibility(View.GONE);

            txtPrimeiroAcessoData.setVisibility(View.GONE);
            txtUltimoAcessoData.setVisibility(View.GONE);
            emptyContainer.setVisibility(View.VISIBLE);
        } else {
            containerPrimeiraLinha.setVisibility(View.VISIBLE);
            containerSegundaLinha.setVisibility(View.VISIBLE);
            containerTerceiraLinha.setVisibility(View.VISIBLE);
            containerQuartaLinha.setVisibility(View.VISIBLE);
            containerQuintaLinha.setVisibility(View.VISIBLE);

            spnUsuarios.setVisibility(View.VISIBLE);

            txtPrimeiroAcessoData.setVisibility(View.VISIBLE);
            txtUltimoAcessoData.setVisibility(View.VISIBLE);
            emptyContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        selectItem(mListUsuarios.get(position).getUsuarioId());

    }

    private void selectItem(int userId) {

        HistoricoUsuario animaisPrimeiroAcesso = mDao.getHistoricoUsuarioPrimeiroUltimoAcesso("ASC", 1, userId);
        HistoricoUsuario animaisUltimoAcesso = mDao.getHistoricoUsuarioPrimeiroUltimoAcesso("DESC", 1, userId);


        HistoricoUsuario primeiroAcesso = mDao.getAcessoData("ASC", userId);
        HistoricoUsuario ultimoAcesso = mDao.getAcessoData("DESC", userId);

        if (primeiroAcesso != null) {
            txtPrimeiroAcessoData.setText(getString(br.ufms.ladesp.alfabrinque.R.string.txt_primeiro_acesso).concat(df.format(primeiroAcesso.getDataCriacao())));
        } else {
            txtPrimeiroAcessoData.setText(getString(br.ufms.ladesp.alfabrinque.R.string.txt_nenhum_acesso));
        }

        if (ultimoAcesso != null) {
            txtUltimoAcessoData.setText(getString(br.ufms.ladesp.alfabrinque.R.string.txt_ultimo_acesso).concat(df.format(ultimoAcesso.getDataCriacao())));
        } else {
            txtUltimoAcessoData.setText(getString(br.ufms.ladesp.alfabrinque.R.string.txt_nenhum_acesso));
        }

        if (animaisPrimeiroAcesso != null) {
            animaisAcertos.setText(String.valueOf(animaisPrimeiroAcesso.getNumeroAcertos()));
            animaisErros.setText(String.valueOf(animaisPrimeiroAcesso.getNumeroErros()));
        } else {
            animaisAcertos.setText("--");
            animaisErros.setText("--");
        }

        if (animaisUltimoAcesso != null) {
            animaisAcertosUltimoAcesso.setText(String.valueOf(animaisUltimoAcesso.getNumeroAcertos()));
            animaisErrosUltimoAcesso.setText(String.valueOf(animaisUltimoAcesso.getNumeroErros()));
        } else {
            animaisAcertosUltimoAcesso.setText("--");
            animaisErrosUltimoAcesso.setText("--");
        }

        HistoricoUsuario alimentosPrimeiroAcesso = mDao.getHistoricoUsuarioPrimeiroUltimoAcesso("ASC", 2, userId);
        HistoricoUsuario alimentosUltimoAcesso = mDao.getHistoricoUsuarioPrimeiroUltimoAcesso("DESC", 2, userId);


        if (alimentosPrimeiroAcesso != null) {
            alimentosAcertos.setText(String.valueOf(alimentosPrimeiroAcesso.getNumeroAcertos()));
            alimentosErros.setText(String.valueOf(alimentosPrimeiroAcesso.getNumeroErros()));
        } else {
            alimentosAcertos.setText("--");
            alimentosErros.setText("--");
        }

        if (alimentosUltimoAcesso != null) {
            alimentosAcertosUltimoAcesso.setText(String.valueOf(alimentosUltimoAcesso.getNumeroAcertos()));
            alimentosErrosUltimoAcesso.setText(String.valueOf(alimentosUltimoAcesso.getNumeroErros()));
        } else {
            alimentosAcertosUltimoAcesso.setText("--");
            alimentosErrosUltimoAcesso.setText("--");
        }

        HistoricoUsuario objetosPrimeiroAcesso = mDao.getHistoricoUsuarioPrimeiroUltimoAcesso("ASC", 3, userId);
        HistoricoUsuario objetosUltimoAcesso = mDao.getHistoricoUsuarioPrimeiroUltimoAcesso("DESC", 3, userId);

        if (objetosPrimeiroAcesso != null) {
            objetosAcertos.setText(String.valueOf(objetosPrimeiroAcesso.getNumeroAcertos()));
            objetosErros.setText(String.valueOf(objetosPrimeiroAcesso.getNumeroErros()));
        } else {
            objetosAcertos.setText("--");
            objetosErros.setText("--");
        }

        if (objetosUltimoAcesso != null) {
            objetosAcertosUltimoAcesso.setText(String.valueOf(objetosUltimoAcesso.getNumeroAcertos()));
            objetosErrosUltimoAcesso.setText(String.valueOf(objetosUltimoAcesso.getNumeroErros()));
        } else {
            objetosAcertosUltimoAcesso.setText("--");
            objetosErrosUltimoAcesso.setText("--");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
