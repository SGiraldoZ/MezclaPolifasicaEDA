package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import manejoFicheros.*;

public class Ordenamiento {
	private static String aux1 = "aux1.txt";
	private static String aux2 = "aux2.txt";

	public static void sortIntsTxt0(String url) throws IOException {
		(new File(aux1)).createNewFile();
		(new File(aux2)).createNewFile();

		FileReader frMain, frAux1, frAux2;
		FileWriter fwAux1, fwAux2, fwMain;
		BufferedReader brMain, brAux1, brAux2;
		BufferedWriter bwMain, bwAux1, bwAux2;

		int tot;
		int div = 0;
		int lenSecuencia = 1;
		int eDivid; // Numeros de elementos asignados en una fase de división
		int numMezclados, nM1, nM2;
		boolean aux1 = true;
		String line, line1, line2;
		do {
			frMain = new FileReader(url);
			brMain = new BufferedReader(frMain);
			fwAux1 = new FileWriter(Ordenamiento.aux1);
			fwAux2 = new FileWriter(aux2);
			bwAux1 = new BufferedWriter(fwAux1);
			bwAux2 = new BufferedWriter(fwAux2);

			eDivid = 0;
			tot = 0;
			// Se hace la division numero div
			while ((line = brMain.readLine()) != null) {
				if(!line.isBlank()) {
					if (aux1) {
						bwAux1.write(line+"\n");
					} else {
						bwAux2.write(line+"\n");
					}
					eDivid++;
					aux1 = (eDivid % lenSecuencia == 0) ? !aux1 : aux1;	
					tot++;	
				}
			}
			div++;
			brMain.close();
			frMain.close();
			bwAux1.close();
			bwAux2.close();
			fwAux1.close();
			fwAux2.close();
			

			// FASE MEZCLA

			
			numMezclados = 0;
			frAux1 = new FileReader(Ordenamiento.aux1);
			frAux2 = new FileReader(aux2);
			fwMain = new FileWriter(url);
			brAux1 = new BufferedReader(frAux1);
			brAux2 = new BufferedReader(frAux2);
			bwMain = new BufferedWriter(fwMain);
			line1 = brAux1.readLine();
			line2 = brAux2.readLine();
			
			while (numMezclados < tot) {
				nM1 = 0;
				nM2 = 0;
				while ((nM1 < lenSecuencia || nM2 < lenSecuencia)&&!((line1==null||line1.isBlank())&&(line2==null||line2.isBlank()))) {
					if ((nM1 >= lenSecuencia || line1==null ||line1.isBlank())) {
						bwMain.write(line2 + "\n");
						line2 = brAux2.readLine();
						nM2++;
						numMezclados++;
					} else if ((nM2 >= lenSecuencia || line2==null||line2.isBlank())){
						bwMain.write(line1 + "\n");
						line1 = brAux1.readLine();
						nM1++;
						numMezclados++;
					} else if (Integer.parseInt(line1)<Integer.parseInt(line2)) {
						bwMain.write(line1+"\n");
						line1 = brAux1.readLine();
						nM1++;
						numMezclados++;
					} else {
						bwMain.write(line2+"\n");
						line2 = brAux2.readLine();
						nM2++;
						numMezclados++;
					}
				}
				
				bwMain.flush();
			}
			lenSecuencia = (int) Math.pow(2, div);
			frAux1.close();
			frAux2.close();
			fwMain.close();
			brAux1.close();
			brAux2.close();
			bwMain.close();

		} while (lenSecuencia < tot);

	}

	public static void main(String[] args) {
		try {
			sortIntsTxt0("Enteros.txt");
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
	}
}
