package com.kvitems.core;

import java.io.IOException;

public interface ItemStore extends ReadItemStore {
	Item post(IsItem commit) throws IOException;

	Items post(IsItems commit) throws IOException;

	void delete(Iterable<String> ids) throws IOException;

	void delete(String id) throws IOException;
	
}
