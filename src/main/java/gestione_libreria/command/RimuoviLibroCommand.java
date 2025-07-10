package gestione_libreria.command;

import gestione_libreria.controller.GestoreLibri;
import gestione_libreria.model.Libro;

public class RimuoviLibroCommand implements Command {
    private GestoreLibri gestore;
    private Libro libro;

    public RimuoviLibroCommand(GestoreLibri gestore, Libro libro) {
        this.gestore = gestore;
        this.libro = libro;
    }

    @Override
    public void esegui() {
        gestore.rimuoviLibro(libro);
    }

    @Override
    public void annulla() {
        gestore.aggiungiLibro(libro);
    }
}
