package gestione_libreria.memento;

import java.util.Stack;

public class Caretaker {
    private final Stack<LibreriaMemento> cronologia = new Stack<>();

    public void salvaStato(LibreriaMemento memento) {
        cronologia.push(memento);
    }

    public LibreriaMemento ripristinaUltimo() {
        return cronologia.isEmpty() ? null : cronologia.pop();
    }
}
