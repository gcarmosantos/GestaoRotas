/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.preservarecicla.gestaorota.layout.util;

import java.sql.SQLException;
import java.util.Map;
import javax.swing.JComboBox;

/**
 *
 * @author Gustavo
 */
public class PreencheCombos {
    
    public void retornaComboVeiculo(JComboBox combo,Map map) throws SQLException, ClassNotFoundException{
        String sql = "select id,placa from cadveiculo order by placa";
        new LayoutUtil().retornaComboPreenchida(sql,combo,map);
    }
    
    public void retornaComboFuncionarioByCargo(String cargo,JComboBox combo,Map map) throws SQLException, ClassNotFoundException{
        
        String sql = "select f.id,f.nome from cadfuncionario f inner join cadcargo c on f.codcargo = c.id ";
        if (cargo != null && !"".equals(cargo)){
            sql += " where upper(c.descricao) = '" + cargo + "'";
        }
        sql += " order by nome";
        new LayoutUtil().retornaComboPreenchida(sql,combo,map);
    }
    
    public void retornaComboEmpresa(JComboBox combo,Map map) throws SQLException, ClassNotFoundException{
        String sql = "select id,nome from cadcliente order by nome";
        new LayoutUtil().retornaComboPreenchida(sql,combo,map);
    }
            
    
    
}
