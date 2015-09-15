/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.preservarecicla.gestaorota.service;

import br.com.preservarecicla.gestaorota.model.Rota;
import br.com.preservarecicla.gestaorota.model.vo.ConsultaGestaoRotaVo;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gustavo
 */
public interface GestaoService {
    public List<Rota> listarRota(String data) throws SQLException,ClassNotFoundException;
    public List<Rota> listarGestaoRota(ConsultaGestaoRotaVo vo) throws SQLException,ClassNotFoundException;

    public void salvarRota(List<Rota> listaRota) throws SQLException,ClassNotFoundException;

}
