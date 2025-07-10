package gestione_libreria.memento;

import gestione_libreria.model.Libro;

import java.util.ArrayList;
import java.util.List;

public class LibreriaMemento {
    private final List<Libro> stato;

    public LibreriaMemento(List<Libro> stato) {
        this.stato = new ArrayList<>(stato);
    }

    public List<Libro> getStato() {
        return new ArrayList<>(stato);
    }
}