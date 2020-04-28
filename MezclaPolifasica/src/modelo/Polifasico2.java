package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import manejoFicheros.FicherosTexto;

public class Polifasico2 {

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
	
	public long[] secuenciasPorCinta(int cantidadDeTramos) {

		if (cantidadDeTramos == 1) {
			long[] p = { 1 };
			return p;
		} else {
			long[] toReturn = new long[0];
			long cantidadDeTramosNulos = cantidadDeTramos;

			for (int i = 2; i < 8; i++) {
				long[] auxiliar = new long[i];
				auxiliar[0] = 1;
				for (int w = 1; w < auxiliar.length; w++) {
					auxiliar[w] = 0;
				}
				long sum = 0;

				long[] siguienteNivel = Arrays.copyOf(auxiliar, auxiliar.length);

				// Escalo el arreglo hasta que la suma de sus componentes sea mayor o igual que
				// la cantidad de tramos
				while (sum <= cantidadDeTramos) {
					siguienteNivel[siguienteNivel.length - 1] = auxiliar[0];
					siguienteNivel[0] = auxiliar[0] + auxiliar[1];
					for (int r = 1; r < siguienteNivel.length - 1; r++) {
						siguienteNivel[r] = auxiliar[0] + auxiliar[r + 1];
					}
					// Luego calculo la suma de los valores dentro de siguienteNivel
					for (int k = 0; k < siguienteNivel.length; k++) {
						sum += siguienteNivel[k];
					}
					if (sum == cantidadDeTramos) {
						return siguienteNivel;
					}

					if (sum < cantidadDeTramos) {
						auxiliar = Arrays.copyOf(siguienteNivel, siguienteNivel.length);
						sum = 0;
					} else {
						break;
					}

				}

				sum = sum - cantidadDeTramos;

				if (sum < cantidadDeTramosNulos) {
					toReturn = siguienteNivel;
					cantidadDeTramosNulos = sum;
				}

			}

			return toReturn;

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

	public static void main(String[] args) {
		String url = "Enteros.txt";
		Polifasico2 p = new Polifasico2();
		FicherosTexto f= new FicherosTexto();
		
		try {
		f.fillIntsTxt(url, 3, 20);
		int cant=0;
		cant=p.cantidadDeTramos(url);
		System.out.println("Cantidad de tramos: "+cant);
		long secuenciasPorCinta[]=p.secuenciasPorCinta(cant);
		int sum=0, cantSecNulas=0;
		System.out.print("Reparticion de los tramos en los archivos: ");
		for (int i=0; i<secuenciasPorCinta.length; i++) {
			System.out.print(secuenciasPorCinta[i]+" ");
			sum+=secuenciasPorCinta[i];
		}
		System.out.println();
		cantSecNulas=sum-cant;
		System.out.println("La cantidad de secuencias nulas es: "+cantSecNulas);
		
		p.division(url, secuenciasPorCinta, cant);
		
		}
		
		catch (IOException e) {
			e.getMessage();
		}
		

	}

}
