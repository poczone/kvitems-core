package com.kvitems.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Items implements ItemStore, IsItems, Serializable {
	private static final long serialVersionUID = -6157122210632559989L;

	private List<Item> items = new ArrayList<Item>();

	public Items() {
	}

	public Items(IsItem... items) {
		if (items != null) {
			add(Arrays.asList(items));
		}
	}

	public Items(Iterable<? extends IsItem> items) {
		add(items);
	}

	public Item getFirst() {
		return !items.isEmpty() ? items.get(0) : null;
	}

	@Override
	public Item get(String id) throws IOException {
		return get(Item.ID, id).getFirst();
	}

	@Override
	public Items get(Iterable<String> ids) throws IOException {
		return get(Item.ID, ids);
	}

	@Override
	public Items get(String key, String value) throws IOException {
		Items result = new Items();
		for (Item item : this) {
			if (item.has(key, value)) {
				result.add(item);
			}
		}
		return result;
	}

	@Override
	public Items get(String key, Iterable<String> values) throws IOException {
		Items result = new Items();
		for (Item item : this) {
			if (item.hasAny(key, values)) {
				result.add(item);
			}
		}
		return result;
	}

	@Override
	public Item post(IsItem commit) throws IOException {
		return post(new Items(commit)).getFirst();
	}

	@Override
	public Items post(IsItems commit) throws IOException {
		delete(getValueSet(Item.ID));
		add(commit);
		return new Items(commit);
	}

	@Override
	public void delete(Iterable<String> ids) throws IOException {
		Iterator<Item> iterator = items.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().hasAny(Item.ID, ids)) {
				iterator.remove();
			}
		}
	}

	@Override
	public void delete(String id) throws IOException {
		Iterator<Item> iterator = items.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().has(Item.ID, id)) {
				iterator.remove();
			}
		}
	}

	public Set<String> getValueSet(String key) {
		return collectValues(key, new LinkedHashSet<String>());
	}

	public SortedSet<String> getValueSortedSet(String key) {
		return collectValues(key, new TreeSet<String>());
	}

	public List<String> getValueList(String key) {
		return collectValues(key, new ArrayList<String>());
	}

	private <T extends Collection<String>> T collectValues(String key,
			T collection) {
		for (Item item : this) {
			for (Property property : item.getProperties()) {
				if (property.getKey().equals(key)) {
					collection.add(property.getValue());
				}
			}
		}
		return collection;
	}

	@Override
	public Iterator<Item> iterator() {
		return items.iterator();
	}

	private void add(Iterable<? extends IsItem> items) {
		for (IsItem item : items) {
			add(item);
		}
	}

	private void add(IsItem item) {
		items.add(item.asItem());
	}

	@Override
	public Items asItems() {
		return this;
	}
}
