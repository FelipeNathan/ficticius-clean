package br.com.ficticiusclean.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3018880788991908213L;

	private final String entity;

	public EntityNotFoundException(String entity) {

		this.entity = entity;
	}

	public String getEntity() {

		return entity;
	}
}
