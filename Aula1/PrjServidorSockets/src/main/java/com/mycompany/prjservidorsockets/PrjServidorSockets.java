/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.prjservidorsockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Alexssander
 */
public class PrjServidorSockets {

    public static void main(String[] args) {
        try{
            
            ServerSocket s = new ServerSocket(2222);
            
            while(true){
                
                Socket con = s.accept();
                System.out.println("Conectou!!!");
                BufferedReader entrada = new BufferedReader(new InputStreamReader(con.getInputStream()));
                PrintStream saida = new PrintStream(con.getOutputStream());
                String linha= entrada.readLine();
                
                while(linha!=null && !(linha.trim().equals(""))){
                    System.out.println("Msg do cliente: "+linha);
                    saida.println("Saida: " +linha);
                    linha=entrada.readLine();
                    
                }
                con.close();
            }
            
        }catch(IOException e){
            
            System.out.println(e);
            
            
        }
    }
}
