/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalizadorSintactico;

import AnalizadorLexico.AnalizadorLexico;

/**
 *
 * @author Vanessa
 */
//Cada regla (no terminal) BNF es un método
//Si encontramos un terminal, es validar si es ese elemento el que está
public class BNF {
    private String tokenProvisional;
    
    private void programa(){
        //INICIO <Inicialización> <Instrucciones> <AuxInst> FIN
                //Se manda llamar a la palabra del archivo porque se espera un terminal "INICIO"
        this.tokenProvisional = "INICIO"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("INICIO")){
            inicializacion();
            instrucciones();
            auxInst();
                this.tokenProvisional = "FIN"; //AQUI SE MANDA LLAMAR AL LEXICO
                if(this.tokenProvisional.equals("FIN")){
                    compilacionExitosa();
                }else{
                    imprimirErrorPalabra("FIN");
                }
        }else{
            //Control de errores
            imprimirErrorPalabra("INICIO");
        }
    }

    private void auxInst() {
        //. <Instrucciones> <AuxInst>
        this.tokenProvisional = "."; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals(".")){
            instrucciones();
            auxInst(); // <- se manda llamar a si, después se arregla eso
        }else this.imprimirErrorSimbolo(".");
        //ԑ <- después se programa
    }

    private void inicializacion() {
        //<AuxCons> <AuxTipo> <AuxFoncion>
        auxCons();
        auxTipo();
        auxFoncion();
    }

    private void auxCons() {
        //CONS  <Identificador> : <AuxNI>  <AuxI> .
        this.tokenProvisional = "CONS";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("CONS")){
            this.tokenProvisional = "identificador"; //AQUI SE MANDA LLAMAR AL LEXICO
            if(AnalizadorLexico.expresionEsIdentificador(this.tokenProvisional)){//????
                this.tokenProvisional = ":"; //AQUI SE MANDA LLAMAR AL LEXICO
                if(this.tokenProvisional.equals(":")){
                   auxNI();
                    auxI(); 
                    this.tokenProvisional = "."; //AQUI SE MANDA LLAMAR AL LEXICO
                    if(this.tokenProvisional.equals(".")){
                        //FALTA
                    }else this.imprimirErrorSimbolo(".");
                }else this.imprimirErrorSimbolo(":");
            }else this.imprimirError("un identificador");
        }else this.imprimirErrorPalabra("CONS");
         //ԑ <- después se programa
    }

    private void auxI() {
        //~ <Identificador> : <AuxNI>  <AuxI>
        // .
        
        this.tokenProvisional = "~"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("~")){
            this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
            if(AnalizadorLexico.expresionEsIdentificador(this.tokenProvisional)){//????
                this.tokenProvisional = ":";
                if(this.tokenProvisional.equals(":")){
                    auxNI();
                    auxI();
                }else{
                    this.imprimirErrorSimbolo(":");
                }
            }else this.imprimirError("un identificador");
        }else if(this.tokenProvisional.equals(".")){
            //FALTA
        }else this.imprimirErrorSimbolo("~ / .");
    }
    
    private void auxNI() {
        /*<AuxNI> ::= <Identificador>
        <AuxNI> ::= <Numero>*/
        this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(AnalizadorLexico.expresionEsIdentificador(this.tokenProvisional)){//????
            
        }else if(AnalizadorLexico.expresionSonNumeros(this.tokenProvisional)){
            
        }else this.imprimirError("número o identificador");

    }
    
    private void auxTipo() {
        //<AuxTipo> ::= <Tipo>  <Identificador> <AuxI2>
        tipo();
        this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(AnalizadorLexico.expresionEsIdentificador(this.tokenProvisional)){//????
            auxI2();
        }else this.imprimirError("identificador");
    }
    
    private void auxI2() {
        /* <AuxI2> ::= ~ <Identificador> <AuxI2>
            <AuxI2> ::= . */
        this.tokenProvisional="~";//AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("~")){
            this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
            if(AnalizadorLexico.expresionEsIdentificador(this.tokenProvisional)){//????
                auxI2();
            }else this.imprimirError("un identificador");
        }else this.imprimirErrorSimbolo("~");
    }
    private void auxFoncion() {
        //<AuxFoncion>::= FONCION <Tipo> <Identificador> [ ] { <Inicialización> <Instrucciones> <AuxInst> } <AuxFoncion>
        //< AuxFoncion> ::= ԑ
        this.tokenProvisional="FONCION";//AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("FONCION")){
            tipo();
            this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
            if(AnalizadorLexico.expresionEsIdentificador(this.tokenProvisional)){//????
                   this.tokenProvisional="["; //AQUI SE MANDA LLAMAR AL LEXICO
                   if(this.tokenProvisional.equals("[")){
                        this.tokenProvisional="["; //AQUI SE MANDA LLAMAR AL LEXICO
                        if(this.tokenProvisional.equals("]")){
                            this.tokenProvisional="{"; //AQUI SE MANDA LLAMAR AL LEXICO
                            if(this.tokenProvisional.equals("{")){
                                 inicializacion();
                                 instrucciones();
                                 auxInst();
                                 if(this.tokenProvisional.equals("}")){ //AQUI SE MANDA LLAMAR AL LEXICO
                                     auxFoncion();
                                 }else this.imprimirErrorSimbolo("}");
                            }else this.imprimirErrorSimbolo("}");
                        }else this.imprimirErrorSimbolo("]");
                   }else this.imprimirErrorSimbolo("[");
            }else this.imprimirError("un identificador");
        }else this.imprimirErrorPalabra("FONCION");
    }
    
    private void auxIn0(){
        //{ <Instrucciones> <AuxInst> }
        this.tokenProvisional="{"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("{")){
             instrucciones();
             auxInst();
             this.tokenProvisional="}"; //AQUI SE MANDA LLAMAR AL LEXICO
             if(this.tokenProvisional.equals("}")){ //AQUI SE MANDA LLAMAR AL LEXICO
                 //FALTA
             }else this.imprimirErrorSimbolo("}");
        }else this.imprimirErrorSimbolo("{");
    }
    
    private void instrucciones() {
        //<Identificador> : <AuxI0> <AuxI1>
        this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(AnalizadorLexico.expresionEsIdentificador(this.tokenProvisional)){//????
            this.tokenProvisional=":";  //AQUI SE MANDA LLAMAR AL LEXICO
            if(this.tokenProvisional.equals(":")){ //AQUI SE MANDA LLAMAR AL LEXICO
                auxI0();
                auxI1();
            }else this.imprimirErrorSimbolo(":");
        }else if(this.tokenProvisional.equals("SI")){
            //SI <AuxCon> <AuxIn0> <AuxSi1> <AuxSi2>
            auxCon();
            auxIn0();
            auxSi1();
            auxSi2();
        }else if(this.tokenProvisional.equals("POUR")){
            //POUR ( <Identificador> : <AuxNI> . <Identificador> <SimRel> <AuxNI> .  <Identificador> : <identificador> <AuxPo1> <AuxNI>  ) <AuxIn0>

            this.tokenProvisional="(";  //AQUI SE MANDA LLAMAR AL LEXICO
            if(this.tokenProvisional.equals("(")){
                this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
                if(AnalizadorLexico.expresionSonNumeros(this.tokenProvisional)){
                    this.tokenProvisional=":";  //AQUI SE MANDA LLAMAR AL LEXICO
                    if(this.tokenProvisional.equals(":")){
                        auxNI();
                        this.tokenProvisional=".";  //AQUI SE MANDA LLAMAR AL LEXICO
                        if(this.tokenProvisional.equals(".")){
                            this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
                             if(AnalizadorLexico.expresionSonNumeros(this.tokenProvisional)){
                                 simRel();
                                 auxNI();
                                 this.tokenProvisional=".";  //AQUI SE MANDA LLAMAR AL LEXICO
                                  if(this.tokenProvisional.equals(".")){
                                        this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
                                        if(AnalizadorLexico.expresionSonNumeros(this.tokenProvisional)){
                                            this.tokenProvisional=":";  //AQUI SE MANDA LLAMAR AL LEXICO
                                            if(this.tokenProvisional.equals(":")){
                                                this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
                                                if(AnalizadorLexico.expresionSonNumeros(this.tokenProvisional)){
                                                    auxPo1();
                                                    auxNI();
                                                    this.tokenProvisional=")";  //AQUI SE MANDA LLAMAR AL LEXICO
                                                    if(this.tokenProvisional.equals(")")){
                                                        auxIn0();
                                                    }else this.imprimirErrorSimbolo(")");
                                                }else this.imprimirError("un identificador");
                                            }else this.imprimirErrorSimbolo(":");
                                        }else this.imprimirError("un identificador");
                                   }else this.imprimirErrorSimbolo(".");
                             }else this.imprimirError("un identificador");
                       }else this.imprimirErrorSimbolo(".");
                    }else this.imprimirErrorSimbolo(":");
                }else this.imprimirError("un identificador");
            }else this.imprimirErrorSimbolo("(");
        }else if(this.tokenProvisional.equals("TANDISQUE")){
            //<AuxCon> <AuxIns0>
            auxCon();
            auxIn0();
        }else if(this.tokenProvisional.equals("FAIRE")){
            //<AuxIn0> TANDISQUE <AuxCon> .
            auxIn0();
            this.tokenProvisional="TANDISQUE";
            if(this.tokenProvisional.equals("TANDISQUE")){
                auxCon();
            }else this.imprimirErrorPalabra("TANDISQUE");
        }else this.imprimirError("un identificador o \"SI\" o \"POUR\" o \"TANDISQUE\" o \"FAIRE\"");
    }
    
    private void auxI0(){
        //<AuxI0>::=<Numero>
        //<AuxI0>::=<Identificador> <AuxI01>
        this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(AnalizadorLexico.expresionSonNumeros(this.tokenProvisional)){
            
        }else if(AnalizadorLexico.expresionEsIdentificador(this.tokenProvisional)){//????
            auxI01();
        }else this.imprimirError("un número o identificador");
    }
    
    private void auxI01(){
        //<AuxI01>::=ɛ
        //<AuxI01>::= [ ]
        this.tokenProvisional="[";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("[")){
             this.tokenProvisional="]"; //AQUI SE MANDA LLAMAR AL LEXICO
             if(this.tokenProvisional.equals("]")){ //AQUI SE MANDA LLAMAR AL LEXICO
                 //FALTA
             }else this.imprimirErrorSimbolo("]");
        }else this.imprimirErrorSimbolo("[");
    }
    
    private void auxI1(){
        //<AuxI1>::= .
        //<AuxI1>::= <AuxI2> <AuxNI> <AuxI1>
        this.tokenProvisional=".";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals(".")){
            //FALTA
        }else{
            auxI2();
            auxNI();
            auxI1();
        }
    }
    
    private void auxI12(){
        /*<AuxI2>::= *
        <AuxI2>::= -
        <AuxI2>::= +
        <AuxI2>::= /
        <AuxI2>::= ^
        <AuxI2>::= %
        */
        this.tokenProvisional=".";  //AQUI SE MANDA LLAMAR AL LEXICO
        switch(this.tokenProvisional){
            case "*":
                break;
            case "-":
                break;
            case "+":
                break;
            case "/":
                break;
            case "^":
                break;
            case "%":
                break;
            default : this.imprimirError("un simbolo aritmetico");
        }
    }
    
    private void auxSi1(){
        //<AuxSi1>::= Ɛ
        //<AuxSi1>::= SIONSI <AuxCon>  <AuxIn0> <AuxSi1>
        this.tokenProvisional = "SIONSI"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("SIONSI")){
            auxCon();
            auxIn0();
            auxSi1();
        }else this.imprimirErrorPalabra("SIONSI");
    }
    private void auxSi2(){
        //<AuxSi2>::=AUTREI <AuxInt0>
        //<AuxSi2>::= Ɛ
        this.tokenProvisional = "AUTREI"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("AUTREI")){
            auxIn0();
        }else this.imprimirErrorPalabra("AUTREI");
    }
    private void auxPo1(){
        this.tokenProvisional = "+"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("+")){
            //FALTA
        }else if(this.tokenProvisional.equals("-")){
            //FALTA
        }else this.imprimirErrorPalabra("+ o -");
    }
    
    private void auxCon(){
        //<AuxCon>::=( <Condición> )
         this.tokenProvisional = "("; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("(")){
            condicion();
            this.tokenProvisional = "("; //AQUI SE MANDA LLAMAR AL LEXICO
            if(this.tokenProvisional.equals(")")){
                    //FALTA
            }else this.imprimirErrorSimbolo(")");
        }else this.imprimirErrorSimbolo("(");
    }
    
    private void condicion(){
        //<Condición>::= ¬ <AuxCon> <AuxC3>
        //<Condición>::= <AuxNI> <SimRel> <AuxNI> <AuxC3>
        this.tokenProvisional = "¬"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("¬")){
            auxCon();
            auxC3();
        }else{
           auxNI();
           simRel();
           auxNI();
           auxC3();
        }
    }
    private void auxC2(){
        this.tokenProvisional = "&"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("&")){
            //FALTA
        }else if(this.tokenProvisional.equals("°")){
            //FALTA
        }else this.imprimirErrorSimbolo("& o °");
    }
    
    private void auxC3(){
        //<AuxC3>::= <AuxC2><AuxCon><AuxC3>
        //<AuxC3>::= Ɛ
        auxC2();
        auxCon();
        auxC3();
    }
    private void simRel(){
        /*<SimRel>::= ==
<SimRel>::= !=
<SimRel>::= <<
<SimRel>::= >>
<SimRel>::= <=
<SimRel>::= >=
*/
        this.tokenProvisional = "==";//AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("==")){
            
        }else if(this.tokenProvisional.equals("!=")){
            
        }else if(this.tokenProvisional.equals("<<")){
            
        }else if(this.tokenProvisional.equals(">>")){
            
        }else if(this.tokenProvisional.equals("<=")){
            
        }else if(this.tokenProvisional.equals(">=")){
            
        }else this.imprimirError("un simbolo relacional");
    }
    private void tipo(){
        /*<Tipo>::= DEC
<Tipo>::= CARAC
<Tipo>::= CHAINE
<Tipo>::= ENT
*/
       this.tokenProvisional = "DEC";//AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("DEC")){
            
        }else if(this.tokenProvisional.equals("CARAC")){
            
        }else if(this.tokenProvisional.equals("CHAINE")){
            
        }else if(this.tokenProvisional.equals("ENT")){
            
        }else this.imprimirError("un tipo de dato");
    }
    private void imprimirErrorPalabra(String palabraEsperada){
        System.out.println("Se esperaba la palabra: " + palabraEsperada);
    }
    private void imprimirErrorSimbolo(String simboloEsperada){
        System.out.println("Se esperaba el símbolo: " + simboloEsperada);
    }
    private void imprimirError(String esperaba){
        System.out.println("Se esperaba: " + esperaba);
    }
    private void compilacionExitosa() {
        System.out.println("Se logró una compilación exitosa");
    }
}
