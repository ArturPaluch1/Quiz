package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quiz.Klasy.Odpowiedzi;
import com.example.quiz.Klasy.Pytanie;
import com.example.quiz.Klasy.Quiz;
import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;


public class Podsumowanie extends AppCompatActivity implements OnClickListener {
    Odpowiedzi odp;
    Quiz quiz = null;
    Pytanie pytanie = null;
    int nrQuizu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podsumowanie);


        Button btnMenu = findViewById(R.id.btnDoMenu);
        Button btnPowrot = findViewById(R.id.btnSprBl);
        btnMenu.setOnClickListener(this);
        btnPowrot.setOnClickListener(this);


        TextView twWynik = findViewById(R.id.textViewWyniki);
        TextView twQuiz = findViewById(R.id.textViewQuiz);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
//rozpakowanie jSon do obiektu klasy Odpowiedzi i numeru quizu
        odp = new Gson().fromJson(extras.getString("odpowiedzi"), Odpowiedzi.class);
        nrQuizu = extras.getInt("nrQuizu");
        int minalCzas= extras.getInt("minalCzas");

      pobierzDane();

//Nazwa quizu
        twQuiz.setText("Quiz: " + odp.quiz.nazwa);

        //Wyświetlanie podsumowania w zależności od błędów
        if(odp.sprawdzOdp(quiz).get(1)==quiz.pytania.size())
            twWynik.setText("\nOpuściłeś wszystkie pytania...");
        else if(odp.sprawdzOdp(quiz).get(0) == 0&&odp.sprawdzOdp(quiz).get(1) == 0)
            twWynik.setText("Brawo! Nie zrobiłeś błędu w żadnym pytaniu :U");
            else {
            if(minalCzas==0)
            twWynik.setText("Zrobiłeś " + odp.sprawdzOdp(quiz).get(0) + " błędów na " + quiz.pytania.size() + "pytań."+"\n Opuściłeś "+odp.sprawdzOdp(quiz).get(1)+"pytań");
            else
            {
                if(odp.sprawdzOdp(quiz).get(1)==quiz.pytania.size())
                    twWynik.setText("\nTwój czas minął!\n\n Opuściłeś wszystkie pytania...");

                else
                twWynik.setText("Twój czas minął!\n\nZrobiłeś " + odp.sprawdzOdp(quiz).get(0) + " błędów na " + quiz.pytania.size() + "pytań."+"\n Opuściłeś "+odp.sprawdzOdp(quiz).get(1)+"pytań");

            }
                 }


    }

    private void pobierzDane() {
        try {
            InputStream is1 = getAssets().open("dane.xml");
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();
            myParser.setInput(is1, null);
            String text = null;
            int ktorePytanie = -1;
            int event;
            int zmPom = 0;
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (name.equals("Quiz") && zmPom == 0) {

                            if (Integer.parseInt(myParser.getAttributeValue(null, "id")) == nrQuizu) {

                                quiz = new Quiz();
                                quiz.id = Integer.parseInt(myParser.getAttributeValue(null, "id"));
                                quiz.nazwa = myParser.getAttributeValue(null, "nazwa");
                                zmPom++;
                            }

                            //
                        } else if (name.equals("pytanie") && zmPom == 1) {
                            quiz.pytania.add(pytanie = new Pytanie());

                            ktorePytanie++;
                            quiz.pytania.get(ktorePytanie).pytanie = myParser.getAttributeValue(null, "value");
                        }

                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (quiz != null && zmPom == 1) {
                            if (name.equals("op1")) {
                                quiz.pytania.get(ktorePytanie).op1 = text;
                            } else if (name.equals("op2")) {
                                quiz.pytania.get(ktorePytanie).op2 = text;
                            } else if (name.equals("op3")) {
                                quiz.pytania.get(ktorePytanie).op3 = text;
                            } else if (name.equals("op4")) {
                                quiz.pytania.get(ktorePytanie).op4 = text;
                            } else if (name.equals("odp1")) {
                                quiz.pytania.get(ktorePytanie).odp1 = Boolean.parseBoolean(text);
                            } else if (name.equals("odp2")) {
                                quiz.pytania.get(ktorePytanie).odp2 = Boolean.parseBoolean(text);
                            } else if (name.equals("odp3")) {
                                quiz.pytania.get(ktorePytanie).odp3 = Boolean.parseBoolean(text);
                            } else if (name.equals("odp4")) {
                                quiz.pytania.get(ktorePytanie).odp4 = Boolean.parseBoolean(text);
                            } else if (name.equals("Quiz")) {
                                zmPom++;
                            }
                        }
                }
                try {
                    event = myParser.next();
                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnDoMenu:
               //przejście do menu
                Intent i = new Intent(findViewById(R.id.Podsumowanie).getContext(), MainActivity.class);
                startActivity(i);
                finish();

                break;

            case R.id.btnSprBl:
//przekazanie do Activity SprBledy odpowiedzi i numeru quizu
                String json = new Gson().toJson(odp);
                Bundle extras = new Bundle();
                extras.putString("odpowiedzi", json);
                extras.putInt("nrQuizu", nrQuizu);

;
                Intent j = new Intent(findViewById(R.id.Podsumowanie).getContext(), SprBledy.class);

                j.putExtras(extras);
                startActivity(j);
                finish();
                break;
        }


    }
}
