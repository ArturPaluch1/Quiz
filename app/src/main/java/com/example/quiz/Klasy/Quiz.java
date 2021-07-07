package com.example.quiz.Klasy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Quiz implements Serializable {
    public int id;
    public String nazwa;
    public List<Pytanie> pytania = new ArrayList<Pytanie>();

    public Quiz(Quiz q1) {
        this.nazwa = q1.nazwa;
        Pytanie temp;
        for (Pytanie i : q1.pytania) {
            this.pytania.add(temp = new Pytanie(i));
        }

    }

    public Quiz() {

    }
}
