package gestione_libreria.controller;



import gestione_libreria.model.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GestoreLibriTest {

    private GestoreLibri gestore;

    @BeforeEach
    public void setUp() {
        gestore = GestoreLibri.getInstance();
        gestore.getLibri().clear(); // pulizia prima di testing
    }

    @Test
    public void testAggiuntaLibro() {
        Libro libro = new Libro.Builder()
                .titolo("1984")
                .autore("George Orwell")
                .isbn("9780451524935")
                .genere("Distopico")
                .valutazione(5)
                .statoLettura(Libro.StatoLettura.DA_LEGGERE)
                .build();

        gestore.aggiungiLibro(libro);

        assertEquals(1, gestore.getLibri().size());
        assertEquals("1984", gestore.getLibri().get(0).getTitolo());
    }

    @Test
    public void testRimozioneLibro() {
        Libro libro = new Libro.Builder()
                .titolo("1984")
                .autore("George Orwell")
                .isbn("9780451524935")
                .genere("Distopico")
                .valutazione(5)
                .statoLettura(Libro.StatoLettura.DA_LEGGERE)
                .build();

        gestore.aggiungiLibro(libro);
        gestore.rimuoviLibro(libro);

        assertTrue(gestore.getLibri().isEmpty());
    }
}

