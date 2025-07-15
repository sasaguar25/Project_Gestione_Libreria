package gestione_libreria.mediator;

import gestione_libreria.archivio.ArchivioLibri;
import gestione_libreria.controller_command.AggiungiLibroCommand;
import gestione_libreria.controller_command.ModificaLibroCommand;
import gestione_libreria.controller_command.RimuoviLibroCommand;
import gestione_libreria.controller_command.RipristinoCommand;
import gestione_libreria.controller_command.Command;
import gestione_libreria.controller_command.GestoreLibri;
import gestione_libreria.grafica.CampoLibroPanel;
import gestione_libreria.grafica.ListaLibriPanel;
import gestione_libreria.memento.Caretaker;
import gestione_libreria.model.Libro;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

//Centro operativo
public class LibroMediatorImpl implements LibroMediator {
    private GestoreLibri gestore = GestoreLibri.getInstance();
    private Caretaker storico = new Caretaker();

    private CampoLibroPanel campoPanel;
    private ListaLibriPanel listaPanel;

    private List<Libro> libriFiltrati = null;

    public void setCampoPanel(CampoLibroPanel campoPanel) {
        this.campoPanel = campoPanel;
    }

    public void setListaPanel(ListaLibriPanel listaPanel) {
        this.listaPanel = listaPanel;
    }

    @Override
    public boolean aggiungiLibro() {
        Libro nuovo = campoPanel.creaLibroDaCampi();
        if (nuovo == null) {
            JOptionPane.showMessageDialog(null, "Titolo e autore sono obbligatori!",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        for (Libro l: gestore.getLibri() ) {
            if (l.getIsbn().equals(nuovo.getIsbn())) {
                JOptionPane.showMessageDialog(null,
                        "Errore! Codice ISBN già presente in lista.", "Errore", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        Command cmd = new AggiungiLibroCommand(gestore, nuovo);
        storico.salvaStato(gestore.creaMemento());
        cmd.esegui();
        ArchivioLibri.salvaLibri(gestore.getLibri());
        return true;
    }

    @Override
    public void rimuoviLibro() {
        Libro selezionato = listaPanel.getLibroSelezionato();
        if (selezionato != null) {
            Command cmd = new RimuoviLibroCommand(gestore, selezionato);
            storico.salvaStato(gestore.creaMemento());
            cmd.esegui();
            campoPanel.pulisciCampi();
            ArchivioLibri.salvaLibri(gestore.getLibri());
        }
    }

    @Override
    public boolean modificaLibro() {
        Libro selezionato = listaPanel.getLibroSelezionato();
        if (selezionato != null) {
            Libro modificato = campoPanel.creaLibroDaCampi();

            int cnt=0;
            for (Libro l: gestore.getLibri() ) {
                if (l != selezionato && l.getIsbn().equals(modificato.getIsbn())) {
                    JOptionPane.showMessageDialog(null, "Errore! Codice ISBN già " +
                            "presente in lista.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }

            Map<String, String> campiModificati = new HashMap<>();

            if (!selezionato.getTitolo().equals(modificato.getTitolo())) {
                campiModificati.put("titolo", modificato.getTitolo());
            }
            if (!selezionato.getAutore().equals(modificato.getAutore())) {
                campiModificati.put("autore", modificato.getAutore());
            }
            if (!selezionato.getIsbn().equals(modificato.getIsbn())) {
                campiModificati.put("isbn", modificato.getIsbn());
            }
            if (!selezionato.getGenere().equals(modificato.getGenere())) {
                campiModificati.put("genere", modificato.getGenere());
            }
            if (selezionato.getValutazione() != modificato.getValutazione()) {
                campiModificati.put("valutazione", String.valueOf(modificato.getValutazione()));
            }
            if (!selezionato.getStatoLettura().equals(modificato.getStatoLettura())) {
                campiModificati.put("stato", modificato.getStatoLettura().name());
            }

            if (!campiModificati.isEmpty()) {
                Command cmd = new ModificaLibroCommand(selezionato, campiModificati,gestore);
                storico.salvaStato(gestore.creaMemento());
                cmd.esegui();
                ArchivioLibri.salvaLibri(gestore.getLibri());
            }
        }
        return true;
    }

    @Override
    public void annullaUltimaAzione() {
        Command cmd = new RipristinoCommand(gestore,storico);
        cmd.esegui();
        ArchivioLibri.salvaLibri(gestore.getLibri());
    }

    @Override
    public void selezionaLibro(Libro libro) {
        campoPanel.caricaLibroNeiCampi(libro);
    }

    @Override
    public void cerca(String campo, String testo) {
        List<Libro> tuttiLibri = GestoreLibri.getInstance().getLibri();

        if (testo == null || testo.trim().isEmpty()) {
            libriFiltrati = new ArrayList<>(tuttiLibri);
        } else {
            String testoLower = testo.toLowerCase();
            libriFiltrati = tuttiLibri.stream()
                    .filter(libro -> {
                        switch (campo) {
                            case "Titolo":
                                return libro.getTitolo().toLowerCase().contains(testoLower);
                            case "Autore":
                                return libro.getAutore().toLowerCase().contains(testoLower);
                            case "ISBN":
                                return libro.getIsbn().toLowerCase().contains(testoLower);
                            default:
                                return false;
                        }
                    })
                    .collect(Collectors.toList());
        }
        gestore.setLibriFiltrati(libriFiltrati);
    }



    @Override
    public void filtraEOrdina(String genere, Libro.StatoLettura stato, Integer valutazione,
                              String campoOrdina, String direzione) {
        List<Libro> base = GestoreLibri.getInstance().getLibri(); // sempre dalla base

            List<Libro> filtrati = base.stream()
                    .filter(libro -> (genere == null || libro.getGenere().equals(genere)))
                    .filter(libro -> (stato == null || libro.getStatoLettura() == stato))
                    .filter(libro -> (valutazione == null || libro.getValutazione() == valutazione))
                    .collect(Collectors.toList());

            Comparator<Libro> comparator;
            switch (campoOrdina) {
                case "Titolo":
                    comparator = Comparator.comparing(Libro::getTitolo, String.CASE_INSENSITIVE_ORDER);
                    break;
                case "Autore":
                    comparator = Comparator.comparing(Libro::getAutore, String.CASE_INSENSITIVE_ORDER);
                    break;
                case "Valutazione":
                    comparator = Comparator.comparingInt(Libro::getValutazione);
                    break;
                default:
                    comparator = Comparator.comparing(Libro::getTitolo, String.CASE_INSENSITIVE_ORDER);
            }

            if ("Decrescente".equals(direzione)) {
                comparator = comparator.reversed();
            }

            filtrati.sort(comparator);

            libriFiltrati = filtrati;
            gestore.setLibriFiltrati(libriFiltrati);

    }

    @Override
    public void resetVista() {
        libriFiltrati = null;
        gestore.setLibriFiltrati(libriFiltrati);
    }
}

