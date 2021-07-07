package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
public class SprBledy extends AppCompatActivity implements View.OnClickListener {

    int nrPytania = 0;
    Odpowiedzi odp;

    Quiz quiz;
    Pytanie pytanie;
    int numerQuizu;
    TextView numerPytania;

    Button buttOp1;
    Button buttOp2;
    Button buttOp3;
    Button buttOp4;
    Button buttOp11;
    Button buttOp22;
    Button buttOp33;
    Button buttOp44;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spr_bledy);


        buttOp1=findViewById(R.id.opcja1);
        buttOp2=findViewById(R.id.opcja2);
        buttOp3=findViewById(R.id.opcja3);
        buttOp4=findViewById(R.id.opcja4);
        buttOp11=findViewById(R.id.opcja11);
        buttOp22=findViewById(R.id.opcja22);
        buttOp33=findViewById(R.id.opcja33);
        buttOp44=findViewById(R.id.opcja44);



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
//pobranie odpowiedzi i nr quizu z poprzedniego activity
        odp = new Gson().fromJson(extras.getString("odpowiedzi"), Odpowiedzi.class);
        numerQuizu = extras.getInt("nrQuizu");

         //łączenie  z xml i wybieranie do listy

        quiz = pobierzQuiz();

        TextView pytania = findViewById(R.id.pytanie);
        pytania.setText(odp.quiz.pytania.get(nrPytania).pytanie);
numerPytania=findViewById(R.id.nrPytania);
numerPytania.setText("Pytanie "+(nrPytania+1)+"/"+quiz.pytania.size());
      buttOp1.setText(quiz.pytania.get(nrPytania).op1);
        buttOp2.setText(quiz.pytania.get(nrPytania).op2);
        buttOp3.setText(quiz.pytania.get(nrPytania).op3);
        buttOp4.setText(quiz.pytania.get(nrPytania).op4);
        buttOp11.setText(quiz.pytania.get(nrPytania).op1);
        buttOp22.setText(quiz.pytania.get(nrPytania).op2);
        buttOp33.setText(quiz.pytania.get(nrPytania).op3);
        buttOp44.setText(quiz.pytania.get(nrPytania).op4);

        Button buttonP;
        buttonP = findViewById(R.id.buttonP);
        buttonP.setOnClickListener(this);
        Button buttonL;
        buttonL = findViewById(R.id.buttonL);
        buttonL.setOnClickListener(this);

        Button btnMenu = findViewById(R.id.Menu);
        btnMenu.setOnClickListener(this);

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
//Pierwsze wyświetlenie activity (aktualizcja kolorów)

                if (odp.quiz.pytania.get(nrPytania).odp1 == false)
                    buttOp1.setBackgroundResource(R.drawable.kafelek);
                else
                    buttOp1.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
                if (odp.quiz.pytania.get(nrPytania).odp2 == false)
                    buttOp2.setBackgroundResource(R.drawable.kafelek);
                else
                    buttOp2.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
                if (odp.quiz.pytania.get(nrPytania).odp3 == false)
                    buttOp3.setBackgroundResource(R.drawable.kafelek);
                else
                    buttOp3.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
                if (odp.quiz.pytania.get(nrPytania).odp4 == false)
                    buttOp4.setBackgroundResource(R.drawable.kafelek);
                else
                    buttOp4.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));



                if (quiz.pytania.get(nrPytania).odp1 == false)
                    buttOp11.setBackgroundResource(R.drawable.kafelek);
                else
                    buttOp11.setBackgroundResource(R.drawable.kafelek_poprawna_dp);
                if (quiz.pytania.get(nrPytania).odp2 == false)
                    buttOp22.setBackgroundResource(R.drawable.kafelek);
                else
                    buttOp22.setBackgroundResource(R.drawable.kafelek_poprawna_dp);
                if (quiz.pytania.get(nrPytania).odp3 == false)
                    buttOp33.setBackgroundResource(R.drawable.kafelek);
                else
                    buttOp33.setBackgroundResource(R.drawable.kafelek_poprawna_dp);
                if (quiz.pytania.get(nrPytania).odp4 == false)
                    buttOp44.setBackgroundResource(R.drawable.kafelek);
                else
                    buttOp44.setBackgroundResource(R.drawable.kafelek_poprawna_dp);


        }
    }







    @Override
    public void onClick(View v) {

        TextView aa = findViewById(R.id.pytanie);

        switch (v.getId()) {
            case R.id.buttonP:
                if (odp.quiz.pytania.size() > nrPytania + 1) {
                    nrPytania++;

                } else {
                    View context;
                    context =  findViewById(R.id.sprBl);
                    Intent i = new Intent(context.getContext(), MainActivity.class);
                    startActivity(i);
                    finish();

                }

                break;

            case R.id.buttonL:
                if (nrPytania > 0) {

                    nrPytania--;


                } else {

                }
                break;
            case R.id.Menu:
                View context;

                context = findViewById(R.id.sprBl);
                Intent i = new Intent(context.getContext(), MainActivity.class);
                startActivity(i);
                finish();


                break;

            default:

                break;

        }


        numerPytania.setText("Pytanie "+(nrPytania+1)+"/"+quiz.pytania.size());
        buttOp1.setText(quiz.pytania.get(nrPytania).op1);
        buttOp2.setText(quiz.pytania.get(nrPytania).op2);
        buttOp3.setText(quiz.pytania.get(nrPytania).op3);
        buttOp4.setText(quiz.pytania.get(nrPytania).op4);
        buttOp11.setText(quiz.pytania.get(nrPytania).op1);
        buttOp22.setText(quiz.pytania.get(nrPytania).op2);
        buttOp33.setText(quiz.pytania.get(nrPytania).op3);
        buttOp44.setText(quiz.pytania.get(nrPytania).op4);


        if (odp.quiz.pytania.get(nrPytania).odp1 == false)
            buttOp1.setBackgroundResource(R.drawable.kafelek);
        else
            buttOp1.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
        if (odp.quiz.pytania.get(nrPytania).odp2 == false)
            buttOp2.setBackgroundResource(R.drawable.kafelek);
        else
            buttOp2.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
        if (odp.quiz.pytania.get(nrPytania).odp3 == false)
            buttOp3.setBackgroundResource(R.drawable.kafelek);
        else
            buttOp3.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
        if (odp.quiz.pytania.get(nrPytania).odp4 == false)
            buttOp4.setBackgroundResource(R.drawable.kafelek);
        else
            buttOp4.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));


        if (quiz.pytania.get(nrPytania).odp1 == false)
            buttOp11.setBackgroundResource(R.drawable.kafelek);
        else
            buttOp11.setBackgroundResource(R.drawable.kafelek_poprawna_dp);
        if (quiz.pytania.get(nrPytania).odp2 == false)
            buttOp22.setBackgroundResource(R.drawable.kafelek);
        else
            buttOp22.setBackgroundResource(R.drawable.kafelek_poprawna_dp);
        if (quiz.pytania.get(nrPytania).odp3 == false)
            buttOp33.setBackgroundResource(R.drawable.kafelek);
        else
            buttOp33.setBackgroundResource(R.drawable.kafelek_poprawna_dp);
        if (quiz.pytania.get(nrPytania).odp4 == false)
            buttOp44.setBackgroundResource(R.drawable.kafelek);
        else
            buttOp44.setBackgroundResource(R.drawable.kafelek_poprawna_dp);

        aa.setText(quiz.pytania.get(nrPytania).pytanie);








    }

    public Quiz pobierzQuiz() {
        Quiz quiz1 = null;
        try {
            InputStream is1 = getAssets().open("dane.xml");
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();
            myParser.setInput(is1, null);
            String text = null;
            int zmPom = 0;
            int ktorePytanie = -1;
            int event ;

            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (name.equals("Quiz") && zmPom == 0) {

                            if (Integer.parseInt(myParser.getAttributeValue(null, "id")) == numerQuizu) {

                                quiz1 = new Quiz();
                                quiz1.id = Integer.parseInt(myParser.getAttributeValue(null, "id"));
                                quiz1.nazwa = myParser.getAttributeValue(null, "nazwa");
                                zmPom++;
                            }

                        } else if (name.equals("pytanie") && zmPom == 1) {
                            quiz1.pytania.add(pytanie = new Pytanie());

                            ktorePytanie++;
                            quiz1.pytania.get(ktorePytanie).pytanie = myParser.getAttributeValue(null, "value");
                        }

                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (quiz1 != null && zmPom == 1) {
                            if (name.equals("op1")) {
                                quiz1.pytania.get(ktorePytanie).op1 = text;
                            } else if (name.equals("op2")) {
                                quiz1.pytania.get(ktorePytanie).op2 = text;
                            } else if (name.equals("op3")) {
                                quiz1.pytania.get(ktorePytanie).op3 = text;
                            } else if (name.equals("op4")) {
                                quiz1.pytania.get(ktorePytanie).op4 = text;
                            } else if (name.equals("odp1")) {
                                quiz1.pytania.get(ktorePytanie).odp1 = Boolean.parseBoolean(text);
                            } else if (name.equals("odp2")) {
                                quiz1.pytania.get(ktorePytanie).odp2 = Boolean.parseBoolean(text);
                            } else if (name.equals("odp3")) {
                                quiz1.pytania.get(ktorePytanie).odp3 = Boolean.parseBoolean(text);
                            } else if (name.equals("odp4")) {
                                quiz1.pytania.get(ktorePytanie).odp4 = Boolean.parseBoolean(text);
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
        return quiz1;
    }

}
