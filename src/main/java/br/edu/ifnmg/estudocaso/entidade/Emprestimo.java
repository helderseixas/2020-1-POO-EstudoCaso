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
    private ContaCorrente contaCorrente;
    
    public Emprestimo(ContaCorrente contaCorrente, double valor, int numeroParcelas){        
        this.contaCorrente = contaCorrente;
        this.valor = valor;
        this.numeroParcelas = numeroParcelas;
        this.juros = TAXA_JUROS_PADRAO;        
        
        this.verificarValor();
        this.verificarCliente();
        this.verificarNumeroParcelas();                
        this.ajustarValorJuros();      
        this.ajustarValorParcela();        
        this.contaCorrente.getCliente().incrementarNumeroEmprestimos();
        this.contaCorrente.depositar(valor);
    }

    private void ajustarValorParcela() throws RuntimeException {
        double valorComJuros = this.valor + (this.valor * this.juros);
        this.valorParcela = valorComJuros / this.numeroParcelas;
        if(this.valorParcela < 100){
            throw new RuntimeException("Valor mínimo da parcela é inválido!");
        }
    }

    private void ajustarValorJuros() {
        Cliente cliente = this.contaCorrente.getCliente();
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
        if(cliente.getNumeroEmprestimos() >= 1){
            this.juros = this.juros * 2;
        }
    }
    
    private void verificarValor(){
        if(this.valor > 2 * this.contaCorrente.calcularLimite()){
           throw new RuntimeException("Valor do empréstimo é inválido!"); 
        }
    }
    
    private void verificarCliente(){
        if(this.contaCorrente.getCliente().habilitadoParaNovoEmprestimo() == false){
            RuntimeException clienteNaoHabilitadoException = 
                        new RuntimeException("Cliente não habilitado para novos empréstimos!");
            throw clienteNaoHabilitadoException;            
        }
    }
    
    private void verificarNumeroParcelas(){        
        if(this.numeroParcelas  > 24){
            throw new RuntimeException("Número de parcelas é inválido!"); 
        }else if(this.numeroParcelas > 12 && 
                this.contaCorrente.getCliente() instanceof PessoaFisica){
            PessoaFisica pessoaFisica = (PessoaFisica) this.contaCorrente.getCliente();
            if(pessoaFisica.getSalario() <= 10000){
                throw new RuntimeException("Número de parcelas é inválido!");     
            }
        }       
    }

    public double getJuros() {
        return this.juros;
    }
    
    public double getJurosEmPorcentagem(){
        return this.juros * 100;
    }

    public double getValor() {
        return valor;
    }

    public int getNumeroParcelas() {
        return numeroParcelas;
    }

    public double getValorParcela() {
        return valorParcela;
    }
    	
}