package br.edu.ifnmg.estudocaso.entidade;

import java.util.*;

/**
 *
 * @author Denilson Melo e Murilo Ferreira
 */
public abstract class Conta {
    protected String numero;
    protected double saldo;
    
    protected Cliente cliente;    
    
    public Conta(String numero, Cliente cliente, double saldo){
        this.numero = numero;
        this.cliente = cliente;
        this.saldo = saldo;
    }

    public String getNumero() {
        return this.numero;
    }

    public double getSaldo(){
        return this.saldo;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void autenticar(HashMap codigo_Conta) {
        // Ler os dados a partir do teclado

        Scanner scanNumero = new Scanner(System.in);

        System.out.println("\nDigite o nº da conta:");
        String numeroPesquisado = scanNumero.nextLine();

        Scanner scanSenha = new Scanner(System.in);

        System.out.println("Digite a senha:");
        String senhaPesquisada = scanSenha.nextLine();

        // Pesquisa no HashMap de Contas pelo numero pesquisado.
        if ((codigo_Conta.get(numeroPesquisado) != null)) {
            Conta contaPesquisada = (Conta) codigo_Conta.get(numeroPesquisado);

            if (contaPesquisada
                    .getCliente()
                    .getSenha()
                    .equals(senhaPesquisada)) {
                System.out.printf("Usuário autenticado com sucesso! (" + contaPesquisada.getCliente().getNome() + ")");
                return;
            } else {
                System.out.println("Senha incorreta!");
            }
        }
        else {
            System.out.println("Conta não encontrada!");
        }

    }
}