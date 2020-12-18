package top.webra.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CountUtil {

    public static Map<String,Object> getCount(){
        Map<String, Object> countMap = new HashMap<>();
        ArrayList<String> operatorList = new ArrayList<>();
        operatorList.add("+");
        operatorList.add("-");
        operatorList.add("*");
//        operatorList.add("÷");

        int index = (int)(Math.random()* operatorList.size());
        String operator = operatorList.get(index);
        int number1 =(int) (Math.random()*9+1);
        int number2 =(int) (Math.random()*9+1);
        String formula = number1 + operator + number2;
        int number = 0;
        if (operator.equals("+")){
            number = number1 + number2;
        }
        if (operator.equals("-")){
            number = number1 - number2;
        }
        if (operator.equals("*")){
            number = number1 * number2;
        }
//        if (operator.equals("÷")){
//            number = number1 / number2;
//        }

        countMap.put("formula",formula);
        countMap.put("number",number);
        return countMap;
    }
}
