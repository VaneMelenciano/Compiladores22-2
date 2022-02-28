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
import java.util.LinkedList;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Vanessa
 */
public class Tokenizador {
    //lee datos de un archivo, los tokeniza (con la clase en la primera posición o el la última), y los guarda en una lista de instancias
    //de tipo Patron, cada instancia tiene su vector y clase original
    
    //private ArrayList<Patron> instancias;
    //private Instancias instancias;
    //private ArrayList<Item> items;
    //Leer archivo (csv, txt), tokenizar datos, guardar las instancias (de cada renglon)
    
    //private int ordenDeClase; //si la clase se encuentra al final de cada instancia, es 0. Si la clase está al principio, es 1
    
    public ArrayList<String> lista;
    
    public Tokenizador(){
        //this.items=new ArrayList<Item>();
        leerArchivo();
    }
    
    //Leer el archivo (csc o txt)
    public void leerArchivo(){
        String aux, texto;
        lista = new ArrayList(); //para guardar los datos que se vayan leyendo
        
        
        try {
            JFileChooser file = new JFileChooser(); //llamamos el metodo que permite cargar la ventana
            file.setCurrentDirectory(new File("./"));
            file.showOpenDialog(file);
            //Abre el archivo
            File abre = file.getSelectedFile();

            //recorremos el archivo y lo leemos
            if (abre != null) { //verifica que esté abierto
                
                FileReader archivos = new FileReader(abre);
                BufferedReader lee = new BufferedReader(archivos);

                while ((aux = lee.readLine()) != null) {
                    texto = aux; //guarda la linea del archivo leido en el String
                    lista.add(texto); //añade el String anterior a la lista
                }
                lee.close();
                
                
                //TOKENIZAR DATOS
                /*ArrayList<String> lista2 = new ArrayList<>(); //un renglon
                
                for (int i = 0; i < lista.size(); i++) { 

                    StringTokenizer tokens = new StringTokenizer(lista.get(i), ","); //va separando los renglones guardado en la lista, por las comas

                    while (tokens.hasMoreTokens()) { //mientras existan tokens (renglones)
                        lista2.add(tokens.nextToken()); //guarda cada dato del renglo en la lista2
                    }
                    //34,6
                    //double[] vector = new double[lista2.size() - 1]; //declarando un vector para guarda los datos
                    int peso =Integer.parseInt ( lista2.get(0) );
                    int beneficio =Integer.parseInt (  lista2.get(1) );
                    this.getItems().add(new Item(peso, beneficio));
                    lista2.clear();
                }*/
          
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
    
    /**
     * @return the items
     */
    /*public ArrayList<Item> getItems() {
        return items;
    }*/
}
