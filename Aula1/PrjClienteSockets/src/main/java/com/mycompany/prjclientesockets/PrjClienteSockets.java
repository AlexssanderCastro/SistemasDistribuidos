/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.prjclientesockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author Alexssander
 */
public class PrjClienteSockets {

    public static void main(String[] args)
    {
        try{
            Socket con = new Socket("127.0.0.1",2222);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(con.getInputStream()));
            PrintStream saida = new PrintStream(con.getOutputStream());
            String linha;
            BufferedReader entradaTeclado = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                System.out.println("> ");
                linha=entradaTeclado.readLine();
                saida.println(linha);
                linha=entrada.readLine();
                if(linha==null){
                    System.out.println("Conexao encerrada!!!");
                    break;
                }
                System.out.println(linha);
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
    }
}
