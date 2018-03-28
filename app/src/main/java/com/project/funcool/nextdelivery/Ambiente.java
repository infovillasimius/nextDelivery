package com.project.funcool.nextdelivery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by anto on 26/01/16.
 */
public class Ambiente implements Serializable {

    ArrayList<Agriturismo> aziende;
    Furgone furgone;
    Person user;
    double distanzaPercorsa;
    boolean end;


    public Ambiente() {
        this.aziende = Utility.elencoaziende();
        this.furgone = Utility.nuovoFurgone(new ArrayList<MerceTrasportata>());
        this.user = Utility.nuovoUtente();
        this.distanzaPercorsa=0;
        this.end=false;
    }

    public Person getUser() {
        return user;
    }

    public Agriturismo dove(){

        Iterator<Agriturismo> it= aziende.listIterator();
        Agriturismo agriturismo = null,a;
        while (it.hasNext()){
            a=it.next();

            if (Math.abs(a.getXpos()-furgone.getX_pos())<0.001 && Math.abs(a.getYpos()-furgone.getY_pos())<0.001){
                agriturismo=a;
            }
        }
        return agriturismo;
    }

    public Agriturismo nextDelivery(){

        Agriturismo partenza = this.dove();

        Agriturismo next;
        ArrayList<Agriturismo> listaAziende = new ArrayList<Agriturismo>();

        if(furgone.getLiv_carico()<furgone.getCarico_max()*7/8){

            for (Agriturismo a:this.aziende) {
                if(!partenza.equals(a) && a.getMerce().size()>0
                        && ((a.quantita()-furgone.quantitaDestinatario(a.getNome())+furgone.getLiv_carico())<=furgone.getCarico_max())){
                    listaAziende.add(a);
                    //System.out.println(a.getNome()+" "+Utility.distanza(partenza,a)*40000/180);
                }
                if(listaAziende.size()==0){
                    ArrayList<String> destinatari=furgone.getDestinatari();
                    for (Agriturismo ag: this.aziende) {
                        if(!partenza.equals(ag) && destinatari.contains(a.getNome()) && ((a.quantita()-furgone.quantitaDestinatario(a.getNome())+furgone.getLiv_carico())<=furgone.getCarico_max())){
                            listaAziende.add(ag);
                        }
                    }
                }
            }
        } else {

            ArrayList<String> destinatari=furgone.getDestinatari();
            for (Agriturismo a: this.aziende) {
                if(!partenza.equals(a) && destinatari.contains(a.getNome()) && ((a.quantita()-furgone.quantitaDestinatario(a.getNome())+furgone.getLiv_carico())<=furgone.getCarico_max())){
                    listaAziende.add(a);
                }
            }

        }

        if(furgone.quantitaDestinatario("Magazzino")>furgone.getCarico_max()/2){
            listaAziende.add(aziende.get(0));
        }

        if(listaAziende.size()==0){
            this.end=end();
            return this.aziende.get(0);
        }

        double distMin,dist;

        distMin = 100;
        next=null;

        for (Agriturismo a :listaAziende){
            dist=Utility.distanza(partenza,a);
            if((dist<distMin && (a.getMerce().size()>0  || furgone.getDestinatari().contains(a.getNome())))&& !a.getNome().equals("Magazzino")){
                next=a;
                distMin=dist;
            }
        }
        if (next ==null ){  //|| distMin==0
            next=this.aziende.get(0);
            this.end=end();

        }
        if(furgone.quantitaDestinatario("Magazzino")>furgone.getCarico_max()/2){
            next=aziende.get(0);
            this.end=end();
        }
        return next;
    }

    public void vuota (Agriturismo agriturismo){
        for (Agriturismo a:aziende) {
            if(a.equals(agriturismo)){
                a.setMerce(new ArrayList<MerceTrasportata>());
            }
        }
    }

    public boolean end (){
        int tot=0;
        for (Agriturismo a:aziende) {
            for (MerceTrasportata m: a.getMerce()) {
                tot+=m.getQuantita();
            }
        }
        return (tot == 0 && furgone.getLiv_carico()==0 && dove().equals(aziende.get(0)));
    }

}
