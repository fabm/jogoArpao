package projectojogoarpao.excepcoes;

/**
 * Exceção em tempo de execução
 * @author francisco
 */
abstract public class ExcecaoTE extends RuntimeException {

    protected ExcecaoTE() {
        super();
    }

    protected ExcecaoTE(String message) {
        super(message);
    }

    protected ExcecaoTE(Throwable cause) {
        super(cause);
    }

    protected ExcecaoTE(String message, Throwable cause) {
        super(message, cause);
    }
}
