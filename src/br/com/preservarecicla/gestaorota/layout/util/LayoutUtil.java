/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.preservarecicla.gestaorota.layout.util;

import br.com.preservarecicla.gestaorota.dao.util.BancoUtil;
import br.com.preservarecicla.gestaorota.model.vo.ConsultaGestaoRotaVo;
import br.com.preservarecicla.gestaorota.model.vo.ConsultaLogisticaVo;
import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

/**
 *
 * @author Gustavo
 */
public class LayoutUtil {
    
    public void retornaComboPreenchida(String sql,JComboBox combo,Map<String,Integer> map) throws SQLException, ClassNotFoundException{
        BancoUtil banco = new  BancoUtil();
        ResultSet rs = banco.retornaResult(sql);      
                DefaultComboBoxModel model;
       //ComboBox Items have gotten from Data Base initially.
        Vector comboBoxItems = new Vector();
        while (rs.next()){
            comboBoxItems.addElement(rs.getString(2));
            map.put(rs.getString(2),rs.getInt(1));
        }
        model = new DefaultComboBoxModel(comboBoxItems);
        combo.setModel(model);
        combo.setSelectedIndex(-1);
    }
    
    public void preencheTabelaLogistica(JTable tabela,Map map,ConsultaLogisticaVo vo) throws ClassNotFoundException, SQLException{
        PreencheTabelas tabelas = new PreencheTabelas();
        tabelas.preencheTabelaLogistica(tabela, map,vo);
    }
    
    public void preencheTabelaRota(JTable tabela,Map map,String data){
        PreencheTabelas tabelas = new PreencheTabelas();
        tabelas.preencheTabelaRota(tabela, map,data);
    }
    
    public void preencheTabelaGestaoRota(JTable tabela,ConsultaGestaoRotaVo vo) throws SQLException, ClassNotFoundException{
        PreencheTabelas tabelas = new PreencheTabelas();
        tabelas.preencheTabelaGestaoRota(tabela, vo);
    }
    
    
}
