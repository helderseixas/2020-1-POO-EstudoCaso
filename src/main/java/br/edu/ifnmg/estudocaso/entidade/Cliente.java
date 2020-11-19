/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.estudocaso.entidade;

import java.util.Objects;

/**
 *
 * @author helder
 */
public abstract class Cliente {
    protected String codigo;
    protected String senha;
    protected String  nome;  
    
    public Cliente(String codigo, String nome, String senha){
        this.codigo = codigo;
        this.nome = nome;
        this.senha = senha;
    }
    
    public abstract String getDescricaoCliente();
    
    public void imprimirNome(){
        System.out.println(this.nome);
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public String getCodigo(){
        return this.codigo;
    }    
    
    public String getNome(){
        return this.nome;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }
}
