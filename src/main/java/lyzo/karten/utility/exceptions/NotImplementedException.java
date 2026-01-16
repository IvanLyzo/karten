package lyzo.karten.utility.exceptions;

public class NotImplementedException extends RuntimeException {

    public NotImplementedException() {
      System.err.println("Attempted to call method that has been marked as not implemented.");
    }
}
