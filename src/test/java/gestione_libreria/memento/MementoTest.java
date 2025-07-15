package gestione_libreria.memento;

import gestione_libreria.controller_command.AggiungiLibroCommand;
import gestione_libreria.controller_command.Command;
import gestione_libreria.controller_command.GestoreLibri;
import gestione_libreria.controller_command.RipristinoCommand;
import gestione_libreria.model.Libro;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MementoTest {

    @Test
    public void testSalvataggioERipristinoStato() {
        GestoreLibri gestore = GestoreLibri.getInstance();
        gestore.getLibri().clear();

        Caretaker storico = new Caretaker();

        Libro libro1 = new Libro.Builder()
                .titolo("1984")
                .autore("George Orwell")
                .isbn("9780451524935")
                .genere("Distopico")
                .valutazione(5)
                .statoLettura(Libro.StatoLettura.TERMINATO)
                .build();
        Command comando1 = new AggiungiLibroCommand(gestore, libro1);
        comando1.esegui();
        storico.salvaStato(gestore.creaMemento());
        Libro libro2 = new Libro.Builder()
                .titolo("Frankenstein")
                .autore("Mary Shelley")
                .isbn("444555666")
                .genere("Horror")
                .valutazione(5)
                .statoLettura(Libro.StatoLettura.TERMINATO)
                .build();
        Command comando2 = new AggiungiLibroCommand(gestore, libro2);
        comando2.esegui();

        // Verifico che ci siano 2 libri ora
        assertEquals(2, gestore.getLibri().size());

        // Ripristino allo stato precedente
        Command comando3 = new RipristinoCommand(gestore, storico);
        comando3.esegui();

        // Verifico che ci sia solo libro1
        assertEquals(1, gestore.getLibri().size());
        assertTrue(gestore.getLibri().contains(libro1));
        assertFalse(gestore.getLibri().contains(libro2));
    }
}
