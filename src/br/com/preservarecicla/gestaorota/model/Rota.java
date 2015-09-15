/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.preservarecicla.gestaorota.model;

/**
 *
 * @author Gustavo
 */
public class Rota {
    
    private Integer id;
    private String data;
    private Integer codCliente;
    private Integer codPontoColeta;
    private String endereco;
    private String cidade;
    private Long quilometragem;
    private Long litros; 
    private Long gordura;
    private String pagou;
    private Double valorPagamento;
    private String justificativa;
    private String portePonto;
    private String nomeCliente;
    private Integer codFuncionario;
    private Integer codVeiculo;
    private Double valorLitro;
    
    
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

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

    /**
     * @return the quilometragem
     */
    public Long getQuilometragem() {
        return quilometragem;
    }

    /**
     * @param quilometragem the quilometragem to set
     */
    public void setQuilometragem(Long quilometragem) {
        this.quilometragem = quilometragem;
    }

    /**
     * @return the litros
     */
    public Long getLitros() {
        return litros;
    }

    /**
     * @param litros the litros to set
     */
    public void setLitros(Long litros) {
        this.litros = litros;
    }

    /**
     * @return the gordura
     */
    public Long getGordura() {
        return gordura;
    }

    /**
     * @param gordura the gordura to set
     */
    public void setGordura(Long gordura) {
        this.gordura = gordura;
    }

    /**
     * @return the pagou
     */
    public String getPagou() {
        return pagou;
    }

    /**
     * @param pagou the pagou to set
     */
    public void setPagou(String pagou) {
        this.pagou = pagou;
    }

    /**
     * @return the valorPagamento
     */
    public Double getValorPagamento() {
        return valorPagamento;
    }

    /**
     * @param valorPagamento the valorPagamento to set
     */
    public void setValorPagamento(Double valorPagamento) {
        this.valorPagamento = valorPagamento;
    }

    /**
     * @return the justificativa
     */
    public String getJustificativa() {
        return justificativa;
    }

    /**
     * @param justificativa the justificativa to set
     */
    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    /**
     * @return the portePonto
     */
    public String getPortePonto() {
        return portePonto;
    }

    /**
     * @param portePonto the portePonto to set
     */
    public void setPortePonto(String portePonto) {
        this.portePonto = portePonto;
    }

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
     * @return the codPontoColeta
     */
    public Integer getCodPontoColeta() {
        return codPontoColeta;
    }

    /**
     * @param codPontoColeta the codPontoColeta to set
     */
    public void setCodPontoColeta(Integer codPontoColeta) {
        this.codPontoColeta = codPontoColeta;
    }

    /**
     * @return the codFuncionario
     */
    public Integer getCodFuncionario() {
        return codFuncionario;
    }

    /**
     * @param codFuncionario the codFuncionario to set
     */
    public void setCodFuncionario(Integer codFuncionario) {
        this.codFuncionario = codFuncionario;
    }

    /**
     * @return the codVeiculo
     */
    public Integer getCodVeiculo() {
        return codVeiculo;
    }

    /**
     * @param codVeiculo the codVeiculo to set
     */
    public void setCodVeiculo(Integer codVeiculo) {
        this.codVeiculo = codVeiculo;
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
    
}
 