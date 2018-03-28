package com.project.funcool.nextdelivery;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class StartActivity extends Activity {

    ImageView logo;
    static final int STOPSPLASH = 0;
    static final int SPLASHTIME = 4000; //il tempo in millisecondi durante il quale verrà visualizzato il logo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //nasconde la barra del titolo
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_start);

        logo = (ImageView) findViewById(R.id.logo);
        Message msg = new Message();

        msg.what = STOPSPLASH;

        splashHandler.sendMessageDelayed(msg, SPLASHTIME);


        //gestione click sul pulsante INIZIA
        /*start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent apriLoginPage = new Intent(StartActivity.this,LoginActivity.class);
                startActivity(apriLoginPage);
            }
        });*/

    }

    //handler per splash screen
    private Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOPSPLASH:
                    //rimuove lo SplashScreen dalla vista
                    logo.setVisibility(View.GONE);

                    //terminato il tempo di visualizzazione del logo, appare la schermata di login
                    Intent apriLoginPage = new Intent(StartActivity.this,LoginActivity.class);
                    startActivity(apriLoginPage);
                    //se dalla schermata di login si preme il tasto back, l'app viene chiusa
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };

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
}
