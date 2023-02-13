package com.qterminals.util;

import com.qterminals.dto.Sequence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtil {
    public String convertAPINameToEntityName(String text) {
        String[] textAsArray = text.split("-");

        if(textAsArray.length > 2){
            return convertToTitleCaseIteratingChars(textAsArray[0]) + convertToTitleCaseIteratingChars(textAsArray[1]) + convertToTitleCaseIteratingChars(textAsArray[2]);
        } else if(textAsArray.length > 1){
            return convertToTitleCaseIteratingChars(textAsArray[0]) + convertToTitleCaseIteratingChars(textAsArray[1]);
        } else {
            return convertToTitleCaseIteratingChars(textAsArray[0]);
        }
    }

    public String convertToTitleCaseIteratingChars(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder converted = new StringBuilder();

        boolean convertNext = true;
        for (char ch : text.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                ch = Character.toLowerCase(ch);
            }
            converted.append(ch);
        }

        return converted.toString();
    }

    public String convertToCameCase(String text){
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder converted = new StringBuilder();
        boolean convert = true;
        for (char ch : text.toCharArray()) {
            if(convert){
                converted.append(Character.toLowerCase(ch));
                convert = false;
            } else {
                converted.append(ch);
            }
        }

        return converted.toString();
    }

    public String formatDate(String dateAsString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedDate = null;
        try {
            // 23 is the length of the date pattern
            if (dateAsString.length() > 23) {
                dateAsString = dateAsString.substring(0, 23);
            }
            Date outputdate = simpleDateFormat.parse(dateAsString);
            formattedDate = simpleDateFormat.format(outputdate);  //==output date is: 2016-03-16 01:14:21.673
            System.out.println(formattedDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return formattedDate;
    }

    public String formatSequence(Sequence sequence){
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");

        String formatSequenceId = sequence.getSequenceFormat();
        String numberFormat = ("%0"+ (sequence.getMaximumDigits() == null ? 1 : sequence.getMaximumDigits()) +"d");
        String replaceSequence = formatSequenceId.replaceFirst("(?i)(\\{SQ\\})", String.format(numberFormat, sequence.getSequenceCounter()));
        String replaceYear = replaceSequence.replaceFirst("(?i)(\\{YYYY\\})", yearFormat.format(new Date()));
        String replaceMonth = replaceYear.replaceFirst("(?i)(\\{MM\\})", monthFormat.format(new Date()).toUpperCase());
        return replaceMonth;
    }
}
