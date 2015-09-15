/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.preservarecicla.gestaorota.service;

import br.com.preservarecicla.gestaorota.dao.util.BancoUtil;
import br.com.preservarecicla.gestaorota.model.Rota;
import br.com.preservarecicla.gestaorota.model.vo.ConsultaGestaoRotaVo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo
 */
public class GestaoServiceImpl implements GestaoService {

    @Override
    public List<Rota> listarGestaoRota(ConsultaGestaoRotaVo vo) throws SQLException, ClassNotFoundException {
        BancoUtil banco = new BancoUtil();
        return banco.listarGestaoRota(vo);
    }

    @Override
    public List<Rota> listarRota(String data) throws SQLException, ClassNotFoundException {
        BancoUtil banco = new BancoUtil();
        return banco.listarRota(data);
    }

    @Override
    public void salvarRota(List<Rota> listaRota) throws ClassNotFoundException, SQLException {
        BancoUtil banco = new BancoUtil();
        banco.salvarRota(listaRota);
    }
    
    
}
