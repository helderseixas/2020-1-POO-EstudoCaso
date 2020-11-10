/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.estudocaso;

import br.edu.ifnmg.estudocaso.entidade.Cliente;
import br.edu.ifnmg.estudocaso.entidade.Conta;
import br.edu.ifnmg.estudocaso.entidade.ContaCorrente;
import br.edu.ifnmg.estudocaso.entidade.ContaPoupanca;
import br.edu.ifnmg.estudocaso.entidade.PessoaFisica;
import br.edu.ifnmg.estudocaso.entidade.PessoaJuridica;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author helder
 */
public class Main {
    
    private static ArrayList<Cliente> clientes = new ArrayList<>();
    private static ArrayList<Conta> contas = new ArrayList<>();

    public static void main(String[] args) throws IOException {                       
        carregarDados();
        imprimirTotalDepositado();
    }

    private static void carregarDados() throws IOException {            
        carregarDadosClientesPessoaFisica();
        carregarDadosClientesPessoaJuridica();
        carregarDadosContaCorrente();  
        carregarDadosContaPoupanca();
    }

    private static void carregarDadosClientesPessoaFisica() throws IOException {
        System.out.println("Carregando dados de clientes pessoa física...");
        
        String arquivo = "dados/clientes_pessoa_fisica.csv";
        BufferedReader bufferedReader = null;        
        String separador = ",";                        
        
        FileReader fileReader = new FileReader(arquivo);
        bufferedReader = new BufferedReader(fileReader);
        
        //Lê e ignora a primeira linha com cabeçalho das colunas
        String linha = bufferedReader.readLine();

        while((linha = bufferedReader.readLine()) != null){
            String[] colunas = linha.split(separador);
            String codigo = colunas[0];
            String nome = colunas[1];
            String senha = colunas[2];
            double salario =  Double.parseDouble(colunas[3]);
            PessoaFisica pessoaFisica = new PessoaFisica(codigo, nome, senha, salario); 
            clientes.add(pessoaFisica);            
        }                            
        System.out.println("Dados de clientes pessoa física carregados.");
    }
    
    private static void carregarDadosClientesPessoaJuridica() throws IOException {
        System.out.println("Carregando dados de clientes pessoa jurídica...");
        
        String arquivo = "dados/clientes_pessoa_juridica.csv";
        BufferedReader bufferedReader = null;        
        String separador = ",";                        
        
        FileReader fileReader = new FileReader(arquivo);
        bufferedReader = new BufferedReader(fileReader);
        
        //Lê e ignora a primeira linha com cabeçalho das colunas
        String linha = bufferedReader.readLine();

        while((linha = bufferedReader.readLine()) != null){
            String[] colunas = linha.split(separador);
            String codigo = colunas[0];
            String nome = colunas[1];
            String senha = colunas[2];
            char porte =  colunas[3].toCharArray()[0];
            PessoaJuridica pessoaJuridica = new PessoaJuridica(codigo, nome, senha, porte); 
            clientes.add(pessoaJuridica);            
        }                  
        
        System.out.println("Dados de clientes pessoa jurídica carregados.");
    }

    private static void carregarDadosContaCorrente() throws IOException {
        System.out.println("Carregando dados de conta corrente...");
        
        String arquivo = "dados/contas_corrente.csv";
        BufferedReader bufferedReader = null;        
        String separador = ",";                        
        
        FileReader fileReader = new FileReader(arquivo);
        bufferedReader = new BufferedReader(fileReader);
        
        //Lê e ignora a primeira linha com cabeçalho das colunas
        String linha = bufferedReader.readLine();

        while((linha = bufferedReader.readLine()) != null){
            String[] colunas = linha.split(separador);
            String numero = colunas[0];
            String codigoCliente = colunas[1];
            double saldo = Double.parseDouble(colunas[2]);            
            Cliente cliente = recuperarCliente(codigoCliente);
            ContaCorrente contaCorrente = new ContaCorrente(numero, cliente, saldo);
            contas.add(contaCorrente);            
        }      
        
        System.out.println("Dados de conta corrente carregados.");
    }

    private static void carregarDadosContaPoupanca() throws IOException {
        System.out.println("Carregando dados de conta poupança...");
        
        String arquivo = "dados/contas_poupanca.csv";
        BufferedReader bufferedReader = null;        
        String separador = ",";                        
        
        FileReader fileReader = new FileReader(arquivo);
        bufferedReader = new BufferedReader(fileReader);
        
        //Lê e ignora a primeira linha com cabeçalho das colunas
        String linha = bufferedReader.readLine();

        while((linha = bufferedReader.readLine()) != null){
            String[] colunas = linha.split(separador);
            String numero = colunas[0];
            String codigoCliente = colunas[1];
            double saldo = Double.parseDouble(colunas[2]);            
            int diaAniversario = Integer.parseInt(colunas[3]);
            Cliente cliente = recuperarCliente(codigoCliente);
            ContaPoupanca contaPoupanca = new ContaPoupanca(numero, cliente, saldo, diaAniversario);
            contas.add(contaPoupanca);            
        }      
        
        System.out.println("Dados de conta poupança carregados.");
    }

    private static void imprimirTotalDepositado() {
        double total = 0.0;
        for(Conta conta : contas){
            total += conta.getSaldo();
        }
        System.out.printf("\nTotal depositado: R$ %f", total);
    }

    private static Cliente recuperarCliente(String codigoCliente) {
        for (Cliente cliente : clientes) {
            if (cliente.getCodigo().equals(codigoCliente)){
                return cliente;
            }
        }
        return null;
    }    
}
