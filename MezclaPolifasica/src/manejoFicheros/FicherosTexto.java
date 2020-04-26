package manejoFicheros;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;



public class FicherosTexto {

	public static void fillIntsTxt(String url) throws IOException{
		File f  = new File(url);
		f.createNewFile();
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);
		Random r = new Random();
		for (int i = 0; i < 20;i++) {
			bw.write(r.nextInt(100) + "\n");
			
		}
		bw.close();
		fw.close();
	}
	
	public static void main(String[] args) {
	//	 TODO Auto-generated method stub
		try{
			fillIntsTxt("Enteros.txt");
		}catch(IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
		
	
	}

}
