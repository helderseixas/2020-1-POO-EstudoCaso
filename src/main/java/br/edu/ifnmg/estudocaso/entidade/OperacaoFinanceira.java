package br.edu.ifnmg.estudocaso.entidade;

public abstract class OperacaoFinanceira {
	
    protected int codigo;
    protected Date data;   
    
    public void setCodigo(int codigo){
        this.codigo = codigo;
    }
	
	public void setData(Date data){
        this.data = data;
    }
}