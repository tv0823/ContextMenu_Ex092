package com.example.contextmenu_ex092;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnCreateContextMenuListener{

    Intent gi;
    ListView firstTwentyLv;
    TextView ansTv;
    double firstNum, numD, seqSum;
    int math;
    String[] seqArr;
    double[] sumValuesArr;
    int posN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        firstTwentyLv = findViewById(R.id.firstTwentyLv);
        ansTv = findViewById(R.id.ansTv);

        gi = getIntent();
        firstNum = gi.getDoubleExtra("firstNum",0);
        numD = gi.getDoubleExtra("numD",0);
        math = gi.getIntExtra("action", -1);

        seqSum = 0;
        seqArr = new String[20];
        sumValuesArr = new double[20];

        calcArrValues();

        firstTwentyLv.setOnItemClickListener(this);
        firstTwentyLv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, seqArr);
        firstTwentyLv.setAdapter(adp);
        firstTwentyLv.setOnCreateContextMenuListener(this);
    }

    public String bigNumSimplifier(double value){
        String scientificNotation = String.format("%.4e", value);
        String[] parts = scientificNotation.split("e");
        double base = Double.parseDouble(parts[0]) / 10.0;
        int exponent = Integer.parseInt(parts[1]) + 1;

        return String.format("%.4f * 10^%d", base, exponent);
    }

    public void calcArrValues() {
        for (int i = 0; i < 20; i++) {
            double currentValue;

            if (math == 1) {
                currentValue = firstNum + i * numD;
            } else {
                currentValue = firstNum * Math.pow(numD, i);
            }

            if ((currentValue > 1000000) || (currentValue < -1000000)) {
                seqArr[i] = bigNumSimplifier(currentValue);
            } else if(currentValue > -1 && currentValue < 1) {
                seqArr[i] = currentValue + "";
            } else{
                seqArr[i] = String.format("%.2f", currentValue);
            }

            seqSum += currentValue;
            sumValuesArr[i] = seqSum;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        posN = pos;
    }

    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Sequence options");
        menu.add("n");
        menu.add("Sn");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String action = item.getTitle().toString();
        if(action.equals("n"))
        {
            ansTv.setText("n: " + (posN+1));
            return true;
        } else if (action.equals("Sn")) {
            if ((sumValuesArr[posN] > 1000000) || (sumValuesArr[posN] < -1000000)) {
                ansTv.setText("Sn: " + bigNumSimplifier(sumValuesArr[posN]));
                return true;
            } else if(sumValuesArr[posN] > -1 && sumValuesArr[posN] < 1) {
                ansTv.setText("Sn: " + sumValuesArr[posN] + "");
                return true;
            } else{
                ansTv.setText("Sn: " + String.format("%.2f", sumValuesArr[posN]));
                return true;
            }
        }
        return onContextItemSelected(item);
    }

    public void goBack(View view) {
        setResult(RESULT_OK, gi);
        finish();
    }
}