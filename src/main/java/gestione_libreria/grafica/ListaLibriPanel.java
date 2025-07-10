package gestione_libreria.grafica;

import gestione_libreria.controller.GestoreLibri;
import gestione_libreria.mediator.LibroMediator;
import gestione_libreria.model.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//è la sezione dove vengono mostrati i libri
public class ListaLibriPanel extends JPanel {
    private JTable tabella;
    private DefaultTableModel modello; //dati dei libri
    private JButton rimuoviBtn = new JButton("Rimuovi");
    private JButton annullaBtn = new JButton("Annulla");

    private LibroMediator mediator;
    public List<Libro> listaCorrente = new ArrayList<>();

    public ListaLibriPanel() {
        setLayout(new BorderLayout());

        modello = new DefaultTableModel(new String[]{"Titolo", "Autore", "ISBN", "Genere", "Valutazione", "Stato"}, 0);
        tabella = new JTable(modello);
        tabella.setRowHeight(40);
        JScrollPane scrollPane = new JScrollPane(tabella);
        add(scrollPane, BorderLayout.CENTER);

        JPanel pulsantiPanel = new JPanel();
        pulsantiPanel.add(rimuoviBtn);
        pulsantiPanel.add(annullaBtn);
        add(pulsantiPanel, BorderLayout.SOUTH);

        rimuoviBtn.addActionListener(e -> {
            if (mediator != null) mediator.rimuoviLibro();
        });

        annullaBtn.addActionListener(e -> {
            if (mediator != null) mediator.annullaUltimaAzione();
        });

        aggiornaLista(listaCorrente);
        tabella.getSelectionModel().addListSelectionListener(e -> {
            int i = tabella.getSelectedRow();
            if (i >= 0 && mediator != null) {
                Libro libro = listaCorrente.get(i);
                mediator.selezionaLibro(libro);
            }
        });
    }

    public void setMediator(LibroMediator mediator) {
        this.mediator = mediator;
    }

    public void aggiornaLista(List<Libro> libriDaMostrare) {
        modello.setRowCount(0);
        listaCorrente = libriDaMostrare; // ✅ memorizza
        for (Libro libro : libriDaMostrare) {
            modello.addRow(new Object[]{
                    libro.getTitolo(),
                    libro.getAutore(),
                    libro.getIsbn(),
                    libro.getGenere(),
                    libro.getValutazione(),
                    libro.getStatoLettura()
            });
        }
    }

    public Libro getLibroSelezionato() {
        int i = tabella.getSelectedRow();
        if (i >= 0 && i < listaCorrente.size()) {
            return listaCorrente.get(i);
        }
        return null;
    }
}

