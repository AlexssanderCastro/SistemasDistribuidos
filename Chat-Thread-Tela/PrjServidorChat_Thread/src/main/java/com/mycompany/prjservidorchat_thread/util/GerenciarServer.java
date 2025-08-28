/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prjservidorchat_thread.util;

import com.mycompany.prjservidorchat_thread.PrjServidorChat_Thread;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Alexssander
 */
public class GerenciarServer extends Thread{
    public static int porta ;
            
    @Override
    public void run(){
    
            try{
            //Instância do Socket que fica na porta 2222
            ServerSocket ss = new ServerSocket(porta);
            
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
}
