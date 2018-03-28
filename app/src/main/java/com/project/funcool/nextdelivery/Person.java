package com.project.funcool.nextdelivery;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by anto on 19/10/15.
 */
public class Person implements Serializable{

    private String nome;
    private String cognome;
    private String username;
    private String password;

    public Person() {
        this.nome = "";
        this.cognome = "";
        this.username = "";
        this.password = "";
    }

    public Person(String nome, String cognome, String username, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }


    public boolean match(String username, String password) {
        return (this.username.equals(username) && this.password.equals(password));
    }

}
