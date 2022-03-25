package com.bonita.rental.ui;

import org.eclipse.jface.viewers.IColorProvider;

public class Palette {

	private String id, name;
	private IColorProvider provider;

	public Palette(String id, String name, IColorProvider provider) {
		super();
		this.id = id;
		this.name = name;
		this.provider = provider;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IColorProvider getProvider() {
		return provider;
	}

	public void setProvider(IColorProvider provider) {
		this.provider = provider;
	}

	@Override
	public String toString() {
		return "Palette [id=" + id + ", name=" + name + ", provider=" + provider + "]";
	}

}
