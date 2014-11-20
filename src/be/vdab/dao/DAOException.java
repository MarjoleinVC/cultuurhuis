package be.vdab.dao;

public class DAOException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DAOException() {
	}

	public DAOException(String ex) {
		super(ex);
	}

	public DAOException(String ex, Throwable cause) {
		super(ex, cause);
	}
}