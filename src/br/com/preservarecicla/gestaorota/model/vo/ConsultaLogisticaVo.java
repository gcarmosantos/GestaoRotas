/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.preservarecicla.gestaorota.model.vo;

/**
 *
 * @author Gustavo
 */
public class ConsultaLogisticaVo {
    
    private Integer codCliente;
    private String endereco;
    private String cidade;

    /**
     * @return the codCliente
     */
    public Integer getCodCliente() {
        return codCliente;
    }

    /**
     * @param codCliente the codCliente to set
     */
    public void setCodCliente(Integer codCliente) {
        this.codCliente = codCliente;
    }

    /**
     * @return the endereco
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * @return the cidade
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
            
    
    
}
