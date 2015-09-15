/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.preservarecicla.gestaorota.layout.util;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

/**
 *
 * @author Gustavo
 */
public class ComboJTableEditor extends DefaultCellEditor {
    public ComboJTableEditor(String[] items) {
        super(new JComboBox(items));
    }

}
