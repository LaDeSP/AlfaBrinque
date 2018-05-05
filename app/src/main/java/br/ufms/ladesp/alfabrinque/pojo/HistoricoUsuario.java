package br.ufms.ladesp.alfabrinque.pojo;


import java.util.Date;

public class HistoricoUsuario {

    private int historicoId;
    private Date dataCriacao;
    private int numeroQuestoes;
    private int numeroAcertos;
    private int numeroErros;
    private int categoria;
    private int usuario;

    public HistoricoUsuario(int historicoId, Date dataCriacao, int numeroQuestoes, int numeroAcertos, int numeroErros, int categoria, int usuario) {
        this.historicoId = historicoId;
        this.dataCriacao = dataCriacao;
        this.numeroQuestoes = numeroQuestoes;
        this.numeroAcertos = numeroAcertos;
        this.numeroErros = numeroErros;
        this.categoria = categoria;
        this.usuario = usuario;
    }

    public HistoricoUsuario(Date dataCriacao, int numeroQuestoes, int numeroAcertos, int numeroErros, int categoria, int usuario) {
        this.dataCriacao = dataCriacao;
        this.numeroQuestoes = numeroQuestoes;
        this.numeroAcertos = numeroAcertos;
        this.numeroErros = numeroErros;
        this.categoria = categoria;
        this.usuario = usuario;
    }


    public HistoricoUsuario() {
    }

    public int getHistoricoId() {
        return historicoId;
    }

    public void setHistoricoId(int historicoId) {
        this.historicoId = historicoId;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getNumeroQuestoes() {
        return numeroQuestoes;
    }

    public void setNumeroQuestoes(int numeroQuestoes) {
        this.numeroQuestoes = numeroQuestoes;
    }

    public int getNumeroAcertos() {
        return numeroAcertos;
    }

    public void setNumeroAcertos(int numeroAcertos) {
        this.numeroAcertos = numeroAcertos;
    }

    public int getNumeroErros() {
        return numeroErros;
    }

    public void setNumeroErros(int numeroErros) {
        this.numeroErros = numeroErros;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }
}
