/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

/**
 *
 * @author Alexssander
 */
public class ClasseContThreadB extends Thread
{
    String nome;
    int tamanhoContador;

    public ClasseContThreadB(String nome, int tamanhoContador) 
    {
        this.nome= nome;
        this.tamanhoContador=tamanhoContador;
    }
    
    @Override
    public void run(){
        
        for(int i=0;i<tamanhoContador;i++)
        {
            System.out.println(""+nome+" --> "+i);
        }
    }
    
}
