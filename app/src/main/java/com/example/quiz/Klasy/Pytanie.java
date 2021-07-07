package com.example.quiz.Klasy;

import java.io.Serializable;

public class Pytanie implements Serializable {

    public String pytanie;
    public String op1 = "";
    public String op2 = "";
    public String op3 = "";
    public String op4 = "";

    public boolean odp1 = false;
    public boolean odp2 = false;
    public boolean odp3 = false;
    public boolean odp4 = false;



    public Pytanie(Pytanie p1) {
        this.pytanie = p1.pytanie;
        this.op1 = p1.op1;
        this.op2 = p1.op2;
        this.op3 = p1.op3;
        this.op4 = p1.op4;

        this.odp1 = p1.odp1;
        this.odp2 = p1.odp2;
        this.odp3 = p1.odp3;
        this.odp4 = p1.odp4;
    }

    public Pytanie() {
    }



}


