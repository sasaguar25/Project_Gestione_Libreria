package gestione_libreria.command;

import gestione_libreria.model.Libro;

import java.util.HashMap;
import java.util.Map;

public class ModificaLibroCommand implements Command {
    private final Libro libro;
    private final Map<String, String> nuoviValori;
    private final Map<String, String> backupValori = new HashMap<>();

    public ModificaLibroCommand(Libro libro, Map<String, String> nuoviValori) {
        this.libro = libro;
        this.nuoviValori = nuoviValori;
    }

    @Override
    public void esegui() {
        for (Map.Entry<String, String> entry : nuoviValori.entrySet()) {
            String campo = entry.getKey();
            String nuovoValore = entry.getValue();

            switch (campo.toLowerCase()) {
                case "titolo":
                    backupValori.put("titolo", libro.getTitolo());
                    libro.setTitolo(nuovoValore);
                    break;
                case "autore":
                    backupValori.put("autore", libro.getAutore());
                    libro.setAutore(nuovoValore);
                    break;
                case "isbn":
                    backupValori.put("isbn", libro.getIsbn());
                    libro.setIsbn(nuovoValore);
                    break;
                case "genere":
                    backupValori.put("genere", libro.getGenere());
                    libro.setGenere(nuovoValore);
                    break;
                case "valutazione":
                    backupValori.put("valutazione", String.valueOf(libro.getValutazione()));
                    libro.setValutazione(Integer.parseInt(nuovoValore));
                    break;
                case "stato":
                    backupValori.put("stato", libro.getStatoLettura().name());
                    libro.setStatoLettura(Libro.StatoLettura.valueOf(nuovoValore));
                    break;
                default:
                    throw new IllegalArgumentException("Campo non valido: " + campo);
            }
        }
    }

    @Override
    public void annulla() {
        for (Map.Entry<String, String> entry : backupValori.entrySet()) {
            String campo = entry.getKey();
            String valorePrecedente = entry.getValue();

            switch (campo.toLowerCase()) {
                case "titolo":
                    libro.setTitolo(valorePrecedente);
                    break;
                case "autore":
                    libro.setAutore(valorePrecedente);
                    break;
                case "isbn":
                    libro.setIsbn(valorePrecedente);
                    break;
                case "genere":
                    libro.setGenere(valorePrecedente);
                    break;
                case "valutazione":
                    libro.setValutazione(Integer.parseInt(valorePrecedente));
                    break;
                case "stato":
                    libro.setStatoLettura(Libro.StatoLettura.valueOf(valorePrecedente));
                    break;
            }
        }
    }
}