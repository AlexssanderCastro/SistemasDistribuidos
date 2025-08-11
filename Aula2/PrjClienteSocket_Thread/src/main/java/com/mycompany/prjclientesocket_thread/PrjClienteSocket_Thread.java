/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.prjclientesocket_thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author Alexssander
 */
public class PrjClienteSocket_Thread extends Thread{
    
    
    private static boolean done = false;
    private Socket conexao;

    public PrjClienteSocket_Thread(Socket conexao) {
        this.conexao = conexao;
    }
    
    public static void main(String[] args) {
        
        try{
            Socket con = new Socket("127.0.0.1",2222);
            PrintStream saida = new PrintStream(con.getOutputStream());
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Digite o seu nome: ");
            String meuNome = teclado.readLine();
            saida.println(meuNome);
            Thread t = new PrjClienteSocket_Thread(con);
            t.start();
            String linha;
            while(true){
                System.out.println("> ");
                linha=teclado.readLine();
                if(done){
                    break;
                }
                saida.println(linha);
                
            }
            
        }catch(IOException ex){
            ex.printStackTrace();
            
        }
    }
    
    @Override
    public void run(){
        try{
            BufferedReader entrada= new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String linha;
            while(true){
                linha=entrada.readLine();
                if(linha==null){
                    System.out.println("Conexão encerrada!!!");
                    break;
                }
                System.out.println();
                System.out.println(linha);
                System.out.println("...> ");
            }
                    
            
        }catch(IOException ex){
            ex.printStackTrace();
            
        }
        done=true;
    }
}
