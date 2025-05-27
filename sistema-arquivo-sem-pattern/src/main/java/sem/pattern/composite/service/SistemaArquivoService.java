package sem.pattern.composite.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import sem.pattern.composite.dto.ComponenteMapper;
import sem.pattern.composite.model.Arquivo;
import sem.pattern.composite.model.Pasta;


@Service
public class SistemaArquivoService {

    private final Pasta raiz = new Pasta("raiz");

    public Pasta getRaiz() {
        return raiz;
    }

    public Map<String, Object> getRaizDTO() {
        return ComponenteMapper.pastaParaDTO(raiz);
    }

    public Optional<Object> buscarPorCaminho(String caminho) {
        String[] partes = caminho.split("/");
        return buscarRecursivamente(raiz, partes, 1);
    }

    private Optional<Object> buscarRecursivamente(Pasta atual, String[] partes, int index) {
        if (index >= partes.length) {
            return Optional.of(atual);
        }

        for (Object c : atual.getComponentes()) {
            if (c instanceof Arquivo arquivo && arquivo.getNome().equals(partes[index])) {
                if (index == partes.length - 1) return Optional.of(arquivo);
            } else if (c instanceof Pasta pasta && pasta.getNome().equals(partes[index])) {
                return buscarRecursivamente(pasta, partes, index + 1);
            }
        }
        return Optional.empty();
    }

    public boolean adicionarComponente(String caminhoDestino, Object componente) {
        Optional<Object> destino = buscarPorCaminho(caminhoDestino);
        if (destino.isPresent() && destino.get() instanceof Pasta pasta) {
            pasta.adicionarComponente(componente);
            if (componente instanceof Arquivo arquivo) arquivo.definirPai(pasta);
            if (componente instanceof Pasta pastaFilha) pastaFilha.definirPai(pasta);
            return true;
        }
        return false;
    }

    public boolean moverComponente(String caminhoOrigem, String caminhoDestino) {
        Optional<Object> origemOpt = buscarPorCaminho(caminhoOrigem);
        Optional<Object> destinoOpt = buscarPorCaminho(caminhoDestino);

        if (origemOpt.isEmpty() || destinoOpt.isEmpty()) return false;

        Object origem = origemOpt.get();
        Object destino = destinoOpt.get();

        if (!(destino instanceof Pasta destinoPasta)) return false;

        if (origem instanceof Pasta pastaOrigem) {
            if (isSubdiretorio(pastaOrigem, destinoPasta)) return false;
        }

        if (origem instanceof Arquivo arquivo) {
            Pasta paiAntigo = arquivo.getPai();
            if (paiAntigo != null) {
                paiAntigo.getComponentes().remove(arquivo);
            }
            destinoPasta.adicionarComponente(arquivo);
            arquivo.definirPai(destinoPasta);
            return true;
        } else if (origem instanceof Pasta pastaOrigem) {
            Pasta paiAntigo = pastaOrigem.obterComponentePai();
            if (paiAntigo != null) {
                paiAntigo.getComponentes().remove(pastaOrigem);
            }
            destinoPasta.adicionarComponente(pastaOrigem);
            pastaOrigem.definirPai(destinoPasta);
            return true;
        }

        return false;
    }

    private boolean isSubdiretorio(Pasta origem, Pasta destino) {
        Pasta atual = destino;
        while (atual != null) {
            if (atual == origem) return true;
            atual = atual.obterComponentePai();
        }
        return false;
    }

    public boolean deletarComponente(String caminho) {
        Optional<Object> alvoOpt = buscarPorCaminho(caminho);
        if (alvoOpt.isEmpty()) return false;

        Object alvo = alvoOpt.get();

        if (alvo instanceof Arquivo arquivo) {
            Pasta pai = arquivo.getPai();
            if (pai != null) {
                return pai.getComponentes().remove(arquivo);
            }
        } else if (alvo instanceof Pasta pasta) {
            Pasta pai = pasta.obterComponentePai();
            if (pai != null) {
                return pai.getComponentes().remove(pasta);
            }
        }

        return false;
    }

    public boolean renomearComponente(String caminho, String novoNome) {
        Optional<Object> alvoOpt = buscarPorCaminho(caminho);
        if (alvoOpt.isEmpty()) return false;

        Object alvo = alvoOpt.get();

        if (alvo instanceof Arquivo arquivo) {
            arquivo.setNome(novoNome);
            return true;
        } else if (alvo instanceof Pasta pasta) {
            pasta.setNome(novoNome);
            return true;
        }

        return false;
    }
}