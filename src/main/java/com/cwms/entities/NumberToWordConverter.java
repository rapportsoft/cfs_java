package com.cwms.entities;

public class NumberToWordConverter {
    private static final String[] units = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
    private static final String[] teens = {"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private static final String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};

    public static String convert(int number) {
        if (number < 10) {
            return units[number];
        } else if (number < 20) {
            return teens[number - 10];
        } else if (number < 100) {
            return tens[number / 10] + (number % 10 != 0 ? " " + units[number % 10] : "");
        } else if (number < 1000) {
            return capitalize(units[number / 100]) + " Hundred" + (number % 100 != 0 ? " and " + convert(number % 100) : "");
        } else if (number < 100000) { // Up to 99,999 (Thousands)
            return capitalize(convert(number / 1000)) + " Thousand" + (number % 1000 != 0 ? " " + convert(number % 1000) : "");
        } else if (number < 10000000) { // Up to 9,999,999 (Lakhs)
            return capitalize(convert(number / 100000)) + " Lakh" + (number % 100000 != 0 ? " " + convert(number % 100000) : "");
        } else if (number < 1000000000) { // Up to 99,99,99,999 (Crores)
            return capitalize(convert(number / 10000000)) + " Crore" + (number % 10000000 != 0 ? " " + convert(number % 10000000) : "");
        } else {
            return "Number out of range";
        }
    }

    private static String capitalize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}

