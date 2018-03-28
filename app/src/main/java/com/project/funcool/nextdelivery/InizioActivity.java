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
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class InizioActivity extends Activity {

    public static final String AMBIENTE_EXTRA = "com.project.funcool.nextdelivery.ambiente";

    Ambiente ambiente;
    Person person;

    boolean isResumed = false;

    TextView benvenutoLabel, idFurgone, marcaFurgone, modelloFurgone;
    Button confermaVettura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //nasconde la barra del titolo
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_inizio);

        benvenutoLabel = (TextView) findViewById(R.id.benvenutoLabel);
        idFurgone = (TextView) findViewById(R.id.numF);
        marcaFurgone = (TextView) findViewById(R.id.marcaF);
        modelloFurgone = (TextView) findViewById(R.id.modelloF);

        confermaVettura = (Button) findViewById(R.id.confermaVettura);

        // prendo l'intent dall'activity precedente
        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra(LoginActivity.AMBIENTE_EXTRA);
        if (obj instanceof Ambiente) {
            ambiente = (Ambiente) obj;
        } else {
            ambiente = new Ambiente();
        }

        person = ambiente.getUser(); //rende l'utente loggato

        confermaVettura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent apriInizioConsegne = new Intent(InizioActivity.this, ConsegnaActivity.class);
                apriInizioConsegne.putExtra(AMBIENTE_EXTRA, ambiente);
                startActivity(apriInizioConsegne);
                InizioActivity.this.finish();
            }
        });

        updateUi(); //metodo che permette di aggiornare la schermata con i dati prelevati da quella precedente

    }

    private void updateUi() {

        String benvenuto = "Benvenuto " + person.getNome() + " " + person.getCognome();
        benvenutoLabel.setText(benvenuto);

        String[] furgone = ambiente.furgone.getNome().split(" ");

        idFurgone.setText("Prendi dal parcheggio il furgone n°: " + furgone[2]);
        marcaFurgone.setText("Marca: " + furgone[0]);
        modelloFurgone.setText("Modello: " + furgone[1]);

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

    @Override
    public void onResume() {
        super.onResume();
        isResumed = true;
    }
}
