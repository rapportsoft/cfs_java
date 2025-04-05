package com.cwms.entities.jsonentities;

import java.util.List;

import lombok.Data;

@Data
public class Master {

	private List<CargoContainer> cargoContainer;
	private Declaration declaration;
	private Location location;

	public Master() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Master(List<CargoContainer> cargoContainer, Declaration declaration, Location location) {
		super();
		this.cargoContainer = cargoContainer;
		this.declaration = declaration;
		this.location = location;
	}

	public List<CargoContainer> getCargoContainer() {
		return cargoContainer;
	}

	public void setCargoContainer(List<CargoContainer> cargoContainer) {
		this.cargoContainer = cargoContainer;
	}

	public Declaration getDeclaration() {
		return declaration;
	}

	public void setDeclaration(Declaration declaration) {
		this.declaration = declaration;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	
	
}
