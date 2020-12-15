/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.estudocaso.exception;

/**
 *
 * @author helder
 */
public class NumeroParcelasException extends NegocioException{
    public NumeroParcelasException(){
        super("Número de parcelas é inválido!");
    }        
}
