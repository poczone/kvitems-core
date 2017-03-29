package com.kvitems.core;

import java.util.ArrayList;

public class Items extends ArrayList<Item> implements IsItems {
	private static final long serialVersionUID = -6157122210632559989L;

	@Override
	public Items asItems() {
		return this;
	}

}
