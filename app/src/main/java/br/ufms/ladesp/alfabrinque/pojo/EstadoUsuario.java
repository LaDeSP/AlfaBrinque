package br.ufms.ladesp.alfabrinque.pojo;

public class EstadoUsuario {
    private int estadoUsuarioID;
    private int chaveUsuario;
    private int chaveQuestao;
    private int questaoRespondida;
    private int statusQuestao;

    public EstadoUsuario() {
    }

    public EstadoUsuario(int estadoUsuarioID, int chaveUsuario, int chaveQuestao, int questaoRespondida, int statusQuestao) {
        this.estadoUsuarioID = estadoUsuarioID;
        this.chaveUsuario = chaveUsuario;
        this.chaveQuestao = chaveQuestao;
        this.questaoRespondida = questaoRespondida;
        this.statusQuestao = statusQuestao;
    }

    public EstadoUsuario(int chaveUsuario, int chaveQuestao, int questaoRespondida, int statusQuestao) {
        this.chaveUsuario = chaveUsuario;
        this.chaveQuestao = chaveQuestao;
        this.questaoRespondida = questaoRespondida;
        this.statusQuestao = statusQuestao;
    }


    public int getStatusQuestao() {
        return statusQuestao;
    }

    public void setStatusQuestao(int statusQuestao) {
        this.statusQuestao = statusQuestao;
    }

    public int getEstadoUsuarioID() {
        return estadoUsuarioID;
    }

    public void setEstadoUsuarioID(int estadoUsuarioID) {
        this.estadoUsuarioID = estadoUsuarioID;
    }

    public int getChaveUsuario() {
        return chaveUsuario;
    }

    public void setChaveUsuario(int chaveUsuario) {
        this.chaveUsuario = chaveUsuario;
    }

    public int getChaveQuestao() {
        return chaveQuestao;
    }

    public void setChaveQuestao(int chaveQuestao) {
        this.chaveQuestao = chaveQuestao;
    }

    public int getQuestaoRespondida() {
        return questaoRespondida;
    }

    public void setQuestaoRespondida(int questaoRespondida) {
        this.questaoRespondida = questaoRespondida;
    }
}
