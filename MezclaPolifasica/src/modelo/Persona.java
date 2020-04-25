package modelo;

import java.io.Serializable;

public class Persona implements Comparable<Persona>, Serializable{
	private String nombre;
	private int edad;
	private String cedula;
	
	
	public String getCedula() {
		return this.cedula;
	}
	
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
		int comp = this.cedula.compareTo(p.getCedula());
		if (comp != 0) return comp;
		comp = this.nombre.compareTo(p.getNombre());
		if (comp!=0) return comp;
		return this.edad-p.getEdad();
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
	
	public static void main(String[] args) {
	}

}
