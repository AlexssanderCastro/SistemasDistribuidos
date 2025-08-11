/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.prjservidorchat_thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Vector;

/**
 *
 * @author Alexssander
 */
public class PrjServidorChat_Thread extends Thread{
    
    private static Vector clientes;
    private Socket conexao;
    private String meuNome;

    public PrjServidorChat_Thread(Socket ss) {
        this.conexao = ss;
    }
    
    

    public static void main(String[] args) {
        clientes = new Vector();
        
        try{
            //Instância do Socket que fica na porta 2222
            ServerSocket ss = new ServerSocket(2222);
            while(true)
            {
                //Aguarda algum cliente se conectar
                
                System.out.println("Esperando um cliente realiar a conexão...");
                Socket con = ss.accept();
                System.out.println("Conexão realizada!!");
                
                Thread t = new PrjServidorChat_Thread(con);
                t.start();
                
            }
            
        }catch(IOException ex){
            ex.printStackTrace();
            
        }
    }
    
    //execução da Thread
    @Override
    public void run(){
        try{
            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            PrintStream saida = new PrintStream(conexao.getOutputStream());
            meuNome = entrada.readLine();
            
            if(meuNome==null){
                return;
            }
            clientes.add(saida);
            String linha = entrada.readLine();
            while(linha!=null && !(linha.trim().equals(""))){
                enviarParaTodos(saida," saiu "," do chat");
                clientes.remove(saida);
                conexao.close();
            }
            
        }catch(IOException ex){
            ex.printStackTrace();
            
        }
    }
    
    
    public void enviarParaTodos(PrintStream saida,String acao,String linha){
        Enumeration e = clientes.elements();
        while(e.hasMoreElements()){
            PrintStream chat = (PrintStream) e.nextElement();
            if(chat!=saida){
                chat.println(meuNome+acao+linha);
            }
        }
    }
    
}
