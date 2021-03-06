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
import br.edu.ifnmg.estudocaso.entidade.Emprestimo;
import br.edu.ifnmg.estudocaso.entidade.PessoaFisica;
import br.edu.ifnmg.estudocaso.entidade.PessoaJuridica;
import br.edu.ifnmg.estudocaso.exception.NegocioException;
import br.edu.ifnmg.estudocaso.exception.NumeroParcelasException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.*;

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
//        imprimirTotalDepositado();
//        exibirContasOrdenadasPeloNomeCliente();        
        
        Conta conta = autenticarConta();
        
        Scanner scanner = new Scanner(System.in);
        int operacao = 0;
        do{
            System.out.println("\n\n***Menu***");
            System.out.println("Operação: ");
            System.out.println("1 - Transferência");
            System.out.println("2 - Empréstimo");
            System.out.println("3 - Trocar conta autenticada");
            System.out.println("4 - Consultar saldo");
            System.out.println("5 - Consultar limite");
            System.out.println("6 - Sair");
            System.out.println("Informe a operação: ");
            operacao = Integer.parseInt(scanner.nextLine());
            
            if(operacao == 1){
                //TODO: implementar transferencia
            }else if(operacao == 2){
                if(conta instanceof ContaCorrente){
                    ContaCorrente contaCorrente = (ContaCorrente) conta;
                    
                    System.out.println("Valor do empréstimo: ");
                    double valorEmprestimo = Double.parseDouble(scanner.nextLine());
                    System.out.println("Número de parcelas: ");
                    int numeroParcelas = Integer.parseInt(scanner.nextLine());
                    
                    try{
                        Emprestimo emprestimo = 
                            contaCorrente.solicitarEmprestimo(valorEmprestimo, numeroParcelas);                                                        
                        imprimirMensagemEmprestimoComSucesso(emprestimo);
                    }catch(NumeroParcelasException e){
                        System.out.println("Empréstimo não autorizado. O número de parcelas deve ser no mínimo 1 e no máximo 24.");
                    }catch(NegocioException e){
                        System.out.println("Empréstimo não concedido!");
                        System.out.println(e.getMessage());
                    }                    
                }else{
                    System.out.println("Conta poupança não pode solicitar empréstimo!");
                }
            }else if(operacao ==  3){
                conta = autenticarConta();
            }else if(operacao == 4){
                System.out.printf("Saldo: %f\n", conta.getSaldo());
            }else if(operacao == 5){
                if(conta instanceof ContaCorrente){
                    System.out.printf("Limite: %f",((ContaCorrente)conta).calcularLimite());
                }else{
                    System.out.println("Esta conta não possui limite.");
                }
            }
        }while(operacao != 6);
        

        Date fim = new Date();
        System.out.printf("\nFim da execução: %s\n", fim.toString());
        long tempo = fim.getTime() - inicio.getTime();
        double tempoMinutos = tempo / 1000.0 / 60.0;
        System.out.printf("\nTempo: %f minutos", tempoMinutos);
    }

    private static void imprimirMensagemEmprestimoComSucesso(Emprestimo emprestimo) {
        System.out.println("Empréstimo realizado com sucesso!");
        System.out.printf("\nTaxa de juros: %f%% \nValor: R$ %f \nNúmero de parcelas: %d \nValor da parcela: %f", 
                emprestimo.getJurosEmPorcentagem(),
                emprestimo.getValor(),
                emprestimo.getNumeroParcelas(),
                emprestimo.getValorParcela());
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

    private static Conta autenticarConta() {
        // Ler os dados a partir do teclado
        Scanner scanner = new Scanner(System.in);        

        Conta contaPesquisada = null;
        
        do{        
            System.out.println("\nDigite o nº da conta:");
            //String numeroPesquisado = "1";
            String numeroPesquisado = scanner.next();      

            System.out.println("Digite a senha:");
            //String senhaPesquisada = "6838";
            String senhaPesquisada = scanner.next();

            // Pesquisa no HashMap de Contas pelo numero pesquisado.
            if ((mapaContas.get(numeroPesquisado) != null)) {
                contaPesquisada = (Conta) mapaContas.get(numeroPesquisado);

                boolean autenticadoSucesso = 
                        contaPesquisada.autenticar(senhaPesquisada);

                if(autenticadoSucesso){
                    System.out.printf("%s (%s) autenticado com sucesso!", 
                            contaPesquisada.getCliente().getNome(), 
                            contaPesquisada.getNumero());
                }else{
                    System.out.printf("%s (%s) não autenticado! Senha incorreta", 
                            contaPesquisada.getCliente().getNome(), 
                            contaPesquisada.getNumero());
                }
            }else {
                System.out.println("Conta não encontrada!");
            }
        }while (contaPesquisada == null);
        
        return contaPesquisada;
    }
    
}
