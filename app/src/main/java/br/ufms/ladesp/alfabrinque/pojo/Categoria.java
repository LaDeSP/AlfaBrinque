package br.ufms.ladesp.alfabrinque.pojo;


public class Categoria {
    private int categoriaId;
    private String categoria;

    public Categoria() {
    }

    public Categoria(int categoriaId, String categoria) {
        this.categoriaId = categoriaId;
        this.categoria = categoria;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
