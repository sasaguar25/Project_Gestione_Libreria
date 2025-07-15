package gestione_libreria.archivio;

import gestione_libreria.controller_command.AggiungiLibroCommand;
import gestione_libreria.controller_command.Command;
import gestione_libreria.controller_command.GestoreLibri;
import gestione_libreria.factory.LibroCompletoFactory;
import gestione_libreria.model.Libro;

public class ArchivioLibriTest {
    public static void main(String[] args) {
        GestoreLibri gestore = GestoreLibri.getInstance();

        Libro libro1 =new LibroCompletoFactory(
                "Il nome della rosa",
                "umberto Eco",
                "9788804491013",
                "Giallo storico",
                5,
                Libro.StatoLettura.TERMINATO)
                .creaLibro();
        Libro libro2 =new LibroCompletoFactory(
                "1984",
                "George Orwell",
                "9780451524935",
                "Distopico",
                4,
                Libro.StatoLettura.DA_LEGGERE)
                .creaLibro();

        Command comando1 = new AggiungiLibroCommand(gestore, libro1);
        Command comando2 = new AggiungiLibroCommand(gestore, libro2);
        comando1.esegui();
        comando2.esegui();

        // Salva su JSON
        ArchivioLibri.salvaLibri(gestore.getLibri());


        gestore.getLibri().clear();
        gestore.getLibri().addAll(ArchivioLibri.caricaLibri());


        for (Libro l : gestore.getLibri()) {
            System.out.println(l.getTitolo() + " - " + l.getAutore());
        }
    }
}

