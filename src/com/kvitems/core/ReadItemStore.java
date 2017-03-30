package com.kvitems.core;

import java.io.IOException;

public interface ReadItemStore {
	Item get(String id) throws IOException;

	Items get(Iterable<String> id) throws IOException;

	Items get(String key, String value) throws IOException;

	Items get(String key, Iterable<String> values) throws IOException;
}
