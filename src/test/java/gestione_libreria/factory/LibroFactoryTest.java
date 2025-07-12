package gestione_libreria.factory;

import gestione_libreria.model.Libro;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibroFactoryTest {

    @Test
    public void testLibroBaseFactory() {
        LibroFactory factory = new LibroBaseFactory("1984", "George Orwell");
        Libro libro = factory.creaLibro();

        assertEquals("1984", libro.getTitolo());
        assertEquals("George Orwell", libro.getAutore());
        assertEquals("N/A", libro.getIsbn());
        assertEquals("Generico", libro.getGenere());
        assertEquals(0, libro.getValutazione());
        assertEquals(Libro.StatoLettura.DA_LEGGERE, libro.getStatoLettura());
    }

    @Test
    public void testLibroConIsbnFactory() {
        LibroFactory factory = new LibroConISBNFactory("Il Gattopardo", "Tomasi di Lampedusa", "12345");
        Libro libro = factory.creaLibro();

        assertEquals("Il Gattopardo", libro.getTitolo());
        assertEquals("Tomasi di Lampedusa", libro.getAutore());
        assertEquals("12345", libro.getIsbn());
        assertEquals("Generico", libro.getGenere());
        assertEquals(0, libro.getValutazione());
        assertEquals(Libro.StatoLettura.DA_LEGGERE, libro.getStatoLettura());
    }

    @Test
    public void testLibroCompletoFactory() {
        LibroFactory factory = new LibroCompletoFactory(
                "Il Signore degli Anelli",
                "J.R.R. Tolkien",
                "987654321",
                "Fantasy",
                5,
                Libro.StatoLettura.TERMINATO
        );

        Libro libro = factory.creaLibro();

        assertEquals("Il Signore degli Anelli", libro.getTitolo());
        assertEquals("J.R.R. Tolkien", libro.getAutore());
        assertEquals("987654321", libro.getIsbn());
        assertEquals("Fantasy", libro.getGenere());
        assertEquals(5, libro.getValutazione());
        assertEquals(Libro.StatoLettura.TERMINATO, libro.getStatoLettura());
    }
}

