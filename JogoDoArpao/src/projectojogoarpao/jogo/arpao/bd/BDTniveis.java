/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectojogoarpao.jogo.arpao.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import projectojogoarpao.excepcoes.ExcecaoSQL;
import projectojogoarpao.excepcoes.ExcepcaoSemBolas;

/**
 *
 * @author francisco
 */
public class BDTniveis extends BDJogoArpao {

    public BDTniveis() {
        super();
    }

    public DadosNivel getNivel(int nivel) {
        DadosNivel dadosNivel = new DadosNivel();
        try {
            PreparedStatement ps = ligacao.prepareStatement(
                    "Select * from niveis "
                    + "where niveis.nivel = ?");
            ps.setInt(1, nivel);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                ps.close();
                return null;
            }
            dadosNivel.num = nivel;
            dadosNivel.fundo = rs.getString("fundo");
            dadosNivel.xPersonagem = rs.getInt("xpersonagem");
            dadosNivel.yPersonagem = rs.getInt("ypersonagem");
            dadosNivel.bolas = getBolas(dadosNivel.num);
            dadosNivel.estrelas = getEstrelas(dadosNivel.num);
            ps.close();
            ligacao.commit();
        } catch (SQLException ex) {
            throw new ExcecaoSQL(ex);
        }
        return dadosNivel;
    }

    public ArrayList<DadosBola> getBolas(int nivel) {
        try {
            ArrayList<DadosBola> bolas = null;
            PreparedStatement ps = ligacao.prepareStatement(
                    "select tipo,nivel,x,y,vx,vy from bolas "
                    + "where nivel = ?");
            ps.setInt(1, nivel);
            ResultSet rs = ps.executeQuery();
            bolas = new ArrayList<DadosBola>();
            while (rs.next()) {
                DadosBola bola = new DadosBola();
                bola.tipo = rs.getInt("tipo");
                bola.x = rs.getInt("x");
                bola.y = rs.getInt("y");
                bola.vx = rs.getInt("vx");
                bola.vy = rs.getInt("vy");
                bolas.add(bola);
            }
            ps.close();
            return bolas;
        } catch (SQLException ex) {
            throw new ExcecaoSQL(ex);
        }
    }

    public ArrayList<DadosEstrela> getEstrelas(int nivel) {
        try {
            ArrayList<DadosEstrela> estrelas = null;
            PreparedStatement ps = ligacao.prepareStatement(
                    "select tamanho,nivel,x,y from estrelas "
                    + "where nivel = ?");
            ps.setInt(1, nivel);
            ResultSet rs = ps.executeQuery();
            estrelas = new ArrayList<DadosEstrela>();
            while (rs.next()) {
                DadosEstrela estrela = new DadosEstrela();
                estrela.tamanho= rs.getInt("tamanho");
                estrela.x = rs.getInt("x");
                estrela.y = rs.getInt("y");
                estrelas.add(estrela);
            }
            ps.close();
            return estrelas;
        } catch (SQLException ex) {
            throw new ExcecaoSQL(ex);
        }
    }

    
    public void criaNivel(DadosNivel dadosNivel) {
        PreparedStatement ps = null;
        try {
            
            if (dadosNivel.bolas.isEmpty()) {
                throw new ExcepcaoSemBolas("O nivel não pode ser gravado,"
                        + " porque não tem bolas");
            }
            ps = ligacao.prepareStatement("insert or replace into "
                    + "niveis (\"nivel\", \"fundo\", \"xpersonagem\",\"ypersonagem\")"
                    + " values (?, ?, ?, ?);");
            ps.setInt(1, dadosNivel.num);
            ps.setString(2, dadosNivel.fundo);
            ps.setInt(3, dadosNivel.xPersonagem);
            ps.setInt(4, dadosNivel.yPersonagem);
            ps.execute();

            ps = ligacao.prepareStatement("delete from bolas where \"nivel\"=?");
            ps.setInt(1, dadosNivel.num);
            ps.execute();

            ps = ligacao.prepareStatement("delete from estrelas where \"nivel\"=?");
            ps.setInt(1, dadosNivel.num);
            ps.execute();

            for (int i = 0; i < dadosNivel.bolas.size(); i++) {
                DadosBola dadosBola = dadosNivel.bolas.get(i);
                ps = ligacao.prepareStatement("insert into bolas "
                        + "(\"tipo\", \"nivel\", \"x\", \"y\", \"vx\", \"vy\") "
                        + "values (?, ?, ?, ?, ?, ?);");
                ps.setInt(1, dadosBola.tipo);
                ps.setInt(2, dadosNivel.num);
                ps.setInt(3, dadosBola.x);
                ps.setInt(4, dadosBola.y);
                ps.setInt(5, dadosBola.vx);
                ps.setFloat(6, dadosBola.vy);
                ps.execute();
            }
            
            for (int i = 0; i < dadosNivel.estrelas.size(); i++) {
                DadosEstrela dadosEstrela = dadosNivel.estrelas.get(i);
                ps = ligacao.prepareStatement("insert into estrelas "
                        + "(\"tamanho\", \"nivel\", \"x\", \"y\") "
                        + "values (?, ?, ?, ?);");
                ps.setInt(1, dadosEstrela.tamanho);
                ps.setInt(2, dadosNivel.num);
                ps.setInt(3, dadosEstrela.x);
                ps.setInt(4, dadosEstrela.y);
                ps.execute();
            }            
            
            ligacao.commit();
            ps.close();
        } catch (SQLException ex) {
            throw new ExcecaoSQL(ex);
        }
    }
}
