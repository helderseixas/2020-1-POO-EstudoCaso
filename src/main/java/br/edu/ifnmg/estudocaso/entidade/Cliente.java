/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.estudocaso.entidade;

/**
 *
 * @author helder
 */
public abstract class Cliente {
    protected String senha;
    protected String  nome;   
    
    public void imprimirNome(){
        System.out.println(this.nome);
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
}