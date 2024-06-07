package com.booki.ai;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class IndividualPageActivity extends Activity {

    public static TextView individual_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_page);

    }

    public static void changeColor(int pageColor,int textColor)
    {

        individual_page.setBackgroundColor(pageColor);
        individual_page.setTextColor(textColor);

    }
}
