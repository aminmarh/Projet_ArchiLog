package mediatheque;

public class ReserverException extends Exception{
    public ReserverException(String documentDejareserver) {
        super("Document deja reserver");
    }
}
