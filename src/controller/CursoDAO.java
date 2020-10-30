/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Aluno;
import model.Curso;
import model.ICRUD;
import persistence.Conexao;

/**
 *
 * @author sala308b
 */
public class CursoDAO extends Conexao implements ICRUD<Curso>{

   
    private PreparedStatement pstm;
    
    @Override
    public boolean incluir(Curso obj) {
        
        boolean incluiu = false;
        
        try {
            String sql = "insert into curso (curso_nome,carga_horaria) ";
            sql += " values (?, ?)";
           
                pstm = this.conecta().prepareStatement(sql);
                pstm.setString(1, obj.getNome());
                pstm.setInt(2, obj.getCargaHoraria());
                
                incluiu = pstm.execute();
                
                
                
            } catch (SQLException ex) {
                Logger.getLogger(CursoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return incluiu;
            
        }
        
    @Override
    public Curso pesquisar(Curso obj ) {
            Curso curso = null;
            String sql = "select * from curso where curso_id = ?";
            
        
        try {
            
            pstm = this.conecta().prepareStatement(sql);
            pstm.setInt(1,obj.getCursoId());
            pstm.execute();
            
            ResultSet rs = pstm.getResultSet();
            
            while (rs.first()){
            
            curso = new Curso();
            curso.setCursoId(rs.getInt("curso_id"));
            curso.setNome(rs.getString("curso_nome"));
        }
            
        } catch (SQLException ex) {
            Logger.getLogger(CursoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return curso;
       
    }    
    

    @Override
    public boolean atualizar(Curso obj) {
        boolean atualizou = false;
        try {
            String sql = "update curso set curso_nome = ?, curso_id = ?";
            
            pstm=this.conecta().prepareStatement(sql);
            pstm.setString(1, obj.getNome());
            
            pstm.setInt(2, obj.getCursoId());
            atualizou = pstm.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(CursoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
     return atualizou;
     
    }

    @Override
    public boolean excluir(Curso obj) {
          try {
            String sql = "delete from curso where curso_id = ?";
            pstm = this.conecta().prepareCall(sql);
            pstm.setInt(1,obj.getCursoId());
            pstm.execute();
            pstm.close();
            
           
        } catch (SQLException ex) {
            Logger.getLogger(CursoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
           return false;
     }

    @Override
    public List<Curso> listar() {
        List<Curso> lista = null;
        try {
        lista = new ArrayList<Curso>();
        
        String sql= "select * from cursos";
                pstm = this.conecta().prepareStatement(sql);
                //retorna o resultado da consulta
                pstm.execute();
                ResultSet rs = pstm.getResultSet();
                
                while (rs.next()) {
                    Curso curso = new Curso();
                    curso.setCursoId(rs.getInt("curso_id"));
                    curso.setNome(rs.getString("curso_nome"));
                    
                    curso.setCargaHoraria(rs.getInt("carga_horaria"));
                   lista.add(curso);
                }
                
                rs.close();
                pstm.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
         return lista;
    }

    @Override
    public Curso exibir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
