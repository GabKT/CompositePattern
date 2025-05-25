package pattern.composite.service;

import java.util.Optional;

import pattern.composite.model.Componente;
import pattern.composite.model.Pasta;

public class SistemaArquivoService {
    private final Pasta raiz = new Pasta("raiz");

    public Pasta getRaiz(){
        return raiz;
    }

    public Optional<Componente> buscarPorCaminho(String caminho) {
        String[] partes = caminho.split("/");
        return buscarRecursivamente(raiz, partes, 1);
    }

    private Optional<Componente> buscarRecursivamente(Pasta atual, String[] partes, int index) {
        if (index >= partes.length) return Optional.of(atual);

        for (Componente c : atual.getComponentes()) {
            if (c.getNome().equals(partes[index])) {
                if (c instanceof Pasta p){
                    return buscarRecursivamente(p, partes, index + 1);
                }
                else if (index == partes.length - 1){
                    System.out.println("Retornando um Componente! "+ c.getNome());
                    return Optional.of(c);
                }      
            }
        }
        System.out.println("Retornando nada!");
        return Optional.empty();
    }

    public boolean adicionarComponente(String caminhoDestino, Componente componente) {
        Optional<Componente> destino = buscarPorCaminho(caminhoDestino);
        if (destino.isPresent() && destino.get() instanceof Pasta pasta) {
            pasta.adicionarComponente(componente);
            componente.definirPai(pasta);
            return true;
        }
        return false;
    }

    public boolean moverComponente(String caminhoOrigem, String caminhoDestino) {
        Optional<Componente> origemOpt = buscarPorCaminho(caminhoOrigem);
        Optional<Componente> destinoOpt = buscarPorCaminho(caminhoDestino);

        if (origemOpt.isPresent() && destinoOpt.isPresent() && destinoOpt.get() instanceof Pasta destino) {
            Componente origem = origemOpt.get();

            if (isSubdiretorio(origem, destino)) {
                return false;
            }

            if (origem.obterComponentePai() instanceof Pasta pastaAntiga)
                pastaAntiga.removerComponente(origem);

            destino.adicionarComponente(origem);
            origem.definirPai(destino);
            return true;
        }

        return false;
    }

    public boolean deletarComponente(String caminho) {
        Optional<Componente> alvo = buscarPorCaminho(caminho);
        if (alvo.isPresent() && alvo.get().obterComponentePai() instanceof Pasta pai)
            return pai.getComponentes().remove(alvo.get());
        return false;
    }

    public boolean renomearComponente(String caminho, String novoNome) {
        Optional<Componente> alvo = buscarPorCaminho(caminho);
        alvo.ifPresent(c -> c.renomear(novoNome));
        return alvo.isPresent();
    }

    private boolean isSubdiretorio(Componente origem, Componente destino) {
        if (!(origem instanceof Pasta)) return false;

        Componente atual = destino;
        while (atual != null) {
            if (atual == origem) return true;
            atual = atual.obterComponentePai();
        }
        return false;
    }
}
