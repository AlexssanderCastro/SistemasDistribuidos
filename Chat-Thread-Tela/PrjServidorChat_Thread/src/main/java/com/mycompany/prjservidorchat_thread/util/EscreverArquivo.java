/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prjservidorchat_thread.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Alexssander
 */
public class EscreverArquivo {
    
    public static void escrever(String texto){
        try{
            File f = new File("C:\\Users\\alexs\\Desktop\\Codigos\\SD\\TextoGravado.txt");
            FileWriter fw = new FileWriter(f,true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(texto);
            fw.close();
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
}
