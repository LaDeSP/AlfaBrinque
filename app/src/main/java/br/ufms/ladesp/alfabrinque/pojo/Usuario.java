package br.ufms.ladesp.alfabrinque.pojo;


public class Usuario {
    private int usuarioId;
    private String apelido;
    private int avatarUrl;


    public Usuario() {
    }

    public Usuario(String apelido, int avatarUrl) {
        this.apelido = apelido;
        this.avatarUrl = avatarUrl;
    }

    public Usuario(int usuarioId, String apelido, int avatarUrl) {
        this.usuarioId = usuarioId;
        this.apelido = apelido;
        this.avatarUrl = avatarUrl;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }


    public int getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(int avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
