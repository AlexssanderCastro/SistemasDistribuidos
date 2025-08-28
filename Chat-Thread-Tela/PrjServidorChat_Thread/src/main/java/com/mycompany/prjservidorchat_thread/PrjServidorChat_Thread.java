/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.prjservidorchat_thread;

import com.mycompany.prjservidorchat_thread.Forms.TelaInicial;
import com.mycompany.prjservidorchat_thread.util.EscreverArquivo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author Alexssander
 */
public class PrjServidorChat_Thread extends Thread{
    
    private static Vector clientes;
    private static List<String> termos;
    private Socket conexao;
    private String meuNome;

    
    
    public PrjServidorChat_Thread() {
        
    }
    
    public PrjServidorChat_Thread(Socket ss) {
        this.conexao = ss;
    }
    
    

    public static void main(String[] args) {
        clientes = new Vector();
        termos = new ArrayList();
        
        TelaInicial tela = new TelaInicial();
        tela.setVisible(true);
        
//        System.out.println("Digite as palavras que deverão ser filtradas para guardar as mensagens (Apenas aperte ENTER para continuar):");
//        Scanner leitor = new Scanner(System.in);
//        String palavra = leitor.nextLine();
//        
//        while(palavra!=null && !(palavra.trim().equals(""))){
//            termos.add(palavra);
//            palavra=leitor.nextLine();
//        }
//        
//        
//        leitor.close();
        
        
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
                
                for(String palavra: termos){
                    if(linha.toLowerCase().contains(palavra)){
                        EscreverArquivo.escrever(meuNome+" falou: "+linha);
                    }
                }
                
                enviarParaTodos(saida," falou: ",linha);
                linha=entrada.readLine();
            }
            
            enviarParaTodos(saida," saiu "," do chat");
            clientes.remove(saida);
            conexao.close();
            
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
