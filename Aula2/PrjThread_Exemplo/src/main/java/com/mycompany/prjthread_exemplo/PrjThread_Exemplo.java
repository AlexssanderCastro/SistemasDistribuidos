/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.prjthread_exemplo;

import Classes.ClasseContA;
import Classes.ClasseContThreadB;
import Classes.ClasseContThreadC;

/**
 *
 * @author Alexssander
 */
public class PrjThread_Exemplo {

    public static void main(String[] args) {
        
//        new ClasseContA("Teste I",100).contador();
//        new ClasseContA("Teste II",100).contador();

//        Thread A = new ClasseContThreadB("Teste I",1000);
//        Thread B = new ClasseContThreadB("Teste II",1000);
//        
//        A.start();
//        B.start();

        Thread A = new Thread(new ClasseContThreadC("Teste I",100));
        Thread B = new Thread(new ClasseContThreadC("Teste II",100));
        
        A.start();
        B.start();
        

        
    }
}
