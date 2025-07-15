package gestione_libreria.controller_command;

import gestione_libreria.grafica.Observer;
import gestione_libreria.model.Libro;

import java.util.ArrayList;
import java.util.List;

public abstract class GestoreAbstract {

    private List<Observer> osservatori = new ArrayList<>();
    List<Libro> libri = new ArrayList<>();
    List<Libro> libriFiltrati = new ArrayList<>();


    //Observer
    public void aggiungiObserver(Observer osservatore) {
        this.osservatori.add(osservatore);
    }

    public void rimuoviObserver(Observer osservatore) {
        this.osservatori.remove(osservatore);
    }

    public void notificaObserver() {
        List<Libro> listaAggiornata=null;
        if(!(libriFiltrati==null)) {
            listaAggiornata = new ArrayList<>(libriFiltrati);
        }
        else {
            listaAggiornata = libri;
        }
        for (Observer o : osservatori) {
            o.aggiorna(listaAggiornata);
        }
    }

}
