package com.project.funcool.nextdelivery;

import java.io.Serializable;

/**
 * Created by anto on 17/01/16.
 */
public class MerceTrasportata implements Serializable {

    private String codice;
    private int quantita;
    private String mittente;
    private String destinatario;


    public MerceTrasportata(String cod, int quant, String mittente, String destinatario) {
        this.codice = cod;
        this.quantita = quant;
        this.mittente = mittente;
        this.destinatario = destinatario;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getMittente() {
        return mittente;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MerceTrasportata that = (MerceTrasportata) o;

        if (getQuantita() != that.getQuantita()) return false;
        if (!getCodice().equals(that.getCodice())) return false;
        if (getMittente() != null ? !getMittente().equals(that.getMittente()) : that.getMittente() != null)
            return false;
        return !(getDestinatario() != null ? !getDestinatario().equals(that.getDestinatario()) : that.getDestinatario() != null);

    }

    @Override
    public int hashCode() {
        int result = getCodice().hashCode();
        result = 31 * result + getQuantita();
        result = 31 * result + (getMittente() != null ? getMittente().hashCode() : 0);
        result = 31 * result + (getDestinatario() != null ? getDestinatario().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "N. "+this.quantita +" cassette di "+ this.codice + " per " + this.destinatario;
    }


    public String toString2() {
        return "N. "+this.quantita +" cassette di "+ this.codice + " da " + this.mittente;
    }


}
