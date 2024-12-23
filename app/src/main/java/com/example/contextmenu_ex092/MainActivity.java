package com.example.contextmenu_ex092;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button geometricalBtn, mathematicalBtnv, ResultBtn;
    EditText FirstNumberEt, numDEt;
    int REQUEST_CODE = 5789;
    int math;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        geometricalBtn = findViewById(R.id.geometricalBtn);
        mathematicalBtnv = findViewById(R.id.mathematicalBtn);
        ResultBtn = findViewById(R.id.ResultBtn);

        FirstNumberEt = findViewById(R.id.FirstNumberEt);
        numDEt = findViewById(R.id.numDEt);

    }

    public void resetItems()
    {
        FirstNumberEt.setText("");
        numDEt.setText("");

        FirstNumberEt.setVisibility(View.VISIBLE);
        numDEt.setVisibility(View.VISIBLE);
        ResultBtn.setVisibility(View.VISIBLE);
    }

    public void mathematical(View view) {
        math = 1;
        numDEt.setHint("Enter difference");
        resetItems();
    }

    public void geometrical(View view) {
        math = 0;
        numDEt.setHint("Enter multiplier");
        resetItems();
    }

    public void Result(View view) {
        String first = FirstNumberEt.getText().toString();
        String strNumD = numDEt.getText().toString();

        if(first.isEmpty() || strNumD.isEmpty()) {
            Toast.makeText(this, "One or more input is empty", Toast.LENGTH_SHORT).show();
        } else if ((first.equals("+.") || first.equals("+") || first.equals("-.") || first.equals("-") || first.equals(".")) || (strNumD.equals("+.") || strNumD.equals("+") || strNumD.equals("-.") || strNumD.equals("-") || strNumD.equals("."))) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        } else if((math == 0) && (first.equals("0"))){
            Toast.makeText(this, "Geometrical seq can't start with 0", Toast.LENGTH_SHORT).show();
        } else if(strNumD.equals("0")){
            Toast.makeText(this, "multiplier and difference can't be 0", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent si = new Intent(this,ResultsActivity.class);
            double firstNum = Float.parseFloat(first);
            double numD = Float.parseFloat(strNumD);

            si.putExtra("numD", numD);
            si.putExtra("firstNum", firstNum);
            si.putExtra("action", math);

            startActivityForResult(si, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int source, int result, @Nullable Intent data_back){
        super.onActivityResult(source, result, data_back);
        if(data_back != null) {
            resetItems();

            FirstNumberEt.setVisibility(View.INVISIBLE);
            numDEt.setVisibility(View.INVISIBLE);
            ResultBtn.setVisibility(View.INVISIBLE);
        }
    }
}