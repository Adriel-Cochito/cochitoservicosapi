package br.edu.infnet.cochitoservicosapi.model.domain;

public class DistanciaQueryResult {
    private String cepOrigem;
    private String cepDestino;
    private String enderecoOrigem;
    private String bairroOrigem;
    private String ufOrigem;
    private String enderecoDestino;
    private String bairroDestino;
    private String ufDestino;
    private double distanciaKm;
    private double tempoMinutos;

    // Getters e Setters
    public String getCepOrigem() {
        return cepOrigem;
    }

    public void setCepOrigem(String cepOrigem) {
        this.cepOrigem = cepOrigem;
    }

    public String getCepDestino() {
        return cepDestino;
    }

    public void setCepDestino(String cepDestino) {
        this.cepDestino = cepDestino;
    }

    public String getEnderecoOrigem() {
        return enderecoOrigem;
    }

    public void setEnderecoOrigem(String enderecoOrigem) {
        this.enderecoOrigem = enderecoOrigem;
    }

    public String getBairroOrigem() {
        return bairroOrigem;
    }

    public void setBairroOrigem(String bairroOrigem) {
        this.bairroOrigem = bairroOrigem;
    }

    public String getUfOrigem() {
        return ufOrigem;
    }

    public void setUfOrigem(String ufOrigem) {
        this.ufOrigem = ufOrigem;
    }

    public String getEnderecoDestino() {
        return enderecoDestino;
    }

    public void setEnderecoDestino(String enderecoDestino) {
        this.enderecoDestino = enderecoDestino;
    }

    public String getBairroDestino() {
        return bairroDestino;
    }

    public void setBairroDestino(String bairroDestino) {
        this.bairroDestino = bairroDestino;
    }

    public String getUfDestino() {
        return ufDestino;
    }

    public void setUfDestino(String ufDestino) {
        this.ufDestino = ufDestino;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public double getTempoMinutos() {
        return tempoMinutos;
    }

    public void setTempoMinutos(double tempoMinutos) {
        this.tempoMinutos = tempoMinutos;
    }
}