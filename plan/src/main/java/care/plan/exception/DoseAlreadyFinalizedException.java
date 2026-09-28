package care.plan.exception;

public class DoseAlreadyFinalizedException extends RuntimeException {

    public DoseAlreadyFinalizedException(String message) {
        super(message);
    }
}