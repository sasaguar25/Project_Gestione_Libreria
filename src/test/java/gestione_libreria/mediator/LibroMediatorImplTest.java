package gestione_libreria.mediator;

import gestione_libreria.controller.GestoreLibri;
import gestione_libreria.factory.LibroCompletoFactory;
import gestione_libreria.grafica.CampoLibroPanel;
import gestione_libreria.grafica.ListaLibriPanel;
import gestione_libreria.model.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class LibroMediatorImplTest {

    private LibroMediatorImpl mediator;
    private GestoreLibri gestore;
    private CampoLibroPanel campoPanel;
    private ListaLibriPanel listaPanel;

    @BeforeEach
    public void setUp() {
        gestore = GestoreLibri.getInstance();
        gestore.getLibri().clear(); // Pulisce i dati

        mediator = new LibroMediatorImpl();
        campoPanel = mock(CampoLibroPanel.class);
        listaPanel = mock(ListaLibriPanel.class);

        mediator.setCampoPanel(campoPanel);
        mediator.setListaPanel(listaPanel);
    }

    @Test
    public void testAggiuntaLibro() {
        Libro libro = new LibroCompletoFactory("Titolo Test", "Autore Test", "123", "Narrativa", 5, Libro.StatoLettura.TERMINATO).creaLibro();
        when(campoPanel.creaLibroDaCampi()).thenReturn(libro);

        mediator.aggiungiLibro();

        List<Libro> libri = gestore.getLibri();
        assertEquals(1, libri.size());
        assertEquals("Titolo Test", libri.get(0).getTitolo());
    }

    @Test
    public void testRimuoviLibro() {
        Libro libro = new LibroCompletoFactory("Rimuovi", "Autore", "456", "Saggio", 3, Libro.StatoLettura.IN_LETTURA).creaLibro();
        gestore.getLibri().add(libro);

        when(listaPanel.getLibroSelezionato()).thenReturn(libro);

        mediator.rimuoviLibro();

        assertFalse(gestore.getLibri().contains(libro));
    }

    @Test
    public void testModificaLibro() {
        Libro libro = new LibroCompletoFactory("Titolo", "Autore", "789", "Giallo", 4, Libro.StatoLettura.DA_LEGGERE).creaLibro();
        gestore.getLibri().add(libro);

        when(listaPanel.getLibroSelezionato()).thenReturn(libro);
        Libro modificato = new LibroCompletoFactory("Titolo Mod", "Autore", "789", "Giallo", 5, Libro.StatoLettura.DA_LEGGERE).creaLibro();
        when(campoPanel.creaLibroDaCampi()).thenReturn(modificato);

        mediator.modificaLibro();

        assertEquals("Titolo Mod", libro.getTitolo());
        assertEquals(5, libro.getValutazione());
    }

    @Test
    public void testCercaPerTitolo() {
        gestore.getLibri().add(new LibroCompletoFactory("Harry Potter", "J.K. Rowling", "111", "Fantasy", 5, Libro.StatoLettura.TERMINATO).creaLibro());
        gestore.getLibri().add(new LibroCompletoFactory("Il Signore degli Anelli", "Tolkien", "222", "Fantasy", 5, Libro.StatoLettura.TERMINATO).creaLibro());

        mediator.cerca("Titolo", "Harry");

        //verifica aggiornamento lista
        verify(listaPanel).aggiornaLista(any());
    }

    @Test
    public void testFiltraEOrdina() {
        gestore.getLibri().add(new LibroCompletoFactory("Z", "A", "333", "Fantasy", 2, Libro.StatoLettura.IN_LETTURA).creaLibro());
        gestore.getLibri().add(new LibroCompletoFactory("A", "B", "444", "Fantasy", 4, Libro.StatoLettura.IN_LETTURA).creaLibro());
        gestore.getLibri().add(new LibroCompletoFactory("M", "C", "555", "Giallo", 3, Libro.StatoLettura.TERMINATO).creaLibro());

        // Solo libri "Fantasy", valutazione = 4, ordinati per Titolo decrescente
        mediator.filtraEOrdina("Fantasy", Libro.StatoLettura.IN_LETTURA, 4, "Titolo", "Decrescente");

        verify(listaPanel).aggiornaLista(argThat(lista ->
                listHasSingleLibroWithTitle(lista, "A")
        ));
    }

    private boolean listHasSingleLibroWithTitle(List<Libro> lista, String titolo) {
        return lista.size() == 1 && lista.get(0).getTitolo().equals(titolo);
    }
}
