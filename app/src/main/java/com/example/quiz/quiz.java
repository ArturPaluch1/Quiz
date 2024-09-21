package com.example.quiz;



import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.List;

public class quiz extends AppCompatActivity implements View.OnClickListener {



    Integer numerQuizu;

    Odpowiedzi odp;

    int nrPytania = 0;
    Quiz quiz = null;  //Pobrany quiz
    Pytanie pytanie = null;
    TextView tvNrPytania=null;
    TextView tvCzas=null;
    CountDownTimer cTimer = null;
    Button buttOp1;
    Button buttOp2;
    Button buttOp3;
    Button buttOp4;
    TextView tvPytanie;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


         buttOp1=findViewById(R.id.opcja1);
         buttOp2=findViewById(R.id.opcja2);
         buttOp3=findViewById(R.id.opcja3);
         buttOp4=findViewById(R.id.opcja4);
buttOp1.setOnClickListener(this);
buttOp2.setOnClickListener(this);
buttOp3.setOnClickListener(this);
buttOp4.setOnClickListener(this);








        //Pobieranie numeru wybranego quizu z poprzedniego activity
        numerQuizu = getIntent().getIntExtra("nrQuizu", -1);

        tvPytanie = findViewById(R.id.pytanie);

       // łączenie  z xml i wybieranie do listy
        quiz = pobierzQuiz();

        //przypisanie do kontrolki TextView pytania z quizu
        tvPytanie.setText(quiz.pytania.get(nrPytania).pytanie);

        //tworzenie obiektu odpowiedzi gracza
        odp = new Odpowiedzi(quiz);



     buttOp1.setText(quiz.pytania.get(nrPytania).op1);
        buttOp2.setText(quiz.pytania.get(nrPytania).op2);
        buttOp3.setText(quiz.pytania.get(nrPytania).op3);
        buttOp4.setText(quiz.pytania.get(nrPytania).op4);




        Button buttonP;
        buttonP = findViewById(R.id.buttonP);
        buttonP.setOnClickListener(this);
        Button buttonL;
        buttonL = findViewById(R.id.buttonL);
        buttonL.setOnClickListener(this);


        tvNrPytania=findViewById(R.id.numerPytania);


        tvNrPytania.setText("Pytanie: "+(nrPytania+1)+"/"+quiz.pytania.size() );






//timer

        tvCzas=findViewById(R.id.czas);


        int czasPytan=quiz.pytania.size()*10000;
      cTimer=  new CountDownTimer(czasPytan, 1000) {

            public void onTick(long millisUntilFinished) {
                Integer c=(int)millisUntilFinished/1000;
Integer minuty=c/60;
Integer sekundy=c%60;
                tvCzas.setText(minuty.toString()+":"+sekundy.toString().format("%02d", sekundy) );

            }

            public void onFinish() {

                zmianaActivityNaPodsumowanie(1);
            }

        }.start();






}





    private Quiz pobierzQuiz() {
        try {
            InputStream is1 = getAssets().open("dane.xml");
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();
            myParser.setInput(is1, null);
            String text = null;
            int zmPom = 0;  //zmienna==1 informuje, że został odnaleziony oczekiwany quiz
            int ktorePytanie = -1;
            int event;
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:

                        if (name.equals("Quiz") && zmPom == 0)
                        {//zmPom==0 -> nie znaleziono jeszcze poszukiwanego quizu
//sprawdzenie czy id quizu w pliku xml równa się id przekazanemu z poprzedniego activity
                            if (myParser.getAttributeValue(null, "id").equals(numerQuizu.toString())) {
//id zgadzają się tworzony jest obiekt klasy Quiz
                                quiz = new Quiz();
                                quiz.id = Integer.parseInt(myParser.getAttributeValue(null, "id"));
                                quiz.nazwa = myParser.getAttributeValue(null, "nazwa");

                                zmPom++;//Quiz znaleziony zmienna !=0 nie będzie tworzony następny obiekt klasy Quiz
                            }

                            //
                        }
                        else if (name.equals("pytanie") && zmPom == 1)
                        { //tag==pytanie, a quiz został znalziony dodaje pytanie do quizu
                            quiz.pytania.add(pytanie = new Pytanie());

                            ktorePytanie++;//numer pytania
                            //przypisanie pytania do obiektu klasy Pytanie
                            quiz.pytania.get(ktorePytanie).pytanie = myParser.getAttributeValue(null, "value");

                        }


                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        //przypisanie opcji i odpowiedzi dop pytania
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
                                zmPom++;//zmPom>1 następne znalezione tagi Pytanie nie będą dodawały kolejnych pytań do obiektu quiz
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
        return quiz;
    }


    public List<String> pobierzOpcje() {
//pobieranie z quizu opcji i zapisywanie ich do listy
        List<String> bb = new ArrayList<String>();
        bb.add(quiz.pytania.get(nrPytania).op1);
        bb.add(quiz.pytania.get(nrPytania).op2);
        bb.add(quiz.pytania.get(nrPytania).op3);
        bb.add(quiz.pytania.get(nrPytania).op4);


        return bb;


    }
    void zmianaActivityNaPodsumowanie(int minalCzas)
    {
        //skończyły się pytania zapisywanie odpowiedzi jako string
        String json = new Gson().toJson(odp);

        //opakowywanie  zmiennych do przesłania do jednego obiektu klasy Bundle i przekazanie ich za pomocą Intent do następnego activity i zamknięcie obecnego
        Bundle extras = new Bundle();
        extras.putString("odpowiedzi", json);
        extras.putInt("nrQuizu", numerQuizu);

        if(minalCzas!=0)
        {
            extras.putInt("minalCzas", 1);
        }



        Intent i = new Intent(findViewById(R.id.quiz).getContext(), Podsumowanie.class);

        i.putExtras(extras);
        startActivity(i);
        finish();
    }


    @Override
    public void onClick(View v) {
        TextView twPytanie = findViewById(R.id.pytanie);
        List<String> bb = new ArrayList<String>();


        switch (v.getId()) {
            case R.id.buttonP:
                //naciśnięty przycisk następny , jest w quizie jeszcze pytanie
                if (quiz.pytania.size() > nrPytania + 1) {
                    nrPytania++;

                    break;
                } else {
                   cTimer.cancel();
                    zmianaActivityNaPodsumowanie(0);

                    break;
                }



            case R.id.buttonL:
                //naciśnięty przycisk poprzedni. Jeśli nie jest wyświetlane pierwsze pytanie zmniejszany jest jego numer
                if (nrPytania > 0) {
                    nrPytania--;
                    break;
                } else {
                    break;
                }
            case R.id.opcja1:
                if (odp.quiz.pytania.get(nrPytania).odp1 == false)
                    odp.quiz.pytania.get(nrPytania).odp1 =true;
                else
                    odp.quiz.pytania.get(nrPytania).odp1 =false;
                break;
            case R.id.opcja2:
                if (odp.quiz.pytania.get(nrPytania).odp2 == false)
                    odp.quiz.pytania.get(nrPytania).odp2 =true;
                else
                    odp.quiz.pytania.get(nrPytania).odp2 =false;
                break;
            case R.id.opcja3:
                if (odp.quiz.pytania.get(nrPytania).odp3 == false)
                    odp.quiz.pytania.get(nrPytania).odp3 =true;
                else
                    odp.quiz.pytania.get(nrPytania).odp3 =false;
                break;
            case R.id.opcja4:
                if (odp.quiz.pytania.get(nrPytania).odp4 == false)
                    odp.quiz.pytania.get(nrPytania).odp4 =true;
                else
                    odp.quiz.pytania.get(nrPytania).odp4 =false;
                break;

            default:

                break;

        }

        //uaktualnienie wyświetlanych opcji i nr pytania(w razie zmiany nrPytania)
       buttOp1.setText(quiz.pytania.get(nrPytania).op1);
        buttOp2.setText(quiz.pytania.get(nrPytania).op2);
        buttOp3.setText(quiz.pytania.get(nrPytania).op3);
        buttOp4.setText(quiz.pytania.get(nrPytania).op4);
        tvNrPytania.setText("Pytanie: "+(nrPytania+1)+"/"+quiz.pytania.size() );
        //aktualizacja wyświetlanych kolorów
        if (odp.quiz.pytania.get(nrPytania).odp1 == false)
           buttOp1 .setBackgroundResource(R.drawable.kafelek);
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

        //uaktualnienie wyświetlanego pytania
        twPytanie.setText(quiz.pytania.get(nrPytania).pytanie);






        }
}
