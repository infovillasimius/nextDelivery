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
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;

public class LoginActivity extends Activity {

    // nome per l'extra message da passare all'Activity per il risultato
    public static final String AMBIENTE_EXTRA = "com.project.funcool.nextdelivery.ambiente";
    Button loginButton;
    EditText username;
    EditText password;
    Space errorSpace;
    TextView errorText;
    boolean isResumed = false;
    boolean wrongPassword = false;
    boolean end=false;

    Person person;
    Ambiente ambiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //nasconde la barra del titolo
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_login);

        // person=new Person();
        ambiente = new Ambiente();
        person = ambiente.getUser();


        loginButton = (Button) findViewById(R.id.loginButton);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        errorSpace = (Space) findViewById(R.id.errorSpace);
        errorText = (TextView) findViewById(R.id.errorText);

        //errorSpace.setVisibility(View.GONE);
        //errorText.setVisibility(View.GONE);
        errorText.setText("");


        // gestiamo il click sul bottone di salvataggio
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(end){
                    LoginActivity.this.finish();
                }

                if (checkInput()) {
                    if (checkPerson()) {
                        wrongPassword = false;
                        checkInput();
                        // mostro prossima schermata
                        Intent showResult = new Intent(LoginActivity.this, InizioActivity.class);
                        showResult.putExtra(AMBIENTE_EXTRA, ambiente);
                        end=true;
                        checkInput();
                        startActivity(showResult);

                        LoginActivity.this.finish();
                    }
                    else{
                        wrongPassword = true;
                        checkInput();
                    }

                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        isResumed = true;
    }

    /**
     * Questo metodo effettua un controllo dei valori inseriti dall'utente
     *
     * @return true se tutti i campi sono stati inseriti correttamente
     * false altrimenti
     */
    private boolean checkInput() {
        int errors = 0;
        username.setError(null);
        password.setError(null);

        if(end){
            loginButton.setText("Uscita!");
        }

        if (username.getText() == null || username.getText().length() == 0) {
            // non e' stato inserito il nome
            username.setError("Inserire il proprio nome utente");
            wrongPassword=false;
            errors++;
        } else {
            // reset dell'errore
            username.setError(null);
        }
        if (password.getText() == null || password.getText().length() == 0) {
            // non e' stato inserito il cognome
            password.setError("Inserire la propria password");
            wrongPassword=false;
            errors++;
        } else {
            // reset dell'errore
            password.setError(null);
        }
        if (wrongPassword){
            username.setError("Inserire nome utente e password corretti");
            password.setError("Inserire nome utente e password corretti");
            errors = 99;
        }



        switch (errors) {
            case 0:
                /*errorText.setVisibility(View.GONE);
                errorSpace.setVisibility(View.GONE);*/
                errorText.setText("");
                break;
            case 1:
                /*errorText.setVisibility(View.VISIBLE);
                errorSpace.setVisibility(View.VISIBLE);*/
                errorText.setText("Si è verificato un errore");
                break;
            case 99:
                /*errorText.setVisibility(View.VISIBLE);
                errorSpace.setVisibility(View.VISIBLE);*/
                errorText.setText("Inseriti dati errati");
                wrongPassword=false;
                break;
            default:
                /*errorText.setVisibility(View.VISIBLE);
                errorSpace.setVisibility(View.VISIBLE);*/
                errorText.setText("Si sono verificati " + errors + " errori");
                break;
        }

        return errors == 0;
    }

    private boolean checkPerson() {
        return this.person.match(this.username.getText().toString(), this.password.getText().toString());
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
}