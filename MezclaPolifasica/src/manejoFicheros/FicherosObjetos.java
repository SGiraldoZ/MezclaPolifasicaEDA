package manejoFicheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Random;


public class FicherosObjetos<T> {
	public static void fillRandInteger(String url) throws IOException{
		File f = new File(url);
		f.createNewFile();
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		Random r = new Random();
		for (int i = 0; i < 50; i++) {
			oos.writeObject((Integer)r.nextInt(100));
		}
		oos.close();
		fos.close();
		
	}
	
	public static Integer[] readIntegers(String url) throws IOException, ClassNotFoundException{
		File f = new File(url);
		f.createNewFile();
		FileInputStream fos = new FileInputStream(f);
		ObjectInputStream oos = new ObjectInputStream(fos);
		Integer a []= new Integer[0], b;
		while (true) {	
			try {
				b = (Integer)oos.readObject();
				a = Arrays.copyOf(a,a.length+1);
				a[a.length-1] = b;
				
			}catch(EOFException e) {
				break;
			}
		}
		oos.close();
		fos.close();
		return a;
	}
	
	public static void main(String[] args) {

	}
}
