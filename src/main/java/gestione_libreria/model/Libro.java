package gestione_libreria.model;

public class Libro {

    public enum StatoLettura {
        DA_LEGGERE,
        IN_LETTURA,
        TERMINATO
    }

    private String titolo;
    private String autore;
    private String isbn;
    private String genere;
    private int valutazione;
    private StatoLettura statoLettura;


    private Libro(Builder builder) {
        this.titolo = builder.titolo;
        this.autore = builder.autore;
        this.isbn = builder.isbn;
        this.genere = builder.genere;
        this.valutazione = builder.valutazione;
        this.statoLettura = builder.statoLettura;
    }

    public static class Builder {
        private String titolo;
        private String autore;
        private String isbn;
        private String genere;
        private int valutazione;
        private StatoLettura statoLettura;

        public Builder titolo(String titolo) {
            this.titolo = titolo;
            return this;
        }

        public Builder autore(String autore) {
            this.autore = autore;
            return this;
        }

        public Builder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder genere(String genere) {
            this.genere = genere;
            return this;
        }

        public Builder valutazione(int valutazione) {
            this.valutazione = valutazione;
            return this;
        }

        public Builder statoLettura(StatoLettura statoLettura) {
            this.statoLettura = statoLettura;
            return this;
        }

        public Libro build() {
            return new Libro(this);
        }
    }

    public String getTitolo() { return titolo; }
    public String getAutore() { return autore; }
    public String getIsbn() { return isbn; }
    public String getGenere() { return genere; }
    public int getValutazione() { return valutazione; }
    public StatoLettura getStatoLettura() { return statoLettura; }

    public void setTitolo(String titolo) { this.titolo=titolo; }
    public void setAutore(String autore) { this.autore=autore; }
    public void setIsbn(String isbn) { this.isbn=isbn; }
    public void setGenere(String genere) { this.genere=genere; }
    public void setValutazione(int valutazione) {this.valutazione=valutazione; }
    public void setStatoLettura(StatoLettura statoLettura) { this.statoLettura=statoLettura; }
}