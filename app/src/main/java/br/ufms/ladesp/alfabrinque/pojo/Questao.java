package br.ufms.ladesp.alfabrinque.pojo;

public class Questao {
    private int questaoID;
    private int chaveCatogoria;
    private int imagemUrl;
    private String respostaCerta;
    private int ordem;
    private String somUrl;

    public Questao() {
    }

    public Questao(int questaoID, int chaveCatogoria, int imagemUrl, String respostaCerta, int ordem, String somUrl) {
        this.questaoID = questaoID;
        this.chaveCatogoria = chaveCatogoria;
        this.imagemUrl = imagemUrl;
        this.respostaCerta = respostaCerta;
        this.ordem = ordem;
        this.somUrl = somUrl;
    }


    public Questao(int chaveCatogoria, int imagemUrl, String respostaCerta, int ordem) {
        this.chaveCatogoria = chaveCatogoria;
        this.imagemUrl = imagemUrl;
        this.respostaCerta = respostaCerta;
        this.ordem = ordem;
    }

    public Questao(int chaveCatogoria, int imagemUrl, String respostaCerta, int ordem, String somUrl) {
        this.chaveCatogoria = chaveCatogoria;
        this.imagemUrl = imagemUrl;
        this.respostaCerta = respostaCerta;
        this.ordem = ordem;
        this.somUrl = somUrl;
    }

    public String getSomUrl() {
        return somUrl;
    }

    public void setSomUrl(String somUrl) {
        this.somUrl = somUrl;
    }

    public int getQuestaoID() {
        return questaoID;
    }

    public void setQuestaoID(int questaoID) {
        this.questaoID = questaoID;
    }


    public int getChaveCatogoria() {
        return chaveCatogoria;
    }

    public void setChaveCatogoria(int chaveCatogoria) {
        this.chaveCatogoria = chaveCatogoria;
    }

    public int getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(int imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getRespostaCerta() {
        return respostaCerta;
    }

    public void setRespostaCerta(String respostaCerta) {
        this.respostaCerta = respostaCerta;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }
}
