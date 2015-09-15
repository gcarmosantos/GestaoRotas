/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.preservarecicla.gestaorota.dao.util;

import br.com.preservarecicla.gestaorota.model.Rota;
import br.com.preservarecicla.gestaorota.model.vo.ConsultaGestaoRotaVo;
import br.com.preservarecicla.gestaorota.model.vo.RelatorioReciboVo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gustavo
 */
public class BancoUtil {
    
    //private static final String USER = "preservarecicla";
    private static final String USER = "root";
    private static final String PASSWORD = "root123";
    private static final String PORTA = "3306";
    private static final String SERVER = "localhost";
    //private static final String SERVER = "mysql01.preservarecicla.com.br";
    private static final String DATABASE = "preservarecicla";
    //private static final String DATABASE = "controleoleo";
    private static final String  URL = "jdbc:mysql://" + SERVER + ":" + PORTA + "/" + DATABASE;

    private static Connection conexao;
            
    
    private static Connection getConnection() throws ClassNotFoundException, SQLException{
        if (conexao == null){
            Class.forName("com.mysql.jdbc.Driver");
            conexao = DriverManager.getConnection(URL,USER,PASSWORD);  
        }
        return conexao;
    }
            
    public static void main(String args[]){
        try {
            BancoUtil banco = new BancoUtil();
            Connection conexao = getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BancoUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BancoUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }        
            
    public ResultSet retornaResult(String sql) throws ClassNotFoundException,SQLException{
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public List<Rota> listarGestaoRota(ConsultaGestaoRotaVo vo) throws SQLException, ClassNotFoundException {
            
        String sql = "select r.id,data,r.codpontocoleta, r.codCliente,c.nome,endereco,cidade,quilometragem,litros,valorLitro,gordura,pagou,valorPagamento,justificativa from cadrota r";
        sql += " inner join cadcliente c on c.id = r.codcliente";
        if (vo.getDataIni()!=null && !"".equals(vo.getDataIni()) && vo.getDataFim()!=null && !"".equals(vo.getDataFim())){
             sql += " where data >= '" + vo.getDataIni() + "' and data <= '" + vo.getDataFim() + "'";
        }
        sql += " order by r.data,r.endereco ";
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Rota rota = null;
        List<Rota> listaRota = new ArrayList<Rota>();
        while(rs.next()){
            rota = new Rota();
            rota.setId(rs.getInt(1));
            rota.setData(rs.getString("data"));
            rota.setCodCliente(rs.getInt("codCliente"));
            rota.setEndereco(rs.getString("endereco"));
            rota.setCidade(rs.getString("cidade"));
            rota.setQuilometragem(rs.getLong("quilometragem"));
            rota.setLitros(rs.getLong("litros"));
            rota.setValorLitro(rs.getDouble("valorLitro"));
            rota.setGordura(rs.getLong("gordura"));
            rota.setPagou(rs.getString("pagou"));
            rota.setValorPagamento(rs.getDouble("valorPagamento"));
            rota.setJustificativa(rs.getString("justificativa"));
            rota.setNomeCliente(rs.getString("nome"));
            rota.setCodPontoColeta(rs.getInt("codpontocoleta"));
            listaRota.add(rota);
        }
        return listaRota;
    }   
            

        public List<Rota> listarRota(String data) throws ClassNotFoundException, SQLException{
            
            String sql = "select r.id as idRota,c.id as codCliente,r.codpontocoleta,r.data,c.nome,r.cidade,r.endereco,p.portePonto from cadcliente c inner join cadpontocoleta p";
            sql += " on p.codCliente = c.id inner join cadrota r on r.codpontocoleta = p.id where r.data = ? order by r.endereco";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            if (data!=null && !"".equals(data)){
                ps.setString(1, data);
            }    
            ResultSet rs = ps.executeQuery();
            Rota rota = null;
            List<Rota> listaRota = new ArrayList<Rota>();
            while (rs.next()){
                rota = new Rota();
                rota.setId(rs.getInt("idRota"));
                rota.setCodCliente(rs.getInt("codCliente"));
                rota.setNomeCliente(rs.getString("nome"));
                rota.setData(rs.getString("data"));
                rota.setEndereco(rs.getString("endereco"));
                rota.setCidade(rs.getString("cidade"));
                rota.setPortePonto(rs.getString("portePonto"));
                rota.setCodPontoColeta(rs.getInt("codpontocoleta"));
                rota.setNomeCliente(rs.getString("nome"));
                listaRota.add(rota);
            }    
            return listaRota;
        }

    public void salvarRota(List<Rota> listaRota) throws ClassNotFoundException, SQLException {
        String sql = "";
        Statement st = getConnection().createStatement();
        for (Rota rota:listaRota){
            if (rota.getId() == null || rota.getId().intValue() == 0){
                sql = "insert into cadrota (data, codcliente, cidade, endereco, codPontoColeta, portePonto,codFuncionario,codVeiculo)";
                sql += " values ('" + rota.getData() + "'," + rota.getCodCliente() + ",'" + rota.getCidade() + "','" + rota.getEndereco() + "'," + rota.getCodPontoColeta() + ",";
                sql += "'" + rota.getPortePonto() + "'," + rota.getCodFuncionario() + "," + rota.getCodVeiculo() + ")";
            }
            else{
                sql = "update cadrota set data = '" + rota.getData() + "',codCliente = " + rota.getCodCliente() + ",cidade = '" + rota.getCidade() + "',";
                sql +=  " endereco = '" + rota.getEndereco() + "',codpontocoleta = " + rota.getCodPontoColeta() + ",portePonto = '" + rota.getPortePonto() + "',";
                sql += " codFuncionario = " + rota.getCodFuncionario() + ",codVeiculo = " + rota.getCodVeiculo() + ",";
                sql += " litros = " + rota.getLitros() + ",valorPagamento = " + rota.getValorPagamento() + ", gordura = " + rota.getGordura() + ",";
                sql += " valorTotalRecebido = " + (rota.getLitros()==null||rota.getLitros().longValue()==0 ?0:rota.getLitros().longValue()) * (rota.getValorLitro()==null||rota.getValorLitro().longValue()==0?0:rota.getValorLitro().doubleValue()) + ",valorLitro = " + rota.getValorLitro() + "";
                sql += " where id = " + rota.getId() + "";
            }
            st.addBatch(sql);
        }
        st.executeBatch();
        st.close();
        st = null;
    } 

    public void salvarGestaoRota(List<Rota> listaRota) throws ClassNotFoundException, SQLException {
        String sql = "";
        Statement st = getConnection().createStatement();
        for (Rota rota:listaRota){
            if (rota.getId() == null || rota.getId().intValue() == 0){
                sql = "insert into cadrota (id,data,codpontocoleta,codCliente,endereco,cidade,quilometragem,litros,gordura,pagou,valorPagamento,justificativa)";
                sql += " values ('" + rota.getData() + "'," + rota.getCodPontoColeta() + "," + rota.getCodCliente() + ",'" + rota.getEndereco() + "','" + rota.getCidade() + "',";
                sql += "" + rota.getQuilometragem() + "'," + rota.getLitros() + "," + rota.getGordura() + ",'" + rota.getPagou() + "'," + rota.getValorPagamento() + ",'" + rota.getJustificativa() + "')";
            }
            else{
                sql = "update cadrota set data = '" + rota.getData() + "',codCliente = " + rota.getCodCliente() + ",cidade = '" + rota.getCidade() + "',";
                sql +=  " endereco = '" + rota.getEndereco() + "',codpontocoleta = " + rota.getCodPontoColeta() + ",portePonto = '" + rota.getPortePonto() + "' where id = " + rota.getId() + "";
            }
            st.addBatch(sql);
        }
        st.executeBatch();
        st.close();
        st = null;
    } 
    
    public void atualizarRecibosImpressos(List<RelatorioReciboVo> listaRelatorio) throws ClassNotFoundException, SQLException{
        StringBuilder bf = new StringBuilder();
        Statement st = getConnection().createStatement();
        for (RelatorioReciboVo vo:listaRelatorio){
            bf.append("update cadrota set imprimiuRecibo = 1 where id = " + vo.getIdRota());
            st.addBatch(bf.toString());
            bf = new StringBuilder();
        }
        st.executeBatch();
        st.close();
        st = null;            
    }
    
}
