package com.project.funcool.nextdelivery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by anto on 24/01/16.
 */
public class Utility {

    /**
     * Costruisce l'ArrayList delle aziende da usare nell'app per simulare la connessione al database
     *
     * @return ArrayList aziende
     */
    public static ArrayList<Agriturismo> elencoaziende() {
        ArrayList<Agriturismo> lista = new ArrayList<Agriturismo>();

        ArrayList<Double> xpos = new ArrayList<>();
        xpos.add(9.50225085150315);
        xpos.add(9.51051060484378);
        xpos.add(9.51754228842908);
        xpos.add(9.50684517769803);
        xpos.add(9.49633819420862);
        xpos.add(9.50048139998536);
        xpos.add(9.50124999976946);
        xpos.add(9.52315458757066);
        xpos.add(9.54095192477326);
        xpos.add(9.54328288507024);
        xpos.add(9.55251817012626);
       /* xpos.add(9.54904601060709);
        xpos.add(9.54536008935624);
        xpos.add(9.54011526817839);
        xpos.add(9.54275380154774);
        xpos.add(9.53284049675723);
        xpos.add(9.49908679333672);
        xpos.add(9.50317175122379);
        xpos.add(9.57307528166521);
        xpos.add(9.53197864614733);
        xpos.add(9.53785178767115);
        xpos.add(9.54307743474189);
        xpos.add(9.52707700014005);
        xpos.add(9.62854590920934);
        xpos.add(9.60519740196519);
        xpos.add(9.60554152103946);
        xpos.add(9.60228368296569);*/

        ArrayList<Double> ypos = new ArrayList<>();
        ypos.add(39.2355205378512);
        ypos.add(39.1526014488991);
        ypos.add(39.1471295688268);
        ypos.add(39.2253249362728);
        ypos.add(39.2420612773182);
        ypos.add(39.2375185002843);
        ypos.add(39.2357899998775);
        ypos.add(39.2411294204276);
        ypos.add(39.2122010025259);
        ypos.add(39.2089888488962);
        ypos.add(39.2032609375781);
     /*   ypos.add(39.1962079140114);
        ypos.add(39.1946247925230);
        ypos.add(39.2804284076107);
        ypos.add(39.2773373059332);
        ypos.add(39.2653448374015);
        ypos.add(39.2871291441189);
        ypos.add(39.3038997862218);
        ypos.add(39.3536341277277);
        ypos.add(39.3179072197490);
        ypos.add(39.4310868497771);
        ypos.add(39.3557372709844);
        ypos.add(39.3533211003544);
        ypos.add(39.4489501776320);
        ypos.add(39.4542607913052);
        ypos.add(39.4628491056402);
        ypos.add(39.4702328024763); */

        Iterator<Double> itx = xpos.iterator();
        Iterator<Double> ity = ypos.iterator();

        ArrayList<String> indirizzi = new ArrayList<>();
        indirizzi.add("Localita' Centrale");
        indirizzi.add("Localita' Corr'e Pruna");
        indirizzi.add("Via Viviani");
        indirizzi.add("Loc. Masone Murtas 46");
        indirizzi.add("Loc. Sa Mandria");
        indirizzi.add("Loc. Monte Gruttas 84");
        indirizzi.add("Via Centrale");
        indirizzi.add("Localita' Sabadi 17");
        indirizzi.add("Via Sarmentus Loc. S. Pietro");
        indirizzi.add("Localita' S. Pietro");
        indirizzi.add("Località San Pietro");
        /* indirizzi.add("Località San Pietro");
        indirizzi.add("Località San Pietro");
        indirizzi.add("Localita' Maloccu");
        indirizzi.add("Loc. Oliaspeciosa");
        indirizzi.add("Loc. Masone Pardu, 37");
        indirizzi.add("Località l'Annunziata");
        indirizzi.add("Località l'Annunziata");
        indirizzi.add("Località S' Ollasteddu");
        indirizzi.add("Loc. Tuerra");
        indirizzi.add("Localita' Cuccuru De Ferrus");
        indirizzi.add("Località tuerra");
        indirizzi.add("Località tuerra");
        indirizzi.add("Loc. Porto Tramatzu");
        indirizzi.add("Ex Strada Statale 125, km 72");
        indirizzi.add("Loc. Nurazzolas");
        indirizzi.add("SS 125 Km 74"); */

        ArrayList<String> comuni = new ArrayList<>();
        comuni.add("Castiadas");
        comuni.add("Villasimius");
        comuni.add("Villasimius");
        comuni.add("Castiadas");
        comuni.add("Castiadas");
        comuni.add("Castiadas");
        comuni.add("Castiadas");
        comuni.add("Castiadas");
        comuni.add("Castiadas");
        comuni.add("Castiadas");
        comuni.add("Castiadas");
     /*   comuni.add("Castiadas");
        comuni.add("Castiadas");
        comuni.add("Castiadas");
        comuni.add("Castiadas");
        comuni.add("Castiadas");
        comuni.add("Castiadas");
        comuni.add("Castiadas");
        comuni.add("Muravera");
        comuni.add("Muravera");
        comuni.add("San Vito");
        comuni.add("San Vito");
        comuni.add("San Vito");
        comuni.add("Villaputzu");
        comuni.add("Villaputzu");
        comuni.add("Villaputzu");
        comuni.add("Villaputzu");*/

        Iterator<String> itind = indirizzi.iterator();
        Iterator<String> itcom = comuni.iterator();
        ArrayList<String> nomi = Utility.nomiAziende();
        for (String a : nomi) {
            Agriturismo azienda = new Agriturismo(a, itx.next(), ity.next(), itind.next(), itcom.next(), Utility.merce(a));
            lista.add(azienda);
        }
        return lista;
    }

    /**
     * Costruisce ArrayList con merci che l'azienda intende spedire (simula connessione a database)
     *
     * @param mittente String che rappresenta l'azienda mittente
     * @return ArrayList merci da spedire
     */
    public static ArrayList<MerceTrasportata> merce(String mittente) {
        ArrayList<MerceTrasportata> lista = new ArrayList<MerceTrasportata>();
        ArrayList<String> merci = Utility.nomiMerci();
        ArrayList<String> aziende = Utility.nomiAziende();
        int num=aziende.indexOf(mittente);
        aziende.remove(mittente);

        switch(num){
            case 0:
                lista.add(new MerceTrasportata("Pesche",1,mittente,"Agriturismo La Rosa dei Venti"));
                lista.add(new MerceTrasportata("Zucchine",2,mittente,"Agriturismo San Pietro"));
                lista.add(new MerceTrasportata("Fagiolini",5,mittente,"Agriturismo San Pietro"));
                lista.add(new MerceTrasportata("Cetrioli",1,mittente,"Agriturismo San Pietro"));
                lista.add(new MerceTrasportata("Prugne",1,mittente,"Agriturismo Cesarò"));
                lista.add(new MerceTrasportata("Peperoni",1,mittente,"Agriturismo Agus Pierangela"));
                break;
            case 1:
                lista.add(new MerceTrasportata("Prezzemolo",5,mittente,"Agriturismo Agus Pierangela"));
                lista.add(new MerceTrasportata("Fagiolini",3,mittente,"Agriturismo Cesarò"));
                break;
            case 2:
                lista.add(new MerceTrasportata("Olive",2,mittente,"Agriturismo Bettoli Maria Paola"));
                lista.add(new MerceTrasportata("Cipolle",4,mittente,"Agriturismo Monte Gruttas"));
                lista.add(new MerceTrasportata("Melanzane",1,mittente,"Agriturismo San Pietro"));
                lista.add(new MerceTrasportata("Meloni",1,mittente,"Magazzino"));
                lista.add(new MerceTrasportata("Prugne",2,mittente,"Agriturismo Sa Bingia"));
                lista.add(new MerceTrasportata("Pere",3,mittente,"Agriturismo Cesarò"));
                break;
            case 3:
                lista.add(new MerceTrasportata("Pesche",1,mittente,"Magazzino"));
                lista.add(new MerceTrasportata("Angurie",4,mittente,"Agriturismo Sa Bingia"));
                break;
            case 4:
                lista.add(new MerceTrasportata("Zucchine",2,mittente,"Agriturismo di Cogoni Alessandro"));
                lista.add(new MerceTrasportata("Pesche",4,mittente,"Agriturismo San Pietro"));
                lista.add(new MerceTrasportata("Melanzane",2,mittente,"Agriturismo di Cogoni Alessandro"));
                lista.add(new MerceTrasportata("Sedani",4,mittente,"Agriturismo Cesarò"));
                lista.add(new MerceTrasportata("Arance",1,mittente,"Agriturismo di Cogoni Alessandro"));
                break;
            case 5:
                break;

            case 6:
                lista.add(new MerceTrasportata("Cetrioli",2,mittente,"Agriturismo Minni Minni"));
                lista.add(new MerceTrasportata("Sedani",4,mittente,"Agriturismo Bettoli Maria Paola"));
                lista.add(new MerceTrasportata("Arance",5,mittente,"Agriturismo San Pietro"));
                lista.add(new MerceTrasportata("Sedani",4,mittente,"Agriturismo di Cogoni Alessandro"));
                break;
            case 7:
                lista.add(new MerceTrasportata("Melanzane",3,mittente,"Agriturismo Monte Gruttas"));
                lista.add(new MerceTrasportata("Marmellata di arance",4,mittente,"Agriturismo Bettoli Maria Paola"));
                break;
            case 8:
                lista.add(new MerceTrasportata("Prugne",1,mittente,"Agriturismo Bettoli Maria Paola"));
                lista.add(new MerceTrasportata("Peperoncini piccanti",2,mittente,"Agriturismo Minni Minni"));
                lista.add(new MerceTrasportata("Peperoni",4,mittente,"Agriturismo San Pietro"));
                lista.add(new MerceTrasportata("Prezzemolo",3,mittente,"Agriturismo Monte Gruttas"));
                lista.add(new MerceTrasportata("Peperoni",2,mittente,"Agriturismo Bettoli Maria Paola"));
                break;
            case 9:
                lista.add(new MerceTrasportata("Melanzane",3,mittente,"Agriturismo Sa Mandria"));
                lista.add(new MerceTrasportata("Marmellata di arance",2,mittente,"Agriturismo Agus Pierangela"));
                lista.add(new MerceTrasportata("Pomodori",5,mittente,"Magazzino"));
                lista.add(new MerceTrasportata("Melanzane",1,mittente,"Magazzino"));
                lista.add(new MerceTrasportata("Marmellata di arance",4,mittente,"Agriturismo Agus Pierangela"));
                lista.add(new MerceTrasportata("Peperoni",3,mittente,"Agriturismo Sa Mandria"));
                break;
            case 10:
                lista.add(new MerceTrasportata("Aglio",4,mittente,"Agriturismo Agus Pierangela"));
                lista.add(new MerceTrasportata("Prugne",5,mittente,"Agriturismo San Pietro"));
                lista.add(new MerceTrasportata("Peperoncini piccanti",1,mittente,"Agriturismo Minni Minni"));
                lista.add(new MerceTrasportata("Marmellata di arance",5,mittente,"Agriturismo San Pietro"));
                lista.add(new MerceTrasportata("Olive",5,mittente,"Agriturismo Minni Minni"));
                break;


        }
        /*int n = randInt(0, 6);

        for (int i = 0; i < n; i++) {
            int n1 = Utility.randInt(0, 19);
            int n2 = Utility.randInt(0, 9); //erano 26
            int q = Utility.randInt(1, 5);
            lista.add(new MerceTrasportata(merci.get(n1), q, mittente, aziende.get(n2)));
        }*/

        return lista;
    }

    /**
     * Costruisce ArrayList con i nomi delle 26 aziende
     *
     * @return ArrayList di String con nomi aziende
     */
    public static ArrayList<String> nomiAziende() {
        ArrayList<String> lista = new ArrayList<String>();

        lista.add("Magazzino");
        lista.add("Agriturismo Sa Bingia");
        lista.add("Agriturismo di Cogoni Alessandro");
        lista.add("Agriturismo Minni Minni");
        lista.add("Agriturismo Sa Mandria");
        lista.add("Agriturismo Monte Gruttas");
        lista.add("Agriturismo Agus Pierangela");
        lista.add("Agriturismo Cesarò");
        lista.add("Agriturismo La Rosa dei Venti");
        lista.add("Agriturismo San Pietro");
        lista.add("Agriturismo Bettoli Maria Paola");
   /*     lista.add("Agriturismo Sa Caminera");
        lista.add("Agriturismo Gli Ulivi");
        lista.add("Agriturismo Atzeni - Piazza");
        lista.add("Agriturismo Sa Marighedda");
        lista.add("Agriturismo Praidis");
        lista.add("Agriturismo La Sorgente");
        lista.add("Agriturismo Camboni");
        lista.add("Centro Ippico Agrituristico del Sarrabus");
        lista.add("Agriturismo Il Nuraghe");
        lista.add("Agriturismo Domus de Janas");
        lista.add("Agriturismo Su Pirastu");
        lista.add("Agriturismo Ciccilanu");
        lista.add("Agriturismo S'Abioi");
        lista.add("Agriturismo Marongiu");
        lista.add("Agriturismo Nurazzolas");
        lista.add("Agriturismo Gutturus");*/

        return lista;
    }


    /**
     * Restituisce un numero intero casuale tra min e max
     *
     * @param min valore minimo
     * @param max valore massimo
     * @return intero casuale restituito
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    /**
     * Costruisce ArrayList con i nomi delle merci (codici)
     *
     * @return ArrayList of String
     */
    public static ArrayList<String> nomiMerci() {
        ArrayList<String> lista = new ArrayList<String>();

        /* TODO ampliare lista con altri nomi di merci (codici) */

        lista.add("Sedani");
        lista.add("Zucchine");
        lista.add("Cetrioli");
        lista.add("Pomodori");
        lista.add("Peperoni");
        lista.add("Melanzane");
        lista.add("Fagiolini");
        lista.add("Marmellata di arance");
        lista.add("Arance");
        lista.add("Pere");
        lista.add("Pesche");
        lista.add("Prezzemolo");
        lista.add("Olive");
        lista.add("Cipolle");
        lista.add("Aglio");
        lista.add("Peperoncini piccanti");
        lista.add("Prugne");
        lista.add("Ciliegie");
        lista.add("Angurie");
        lista.add("Meloni");
        return lista;
    }

    /**
     * Restituisce la distanza tra due agriturismo
     *
     * @param a1 Primo agrit.
     * @param a2 Secondo agrit.
     * @return Distanza tra i due agrit.
     */
    public static double distanza(Agriturismo a1, Agriturismo a2) {
        return Math.sqrt(Math.pow(a1.getXpos() - a2.getXpos(), 2.0) + Math.pow(a1.getYpos() - a2.getYpos(), 2.0));
    }

    public static double distanza(double x, double y, Agriturismo a){

        return Math.sqrt(Math.pow(x - a.getXpos(), 2.0) + Math.pow(y - a.getYpos(), 2.0));
    }

    /**
     * Restituisce un oggetto Furgone carico con la merce giacente in magazzino
     *
     * @param in Array contenente la merce in magazzino da caricare sul furgone
     * @return oggetto furgone se il caricamento merce e' ok altrimenti null
     */
    public static Furgone nuovoFurgone(ArrayList<MerceTrasportata> in) {
        Furgone f = new Furgone("Fiat Ducato 3", 100, 9.50225085150315, 39.2355205378512);
        if (f.carica(in)) {
            return f;
        }
        return null;
    }

    public static Person nuovoUtente() {
        return new Person("Pio", "Piu", "piopio", "piopio");
    }


}

