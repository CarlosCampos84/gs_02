package br.com.greenvision.model;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Indicacao {

    private Long id;
    private String nomeIndicado;
    private String emailIndicado;
    private String relacao;
    private LocalDate dataIndicacao;
    private Long idUsuario;

    public Indicacao() {
    }

    public Indicacao(Long id, String nomeIndicado, String emailIndicado, String relacao, LocalDate dataIndicacao, Long idUsuario) {
        this.id = id;
        this.nomeIndicado = nomeIndicado;
        setEmailIndicado(emailIndicado); // Usando o método set com validação
        this.relacao = relacao;
        setDataIndicacao(dataIndicacao);
        this.idUsuario = idUsuario;
    }

    public Indicacao(String nomeIndicado, String emailIndicado, String relacao, LocalDate dataIndicacao, Long idUsuario) {
        this.nomeIndicado = nomeIndicado;
        this.emailIndicado = emailIndicado;
        this.relacao = relacao;
        this.dataIndicacao = dataIndicacao;
        this.idUsuario = idUsuario;
    }

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
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (emailIndicado == null || !Pattern.matches(EMAIL_REGEX, emailIndicado)) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.emailIndicado = emailIndicado;
    }

    public String getRelacao() {
        return relacao;
    }

    public void setRelacao(String relacao) {
        this.relacao = relacao;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getDataIndicacao() {
        return dataIndicacao;
    }

    public void setDataIndicacao(LocalDate dataIndicacao) {
        this.dataIndicacao = LocalDate.now();
    }
}

