package manejoFicheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;



public class FicherosTexto {

	public static void fillIntsTxt(String url, int digitos, int N) throws IOException{
		File f  = new File(url);
		f.createNewFile();
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);
		Random r = new Random();
		for (int i = 0; i < N;i++) {
			bw.write(r.nextInt((int)Math.pow(10, digitos)) + "\n");
			
		}
		bw.close();
		fw.close();
	}
	
	public static String[] leerFichero(String url)throws IOException{
		FileReader fr = new FileReader(url);
		BufferedReader br = new BufferedReader(fr);
		String lines[] = new String[0], line;
		while((line=br.readLine())!=null&&!line.isBlank()) {
			lines = Arrays.copyOf(lines, lines.length+1);
			lines[lines.length-1] = line;
		}
		return lines;
	}
	
	public static void main(String[] args) {
	//	 TODO Auto-generated method stub
		String[] a = {};
		try{
			fillIntsTxt("Enteros.txt",2,10000);
			a = leerFichero("Enteros.txt");
		}catch(IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		for (String s: a) {
			System.out.println(s);
		}
		
	
	}

}
