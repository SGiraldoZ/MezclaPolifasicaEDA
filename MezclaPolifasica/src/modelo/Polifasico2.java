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

public class Polifasico2 {

	private static String auxiliares = "aux%s.txt";
	private static String tempFile = "auxBorrar.txt";

	public static void division(String url, long[] secuenciasCintas, int cantTramos) throws IOException {
		FileReader fr = new FileReader(url);
		BufferedReader br = new BufferedReader(fr);
		int m = secuenciasCintas.length; // Números de archivos con los que se va a trabajar
		long totSecuencias = 0;
		for (long l : secuenciasCintas) {
			totSecuencias += l;
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
					if (secuenciasNulas > 0 && secuCinta[i] < secuenciasCintas[i]) {
						bw[i].write("@\n");
						secuCinta[i]++;
						secuenciasNulas--;
					}
					do {
						i = (i + 1) % (m - 1);
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
				i = (i + 1) % (m - 1);
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

	public static String mezclaHastaVacio(long[] secuenciasCintas) throws IOException, FileNotFoundException {
		
		int totSecuencias = 0;
		for (long l : secuenciasCintas) {
			totSecuencias += l;
		}
		
		if (totSecuencias == 1) {
			int k = 0;
			while (secuenciasCintas[k] == 0)
				k++;
			return String.format(auxiliares, k);
		}

		FileWriter fw = null;
		BufferedWriter bw = null;
		File[] f = new File[secuenciasCintas.length - 1];
		FileReader[] fr = new FileReader[f.length];
		BufferedReader[] br = new BufferedReader[fr.length];
		int numBr = 0;
		for (int i = 0; i <= br.length; i++) {
			if (secuenciasCintas[i] == 0) {
				fw = new FileWriter(String.format(auxiliares, i));
				bw = new BufferedWriter(fw);
			}else if (secuenciasCintas[i] > 0) {
				f[numBr] = new File(String.format(auxiliares, i));
				fr[numBr] = new FileReader(f[numBr]);
				br[numBr] = new BufferedReader(fr[numBr]);
				numBr++;
			}
		}
		boolean[] wereChecking = new boolean[br.length];
		boolean mezclandoTramos = true;
		String[] firstOfSecuence = new String[br.length];
		boolean finMezcla = false;
		int numTramosMezclados = 0; // Actualmente estamos mezclando un conjunto de tramos (i.e un conjunto de
									// secuencias sería primero de cada cinta)
		int minActual = 0;// siguiente elemento en el fichero destino
		boolean soloVacios;
		int temp;
		for (int i = 0; i < br.length; i++) {
			firstOfSecuence[i] = br[i].readLine();
		}
		while (!finMezcla) {
			Arrays.fill(wereChecking, true);
			while (mezclandoTramos) {
				// Iniciar dos variables necesarias para la comparacion
				soloVacios = true;
				int q = 0;
				while (!wereChecking[q])
					q++;
				minActual = q;

				// Este ciclo busca el menor
				for (int j = 0; j < br.length; j++) {
					if (wereChecking[j]) {
						soloVacios = soloVacios && firstOfSecuence[j].trim().equals("@");
						if (firstOfSecuence[j].trim().compareTo("@") == 0) {
							firstOfSecuence[j] = br[j].readLine();
							wereChecking[j] = false;
						} else if (Integer.parseInt(firstOfSecuence[j]) < Integer.parseInt(firstOfSecuence[minActual])) {
							minActual = j;
						}
					}
				}
				
				if (soloVacios)
					bw.write("@\n");
				else {
					temp = Integer.parseInt(firstOfSecuence[minActual]);
					bw.write(temp + "\n");
					firstOfSecuence[minActual] = br[minActual].readLine();
					if (firstOfSecuence[minActual] == null) {
						finMezcla = true;
						wereChecking[minActual] = false;

					} else if (firstOfSecuence[minActual].trim().equals("@")
							|| Integer.parseInt(firstOfSecuence[minActual]) < temp) {
						wereChecking[minActual] = false;
					}
				}

				// Esta sentencias sirven para determinar si terminé la mezcla de las secuencias
				// de todas las cintas
				mezclandoTramos = false;
				for (boolean b : wereChecking) {
					mezclandoTramos = mezclandoTramos || b;
				}
			}
			numTramosMezclados++;
		}
	
		bw.close();
		fw.close();

		for (int i = 0; i < fr.length; i++) {
			File f1 = new File(tempFile);
			f1.createNewFile();
			fw = new FileWriter(f1);
			bw = new BufferedWriter(fw);
			String line;
			if (firstOfSecuence[i] != null) {
				fw.write(firstOfSecuence[i] + "\n");
				while ((line = br[i].readLine()) != null) {
					fw.write(line + "\n");
				}
			}
			String fileName = f[i].getPath();
			fr[i].close();
			br[i].close();
			f[i].delete();
			bw.close();
			fw.close();
			f1.renameTo(new File(fileName));

		}

//		SiguientePasoMezcla
		for (int l = 0; l < secuenciasCintas.length;l++) {
			if (secuenciasCintas[l] == 0)
				secuenciasCintas[l] = numTramosMezclados;
			else
				secuenciasCintas[l] -= numTramosMezclados;

		}
		return mezclaHastaVacio(secuenciasCintas);

	}

	public static long[] secuenciasPorCinta(int cantidadDeTramos) {

		if (cantidadDeTramos == 1) {
			long[] p = { 1, 0 };
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
						siguienteNivel = Arrays.copyOf(siguienteNivel, siguienteNivel.length + 1);
						siguienteNivel[siguienteNivel.length - 1] = 0;
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

			toReturn = Arrays.copyOf(toReturn, toReturn.length + 1);
			toReturn[toReturn.length - 1] = 0;
			return toReturn;

		}

	}

	public static int cantidadDeTramos(String url) throws IOException {
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

		try {
			FicherosTexto.fillIntsTxt(url, 3, 10);
			int cant = 0;
			cant = cantidadDeTramos(url);
			System.out.println("Cantidad de tramos: " + cant);
			long secuenciasPorCinta[] = secuenciasPorCinta(cant);
			int sum = 0, cantSecNulas = 0;
			System.out.print("Reparticion de los tramos en los archivos: ");
			for (int i = 0; i < secuenciasPorCinta.length; i++) {
				System.out.print(secuenciasPorCinta[i] + " ");
				sum += secuenciasPorCinta[i];
			}
			System.out.println();

			division(url, secuenciasPorCinta, cant);

			System.out.println(mezclaHastaVacio(secuenciasPorCinta));
//		System.out.println();
//		cantSecNulas=sum-cant;
//		System.out.println("La cantidad de secuencias nulas es: "+cantSecNulas);

		}

		catch (IOException e) {
			e.getMessage();
		}

	}

}
