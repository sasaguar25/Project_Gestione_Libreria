package gestione_libreria.archivio;

import com.google.gson.Gson;
import gestione_libreria.model.Libro;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArchivioLibri {
    private static final String PERCORSO_FILE = "src/main/java/gestione_libreria/archivio/libreria.json";
    private static final Gson gson = new Gson();

    public static void salvaLibri(List<Libro> libri) {
        try (Writer writer = new FileWriter(PERCORSO_FILE)) {
            gson.toJson(libri, writer);
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio dei libri: " + e.getMessage());
        }
    }

    public static List<Libro> caricaLibri() {
        File file = new File(PERCORSO_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(PERCORSO_FILE)) {
            Libro[] arrayLibri = gson.fromJson(reader, Libro[].class);
            return new ArrayList<>(Arrays.asList(arrayLibri));
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento dei libri: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
