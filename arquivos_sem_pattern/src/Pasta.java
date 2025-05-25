import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Pasta {
    private final String id;
    private String nome;
    private List<Object> conteudo;
    private Pasta pai;

    public Pasta(String nome) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.conteudo = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getTamanho() {
        int total = 0;
        for (Object item : getComponentes()) {
            if (item instanceof Arquivo) {
                total += ((Arquivo) item).getTamanho();
            }
            if (item instanceof Pasta) {
                total += ((Pasta) item).getTamanho();
            }
        }
        return total;
    }

    public List<Object> getComponentes() {
        return conteudo;
    }

    public void setComponentes(List<Object> conteudo) {
        this.conteudo = conteudo;
    }

    public Pasta obterComponentePai() {
        return pai;
    }

    public void definirPai(Pasta pai) {
        this.pai = pai;
    }

    public String obterCaminho() {
        if (pai == null)
            return "/" + nome;
        return pai.obterCaminho() + "/" + nome;
    }

    public void adicionarComponente(Object item) {
        if (item instanceof Arquivo) {
            ((Arquivo) item).definirPai(this);
        } else if (item instanceof Pasta) {
            ((Pasta) item).definirPai(this);
        }
        conteudo.add(item);
    }

    public void deletar(Object item) {
        pai.removerComponente(item);
    }

    public void removerComponente(Object item) {
        conteudo.remove(item);
    }

    public void renomear(String nome) {
        this.nome = nome;
    }

    public void moverPara(Pasta destino) {
        if (conteudo.contains(destino) || isSubpasta(destino)) {
            System.out.println("\nNão é possível mover a pasta para um subdiretório dela.");
            return;
        }
        if (pai != null && destino != null) {
            pai.removerComponente(this);
            destino.adicionarComponente(this);
            System.out.println("\nPasta movida com sucesso.");
        }
    }

    private boolean isSubpasta(Pasta pasta) {
        for (Object item : conteudo) {
            if (item instanceof Pasta subpasta) {
                if (subpasta == pasta || subpasta.isSubpasta(pasta)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void exibir(String indentacao) {
        System.out.println(indentacao + nome + ":");
        for (Object item : conteudo) {
            if (item instanceof Arquivo) {
                Arquivo arq = (Arquivo) item;
                System.out.println(indentacao + "   " + arq.getNome() + " (Tamanho: " + arq.getTamanho() + ")");
            } else if (item instanceof Pasta) {
                Pasta subpasta = (Pasta) item;
                System.out.println("Tamanho da pasta " + subpasta.getNome() + " " + subpasta.getTamanho());
                subpasta.exibir(indentacao + "   ");
            }
        }
    }
}