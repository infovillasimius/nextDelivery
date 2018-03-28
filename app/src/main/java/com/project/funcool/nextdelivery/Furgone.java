package com.project.funcool.nextdelivery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by anto on 17/01/16.
 */
public class Furgone implements Serializable {

    private String nome;
    private int carico_max;
    int liv_carico;
    private double x_pos;
    private double y_pos;
    private double distanza;

    ArrayList<MerceTrasportata> carico;
    ArrayList<MerceTrasportata> consegnata;
    ArrayList<String> destinatari;


    public Furgone(String nome, int caricoMax, double xpos, double ypos) {
        this.nome = nome;
        this.carico_max = caricoMax;
        this.liv_carico = 0;
        this.x_pos = xpos;
        this.y_pos = ypos;
        this.carico = new ArrayList<>();
        this.destinatari = new ArrayList<>();
        this.consegnata= new ArrayList<>();
        this.distanza=0;

    }

    public String getNome() {
        return nome;
    }

    public int getCarico_max() {
        return carico_max;
    }

    public int getLiv_carico() {
        return liv_carico;
    }

    public double getX_pos() {
        return x_pos;
    }

    public void setX_pos(double x_pos) {
        this.x_pos = x_pos;
    }

    public double getY_pos() {
        return y_pos;
    }

    public void setY_pos(double y_pos) {
        this.y_pos = y_pos;
    }

    public ArrayList<String> getDestinatari() {
        return destinatari;
    }

    public ArrayList<MerceTrasportata> getCarico() {
        return this.carico;
    }

    /**
     * Funzione di aggiunta merce al carico trasportato dal furgone
     *
     * @param in MerceTrasportata da caricare sul furgone
     * @return Boolean True se la merce e' stata caricata, false se non ci sta
     */
    public boolean carica(MerceTrasportata in) {
        if (this.liv_carico + in.getQuantita() <= this.carico_max) {
            this.liv_carico += in.getQuantita();
            this.carico.add(in);
            return true;
        }
        return false;
    }

    /**
     * Carica merce sul furgone
     *
     * @param in ArrayList di MerceTrasportata
     * @return Boolean true se tutto ok - False se la merce da caricare e' troppa
     */
    public boolean carica(ArrayList<MerceTrasportata> in) {
        if (controlla(in)) {
            for (MerceTrasportata a : in) {
                this.carica(a);
                if (!destinatari.contains(a.getDestinatario()))
                    destinatari.add(a.getDestinatario());
            }
            return true;
        }
        return false;
    }

    /**
     * Procedura di scarico merce trasportata presso il destinatario
     *
     * @param destinatario String con il nome(Codice) del destinatario della merce
     */
    public void scarica(String destinatario) {
        MerceTrasportata a;
        if (liv_carico > 0) {
            ListIterator<MerceTrasportata> it = this.carico.listIterator();

            while (it.hasNext()) {
                a = it.next();
                if (a.getDestinatario().equalsIgnoreCase(destinatario)) {
                    this.consegnata.add(a);
                    it.remove();
                    this.liv_carico -= a.getQuantita();
                    this.destinatari.remove(destinatario);
                }
            }
        }
    }

    /**
     * Restituisce un ArrayList contenente tutte le merci indirizzate al destinatario passato come parametro
     *
     * @param destinatario String con il nome(Codice) del destinatario della merce
     * @return ArrayList<MerceTrasportata> contenente tutte le merci che sono indirizzate al destinatario
     */
    public ArrayList<MerceTrasportata> elenca(String destinatario) {
        ListIterator<MerceTrasportata> it = this.carico.listIterator();
        ArrayList<MerceTrasportata> elenco = new ArrayList<MerceTrasportata>();
        while (it.hasNext()) {
            MerceTrasportata a = it.next();
            if (a.getDestinatario().equalsIgnoreCase(destinatario)) {
                elenco.add(a);
            }
        }
        return elenco;
    }

    /**
     * Controlla se l'arrayList passato come parametro comporta un carico compatibile con l'attuale liv di carico del furgone
     *
     * @param in ArrayList<MerceTrasportata> contenente delle merci di cui occorre verificare la quantita'
     * @return Boolean true se la merce puo' essere caricata, false altrimenti
     */
    public boolean controlla(ArrayList<MerceTrasportata> in) {
        int tot = 0;
        ListIterator<MerceTrasportata> it = in.listIterator();
        while (it.hasNext()) {
            MerceTrasportata a = it.next();
            tot += a.getQuantita();
        }

        return (tot + this.liv_carico) <= this.carico_max;
    }

    public boolean controlla(ArrayList<MerceTrasportata> in, String destinatario) {
        MerceTrasportata a;
        int spazio = 0;
        int tot = 0;
        if (this.liv_carico > 0) {
            ListIterator<MerceTrasportata> it = this.carico.listIterator();

            while (it.hasNext()) {
                a = it.next();
                if (a.getDestinatario().equalsIgnoreCase(destinatario)) {
                    spazio += a.getQuantita();
                }
            }
        }

        ListIterator<MerceTrasportata> it = in.listIterator();
        while (it.hasNext()) {
            a = it.next();
            tot += a.getQuantita();
        }
        return (tot + this.liv_carico - spazio) <= this.carico_max;

    }

    public void sposta(double x,double y){
        this.distanza += Math.sqrt(Math.pow(x_pos-x,2)+Math.pow(y_pos-y,2));
        x_pos=x;
        y_pos=y;
    }

    public double getDistanza() {
        return distanza;
    }

    public int quantitaDestinatario(String destinatario){
        ArrayList<MerceTrasportata> a=elenca(destinatario);
        int totMerce=0;
        for (MerceTrasportata m:a) {
            totMerce+=m.getQuantita();
        }
        return totMerce;
    }
}
