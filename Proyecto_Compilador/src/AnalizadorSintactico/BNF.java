/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalizadorSintactico;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.LectorTxt;
import java.util.ArrayList;

/**
 *
 * @author Vanessa
 */
//Cada regla (no terminal) BNF es un método
//Si encontramos un terminal, es validar si es ese elemento el que está
public class BNF {
    private String token;
    private ArrayList<String> codigo;   
    private int posicionEnCodigo;
    public BNF(){
        this.codigo = AnalizadorLexico.getListaFiltrada();
        this.posicionEnCodigo = 0;
        programa();
    }
    private void programa(){
        //INICIO <Inicialización> <Instrucciones> <MásInstrucciones> FIN
                //Se manda llamar a la palabra del archivo porque se espera un terminal "INICIO"
        if(this.codigo.get(posicionEnCodigo).equals("INICIO")){
            System.out.println("1");
            inicializacion();
            System.out.println("2");
            instrucciones();
            System.out.println("3");
            masInstrucciones();
            System.out.println("4");
            
            this.token = this.codigo.get(++posicionEnCodigo);    
            System.out.println("ñ: " + this.token + this.posicionEnCodigo);
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
        System.out.println("1.1");
        constantes();
        System.out.println(this.token + " " + this.posicionEnCodigo);
        System.out.println("1.2");        
        variables();
        System.out.println(this.token + " " + this.posicionEnCodigo);
        System.out.println("1.3"); 
        funciones();
        System.out.println(this.token + " " + this.posicionEnCodigo);
        System.out.println("1.4"); 
    }

    private void inicializacionFunciona(){
        System.out.println("#1" + this.token + " " + this.posicionEnCodigo);
        constantes();
        System.out.println("#2" + this.token + " " + this.posicionEnCodigo);
        variables();
        System.out.println("#3" + this.token + " " + this.posicionEnCodigo);
    }
    private void masInstrucciones() {
        //<MásInstrucciones> ::= <Instrucciones> <MásInstrucciones>
        this.token = this.codigo.get(++posicionEnCodigo);
        if(!(this.token.equals("FIN") || this.token.equals("}"))){
                instrucciones();
                masInstrucciones(); 
        }
        else{
            posicionEnCodigo--;
        }
    }

    private void constantes() {
        //<Constantes> ::= CONS <EstructuraConstantes>.
        this.token = this.codigo.get(++posicionEnCodigo);
        if(!(token.equals("DEC") || token.equals("CARAC") || token.equals("CHAINE") || token.equals("ENT") || AnalizadorLexico.expresionEsIdentificador(token) || token.equals("SI") || token.equals("POUR") || token.equals("TANDISQUE") || token.equals("FAIRE"))){
            //this.token = this.codigo.get(posicionEnCodigo);
            if(this.token.equals("CONS")){
                estructuraConstantes();
                if(this.codigo.get(++posicionEnCodigo).equals(".")){
                    //FALTA
                }else this.imprimirErrorSimbolo(".");
            }else this.imprimirErrorPalabra("CONS");
        }
        else{
           posicionEnCodigo--;
        }
    }

    private void masConstantes() {
       //<MasConstantes> ::= ~ <EstructuraConstantes>
       //<MasConstantes> ::= ɛ
        this.token = this.codigo.get(++posicionEnCodigo);
        if(!this.token.equals(".")){
            if(this.token.equals("~")){
                estructuraConstantes();
            }else this.imprimirErrorSimbolo("~"); 
        }else{
            this.posicionEnCodigo--;
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
        this.token=this.codigo.get(++posicionEnCodigo);
        if(AnalizadorLexico.expresionEsIdentificador(this.token)){
            this.token = this.codigo.get(++posicionEnCodigo);
            if(this.token.equals(":")){
                //FALTA
            }else this.imprimirErrorSimbolo(":");
        }else this.imprimirError("un identificador");
    }
    
    private void numeroIdentificador() {
        /*<AuxNI> ::= <Identificador>
        <AuxNI> ::= <Numero>*/
        System.out.println("?0 " + this.token + " " + this.posicionEnCodigo);
        this.token=this.codigo.get(++posicionEnCodigo);
        System.out.println("?0.1 " + this.token + " " + this.posicionEnCodigo);
        if(AnalizadorLexico.expresionEsIdentificador(this.token)){//????
            System.out.println("?1 " + this.token + " " + this.posicionEnCodigo);
        }else if(AnalizadorLexico.expresionSonNumeros(this.token)){
            System.out.println("?2 " + this.token + " " + this.posicionEnCodigo);
        }else this.imprimirError("número o identificador");
    }
    
    private void variables() {
        //<Variables> ::= <TipoDato>  <Identificador> <MasVariables>
        //<Variables > ::= ԑ
        this.token = this.codigo.get(++posicionEnCodigo);
        if(!(AnalizadorLexico.expresionEsIdentificador(token) || token.equals("FONCION") || token.equals("SI") || token.equals("POUR") || token.equals("TANDISQUE") || token.equals("FAIRE"))){
            this.posicionEnCodigo--;
            tipoDato();
            this.token=this.codigo.get(++posicionEnCodigo);
            if(AnalizadorLexico.expresionEsIdentificador(this.token)){
                masVariables();
            }else this.imprimirError("identificador");  
        }else{
            this.posicionEnCodigo--;
        }
    }
    
    private void masVariables() {
        /* <AuxI2> ::= ~ <Identificador> <AuxI2>
            <AuxI2> ::= . */
        this.token = this.codigo.get(++posicionEnCodigo);
        if(this.token.equals("~")){
            this.token=this.codigo.get(++posicionEnCodigo);
            if(AnalizadorLexico.expresionEsIdentificador(this.token)){
                masVariables();
            }else this.imprimirError("un identificador");
        }else if(this.token.equals(".")){
            
        }else this.imprimirErrorSimbolo("~ o .");
    }
    private void funciones() {
        //<Funciones>::= FONCION <TipoDato> <Identificador> [ ] { <Inicialización> <Instrucciones> <MásInstrucciones>} <Funciones>
        //< Funciones> ::= ԑ
        this.token=this.codigo.get(++posicionEnCodigo);
        if(!(AnalizadorLexico.expresionEsIdentificador(this.token) || token.equals("SI") || token.equals("POUR") || token.equals("TANDISQUE") || token.equals("FAIRE"))){
            System.out.println(this.token + " " + this.posicionEnCodigo);
            if(this.token.equals("FONCION")){
                tipoDato();
                this.token=this.codigo.get(++posicionEnCodigo);
                if(AnalizadorLexico.expresionEsIdentificador(this.token)){
                    corchetes();
                    if(this.codigo.get(++posicionEnCodigo).equals("{")){
                        inicializacionFunciona();
                        instrucciones();
                        masInstrucciones();
                        if(this.codigo.get(++posicionEnCodigo).equals("}")){
                            funciones();
                        }else this.imprimirErrorSimbolo("}");
                    }else this.imprimirErrorSimbolo("}");
                }else this.imprimirError("un identificador");
            }else this.imprimirErrorPalabra("FONCION");
        }else{
            this.posicionEnCodigo--;
        }
        System.out.println("aq");
    }
    
    private void instruccionesConCorchetes(){
        //{ <Instrucciones> <MásInstrucciones> }
        this.token = this.codigo.get(++posicionEnCodigo);
        if(this.token.equals("{")){
            System.out.println("t1 " + this.token + " " + this.posicionEnCodigo);
             instrucciones();
             System.out.println("t2 " + this.token + " " + this.posicionEnCodigo);
             masInstrucciones();
             this.token = this.codigo.get(posicionEnCodigo);
             System.out.println("t3 " + this.token + " " + this.posicionEnCodigo);
             this.token = this.codigo.get(++posicionEnCodigo);
             if(this.token.equals("}")){ //AQUI SE MANDA LLAMAR AL LEXICO
                 System.out.println("t4 " + this.token + " " + this.posicionEnCodigo);
                 //FALTA
             }else this.imprimirErrorSimbolo("}");
        }else this.imprimirErrorSimbolo("{");
    }
    
    private void instrucciones() {
        this.token=this.codigo.get(++posicionEnCodigo);
        System.out.println("=1 " + this.token + " " + this.posicionEnCodigo);
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
            this.posicionEnCodigo--;
            asignarIdentificador();
            numeroIdentificador_LlamarFuncion();
            operacionesAritmeticas();
        }
     }
    
    private void estructuraPOUR(){
        /*<EstructuraPOUR> ::= ( <AsignarIdentificador> <NumeroIdentificador> . <Identificador> 
        <SimbolosRelacionales> <NumeroIdentificador> . <AsignarIdentificador><identificador> 
        <SumaResta> <NumeroIdentificador>  ) <InstruccionesConCorchetes>*/
        if(this.codigo.get(++posicionEnCodigo).equals("(")){
            asignarIdentificador();
            numeroIdentificador();
            if(this.codigo.get(++posicionEnCodigo).equals(".")){
                this.token=this.codigo.get(++posicionEnCodigo);
                 if(AnalizadorLexico.expresionEsIdentificador(this.token)){
                     simbolosRelacionales();
                     numeroIdentificador();
                      if(this.codigo.get(++posicionEnCodigo).equals(".")){
                            asignarIdentificador();
                            this.token=this.codigo.get(++posicionEnCodigo);
                            if(AnalizadorLexico.expresionEsIdentificador(this.token)){
                                sumaResta();
                                numeroIdentificador();
                                if(this.codigo.get(++posicionEnCodigo).equals(")")){
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
        if(this.codigo.get(++posicionEnCodigo).equals("TANDISQUE")){
            condicionesConParentesis();
            if(this.codigo.get(++posicionEnCodigo).equals(".")){
                
            }else this.imprimirErrorSimbolo(".");
        }else this.imprimirErrorPalabra("TANDISQUE");
    }
    private void numeroIdentificador_LlamarFuncion(){
        /*
        <NumeroIdentificador_LlamarFuncion>::=<Numero>
        <NumeroIdentificador_LlamarFuncion>::=<Identificador> <Corchetes_Vacion>
        */
        this.token=this.codigo.get(++posicionEnCodigo);
        if(AnalizadorLexico.expresionSonNumeros(this.token)){
            //FALTA
        }else if(AnalizadorLexico.expresionEsIdentificador(this.token)){
            corchetes_Vacio();
        }else this.imprimirError("un número o identificador");
    }
    
    private void corchetes_Vacio(){
        /*<Corchetes_Vacion>::=ɛ
        <Corchetes_Vacion>::= [ ]
        */
        this.token=this.codigo.get(++posicionEnCodigo);
        if(!(token.equals(".") || token.equals("*") || token.equals("-") || token.equals("+") || token.equals("/") || token.equals("^") || token.equals("%"))){
            if(this.token.equals("[")){
                 if(this.codigo.get(++posicionEnCodigo).equals("]")){ //AQUI SE MANDA LLAMAR AL LEXICO
                     //FALTA
                 }else this.imprimirErrorSimbolo("]");
            }else this.imprimirErrorSimbolo("[");
        }else{
            this.posicionEnCodigo--;
        }
    }
    
    private void corchetes(){
        /*
        <Corchetes>::= [ ]
        */
        if(this.codigo.get(++posicionEnCodigo).equals("[")){
             if(this.codigo.get(++posicionEnCodigo).equals("]")){ //AQUI SE MANDA LLAMAR AL LEXICO
                 //FALTA
             }else this.imprimirErrorSimbolo("]");
        }else this.imprimirErrorSimbolo("[");
    }
    
    private void operacionesAritmeticas(){
        /*<OperacionesAritmeticas>::= .
        <OperacionesAritmeticas>::= <SimbolosAritmeticos> <NumeroIdentificador> <OperacionesAritmeticas>
        */
        if(this.codigo.get(++posicionEnCodigo).equals(".")){
            
            //FALTA
        }else{
            this.posicionEnCodigo--;
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
        this.token=this.codigo.get(++posicionEnCodigo);  //AQUI SE MANDA LLAMAR AL LEXICO
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
        System.out.println("%1 " + this.token + " " + this.posicionEnCodigo);
        condicionesConParentesis();
        System.out.println("%2 " + this.token + " " + this.posicionEnCodigo);
        instruccionesConCorchetes();
        System.out.println("%3 " + this.token + " " + this.posicionEnCodigo);
        siAnidado();
        System.out.println("%4 " + this.token + " " + this.posicionEnCodigo);
        finalSi();
    }
    
    private void siAnidado(){
        /*<SiAnidado>::= Ɛ
        <SiAnidado>::= SIONSI <CondicionesConParentesis>  <InstruccionesConCorchetes> <SiAnidado>
        */
        this.token = this.codigo.get(++posicionEnCodigo); //AQUI SE MANDA LLAMAR AL LEXICO
        
        if(!token.equals("AUTREI")){
            if(this.token.equals("SIONSI")){
                condicionesConParentesis();
                instruccionesConCorchetes();
                siAnidado();
            }else this.imprimirErrorPalabra("SIONSI");
        }else{
            this.posicionEnCodigo--;
        }
    }
    private void finalSi(){
        /*<FinalSi>::=AUTREI <InstruccionesConCorchetes>
        <FinalSi>::= Ɛ
        */
        this.token = this.codigo.get(++posicionEnCodigo);
        
        if(!token.equals(".")){
            if(this.token.equals("AUTREI")){
                instruccionesConCorchetes();
            }else this.imprimirErrorPalabra("AUTREI");
        }else{
            this.posicionEnCodigo--;
        }
    }
    private void sumaResta(){
        /*<SumaResta>::= -
        <SumaResta>::= +
        */
        this.token = this.codigo.get(++posicionEnCodigo); //AQUI SE MANDA LLAMAR AL LEXICO
        if(this.token.equals("+")){
            //FALTA
        }else if(this.token.equals("-")){
            //FALTA
        }else this.imprimirErrorPalabra("+ o -");
    }
    
    private void condicionesConParentesis(){
        //<CondicionesConParentesis>::=( <Condición> )
        this.token = this.codigo.get(++posicionEnCodigo);
        if(this.token.equals("(")){
            System.out.println("&1 " + this.token + " " + this.posicionEnCodigo);
            condicion();
            System.out.println("&2 " + this.token + " " + this.posicionEnCodigo);
            if(this.codigo.get(++posicionEnCodigo).equals(")")){
                    //FALTA
            }else this.imprimirErrorSimbolo(")");
        }else this.imprimirErrorSimbolo("(");
    }
    
    private void condicion(){
        /*<Condición>::= | <CondicionesConParentesis> <MasCondiciones>
        <Condición>::= <NumeroIdentificador> <SimbolosRelacionales> <NumeroIdentificador> <MasCondiciones>
        */
        this.token = this.codigo.get(++posicionEnCodigo);
        if(this.token.equals("|")){
            condicionesConParentesis();
            masCondiciones();
        }else{
            posicionEnCodigo--;
            System.out.println("¡1 " + this.token + " " + this.posicionEnCodigo);
           numeroIdentificador();
           System.out.println("¡2 " + this.token + " " + this.posicionEnCodigo);
           simbolosRelacionales();
           System.out.println("¡3 " + this.token + " " + this.posicionEnCodigo);
           numeroIdentificador();
           this.token = this.codigo.get(this.posicionEnCodigo);
           System.out.println("¡4 " + this.token + " " + this.posicionEnCodigo);
           masCondiciones();
           System.out.println("¡5 " + this.token + " " + this.posicionEnCodigo);
        }
    }
    private void andOr(){
        /*<AndOr>::= &
        <AndOr>::= °
        */
        this.token = this.codigo.get(++posicionEnCodigo);
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
        //{“)“, “&”, “°”}
        this.token = this.codigo.get(++posicionEnCodigo);
        System.out.println("$ " + this.token + " " + this.posicionEnCodigo);
        if(!(this.token.equals(")"))){
            this.posicionEnCodigo--;
            andOr();
            condicionesConParentesis();
            masCondiciones();
        }else{
            this.posicionEnCodigo--;
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
        this.token = this.codigo.get(++posicionEnCodigo);
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
       this.token = this.codigo.get(++posicionEnCodigo);//AQUI SE MANDA LLAMAR AL LEXICO
        if(this.token.equals("DEC")){
            
        }else if(this.token.equals("CARAC")){
            
        }else if(this.token.equals("CHAINE")){
            
        }else if(this.token.equals("ENT")){
            
        }else this.imprimirError("un tipo de dato");
    }
    
    private void imprimirErrorPalabra(String palabraEsperada){
        System.out.println("Se esperaba la palabra: " + palabraEsperada);
        System.exit(0);
    }
    private void imprimirErrorSimbolo(String simboloEsperada){
        System.out.println("Se esperaba el símbolo: " + simboloEsperada);
        System.exit(0);
    }
    private void imprimirError(String esperaba){
        System.out.println("Se esperaba: " + esperaba);
        System.exit(0);
    }
    private void compilacionExitosa() {
        System.out.println("Se logró una compilación exitosa");
    }
}
