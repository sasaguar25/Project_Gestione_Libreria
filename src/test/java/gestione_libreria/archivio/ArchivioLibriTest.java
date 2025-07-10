package gestione_libreria.archivio;

import gestione_libreria.controller.GestoreLibri;
import gestione_libreria.model.Libro;

public class ArchivioLibriTest {
    public static void main(String[] args) {
        GestoreLibri gestore = GestoreLibri.getInstance();

        Libro libro1 = new Libro.Builder()
                .titolo("Il nome della rosa")
                .autore( "umberto Eco")
                .isbn("9788804491013")
                .genere("Giallo storico")
                .valutazione(5)
                .statoLettura(Libro.StatoLettura.TERMINATO)
                .build();

        Libro libro2 = new Libro.Builder()
                .titolo("1984")
                .autore( "George Orwell")
                .isbn("9780451524935")
                .genere("Distopico")
                .valutazione(4)
                .statoLettura(Libro.StatoLettura.DA_LEGGERE)
                .build();

        gestore.aggiungiLibro(libro1);
        gestore.aggiungiLibro(libro2);

        // Salva su JSON
        ArchivioLibri.salvaLibri(gestore.getLibri());


        gestore.getLibri().clear();
        gestore.getLibri().addAll(ArchivioLibri.caricaLibri());


        for (Libro l : gestore.getLibri()) {
            System.out.println(l.getTitolo() + " - " + l.getAutore());
        }
    }
}

