package br.edu.ifnmg.estudocaso.entidade;

/**
 *
 * @author Denilson Melo e Murilo Ferreira
 */
public abstract class Conta implements Comparable<Conta>{
    protected String numero;
    protected double saldo;
    
    protected Cliente cliente;    
    
    public Conta(String numero, Cliente cliente, double saldo){
        this.numero = numero;
        this.cliente = cliente;
        this.saldo = saldo;
    }
    
    public double getSaldo(){
        return this.saldo;
    }
    
    public Cliente getCliente(){
        return this.cliente;
    }
    
    public String getNumero(){
        return this.numero;
    }
    
    @Override
    public int compareTo(Conta outraConta){
        double diferenca = this.getSaldo() - outraConta.getSaldo();
        return (int)diferenca * -1;
    }
}