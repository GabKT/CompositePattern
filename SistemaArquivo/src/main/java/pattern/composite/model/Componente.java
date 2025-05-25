package pattern.composite.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipo"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Arquivo.class, name = "arquivo"),
    @JsonSubTypes.Type(value = Pasta.class, name = "pasta")
})
public abstract class Componente {

    private final String id;
    private String nome;
    private int tamanho;
    @JsonBackReference
    private Componente pai;

    public Componente() {
        this.id = UUID.randomUUID().toString();
    }

    public Componente(String nome, int tamanho) {
        this.id = UUID.randomUUID().toString();
        this.tamanho = tamanho;
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

    public int getTamanho(){
        return tamanho;
    }

    public String getId(){
        return id;
    }

    public Componente obterComponentePai() {
        return pai;
    }

    public void definirPai(Componente pai) {
        this.pai = pai;
    }

    public String obterCaminho(){
        if (pai == null) return "/" + nome;
        return pai.obterCaminho() + "/" + nome;
    }
    
    public void moverPara(Componente novaPasta) {
        if (pai instanceof Composite) {
            Composite compositeAntiga = (Composite) pai;
            compositeAntiga.removerComponente(this);
        }

        if (novaPasta instanceof Composite) {
            Composite compositeNova = (Composite) novaPasta;
            compositeNova.adicionarComponente(this);
            definirPai(novaPasta);
        }
    }
    
    public void deletar() {
        if (pai instanceof Composite) {
            Composite composite = (Composite) pai;
            composite.removerComponente(this);
        }
    }

    public void renomear(String novoNome){
        this.nome = novoNome;
    }

}
