/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.preservarecicla.gestaorota.relatorio;

import br.com.preservarecicla.gestaorota.dao.util.BancoUtil;
import br.com.preservarecicla.gestaorota.model.vo.RelatorioReciboVo;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Gustavo
 */
public class RelatorioRecibo {
    
    
    public void geraRelatorioRecibo(List<Integer> ids){
        
        List<RelatorioReciboVo> listaRelatorio = new ArrayList<RelatorioReciboVo>();
        RelatorioReciboVo relatorioVo =  new RelatorioReciboVo();
        StringBuilder bf = new StringBuilder();
        bf.append("select r.id,c.nome as nomeCliente,p.endereco,r.dataPagamento,r.litros,r.valorLitro from cadrota r inner join cadpontocoleta p");
        bf.append(" on r.codpontocoleta = p.id inner join cadcliente c on c.id = r.codcliente");
        bf.append(" where (r.imprimiuRecibo = 0 or r.imprimiuRecibo is null)");
        bf.append(" and r.id in (");
        for (int i = 0;i < ids.size();i++){
            bf.append(ids.get(i));
            bf.append(",");
        }
        String query = bf.toString().substring(0, bf.toString().length()-1);
        bf = new StringBuilder();
        bf.append(query);
        bf.append(") ");
        bf.append(" order by c.nome");
        BancoUtil bdUtil = new BancoUtil();
        try {
            ResultSet rs = bdUtil.retornaResult(bf.toString());
            while (rs.next()){
                relatorioVo.setNomeCliente(rs.getString("nomeCliente"));
                relatorioVo.setEnderecoCliente(rs.getString("endereco"));
                relatorioVo.setDataPagamento(rs.getString("dataPagamento"));
                relatorioVo.setQtdLitros(rs.getInt("litros"));
                relatorioVo.setValorLitro(rs.getDouble("valorLitro"));
                relatorioVo.setValorTotalRecebido(new BigDecimal(rs.getDouble("valorLitro") * rs.getInt("litros")));
                relatorioVo.setIdRota(rs.getInt("id"));
                listaRelatorio.add(relatorioVo);
                relatorioVo =  new RelatorioReciboVo();
            }
            JasperPrint impressao = JasperFillManager.fillReport(getClass().getResource("layout/impressaoRecibo.jasper").getPath(), null, new JRBeanCollectionDataSource(listaRelatorio));
            JasperViewer viewer = new JasperViewer(impressao, true);
            viewer.setVisible(true);
            bdUtil.atualizarRecibosImpressos(listaRelatorio);

        } catch (ClassNotFoundException | SQLException | JRException ex) {
            Logger.getLogger(RelatorioRecibo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public  static void main(String args[]){

    }
    
    
    
    
}
