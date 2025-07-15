package gestione_libreria.controller_command;

import gestione_libreria.model.Libro;

public class AggiungiLibroCommand implements Command {
    private GestoreLibri gestore;
    private Libro libro;

    public AggiungiLibroCommand(GestoreLibri gestore, Libro libro) {
        this.gestore = gestore;
        this.libro = libro;
    }

    @Override
    public void esegui() {
        gestore.aggiungiLibro(libro);
    }

    @Override
    public void annulla() {
        gestore.rimuoviLibro(libro);
    }
}
