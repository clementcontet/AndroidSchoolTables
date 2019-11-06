package com.example.tables;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComputeActivity extends AppCompatActivity {
    static final String EXTRA_IS_ADDITION = "EXTRA_IS_ADDITION";
    static final String EXTRA_DIGITS = "EXTRA_DIGITS";
    private int mOperationNumber;
    private int mNumberOfErrors = 0;
    private TextView mQuestion;
    private TextView mErrors;
    private TextView mRemaining;
    private ImageView mFace;
    private EditText mAnswer;
    private List<Integer[]> mOperations;
    private boolean mIsAddition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);

        mQuestion = findViewById(R.id.question);
        mAnswer = findViewById(R.id.answer);
        mErrors = findViewById(R.id.errors);
        mRemaining = findViewById(R.id.remaining);
        mFace = findViewById(R.id.face);

        findViewById(R.id.quit).setOnClickListener(v -> finish());

        final Intent receivedIntent = getIntent();
        mIsAddition = receivedIntent.getBooleanExtra(EXTRA_IS_ADDITION, true);
        boolean[] digits = receivedIntent.getBooleanArrayExtra(EXTRA_DIGITS);
        List<Integer> tables = new ArrayList<>();
        for (int index = 0; index < digits.length; index++) {
            if (digits[index]) {
                tables.add(index + 1);
            }
        }

        mOperations = new ArrayList<>(tables.size() * 10);
        for (Integer table : tables) {
            for (int digit = 1; digit <= 10; digit++) {
                mOperations.add(new Integer[]{table, digit});
            }
        }

        Collections.shuffle(mOperations);

        mOperationNumber = 0;
        mNumberOfErrors = 0;

        showErrors();
        newOperation();

        mAnswer.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                int result = mIsAddition ?
                        (mOperations.get(mOperationNumber)[0] + mOperations.get(mOperationNumber)[1])
                        : (mOperations.get(mOperationNumber)[0] * mOperations.get(mOperationNumber)[1]);
                int answer = 0;
                try {
                    answer = Integer.parseInt(mAnswer.getText().toString());
                } catch (Exception e) {
                    new AlertDialog.Builder(ComputeActivity.this)
                            .setTitle("Je n'ai pas compris !")
                            .setPositiveButton("Ok", (dialog, which) -> {
                                mAnswer.setText("");
                            })
                            .show();
                    return true;
                }

                if (answer == result) {
                    new AlertDialog.Builder(ComputeActivity.this)
                            .setTitle("Bravo !")
                            .setPositiveButton("Suivant", (dialog, which) -> {
                                mOperationNumber++;
                                newOperation();
                            })
                            .show();
                } else {
                    new AlertDialog.Builder(ComputeActivity.this)
                            .setTitle("Essaie encore !")
                            .setPositiveButton("Ok", (dialog, which) -> {
                                mNumberOfErrors++;
                                mAnswer.setText("");
                                showErrors();
                            })
                            .show();
                }
                return true;
            }
            return false;
        });
    }

    private void newOperation() {
        mRemaining.setText(mOperationNumber + "/" + mOperations.size());
        showErrors();
        if (mOperationNumber == mOperations.size()) {
            new AlertDialog.Builder(this)
                    .setTitle("C'est fini !")
                    .setMessage(mNumberOfErrors > 0 ? ("Tu as fait " + mNumberOfErrors + " " + (mNumberOfErrors <= 1 ? "erreur" : "erreurs") + " sur les " + mOperations.size() + " opérations.")
                            : "C'était parfait !!")
                    .setPositiveButton("Recommencer", (dialog, which) -> {
                        finish();
                    })
                    .show();
        } else {
            mQuestion.setText(String.format("%s%s%s ?",
                    mOperations.get(mOperationNumber)[0].toString(),
                    mIsAddition ? "+" : "x",
                    mOperations.get(mOperationNumber)[1].toString()));
            mAnswer.setText("");
        }
    }

    private void showErrors() {
        mErrors.setText(mNumberOfErrors + (mNumberOfErrors <= 1 ? " erreur" : " erreurs"));
        if (mNumberOfErrors == 0) {
            mFace.setImageResource(R.drawable.face6);
        } else if ((float) mNumberOfErrors / (mOperationNumber + 1) < 0.1) {
            mFace.setImageResource(R.drawable.face5);
        } else if ((float) mNumberOfErrors / (mOperationNumber + 1) < 0.2) {
            mFace.setImageResource(R.drawable.face4);
        } else if ((float) mNumberOfErrors / (mOperationNumber + 1) < 0.3) {
            mFace.setImageResource(R.drawable.face3);
        } else if ((float) mNumberOfErrors / (mOperationNumber + 1) < 0.4) {
            mFace.setImageResource(R.drawable.face2);
        } else {
            mFace.setImageResource(R.drawable.face1);
        }
    }
}
