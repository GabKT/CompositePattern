package sem.pattern.composite.dto;

import java.util.List;

public class ComponenteDTO {
    private String tipo;
    private String nome;
    private String id;
    private int tamanho;
    private List<ComponenteDTO> componentes;
    
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getTamanho() {
        return tamanho;
    }
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
    public List<ComponenteDTO> getComponentes() {
        return componentes;
    }
    public void setComponentes(List<ComponenteDTO> componentes) {
        this.componentes = componentes;
    }

    

}

