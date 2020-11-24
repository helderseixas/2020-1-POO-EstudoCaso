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
public class PessoaFisica extends Cliente{
    private double salario;   

    public PessoaFisica(String codigo, String nome, String senha, double salario) {
        super(codigo, nome, senha);
        this.salario = salario;
    }
    
    public double getSalario(){
        return this.salario;
    }
    
    public void setSalario(double salario){
        this.salario = salario;
    }
}
