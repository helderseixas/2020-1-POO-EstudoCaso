/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.estudocaso;

import br.edu.ifnmg.estudocaso.entidade.Cliente;
import br.edu.ifnmg.estudocaso.entidade.ContaCorrente;
import br.edu.ifnmg.estudocaso.entidade.PessoaFisica;
import br.edu.ifnmg.estudocaso.entidade.PessoaJuridica;

/**
 *
 * @author helder
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        System.out.println("Teste");
        
        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setNome("João");
        pessoaFisica.setSalario(3000);
        pessoaFisica.imprimirNome();
        
        PessoaJuridica pj = new PessoaJuridica(PessoaJuridica.MICROEMPRESA);
        pj.setNome("Empresa ABC");
        pj.imprimirNome();
        
        ContaCorrente contaCorrente = new ContaCorrente(pessoaFisica);
        double limite = contaCorrente.calcularLimite();
        System.out.println("Limite do João: " + limite);
        
    }
    
}
