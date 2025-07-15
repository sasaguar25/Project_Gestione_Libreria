package gestione_libreria.controller_command;

import gestione_libreria.model.Libro;

import java.util.HashMap;
import java.util.Map;

public class ModificaLibroCommand implements Command {
    private final Libro libro,modificato;
    private final Map<String, String> nuoviValori;
    private final Map<String, String> backupValori = new HashMap<>();
    private GestoreLibri gestore;

    public ModificaLibroCommand(Libro libro, Map<String, String> nuoviValori, GestoreLibri gestore) {
        this.libro = libro;
        this.modificato=libro;
        this.nuoviValori = nuoviValori;
        this.gestore = gestore;
    }

    @Override
    public void esegui() {
        for (Map.Entry<String, String> entry : nuoviValori.entrySet()) {
            String campo = entry.getKey();
            String nuovoValore = entry.getValue();

            switch (campo.toLowerCase()) {
                case "titolo":
                    backupValori.put("titolo", libro.getTitolo());
                    modificato.setTitolo(nuovoValore);
                    break;
                case "autore":
                    backupValori.put("autore", libro.getAutore());
                    modificato.setAutore(nuovoValore);
                    break;
                case "isbn":
                    backupValori.put("isbn", libro.getIsbn());
                    modificato.setIsbn(nuovoValore);
                    break;
                case "genere":
                    backupValori.put("genere", libro.getGenere());
                    modificato.setGenere(nuovoValore);
                    break;
                case "valutazione":
                    backupValori.put("valutazione", String.valueOf(libro.getValutazione()));
                    modificato.setValutazione(Integer.parseInt(nuovoValore));
                    break;
                case "stato":
                    backupValori.put("stato", libro.getStatoLettura().name());
                    modificato.setStatoLettura(Libro.StatoLettura.valueOf(nuovoValore));
                    break;
                default:
                    throw new IllegalArgumentException("Campo non valido: " + campo);
            }
        }
        gestore.rimuoviLibro(libro);
        gestore.aggiungiLibro(modificato);
    }

    @Override
    public void annulla() {
        gestore.rimuoviLibro(modificato);
        gestore.aggiungiLibro(libro);
    }
}