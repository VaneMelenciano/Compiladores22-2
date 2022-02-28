/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalizadorLexico;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author aaron
 */
public class Main {
    public static void main(String args[]) throws FileNotFoundException, IOException {
        LectorTxt t = new LectorTxt();
        
        AnalizadorLexico.contadorExpresionLeida = 0;
        AnalizadorLexico.expresionLeida = t.getTexto().get(0);
        
        System.out.println(AnalizadorLexico.retornarCadena());
    }
}
