package gestione_libreria.controller_command;

import gestione_libreria.memento.Caretaker;
import gestione_libreria.memento.LibreriaMemento;

public class RipristinoCommand implements Command {
    private GestoreLibri gestore;
    private Caretaker storico;
    private LibreriaMemento statoCorrente;

    public RipristinoCommand(GestoreLibri gestore, Caretaker storico) {
        this.gestore = gestore;
        this.storico = storico;
        statoCorrente=new LibreriaMemento(gestore.getLibri());
    }

    @Override
    public void esegui() {
        gestore.ripristinaDaMemento(storico.ripristinaUltimo());
    }

    @Override
    public void annulla() {
        gestore.ripristinaDaMemento(statoCorrente);
    }
}