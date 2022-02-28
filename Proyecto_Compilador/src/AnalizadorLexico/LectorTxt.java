/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalizadorLexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Vanessa
 */
public class LectorTxt {
    
    public ArrayList<String> lista;
    
    public LectorTxt(){
        leerArchivo();
    }
    
    //Leer el archivo (csc o txt)
    public void leerArchivo(){
        String aux, texto;
        lista = new ArrayList();
        
        
        try {
            JFileChooser jFileChooser = new JFileChooser(); //objeto que permite cargar la ventana de archivos
            jFileChooser.setCurrentDirectory(new File("./"));
            jFileChooser.showOpenDialog(jFileChooser);
            
            //Abre el archivo
            File file = jFileChooser.getSelectedFile();

            //recorremos el archivo y lo leemos
            if (file != null) {
                
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while ((aux = bufferedReader.readLine()) != null) {
                    
                    texto = aux; //guarda la linea del archivo leido en el String
                    
                    lista.add(texto); //añade el String anterior a la lista
                }
                bufferedReader.close();
            }
            
        }catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex + ""
                    + "\nArchivo no encontrado",
                    "ADVERTENCIA!!!", JOptionPane.WARNING_MESSAGE);
            
        }
    }

    public ArrayList<String> getTexto(){
        return lista;
    }
}
