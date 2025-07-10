package gestione_libreria.controller;

import gestione_libreria.memento.LibreriaMemento;
import gestione_libreria.model.Libro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestoreLibri {

    private static GestoreLibri istanza;

    private List<Libro> libri = new ArrayList<>();
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


    //Observer
    public void aggiungiObserver(Observer osservatore) {
        this.osservatori.add(osservatore);
    }

    private void notificaObserver() {
        for (Observer o : osservatori) {
            o.aggiorna();
        }
    }


    //Memento: ripristino stati passati libreria
    public LibreriaMemento creaMemento() {
        return new LibreriaMemento(new ArrayList<>(libri));
    }

    public void ripristinaDaMemento(LibreriaMemento memento) {
        libri = new ArrayList<>(memento.getStato());
        notificaObserver();
    }



}