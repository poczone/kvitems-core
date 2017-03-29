package com.kvitems.core;

import java.io.Serializable;

public class Property implements Serializable {
	private static final long serialVersionUID = 2011567658998591536L;

	private String key;
	private String value;

	public Property() {
		this("", "");
	}

	public Property(String key, String value) {
		this.key = key != null ? key : "";
		this.value = value != null ? value : "";
		checkKey();
	}

	private void checkKey() {
		if (key.contains(": ")) {
			throw new IllegalArgumentException("Key must not contain \": \"");
		}
		if (key.startsWith(" ")) {
			throw new IllegalArgumentException(
					"Key must not start with a space");
		}
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return key + ": " + value.replace("\n", "\n ");
	}

	@Override
	public int hashCode() {
		return key.hashCode() * 31 + value.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		} else if (!(other instanceof Property)) {
			return false;
		}

		Property otherProperty = (Property) other;
		return key.equals(otherProperty.key)
				&& value.equals(otherProperty.value);
	}
}
