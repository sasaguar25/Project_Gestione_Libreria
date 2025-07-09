package gestione_libreria.model;

public class Libro {
    private String titolo;
    private String autore;
    private String isbn;
    private String genere;
    private int valutazione;
    private String statoLettura;

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
        private String statoLettura;

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

        public Builder statoLettura(String statoLettura) {
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
    public String getStatoLettura() { return statoLettura; }
}