package gestione_libreria.grafica;


import gestione_libreria.factory.LibroFactory;
import gestione_libreria.mediator.LibroMediator;
import gestione_libreria.model.Libro;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

//è la sezione che riguarda l'aggiunta/modifica di un libro

public class CampoLibroPanel extends JPanel {
    public JTextField titoloField = new JTextField(20);
    public JTextField autoreField = new JTextField(20);
    public JTextField isbnField = new JTextField(20);
    public JComboBox<String> genereBox = new JComboBox<>(new String[]{
            "Narrativa", "Saggio", "Fantasy", "Thriller", "Giallo", "Fantascienza",
            "Biografia", "Avventura", "Horror","Storico", "Romanzo Rosa"
    });
    public JComboBox<Integer> valutazioneBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
    public JComboBox<Libro.StatoLettura> statoBox = new JComboBox<>(Libro.StatoLettura.values());

    private JButton aggiungiBtn = new JButton("Aggiungi");
    private JButton modificaBtn = new JButton("Modifica");

    private LibroMediator mediator;
    private Libro libroCorrente = null;



    public CampoLibroPanel() {
        setLayout(new BorderLayout());
        JPanel griglia = new JPanel(new GridLayout(7, 2, 5, 5));

        griglia.add(new JLabel("Titolo:"));
        griglia.add(titoloField);

        griglia.add(new JLabel("Autore:"));
        griglia.add(autoreField);

        griglia.add(new JLabel("ISBN:"));
        griglia.add(isbnField);

        griglia.add(new JLabel("Genere:"));
        griglia.add(genereBox);

        griglia.add(new JLabel("Valutazione:"));
        griglia.add(valutazioneBox);

        griglia.add(new JLabel("Stato Lettura:"));
        griglia.add(statoBox);

        griglia.add(aggiungiBtn);
        griglia.add(modificaBtn);
        modificaBtn.setEnabled(false);

        //pannello che centra orizzontalmente
        JPanel contenitoreCentrato = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contenitoreCentrato.add(griglia);

        add(contenitoreCentrato, BorderLayout.CENTER);

        aggiungiBtn.addActionListener(e -> {
            if (mediator != null) {
                mediator.aggiungiLibro();
                pulisciCampi();
            }
        });

        modificaBtn.addActionListener(e -> {
            mediator.modificaLibro();
            pulisciCampi();
            libroCorrente = null;
            modificaBtn.setEnabled(false);
        });

        abilitaRilevamentoModifiche();
    }

    public void setMediator(LibroMediator mediator) {
        this.mediator = mediator;
    }

    //builder del libro, distingue i casi in cui usare i diversi factory method
    public Libro creaLibroDaCampi() {
        Libro libro= new Libro.Builder()
                .titolo(titoloField.getText())
                .autore(autoreField.getText())
                .isbn(isbnField.getText())
                .genere((String) genereBox.getSelectedItem())
                .valutazione((Integer) valutazioneBox.getSelectedItem())
                .statoLettura((Libro.StatoLettura) statoBox.getSelectedItem())
                .build();

        if (titoloField.getText().isEmpty() || autoreField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Titolo e Autore sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
            System.out.print("null");
            return null;
        }

        // Usa Factory Method appropriato
        if (isbnField.getText().isEmpty() &&  (Integer)valutazioneBox.getSelectedItem() == 0) {
            return LibroFactory.creaLibroBase(titoloField.getText(), autoreField.getText());
        } else if (!isbnField.getText().isEmpty() && (Integer)valutazioneBox.getSelectedItem() == 0) {
            return LibroFactory.creaLibroConIsbn(titoloField.getText(), autoreField.getText(), isbnField.getText());
        } else {
            return LibroFactory.creaLibroCompleto(titoloField.getText(), autoreField.getText(),
                    isbnField.getText().isEmpty() ? "N/A" : isbnField.getText(),
                    (String) genereBox.getSelectedItem(),
                    (Integer)valutazioneBox.getSelectedItem(),
                    (Libro.StatoLettura) statoBox.getSelectedItem());
        }

    }

    public void caricaLibroNeiCampi(Libro libro) {
        titoloField.setText(libro.getTitolo());
        autoreField.setText(libro.getAutore());
        isbnField.setText(libro.getIsbn());
        genereBox.setSelectedItem(genereBox.getSelectedItem());
        valutazioneBox.setSelectedItem(libro.getValutazione());
        statoBox.setSelectedItem(libro.getStatoLettura());
        this.libroCorrente = libro;
        verifica();
    }

    public void pulisciCampi() {
        titoloField.setText("");
        autoreField.setText("");
        isbnField.setText("");
        genereBox.setSelectedItem("");
        valutazioneBox.setSelectedIndex(0);
        statoBox.setSelectedIndex(0);
    }

    //i due metodi sotto servono a rilevare eventuali modifiche sui campi, abilitando così il tasto modifica
    private void abilitaRilevamentoModifiche() {
        DocumentListener docListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { verifica(); }
            public void removeUpdate(DocumentEvent e) { verifica(); }
            public void changedUpdate(DocumentEvent e) { verifica(); }
        };

        titoloField.getDocument().addDocumentListener(docListener);
        autoreField.getDocument().addDocumentListener(docListener);
        isbnField.getDocument().addDocumentListener(docListener);

        genereBox.addActionListener(e -> verifica());
        valutazioneBox.addActionListener(e -> verifica());
        statoBox.addActionListener(e -> verifica());
    }

    private void verifica() {
        if (libroCorrente == null) {
            modificaBtn.setEnabled(false);
            return;
        }

        boolean cambiato =
                !titoloField.getText().equals(libroCorrente.getTitolo()) ||
                        !autoreField.getText().equals(libroCorrente.getAutore()) ||
                        !isbnField.getText().equals(libroCorrente.getIsbn()) ||
                        !genereBox.getSelectedItem().equals(libroCorrente.getGenere()) ||
                        (int) valutazioneBox.getSelectedItem() != libroCorrente.getValutazione() ||
                        statoBox.getSelectedItem() != libroCorrente.getStatoLettura();

        modificaBtn.setEnabled(cambiato);
    }
}

