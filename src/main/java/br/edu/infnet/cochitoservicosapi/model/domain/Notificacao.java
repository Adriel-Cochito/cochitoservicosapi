package br.edu.infnet.cochitoservicosapi.model.domain;

import java.time.LocalDateTime;

public class Notificacao {
	
	private String titulo;
    private String mensagem;
    private TipoNotificacao tipoNotificacao;
    private Funcionario funcionario;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataLeitura;
    private boolean lida;
    
    /**
     * Construtor padrão que inicializa a notificação com valores padrão.
     * Por padrão, uma notificação é criada como não lida.
     */
    public Notificacao() {
        this.lida = false;
    }
    
    /**
     * Marca a notificação como lida e registra automaticamente a data de leitura.
     */
    public void marcarComoLida() {
        this.lida = true;
        this.dataLeitura = LocalDateTime.now();
    }
    
    /**
     * Verifica se a notificação está como não lida.
     * @return true se não foi lida, false caso contrário
     */
    public boolean estaComoNaoLida() {
        return !this.lida;
    }
    
    // Getters e Setters
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getMensagem() {
        return mensagem;
    }
    
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public TipoNotificacao getTipoNotificacao() {
        return tipoNotificacao;
    }
    
    public void setTipoNotificacao(TipoNotificacao tipoNotificacao) {
        this.tipoNotificacao = tipoNotificacao;
    }
    
    public Funcionario getFuncionario() {
        return funcionario;
    }
    
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public LocalDateTime getDataLeitura() {
        return dataLeitura;
    }
    
    public void setDataLeitura(LocalDateTime dataLeitura) {
        this.dataLeitura = dataLeitura;
    }
    
    public boolean isLida() {
        return lida;
    }
    
    public void setLida(boolean lida) {
        this.lida = lida;
    }
}
