package com.miftah.calculatoruts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mNumberScreen1;
    private TextView mNumberScreen2;
    private TextView mOperator;

    private Double operand1;
    private Double operand2;
    private String currentOperator;
    private Boolean equalsButtonPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNumberScreen1 = (TextView) findViewById(R.id.numberScreen1);
        mNumberScreen2 = (TextView) findViewById(R.id.numberScreen2);
        mOperator = (TextView) findViewById(R.id.operator);
        reset();
    }

    public void doEquals(View view){
        operand1 = calculate(currentOperator, operand1, operand2);
        equalsButtonPressed = true;
        currentOperator = null;
        operand2 = null;
        updateDisplay();
    }

    public void doCE(View view){
        reset();
    }

    public void doC(View view){
        operand2 = null;
        updateDisplay();
    }

    public void responsePressedOperator(View view){
        Button operatorButton = (Button)view;
        String pressedOperator = operatorButton.getText().toString();

        if(currentOperator != null && operand2 != null){
            operand1 = calculate(currentOperator, operand1, operand2);
            operand2 = null;
        }

        if(!pressedOperator.equalsIgnoreCase("LOG")){
            currentOperator = pressedOperator;
        }
        else {
            currentOperator = null;
            operand1 = Math.log(operand1);
        }
        updateDisplay();
    }

    public void responsePressedNumber(View view){
        Button numberButton = (Button)view;
        if(currentOperator == null){
            if(operand1 == 0.00 || equalsButtonPressed)
                mNumberScreen1.setText("");

            equalsButtonPressed = false;
            mNumberScreen1.append(numberButton.getText().toString());
            operand1 = Double.valueOf(mNumberScreen1.getText().toString());
        }
        else{
            mNumberScreen2.append(numberButton.getText().toString());
            operand2 = Double.valueOf(mNumberScreen2.getText().toString());
        }
    }

    private void reset(){
        operand1 = 0.00;
        operand2 = null;
        currentOperator = null;

        updateDisplay();
    }

    private Double calculate(String operator, Double operand1, Double operand2){
        if(operator == null)
            return operand1;

        if(operator.equals("+")){
            return operand1 + operand2;
        }
        else if(operator.equals("x")){
            return operand1 * operand2;
        }
        else if(operator.equals("/")){
            return operand1 / operand2;
        }
        else if(operator.equals("-")){
            return operand1 - operand2;
        }
        else{
            return operand1 % operand2;
        }
    }

    private void updateDisplay(){
        mNumberScreen1.setText(operand1 == null ? null : operand1.toString());
        mNumberScreen2.setText(operand2 == null ? null : operand2.toString());
        mOperator.setText(currentOperator);
    }
}
