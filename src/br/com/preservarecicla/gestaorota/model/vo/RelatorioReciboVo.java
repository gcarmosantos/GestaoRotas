/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.preservarecicla.gestaorota.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Gustavo
 */
public class RelatorioReciboVo implements Serializable {
    
    private Integer idRota;
    private String nomeCliente;
    private String enderecoCliente;
    private String dataPagamento;
    private Integer qtdLitros;
    private Double valorLitro;
    private BigDecimal valorTotalRecebido;

    /**
     * @return the nomeCliente
     */
    public String getNomeCliente() {
        return nomeCliente;
    }

    /**
     * @param nomeCliente the nomeCliente to set
     */
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    /**
     * @return the enderecoCliente
     */
    public String getEnderecoCliente() {
        return enderecoCliente;
    }

    /**
     * @param enderecoCliente the enderecoCliente to set
     */
    public void setEnderecoCliente(String enderecoCliente) {
        this.enderecoCliente = enderecoCliente;
    }

    /**
     * @return the dataPagamento
     */
    public String getDataPagamento() {
        return dataPagamento;
    }

    /**
     * @param dataPagamento the dataPagamento to set
     */
    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    /**
     * @return the qtdLitros
     */
    public Integer getQtdLitros() {
        return qtdLitros;
    }

    /**
     * @param qtdLitros the qtdLitros to set
     */
    public void setQtdLitros(Integer qtdLitros) {
        this.qtdLitros = qtdLitros;
    }

    /**
     * @return the valorLitro
     */
    public Double getValorLitro() {
        return valorLitro;
    }

    /**
     * @param valorLitro the valorLitro to set
     */
    public void setValorLitro(Double valorLitro) {
        this.valorLitro = valorLitro;
    }

    /**
     * @return the valorTotalRecebido
     */
    public BigDecimal getValorTotalRecebido() {
        return valorTotalRecebido;
    }

    /**
     * @param valorTotalRecebido the valorTotalRecebido to set
     */
    public void setValorTotalRecebido(BigDecimal valorTotalRecebido) {
        this.valorTotalRecebido = valorTotalRecebido;
    }

    /**
     * @return the idRota
     */
    public Integer getIdRota() {
        return idRota;
    }

    /**
     * @param idRota the idRota to set
     */
    public void setIdRota(Integer idRota) {
        this.idRota = idRota;
    }
    
}
