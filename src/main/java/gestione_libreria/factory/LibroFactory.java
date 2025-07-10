package gestione_libreria.factory;

import gestione_libreria.model.Libro;

public class LibroFactory {

    public static Libro creaLibroBase(String titolo, String autore) {
        return new Libro.Builder()
                .titolo(titolo)
                .autore(autore)
                .isbn("N/A")
                .genere("Generico")
                .valutazione(0)
                .statoLettura(Libro.StatoLettura.DA_LEGGERE)
                .build();
    }

    public static Libro creaLibroConIsbn(String titolo, String autore, String isbn) {
        return new Libro.Builder()
                .titolo(titolo)
                .autore(autore)
                .isbn(isbn)
                .genere("Generico")
                .valutazione(0)
                .statoLettura(Libro.StatoLettura.DA_LEGGERE)
                .build();
    }

    public static Libro creaLibroCompleto(String titolo, String autore, String isbn, String genere, int valutazione, Libro.StatoLettura stato) {
        return new Libro.Builder()
                .titolo(titolo)
                .autore(autore)
                .isbn(isbn)
                .genere(genere)
                .valutazione(valutazione)
                .statoLettura(stato)
                .build();
    }
}