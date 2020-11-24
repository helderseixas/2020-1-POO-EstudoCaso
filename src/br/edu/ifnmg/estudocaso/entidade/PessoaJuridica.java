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
public class PessoaJuridica extends Cliente{
    public final static char MICROEMPRESA = 'M';
    public final static char PEQUENO_PORTE = 'P';
    public final static char MEDIO_PORTE = 'E';
    public final static char GRANDE_PORTE = 'G';
    
    private char porte;
    
    public PessoaJuridica(String codigo, String nome, String senha, char porte){
        super(codigo, nome, senha);
        this.porte = porte;
    }   
    
    public char getPorte(){
        return this.porte;
    }
}
