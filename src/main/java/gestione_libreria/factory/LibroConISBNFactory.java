package gestione_libreria.factory;

import gestione_libreria.model.Libro;

public class LibroConISBNFactory implements LibroFactory {
    private final String titolo;
    private final String autore;
    private final String isbn;

    public LibroConISBNFactory(String titolo, String autore, String isbn) {
        this.titolo = titolo;
        this.autore = autore;
        this.isbn = isbn;
    }

    @Override
    public Libro creaLibro() {
        return new Libro.Builder()
                .titolo(titolo)
                .autore(autore)
                .isbn(isbn)
                .genere("Generico")
                .valutazione(0)
                .statoLettura(Libro.StatoLettura.DA_LEGGERE)
                .build();
    }
}
