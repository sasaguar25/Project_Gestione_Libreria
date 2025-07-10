package gestione_libreria.grafica;

import gestione_libreria.mediator.LibroMediator;
import gestione_libreria.model.Libro;

import javax.swing.*;
import java.awt.*;
import java.util.List;

//sezione che riguarda ricerca e filtro/ordinamento
public class RicercaPanel extends JPanel {
    private JComboBox<String> campoRicercaBox = new JComboBox<>(new String[]{"Titolo", "Autore", "ISBN"});
    private JTextField campoRicercaField = new JTextField(15);
    private JButton cercaBtn = new JButton("Cerca");

    private JComboBox<String> genereBox = new JComboBox<>();
    private JComboBox<String> statoBox = new JComboBox<>();
    private JComboBox<String> valutazioneBox = new JComboBox<>();

    private JComboBox<String> campoOrdinaBox = new JComboBox<>(new String[]{"Titolo", "Autore", "Valutazione"});
    private JComboBox<String> direzioneOrdinaBox = new JComboBox<>(new String[]{"Crescente", "Decrescente"});
    private JButton applicaBtn = new JButton("Applica");
    private JButton resetBtn = new JButton("Reset");

    private LibroMediator mediator;

    public RicercaPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setAlignmentX(LEFT_ALIGNMENT);

        // Sezione Ricerca
        JPanel ricercaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ricercaPanel.add(new JLabel("Cerca per:"));
        ricercaPanel.add(campoRicercaBox);
        ricercaPanel.add(campoRicercaField);
        ricercaPanel.add(cercaBtn);
        add(ricercaPanel);
        add(Box.createVerticalStrut(10));
        add(new JSeparator());

        // Setup filtri
        statoBox.addItem("Tutti");
        for (Libro.StatoLettura stato : Libro.StatoLettura.values()) {
            statoBox.addItem(stato.name());
        }

        valutazioneBox.addItem("-");
        for (int i = 1; i <= 5; i++) {
            valutazioneBox.addItem(String.valueOf(i));
        }

        genereBox.addItem("Tutti");

        // Pannello principale
        JPanel filtroOrdinaPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Filtra per
        JPanel filtriPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filtriPanel.setBorder(BorderFactory.createTitledBorder("Filtra per"));
        filtriPanel.add(new JLabel("Genere:"));
        filtriPanel.add(genereBox);
        filtriPanel.add(new JLabel("Stato:"));
        filtriPanel.add(statoBox);
        filtriPanel.add(new JLabel("Valutazione:"));
        filtriPanel.add(valutazioneBox);

        // Ordina per
        JPanel ordinaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        ordinaPanel.setBorder(BorderFactory.createTitledBorder("Ordina per"));
        ordinaPanel.add(new JLabel("Campo:"));
        ordinaPanel.add(campoOrdinaBox);
        ordinaPanel.add(new JLabel("Direzione:"));
        ordinaPanel.add(direzioneOrdinaBox);

        // Bottoni
        JPanel bottoniPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        bottoniPanel.add(applicaBtn);
        bottoniPanel.add(resetBtn);

        gbc.gridx = 0;
        filtroOrdinaPanel.add(filtriPanel, gbc);
        gbc.gridx = 1;
        filtroOrdinaPanel.add(ordinaPanel, gbc);
        gbc.gridx = 2;
        filtroOrdinaPanel.add(bottoniPanel, gbc);

        add(filtroOrdinaPanel);

        // Eventi
        cercaBtn.addActionListener(e -> {
            if (mediator != null)
                mediator.cerca(campoRicercaBox.getSelectedItem().toString(), campoRicercaField.getText());
        });

        applicaBtn.addActionListener(e -> {
            if (mediator != null) {
                String genereSelezionato = (String) genereBox.getSelectedItem();
                String genere = "Tutti".equals(genereSelezionato) ? null : genereSelezionato;

                String statoSelezionato = (String) statoBox.getSelectedItem();
                Libro.StatoLettura stato = "Tutti".equals(statoSelezionato) ? null : Libro.StatoLettura.valueOf(statoSelezionato);

                String valutazioneSelezionata = (String) valutazioneBox.getSelectedItem();
                Integer valutazione = "-".equals(valutazioneSelezionata) ? null : Integer.parseInt(valutazioneSelezionata);

                String campoOrdina = (String) campoOrdinaBox.getSelectedItem();
                String direzioneOrdina = (String) direzioneOrdinaBox.getSelectedItem();

                mediator.filtraEOrdina(genere, stato, valutazione, campoOrdina, direzioneOrdina);
            }
        });

        resetBtn.addActionListener(e -> {
            if (mediator != null) {
                campoRicercaField.setText("");
                campoRicercaBox.setSelectedIndex(0);
                genereBox.setSelectedItem("Tutti");
                statoBox.setSelectedItem("Tutti");
                valutazioneBox.setSelectedItem("-");
                campoOrdinaBox.setSelectedIndex(0);
                direzioneOrdinaBox.setSelectedIndex(0);
                mediator.resetVista();
            }
        });
    }

    public void setMediator(LibroMediator mediator) {
        this.mediator = mediator;
    }

    public void aggiornaGeneri(List<String> generiDisponibili) {
        genereBox.removeAllItems();
        genereBox.addItem("Tutti");
        for (String g : generiDisponibili) {
            genereBox.addItem(g);
        }
    }
}




