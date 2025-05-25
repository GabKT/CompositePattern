package pattern.composite.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public abstract class Composite extends Componente {

    public Composite() {}

    public Composite(String nome) {
        super(nome, 0);
    }

    @JsonManagedReference
    private List<Componente> componentes = new ArrayList<>();

    public void adicionarComponente(Componente componente){
        componentes.add(componente);
    }

    public void removerComponente(Componente componente){
        componentes.remove(componente);
    }

    @Override
    public int getTamanho() {
        int total = 0;
        for (Componente c : getComponentes()) {
            total += c.getTamanho();
        }
        return total;
    }

    public List<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<Componente> componentes) {
        this.componentes = componentes;
    }

}
