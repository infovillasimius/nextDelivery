package com.project.funcool.nextdelivery;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by anto on 24/02/16.
 */

public class Conferma extends DialogFragment {

    private ConfermaListener listener;
    public boolean status;
    public MerceTrasportata m;


    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState){

        String siBtn;

        if(status){
            siBtn="Conferma carico";
        } else {
            siBtn="Conferma scarico";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setMessage("Confermi?");
        builder.setPositiveButton(siBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // questo metodo viene richiamato quando si preme il bottone ok

                // notifichiamo l'evento
                if (listener != null) {
                    // notifichiamo l'evento
                    listener.onConfermaOkButton(Conferma.this, m);
                }
            }
        });
        builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (listener != null) {
                    // notifichiamo l'evento
                    listener.onConfermaCancelButton(Conferma.this);
                }
            }
        });


        return builder.create();
    }

    public void setOnConfermaChanged(ConfermaListener l) {
        this.listener = l;
    }

    public interface ConfermaListener {
        void onConfermaOkButton(DialogFragment dialog, MerceTrasportata m);

        void onConfermaCancelButton(DialogFragment dialog);
    }


}

