package br.com.greenvision.dto;

import java.time.LocalDate;

public class IndicacaoDto {

    private Long id;
    private String nomeIndicado;
    private String emailIndicado;
    private String relacao;
    private LocalDate dataIndicacao;
    private Long idUsuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeIndicado() {
        return nomeIndicado;
    }

    public void setNomeIndicado(String nomeIndicado) {
        this.nomeIndicado = nomeIndicado;
    }

    public String getEmailIndicado() {
        return emailIndicado;
    }

    public void setEmailIndicado(String emailIndicado) {
        this.emailIndicado = emailIndicado;
    }

    public String getRelacao() {
        return relacao;
    }

    public void setRelacao(String relacao) {
        this.relacao = relacao;
    }

    public LocalDate getDataIndicacao() {
        return dataIndicacao;
    }

    public void setDataIndicacao(LocalDate dataIndicacao) {
        this.dataIndicacao = dataIndicacao;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
