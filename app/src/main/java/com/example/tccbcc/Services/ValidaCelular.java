package com.example.tccbcc.Services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidaCelular {
    public static boolean ValidarCelular(String celular){
        boolean isValidCelular = false;
        if (celular != null && celular.length() > 0) {
            String expression = "^\\([1-9]{2}\\)(?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(celular);
            if (matcher.matches()) {
                isValidCelular = true;
            }
        }
        return isValidCelular;
    }
}
