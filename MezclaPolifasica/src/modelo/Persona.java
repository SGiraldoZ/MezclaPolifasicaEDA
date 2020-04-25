package modelo;

import java.io.Serializable;

public class Persona implements Comparable<Persona>, Serializable{
	private String nombre;
	private int edad;
	private String cedula;
	
	
	
	public Persona(String nombre, int edad, String cedula) {
		this.nombre = nombre;
		this.edad = edad;
		this.cedula = cedula;
	}
	
	
	@Override
	public String toString() {
		return this.cedula + ";"+this.nombre+";"+edad;	
	}
	
	@Override
	public int compareTo(Persona p){
		int compNombres = this.nombre.compareTo(p.getNombre());
		/*if (compNombres != 0)*/ return compNombres;
		//return this.edad-p.getEdad();
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	

}
