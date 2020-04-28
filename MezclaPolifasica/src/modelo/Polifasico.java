package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import manejoFicheros.FicherosTexto;

public class Polifasico {
	
	public void division(String url, long[] subCadenas) {
		
	}

	
	public int cantidadDeTramos(String url) throws IOException {
		String line;
		int lastRead=0, read;
		FileReader frMain = new FileReader(url);
		BufferedReader brMain = new BufferedReader(frMain);
		int cont = 0;
		while((line=brMain.readLine())!=null){
			if(!line.isBlank()) {	
				if((read=Integer.parseInt(line))<lastRead || cont == 0) {
					cont++;
				}
				lastRead = read;
			}
		}

		frMain.close();
		brMain.close();

		return cont;
	}

	public static long[] FibonacciMejorado(int cantidadDeTramos, int k) {
		long[] f = new long[k];
		// Lleno los primeros k valores del arreglo "f" con ceros, pero la ultima
		// casilla en 1
		for (int i = 0; i < k - 1; i++) {
			f[i] = 0;
			f[k - 1] = 1;
		}

		if (cantidadDeTramos == 1) {
			return f;
		}

		else {
			// Aplico fibonacci con arreglo dinamico, hasta que el ultimo valor sea mayor o
			// igual que la cantidad de tramos
			int j = k;
			while (f[j - 1] < cantidadDeTramos) {
				int sum = 0;
				for (int l = 1; l <= k; ++l) {
					sum += f[j - l];
				}
				f = Arrays.copyOf(f, f.length + 1);
				f[j] = sum;
				j++;
			}

			// Después de que en f esté la serie de fibonacci para el k dado, hasta que el
			// ultimo numero
			// sea mayor que la cantidad de tramos dada, guardo en "arregloFinal" solo los
			// valores de la sucesion
			// que en realidad necesito, y lo retorno
			long[] arregloFinal = new long[k + 1];
			int p = arregloFinal.length - 1;
			int i = f.length - 1;
			while (p >= 0) {
				arregloFinal[p] = f[i];
				i--;
				p--;
			}
			return arregloFinal;

		}
	}

	public long[] fibonacciOptimo(String url) throws IOException {
		long[] aux2, aux3, aux4, aux5, aux6, aux7, auxReturn;
		long k2, k3, k4, k5, k6, k7;
		int cantidadDeTramos = this.cantidadDeTramos(url);

		aux2 = FibonacciMejorado(cantidadDeTramos, 2);
		k2 = aux2[aux2.length - 1] - cantidadDeTramos;
		auxReturn = aux2;

		aux3 = FibonacciMejorado(cantidadDeTramos, 3);
		k3 = aux3[aux3.length - 1] - cantidadDeTramos;
		if (k3 < k2) {
			auxReturn = aux3;
		}
		aux4 = FibonacciMejorado(cantidadDeTramos, 4);
		k4 = aux4[aux4.length - 1] - cantidadDeTramos;
		if (k4 < (auxReturn[auxReturn.length - 1] - cantidadDeTramos)) {
			auxReturn = aux4;
		}

		aux5 = FibonacciMejorado(cantidadDeTramos, 5);
		k5 = aux5[aux5.length - 1] - cantidadDeTramos;
		if (k5 < (auxReturn[auxReturn.length - 1] - cantidadDeTramos)) {
			auxReturn = aux5;
		}

		aux6 = FibonacciMejorado(cantidadDeTramos, 6);
		k6 = aux6[aux6.length - 1] - cantidadDeTramos;
		if (k6 < (auxReturn[auxReturn.length - 1] - cantidadDeTramos)) {
			auxReturn = aux6;
		}

		aux7 = FibonacciMejorado(cantidadDeTramos, 7);
		k7 = aux7[aux7.length - 1] - cantidadDeTramos;
		if (k7 < (auxReturn[auxReturn.length - 1] - cantidadDeTramos)) {
			auxReturn = aux7;
		}

		return auxReturn;

	}

	public static void main(String[] args) {
		String url = "Enteros.txt";
		Polifasico obj = new Polifasico();
	//	FicherosTexto.main(null);
		try {
			System.out.println("Numero Tramos: "+obj.cantidadDeTramos(url)+"\n");
			long[] k = obj.fibonacciOptimo(url);
			System.out.println("K: " + (k.length-1));
			for (int i = 0; i < k.length; i++)
				System.out.println(k[i]);
		} catch (IOException e) {
			e.getMessage();
		}
	}

}