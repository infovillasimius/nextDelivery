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

public class FineActivity extends Activity {

    Ambiente ambiente;
    Person person;

    boolean isResumed = false;

    TextView arrivederciLabel, distPercorsa;
    Button esci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //nasconde la barra del titolo
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_fine);

        distPercorsa = (TextView) findViewById(R.id.distPercorsa);
        arrivederciLabel = (TextView) findViewById(R.id.arrivederciLabel);
        esci = (Button) findViewById(R.id.esci);

        // prendo l'intent dall'activity precedente
        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra(InizioActivity.AMBIENTE_EXTRA);
        if (obj instanceof Ambiente) {
            ambiente = (Ambiente) obj;
        } else {
            ambiente = new Ambiente();
        }

        person = ambiente.getUser();

        esci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChiudiApp().show(getFragmentManager(), "Chiudi app");
            }
        });

        updateUi();
    }

    private void updateUi() {

        String km = "Hai percorso " + (int)(ambiente.furgone.getDistanza()*40000/180) + " km circa";
        distPercorsa.setText(km);

        String saluto = "Alla prossima "+ person.getNome()+ " " + person.getCognome();
        arrivederciLabel.setText(saluto);

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
