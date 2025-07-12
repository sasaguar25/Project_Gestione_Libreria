package gestione_libreria.factory;

import gestione_libreria.model.Libro;

public class LibroCompletoFactory implements LibroFactory {
    private final String titolo;
    private final String autore;
    private final String isbn;
    private final String genere;
    private final int valutazione;
    private final Libro.StatoLettura stato;

    public LibroCompletoFactory(String titolo, String autore, String isbn, String genere, int valutazione, Libro.StatoLettura stato) {
        this.titolo = titolo;
        this.autore = autore;
        this.isbn = isbn;
        this.genere = genere;
        this.valutazione = valutazione;
        this.stato = stato;
    }

    @Override
    public Libro creaLibro() {
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
