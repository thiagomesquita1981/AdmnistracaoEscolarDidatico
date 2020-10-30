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
 * 
 * @author sala308b
 */
public class AlunoDAO extends Conexao implements ICRUD<Aluno> {
    
    private PreparedStatement pstm;

    @Override
    public boolean incluir(Aluno obj) {
    {
            try {
                String sql = " insert into alunos ( aluno_nome,curso_id)";
                sql += "values (?,?)";
                pstm = this.conecta().prepareStatement(sql);
                pstm.setString(1,obj.getNome());
                pstm.setInt (2,obj.getCurso().getCursoId());
                pstm.execute();
                pstm.close();
                
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
         
            return false;
            
            
        }
    }

    @Override
    public Aluno pesquisar(Aluno obj) {
        Aluno aluno = null;
        String sql = "select * from alunos where aluno_id = ?"; // or aluno_nome = ? ";
        try {
            pstm= this.conecta().prepareStatement(sql);
            pstm.setInt(1, obj.getAlunoId());
           // pstm.setString(2, obj.getNome());
            pstm.execute();
            ResultSet rs = pstm.getResultSet();
            
            while (rs.first()){
            
            aluno = new Aluno();
            aluno.setAlunoId(rs.getInt("aluno_id"));
            aluno.setNome(rs.getString("aluno_nome"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return aluno;
    }

    @Override
    public boolean atualizar(Aluno obj) {
        try {
            String sql = "update alunos set aluno_nome = ?, curso_id = ?";
            pstm = this.conecta().prepareStatement(sql);
            pstm.setString(1, obj.getNome());
            pstm.setInt(2,obj.getCurso().getCursoId());
            pstm.execute();
            pstm.close();
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public boolean excluir(Aluno obj) {
        try {
            String sql = "delete from alunos where aluno_id = ?";
            pstm = this.conecta().prepareCall(sql);
            pstm.setInt(1,obj.getAlunoId());
            pstm.execute();
            pstm.close();
            
           
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
           return false;
        
        
    }

    @Override
    public List<Aluno> listar() {
       List<Aluno> lista = null;
        try {
        lista = new ArrayList<Aluno>();
        
        String sql= "select * from alunos";
                pstm = this.conecta().prepareStatement(sql);
                //retorna o resultado da consulta
                pstm.execute();
                ResultSet rs = pstm.getResultSet();
                
                while (rs.next()) {
                    Aluno aluno = new Aluno();
                    aluno.setAlunoId(rs.getInt("aluno_id"));
                    aluno.setNome(rs.getString("aluno_nome"));
                   aluno.setDataNascimento(rs.getDate("data_nascimento"));
                   Curso curso =new Curso();
                   curso.setCursoId(rs.getInt("curso_id"));
                   aluno.setCurso(curso);
                   lista.add(aluno);
                  
                }
                
                rs.close();
                pstm.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        
       return lista;
    }

    @Override
    public Aluno exibir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
