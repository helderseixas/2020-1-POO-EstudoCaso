package br.edu.ifnmg.estudocaso.entidade;

public class Emprestimo extends OperacaoFinanceira{

    private static final double TAXA_JUROS_PADRAO = 0.1;
    
    private static final double TAXA_JUROS_ELEVADA_PESSOA_FISICA = 0.15;
    private static final double TAXA_JUROS_REDUZIDA_PESSOA_FISICA = 0.03;
    
    private static final double TAXA_JUROS_EMPRESA_PEQUENO_PORTE = 0.08;
    private static final double TAXA_JUROS_EMPRESA_MEDIO_PORTE = 0.04;
    private static final double TAXA_JUROS_EMPRESA_GRANDE_PORTE = 0.02;
    
    private static final int NIVEL_SALARIO_BAIXO = 2000;
    private static final int NIVEL_SALARIO_ALTO = 10000;
    
    private double valor;        
    private int numeroParcelas;        
    private double juros;        
    private double valorParcela;
    
    public Emprestimo(Cliente cliente){        
        this.juros = TAXA_JUROS_PADRAO;        
        
        if(cliente instanceof PessoaFisica){
            PessoaFisica clientePessoaFisica = (PessoaFisica) cliente;
            if(clientePessoaFisica.getSalario() > NIVEL_SALARIO_ALTO){
                this.juros = TAXA_JUROS_REDUZIDA_PESSOA_FISICA;
            }else if(clientePessoaFisica.getSalario() < NIVEL_SALARIO_BAIXO){
                this.juros = TAXA_JUROS_ELEVADA_PESSOA_FISICA;
            }
        }else if(cliente instanceof PessoaJuridica){
            PessoaJuridica clientePessoaJuridica = (PessoaJuridica) cliente;
            if(clientePessoaJuridica.getPorte() == PessoaJuridica.GRANDE_PORTE){
                this.juros = TAXA_JUROS_EMPRESA_GRANDE_PORTE;
            }else if(clientePessoaJuridica.getPorte() == PessoaJuridica.MEDIO_PORTE){
                this.juros = TAXA_JUROS_EMPRESA_MEDIO_PORTE;
            }else if(clientePessoaJuridica.getPorte() == PessoaJuridica.PEQUENO_PORTE){
                this.juros = TAXA_JUROS_EMPRESA_PEQUENO_PORTE;
            }
        }
        if(cliente.numeroEmprestimos >= 1){
            this.juros = this.juros * 2;
        }
        cliente.numeroEmprestimos++;
    }

    public double getJuros() {
        return this.juros;
    }
    
    public double getJurosEmPorcentagem(){
        return this.juros * 100;
    }
    
	
}