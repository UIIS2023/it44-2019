package strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.JFileChooser;


public class LogSaving implements Saving, Serializable {
	
	JFileChooser fileChooser= new JFileChooser();
	int d = fileChooser.showSaveDialog(null);
	File fileToSave = fileChooser.getSelectedFile();
	FileOutputStream fileOutputStream;
	
	@Override
	public void save(Object o) throws IOException{
		if(d == JFileChooser.APPROVE_OPTION) {
			try {
				fileOutputStream = new FileOutputStream(fileToSave);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
			out.writeObject(o);
			out.close(); 
			fileOutputStream.close();  
		}
	}
}
