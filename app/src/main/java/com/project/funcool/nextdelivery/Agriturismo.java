package com.project.funcool.nextdelivery;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by anto on 20/01/16.
 */
public class Agriturismo implements Serializable {

    private String nome;
    private double xpos;
    private double ypos;
    private String indirizzo;
    private String comune;
    private ArrayList<MerceTrasportata> merce;

    public Agriturismo(String nome, double xpos, double ypos, String indirizzo, String comune, ArrayList<MerceTrasportata> merce) {
        this.nome = nome;
        this.xpos = xpos;
        this.ypos = ypos;
        this.indirizzo = indirizzo;
        this.comune = comune;
        this.merce = merce;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getXpos() {
        return xpos;
    }


    public double getYpos() {
        return ypos;
    }


    public String getIndirizzo() {
        return indirizzo;
    }


    public String getComune() {
        return comune;
    }

    public ArrayList<MerceTrasportata> getMerce() {
        return merce;
    }

    public void setMerce(ArrayList<MerceTrasportata> merce) {
        this.merce = merce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Agriturismo that = (Agriturismo) o;

        //if (Double.compare(that.getXpos(), getXpos()) != 0) return false;
        //if (Double.compare(that.getYpos(), getYpos()) != 0) return false;
        if (!getNome().equals(that.getNome())) return false;
        //if (!getIndirizzo().equals(that.getIndirizzo())) return false;
        return getComune().equals(that.getComune());

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getNome().hashCode();
        temp = Double.doubleToLongBits(getXpos());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getYpos());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getIndirizzo().hashCode();
        result = 31 * result + getComune().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.nome + "\n" + this.indirizzo + "\n" + this.comune;
    }

    public int quantita(){

        int totMerce=0;
        for (MerceTrasportata m:this.merce) {
            totMerce+=m.getQuantita();
        }
        return totMerce;
    }


}