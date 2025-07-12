package gestione_libreria.factory;

import gestione_libreria.model.Libro;

public class LibroBaseFactory implements LibroFactory {
    private final String titolo;
    private final String autore;

    public LibroBaseFactory(String titolo, String autore) {
        this.titolo = titolo;
        this.autore = autore;
    }

    @Override
    public Libro creaLibro() {
        return new Libro.Builder()
                .titolo(titolo)
                .autore(autore)
                .isbn("N/A")
                .genere("Generico")
                .valutazione(0)
                .statoLettura(Libro.StatoLettura.DA_LEGGERE)
                .build();
    }
}

