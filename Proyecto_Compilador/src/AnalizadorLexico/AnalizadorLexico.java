/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalizadorLexico;
/**
 * prueba
 * @author Vanessa Melenciano, Hugo Arroyo, Aarón Cano
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;

public class AnalizadorLexico {

    public static JFileChooser fileChooser;
    public static File file;
    public static FileReader fileReader;
    
    public static String expresion;
    public static char caracter;

    public static void main(String args[]) throws FileNotFoundException, IOException {
        leerArchivo();
        System.out.println(retornarCadena());
    }

    //LEER ARCHIVO TXT//
    public static void leerArchivo() throws FileNotFoundException {
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("./"));
        fileChooser.showOpenDialog(fileChooser);

        file = fileChooser.getSelectedFile();
        fileReader = new FileReader(file);
    }

    //LEER Y DEVOLVER CARACTERES DEL TXT SELECCIONADO////
    public static char obtenerSiguienteCaracter() throws IOException {
        int enteroLeido = leerTxtCaracter();
        
        //leerTxtCaracter devuelve -1 en caso de que no haya ningún caracter.
        //si devuelve -1 no lo casteamos a char, ya que da un error.
        //devolvemos '\0' para indicar vacío.
        if(enteroLeido == -1) return '\0'; 
        
        char caracterADevolver = (char)enteroLeido;
        
        return caracterADevolver;
    }

    public static int leerTxtCaracter() throws IOException {
        return fileReader.read();
    }
    ///////////////////////////////////////////////////

    //verifica si la cadena leida en el txt es correcta según las expresiones regulares para los digitos o los identificadores
    public static String retornarCadena() throws IOException {
        expresion = ""; //para poner la expresion en caso de ser valida
        caracter = obtenerSiguienteCaracter(); //String c = imprimirCaracteres()+"";

        //Mayusculas: Character.isUpperCase(char c)
        //Minusculas: Character.isLowerCase(char c)
        
        //REVISAR SI ES UNA PALABRA RESERVADA
        if(expresionEsPalabraReservada()){
            return expresion;
        }
        //si llega a este punto, no es palabra reservada.
        System.out.println("Inválido para ser palabra reservada. Caracter inválido: " + caracter);
        
        //REVISAR SI ES UN IDENTIFICADOR
        if(expresionEsIdentificador()){
            //es identificador.
            return expresion;
        }
        //si llega a esta parte del código, no es identificador.
        System.out.println("Inválido para ser identificador. Caracter inválido: " + caracter);
        
        //REVISAR SI ES UNA CADENA DE ENTEROS O DECIMALES
        if(expresionSonNumeros()){
            //son números.
            return expresion;
        }
        //si llega a esta parte del código, no son números.
        System.out.println("Inválido para ser cadena de números. Caracter inválido: " + caracter);
        
        
        
        //Si llega a este punto, la expresión no es válida en ningún caso.
        return ("La expresión no es válida en ningún caso. Carácter invalido: " + caracter);
    }

        //validar palabras reservadas. [A-Z]+
    public static boolean expresionEsPalabraReservada() throws IOException{
        System.out.println("VALIDANDO SI ES PALABRA RESERVADA...");
        
        if(Character.isUpperCase(caracter)){ //si es mayuscula. Puede ser palabra reservada
            
            while(Character.isUpperCase(caracter)){ //mientras el siguiente char sea mayúscula, continúa en el ciclo.
                expresion += caracter;
                caracter = obtenerSiguienteCaracter(); 
            }
            
            if(caracter == '\0'){ //si ya agregró a cadena todas las mayusculas y ya no hay más letras
                return true;
            }
        }
        return false;
    }
    
    //validar si cumple [a-z]+ [a-z]+ ( [a-z] | [A-Z] | [0-9] )^* .
    public static boolean expresionEsIdentificador() throws IOException {
        System.out.println("VALIDANDO SI ES IDENTIFICADOR...");
        
        if (Character.isLowerCase(caracter)) { //tiene una minuscula      
            
            expresion += caracter;
            caracter = obtenerSiguienteCaracter(); //segundo caracter del txt
            
            if (Character.isLowerCase(caracter)) { //tiene dos minusculas
                
                expresion += caracter;
                caracter = obtenerSiguienteCaracter();
                
                while (Character.isLowerCase(caracter) == true || Character.isUpperCase(caracter) == true || Character.isDigit(caracter)) { //( [a-z] | [A-Z] | [0-9] )^*
                    expresion += caracter;
                    caracter = obtenerSiguienteCaracter();
                }
                if (46 == (int) caracter) { //  46 es . en codigo ASCII
                    //tiene el último carácter, es identificador.
                   expresion += caracter;
                    return true;
                } else {
                    //no tiene punto final, está mal
                    return false;
                }
                
            } else {
                //no tiene dos minusculas, está mal
                return false;
            }
        }
        return false;
    }
    
    //validar [0-9]+ (. [0-9]+)?
    public static boolean expresionSonNumeros() throws IOException{
        System.out.println("VALIDANDO SI SON NÚMEROS...");
        
        if(Character.isDigit(caracter)){ //revisar si es digito/número
            expresion += caracter;
            caracter = obtenerSiguienteCaracter();
            
            while(Character.isDigit(caracter)){ //mientras la expresión tenga números.
                expresion += caracter;
                caracter = obtenerSiguienteCaracter(); 
            }
            
            if(46==(int)caracter){ //si es un punto, puede ser número decimal
                
                expresion += caracter;
                caracter = obtenerSiguienteCaracter(); 
                
                if(Character.isDigit(caracter)){ //si hay un número después del punto, es decimal.
                    
                    while(Character.isDigit(caracter)){ //continúa revisando que lo que sigue sean números.
                        expresion += caracter;
                        caracter = obtenerSiguienteCaracter(); 
                    }
                    if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
                        return true; 
                     }else{
                        return false; 
                     }
                    
                }else{ //no hay número después del decimal, es inválido.
                    return false;
                }
                
            }else if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
               return true; 
            }else{
               return false; 
            }
        }
        return false;
    }
    
}