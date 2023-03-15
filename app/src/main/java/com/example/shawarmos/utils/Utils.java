package com.example.shawarmos.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class Utils {

    public static boolean validateEditTextFields(List<EditText> fields, Context context) {
        for (EditText field : fields) {
            if (TextUtils.isEmpty(field.getText())) {
                Toast.makeText(context, "Please fill " + field.getHint(),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }
}
