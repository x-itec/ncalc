/*
 * Copyright 2017 Tran Le Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.duy.calculator.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.example.duy.calculator.R;
import com.example.duy.calculator.item_math_type.LimitItem;
import com.example.duy.calculator.math_eval.FormatExpression;
import com.example.duy.calculator.math_eval.Tokenizer;
import com.example.duy.calculator.utils.ConfigApp;
import com.example.duy.calculator.activities.abstract_class.AbstractEvaluatorActivity;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

/**
 * Created by DUy on 07-Dec-16.
 */

public class LimitActivity extends AbstractEvaluatorActivity {
    private static final String STARTED = LimitActivity.class.getName() + "started";
    private boolean isDataNull = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.limit));
        mHint1.setHint(getString(R.string.enter_function));
        btnSolve.setText(R.string.eval);

        mLayoutLimit.setVisibility(View.VISIBLE);
        mHint1.setHint("");
        editFrom.post(new Runnable() {
            @Override
            public void run() {
                editFrom.setText("x → ");
                editFrom.setEnabled(false);
                editFrom.setHint(null);
                editFrom.setGravity(Gravity.END);
            }
        });

        getIntentData();
        boolean isStarted = mPreferences.getBoolean(STARTED, false);
        if ((!isStarted) || ConfigApp.DEBUG) {
            if (isDataNull) {
                mInputFormula.setText("1/x + 2");
                editTo.setText("inf");
            }
            showHelp();
        }

    }

    /**
     * get data from activity start it
     */
    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(BasicCalculatorActivity.DATA);
        if (bundle != null) {
            String data = bundle.getString(BasicCalculatorActivity.DATA);
            if (data != null) {
                mInputFormula.setText(data);
                data = new Tokenizer(this).getNormalExpression(data);
                data = getExpression(data);
                isDataNull = false;
//                onResult(data, false);
            }
        }
    }

    private String getExpression(String inp) {
        inp = FormatExpression.cleanExpression(inp, this);
        String params = "x";
        String limit = editTo.getText().toString();
        String expression = "Limit(" + inp + "," + params + "-> " + limit + ")";
        expression = expression.toLowerCase();
        Log.d(TAG, "getExpression: " + expression);
        return expression;
    }

    @Override
    public int getIdStringHelp() {
        return R.string.help_expression;
    }

    @Override
    public void showHelp() {
        final SharedPreferences.Editor editor = mPreferences.edit();
        TapTarget target0 = TapTarget.forView(mInputFormula,
                getString(R.string.enter_function));
        target0.drawShadow(true)
                .cancelable(true)
                .targetCircleColor(R.color.colorAccent)
                .transparentTarget(true)
                .outerCircleColor(R.color.colorPrimary)
                .dimColor(R.color.colorPrimaryDark).targetRadius(70);

        TapTarget target1 = TapTarget.forView(editTo,
                getString(R.string.input_limit));
        target1.drawShadow(true)
                .cancelable(true)
                .targetCircleColor(R.color.colorAccent)
                .transparentTarget(true)
                .outerCircleColor(R.color.colorPrimary)
                .dimColor(R.color.colorPrimaryDark).targetRadius(70);

        TapTarget target2 = TapTarget.forView(btnSolve,
                getString(R.string.eval));
        target2.drawShadow(true)
                .cancelable(true)
                .targetCircleColor(R.color.colorAccent)
                .transparentTarget(true)
                .outerCircleColor(R.color.colorPrimary)
                .dimColor(R.color.colorPrimaryDark).targetRadius(70);

        TapTargetSequence sequence = new TapTargetSequence(LimitActivity.this);
        sequence.targets(target0, target1, target2);
        sequence.listener(new TapTargetSequence.Listener() {
            @Override
            public void onSequenceFinish() {
                editor.putBoolean(STARTED, true);
                editor.apply();
                doEval();
            }

            @Override
            public void onSequenceCanceled(TapTarget lastTarget) {
                editor.putBoolean(STARTED, true);
                editor.apply();
                doEval();
            }
        });
        sequence.start();
    }

    @Override
    public void doEval() {
        String inp = mInputFormula.getCleanText();

        if (inp.isEmpty()) {
//            showDialog(getString(R.string.input_expression));
            mInputFormula.requestFocus();
            mInputFormula.setError(getString(R.string.enter_function));
            return;
        }

        String to = editTo.getText().toString();
        if (to.isEmpty()) {
//            showDialog(getString(R.string.enter_to));
            editTo.requestFocus();
            editTo.setError(getString(R.string.error));
            return;
        }
        LimitItem item = new LimitItem(mInputFormula.getCleanText(),
                "", editTo.getText().toString());
        new ATaskEval().execute(item);
    }


}
