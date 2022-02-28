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

import java.io.IOException;

public class AnalizadorLexico {
    
    public static String expresion, expresionLeida;
    public static char caracter;
    public static int contadorExpresionLeida;

    public static char obtenerSiguienteCaracter(){
        char caracterAdevolver;
        try{
            caracterAdevolver = expresionLeida.charAt(contadorExpresionLeida);
        }catch(StringIndexOutOfBoundsException e){ //En caso de que la expresión leida haya terminado, retorna '\0'
            caracterAdevolver = '\0';
        }
        contadorExpresionLeida++;
        return caracterAdevolver;
    }

    //verifica si la cadena leida en el txt es correcta según las expresiones regulares para los digitos o los identificadores
    public static String retornarCadena() throws IOException {
        expresion = ""; //para poner la expresion en caso de ser valida
        caracter = obtenerSiguienteCaracter();
        
        if(expresionEsPalabraReservada()) return expresion;
        System.out.println("Inválido para ser palabra reservada. Caracter inválido: " + caracter);
        
        if(expresionEsIdentificador()) return expresion;
        System.out.println("Inválido para ser identificador. Caracter inválido: " + caracter);

        if(expresionSonNumeros()) return expresion;
        System.out.println("Inválido para ser cadena de números. Caracter inválido: " + caracter);
        
        if(expresionEsSimbolo()) return expresion;
        System.out.println("Inválido para ser símbolo. Caracter inválido: " + caracter);
        
        
        return ("La expresión no es válida en ningún caso. Carácter invalido: " + caracter);
    }

    //validar palabras reservadas. [A-Z]+
    public static boolean expresionEsPalabraReservada() throws IOException{
        System.out.println("VALIDANDO SI ES PALABRA RESERVADA...");
       
        //Mayusculas: Character.isUpperCase(char c)
        //Minusculas: Character.isLowerCase(char c)
        if(Character.isUpperCase(caracter)){ //si es mayuscula. Puede ser palabra reservada
            
            while(Character.isUpperCase(caracter)){ //mientras el siguiente char sea mayúscula, continúa en el ciclo.
                guardarCaracterYobtenerSiguiente();
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
            
            guardarCaracterYobtenerSiguiente();
            
            if (Character.isLowerCase(caracter)) { //tiene dos minusculas
                
                guardarCaracterYobtenerSiguiente();
                
                //( [a-z] | [A-Z] | [0-9] )^*
                while (Character.isLowerCase(caracter) == true || Character.isUpperCase(caracter) == true || Character.isDigit(caracter)) {
                    guardarCaracterYobtenerSiguiente();
                }
                if (46 == (int) caracter) { //  46 es . en codigo ASCII
                    //tiene el último carácter, es identificador.
                   expresion += caracter;
                    return true;
                }
                
            }
        }
        return false;
    }
    
    //validar [0-9]+ (. [0-9]+)?
    public static boolean expresionSonNumeros() throws IOException{
        System.out.println("VALIDANDO SI SON NÚMEROS...");
        
        if(Character.isDigit(caracter)){ //revisar si es digito/número
            guardarCaracterYobtenerSiguiente();
            
            while(Character.isDigit(caracter)){ //mientras la expresión tenga números.
                guardarCaracterYobtenerSiguiente();
            }
            
            if(46==(int)caracter){ //si es un punto, puede ser número decimal
                
                guardarCaracterYobtenerSiguiente();
                
                if(Character.isDigit(caracter)){ //si hay un número después del punto, es decimal.
                    
                    while(Character.isDigit(caracter)){ //continúa revisando que lo que sigue sean números.
                        guardarCaracterYobtenerSiguiente();
                    }
                    if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
                        return true; 
                     }
                }
            }else if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
               return true; 
            }
        }
        return false;
    }
    
    //validar [== / != / << / >> / <= / >=]
    public static boolean expresionEsSimbolo() throws IOException{
        System.out.println("VALIDANDO SI SON SÍMBOLOS...");
        
        //validando '=='
        if(caracter == '='){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '='){
                guardarCaracterYobtenerSiguiente();
                return true;
            }
        }
        
        //validando '!='
        if(caracter == '!'){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '='){
                guardarCaracterYobtenerSiguiente();
                return true;
            }
        }
        
        //validando '<<' y '<='
        if(caracter == '<'){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '<' || caracter == '='){
                guardarCaracterYobtenerSiguiente();
                return true;
            }
        }
        
        //validando '>>' y '>='
        if(caracter == '>'){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '>' || caracter == '='){
                guardarCaracterYobtenerSiguiente();
                return true;
            }
        }
        
        return false;
    }
    
    public static void guardarCaracterYobtenerSiguiente() throws IOException{
        expresion += caracter;
        caracter = obtenerSiguienteCaracter();
    }
    
}