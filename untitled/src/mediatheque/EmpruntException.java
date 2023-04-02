package mediatheque;

public class EmpruntException extends Exception{
    public EmpruntException(String documentDejaEmprunte) {
        super("Document deja emprunte");
    }

}
