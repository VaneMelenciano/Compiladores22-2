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
    
    public static String expresion;
    public static char caracter;

    //verifica si la cadena leida en el txt es correcta según las expresiones regulares para los digitos o los identificadores
    public static void revisarListaDeTokens() throws IOException {
        while(LectorTxt.getHayTokens() == true){
            System.out.println("--- Nuevo Token --- ");

            expresion = ""; //para poner la expresion en caso de ser valida
            caracter = LectorTxt.obtenerSiguienteCaracter();

            if(validarToken() == true){
                 System.out.println("Token válido: " + expresion + "\n");
            }else{
                System.out.println("Token inválido\n");
            }
            
        }
        System.out.println("\n No hay más tokens");
    }
    
    public static boolean validarToken() throws IOException{
        if(expresionEsPalabraReservada()) return true;

        if(expresionEsIdentificador()) return true;

        if(expresionSonNumeros()) return true;

        if(expresionEsSimbolo()) return true;
        
        return false;
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
        caracter = LectorTxt.obtenerSiguienteCaracter();
    }
    
}