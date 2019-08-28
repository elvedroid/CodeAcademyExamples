package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String NAME_EXTRA = "NAME_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Го вчитуваме ЕдитТекстот. ЕдитТекстот мора да е final поради тоа што се користи во
//        анонимната класа од View.OnClickListener. Не мора да размислувате за тоа дали треба да
//        го поставите final или не затоа што веднаш кога ќе се искористи во анонимната класа
//        автоматски се поставува во final ако претходно не бил поставен.
        final EditText editText = findViewById(R.id.etName);

//        Го вчитуваме копчето.
        Button openSecondActivityButton = findViewById(R.id.btnOpenSecondActivity);

//        Поставуваме лисенер на копчето. Всушност, поставуваме имплементација на интерфејсот
//        View.OnClickListener. Значи Андроид во позадина сам го следи копчето, и кога корисникот
//        ќе го притисне копчето, преку View.OnClickListener интерфејсот го повикува методот
//        onClick(View view) каде што "view" е самото копче.
        openSecondActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Ова ќе се повика кога корисникот ќе го притисне копчето.

//                Го земаме текстот од editText и го претвораме во String. Со овојт текст можеме да
//                правиме што сакаме (поточно тука се прави бизнис логиката). Можеме да го менуваме,
//                да го кратеме, да додаваме друг текст, или пак да правиме проверки.
//                Пример: Можеме да провереме дали стрингот има должина барем од 4 карактери, и ако
//                ја има потребната должина да продолжеме понатаму, а ако не, можеме да му прикажеме
//                грешка на корисникот.
                String enteredName = editText.getText().toString();
//                Креираме Intent. Првиот аргумент е контекстот од каде што се наоѓаме, вториот
//                аргумент е класата од Активитито кое сакаме да го стартуваме.
//                Тука не можеме да користиме this, затоа што this се однесува на
//                анонимната класа од View.OnClickListener. Но, можеме да искористиме MainActivity.this,
//                и ова ќе се однесува на this од MainActivity(Ова можеме да го направиме бидејќи
//                анонимната класа се наоѓа во MainActivity класата.
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                Го ставаме претходно земениот текст во интентот
                intent.putExtra(NAME_EXTRA, enteredName);
//                Го стартуваме активитито со интентот
                startActivity(intent);
            }
        });

    }
}
