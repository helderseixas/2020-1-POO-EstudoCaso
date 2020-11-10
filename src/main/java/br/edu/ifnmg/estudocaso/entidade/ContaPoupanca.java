package br.edu.ifnmg.estudocaso.entidade;

/**
 *
 * @author Denilson Melo e Murilo Ferreira
 */
public class ContaPoupanca extends Conta{
   	private int diaAniversario;             
        
        public ContaPoupanca(String numero, Cliente cliente, double saldo, int diaAniversario){
            super(numero, cliente, saldo);
            this.diaAniversario =  diaAniversario;
        }
}