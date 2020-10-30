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
import model.Disciplina;
import model.ICRUD;
import model.Professor;
import persistence.Conexao;

/**
 *
 * @author sala308b
 */
public class DisciplinaDAO extends Conexao implements ICRUD<Disciplina> {

     private PreparedStatement pstm;
    
    @Override
    public boolean incluir(Disciplina obj) {
         boolean excluiu = false;
         try {
             String sql = "delete from disciplina where  disciplina_id = ?";
             
             pstm=this.conecta().prepareStatement(sql);
             pstm.setInt(1, obj.getDisciplinaId());
             pstm.execute();
             excluiu = pstm.execute();
             
         } catch (SQLException ex) {
             Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         return excluiu;
    }

    @Override
    public Disciplina pesquisar( Disciplina obj) {
        Disciplina disciplina = null;
        String sql = "select * from disciplinas where disciplina_id = ?";
        
        try {
           
            pstm = this.conecta().prepareStatement(sql);
            pstm.setInt(1, obj.getDisciplinaId());
            pstm.execute();
            ResultSet rs = pstm.getResultSet();
            
            while (rs.first()){
            
            disciplina = new Disciplina();
            disciplina.setDisciplinaId(rs.getInt("disciplina_id"));
            disciplina.setNome(rs.getString("disciplina_nome"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
           return disciplina;             

        
    }

    @Override
    public boolean atualizar(Disciplina obj) {
       boolean atualizou = false; 
        
        try {
             String sql = "update disciplina set disciplinas_nome = ? where disciplina_id = ?";
             
             pstm=this.conecta().prepareStatement(sql);
             pstm.setString(1, obj.getNome());
             pstm.setInt(2, obj.getDisciplinaId());
             
             atualizou = pstm.execute();
             
            
         } catch (SQLException ex) {
             Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
        
         return atualizou;
        
    }


    @Override
    public boolean excluir(Disciplina obj) {
       boolean excluiu = false;
         try {
             String sql = "delete from disciplinas where  disciplina_id = ?";
             
             pstm=this.conecta().prepareStatement(sql);
             pstm.setInt(1, obj.getDisciplinaId());
             pstm.execute();
             excluiu = pstm.execute();
         } catch (SQLException ex) {
             Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         return excluiu;
    }


    @Override
    public List<Disciplina> listar() {
        List<Disciplina> disciplinas = null;
         try {
            
             Disciplina disciplina = new Disciplina ();
             
             String sql = "select * from disciplinas d";
             sql += " inner join professores_disciplinas pd";
             sql += "on p.profssores_id = pd.disciplina_id";
             sql += "inner join disciplinas d";
             sql += "on d.disciplinas_id = pd.disciplina_id";
             
             
             
             pstm = this.conecta().prepareStatement(sql);
             pstm.execute();
             ResultSet rs = pstm.getResultSet();
            // List<Disciplina> disciplinas = new ArrayList<Disciplina>();
             
             while (true){
                 while (rs.first()){
                     
                     disciplina.setDisciplinaId(rs.getInt("d.disciplina_id"));
                     disciplina.setNome(rs.getString ("d.disciplina_nome") );
                     
                  //   Disciplina disciplina = new Disciplina();
                     disciplina.setDisciplinaId(rs.getInt("d.disciplina_id"));
                     disciplina.setNome(rs.getString("d.disciplina_nome"));
                     
                     disciplinas.add(disciplina);
                 }
                 disciplina.setDisciplinas(disciplinas);
                 disciplinas.add(disciplina);
                 
                 if(!rs.next()){
                     break;
                 }
             
             }
         } catch (SQLException ex) {
             Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return disciplinas;
    }

    @Override
    public Disciplina exibir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
