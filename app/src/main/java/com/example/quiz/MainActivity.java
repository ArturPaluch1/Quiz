package com.example.quiz;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ListView list;
    //lista dostępnych quizów
      List<String> quizy = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        list = findViewById(R.id.listaQuiz);

        Button btnWyjdz = findViewById(R.id.btnWyjscie);
        btnWyjdz.setOnClickListener(this);

/********************
łączenie  z xml i wybieranie do listy
****************/
pobierzDane();

//podpięcie adaptera do listview
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.listview_main, quizy);
        list.setAdapter(adapter);
//ostawienie Listenera do sprawdzania czy element listy został naciśnięty
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                                            //Pzekazanie w obiekcie klasy Intent numer naciśniętego quizu
                                            //aktywacja następnego Activity i zamknięcie obecnego
                                            Intent i = new Intent(findViewById(R.id.main).getContext(), quiz.class);
                                            i.putExtra("nrQuizu", quizy.indexOf(quizy.get(position)));
                                            startActivity(i);
                                            finish();


                                        }
                                    }

        );
    }



    private void pobierzDane() {
        try {

            InputStream is1 = getAssets().open("dane.xml");

            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();

            myParser.setInput(is1, null);
            int event ;

            event = myParser.getEventType();
            //przeszukiwanie całego dokumentu xml
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();

                switch (event) {

                    case XmlPullParser.START_TAG:
                        //Jeżeli wczytany tag to Quiz dodawana jest nazwa quizu do listy
                        if (name.equals("Quiz")) {
                            String b = myParser.getAttributeValue(null, "nazwa");
                            quizy.add(b);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
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
        super.finish();
    }
}
