package modelo;

public class Persona implements Comparable<Persona>{
	private String nombre;
	private int edad;
	
	
	
	public Persona(String nombre, int edad) {
		this.nombre = nombre;
		this.edad = edad;
	}
	
	
	@Override
	public String toString() {
		return this.nombre+" - "+edad;	
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
	
	//probando 1,2,3...

}
