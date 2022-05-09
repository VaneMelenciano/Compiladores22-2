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
    
    //Revisa si existen más tokens por revisar, si existe, llama a una función para validar los tokens individualmente.
    public static boolean revisarListaDeTokens() throws IOException {
        
        while(LectorTxt.getHayTokens() == true){  //mientras haya más tokens = true
            
            System.out.println("--- Nuevo Token --- ");

            inicializarToken();
            
            if(validarToken() == true){
                 
                System.out.println("Token válido: " + expresion + "\n");
            
            }else{
                
                System.out.println("Token inválido\n");
                return false;
            }
            
        }
        System.out.println("\n No hay más tokens");
        return true;
    }
    
    public static void inicializarToken(){
        expresion = ""; //para poner la expresion en caso de ser valida
        caracter = LectorTxt.obtenerSiguienteCaracter();
    }
    
    public static boolean validarToken() throws IOException{
        if(expresionEsPalabraReservada()) return true;

        if(expresionEsIdentificador()) return true;

        if(expresionSonNumeros()) return true;

        if(expresionEsOperador()) return true;

        if(expresionEsSimbolo()) return true;
        
        if(expresionEsTexto()) return true;
        
        return false;
    }

    //validar palabras reservadas. [A-Z]+
    public static boolean expresionEsPalabraReservada() throws IOException{
        System.out.println("VALIDANDO SI ES PALABRA RESERVADA...");
       
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
                /*if (46 == (int) caracter) { //  46 es . en codigo ASCII
                    //tiene el último carácter, es identificador.
                   expresion += caracter;
                    return true;
                }*/
                if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
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

    //validar [+ / - / / / * / ^ / %]
    public static boolean expresionEsOperador() throws IOException{
        System.out.println("VALIDANDO SI SON SÍMBOLOS...");
        //validando '+'
        if(caracter == '+'){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
                return true; 
            }
        }
        //validando '-'
        if(caracter == '-'){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
                return true; 
            }
        }
        //validando '/'
        if(caracter == '/'){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
                return true; 
            }
        }
        //validando '*'
        if(caracter == '*'){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
                return true; 
            }
        }
        //validando '^'
        if(caracter == '^'){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
                return true; 
            }
        }
        //validando '%'
        if(caracter == '%'){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
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

        //validando '&'
        if(caracter == '&'){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
                return true; 
            }
        }

        //validando '°'
        if(caracter == '°'){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
                return true; 
            }
        }

        //validando '¬'
        if(caracter == '¬'){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
                return true; 
            }
        }

        //validando '('
        if(caracter == 40){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
                return true; 
            }
        }
        
        //validando ')'
        if(caracter == 41){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
                return true; 
            }
        }

        //validando '{'
        if(caracter == 123){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
                return true; 
            }
        }

        //validando '}'
        if(caracter == 125){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
                return true; 
            }
        }

        //validando '['
        if(caracter == 91){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
                return true; 
            }
        }

        //validando ']'
        if(caracter == 93){
            guardarCaracterYobtenerSiguiente();
            if(caracter == '\0'){ //si lo que sigue es vacío, es válido. 
                return true; 
            }
        }
        
        return false;
    }
    
    //validar 'Texto'
    public static boolean expresionEsTexto() throws IOException{
        System.out.println("VALIDANDO SI ES PALABRA 'Texto'...");
        
        if(caracter == '\''){ //si inicia con comilla simple
            guardarCaracterYobtenerSiguiente();
            while(caracter != '\'' ){ //mientras no sea otra comilla simple
                guardarCaracterYobtenerSiguiente();
            }
            
            if(caracter == '\''){ //si termina con comilla simple
                guardarCaracterYobtenerSiguiente();
                return true;
            }
        }
        return false;//si ya agregró a cadena todas los caracteres y ya no hay más letras
    }
    
    public static void guardarCaracterYobtenerSiguiente() throws IOException{
        expresion += caracter;
        caracter = LectorTxt.obtenerSiguienteCaracter();
    }
    
    //funciones que usa vanessa
    public static boolean expresionSonNumeros(String numero) {
        //System.out.println("VALIDANDO SI SON NÚMEROS...");
        for(int i=0; i<numero.length(); ){
        //for(int i=0; i<numero.length(); i++){
            if(Character.isDigit(numero.charAt(i))){ //revisar si es digito/número
                while(Character.isDigit(numero.charAt(i))){ //mientras la expresión tenga números.
                    i++;
                }
                if(46==(int)numero.charAt(i)){ //si es un punto, puede ser número decimal
                    i++;
                    if(Character.isDigit(numero.charAt(i))){ //si hay un número después del punto, es decimal.

                        while(i<numero.length() && Character.isDigit(numero.charAt(i))){ //continúa revisando que lo que sigue sean números.
                            System.out.println(i + " " +numero.charAt(i));
                            i++;
                        }
                        if(i==numero.length()){ //si lo que sigue es vacío, es válido. 
                            return true; 
                         }
                    }
                }else if(numero.charAt(i) == '\0'){ //si lo que sigue es vacío, es válido. 
                   return true; 
                }
            }
        }
        return false;
    }   
    public static boolean expresionEsIdentificador(String identificador) {
        //System.out.println("VALIDANDO SI ES IDENTIFICADOR...");
        for(int i=0; i<identificador.length(); ){
            if (Character.isLowerCase(identificador.charAt(i))) { //tiene una minuscula      
                i++;
                if (Character.isLowerCase(identificador.charAt(i))) { //tiene dos minusculas
                    i++;
                    //( [a-z] | [A-Z] | [0-9] )^*
                    while ((i<identificador.length()) && (Character.isLowerCase(identificador.charAt(i)) == true || Character.isUpperCase(identificador.charAt(i)) == true || Character.isDigit(identificador.charAt(i)))){
                        i++;
                    }
                    if(i==identificador.length()) return true; 
                    else return false;
                } else return false;
            }else return false;
        }
        return false;
    }
    
    
}