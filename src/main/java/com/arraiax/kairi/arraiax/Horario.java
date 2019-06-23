package com.arraiax.kairi.arraiax;

import java.sql.Time;

public class Horario {

    private int idHorario;
    private String idKey;
    private String descricao;
    private String escola;
    private String horariofin;
    private String horarioini;
    private Double latitude;
    private Double longitude;
    private String sala;

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }

    public String getHorariofin() {
        return horariofin;
    }

    public void setHorariofin(String horariofin) {
        this.horariofin = horariofin;
    }

    public String getHorarioini() {
        return horarioini;
    }

    public void setHorarioini(String horarioini) {
        this.horarioini = horarioini;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

}
