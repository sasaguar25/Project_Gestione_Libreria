package gestione_libreria.controller_command;

import gestione_libreria.archivio.ArchivioLibri;
import gestione_libreria.grafica.Observer;
import gestione_libreria.memento.LibreriaMemento;
import gestione_libreria.model.Libro;

import java.util.ArrayList;
import java.util.List;

public class GestoreLibri {

    private static GestoreLibri istanza;

    private List<Libro> libri = new ArrayList<>();
    private List<Libro> libriFiltrati = new ArrayList<>();
    private List<Observer> osservatori = new ArrayList<>();

    private GestoreLibri() {}

    //Singleton: una sola istanza per GestoreLibri
    public static GestoreLibri getInstance() {
        if (istanza == null) {
            istanza = new GestoreLibri();
        }
        return istanza;
    }

    //metodi gestione libri
    public void aggiungiLibro(Libro libro) {
        this.libri.add(libro);
        notificaObserver();
    }

    public void rimuoviLibro(Libro libro) {
        this.libri.remove(libro);
        notificaObserver();
    }

    public List<Libro> getLibri() {
        return libri;
    }

    public void setLibriFiltrati(List<Libro> libriFiltrati){
        this.libriFiltrati = libriFiltrati;
        notificaObserver();
    }

    //Observer
    public void aggiungiObserver(Observer osservatore) {
        this.osservatori.add(osservatore);
    }

    private void notificaObserver() {
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


    //Memento: ripristino stati passati libreria
    public LibreriaMemento creaMemento() {
        return new LibreriaMemento(new ArrayList<>(libri));
    }

    protected void ripristinaDaMemento(LibreriaMemento memento) {
        libri = new ArrayList<>(memento.getStato());
        notificaObserver();
    }

    public void caricaDaFile() {
        this.libri = ArchivioLibri.caricaLibri();
    }
}