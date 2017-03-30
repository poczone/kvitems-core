package com.kvitems.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Item implements IsItem, Serializable {
	private static final long serialVersionUID = -1093123125512914104L;

	private List<Property> properties = new ArrayList<Property>();

	public Item() {
	}

	public Item(String encodedItem) {
		String[] lines = encodedItem.split("\r?\n");
		for (String line : lines) {
			if (line.startsWith(" ")) {
				appendLine(line.substring(1));
			} else if (line.startsWith(": ")) {
				appendLine(line.substring(2));
			} else {
				String[] keyValue = line.split(": ", 2);
				if (keyValue.length == 2) {
					add(keyValue[0], keyValue[1]);
				}
			}
		}
	}

	private void appendLine(String moreValue) {
		if (!properties.isEmpty()) {
			int lastIndex = properties.size() - 1;

			Property oldProperty = properties.get(lastIndex);
			Property newProperty = new Property(oldProperty.getKey(),
					oldProperty.getValue() + "\n" + moreValue);
			properties.set(lastIndex, newProperty);
		}
	}

	public Item add(String key, String value) {
		properties.add(new Property(key, value));
		return this;
	}

	public Item add(String key, Iterable<String> values) {
		for (String value : values) {
			add(key, value);
		}
		return this;
	}

	public Item clear(String key) {
		Iterator<Property> iterator = properties.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getKey().equals(key)) {
				iterator.remove();
			}
		}
		return this;
	}

	public Item set(String key, String value) {
		clear(key);
		add(key, value);
		return this;
	}

	public Item set(String key, Iterable<String> values) {
		clear(key);
		add(key, values);
		return this;
	}

	public Item set(String key, Number value) {
		return set(key, value != null ? value.toString() : "");
	}

	public Item set(String key, Boolean value) {
		return set(key, value != null ? value.toString() : "");
	}

	public String get(String key, String defaultValue) {
		for (Property property : properties) {
			if (property.getKey().equals(key)) {
				return property.getValue();
			}
		}
		return defaultValue;
	}

	public String get(String key) {
		return get(key, "");
	}

	public Integer getInteger(String key, Integer defaultValue) {
		try {
			return Integer.parseInt(get(key));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public Long getLong(String key, Long defaultValue) {
		try {
			return Long.parseLong(get(key));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public Boolean getBoolean(String key, Boolean defaultValue) {
		String value = get(key);
		if ("true".equals(value)) {
			return true;
		} else if ("false".equals(value)) {
			return false;
		} else {
			return defaultValue;
		}
	}

	public List<String> getAll(String key) {
		List<String> result = new ArrayList<String>();
		for (Property property : properties) {
			if (property.getKey().equals(key)) {
				result.add(property.getValue());
			}
		}
		return result;
	}

	public boolean has(String key) {
		for (Property property : properties) {
			if (property.getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}

	public boolean has(String key, String value) {
		return properties.contains(new Property(key, value));
	}

	public boolean hasAny(String key, Iterable<String> values) {
		for (String value : values) {
			if (has(key, value)) {
				return true;
			}
		}
		return false;
	}

	public Set<String> getKeys() {
		Set<String> keys = new LinkedHashSet<String>();
		for (Property property : properties) {
			keys.add(property.getKey());
		}
		return keys;
	}

	public List<Property> getProperties() {
		return properties;
	}

	@Override
	public Item asItem() {
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Property property : properties) {
			if (sb.length() > 0) {
				sb.append("\n");
			}
			sb.append(property.toString());
		}
		return sb.toString();
	}
}
