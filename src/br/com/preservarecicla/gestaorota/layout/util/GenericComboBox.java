/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.preservarecicla.gestaorota.layout.util;

import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

/**
 *
 * @author Gustavo
 */
public class GenericComboBox extends JComboBox<Object> {
    
    private Map<Integer,String> mapValues = new HashMap<Integer,String>();
    
    public GenericComboBox getGenericComboBox(ResultSet rs) throws SQLException{
        DefaultComboBoxModel model;
       //ComboBox Items have gotten from Data Base initially.
        Vector comboBoxItems = new Vector();
        while (rs.next()){
            comboBoxItems.addElement(rs.getString(2));
            getMapValues().put(rs.getInt(1), rs.getString(2));
        }
        GenericComboBox combo = new GenericComboBox();
        model = new DefaultComboBoxModel(comboBoxItems);
        combo.setModel(model);
        return combo;
    }

    /**
     * @return the mapValues
     */
    public Map<Integer,String> getMapValues() {
        return mapValues;
    }

    /**
     * @param mapValues the mapValues to set
     */
    public void setMapValues(Map<Integer,String> mapValues) {
        this.mapValues = mapValues;
    }

}