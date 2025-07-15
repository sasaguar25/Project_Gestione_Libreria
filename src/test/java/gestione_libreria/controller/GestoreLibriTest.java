package gestione_libreria.controller;



import gestione_libreria.controller_command.GestoreLibri;
import gestione_libreria.model.Libro;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        Assertions.assertEquals(1, gestore.getLibri().size());
        Assertions.assertEquals("1984", gestore.getLibri().get(0).getTitolo());
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

        Assertions.assertTrue(gestore.getLibri().isEmpty());
    }
}

