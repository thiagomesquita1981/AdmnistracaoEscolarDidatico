/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.mysql.cj.protocol.Resultset;
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
public class ProfessorDAO extends Conexao implements ICRUD<Professor> {

     private PreparedStatement pstm;
     
    @Override
    public boolean incluir(Professor obj) {
        boolean incluiuProfessor = false;
         try {
             
             
             String sql = "insert into professores (professor_nome)";
             sql+= "values (?)";
             pstm=this.conecta().prepareStatement(sql);
             pstm.setString(1, obj.getNome());
             
             incluiuProfessor = pstm.execute();
             
             if (incluiuProfessor && ! obj.getDisciplinas().isEmpty()){
                 String sql2 = "insert into professores_disciplina (professor_id, disciplina_id)";
                 sql2 += "values (?,?)";
                 
                 pstm = this.conecta().prepareStatement(sql2);
                 
                    for( Disciplina disciplina:obj.getDisciplinas()){
                        pstm.setInt(1, obj.getId());
                        pstm.setInt(2, disciplina.getDisciplinaId());
                        pstm.execute();
                    }
                 
             }
             
             pstm.close();
             
         } catch (SQLException ex) {
             Logger.getLogger(ProfessorDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        return incluiuProfessor;
    
    }

    @Override
    public Professor pesquisar(Professor obj) {
        Professor professor = new Professor ();
            
            String sql = "select * from professores p";
                   sql += " inner join professores_disciplinas pd";
                   sql += "on p.profssores_id = pd.professor_id";
                   sql += "inner join disciplinas d";
                   sql += "on d.disciplinas_id = pd.disciplina_id";
                   sql += "and professor_id = ? ";
        try {
            
            pstm = this.conecta().prepareStatement(sql);
            pstm.setInt(1, obj.getId());
            pstm.execute();
            ResultSet rs = pstm.getResultSet();
            List<Disciplina> disciplinas = new ArrayList<Disciplina>();
            
            while (rs.first()){
            
              professor.setId(rs.getInt("p.professor_id"));
              professor.setNome(rs.getString ("p.professor_nome") );
              
              Disciplina disciplina = new Disciplina();
              disciplina.setDisciplinaId(rs.getInt("d.disciplina_id"));
              disciplina.setNome(rs.getString("d.disciplina_nome"));
              
              disciplinas.add(disciplina);
            }
            professor.setDisciplinas(disciplinas);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ProfessorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return professor;
        
    }

    @Override
    public boolean atualizar(Professor obj) {
          boolean atualizou = false; 
        
        try {
             String sql = "update profesores set professores_nome = ? where professor_id = ?";
             
             pstm=this.conecta().prepareStatement(sql);
             pstm.setString(1, obj.getNome());
             pstm.setInt(2, obj.getId());
             
             atualizou = pstm.execute();
             
            
         } catch (SQLException ex) {
             Logger.getLogger(ProfessorDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
        
         return atualizou;
        
    }

    @Override
    public boolean excluir(Professor obj) {
        boolean excluiu = false;
         try {
             String sql = "delete from professores where  professor_id = ?";
             
             pstm=this.conecta().prepareStatement(sql);
             pstm.setInt(1, obj.getId());
             pstm.execute();
             excluiu = pstm.execute();
         } catch (SQLException ex) {
             Logger.getLogger(ProfessorDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         return excluiu;
    }

    @Override
    public List<Professor> listar() {
         List<Professor> professores = null;
         try {
            
             Professor professor = new Professor ();
             
             String sql = "select * from professores p";
             sql += " inner join professores_disciplinas pd";
             sql += "on p.profssores_id = pd.professor_id";
             sql += "inner join disciplinas d";
             sql += "on d.disciplinas_id = pd.disciplina_id";
             
             
             
             pstm = this.conecta().prepareStatement(sql);
             pstm.execute();
             ResultSet rs = pstm.getResultSet();
             List<Disciplina> disciplinas = new ArrayList<Disciplina>();
             
             while (true){
                 while (rs.first()){
                     
                     professor.setId(rs.getInt("p.professor_id"));
                     professor.setNome(rs.getString ("p.professor_nome") );
                     
                     Disciplina disciplina = new Disciplina();
                     disciplina.setDisciplinaId(rs.getInt("d.disciplina_id"));
                     disciplina.setNome(rs.getString("d.disciplina_nome"));
                     
                     disciplinas.add(disciplina);
                 }
                 professor.setDisciplinas(disciplinas);
                 professores.add(professor);
                 
                 if(!rs.next()){
                     break;
                 }
             
             }
         } catch (SQLException ex) {
             Logger.getLogger(ProfessorDAO.class.getName()).log(Level.SEVERE, null, ex);
         } 
         return professores;
    }
    @Override
    public Professor exibir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
