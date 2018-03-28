package com.project.funcool.nextdelivery;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by anto on 01/11/15.
 */
public class NumberPickerFragment extends DialogFragment {

    private NumberPickerFragmentListener listener;
    int n,nMax,nMin;
    String s="";
    MerceTrasportata m;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);


        final NumberPicker numberPicker = new NumberPicker(getActivity());

        //Impostiamo lo spinner...
        if(nMax<0){
            nMax=0;
        }
        if(nMin>nMax){
            nMin=nMax;
        }

        numberPicker.setMaxValue(nMax);
        numberPicker.setMinValue(nMin);
        numberPicker.setValue(n);
        s="Seleziona la quantità di "+m.getCodice();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // inseriamo il picker nella dialog
        builder.setView(numberPicker);
        builder.setMessage(s);
        // impostiamo i bottoni Ok e Cancel
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // questo metodo viene richiamato quando si preme il bottone ok
                n=numberPicker.getValue();
                // notifichiamo l'evento
                if (listener != null) {
                    // notifichiamo l'evento
                    listener.onNumberPickerFragmentOkButton(NumberPickerFragment.this, n,m);
                }
            }
        });
        builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (listener != null) {
                    // notifichiamo l'evento
                    listener.onNumberPickerFragmentCancelButton(NumberPickerFragment.this);
                }
            }
        });


        return builder.create();
    }

    public void setOnNumberPickerFragmentChanged(NumberPickerFragmentListener l) {
        this.listener = l;
    }

    public interface NumberPickerFragmentListener {
        void onNumberPickerFragmentOkButton(DialogFragment dialog, int n,MerceTrasportata m);

        void onNumberPickerFragmentCancelButton(DialogFragment dialog);
    }

}
