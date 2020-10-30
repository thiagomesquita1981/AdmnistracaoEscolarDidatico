/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author Sala308b
 */
public interface ICRUD<T> {
    
    public boolean incluir (T obj);
    public T pesquisar (T obj);
    public boolean atualizar (T obj);
    public boolean excluir (T obj);
    public List<T> listar ();
    public T exibir();
}
