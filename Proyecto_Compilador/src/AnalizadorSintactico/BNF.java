/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalizadorSintactico;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.LectorTxt;

/**
 *
 * @author Vanessa
 */
//Cada regla (no terminal) BNF es un método
//Si encontramos un terminal, es validar si es ese elemento el que está
public class BNF {
    private String token;
    
    private String siguienteToken(){
        String token = LectorTxt.enviarSiguienteToken();
    }
    private void programa(){
        //INICIO <Inicialización> <Instrucciones> <MásInstrucciones> FIN
                //Se manda llamar a la palabra del archivo porque se espera un terminal "INICIO"
        this.token = "INICIO"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.token.equals("INICIO")){
            inicializacion();
            instrucciones();
            masInstrucciones();
                this.token = "FIN"; //AQUI SE MANDA LLAMAR AL LEXICO
                if(this.token.equals("FIN")){
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
        this.token = "."; //AQUI SE MANDA LLAMAR AL LEXICO
        
        String x = token(); //se tiene que agregar funcionalidad -----------------
        
        if(!this.token.equals("FIN") || !this.token.equals("}")){
            if(this.token.equals(".")){
                instrucciones();
                masInstrucciones(); // <- se manda llamar a si, después se arregla eso
            }else this.imprimirErrorSimbolo(".");        
        }
        else{
            //Retroceder al token anterior.
        }
    }

    private void constantes() {
        //<Constantes> ::= CONS <EstructuraConstantes>.
        this.token = "CONS";  //AQUI SE MANDA LLAMAR AL LEXICO
        
        if(!token.equals("DEC") || !token.equals("CARAC") || !token.equals("CHAINE") || !token.equals("ENT")){
            if(this.token.equals("CONS")){
                estructuraConstantes();
                this.token = ".";  //AQUI SE MANDA LLAMAR AL LEXICO
                if(this.token.equals(".")){
                    //FALTA
                }else this.imprimirErrorSimbolo(".");
            }else this.imprimirErrorPalabra("CONS");
        }
        else{
            //Retroceder
        }
    }

    private void masConstantes() {
       //<MasConstantes> ::= ~ <EstructuraConstantes>
       //<MasConstantes> ::= ɛ
        this.token = "~"; //AQUI SE MANDA LLAMAR AL LEXICO
        
        if(!token.equals(".")){
        if(this.token.equals("~")){
            estructuraConstantes();
        }else this.imprimirErrorSimbolo("~"); 
        }else{
            //Retroceder
        }
    }
    private void estructuraConstantes() {
        //<EstructuraConstantes> ::= <AsignarIdentificador> <NumeroIdentificador>  <MasConstantes>
        asignarIdentificador();
        numeroIdentificador();
        masConstantes();
    }

    private void asignarIdentificador() {
        //<AsignarIdentificador> ::= <Identificador> :
        this.token="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(AnalizadorLexico.expresionSonNumeros(this.token)){
                this.token=":";  //AQUI SE MANDA LLAMAR AL LEXICO
            if(this.token.equals(":")){
                //FALTA
            }else this.imprimirErrorSimbolo(":");
        }else this.imprimirError("un identificador");
    }
    
    private void numeroIdentificador() {
        /*<AuxNI> ::= <Identificador>
        <AuxNI> ::= <Numero>*/
        this.token="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(AnalizadorLexico.expresionEsIdentificador(this.token)){//????
            
        }else if(AnalizadorLexico.expresionSonNumeros(this.token)){
            
        }else this.imprimirError("número o identificador");

    }
    
    private void variables() {
        //<Variables> ::= <TipoDato>  <Identificador> <MasVariables>
        //<Variables > ::= ԑ
        tipoDato();
        
        if(!token.equals("FONCION") || !token.equals("SI") || !token.equals("POUR") || !token.equals("TANDISQUE") || !token.equals("FAIRE")){
            this.token="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
            if(AnalizadorLexico.expresionEsIdentificador(this.token)){//????
                masVariables();
            }else this.imprimirError("identificador");  
        }else{
            //retroceder
        }
    }
    
    private void masVariables() {
        /* <AuxI2> ::= ~ <Identificador> <AuxI2>
            <AuxI2> ::= . */
        this.token="~";//AQUI SE MANDA LLAMAR AL LEXICO
        if(this.token.equals("~")){
            this.token="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
            if(AnalizadorLexico.expresionEsIdentificador(this.token)){//????
                masVariables();
            }else this.imprimirError("un identificador");
        }else this.imprimirErrorSimbolo("~");
    }
    private void funciones() {
        //<Funciones>::= FONCION <TipoDato> <Identificador> [ ] { <Inicialización> <Instrucciones> <MásInstrucciones>} <Funciones>
        //< Funciones> ::= ԑ
        
        this.token="FONCION";//AQUI SE MANDA LLAMAR AL LEXICO
        
        if(!token.equals("SI") || !token.equals("POUR") || !token.equals("TANDISQUE") || !token.equals("FAIRE")){
            if(this.token.equals("FONCION")){
                tipoDato();
                this.token="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
                if(AnalizadorLexico.expresionEsIdentificador(this.token)){//????
                    corchetes();
                    this.token="{"; //AQUI SE MANDA LLAMAR AL LEXICO
                    if(this.token.equals("{")){
                        inicializacion();
                        instrucciones();
                        masInstrucciones();
                        if(this.token.equals("}")){ //AQUI SE MANDA LLAMAR AL LEXICO
                            funciones();
                        }else this.imprimirErrorSimbolo("}");
                    }else this.imprimirErrorSimbolo("}");
                }else this.imprimirError("un identificador");
            }else this.imprimirErrorPalabra("FONCION");
        }else{
            //retroceder.
        }
    }
    
    private void instruccionesConCorchetes(){
        //{ <Instrucciones> <MásInstrucciones> }
        this.token="{"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.token.equals("{")){
             instrucciones();
             masInstrucciones();
             this.token="}"; //AQUI SE MANDA LLAMAR AL LEXICO
             if(this.token.equals("}")){ //AQUI SE MANDA LLAMAR AL LEXICO
                 //FALTA
             }else this.imprimirErrorSimbolo("}");
        }else this.imprimirErrorSimbolo("{");
    }
    
    private void instrucciones() {
        this.token="palabra reservada";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.token.equals("SI")){
            estructuraSI();
        }else if(this.token.equals("POUR")){
            estructuraPOUR(); 
        }else if(this.token.equals("TANDISQUE")){
            //<Instrucciones>::= TANDISQUE <CondicionesConParentesis> <InstruccionesConCorchetes>
            condicionesConParentesis();
            instruccionesConCorchetes();
        }else if(this.token.equals("FAIRE")){
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
        this.token="(";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.token.equals("(")){
            asignarIdentificador();
            numeroIdentificador();
            this.token=".";  //AQUI SE MANDA LLAMAR AL LEXICO
            if(this.token.equals(".")){
                this.token="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
                 if(AnalizadorLexico.expresionSonNumeros(this.token)){
                     simbolosRelacionales();
                     numeroIdentificador();
                     this.token=".";  //AQUI SE MANDA LLAMAR AL LEXICO
                      if(this.token.equals(".")){
                            this.token="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
                            asignarIdentificador();
                            this.token="identificador";  //AQUI SE MANDA LLAMAR AL LEXICO
                            if(AnalizadorLexico.expresionSonNumeros(this.token)){
                                sumaResta();
                                numeroIdentificador();
                                this.token=")";  //AQUI SE MANDA LLAMAR AL LEXICO
                                if(this.token.equals(")")){
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
        this.token="TANDISQUE";
        if(this.token.equals("TANDISQUE")){
            condicionesConParentesis();
        }else this.imprimirErrorPalabra("TANDISQUE");
    }
    private void numeroIdentificador_LlamarFuncion(){
        /*
        <NumeroIdentificador_LlamarFuncion>::=<Numero>
        <NumeroIdentificador_LlamarFuncion>::=<Identificador> <Corchetes_Vacion>
        */
        this.token="identificador o numero";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(AnalizadorLexico.expresionSonNumeros(this.token)){
            //FALTA
        }else if(AnalizadorLexico.expresionEsIdentificador(this.token)){//????
            corchetes_Vacio();
        }else this.imprimirError("un número o identificador");
    }
    
    private void corchetes_Vacio(){
        /*<Corchetes_Vacion>::=ɛ
        <Corchetes_Vacion>::= [ ]
        */
        this.token="[";  //AQUI SE MANDA LLAMAR AL LEXICO
        
        if(!token.equals(".") || !token.equals("*") || !token.equals("-") || !token.equals("+") || !token.equals("/") || !token.equals("^") || !token.equals("%")){
            if(this.token.equals("[")){
                 this.token="]"; //AQUI SE MANDA LLAMAR AL LEXICO
                 if(this.token.equals("]")){ //AQUI SE MANDA LLAMAR AL LEXICO
                     //FALTA
                 }else this.imprimirErrorSimbolo("]");
            }else this.imprimirErrorSimbolo("[");
        }else{
            //retroceder
        }
    }
    
    private void corchetes(){
        /*
        <Corchetes>::= [ ]
        */
        this.token="[";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.token.equals("[")){
             this.token="]"; //AQUI SE MANDA LLAMAR AL LEXICO
             if(this.token.equals("]")){ //AQUI SE MANDA LLAMAR AL LEXICO
                 //FALTA
             }else this.imprimirErrorSimbolo("]");
        }else this.imprimirErrorSimbolo("[");
    }
    
    private void operacionesAritmeticas(){
        /*<OperacionesAritmeticas>::= .
        <OperacionesAritmeticas>::= <SimbolosAritmeticos> <NumeroIdentificador> <OperacionesAritmeticas>
        */
        this.token=".";  //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.token.equals(".")){
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
        this.token=".";  //AQUI SE MANDA LLAMAR AL LEXICO
        switch(this.token){
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
        this.token = "SIONSI"; //AQUI SE MANDA LLAMAR AL LEXICO
        
        if(!token.equals("AUTREI") || !token.equals("")){
            if(this.token.equals("SIONSI")){
                condicionesConParentesis();
                instruccionesConCorchetes();
                siAnidado();
            }else this.imprimirErrorPalabra("SIONSI");
        }else{
            //Retroceder.
        }
    }
    private void finalSi(){
        /*<FinalSi>::=AUTREI <InstruccionesConCorchetes>
        <FinalSi>::= Ɛ
        */
        this.token = "AUTREI"; //AQUI SE MANDA LLAMAR AL LEXICO
        
        if(!token.equals(".") || !token.equals("")){
            if(this.token.equals("AUTREI")){
                instruccionesConCorchetes();
            }else this.imprimirErrorPalabra("AUTREI");
        }else{
            //retroceder.
        }
    }
    private void sumaResta(){
        /*<SumaResta>::= -
        <SumaResta>::= +
        */
        this.token = "+"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.token.equals("+")){
            //FALTA
        }else if(this.token.equals("-")){
            //FALTA
        }else this.imprimirErrorPalabra("+ o -");
    }
    
    private void condicionesConParentesis(){
        //<CondicionesConParentesis>::=( <Condición> )
         this.token = "("; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.token.equals("(")){
            condicion();
            this.token = "("; //AQUI SE MANDA LLAMAR AL LEXICO
            if(this.token.equals(")")){
                    //FALTA
            }else this.imprimirErrorSimbolo(")");
        }else this.imprimirErrorSimbolo("(");
    }
    
    private void condicion(){
        /*<Condición>::= ¬ <CondicionesConParentesis> <MasCondiciones>
        <Condición>::= <NumeroIdentificador> <SimbolosRelacionales> <NumeroIdentificador> <MasCondiciones>
        */
        this.token = "¬"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.token.equals("¬")){
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
        this.token = "&"; //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.token.equals("&")){
            //FALTA
        }else if(this.token.equals("°")){
            //FALTA
        }else this.imprimirErrorSimbolo("& o °");
    }
    
    private void masCondiciones(){
        /*<MasCondiciones>::= <AndOr><CondicionesConParentesis><MasCondiciones>
        <MasCondiciones>::= Ɛ
        */
        if(!token.equals("¬")){
            andOr();
            condicionesConParentesis();
            masCondiciones();
        }else{
            //retroceder.
        }
            
    }
    private void simbolosRelacionales(){
       /*<SimbolosRelacionales>::= ==
        <SimbolosRelacionales>::= !=
        <SimbolosRelacionales>::= <<
        <SimbolosRelacionales>::= >>
        <SimbolosRelacionales>::= <=
        <SimbolosRelacionales>::= >=
        */
        this.token = "==";//AQUI SE MANDA LLAMAR AL LEXICO
        switch(this.token){
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
       this.token = "DEC";//AQUI SE MANDA LLAMAR AL LEXICO
        if(this.token.equals("DEC")){
            
        }else if(this.token.equals("CARAC")){
            
        }else if(this.token.equals("CHAINE")){
            
        }else if(this.token.equals("ENT")){
            
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

    private String token() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
