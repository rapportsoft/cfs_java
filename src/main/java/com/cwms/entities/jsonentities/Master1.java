package com.cwms.entities.jsonentities;

import java.util.List;

public class Master1 {
	private List<CargoDetails1> cargoDetails;
	private Declaration declaration;
	private Location location;
	public Master1(List<CargoDetails1> cargoDetails, Declaration declaration, Location location) {
		super();
		this.cargoDetails = cargoDetails;
		this.declaration = declaration;
		this.location = location;
	}
	public Master1() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<CargoDetails1> getCargoDetails() {
		return cargoDetails;
	}
	public void setCargoDetails(List<CargoDetails1> cargoDetails) {
		this.cargoDetails = cargoDetails;
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
