package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import manejoFicheros.FicherosTexto;

public class Polifasico {
	private static String auxiliares = "aux%s.txt";

	public void division(String url, long[] secuenciasCintas, int cantTramos) throws IOException {
		FileReader fr = new FileReader(url);
		BufferedReader br = new BufferedReader(fr);
		int m = secuenciasCintas.length +1; //Números de archivos con los que se va a trabajar
		long totSecuencias = 0;
		for(long l: secuenciasCintas) {
			totSecuencias+=l;
		}
		long secuenciasNulas = totSecuencias - cantTramos;
		
		File[] f = new File[m];
		FileWriter[] fw = new FileWriter[m];
		BufferedWriter[] bw = new BufferedWriter[m];
		for (int i = 0; i < m; i++) {
			f[i] = new File(String.format(auxiliares, (i)));
			f[i].createNewFile();
			fw[i] = new FileWriter(f[i]);
			bw[i] = new BufferedWriter(fw[i]);
		}

		// Variables necesarias para la mezcla:
		long[] secuCinta = new long[secuenciasCintas.length];
		Arrays.fill(secuCinta, 0);
		String line = br.readLine();
		int last;
		int i = 0;
		bw[i].write(line + "\n");
		last = Integer.parseInt(line);
		while ((line = br.readLine()) != null) {
			if (!line.isBlank()) {
				if (Integer.parseInt(line) < last) {
					secuCinta[i]++;
					if (secuenciasNulas > 0 && secuCinta[i]<secuenciasCintas[i]) {
						bw[i].write("@\n");
						secuCinta[i]++;
						secuenciasNulas--;
					}
					do {
						i = (i + 1) % (m-1);
					} while (secuCinta[i] == secuenciasCintas[i]);
					bw[i].write(line + "\n");
				} else {
					bw[i].write(line + "\n");
				}
				last = Integer.parseInt(line);
			}
		}
		while (secuenciasNulas > 0) {
			do {
				i = (i + 1) % (m-1);
			} while (secuCinta[i] == secuenciasCintas[i]);
			bw[i].write("@\n");
			secuenciasNulas--;
			secuCinta[i]++;
		}

		for (BufferedWriter b : bw) {
			b.close();
		}
		for (FileWriter b : fw) {
			b.close();
		}
	}

	public int cantidadDeTramos(String url) throws IOException {
		String line;
		int lastRead = 0, read;
		FileReader frMain = new FileReader(url);
		BufferedReader brMain = new BufferedReader(frMain);
		int cont = 0;
		while ((line = brMain.readLine()) != null) {
			if (!line.isBlank()) {
				if ((read = Integer.parseInt(line)) < lastRead || cont == 0) {
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

	public static int cantidadDeTramosYNulos(String url) throws IOException {
		String line;
		int lastRead = 0, read;
		FileReader frMain = new FileReader(url);
		BufferedReader brMain = new BufferedReader(frMain);
		int cont = 0;
		while ((line = brMain.readLine()) != null) {
			if (!line.isBlank()) {
				if (line.trim().compareTo("@")==0) {
					cont++;
				}else if ((read = Integer.parseInt(line)) < lastRead || cont == 0) {
					cont++;
					lastRead = read;
				}else lastRead = read;
				
			}
		}

		frMain.close();
		brMain.close();

		return cont;
	}
	
	public long[] fibonacciOptimo(int cantidadDeTramos) throws IOException {
		long[] aux2, aux3, aux4, aux5, aux6, aux7, auxReturn;
		long k2, k3, k4, k5, k6, k7;

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
//		FicherosTexto.main(null);
		try {
			System.out.println("Numero Tramos: " + obj.cantidadDeTramos(url) + "\n");

			obj.division(url, new long[]{2,3,1}, obj.cantidadDeTramos(url));
			
			System.out.println("cant tramos 0: "+obj.cantidadDeTramosYNulos("aux0.txt")+
					"\nCant tramos 1: "+obj.cantidadDeTramosYNulos("aux1.txt")+
					"\nCant tramos 2: "+obj.cantidadDeTramosYNulos("aux2.txt"));
		} catch (IOException e) {
			e.getMessage();
		}

	}

}