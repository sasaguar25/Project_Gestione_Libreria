package gestione_libreria.grafica;


import gestione_libreria.controller.GestoreLibri;
import gestione_libreria.mediator.LibroMediatorImpl;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class PaginaPrincipale extends JFrame {

    public PaginaPrincipale() {
        super("Libreria personale");

        // Caricamento libri
        GestoreLibri.getInstance().caricaDaFile();
        LibroMediatorImpl mediator = new LibroMediatorImpl();

        CampoLibroPanel campoPanel = new CampoLibroPanel();
        ListaLibriPanel listaPanel = new ListaLibriPanel();
        RicercaPanel filtroPanel = new RicercaPanel();
        filtroPanel.aggiornaGeneri(List.of(
                "Narrativa", "Saggio", "Fantasy", "Thriller", "Giallo",
                "Fantascienza", "Biografia", "Storico", "Horror", "Romanzo Rosa"
        ));

        listaPanel.aggiornaLista(GestoreLibri.getInstance().getLibri());

        // Mediator
        filtroPanel.setMediator(mediator);
        campoPanel.setMediator(mediator);
        listaPanel.setMediator(mediator);
        mediator.setCampoPanel(campoPanel);
        mediator.setListaPanel(listaPanel);
        mediator.resetVista();

        // Checkbox switch
        JCheckBox switchBox = new JCheckBox("Modalità Ricerca");

        // Pannello superiore con checkbox a destra e pannello ricerca/filtri a sinistra
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topBar.add(switchBox, BorderLayout.EAST);

        JPanel ricercaContainer = new JPanel(new BorderLayout());
        ricercaContainer.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        ricercaContainer.add(campoPanel, BorderLayout.CENTER); // default: campoPanel

        // Pannello centrale con lista (JScrollPane)
        listaPanel.setBorder(BorderFactory.createTitledBorder("Elenco dei libri"));
        JScrollPane listaScroll = new JScrollPane(listaPanel);
        listaScroll.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        // Layout frame principale
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.add(topBar);
        topContainer.add(ricercaContainer);

        setLayout(new BorderLayout());
        add(topContainer, BorderLayout.NORTH);
        add(listaScroll, BorderLayout.CENTER);

        // Switch modalità
        switchBox.addItemListener(e -> {
            ricercaContainer.removeAll();
            if (switchBox.isSelected()) {
                ricercaContainer.add(filtroPanel, BorderLayout.CENTER);
            } else {
                ricercaContainer.add(campoPanel, BorderLayout.CENTER);
            }
            ricercaContainer.revalidate();
            ricercaContainer.repaint();
        });

        // Dimensioni e chiusura finestra
        setSize(850, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PaginaPrincipale frame = new PaginaPrincipale();
            frame.setVisible(true);
        });
    }
}