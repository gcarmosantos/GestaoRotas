/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.preservarecicla.gestaorota.layout.util;

import br.com.preservarecicla.gestaorota.dao.util.BancoUtil;
import br.com.preservarecicla.gestaorota.layout.GestaoRota;
import br.com.preservarecicla.gestaorota.model.Rota;
import br.com.preservarecicla.gestaorota.model.vo.ConsultaGestaoRotaVo;
import br.com.preservarecicla.gestaorota.model.vo.ConsultaLogisticaVo;
import br.com.preservarecicla.gestaorota.service.GestaoService;
import br.com.preservarecicla.gestaorota.service.GestaoServiceImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Gustavo
 */
public class PreencheTabelas {
    
    public void preencheTabelaRotaAnterior(JTable tabela){
         DefaultTableModel tabelaRotaAnterior = (DefaultTableModel)tabela.getModel(); 
         tabelaRotaAnterior.setNumRows(0);
         BancoUtil banco = new BancoUtil();
        // String sql = "select "
         //ResultSet rs = banco.retornaResult(null)
         
    }
    
    
    public void preencheTabelaLogistica(JTable tabela,Map map,ConsultaLogisticaVo vo) throws ClassNotFoundException, SQLException{
         BancoUtil banco = new BancoUtil();
         String sql = "select null as codRota,p.id as codPontoColeta,c.id as codCliente,c.nome,p.cidade,p.endereco,p.portePonto from cadcliente c inner join cadpontocoleta p";
         sql += " on p.codCliente = c.id where 1=1 ";
         
         if (vo.getCodCliente()!=null && vo.getCodCliente().intValue() > 0){
             sql += " and c.id = " + vo.getCodCliente().intValue() + "";
         }
         if (vo.getCidade()!=null && !"".equals(vo.getCidade())){
             sql += " and p.cidade like '%" + vo.getCidade() + "%'";
         }
         if (vo.getEndereco()!=null && !"".equals(vo.getEndereco())){
             sql += " and p.endereco like '%" + vo.getEndereco() + "%'";
         }
         sql += " order by p.endereco";
         
         ResultSet rs = banco.retornaResult(sql);
         tabela.setModel(new DefaultTableModel(  
            new Object [][]{},  
            new String[] {"id rota","CodPontoColeta","idCliente", "Nome", "Cidade", "Endereço",  
           "Porte Ponto"}));  
         
         tabela.getColumnModel().getColumn(0).setPreferredWidth(150);  
         tabela.getColumnModel().getColumn(1).setPreferredWidth(150);  
         tabela.getColumnModel().getColumn(2).setPreferredWidth(150);  
         tabela.getColumnModel().getColumn(3).setPreferredWidth(370);  
         tabela.getColumnModel().getColumn(4).setPreferredWidth(270);  
         tabela.getColumnModel().getColumn(5).setPreferredWidth(370);  
         tabela.getColumnModel().getColumn(6).setPreferredWidth(200);  
         
         
         DefaultTableModel model = (DefaultTableModel)tabela.getModel(); 
         model.setNumRows(0);
         while (rs.next()){
             model.addRow(new Object[] {"",rs.getInt("codPontoColeta"),rs.getInt("codCliente"),rs.getString("nome"),rs.getString("cidade"),rs.getString("endereco"),rs.getString("portePonto")});
             map.put(rs.getString("endereco"), rs.getInt("codPontoColeta"));
         }    
                
         tabela.setModel(model);
         tabela.repaint();
    }


    public void preencheTabelaRota(JTable tabela,Map map,String data){
        
        try {
            GestaoService serviceRota = new GestaoServiceImpl();
            List<Rota> listaRota = serviceRota.listarRota(data);
            if (listaRota.isEmpty()){
                JOptionPane.showMessageDialog(null, "Não existem dados de rota nessa data", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            tabela.setModel(new DefaultTableModel(  
            new Object [][]{},  
            new String[] {"id rota","idCliente","CodPontoColeta", "Nome", "Endereço",  
            "Cidade","Porte Ponto"}));  
         
            tabela.getColumnModel().getColumn(0).setPreferredWidth(150);  
            tabela.getColumnModel().getColumn(1).setPreferredWidth(150);  
            tabela.getColumnModel().getColumn(2).setPreferredWidth(150);  
            tabela.getColumnModel().getColumn(3).setPreferredWidth(370);  
            tabela.getColumnModel().getColumn(4).setPreferredWidth(400);  
            tabela.getColumnModel().getColumn(5).setPreferredWidth(250);  
            tabela.getColumnModel().getColumn(6).setPreferredWidth(150);  
            
           
            
            DefaultTableModel model = (DefaultTableModel)tabela.getModel(); 
            model.setNumRows(0);
            for (Rota rota:listaRota){
                  model.addRow(new Object[]{rota.getId(),rota.getCodCliente(),rota.getCodPontoColeta(),rota.getNomeCliente(),rota.getEndereco(),rota.getCidade(),rota.getPortePonto()} );  
                  TableColumn col = tabela.getColumnModel().getColumn(6);
                  String[] valoresComboPorte = new String[]{"Vermelho","Amarelo","Azul"};
                  col.setCellEditor(new ComboJTableEditor(valoresComboPorte));
                    col.setCellRenderer(new ComboJTableRenderer(valoresComboPorte));
            }

            tabela.setRowHeight(200);

            tabela.setModel(model);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(GestaoRota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestaoRota.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public void preencheTabelaGestaoRota(JTable tabela, ConsultaGestaoRotaVo vo) throws SQLException, ClassNotFoundException {
        GestaoService serviceRota = new GestaoServiceImpl();
        List<Rota> listaRota = new ArrayList<Rota>();
        listaRota = serviceRota.listarGestaoRota(vo);
        DefaultTableModel model = (DefaultTableModel)tabela.getModel(); 
            model.setNumRows(0);
            //"id rota","Data","Empresa", "Cidade", "Endereço", "Quilometragem","Litros","Gordura","Pagou","Valor pagamento","Justificativa"
            //Data	Empresa	Cidade	Endereço	Quilometragem (6)	litros (1) (2)	Gordura (1) (2)	Pagou (S/N) (4)	Valor de pagamento (3)	Justificativa (1)
            for (Rota rota:listaRota){
                  model.addRow(new Object[]{rota.getId(),rota.getCodPontoColeta(),rota.getCodCliente(), rota.getData(), rota.getNomeCliente(),rota.getCidade(),rota.getEndereco(),
                      rota.getQuilometragem(),rota.getLitros(),rota.getValorLitro(), rota.getGordura(),rota.getPagou(),rota.getValorPagamento(),rota.getJustificativa()} );  
            }
            tabela.setModel(model);
    }

}
