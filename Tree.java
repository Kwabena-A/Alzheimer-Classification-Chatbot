import java.util.ArrayList;
import java.util.HashMap;

public class Tree {
    String tree;

    HashMap<String, String> features;
    ArrayList<Question> fullTree = new ArrayList<>();

    public Tree(HashMap<String, String> features, String tree){
        this.tree= tree;
        this.features = features;
        splitTrees();
//        System.out.println(fullTree);
    }

    void splitTrees(){
        String[] semiSplitTree = tree.split("]");
        for (String question : semiSplitTree){
            if (question.contains(":") && question.contains(">=")){
                String valueType = question.substring(question.indexOf(':') + 2, question.indexOf(">=") - 1);
                String value = question.substring(question.indexOf(">=") + 3, question.indexOf("---->") - 1);
                String truthQuestion = question.substring(question.indexOf('#', 20) + 2, question.indexOf("or") - 1);
                String falseQuestion = question.substring(question.indexOf('#', 50) + 2, question.length() - 1);
//                System.out.println(valueType + " " + value + " " + truthQuestion + " " + falseQuestion);
                fullTree.add(new Question(valueType, value, truthQuestion, falseQuestion));
            }
        }
    }

    public boolean alzPredict(int questionIndex){

        if (questionIndex == -1) {
            return true;
        }
        if (questionIndex == -2){
            return false;
        }

        Question question = fullTree.get(questionIndex);

        int nextIndex = question.predict(this.features);
        return alzPredict(nextIndex);

    }

}
