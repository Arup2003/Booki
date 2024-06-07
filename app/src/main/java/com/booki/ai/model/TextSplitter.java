package com.booki.ai.model;

import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class TextSplitter {

    public static List<String> splitStringToFitTextView(TextView textView, String text) {
        // Create a Paint object with the same text size and typeface as the TextView
        Paint paint = new Paint();
        paint.setTextSize(textView.getTextSize());
        paint.setTypeface(textView.getTypeface());

        // Get the width of the TextView minus the padding
        int textViewWidth = textView.getWidth() - textView.getPaddingLeft() - textView.getPaddingRight();
        System.out.println("WIDTH :::: "+textViewWidth);

        // Split the text into words
        String[] words = text.split(" ");

        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            String testLine = currentLine.length() == 0 ? word : currentLine + " " + word;
            float testLineWidth = paint.measureText(testLine);

            if (testLineWidth <= textViewWidth) {
                currentLine = new StringBuilder(testLine);
            } else {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            }
        }

        // Add the last line
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }
        System.out.println("LINES : "+lines.size());
        return lines;
    }
}

