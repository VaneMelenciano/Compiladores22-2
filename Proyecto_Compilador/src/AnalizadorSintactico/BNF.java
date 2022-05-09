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
        //INICIO <Inicialización> <Instrucciones> <MásInstrucciones> FIN
                //Se manda llamar a la palabra del archivo porque se espera un terminal "INICIO"
        this.tokenProvisional = "INICIO"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("INICIO")){
            inicializacion();
            instrucciones();
            masInstrucciones();
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
    
    private void inicializacion() {
        //<Inicialización>::= <Constantes> <Variables> <Funciones>
        constantes();
        variables();
        funciones();
    }

    private void masInstrucciones() {
        //<MásInstrucciones> ::= . <Instrucciones> <MásInstrucciones>
        this.tokenProvisional = "."; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals(".")){
            instrucciones();
            masInstrucciones(); // <- se manda llamar a si, después se arregla eso
        }else this.imprimirErrorSimbolo(".");
        //ԑ <- después se programa
    }

    private void constantes() {
        //<Constantes> ::= CONS <EstructuraConstantes>.
        this.tokenProvisional = "CONS";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("CONS")){
            estructuraConstantes();
            this.tokenProvisional = ".";  //AQUI SE MANDA LLAMAR AL LEXICO
            if(this.tokenProvisional.equals(".")){
                //FALTA
            }else this.imprimirErrorSimbolo(".");
        }else this.imprimirErrorPalabra("CONS");
         //ԑ <- después se programa
    }

    private void masConstantes() {
       //<MasConstantes> ::= ~ <EstructuraConstantes>
       //<MasConstantes> ::= ɛ
        this.tokenProvisional = "~"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("~")){
            estructuraConstantes();
        }else this.imprimirErrorSimbolo("~");
    }
    private void estructuraConstantes() {
        //<EstructuraConstantes> ::= <AsignarIdentificador> <NumeroIdentificador>  <MasConstantes>
        asignarIdentificador();
        numeroIdentificador();
        masConstantes();
    }

    private void asignarIdentificador() {
        //<AsignarIdentificador> ::= <Identificador> :
        this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(AnalizadorLexico.expresionSonNumeros(this.tokenProvisional)){
                this.tokenProvisional=":";  //AQUI SE MANDA LLAMAR AL LEXICO
            if(this.tokenProvisional.equals(":")){
                //FALTA
            }else this.imprimirErrorSimbolo(":");
        }else this.imprimirError("un identificador");
    }
    
    private void numeroIdentificador() {
        /*<AuxNI> ::= <Identificador>
        <AuxNI> ::= <Numero>*/
        this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(AnalizadorLexico.expresionEsIdentificador(this.tokenProvisional)){//????
            
        }else if(AnalizadorLexico.expresionSonNumeros(this.tokenProvisional)){
            
        }else this.imprimirError("número o identificador");

    }
    
    private void variables() {
        //<Variables> ::= <TipoDato>  <Identificador> <MasVariables>
        //<Variables > ::= ԑ

        tipoDato();
        this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(AnalizadorLexico.expresionEsIdentificador(this.tokenProvisional)){//????
            masVariables();
        }else this.imprimirError("identificador");
    }
    
    private void masVariables() {
        /* <AuxI2> ::= ~ <Identificador> <AuxI2>
            <AuxI2> ::= . */
        this.tokenProvisional="~";//AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("~")){
            this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
            if(AnalizadorLexico.expresionEsIdentificador(this.tokenProvisional)){//????
                masVariables();
            }else this.imprimirError("un identificador");
        }else this.imprimirErrorSimbolo("~");
    }
    private void funciones() {
        //<Funciones>::= FONCION <TipoDato> <Identificador> [ ] { <Inicialización> <Instrucciones> <MásInstrucciones>} <Funciones>
        //< Funciones> ::= ԑ
        
        this.tokenProvisional="FONCION";//AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("FONCION")){
            tipoDato();
            this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
            if(AnalizadorLexico.expresionEsIdentificador(this.tokenProvisional)){//????
                corchetes();
                this.tokenProvisional="{"; //AQUI SE MANDA LLAMAR AL LEXICO
                if(this.tokenProvisional.equals("{")){
                     inicializacion();
                     instrucciones();
                     masInstrucciones();
                     if(this.tokenProvisional.equals("}")){ //AQUI SE MANDA LLAMAR AL LEXICO
                         funciones();
                     }else this.imprimirErrorSimbolo("}");
                }else this.imprimirErrorSimbolo("}");
            }else this.imprimirError("un identificador");
        }else this.imprimirErrorPalabra("FONCION");
    }
    
    private void instruccionesConCorchetes(){
        //{ <Instrucciones> <MásInstrucciones> }
        this.tokenProvisional="{"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("{")){
             instrucciones();
             masInstrucciones();
             this.tokenProvisional="}"; //AQUI SE MANDA LLAMAR AL LEXICO
             if(this.tokenProvisional.equals("}")){ //AQUI SE MANDA LLAMAR AL LEXICO
                 //FALTA
             }else this.imprimirErrorSimbolo("}");
        }else this.imprimirErrorSimbolo("{");
    }
    
    private void instrucciones() {
        this.tokenProvisional="palabra reservada";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("SI")){
            estructuraSI();
        }else if(this.tokenProvisional.equals("POUR")){
            estructuraPOUR(); 
        }else if(this.tokenProvisional.equals("TANDISQUE")){
            //<Instrucciones>::= TANDISQUE <CondicionesConParentesis> <InstruccionesConCorchetes>
            condicionesConParentesis();
            instruccionesConCorchetes();
        }else if(this.tokenProvisional.equals("FAIRE")){
            estructuraFAIRE();
        }else{
            /*<Instrucciones>::= <AsignarIdentificador> <NumeroIdentificador_LlamarFuncion> <OperacionesAritmeticas>*/
            asignarIdentificador();
            numeroIdentificador_LlamarFuncion();
            operacionesAritmeticas();
        }
     }
    
    private void estructuraPOUR(){
        /*<EstructuraPOUR> ::= ( <AsignarIdentificador> <NumeroIdentificador> . <Identificador> 
        <SimbolosRelacionales> <NumeroIdentificador> . <AsignarIdentificador><identificador> 
        <SumaResta> <NumeroIdentificador>  ) <InstruccionesConCorchetes>*/
        this.tokenProvisional="(";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("(")){
            asignarIdentificador();
            numeroIdentificador();
            this.tokenProvisional=".";  //AQUI SE MANDA LLAMAR AL LEXICO
            if(this.tokenProvisional.equals(".")){
                this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
                 if(AnalizadorLexico.expresionSonNumeros(this.tokenProvisional)){
                     simbolosRelacionales();
                     numeroIdentificador();
                     this.tokenProvisional=".";  //AQUI SE MANDA LLAMAR AL LEXICO
                      if(this.tokenProvisional.equals(".")){
                            this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
                            asignarIdentificador();
                            this.tokenProvisional="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
                            if(AnalizadorLexico.expresionSonNumeros(this.tokenProvisional)){
                                sumaResta();
                                numeroIdentificador();
                                this.tokenProvisional=")";  //AQUI SE MANDA LLAMAR AL LEXICO
                                if(this.tokenProvisional.equals(")")){
                                    instruccionesConCorchetes();
                                }else this.imprimirErrorSimbolo(")");
                            }else this.imprimirError("un identificador");
                       }else this.imprimirErrorSimbolo(".");
                 }else this.imprimirError("un identificador");
           }else this.imprimirErrorSimbolo(".");
        }else this.imprimirErrorSimbolo("(");
    }
    private void estructuraFAIRE(){
        //<EstructuraFAIRE>::=<InstruccionesConCorchetes> TANDISQUE <CondicionesConParentesis> .
        instruccionesConCorchetes();
        this.tokenProvisional="TANDISQUE";
        if(this.tokenProvisional.equals("TANDISQUE")){
            condicionesConParentesis();
        }else this.imprimirErrorPalabra("TANDISQUE");
    }
    private void numeroIdentificador_LlamarFuncion(){
        /*
        <NumeroIdentificador_LlamarFuncion>::=<Numero>
        <NumeroIdentificador_LlamarFuncion>::=<Identificador> <Corchetes_Vacion>
        */
        this.tokenProvisional="identificador o numero";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(AnalizadorLexico.expresionSonNumeros(this.tokenProvisional)){
            //FALTA
        }else if(AnalizadorLexico.expresionEsIdentificador(this.tokenProvisional)){//????
            corchetes_Vacio();
        }else this.imprimirError("un número o identificador");
    }
    
    private void corchetes_Vacio(){
        /*<Corchetes_Vacion>::=ɛ
        <Corchetes_Vacion>::= [ ]
        */
        this.tokenProvisional="[";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("[")){
             this.tokenProvisional="]"; //AQUI SE MANDA LLAMAR AL LEXICO
             if(this.tokenProvisional.equals("]")){ //AQUI SE MANDA LLAMAR AL LEXICO
                 //FALTA
             }else this.imprimirErrorSimbolo("]");
        }else this.imprimirErrorSimbolo("[");
    }
    
    private void corchetes(){
        /*
        <Corchetes>::= [ ]
        */
        this.tokenProvisional="[";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("[")){
             this.tokenProvisional="]"; //AQUI SE MANDA LLAMAR AL LEXICO
             if(this.tokenProvisional.equals("]")){ //AQUI SE MANDA LLAMAR AL LEXICO
                 //FALTA
             }else this.imprimirErrorSimbolo("]");
        }else this.imprimirErrorSimbolo("[");
    }
    
    private void operacionesAritmeticas(){
        /*<OperacionesAritmeticas>::= .
        <OperacionesAritmeticas>::= <SimbolosAritmeticos> <NumeroIdentificador> <OperacionesAritmeticas>
        */
        this.tokenProvisional=".";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals(".")){
            //FALTA
        }else{
            simbolosAritmeticos();
            numeroIdentificador();
            operacionesAritmeticas();
        }
    }
    
    private void simbolosAritmeticos(){
        /*<SimbolosAritmeticos>::= *
        <SimbolosAritmeticos>::= -
        <SimbolosAritmeticos>::= +
        <SimbolosAritmeticos>::= /
        <SimbolosAritmeticos>::= ^
        <SimbolosAritmeticos>::= %*/
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
    
    private void estructuraSI(){
        /*<EstructuraSI>::=<CondicionesConParentesis> <InstruccionesConCorchetes> <SiAnidado> <FinalSi>*/
        condicionesConParentesis();
        instruccionesConCorchetes();
        siAnidado();
        finalSi();
    }
    
    private void siAnidado(){
        /*<SiAnidado>::= Ɛ
        <SiAnidado>::= SIONSI <CondicionesConParentesis>  <InstruccionesConCorchetes> <SiAnidado>
        */
        this.tokenProvisional = "SIONSI"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("SIONSI")){
            condicionesConParentesis();
            instruccionesConCorchetes();
            siAnidado();
        }else this.imprimirErrorPalabra("SIONSI");
    }
    private void finalSi(){
        /*<FinalSi>::=AUTREI <InstruccionesConCorchetes>
        <FinalSi>::= Ɛ
        */
        this.tokenProvisional = "AUTREI"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("AUTREI")){
            instruccionesConCorchetes();
        }else this.imprimirErrorPalabra("AUTREI");
    }
    private void sumaResta(){
        /*<SumaResta>::= -
        <SumaResta>::= +
        */
        this.tokenProvisional = "+"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("+")){
            //FALTA
        }else if(this.tokenProvisional.equals("-")){
            //FALTA
        }else this.imprimirErrorPalabra("+ o -");
    }
    
    private void condicionesConParentesis(){
        //<CondicionesConParentesis>::=( <Condición> )
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
        /*<Condición>::= ¬ <CondicionesConParentesis> <MasCondiciones>
        <Condición>::= <NumeroIdentificador> <SimbolosRelacionales> <NumeroIdentificador> <MasCondiciones>
        */
        this.tokenProvisional = "¬"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("¬")){
            condicionesConParentesis();
            masCondiciones();
        }else{
           numeroIdentificador();
           simbolosRelacionales();
           numeroIdentificador();
           masCondiciones();
        }
    }
    private void andOr(){
        /*<AndOr>::= &
        <AndOr>::= °
        */
        this.tokenProvisional = "&"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.tokenProvisional.equals("&")){
            //FALTA
        }else if(this.tokenProvisional.equals("°")){
            //FALTA
        }else this.imprimirErrorSimbolo("& o °");
    }
    
    private void masCondiciones(){
        /*<MasCondiciones>::= <AndOr><CondicionesConParentesis><MasCondiciones>
        <MasCondiciones>::= Ɛ
        */
        andOr();
        condicionesConParentesis();
        masCondiciones();
    }
    private void simbolosRelacionales(){
       /*<SimbolosRelacionales>::= ==
        <SimbolosRelacionales>::= !=
        <SimbolosRelacionales>::= <<
        <SimbolosRelacionales>::= >>
        <SimbolosRelacionales>::= <=
        <SimbolosRelacionales>::= >=
        */
        this.tokenProvisional = "==";//AQUI SE MANDA LLAMAR AL LEXICO
        switch(this.tokenProvisional){
            case "==":
                break;
            case "!=":
                break;
            case "<<":
                break;
            case ">>":
                break;
            case "<=":
                break;
            case ">=":
                break;
            default : this.imprimirError("un simbolo relacional");
        }
    }
    private void tipoDato(){
        /*<TipoDato>::= DEC
        <TipoDato>::= CARAC
        <TipoDato>::= CHAINE
        <TipoDato>::= ENT
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
