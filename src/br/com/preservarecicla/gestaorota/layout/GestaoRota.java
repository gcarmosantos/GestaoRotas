/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.preservarecicla.gestaorota.layout;

import br.com.preservarecicla.gestaorota.layout.util.ComboJTableEditor;
import br.com.preservarecicla.gestaorota.layout.util.ComboJTableRenderer;
import br.com.preservarecicla.gestaorota.layout.util.LayoutUtil;
import br.com.preservarecicla.gestaorota.layout.util.PreencheCombos;
import br.com.preservarecicla.gestaorota.model.Rota;
import br.com.preservarecicla.gestaorota.model.vo.ConsultaGestaoRotaVo;
import br.com.preservarecicla.gestaorota.model.vo.ConsultaLogisticaVo;
import br.com.preservarecicla.gestaorota.relatorio.RelatorioRecibo;
import br.com.preservarecicla.gestaorota.service.GestaoService;
import br.com.preservarecicla.gestaorota.service.GestaoServiceImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Gustavo
 */
public class GestaoRota extends javax.swing.JFrame {

    
    private Map<Integer,String> mapComboVeiculo = new HashMap<Integer,String>();
    private Map<Integer,String> mapComboFuncionario = new HashMap<Integer,String>();
    
    private Map<String,Integer> mapComboEmpresa = new HashMap<String,Integer>();

    private Map<Integer,String> mapTabelaEmprea = new HashMap<Integer,String>();

    private Map<Integer,String> mapTabelaRota = new HashMap<Integer,String>();
    
     private Map<String,Integer> mapTabelaLogistica = new HashMap<String,Integer>();

    
    /**
     * Creates new form GestaoRota
     */
    public GestaoRota() {
        initComponents();
        jTable3.setRowSelectionAllowed(true);
        jTable3.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
         jTable3.setModel(new DefaultTableModel(  
            new Object [][]{},  
            new String[] {"id rota","id pontoColeta","id cliente", "Nome", "Cidade", "Endereço",  
           "Porte Ponto"}));  

         jTable3.getColumnModel().getColumn(0).setPreferredWidth(150);  
         jTable3.getColumnModel().getColumn(1).setPreferredWidth(150);  
         jTable3.getColumnModel().getColumn(2).setPreferredWidth(150);  
         
         jTable3.getColumnModel().getColumn(3).setPreferredWidth(370);  
         jTable3.getColumnModel().getColumn(4).setPreferredWidth(270);  
         jTable3.getColumnModel().getColumn(5).setPreferredWidth(370);  
         jTable3.getColumnModel().getColumn(6).setPreferredWidth(150);  
        
         
         carregaDadosIniciaisGestaoRota();
         
        preencheCombos();
        
        jButton2.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent ae) {
                salvarRota();
            }
        });
        
    
        jButton3.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent ae) {
                adicionaLinhasTabelaRota();
            }
        });
        
        jButton4.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent ae) {
                excluiLinhasTabelaRota();
            } 
        });
    
        jButton8.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent ae) {
                imprimirRecibo();
            } 
        });
        
    
        jButton1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                LayoutUtil layout = new LayoutUtil();
                ConsultaLogisticaVo vo = new ConsultaLogisticaVo();
                if (jComboBox3.getSelectedIndex()!=-1){
                   vo.setCodCliente(new Integer(mapComboEmpresa.get(jComboBox3.getSelectedItem())));
                }
                if (jTextField2.getText()!=null && !"".equals(jTextField2.getText())){
                    vo.setCidade(jTextField2.getText());
                }    
                 if (jTextField1.getText()!=null && !"".equals(jTextField1.getText())){
                    vo.setEndereco(jTextField1.getText());
                }           
                try {
                    layout.preencheTabelaLogistica(jTable1, mapTabelaLogistica,vo);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GestaoRota.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(GestaoRota.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    
        jButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    LayoutUtil layout = new LayoutUtil();
                    String data = "";
                    SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar b = Calendar.getInstance();
                    if (jXDatePicker1.getDate()!=null){
                        b.setTime(jXDatePicker1.getDate());
                        b.add(Calendar.MONTH,-1);
                        data = sf.format(b.getTime());
                        layout.preencheTabelaRota(jTable3, mapTabelaRota,data);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Data para geração da rota é obrigatória", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                catch(Exception error){
                    JOptionPane.showMessageDialog(null, "Erro na geração da rota", "Erro", JOptionPane.ERROR_MESSAGE);
                }    
            }    
        });   
    
        jCheckBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    jCheckBox2.setSelected(false);
                    jXDatePicker2.setDate(null);
                    jXDatePicker3.setDate(null);
                    jXDatePicker2.setEnabled(false);
                    jXDatePicker3.setEnabled(false);
                }
            }
        });

        jCheckBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    jCheckBox1.setSelected(false);
                    jXDatePicker2.setEnabled(true);
                    jXDatePicker3.setEnabled(true);
                }
            }
        });
        
        
        jButton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LayoutUtil layout = new LayoutUtil();
                ConsultaGestaoRotaVo vo = new ConsultaGestaoRotaVo();
                if (jCheckBox1.isSelected()){
                    
                }
                else if (jCheckBox2.isSelected()) {
                    if (jXDatePicker2.getDate()!=null && jXDatePicker3.getDate()!=null){
                        vo.setDataIni(new SimpleDateFormat("dd/MM/yyyy").format(jXDatePicker2.getDate()));
                        vo.setDataFim(new SimpleDateFormat("dd/MM/yyyy").format(jXDatePicker3.getDate()));
                    }
                }
                try {
                    layout.preencheTabelaGestaoRota(jTable4,vo);
                } catch (SQLException ex) {
                    Logger.getLogger(GestaoRota.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GestaoRota.class.getName()).log(Level.SEVERE, null, ex);
                }
            }    
        });   
        
        jButton7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarGestaoRota();
            }    
        });   

        jTable3.setAutoResizeMode (JTable.AUTO_RESIZE_OFF); 
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable4.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }        

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();
        jLabel10 = new javax.swing.JLabel();
        jXDatePicker3 = new org.jdesktop.swingx.JXDatePicker();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setText("Placa do Veículo");
        jLabel3.setName(""); // NOI18N

        jLabel4.setText("Motorista");
        jLabel4.setName(""); // NOI18N

        jComboBox2.setName("cmbMotorista"); // NOI18N
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Data");
        jLabel1.setName(""); // NOI18N

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable3.setEditingColumn(0);
        jTable3.setEditingRow(0);
        jScrollPane3.setViewportView(jTable3);

        jLabel2.setText("Empresa");

        jComboBox3.setName("cmbEmpresa"); // NOI18N
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setName("tabelaRotasAnteriores"); // NOI18N
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel5.setText("Cidade");

        jTextField1.setName("txtEnderecoRota"); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel6.setText("Endereço");

        jTextField2.setName("txtCidadeRota"); // NOI18N
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton1.setText("Pesquisar");

        jButton2.setText("Salvar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Adicionar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Excluir");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Gerar Rota");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 396, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 954, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 913, Short.MAX_VALUE))
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(174, 174, 174)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(101, 398, Short.MAX_VALUE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(107, 107, 107)
                                        .addComponent(jButton3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton4)
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton5)
                        .addComponent(jButton2))
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(47, 47, 47))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Cadastro de Rotas", jPanel4);

        jLabel7.setText("Placa do Veículo");
        jLabel7.setName(""); // NOI18N

        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel8.setText("Motorista");
        jLabel8.setName(""); // NOI18N

        jComboBox5.setName("cmbMotorista"); // NOI18N
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Geral");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox2.setSelected(true);
        jCheckBox2.setText("Por Período");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jLabel9.setText("Data Inicial");
        jLabel9.setName(""); // NOI18N

        jXDatePicker2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker2ActionPerformed(evt);
            }
        });

        jLabel10.setText("Data Final");
        jLabel10.setName(""); // NOI18N

        jXDatePicker3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker3ActionPerformed(evt);
            }
        });

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable4.setEditingColumn(0);
        jTable4.setEditingRow(0);
        jScrollPane4.setViewportView(jTable4);

        jButton6.setText("Pesquisar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Salvar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Imprimir Recibo");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(171, 171, 171))
                            .addComponent(jComboBox5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jXDatePicker3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))))
                .addGap(50, 50, 50)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 954, Short.MAX_VALUE)
                    .addGap(20, 20, 20)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jCheckBox1)
                                .addComponent(jCheckBox2))
                            .addComponent(jXDatePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jXDatePicker3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6)
                            .addComponent(jButton7)
                            .addComponent(jButton8))))
                .addContainerGap(480, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(161, 161, 161)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(95, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("Gestão de Rota", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jTabbedPane2.getAccessibleContext().setAccessibleName("Cadastro de Rotas");
        jTabbedPane2.getAccessibleContext().setAccessibleDescription("Cadastro de Rotas");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jXDatePicker2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jXDatePicker2ActionPerformed

    private void jXDatePicker3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jXDatePicker3ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GestaoRota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestaoRota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestaoRota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestaoRota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new GestaoRota().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker3;
    // End of variables declaration//GEN-END:variables

    private void preencheCombo() {
        
    }

    /**
     * @return the mapComboFuncionario
     */
    public Map<Integer,String> getMapComboFuncionario() {
        return mapComboFuncionario;
    }

    /**
     * @param mapComboFuncionario the mapComboFuncionario to set
     */
    public void setMapComboFuncionario(Map<Integer,String> mapComboFuncionario) {
        this.mapComboFuncionario = mapComboFuncionario;
    }


    /**
     * @return the mapComboVeiculo
     */
    public Map<Integer,String> getMapComboVeiculo() {
        return mapComboVeiculo;
    }

    /**
     * @param mapComboVeiculo the mapComboVeiculo to set
     */
    public void setMapComboVeiculo(Map<Integer,String> mapComboVeiculo) {
        this.mapComboVeiculo = mapComboVeiculo;
    }

    private void preencheCombos(){
        try {
            PreencheCombos preencheCombo = new PreencheCombos();
            preencheCombo.retornaComboVeiculo(jComboBox1,mapComboVeiculo);
            preencheCombo.retornaComboFuncionarioByCargo("MOTORISTA",jComboBox2,mapComboFuncionario);
            preencheCombo.retornaComboEmpresa(jComboBox3,mapComboEmpresa);
            
        } catch (SQLException ex) {
            Logger.getLogger(GestaoRota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestaoRota.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    private void adicionaLinhasTabelaRota(){
        int[] linhas = jTable1.getSelectedRows();

        if (linhas.length == 0){
            JOptionPane.showMessageDialog(null, "É preciso selecionar pelo menos um linha da tabela para adicionar", "Aviso", JOptionPane.OK_OPTION);
            return;
        }
        
        DefaultTableModel modelTable1 = (DefaultTableModel) jTable1.getModel();
        DefaultTableModel modelTable3 = (DefaultTableModel) jTable3.getModel();
        
        for (int i = 0;i < linhas.length;i++){
            modelTable3.addRow(new Object[]{modelTable1.getValueAt(linhas[i], 0),modelTable1.getValueAt(linhas[i], 1),modelTable1.getValueAt(linhas[i], 2),
            modelTable1.getValueAt(linhas[i],3),modelTable1.getValueAt(linhas[i], 4),modelTable1.getValueAt(linhas[i], 5),modelTable1.getValueAt(linhas[i], 6)});
            TableColumn col = jTable3.getColumnModel().getColumn(6);
            String[] valoresComboPorte = new String[]{"Vermelho","Amarelo","Azul"};
            col.setCellEditor(new ComboJTableEditor(valoresComboPorte));
            col.setCellRenderer(new ComboJTableRenderer(valoresComboPorte));
        }
        
        jTable3.setModel(modelTable3);
        jTable3.setRowHeight(18);
        jTable3.repaint();
        
    }
    
    private void excluiLinhasTabelaRota(){
        int[] linhas = jTable3.getSelectedRows();
        if (linhas.length == 0){
            JOptionPane.showMessageDialog(null, "É preciso selecionar pelo menos um linha da tabela para excluir", "Aviso", JOptionPane.OK_OPTION);
            return;
        }

        DefaultTableModel modelTable3 = (DefaultTableModel) jTable3.getModel();
        for (int i = 0;i < linhas.length;i++){
            modelTable3.removeRow(linhas[i]);
        }
        jTable3.setModel(modelTable3);
    }
    
    private void carregaListaRota(){
    }
    
    private void salvarRota(){
        DefaultTableModel modelTable3 = (DefaultTableModel) jTable3.getModel();
        GestaoService gestaoService = new GestaoServiceImpl();
        //valida os campos obrigatórios
        //if (!validaDadosCadastroRota()){
        //    return;
        //}
        //id rota","CodPontoColeta","idCliente", "Nome", "Cidade", "Endereço",  
        //   "Porte Ponto"
        List<Rota> listaRota = new ArrayList<Rota>();
        for (int i = 0;i < modelTable3.getRowCount();i++){
            Rota rota = new Rota();
            if (modelTable3.getValueAt(i, 0)!=null && !"".equals(modelTable3.getValueAt(i, 0))){
                rota.setId(Integer.parseInt(modelTable3.getValueAt(i, 0).toString()));
            }
            if (modelTable3.getValueAt(i, 1)!=null && !"".equals(modelTable3.getValueAt(i, 1))){
                rota.setCodPontoColeta(Integer.parseInt(modelTable3.getValueAt(i, 1).toString()));
            }
            if (modelTable3.getValueAt(i, 2)!=null && !"".equals(modelTable3.getValueAt(i, 2))){
                rota.setCodCliente(Integer.parseInt(modelTable3.getValueAt(i, 2).toString()));
            }
            if (modelTable3.getValueAt(i, 4)!=null && !"".equals(modelTable3.getValueAt(i, 4))){
                rota.setCidade(modelTable3.getValueAt(i, 4).toString());
            }
            if (modelTable3.getValueAt(i, 5)!=null && !"".equals(modelTable3.getValueAt(i, 5))){
                rota.setEndereco(modelTable3.getValueAt(i, 5).toString());
            }
            if (modelTable3.getValueAt(i, 6)!=null && !"".equals(modelTable3.getValueAt(i, 6))){
                rota.setPortePonto(modelTable3.getValueAt(i, 6).toString());
            }
            if (!mapComboFuncionario.isEmpty()){
                rota.setCodFuncionario(new Integer(mapComboFuncionario.get(jComboBox2.getSelectedItem())));
            }
            if (!mapComboVeiculo.isEmpty()){
                rota.setCodVeiculo(new Integer(mapComboVeiculo.get(jComboBox1.getSelectedItem())));
            }
            rota.setData(jXDatePicker1.getDate()!=null?new SimpleDateFormat("dd/MM/yyyy").format(jXDatePicker1.getDate()):null);
            listaRota.add(rota);
        }
        try {
            gestaoService.salvarRota(listaRota);
            JOptionPane.showMessageDialog(null, "Rota gerada com succeso", "Cadastro de Rota",JOptionPane.INFORMATION_MESSAGE);
            modelTable3.setRowCount(0);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro na geração da rota", "Cadastro de Rota",JOptionPane.ERROR);
            Logger.getLogger(GestaoRota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Erro na geração da rota", "Cadastro de Rota",JOptionPane.ERROR);
            Logger.getLogger(GestaoRota.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean validaDadosCadastroRota(){
        if (jComboBox1.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(null, "O campo placa do veículo é obrigatório", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (jComboBox2.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(null, "O campo motorista é obrigatório", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    
    
    private void carregaDadosIniciaisGestaoRota(){

         jCheckBox1.setSelected(false);
         jCheckBox2.setSelected(false);
        
        
        jTable4.setRowSelectionAllowed(true);
         jTable4.setModel(new DefaultTableModel(  
            new Object [][]{},  
            new String[] {"id rota","id coleta","id cliente", "Data","Empresa", "Cidade", "Endereço", "Quilometragem","Litros","Valor Litro","Gordura","Pagou","Valor pagamento","Justificativa"}));  
         
         
         jTable4.getColumnModel().getColumn(0).setPreferredWidth(120);  
         jTable4.getColumnModel().getColumn(1).setPreferredWidth(120);  
         jTable4.getColumnModel().getColumn(2).setPreferredWidth(120);  
         jTable4.getColumnModel().getColumn(3).setPreferredWidth(150);  
         jTable4.getColumnModel().getColumn(4).setPreferredWidth(250);  
         jTable4.getColumnModel().getColumn(5).setPreferredWidth(250);  
         jTable4.getColumnModel().getColumn(6).setPreferredWidth(400);  
         jTable4.getColumnModel().getColumn(7).setPreferredWidth(150);  
         jTable4.getColumnModel().getColumn(8).setPreferredWidth(180);  
         jTable4.getColumnModel().getColumn(9).setPreferredWidth(120);
         jTable4.getColumnModel().getColumn(10).setPreferredWidth(120);
         jTable4.getColumnModel().getColumn(11).setPreferredWidth(200);
         jTable4.getColumnModel().getColumn(12).setPreferredWidth(200);
         jTable4.getColumnModel().getColumn(13).setPreferredWidth(500);
         
       
        jTable4.setAutoResizeMode (JTable.AUTO_RESIZE_OFF); 
        /*JScrollPane jScroll = new JScrollPane(jTable4); 
        jScroll.setHorizontalScrollBar(new JScrollBar(0)); 
        jTable4.setAutoResizeMode (JTable.AUTO_RESIZE_OFF);  
        jPanel1.add(jScroll);
        jPanel1.repaint();
        */        
     }

    private void salvarGestaoRota(){
        DefaultTableModel modelTable4 = (DefaultTableModel) jTable4.getModel();
        GestaoService gestaoService = new GestaoServiceImpl();
        if (!validaCamposGestaoRota()){
            return;
        }
        List<Rota> listaRota = new ArrayList<Rota>();
        for (int i = 0;i < modelTable4.getRowCount();i++){
            Rota rota = new Rota();
            if (modelTable4.getValueAt(i, 0)!=null && !"".equals(modelTable4.getValueAt(i, 0))){
                rota.setId(Integer.parseInt(modelTable4.getValueAt(i, 0).toString()));
            }
            if (modelTable4.getValueAt(i, 1)!=null && !"".equals(modelTable4.getValueAt(i, 1))){
                rota.setCodPontoColeta(Integer.parseInt(modelTable4.getValueAt(i, 1).toString()));
            }
            if (modelTable4.getValueAt(i, 2)!=null && !"".equals(modelTable4.getValueAt(i, 2))){
                rota.setCodCliente(Integer.parseInt(modelTable4.getValueAt(i, 2).toString()));
            }
            if (modelTable4.getValueAt(i, 3)!=null && !"".equals(modelTable4.getValueAt(i, 3))){
                rota.setData(modelTable4.getValueAt(i, 3).toString());
            }
            if (modelTable4.getValueAt(i, 4)!=null && !"".equals(modelTable4.getValueAt(i, 4))){
                rota.setNomeCliente(modelTable4.getValueAt(i, 4).toString());
            }
            if (modelTable4.getValueAt(i, 5)!=null && !"".equals(modelTable4.getValueAt(i, 5))){
                rota.setCidade(modelTable4.getValueAt(i, 5).toString());
            }

            if (modelTable4.getValueAt(i, 6)!=null && !"".equals(modelTable4.getValueAt(i, 6))){
                rota.setEndereco(modelTable4.getValueAt(i, 6).toString());
            }
            if (modelTable4.getValueAt(i, 7)!=null && !"".equals(modelTable4.getValueAt(i, 7))){
                rota.setQuilometragem(modelTable4.getValueAt(i, 7)!=null?new Long(modelTable4.getValueAt(i, 7).toString()):null);
            }
            if (modelTable4.getValueAt(i, 8)!=null && !"".equals(modelTable4.getValueAt(i, 8))){
                rota.setLitros(modelTable4.getValueAt(i, 8)!=null?new Long(modelTable4.getValueAt(i, 8).toString()):null);
            }
            
            if (modelTable4.getValueAt(i, 9)!=null && !"".equals(modelTable4.getValueAt(i, 9))){
                rota.setGordura(modelTable4.getValueAt(i, 9)!=null?new Long(modelTable4.getValueAt(i, 9).toString()):null);
            }

            if (modelTable4.getValueAt(i, 10)!=null && !"".equals(modelTable4.getValueAt(i, 10))){
                rota.setGordura(modelTable4.getValueAt(i, 10)!=null?new Long(modelTable4.getValueAt(i, 10).toString()):null);
            }

            if (modelTable4.getValueAt(i, 11 )!=null && !"".equals(modelTable4.getValueAt(i, 11))){
                rota.setPagou(modelTable4.getValueAt(i, 11).toString());
            }
            if (modelTable4.getValueAt(i, 12)!=null && !"".equals(modelTable4.getValueAt(i, 12))){
                rota.setValorPagamento(modelTable4.getValueAt(i, 12)!=null?new Double(modelTable4.getValueAt(i, 12).toString()):null);
            }
            if (modelTable4.getValueAt(i, 13)!=null && !"".equals(modelTable4.getValueAt(i, 13))){
                rota.setJustificativa(modelTable4.getValueAt(i, 13).toString());
            }
            listaRota.add(rota);
        }
        try {
            gestaoService.salvarRota(listaRota);
            JOptionPane.showMessageDialog(null, "Gestão de rota salva com sucesso", "Aviso", JOptionPane.OK_OPTION);
            modelTable4.setNumRows(0);
        } catch (SQLException ex) {
            Logger.getLogger(GestaoRota.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestaoRota.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void imprimirRecibo(){
        int[] qtdLinhas = jTable4.getSelectedRows();
        if (qtdLinhas.length == 0){
            JOptionPane.showMessageDialog(null, "É necessário selecionar pelo menos uma rota para gerar o recibo", "Aviso", JOptionPane.OK_OPTION);
            return;
        }
        RelatorioRecibo recibo = new RelatorioRecibo();
        List<Integer> linhasTabela = new ArrayList<Integer>();
        for (int i = 0;i<qtdLinhas.length;i++){
            linhasTabela.add(new Integer(jTable4.getModel().getValueAt(qtdLinhas[i], 0).toString()));
        }
        

        recibo.geraRelatorioRecibo(linhasTabela);
    }
    
    private boolean validaCamposGestaoRota(){
        boolean retorno = true;
        if (jTable4.getModel().getRowCount() == 0){
             JOptionPane.showMessageDialog(null, "É necessário ter rotas na tabela para salvar", "Aviso", JOptionPane.OK_OPTION);
             retorno = false;
        }
        return retorno;
    }
    
}
