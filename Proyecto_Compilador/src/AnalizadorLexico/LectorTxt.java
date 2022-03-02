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
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Vanessa
 */
public class LectorTxt {
    
    public static ArrayList<String> lista;
    
    public static int contadorExpresionLeida, contadorTokenLeido;
    public static String expresionLeida;    
    public static boolean hayTokens;
    
    public LectorTxt(){
        leerArchivo();
        
        contadorTokenLeido = 0;
        contadorExpresionLeida = 0;
        
        hayTokens = true;
        
        try{
            expresionLeida = getLista().get(contadorTokenLeido);
        }catch(Exception e){
            System.out.println("No se ha encontrado ningún token en el archivo");
            System.exit(0); //Terminar todo el programa.
        }
    }
    
    //Leer el archivo (csc o txt)
    public void leerArchivo(){
        String aux, texto;
        ArrayList<String> listaAux = new ArrayList();
        
        
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
                    
                    listaAux.add(texto); //añade el String anterior a la lista
                }
                bufferedReader.close();
                
                //TOKENIZAR DATOS
                lista = new ArrayList(); //un renglon
                
                for(String elemento : listaAux){
                    StringTokenizer stringTokenizer = new StringTokenizer(elemento);
                    while(stringTokenizer.hasMoreTokens()){
                        lista.add(stringTokenizer.nextToken());
                    }
                }
                
            }
            
        }catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex + ""
                    + "\nArchivo no encontrado",
                    "ADVERTENCIA!!!", JOptionPane.WARNING_MESSAGE);
            
        }
    }
    
    public static char obtenerSiguienteCaracter(){
        char caracterAdevolver;
        try{
            caracterAdevolver = expresionLeida.charAt(contadorExpresionLeida);
            contadorExpresionLeida++;
        }catch(StringIndexOutOfBoundsException e){ //En caso de que la expresión leida haya terminado, retorna '\0'
            caracterAdevolver = '\0';
           
            siguienteToken();
        }
        return caracterAdevolver;
    }
    
    public static void siguienteToken(){
        contadorTokenLeido++;
        if(contadorTokenLeido < getLista().size()){
            obtenerSiguienteToken();
        }else{
            noHayMasTokens();
        }
    }
    
    public static void obtenerSiguienteToken(){
        expresionLeida = getLista().get(contadorTokenLeido);
        contadorExpresionLeida = 0;
    }
    public static void noHayMasTokens(){
        hayTokens = false;
    }

    public static ArrayList<String> getLista(){
        return lista;
    }
    public static boolean getHayTokens(){
        return hayTokens;
    }
}
