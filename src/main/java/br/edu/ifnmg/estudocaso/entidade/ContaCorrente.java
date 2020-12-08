package br.edu.ifnmg.estudocaso.entidade;

/**
 *
 * @author Denilson Melo e Murilo Ferreira
 */
public class ContaCorrente extends Conta{

    public ContaCorrente(String numero, Cliente cliente, double saldo) {
        super(numero, cliente, saldo);
    }

    public double calcularLimite(){
        if(this.cliente instanceof PessoaFisica){
            PessoaFisica pf = (PessoaFisica) this.cliente;
            return pf.getSalario() * 2;
        }else{
            PessoaJuridica pj = (PessoaJuridica) this.cliente;
            if(pj.getPorte() == PessoaJuridica.MICROEMPRESA){
                return 10000;
            }else if(pj.getPorte() == PessoaJuridica.PEQUENO_PORTE){
                return 30000;
            }else if(pj.getPorte() == PessoaJuridica.MEDIO_PORTE){
                return 100000;
            }else{
                return 1000000;
            }            
        }        
    }

    public Emprestimo solicitarEmprestimo(double valor, int numeroParcelasPagamento){
        if(valor <= 2 * this.calcularLimite()){
            if(cliente.habilitadoParaNovoEmprestimo() == true){
                this.saldo += valor;
                Emprestimo emprestimo = new Emprestimo(this.cliente);
                return emprestimo;
            }else{
                RuntimeException clienteNaoHabilitadoException = 
                        new RuntimeException("Cliente não habilitado para novos empréstimos!");
                throw clienteNaoHabilitadoException;
            }
        }else{
            throw new RuntimeException("Valor do empréstimo é inválido!");
        }
    }
}
