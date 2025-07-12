package gestione_libreria.mediator;

import gestione_libreria.model.Libro;

public interface LibroMediator {
    boolean aggiungiLibro();
    void rimuoviLibro();
    boolean modificaLibro();
    void annullaUltimaAzione();
    void selezionaLibro(Libro libro);
    void cerca(String campo, String valore);
    void filtraEOrdina(String genere, Libro.StatoLettura stato, Integer valutazione, String campoOrdina, String direzione);
    void resetVista();
}

