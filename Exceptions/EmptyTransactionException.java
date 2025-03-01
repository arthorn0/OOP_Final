package Exceptions;

public class EmptyTransactionException extends RuntimeException {
  public EmptyTransactionException(String message) {
    super(message);
  }
}
