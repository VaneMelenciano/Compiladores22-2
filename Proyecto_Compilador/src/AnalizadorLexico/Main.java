/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalizadorLexico;

import AnalizadorSintactico.BNF;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author aaron
 */
public class Main {
    public static void main(String args[]) throws FileNotFoundException, IOException {
        LectorTxt.iniciar();
        if(AnalizadorLexico.revisarListaDeTokens() == true){
            //iniciar bnf.
            System.out.println();
            ArrayList<String> lista = AnalizadorLexico.getListaFiltrada();
            for(String s : lista){
                System.out.println(s);
            }
            System.out.println();
            BNF bnf = new BNF();
        }
    }
}
