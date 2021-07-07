package com.example.quiz.Klasy;

import java.util.ArrayList;
import java.util.List;

public class Odpowiedzi {
    public Quiz quiz;
    // public Pytanie pytanieO;

    public Odpowiedzi(Quiz quiz) {
        this.quiz = new Quiz(quiz);
        // for(Pytanie i:this.quiz.pytania)
        for (int i = 0; i < this.quiz.pytania.size(); i++) {
            this.quiz.pytania.get(i).odp1 = false;
            this.quiz.pytania.get(i).odp2 = false;
            this.quiz.pytania.get(i).odp3 = false;
            this.quiz.pytania.get(i).odp4 = false;
        }
    }

    public List<Integer> sprawdzOdp(Quiz quiz) {
        Integer bledy = 0;
List<Integer> lista1= new ArrayList<Integer>();
        Integer opuszczone=0;
        for (int i = 0; i < this.quiz.pytania.size(); i++)
        {
            if (quiz.pytania.get(i).pytanie.equals(this.quiz.pytania.get(i).pytanie))
            {
                if(this.quiz.pytania.get(i).odp1==false&&this.quiz.pytania.get(i).odp2==false&&this.quiz.pytania.get(i).odp3==false&&this.quiz.pytania.get(i).odp4==false)
                {
                    opuszczone++;
                }
                else
                {
                    if (quiz.pytania.get(i).odp1 == this.quiz.pytania.get(i).odp1)
                    {
                        if (quiz.pytania.get(i).odp2 == this.quiz.pytania.get(i).odp2)
                        {
                            if (quiz.pytania.get(i).odp3 == this.quiz.pytania.get(i).odp3)
                            {
                                if (quiz.pytania.get(i).odp4 == this.quiz.pytania.get(i).odp4)
                                {

                                }
                                else
                                {
                                    bledy++;
                                    //i++;
                                }
                            } else {
                                bledy++;
                                //   i++;
                            }
                        } else {
                            bledy++;
                            //   i++;
                        }

                    } else {
                        bledy++;
                        //   i++;
                    }

                }

                }

        }
lista1.add(bledy);
        lista1.add(opuszczone);

        return lista1;
    }


}
