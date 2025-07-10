package gestione_libreria.command;

import gestione_libreria.controller.GestoreLibri;
import gestione_libreria.model.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import gestione_libreria.model.Libro.StatoLettura;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {

    private GestoreLibri gestore;

    @BeforeEach
    public void setUp() {
        gestore = GestoreLibri.getInstance();
        gestore.getLibri().clear(); // pulizia prima di testing
    }

    @Test
    public void testEseguiEAggiuntaLibro() {
        GestoreLibri gestore = GestoreLibri.getInstance();
        gestore.getLibri().clear(); // Pulizia prima del test

        Libro libro = new Libro.Builder()
                .titolo("1984")
                .autore("George Orwell")
                .isbn("9780451524935")
                .genere("Distopico")
                .valutazione(5)
                .statoLettura(Libro.StatoLettura.DA_LEGGERE)
                .build();
        AggiungiLibroCommand comando = new AggiungiLibroCommand(gestore, libro);
        comando.esegui();

        assertTrue(gestore.getLibri().contains(libro));
    }

    @Test
    public void testAnnullaRimozioneLibro() {
        GestoreLibri gestore = GestoreLibri.getInstance();
        gestore.getLibri().clear();

        Libro libro = new Libro.Builder()
                .titolo("1984")
                .autore("George Orwell")
                .isbn("9780451524935")
                .genere("Distopico")
                .valutazione(5)
                .statoLettura(Libro.StatoLettura.DA_LEGGERE)
                .build();AggiungiLibroCommand comando = new AggiungiLibroCommand(gestore, libro);
        comando.esegui();
        comando.annulla();

        assertFalse(gestore.getLibri().contains(libro)); //verifico che non sia più in lista, dopo comando annulla
    }

    @Test
    public void testModificaParzialeCampi() {
        // Libro originale
        GestoreLibri gestore = GestoreLibri.getInstance();
        Libro libro = new Libro.Builder()
                .titolo("1984")
                .autore("George Orwell")
                .isbn("9780451524935")
                .genere("Distopico")
                .valutazione(5)
                .statoLettura(Libro.StatoLettura.DA_LEGGERE)
                .build();

        // Campi da modificare (solo titolo e valutazione)
        Map<String, String> modifiche = new HashMap<>();
        modifiche.put("titolo", "Titolo Aggiornato");
        modifiche.put("valutazione", "5");

        ModificaLibroCommand comando = new ModificaLibroCommand(libro, modifiche);

        // Esegui modifica
        comando.esegui();

        // Verifica modifiche applicate
        assertEquals("Titolo Aggiornato", libro.getTitolo());
        assertEquals(5, libro.getValutazione());
        assertEquals("Autore Originale", libro.getAutore()); // Non modificato
        assertEquals("Fantasy", libro.getGenere());
        assertEquals(Libro.StatoLettura.DA_LEGGERE, libro.getStatoLettura());

        // Annulla modifica
        comando.annulla();

        // Verifica che i valori originali siano ripristinati
        assertEquals("Titolo Originale", libro.getTitolo());
        assertEquals(4, libro.getValutazione());
    }

    @Test
    public void testNessunaModificaSeMappaVuota() {
        Object StatoLettura;
        Libro libro = new Libro.Builder()
                .titolo("1984")
                .autore("George Orwell")
                .isbn("9780451524935")
                .genere("Distopico")
                .valutazione(5)
                .statoLettura(Libro.StatoLettura.DA_LEGGERE)
                .build();
        Map<String, String> modifiche = new HashMap<>();

        ModificaLibroCommand comando = new ModificaLibroCommand(libro, modifiche);
        comando.esegui(); // Non deve modificare nulla

        // Tutti i valori restano invariati
        assertEquals("T1", libro.getTitolo());
        assertEquals("A1", libro.getAutore());
        assertEquals("111", libro.getIsbn());
        assertEquals("G1", libro.getGenere());
        assertEquals(3, libro.getValutazione());
        assertEquals(Libro.StatoLettura.TERMINATO, libro.getStatoLettura());
    }
}
