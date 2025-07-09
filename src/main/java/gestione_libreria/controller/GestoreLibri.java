package gestione_libreria.controller;

import gestione_libreria.model.Libro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestoreLibri {

    private static GestoreLibri istanza;

    private List<Libro> libri = new ArrayList<>();
    private List<Observer> osservatori = new ArrayList<>();

    private GestoreLibri() {}

    public static GestoreLibri getIstance() {
        if (istanza == null) {
            istanza = new GestoreLibri();
        }
        return istanza;
    }

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

    public void aggiungiObserver(Observer osservatore) {
        this.osservatori.add(osservatore);
    }

    private void notificaObserver() {
        for (Observer o : osservatori) {
            o.aggiorna();
        }
    }
}