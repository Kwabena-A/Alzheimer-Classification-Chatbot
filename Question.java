import java.util.*;

public class Question {
    String valueType;
    String value;

    int truthQuestion;
    int falseQuestion;

    public Question(String valueType, String value, String truthQuestion, String falseQuestion) {
        this.valueType = valueType;
        this.value = value;

        if (truthQuestion.contains("!")){
            this.truthQuestion = ((int) truthQuestion.charAt(1) - 48 + 1) * -1;
        }
        else {
            this.truthQuestion = Integer.parseInt(truthQuestion);
        }

        if (falseQuestion.contains("!")){
            this.falseQuestion = ((int) falseQuestion.charAt(1) - 48 + 1) * -1;
        }
        else {
            this.falseQuestion = Integer.parseInt(falseQuestion);
        }
    }

    public int predict(HashMap<String, String> features){
//        System.out.println(valueType + " should be >=" + value);
//        System.out.println(features.get(valueType));
        if (Double.parseDouble(features.get(valueType)) >= Double.parseDouble(value)){
//            System.out.println("TRUE! Up Next: Q#" + truthQuestion);
//            System.out.println();
            return truthQuestion;
        }
//        System.out.println("FALSE! Up Next: Q#" + falseQuestion);
//        System.out.println();
        return falseQuestion;
    }

}

