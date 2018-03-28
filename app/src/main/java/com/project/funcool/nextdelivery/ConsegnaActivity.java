package com.project.funcool.nextdelivery;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by anto on 01/11/15.
 */
public class ConsegnaActivity extends Activity {

    public static final String AMBIENTE_EXTRA = "com.project.funcool.nextdelivery.ambiente";

	Ambiente ambiente;
    Person person;
	boolean status=true;
    boolean nextDeliveryStatus=false;

    boolean isResumed = false;
	
    LinearLayout posLayout, caricoLayout, merciLayout, viaggioLayout;
    RelativeLayout confermaLayout, nextLayout;
    TextView title, livelloCarico, doveText, azioneSuMerci, helpMsg; //ok
    TextView destinazioneLabel, nuovaDestinazione, distanzaLabel;
    ImageView iconaTitle;
    ListView listView;
	NumberPickerFragment numberPickerFragment;
    Conferma conferma;
    Button okButton, nextDeliveryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //nasconde la barra del titolo
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_carica_furgone);

        //elementi nel titolo dell'app
		title = (TextView) findViewById(R.id.title);
        iconaTitle = (ImageView) findViewById(R.id.iconaTitle);

        //elementi presenti in infoLayout
        caricoLayout = (LinearLayout) findViewById(R.id.caricoLayout);
        livelloCarico = (TextView) findViewById(R.id.livelloCarico);
        posLayout = (LinearLayout) findViewById(R.id.posLayout);
        doveText = (TextView) findViewById(R.id.dove);

        //elementi presenti in listaMerciLayout
		azioneSuMerci = (TextView) findViewById(R.id.azioneSuMerci);
        merciLayout = (LinearLayout) findViewById(R.id.merciLayout);
        listView = (ListView) findViewById(R.id.listView);

        //elementi presenti in viaggioLayout
        viaggioLayout = (LinearLayout) findViewById(R.id.viaggioLayout);
        destinazioneLabel = (TextView) findViewById(R.id.destinazioneLabel);
        nuovaDestinazione = (TextView) findViewById(R.id.nuovaDestinazione);
        distanzaLabel = (TextView) findViewById(R.id.distanzaLabel);

        //elementi presenti in confermaLayout
        confermaLayout = (RelativeLayout) findViewById(R.id.confermaLayout);
        helpMsg = (TextView) findViewById(R.id.helpMsg);
        okButton = (Button) findViewById(R.id.confermaButton);

        //elementi presenti in nextLayout
        nextLayout = (RelativeLayout) findViewById(R.id.nextLayout);
        nextDeliveryButton = (Button) findViewById(R.id.nextDeliveryButton);

        // prendo l'intent dall'activity precedente
        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra(InizioActivity.AMBIENTE_EXTRA);
        if (obj instanceof Ambiente) {
            ambiente = (Ambiente) obj;
        } else {
            ambiente = new Ambiente();
        }

        person = ambiente.getUser();
		numberPickerFragment= new NumberPickerFragment();

		numberPickerFragment.setOnNumberPickerFragmentChanged(new NumberPickerFragment.NumberPickerFragmentListener() {
            @Override
            public void onNumberPickerFragmentOkButton(DialogFragment dialog, int n,MerceTrasportata m) {
                // applico i cambiamenti
                if(status){
                    if(n!=0) {
                        m.setQuantita(n);
                    } else {
                        ambiente.dove().getMerce().remove(m);
                    }
                }else{
                    if(n!=0) {
                        MerceTrasportata nuova = new MerceTrasportata(m.getCodice(), m.getQuantita() - n, m.getMittente(), Utility.nomiAziende().get(0));
                        m.setQuantita(n);
                        ambiente.furgone.carico.add(nuova);
                    } else {

                        ambiente.furgone.destinatari.remove(m.getDestinatario());
                        m.setDestinatario(Utility.nomiAziende().get(0));

                    }
                }
                updateUi();
            }

            @Override
            public void onNumberPickerFragmentCancelButton(DialogFragment dialog) {
                // non facciamo nulla
            }
        });


        conferma = new Conferma();

        conferma.setOnConfermaChanged(new Conferma.ConfermaListener() {
            @Override
            public void onConfermaOkButton(DialogFragment dialog, MerceTrasportata m) {
                // applico i cambiamenti
                if(status){
                    ambiente.furgone.carica(m);
                    ambiente.dove().getMerce().remove(m);
                }else{
                    if(ambiente.furgone.elenca(ambiente.dove().getNome()).size()<2){
                        ambiente.furgone.destinatari.remove(ambiente.dove().getNome());
                    }
                    ambiente.furgone.liv_carico-=m.getQuantita();
                    ambiente.furgone.getCarico().remove(m);
                }
                updateUi();
            }

            @Override
            public void onConfermaCancelButton(DialogFragment dialog) {
                // non facciamo nulla
            }
        });










        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Agriturismo posizione = ambiente.dove();
                if (!ambiente.end) {
                    if (status) {
                        if (ambiente.furgone.carica(posizione.getMerce())) {
                            ambiente.vuota(posizione);

                            if (!(ambiente.nextDelivery().getMerce().size() == 0 &&  posizione.equals(ambiente.nextDelivery()))) {
                                nextDeliveryStatus = true;
                            }

                            status = false;
                        }
                    } else {
                        ambiente.furgone.scarica(posizione.getNome());
                        status = true;
                        if(!ambiente.furgone.controlla(posizione.getMerce())){
                            nextDeliveryStatus = true;
                            status=false;
                        }
                    }
                    updateUi();
                } else { //eseguito quando premo sul tasto ESCI
                    ConsegnaActivity.this.finish();
                }
            }
        });

        nextDeliveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Agriturismo next;
                if (!ambiente.end) {

                    nextDeliveryStatus=false;
                    next = ambiente.nextDelivery();
                    ambiente.furgone.sposta(next.getXpos(), next.getYpos());

                    updateUi();

                } else{
                    nextDeliveryStatus=false;
                    updateUi();
                }

            }
        });

        updateUi();
    }

    private void updateUi() {
        if (!ambiente.end) { //se end=false allora non abbiamo ancora finito il giro di consegne

            String carico = "Livello carico furgone: " + ambiente.furgone.getLiv_carico() + "/" + ambiente.furgone.getCarico_max();
            livelloCarico.setText(carico);
            String luogo = ambiente.dove().getNome(); /**/
            String doveSei = "Ti trovi c/o: " + luogo; /**/
            doveText.setText(doveSei);

            final ArrayList<String> list = new ArrayList<>();
            final ArrayList<MerceTrasportata> merce;

            if (!nextDeliveryStatus) { //se nextDeliveryStatus=false allora si devono mostrare o le merci da caricare o quelle da scaricare
                posLayout.setVisibility(View.VISIBLE);
                merciLayout.setVisibility(View.VISIBLE);
                viaggioLayout.setVisibility(View.GONE);
                confermaLayout.setVisibility(View.VISIBLE);
                nextLayout.setVisibility(View.GONE);

                if (status) { //se status=true allora si mostrano le merci da caricare
                    iconaTitle.setImageResource(R.drawable.carico); //modifica src dell'icona
                    title.setText(R.string.caricoTitle); //modifica titolo schermata
                    azioneSuMerci.setText(R.string.azioneCarica); //modifica il messaggio dell'azione da eseguire per le merci elencate
                    //helpMsg.setText(R.string.helpCarico); /**/
                    okButton.setText(R.string.confermaCarico);
                } else { //se status=false allora si mostrano le merci da scaricare
                    iconaTitle.setImageResource(R.drawable.scarico); //modifica src dell'icona
                    title.setText(R.string.scaricoTitle); //modifica titolo schermata
                    /*if(luogo.equals("Magazzino"))
                        helpMsg.setText(R.string.helpScaricoVuoto);
                    else
                        helpMsg.setText(R.string.helpScarico); /**/
                    azioneSuMerci.setText(R.string.azioneScarica); //modifica il messaggio dell'azione da eseguire per le merci elencate
                    okButton.setText(R.string.confermaScarico);
                }

            } else { //se nextDeliveryStatus=true allora si deve mostrare la prossima destinazione da raggiungere
                iconaTitle.setImageResource(R.drawable.destinazione); //modifica src dell'icona
                title.setText(R.string.prossDestTitle); //modifica titolo schermata
                posLayout.setVisibility(View.GONE);
                merciLayout.setVisibility(View.GONE);
                viaggioLayout.setVisibility(View.VISIBLE);
                nuovaDestinazione.setText(ambiente.nextDelivery().toString());
                String dest = "Distanza: circa " + ((int) (Utility.distanza(ambiente.furgone.getX_pos(), ambiente.furgone.getY_pos(), ambiente.nextDelivery()) * 40000 / 1.80)/100.0) + " km\n";
                distanzaLabel.setText(dest);
                confermaLayout.setVisibility(View.GONE);
                nextLayout.setVisibility(View.VISIBLE);
                nextDeliveryStatus = false;
            }

            if (status) { //se status=true allora si mostrano le merci da caricare
                merce = ambiente.dove().getMerce();
                if(merce.size()>0) {
                    for (MerceTrasportata m : merce) {
                        if (m.getQuantita() > 0) {
                            list.add(m.toString());
                        }
                    }
                }
            } else { //se status=false allora si mostrano le merci da scaricare
                merce = ambiente.furgone.elenca(ambiente.dove().getNome());
                if(merce.size()>0) {
                    for (MerceTrasportata m : merce) {
                        if (m.getQuantita() > 0) {
                            list.add(m.toString2());
                        }
                    }
                }
            }

            if (list.size() == 0) {
                list.add("Nulla!");
                if(status)
                    helpMsg.setText(R.string.helpCaricoVuoto);
                else
                    helpMsg.setText(R.string.helpScaricoVuoto);
            } else{
                if(status)
                    helpMsg.setText(R.string.helpCarico);
                else {
                    if (luogo.equals("Magazzino"))
                        helpMsg.setText(R.string.helpScaricoVuoto);
                    else
                        helpMsg.setText(R.string.helpScarico);
                }
            }

            final StableArrayAdapter adapter = new StableArrayAdapter(this,
                    android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {

                    if (merce.size() > 0 && !(merce.get(position).getDestinatario().equals("Magazzino") && !status) ) {
                        if (status) {
                            numberPickerFragment.n = merce.get(position).getQuantita();
                            numberPickerFragment.nMin = 0;
                            numberPickerFragment.nMax = ambiente.furgone.getCarico_max() - ambiente.furgone.getLiv_carico() - ambiente.dove().quantita();
                            numberPickerFragment.m = merce.get(position);
                            numberPickerFragment.show(getFragmentManager(), "numberPicker");
                        } else {
                            numberPickerFragment.n = merce.get(position).getQuantita();
                            numberPickerFragment.nMin = 0;
                            numberPickerFragment.nMax = merce.get(position).getQuantita();
                            numberPickerFragment.m = merce.get(position);
                            numberPickerFragment.show(getFragmentManager(), "numberPicker");
                        }
                    }
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, final View view,
                                        int position, long id) {

                    conferma.status=status;
                    conferma.m=merce.get(position);
                    conferma.show(getFragmentManager(), "Conferma");

                    return true;
                }
            });

        } else { //se end=true allora il giro di consegne previste per la giornata è terminato
            /*String benvenuto = "Furgone: " + ambiente.furgone.getNome() + "\nCarico " + ambiente.furgone.getLiv_carico() + "/" + ambiente.furgone.getCarico_max();
            livelloCarico.setText(benvenuto);
            String doveSei = "Ti trovi c/o " + ambiente.dove().getNome() + "\nHai percorso "+(int)(ambiente.furgone.getDistanza()*40000/180)+" km circa\nPer oggi abbiamo finito!";
            doveText.setText(doveSei);*/

            /*caricoLayout.setVisibility(View.GONE);
            //posLayout.setVisibility(View.VISIBLE);

            merciLayout.setVisibility(View.GONE);
            viaggioLayout.setVisibility(View.GONE);
            nextLayout.setVisibility(View.GONE);

            confermaLayout.setVisibility(View.VISIBLE);
            helpMsg.setText(R.string.helpFine);
            okButton.setText("Esci!");
            okButton.setVisibility(View.VISIBLE);

            iconaTitle.setImageResource(R.drawable.arrivo); //modifica src dell'icona
            title.setText(R.string.fineTitle); //modifica titolo schermata*/

            //chiamo la schermata finale dell'app
            Intent apriFineConsegne = new Intent(ConsegnaActivity.this, FineActivity.class);
            apriFineConsegne.putExtra(AMBIENTE_EXTRA, ambiente);
            startActivity(apriFineConsegne);
            ConsegnaActivity.this.finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isResumed = true;
    }

    @Override
    public void onBackPressed() {
        new ChiudiApp().show(getFragmentManager(), "Chiudi app");

    }

    //Classe Dialog
    public static class ChiudiApp extends DialogFragment {

        @Override
        public Dialog onCreateDialog (Bundle savedInstanceState){

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(false);
            builder.setMessage("Sei sicuro di voler uscire dall'app?")
                    .setPositiveButton("Esci", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    })
                    .setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            return builder.create();
        }

    }


}
