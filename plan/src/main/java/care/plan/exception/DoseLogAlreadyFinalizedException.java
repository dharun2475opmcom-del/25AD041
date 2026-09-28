package care.plan.exception;

public class DoseLogAlreadyFinalizedException extends RuntimeException {

    public DoseLogAlreadyFinalizedException(String message) {
        super(message);
    }
}