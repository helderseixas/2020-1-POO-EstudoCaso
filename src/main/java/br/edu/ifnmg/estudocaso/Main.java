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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author helder
 */
public class Main {

    private static HashMap<String, Cliente> mapaClientes = new HashMap<>();
    private static HashMap<String, Conta> mapaContas = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Date inicio = new Date();
        System.out.printf("\nInício da execução: %s\n", inicio.toString());

        carregarDados();
        imprimirTotalDepositado();
        exibirContasOrdenadasPeloNomeCliente();

        Date fim = new Date();
        System.out.printf("\nFim da execução: %s\n", fim.toString());
        long tempo = fim.getTime() - inicio.getTime();
        double tempoMinutos = tempo / 1000.0 / 60.0;
        System.out.printf("\nTempo: %f minutos", tempoMinutos);
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

        while ((linha = bufferedReader.readLine()) != null) {
            String[] colunas = linha.split(separador);
            String codigo = colunas[0];
            String nome = colunas[1];
            String senha = colunas[2];
            double salario = Double.parseDouble(colunas[3]);
            PessoaFisica pessoaFisica = new PessoaFisica(codigo, nome, senha, salario);
            mapaClientes.put(codigo, pessoaFisica);
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

        while ((linha = bufferedReader.readLine()) != null) {
            String[] colunas = linha.split(separador);
            String codigo = colunas[0];
            String nome = colunas[1];
            String senha = colunas[2];
            char porte = colunas[3].toCharArray()[0];
            PessoaJuridica pessoaJuridica = new PessoaJuridica(codigo, nome, senha, porte);
            mapaClientes.put(codigo, pessoaJuridica);
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

        while ((linha = bufferedReader.readLine()) != null) {
            String[] colunas = linha.split(separador);
            String numero = colunas[0];
            String codigoCliente = colunas[1];
            double saldo = Double.parseDouble(colunas[2]);
            Cliente cliente = recuperarCliente(codigoCliente);
            ContaCorrente contaCorrente = new ContaCorrente(numero, cliente, saldo);
            mapaContas.put(numero, contaCorrente);
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

        while ((linha = bufferedReader.readLine()) != null) {
            String[] colunas = linha.split(separador);
            String numero = colunas[0];
            String codigoCliente = colunas[1];
            double saldo = Double.parseDouble(colunas[2]);
            int diaAniversario = Integer.parseInt(colunas[3]);
            Cliente cliente = recuperarCliente(codigoCliente);
            ContaPoupanca contaPoupanca = new ContaPoupanca(numero, cliente, saldo, diaAniversario);
            mapaContas.put(numero, contaPoupanca);
        }

        System.out.println("Dados de conta poupança carregados.");
    }

    private static void imprimirTotalDepositado() {
        double total = 0.0;
        for (Conta conta : mapaContas.values()) {
            total += conta.getSaldo();
        }
        System.out.printf("\nTotal depositado: R$ %f", total);
    }

    private static Cliente recuperarCliente(String codigoCliente) {
        return mapaClientes.get(codigoCliente);
    }

    private static void exibirContasOrdenadasPeloNomeCliente() {
        Comparator<Conta> comparadorContas = new Comparator<>() {
            @Override
            public int compare(Conta conta1, Conta conta2) {
                int diferenca = conta1.getCliente().getNome().compareTo(conta2.getCliente().getNome());
                if (diferenca != 0) {
                    return diferenca;
                } else {
                    diferenca = (int) (conta1.getSaldo() - conta2.getSaldo());
                    return diferenca * -1;
                }
            }
        };

        Collection<Conta> colecaoContas = mapaContas.values();
        List<Conta> listaContas = new ArrayList<>(colecaoContas);
        Collections.sort(listaContas, comparadorContas);

        for (Conta conta : listaContas) {
            System.out.printf("\nCliente: %s - Número da conta: %s - Saldo: %f",
                    conta.getCliente().getNome(),
                    conta.getNumero(),
                    conta.getSaldo());
        }
    }
}
