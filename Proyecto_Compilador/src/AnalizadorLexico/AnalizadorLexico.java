/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalizadorLexico;
//asdfgh
//probando probando probando 1 2 3 4
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

public static void main(String args[]) throws FileNotFoundException, IOException {
    leerArchivo();
    System.out.println(retornarCadena());
}

public static void leerArchivo() throws FileNotFoundException{
    fileChooser = new JFileChooser();
    fileChooser.setCurrentDirectory(new File("./"));
    fileChooser.showOpenDialog(fileChooser);

    file = fileChooser.getSelectedFile();
    fileReader = new FileReader(file);
}

public static int leerCaracter() throws IOException{
    int c = fileReader.read();
    return c;
}

public static char imprimirCaracteres() throws IOException{
    int caracter = leerCaracter();
    return (char)caracter;
}

//verifica si la cadena leida en el txt es correcta según las expresiones regulares para los digitos o los identificadores
public static String retornarCadena()  throws IOException{
    String cadena = ""; //para poner la expresion en caso de ser valida
    char c = imprimirCaracteres(); //String c = imprimirCaracteres()+"";
    
    //Mayusculas: Character.isUpperCase(char c)
    //Minusculas: Character.isLowerCase(char c)
    
    //validar si cumple [a-z]+ [a-z]+ ( [a-z] | [A-Z] | [0-9] )^* .
    if(Character.isLowerCase(c)){
        System.out.println("VALIDANDO IDENTIFICADOR...");
        //tiene una minuscula
        cadena += c;
        c=imprimirCaracteres(); //segundo caracter del txt
        if(Character.isLowerCase(c)){
            //tiene dos minusculas
            cadena += c;
             c = imprimirCaracteres();
            while(Character.isLowerCase(c)==true){//mientras siga siendo minuscula  
                cadena += c;
                c = imprimirCaracteres();
            }
            while(Character.isLowerCase(c)==true || Character.isUpperCase(c)==true || Character.isDigit(c)){ //( [a-z] | [A-Z] | [0-9] )^*
                cadena += c;
                c = imprimirCaracteres();
            }
            if(46==(int)c){//  46 es . en codigo ASCII
                //tiene el último carácter
                cadena += c;
                return cadena;
            }else{
                //no tiene punto final, está mal
                return ("Carácter invalido: " + c);
            }
        }else{
            //no tiene dos minusculas, está mal
            return ("Carácter invalido: " + c);
        }
    }else if(Character.isDigit(c)){//es digito
        //validar [0-9]+ (. [0-9]+)?
        System.out.println("VALIDANDO IDENTIFICADOR...");
        cadena += c;
        c = imprimirCaracteres();
        while(Character.isDigit(c)){
            cadena += c;
            c = imprimirCaracteres(); 
        }
        //System.out.println(c);
        if(46==(int)c){ //si es punto, decimal
            cadena += c;
            c = imprimirCaracteres(); 
            if(Character.isDigit(c)){ //si hay un digito después del punto
                while(Character.isDigit(c)){
                    cadena += c;
                    c = imprimirCaracteres(); 
                }
                return cadena;
            }else{ //no hay numero después del decimal, está mal
                return ("Carácter invalido: " + c);
            }
        }else if(!String.valueOf(c).isEmpty()){
           return cadena; 
        }else{
           return ("Carácter invalido: " + c); 
        }
        
    }else{
        //System.out.println("SIMBOLO NO ESTABLECIDO PARA INICIAR CADENA:");
        return ("Carácter invalido: " + c);
    }
}
}