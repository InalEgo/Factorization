package com.android.inalego.factorization;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.inalego.factorization.Maths.Factorization;

import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;

public class MainActivityFragment extends Fragment {

    private EditText numberView;
    private TextView factorizationView;

    public MainActivityFragment() {
    }

    void factorization() {
        if (numberView.getText().length() == 0) {
            factorizationView.setText("");
            return;
        }
        BigInteger n = new BigInteger(numberView.getText().toString());
        TreeMap<BigInteger, Integer> result = new TreeMap<>();
        Factorization.factor(n, result);
        SpannableStringBuilder cs = new SpannableStringBuilder("");
        for (Map.Entry<BigInteger, Integer> entry : result.entrySet()) {
            cs.append(entry.getKey().toString());
            Spanned s = Html.fromHtml("<sup><small>" + entry.getValue() + "</small></sup>");
            cs.append(entry.getValue() != 1 ? s : "");
            cs.append(" · ");
        }
        cs.delete(cs.length() - 2, cs.length() - 1);
        factorizationView.setText(cs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        factorizationView = (TextView) rootView.findViewById(R.id.factorization);
        numberView = (EditText) rootView.findViewById(R.id.number);
        numberView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberView.getText().toString().equals("")) {
                    numberView.setText("109890109889010989011");
                    numberView.setSelection(numberView.getText().length());
                }
            }
        });
        numberView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    factorization();
                    handled = true;
                }
                return handled;
            }
        });
        numberView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (numberView.getText().length() <= 18) {
                    factorization();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        numberView.setFocusableInTouchMode(true);
        numberView.requestFocus();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_clear) {
            numberView.setText("");
            factorizationView.setText("");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
