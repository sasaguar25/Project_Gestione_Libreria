package gestione_libreria.factory;

import gestione_libreria.model.Libro;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LibroFactoryTest{
@Test
    public void testCreaLibroBase() {
        Libro libro = LibroFactory.creaLibroBase("Il nome della rosa", "Umberto Eco");
        assertEquals("Il nome della rosa", libro.getTitolo());
        assertEquals("Umberto Eco", libro.getAutore());
        assertEquals("Generico", libro.getGenere());
        assertEquals(Libro.StatoLettura.DA_LEGGERE, libro.getStatoLettura());
    }

    @Test
    public void testCreaLibroCompleto() {
        Libro libro = LibroFactory.creaLibroCompleto(
                "1984", "George Orwell", "123456789", "Distopico", 5, Libro.StatoLettura.TERMINATO
        );
        assertEquals("1984", libro.getTitolo());
        assertEquals("George Orwell", libro.getAutore());
        assertEquals("123456789", libro.getIsbn());
        assertEquals("Distopico", libro.getGenere());
        assertEquals(5, libro.getValutazione());
        assertEquals(Libro.StatoLettura.DA_LEGGERE, libro.getStatoLettura());
    }

}
