package sem.pattern.composite.model;

import java.util.UUID;

public class Arquivo {
    private final String id;
    private String nome;
    private int tamanho;
    private Pasta pai;

    public Arquivo(String nome, int tamanho) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.tamanho = tamanho;
    }

    public String getId(){
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getTamanho(){
        return tamanho;
    }

    public Pasta getPai() {
        return pai;
    }

    public Pasta obterComponentePai() {
        return pai;
    }

    public void definirPai(Pasta pai) {
        this.pai = pai;
    }

    public String obterCaminho(){
        if (pai == null) return "/" + nome;
        return pai.obterCaminho() + "/" + nome;
    }

    public void renomear(String nome) {
        this.nome = nome;
    }

    public void deletar(Object item) {
        pai.getComponentes().remove(item);
    }

    public void moverPara(Pasta destino) {
        if (pai != null && destino != null) {
            pai.removerComponente(this);
            destino.adicionarComponente(this);
        }
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }
}