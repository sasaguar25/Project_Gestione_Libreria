package gestione_libreria.grafica;

import gestione_libreria.model.Libro;

import java.util.List;

public interface Observer {
    void aggiorna(List<Libro> listaAggiornata);
}
