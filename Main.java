/*----------------------------------ChatBot----------------------------------/

// Name: Kwabena Aboagye

// Student ID: 14817

// Grade and Section: 11 C

// Personal Contribution to Code: 100%

// Core Topic for the Chatbot: Alzheimer Prediction
/*-----------------------------------------------------------------------------*/

import java.util.*;
import java.io.*;

public class Main {
    static Scanner in = new Scanner(System.in);
    static ArrayList<String> verbList = new ArrayList<>();
    static ArrayList<String> nounList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException{

        boolean active = true;
        int score = 0;
        initLists();
        clear();

        // Test Predicts: System.out.println(alziheimersPredict("12", "0", "12", "4", "25"));

        botSpeach("Hi! Im here to predict whether you have Alzheimer's Disease.");
        System.out.println();
        while(active){
            botSpeach("Lets start with your MMSE Exam. Would you like to begin?");
            String response = inputSpeach();
            if ((response.toLowerCase().contains("y") || response.toLowerCase().contains("sure")) && !response.contains("no")){
                botSpeach("Lets begin!");
                think();

                score += whatTimeIsIt(); // 5
                score += whereAreWe(); // 5
                score += rememberStageOne(); // 3
                score += countBackWords();// 5
                score += rememberStageTwo(); // 3
                score += formASentence(); // 9

                // out of 30
                botSpeach("Well Done, Your Done With Your MMSE Exam!");
                botSpeach("You Scored...");
                botSpeach(score + " out of 30");

                System.out.println();
                botSpeach("Now lets work on getting your prediction");
                think(4);

                String age = getAge(true) + "";
                String gender = getGender(true) + "";
                String SES = getSES(true) + "";
                String EDUC = getEDUC(true) + "";

                if (confirmation(age, gender, EDUC, SES, score + "")){
                    think(3);
                    botSpeach(alziheimersPredict(age, gender, EDUC, SES, score + ""));
                    botSpeach("Remember, im not a doctor. pleased get diagnosed by a licensed professional");
                    botSpeach("Thanks for trying my predictions!");
                    break;
                }
                else {
                    System.out.println();
                    botSpeach("Sorry, lets try this again.");
                    think();
                }

            }

            else {
                break;
            }

        }
    }

    // Places words in nounlist.txt and verblist.txt into their respective lists
    static void initLists(){
        try (Scanner fileRead = new Scanner(new File("nounlist.txt"))){
            while (fileRead.hasNext()){
                nounList.add(fileRead.nextLine());
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        try (Scanner fileRead = new Scanner(new File("verblist.txt"))){
            while (fileRead.hasNext()){
                verbList.add(fileRead.nextLine());
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    // Prints what the bot says or responds with
    static void botSpeach(String str) throws InterruptedException{
        System.out.print("Bot: ");
        char[] charS = str.toCharArray();
        for (char letter : charS){
            System.out.print(letter);
            Thread.sleep((int) (Math.random() * 50 + 10));
        }
        System.out.println();

    }

    // Ask for user input
    static String inputSpeach(){
        System.out.print("You: ");
        String response = in.nextLine();
        return response;
    }

    // Clears console
    static void clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();

    }

    // Mimic the bot thinking (...)
    static void think() throws InterruptedException{
        String dotdotdot = ".";
        Thread.sleep(1000);
        for (int i = 0; i < 5; i++){
            clear();
            botSpeach(dotdotdot);
            Thread.sleep(500);
            dotdotdot += ".";
            if (dotdotdot.length() > 3){
                dotdotdot = ".";
            }
        }
        clear();
    }

    // think() but with custom first delay
    static void think(int thinkDelay) throws InterruptedException{
        String dotdotdot = ".";
        Thread.sleep(thinkDelay * 1000);
        for (int i = 0; i < 5; i++){
            clear();
            botSpeach(dotdotdot);
            Thread.sleep(500);
            dotdotdot += ".";
            if (dotdotdot.length() > 3){
                dotdotdot = ".";
            }
        }
        clear();
    }

    // MMSE: What is the current ____?
    static int whatTimeIsIt() throws InterruptedException{
        HashMap<String, String> QnA = new HashMap<>();
        QnA.put("year", "2025");
        QnA.put("month", "September");
        QnA.put("season", "Autumn");
        QnA.put("day of the week", "Saturday");

        int choice = (int) (Math.random() * QnA.keySet().toArray().length);
        String request = QnA.keySet().toArray(new String[QnA.keySet().size()])[choice];

        botSpeach("What is the current " + request);
        String response = inputSpeach();

        if (response.toLowerCase().contains(QnA.get(request).toLowerCase())){
            botSpeach("That's correct. It is " + QnA.get(request));
            think(2);
            return 5;
        }
        else {
            botSpeach("Well no, it is actually " + QnA.get(request) );
            think(3);
            return 0;
        }
    }

    // MMSE: Where are we right now?
    static int whereAreWe() throws InterruptedException {
        botSpeach("Where are we right now?");
        String response = inputSpeach();

        ArrayList<String> valid = new ArrayList<>(Arrays.asList("uae", "u.a.e", "abu dhabi", "school", "hospital"));

        if (valid.contains(response.toLowerCase())){
            botSpeach("That's Right");
            think();
            return 5;
        }
        else {
            botSpeach("Well, not exactly.");
            think();
            return 0;
        }


    }

    // MMSE: Count backwards from __, skipping __, 5 times.
    static int countBackWords() throws InterruptedException {
        int from = (int) (Math.random() * 50) + 50;
        int skip = (int) (Math.random() * 4) + 1;
        botSpeach("Count backwards from " + from + ", skipping " + skip + ", 5 times");

        String correctResponse = "";

        for (int i = 0; i < 5; i++){
            correctResponse += from + " ";
            from -= skip;
        }

        String response = inputSpeach();
        response += " ";

        if (response.equalsIgnoreCase(correctResponse)){
            botSpeach("Correct.");
            think(2);
            return 5;
        }

        botSpeach("No, its actually its " + correctResponse);
        think(5);
        return 0;
    }

    // MMSE: Repeat the following words?
    static String rememberWords = "";
    static int rememberStageOne() throws InterruptedException{
        rememberWords = "";
        for (int i = 0; i < 3; i++){
            String word = nounList.get((int) (Math.random() * nounList.size())) + " ";
            if (word.length() < 7){
                rememberWords += word;
            }
            else {
                i--;
            }
        }

        botSpeach("Repeat These Words: " + rememberWords);
        String response = inputSpeach();
        while (!(response + " ").equalsIgnoreCase(rememberWords)){
            response = inputSpeach();
            botSpeach("No, try again.");
        }
        botSpeach("Alright.");
        think();
        return 3;
    }

    // MMSE: What where those words I told you earlier?
    static int rememberStageTwo() throws InterruptedException{
        botSpeach("Remember those words from earlier? repeat them.");

        String response = inputSpeach();
        if ((response + " ").equalsIgnoreCase(rememberWords)){
            botSpeach("Good");
            think();
            return 3;
        }

        botSpeach("Close, but it was actually " + rememberWords);
        think(4);
        return 0;
    }

    // MMSE: Make up a sentence.
    static int formASentence() throws InterruptedException{
        botSpeach("Write a sentence");
        String[] response = inputSpeach().split(" ");

        boolean[] properSentence = {false, false};

        for (String word : response){
            String testNoun = (word.charAt(word.length() - 1) == 's') ? word.substring(0, word.length() - 1) : word;
            if (nounList.contains(testNoun.toLowerCase()) && !properSentence[0]){
                properSentence[0] = true;
                botSpeach("Good Noun Usage...");
            }
            String testVerb = (word.charAt(word.length() - 1) == 's') ? word.substring(0, word.length() - 1) : word;
            testVerb = (word.endsWith("ing")) ? word.substring(0, word.length() - 3) : testVerb;
            testVerb = (word.endsWith("ed")) ? word.substring(0, word.length() - 2) : testVerb;

            if (verbList.contains(testVerb.toLowerCase()) && !properSentence[1]){
                properSentence[1] = true;
                botSpeach("Good Verb Usage...");
            }
        }

        if (!properSentence[0] && !properSentence[1]){
            botSpeach("Oops, you missed a verb and noun");
            think();
            return 0;
        }

        if (properSentence[0] && !properSentence[1]){
            botSpeach("Oops, you missed a verb");
            think();
            return 2;
        }
        else if (properSentence[1] && !properSentence[0]){
            botSpeach("Oops, you missed a noun");
            think();
            return 2;
        }

        botSpeach("Good Sentence!");
        think();
        return 9;

    }

    static int getAge(boolean think) throws InterruptedException{
        botSpeach("So how old are you?");
        int response;
        try {
            response = Integer.parseInt(inputSpeach());
        }
        catch (NumberFormatException e){
            botSpeach("Sorry, i didnt get that.");
            botSpeach("Try responding with just an integer");
            System.out.println();

            response = getAge(false);
        }

        if (think){think();}
        return response;
    }

    static int getGender(boolean think) throws InterruptedException{
        botSpeach("Are you a Male or Female?");
        String response = inputSpeach();

        if (response.toLowerCase().contains("male")){
            think();
            return 0;
        }

        if (response.toLowerCase().contains("female")){
            think();
            return 1;
        }

        else {
            botSpeach("Sorry i didnt get that.");
            if (think){think();}
            return getGender(false);
        }

    }

    static int getSES(boolean think) throws InterruptedException{
        botSpeach("What is your Social Economic Status? ( 1 (Lower Class) to 5 (Upper Class) )");
        int response;
        try {
            response = Integer.parseInt(inputSpeach());
        }
        catch (NumberFormatException e){
            botSpeach("Sorry, i didnt get that.");
            botSpeach("Try responding with just an integer");
            System.out.println();
            response = getSES(false);
        }

        switch (response){
            case 1 -> {botSpeach("Lower Class: Low income, minimal education (often not completing high school), low-skill or unemployed.");}
            case 2 -> {botSpeach("Lower-Middle Class: Modest income, some high school education, basic jobs or skilled labor.");}
            case 3 -> {botSpeach("Middle Class: Average income, completed high school or college, office or skilled trade jobs.");}
            case 4 -> {botSpeach("Middle-Upper Class: High income, university-educated, professional careers (engineers, doctors, etc.).");}
            case 5 -> {botSpeach("Upper Class: Very high income, advanced education, executive or high-status positions.");}
            default -> {
                botSpeach("Sorry i didnt get that");
                if (think){think();}
                return getSES(false);
            }
        }

        think();
        return response;
    }

    static int getEDUC(boolean think) throws InterruptedException{
        botSpeach("How many years of education have you had?");
        int response;
        try {
            response = Integer.parseInt(inputSpeach());
        }
        catch (NumberFormatException e){
            botSpeach("Sorry, i didnt get that.");
            botSpeach("Try responding with just an integer");
            System.out.println();;
            response = getEDUC(false);
        }
        if (think){think();}
        return response;
    }

    // Confirm Previous get methods.
    static boolean confirmation(String age, String gender, String EDUC, String SES, String MMSE) throws InterruptedException{
        botSpeach("Lets get every thing straight.");
        String printGender = (gender.equalsIgnoreCase("0")) ? "male" : "female";
        botSpeach("You're a " + age + " year old " + printGender + "...");
        botSpeach("You have " + EDUC + " years of education...");
        botSpeach("A SES of " + SES + "...");
        botSpeach("And a scored " + MMSE + "/30 on my MMSE Exam?");

        System.out.println();
        String response = inputSpeach();
        if (response.contains("y") && !response.contains("no")){
            botSpeach("Great ill start calculating your features now.");
            think();
            return true;
        }
        else {
            botSpeach("Sorry, lets try this again.");
            think();
            return false;
        }

    }

    static String[] getStringTree(){
        // This is the output of a .toString() method of a Random Forest model developed in python
        // Its formated into an ArrayList of questions via the Tree and Questions classes
        String[] strForest = {
                """
Question #0 : Age >= 66 ----> Question # 1 or Question # !0 ]
Question #1 : Age >= 80 ----> Question # 2 or Question # 8 ]
Question #2 : Educ >= 3.0 ----> Question # 3 or Question # 5 ]
Question #3 : Age >= 92 ----> Question # !1 or Question # 4 ]
Question #4 : Age >= 84 ----> Question # !0 or Question # !0 ]
Question #5 : Age >= 89 ----> Question # 6 or Question # 7 ]
Question #6 : Age >= 90 ----> Question # !0 or Question # !0 ]
Question #7 : SES >= 5.0 ----> Question # !0 or Question # !1 ]
Question #8 : Educ >= 5.0 ----> Question # 9 or Question # 11 ]
Question #9 : Age >= 73 ----> Question # 10 or Question # !0 ]
Question #10 : Age >= 75 ----> Question # !0 or Question # !1 ]
Question #11 : Age >= 75 ----> Question # 12 or Question # 13 ]
Question #12 : SES >= 2.0 ----> Question # !1 or Question # !1 ]
Question #13 : Age >= 74 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : Age >= 66 ----> Question # 1 or Question # !0 ]
Question #1 : Age >= 72 ----> Question # 2 or Question # 8 ]
Question #2 : Age >= 90 ----> Question # 3 or Question # 5 ]
Question #3 : M/F >= 1 ----> Question # 4 or Question # !1 ]
Question #4 : Age >= 96 ----> Question # !1 or Question # !0 ]
Question #5 : Age >= 78 ----> Question # 6 or Question # 7 ]
Question #6 : M/F >= 1 ----> Question # !1 or Question # !1 ]
Question #7 : Age >= 73 ----> Question # !0 or Question # !1 ]
Question #8 : Age >= 67 ----> Question # 9 or Question # !1 ]
Question #9 : Age >= 71 ----> Question # 10 or Question # 11 ]
Question #10 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #11 : Age >= 70 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : Age >= 69 ----> Question # 1 or Question # 13 ]
Question #1 : Age >= 85 ----> Question # 2 or Question # 7 ]
Question #2 : SES >= 4.0 ----> Question # 3 or Question # 5 ]
Question #3 : Age >= 90 ----> Question # !1 or Question # 4 ]
Question #4 : Age >= 89 ----> Question # !0 or Question # !1 ]
Question #5 : Age >= 96 ----> Question # !1 or Question # 6 ]
Question #6 : Age >= 92 ----> Question # !0 or Question # !0 ]
Question #7 : Age >= 82 ----> Question # 8 or Question # 10 ]
Question #8 : Age >= 84 ----> Question # 9 or Question # !1 ]
Question #9 : SES >= 4.0 ----> Question # !0 or Question # !1 ]
Question #10 : SES >= 3.0 ----> Question # 11 or Question # 12 ]
Question #11 : Age >= 80 ----> Question # !0 or Question # !1 ]
Question #12 : SES >= 2.0 ----> Question # !0 or Question # !1 ]
Question #13 : Age >= 66 ----> Question # 14 or Question # !0 ]
Question #14 : Age >= 67 ----> Question # !0 or Question # 15 ]
Question #15 : SES >= 4.0 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 8 ]
Question #1 : Age >= 66 ----> Question # 2 or Question # !0 ]
Question #2 : Age >= 69 ----> Question # 3 or Question # 6 ]
Question #3 : MMSE >= 29.0 ----> Question # 4 or Question # 5 ]
Question #4 : Educ >= 5.0 ----> Question # !0 or Question # !0 ]
Question #5 : Educ >= 5.0 ----> Question # !1 or Question # !0 ]
Question #6 : Educ >= 5.0 ----> Question # !0 or Question # 7 ]
Question #7 : Age >= 67 ----> Question # !0 or Question # !1 ]
Question #8 : MMSE >= 25.0 ----> Question # 9 or Question # !1 ]
Question #9 : Age >= 70 ----> Question # 10 or Question # !0 ]
Question #10 : Age >= 85 ----> Question # 11 or Question # 12 ]
Question #11 : Age >= 96 ----> Question # !1 or Question # !0 ]
Question #12 : Educ >= 2.0 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : Age >= 66 ----> Question # 1 or Question # 11 ]
Question #1 : Age >= 84 ----> Question # 2 or Question # 5 ]
Question #2 : Educ >= 2.0 ----> Question # 3 or Question # !1 ]
Question #3 : Age >= 96 ----> Question # !1 or Question # 4 ]
Question #4 : Age >= 92 ----> Question # !0 or Question # !0 ]
Question #5 : Age >= 83 ----> Question # 6 or Question # 8 ]
Question #6 : Educ >= 2.0 ----> Question # 7 or Question # !1 ]
Question #7 : Educ >= 3.0 ----> Question # !1 or Question # !0 ]
Question #8 : Age >= 80 ----> Question # 9 or Question # 10 ]
Question #9 : Age >= 81 ----> Question # !0 or Question # !0 ]
Question #10 : Age >= 79 ----> Question # !1 or Question # !1 ]
Question #11 : Age >= 64 ----> Question # 12 or Question # !0 ]
Question #12 : Educ >= 5.0 ----> Question # 13 or Question # !0 ]
Question #13 : Age >= 65 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : Age >= 64 ----> Question # 1 or Question # !0 ]
Question #1 : Age >= 87 ----> Question # 2 or Question # 7 ]
Question #2 : Educ >= 2.0 ----> Question # 3 or Question # 6 ]
Question #3 : Age >= 92 ----> Question # 4 or Question # 5 ]
Question #4 : Age >= 93 ----> Question # !0 or Question # !1 ]
Question #5 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]
Question #6 : SES >= 4.0 ----> Question # !1 or Question # !0 ]
Question #7 : Age >= 72 ----> Question # 8 or Question # 11 ]
Question #8 : Age >= 78 ----> Question # 9 or Question # 10 ]
Question #9 : Age >= 84 ----> Question # !1 or Question # !0 ]
Question #10 : Educ >= 2.0 ----> Question # !1 or Question # !0 ]
Question #11 : SES >= 3.0 ----> Question # 12 or Question # 13 ]
Question #12 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]
Question #13 : Age >= 70 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 9 ]
Question #1 : Educ >= 2.0 ----> Question # 2 or Question # 8 ]
Question #2 : MMSE >= 30.0 ----> Question # 3 or Question # 5 ]
Question #3 : Educ >= 5.0 ----> Question # !0 or Question # 4 ]
Question #4 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #5 : Educ >= 5.0 ----> Question # 6 or Question # 7 ]
Question #6 : MMSE >= 29.0 ----> Question # !0 or Question # !1 ]
Question #7 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #8 : MMSE >= 29.0 ----> Question # !1 or Question # !0 ]
Question #9 : MMSE >= 25.0 ----> Question # 10 or Question # !1 ]
Question #10 : Educ >= 5.0 ----> Question # !1 or Question # 11 ]
Question #11 : MMSE >= 27.0 ----> Question # 12 or Question # 13 ]
Question #12 : Educ >= 2.0 ----> Question # !1 or Question # !1 ]
Question #13 : Educ >= 4.0 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 11 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 7 ]
Question #2 : Educ >= 2.0 ----> Question # 3 or Question # 6 ]
Question #3 : MMSE >= 30.0 ----> Question # 4 or Question # 5 ]
Question #4 : Educ >= 5.0 ----> Question # !0 or Question # !0 ]
Question #5 : Educ >= 5.0 ----> Question # !0 or Question # !0 ]
Question #6 : MMSE >= 30.0 ----> Question # !0 or Question # !1 ]
Question #7 : Educ >= 4.0 ----> Question # 8 or Question # 9 ]
Question #8 : Educ >= 5.0 ----> Question # !1 or Question # !1 ]
Question #9 : Educ >= 3.0 ----> Question # !0 or Question # 10 ]
Question #10 : Educ >= 2.0 ----> Question # !0 or Question # !0 ]
Question #11 : MMSE >= 25.0 ----> Question # 12 or Question # !1 ]
Question #12 : Educ >= 5.0 ----> Question # 13 or Question # 14 ]
Question #13 : MMSE >= 27.0 ----> Question # !0 or Question # !1 ]
Question #14 : MMSE >= 27.0 ----> Question # 15 or Question # 16 ]
Question #15 : Educ >= 4.0 ----> Question # !1 or Question # !0 ]
Question #16 : Educ >= 4.0 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 13 ]
Question #1 : MMSE >= 30.0 ----> Question # 2 or Question # 6 ]
Question #2 : SES >= 4.0 ----> Question # 3 or Question # 4 ]
Question #3 : Educ >= 2.0 ----> Question # !0 or Question # !0 ]
Question #4 : Educ >= 5.0 ----> Question # !0 or Question # 5 ]
Question #5 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #6 : Educ >= 5.0 ----> Question # 7 or Question # 10 ]
Question #7 : SES >= 2.0 ----> Question # 8 or Question # 9 ]
Question #8 : MMSE >= 29.0 ----> Question # !0 or Question # !1 ]
Question #9 : MMSE >= 29.0 ----> Question # !1 or Question # !0 ]
Question #10 : Educ >= 4.0 ----> Question # 11 or Question # 12 ]
Question #11 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #12 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #13 : MMSE >= 25.0 ----> Question # 14 or Question # !1 ]
Question #14 : SES >= 4.0 ----> Question # 15 or Question # 16 ]
Question #15 : Educ >= 2.0 ----> Question # !1 or Question # !1 ]
Question #16 : SES >= 3.0 ----> Question # 17 or Question # 18 ]
Question #17 : Educ >= 5.0 ----> Question # !0 or Question # !1 ]
Question #18 : Educ >= 5.0 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 13 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 8 ]
Question #2 : Educ >= 5.0 ----> Question # 3 or Question # 5 ]
Question #3 : MMSE >= 30.0 ----> Question # !0 or Question # 4 ]
Question #4 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #5 : Educ >= 3.0 ----> Question # 6 or Question # 7 ]
Question #6 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #7 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #8 : SES >= 4.0 ----> Question # 9 or Question # 10 ]
Question #9 : Educ >= 2.0 ----> Question # !0 or Question # !0 ]
Question #10 : Educ >= 3.0 ----> Question # 11 or Question # 12 ]
Question #11 : SES >= 3.0 ----> Question # !0 or Question # !1 ]
Question #12 : SES >= 3.0 ----> Question # !1 or Question # !0 ]
Question #13 : MMSE >= 26.0 ----> Question # 14 or Question # !1 ]
Question #14 : SES >= 5.0 ----> Question # !0 or Question # 15 ]
Question #15 : MMSE >= 27.0 ----> Question # 16 or Question # 17 ]
Question #16 : SES >= 4.0 ----> Question # !1 or Question # !1 ]
Question #17 : Educ >= 5.0 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : SES >= 5.0 ----> Question # !1 or Question # 1 ]
Question #1 : Educ >= 5.0 ----> Question # 2 or Question # 4 ]
Question #2 : SES >= 2.0 ----> Question # 3 or Question # !0 ]
Question #3 : SES >= 3.0 ----> Question # !1 or Question # !0 ]
Question #4 : Educ >= 4.0 ----> Question # 5 or Question # 7 ]
Question #5 : SES >= 4.0 ----> Question # !1 or Question # 6 ]
Question #6 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #7 : SES >= 2.0 ----> Question # 8 or Question # !0 ]
Question #8 : SES >= 3.0 ----> Question # !0 or Question # !0 ]""",

                """
Question #0 : Age >= 66 ----> Question # 1 or Question # 15 ]
Question #1 : M/F >= 1 ----> Question # 2 or Question # 9 ]
Question #2 : SES >= 3.0 ----> Question # 3 or Question # 6 ]
Question #3 : Age >= 72 ----> Question # 4 or Question # 5 ]
Question #4 : Age >= 84 ----> Question # !0 or Question # !1 ]
Question #5 : Age >= 67 ----> Question # !0 or Question # !1 ]
Question #6 : Age >= 92 ----> Question # 7 or Question # 8 ]
Question #7 : Age >= 94 ----> Question # !0 or Question # !1 ]
Question #8 : Age >= 83 ----> Question # !0 or Question # !0 ]
Question #9 : SES >= 3.0 ----> Question # 10 or Question # 13 ]
Question #10 : Age >= 86 ----> Question # 11 or Question # 12 ]
Question #11 : Age >= 90 ----> Question # !1 or Question # !1 ]
Question #12 : Age >= 80 ----> Question # !0 or Question # !1 ]
Question #13 : Age >= 84 ----> Question # !0 or Question # 14 ]
Question #14 : Age >= 79 ----> Question # !1 or Question # !1 ]
Question #15 : Age >= 64 ----> Question # 16 or Question # !0 ]
Question #16 : SES >= 3.0 ----> Question # !0 or Question # 17 ]
Question #17 : M/F >= 1 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : Age >= 66 ----> Question # 1 or Question # 8 ]
Question #1 : Age >= 88 ----> Question # 2 or Question # 5 ]
Question #2 : Age >= 96 ----> Question # !1 or Question # 3 ]
Question #3 : M/F >= 1 ----> Question # !0 or Question # 4 ]
Question #4 : Age >= 89 ----> Question # !0 or Question # !1 ]
Question #5 : Age >= 68 ----> Question # 6 or Question # !1 ]
Question #6 : Age >= 69 ----> Question # 7 or Question # !0 ]
Question #7 : M/F >= 1 ----> Question # !1 or Question # !1 ]
Question #8 : Age >= 64 ----> Question # 9 or Question # !0 ]
Question #9 : M/F >= 1 ----> Question # !0 or Question # 10 ]
Question #10 : Age >= 65 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 11 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 7 ]
Question #2 : Educ >= 5.0 ----> Question # 3 or Question # 4 ]
Question #3 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #4 : MMSE >= 30.0 ----> Question # 5 or Question # 6 ]
Question #5 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #6 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]
Question #7 : Educ >= 4.0 ----> Question # 8 or Question # 9 ]
Question #8 : Educ >= 5.0 ----> Question # !1 or Question # !1 ]
Question #9 : Educ >= 3.0 ----> Question # !0 or Question # 10 ]
Question #10 : Educ >= 2.0 ----> Question # !0 or Question # !0 ]
Question #11 : MMSE >= 25.0 ----> Question # 12 or Question # !1 ]
Question #12 : Educ >= 5.0 ----> Question # 13 or Question # 14 ]
Question #13 : MMSE >= 27.0 ----> Question # !0 or Question # !1 ]
Question #14 : MMSE >= 26.0 ----> Question # 15 or Question # !0 ]
Question #15 : MMSE >= 27.0 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : Age >= 66 ----> Question # 1 or Question # !0 ]
Question #1 : M/F >= 1 ----> Question # 2 or Question # 7 ]
Question #2 : Age >= 72 ----> Question # 3 or Question # 5 ]
Question #3 : Age >= 73 ----> Question # 4 or Question # !1 ]
Question #4 : Age >= 90 ----> Question # !0 or Question # !0 ]
Question #5 : Age >= 67 ----> Question # 6 or Question # !1 ]
Question #6 : Age >= 69 ----> Question # !0 or Question # !0 ]
Question #7 : Age >= 84 ----> Question # 8 or Question # 10 ]
Question #8 : Age >= 90 ----> Question # !1 or Question # 9 ]
Question #9 : Age >= 86 ----> Question # !0 or Question # !0 ]
Question #10 : Age >= 75 ----> Question # 11 or Question # 12 ]
Question #11 : Age >= 81 ----> Question # !1 or Question # !1 ]
Question #12 : Age >= 74 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 6 ]
Question #1 : Age >= 66 ----> Question # 2 or Question # !0 ]
Question #2 : Age >= 86 ----> Question # !0 or Question # 3 ]
Question #3 : Educ >= 5.0 ----> Question # 4 or Question # 5 ]
Question #4 : MMSE >= 30.0 ----> Question # !0 or Question # !1 ]
Question #5 : Age >= 67 ----> Question # !0 or Question # !1 ]
Question #6 : Age >= 64 ----> Question # 7 or Question # !0 ]
Question #7 : MMSE >= 26.0 ----> Question # 8 or Question # !1 ]
Question #8 : Age >= 70 ----> Question # 9 or Question # !0 ]
Question #9 : Educ >= 5.0 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : Age >= 64 ----> Question # 1 or Question # !0 ]
Question #1 : Age >= 89 ----> Question # 2 or Question # 6 ]
Question #2 : Age >= 96 ----> Question # !1 or Question # 3 ]
Question #3 : Age >= 90 ----> Question # 4 or Question # 5 ]
Question #4 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #5 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #6 : Age >= 77 ----> Question # 7 or Question # 10 ]
Question #7 : Age >= 80 ----> Question # 8 or Question # 9 ]
Question #8 : Age >= 83 ----> Question # !1 or Question # !0 ]
Question #9 : M/F >= 1 ----> Question # !1 or Question # !1 ]
Question #10 : Age >= 74 ----> Question # 11 or Question # 12 ]
Question #11 : Age >= 76 ----> Question # !0 or Question # !0 ]
Question #12 : Age >= 69 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : SES >= 3.0 ----> Question # 1 or Question # 12 ]
Question #1 : Educ >= 3.0 ----> Question # 2 or Question # 6 ]
Question #2 : Educ >= 4.0 ----> Question # 3 or Question # 4 ]
Question #3 : M/F >= 1 ----> Question # !0 or Question # !1 ]
Question #4 : SES >= 4.0 ----> Question # !0 or Question # 5 ]
Question #5 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #6 : Educ >= 2.0 ----> Question # 7 or Question # 10 ]
Question #7 : SES >= 4.0 ----> Question # 8 or Question # 9 ]
Question #8 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #9 : M/F >= 1 ----> Question # !0 or Question # !1 ]
Question #10 : SES >= 4.0 ----> Question # 11 or Question # !0 ]
Question #11 : SES >= 5.0 ----> Question # !0 or Question # !1 ]
Question #12 : M/F >= 1 ----> Question # 13 or Question # 20 ]
Question #13 : Educ >= 4.0 ----> Question # 14 or Question # 17 ]
Question #14 : Educ >= 5.0 ----> Question # 15 or Question # 16 ]
Question #15 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #16 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #17 : Educ >= 3.0 ----> Question # 18 or Question # 19 ]
Question #18 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #19 : Educ >= 2.0 ----> Question # !0 or Question # !0 ]
Question #20 : Educ >= 5.0 ----> Question # 21 or Question # 22 ]
Question #21 : SES >= 2.0 ----> Question # !1 or Question # !1 ]
Question #22 : SES >= 2.0 ----> Question # 23 or Question # !0 ]
Question #23 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]""",

                """
Question #0 : MMSE >= 26.0 ----> Question # 1 or Question # !1 ]
Question #1 : MMSE >= 30.0 ----> Question # 2 or Question # 6 ]
Question #2 : Educ >= 3.0 ----> Question # 3 or Question # 5 ]
Question #3 : Educ >= 5.0 ----> Question # !0 or Question # 4 ]
Question #4 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #5 : Educ >= 2.0 ----> Question # !0 or Question # !0 ]
Question #6 : Educ >= 4.0 ----> Question # 7 or Question # 10 ]
Question #7 : MMSE >= 28.0 ----> Question # 8 or Question # 9 ]
Question #8 : Educ >= 5.0 ----> Question # !0 or Question # !0 ]
Question #9 : MMSE >= 27.0 ----> Question # !1 or Question # !1 ]
Question #10 : MMSE >= 29.0 ----> Question # 11 or Question # 12 ]
Question #11 : Educ >= 2.0 ----> Question # !0 or Question # !0 ]
Question #12 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 7 ]
Question #1 : Age >= 66 ----> Question # 2 or Question # !0 ]
Question #2 : Age >= 67 ----> Question # 3 or Question # 6 ]
Question #3 : Age >= 81 ----> Question # 4 or Question # 5 ]
Question #4 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #5 : MMSE >= 29.0 ----> Question # !0 or Question # !1 ]
Question #6 : Educ >= 5.0 ----> Question # !0 or Question # !1 ]
Question #7 : MMSE >= 25.0 ----> Question # 8 or Question # !1 ]
Question #8 : Age >= 72 ----> Question # 9 or Question # !0 ]
Question #9 : Age >= 85 ----> Question # 10 or Question # 11 ]
Question #10 : MMSE >= 27.0 ----> Question # !1 or Question # !0 ]
Question #11 : Age >= 75 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : MMSE >= 25.0 ----> Question # 1 or Question # !1 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 9 ]
Question #2 : M/F >= 1 ----> Question # 3 or Question # 6 ]
Question #3 : MMSE >= 30.0 ----> Question # 4 or Question # 5 ]
Question #4 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #5 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #6 : SES >= 2.0 ----> Question # 7 or Question # 8 ]
Question #7 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #8 : MMSE >= 30.0 ----> Question # !0 or Question # !1 ]
Question #9 : SES >= 3.0 ----> Question # 10 or Question # 13 ]
Question #10 : M/F >= 1 ----> Question # 11 or Question # 12 ]
Question #11 : SES >= 5.0 ----> Question # !0 or Question # !1 ]
Question #12 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #13 : M/F >= 1 ----> Question # 14 or Question # 15 ]
Question #14 : MMSE >= 27.0 ----> Question # !0 or Question # !1 ]
Question #15 : SES >= 2.0 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : MMSE >= 29.0 ----> Question # 1 or Question # 8 ]
Question #1 : SES >= 3.0 ----> Question # 2 or Question # 5 ]
Question #2 : MMSE >= 30.0 ----> Question # 3 or Question # 4 ]
Question #3 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #4 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #5 : SES >= 2.0 ----> Question # 6 or Question # 7 ]
Question #6 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #7 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #8 : MMSE >= 25.0 ----> Question # 9 or Question # !1 ]
Question #9 : MMSE >= 28.0 ----> Question # 10 or Question # 12 ]
Question #10 : SES >= 2.0 ----> Question # 11 or Question # !0 ]
Question #11 : SES >= 4.0 ----> Question # !0 or Question # !1 ]
Question #12 : SES >= 5.0 ----> Question # !0 or Question # 13 ]
Question #13 : MMSE >= 26.0 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 11 ]
Question #1 : Age >= 75 ----> Question # 2 or Question # 6 ]
Question #2 : Age >= 82 ----> Question # !0 or Question # 3 ]
Question #3 : Age >= 79 ----> Question # 4 or Question # 5 ]
Question #4 : MMSE >= 30.0 ----> Question # !0 or Question # !1 ]
Question #5 : MMSE >= 30.0 ----> Question # !1 or Question # !0 ]
Question #6 : MMSE >= 29.0 ----> Question # 7 or Question # 9 ]
Question #7 : Age >= 70 ----> Question # 8 or Question # !0 ]
Question #8 : Age >= 71 ----> Question # !0 or Question # !0 ]
Question #9 : Age >= 66 ----> Question # 10 or Question # !0 ]
Question #10 : Age >= 73 ----> Question # !0 or Question # !1 ]
Question #11 : MMSE >= 25.0 ----> Question # 12 or Question # !1 ]
Question #12 : Age >= 70 ----> Question # 13 or Question # !0 ]
Question #13 : Age >= 92 ----> Question # !1 or Question # 14 ]
Question #14 : Age >= 89 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : Educ >= 4.0 ----> Question # 1 or Question # 4 ]
Question #1 : M/F >= 1 ----> Question # 2 or Question # 3 ]
Question #2 : Educ >= 5.0 ----> Question # !0 or Question # !0 ]
Question #3 : Educ >= 5.0 ----> Question # !0 or Question # !1 ]
Question #4 : Educ >= 2.0 ----> Question # 5 or Question # 8 ]
Question #5 : M/F >= 1 ----> Question # 6 or Question # 7 ]
Question #6 : Educ >= 3.0 ----> Question # !1 or Question # !0 ]
Question #7 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]
Question #8 : M/F >= 1 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : MMSE >= 27.0 ----> Question # 1 or Question # 7 ]
Question #1 : MMSE >= 30.0 ----> Question # 2 or Question # 3 ]
Question #2 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #3 : M/F >= 1 ----> Question # 4 or Question # 6 ]
Question #4 : MMSE >= 29.0 ----> Question # !0 or Question # 5 ]
Question #5 : MMSE >= 28.0 ----> Question # !0 or Question # !0 ]
Question #6 : MMSE >= 29.0 ----> Question # !0 or Question # !0 ]
Question #7 : MMSE >= 25.0 ----> Question # 8 or Question # !1 ]
Question #8 : M/F >= 1 ----> Question # 9 or Question # 10 ]
Question #9 : MMSE >= 26.0 ----> Question # !1 or Question # !1 ]
Question #10 : MMSE >= 26.0 ----> Question # !0 or Question # !0 ]""",

                """
Question #0 : Age >= 70 ----> Question # 1 or Question # 13 ]
Question #1 : Educ >= 4.0 ----> Question # 2 or Question # 7 ]
Question #2 : Age >= 73 ----> Question # 3 or Question # 5 ]
Question #3 : Age >= 96 ----> Question # !1 or Question # 4 ]
Question #4 : Age >= 84 ----> Question # !0 or Question # !0 ]
Question #5 : Educ >= 5.0 ----> Question # 6 or Question # !1 ]
Question #6 : Age >= 72 ----> Question # !1 or Question # !0 ]
Question #7 : Age >= 75 ----> Question # 8 or Question # 11 ]
Question #8 : Age >= 88 ----> Question # 9 or Question # 10 ]
Question #9 : Educ >= 2.0 ----> Question # !0 or Question # !1 ]
Question #10 : Educ >= 2.0 ----> Question # !1 or Question # !1 ]
Question #11 : Educ >= 2.0 ----> Question # 12 or Question # !0 ]
Question #12 : Age >= 72 ----> Question # !1 or Question # !0 ]
Question #13 : Age >= 64 ----> Question # 14 or Question # !0 ]
Question #14 : Educ >= 3.0 ----> Question # 15 or Question # 17 ]
Question #15 : Age >= 65 ----> Question # !0 or Question # 16 ]
Question #16 : Educ >= 5.0 ----> Question # !1 or Question # !0 ]
Question #17 : Age >= 66 ----> Question # 18 or Question # !0 ]
Question #18 : Educ >= 2.0 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : Educ >= 2.0 ----> Question # 1 or Question # 9 ]
Question #1 : SES >= 4.0 ----> Question # 2 or Question # 3 ]
Question #2 : Educ >= 3.0 ----> Question # !0 or Question # !1 ]
Question #3 : SES >= 2.0 ----> Question # 4 or Question # 7 ]
Question #4 : Educ >= 5.0 ----> Question # 5 or Question # 6 ]
Question #5 : SES >= 3.0 ----> Question # !1 or Question # !0 ]
Question #6 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #7 : Educ >= 4.0 ----> Question # 8 or Question # !0 ]
Question #8 : Educ >= 5.0 ----> Question # !0 or Question # !0 ]
Question #9 : SES >= 4.0 ----> Question # 10 or Question # !0 ]
Question #10 : SES >= 5.0 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 9 ]
Question #1 : MMSE >= 30.0 ----> Question # 2 or Question # 5 ]
Question #2 : Educ >= 5.0 ----> Question # !0 or Question # 3 ]
Question #3 : SES >= 2.0 ----> Question # 4 or Question # !0 ]
Question #4 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]
Question #5 : SES >= 4.0 ----> Question # !0 or Question # 6 ]
Question #6 : MMSE >= 29.0 ----> Question # 7 or Question # 8 ]
Question #7 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #8 : SES >= 2.0 ----> Question # !1 or Question # !0 ]
Question #9 : MMSE >= 25.0 ----> Question # 10 or Question # !1 ]
Question #10 : Educ >= 2.0 ----> Question # 11 or Question # 13 ]
Question #11 : MMSE >= 26.0 ----> Question # 12 or Question # !0 ]
Question #12 : MMSE >= 27.0 ----> Question # !1 or Question # !1 ]
Question #13 : SES >= 4.0 ----> Question # 14 or Question # !0 ]
Question #14 : SES >= 5.0 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : Age >= 66 ----> Question # 1 or Question # 11 ]
Question #1 : Age >= 84 ----> Question # 2 or Question # 5 ]
Question #2 : Age >= 96 ----> Question # !1 or Question # 3 ]
Question #3 : Educ >= 4.0 ----> Question # !0 or Question # 4 ]
Question #4 : Age >= 89 ----> Question # !0 or Question # !1 ]
Question #5 : Age >= 75 ----> Question # 6 or Question # 9 ]
Question #6 : SES >= 5.0 ----> Question # 7 or Question # 8 ]
Question #7 : Age >= 80 ----> Question # !0 or Question # !1 ]
Question #8 : Educ >= 4.0 ----> Question # !1 or Question # !1 ]
Question #9 : Age >= 74 ----> Question # !0 or Question # 10 ]
Question #10 : Age >= 67 ----> Question # !1 or Question # !1 ]
Question #11 : Age >= 64 ----> Question # 12 or Question # !0 ]
Question #12 : Age >= 65 ----> Question # !0 or Question # 13 ]
Question #13 : Educ >= 5.0 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 6 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 5 ]
Question #2 : M/F >= 1 ----> Question # 3 or Question # 4 ]
Question #3 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #4 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #5 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #6 : MMSE >= 25.0 ----> Question # 7 or Question # !1 ]
Question #7 : MMSE >= 27.0 ----> Question # 8 or Question # 9 ]
Question #8 : M/F >= 1 ----> Question # !1 or Question # !1 ]
Question #9 : M/F >= 1 ----> Question # !1 or Question # 10 ]
Question #10 : MMSE >= 26.0 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 6 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 5 ]
Question #2 : M/F >= 1 ----> Question # 3 or Question # 4 ]
Question #3 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #4 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #5 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #6 : MMSE >= 25.0 ----> Question # 7 or Question # !1 ]
Question #7 : MMSE >= 27.0 ----> Question # 8 or Question # 9 ]
Question #8 : M/F >= 1 ----> Question # !1 or Question # !1 ]
Question #9 : M/F >= 1 ----> Question # !0 or Question # 10 ]
Question #10 : MMSE >= 26.0 ----> Question # !0 or Question # !0 ]""",

                """
Question #0 : Age >= 66 ----> Question # 1 or Question # !0 ]
Question #1 : Educ >= 3.0 ----> Question # 2 or Question # 8 ]
Question #2 : Age >= 70 ----> Question # 3 or Question # 6 ]
Question #3 : Age >= 84 ----> Question # 4 or Question # 5 ]
Question #4 : Age >= 92 ----> Question # !1 or Question # !0 ]
Question #5 : Age >= 79 ----> Question # !1 or Question # !1 ]
Question #6 : Age >= 68 ----> Question # !0 or Question # 7 ]
Question #7 : Educ >= 4.0 ----> Question # !1 or Question # !0 ]
Question #8 : Age >= 70 ----> Question # 9 or Question # 12 ]
Question #9 : Age >= 75 ----> Question # 10 or Question # 11 ]
Question #10 : Age >= 80 ----> Question # !1 or Question # !1 ]
Question #11 : Age >= 73 ----> Question # !0 or Question # !0 ]
Question #12 : Age >= 69 ----> Question # !1 or Question # 13 ]
Question #13 : Age >= 67 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : Age >= 66 ----> Question # 1 or Question # 14 ]
Question #1 : Age >= 88 ----> Question # 2 or Question # 8 ]
Question #2 : Age >= 92 ----> Question # 3 or Question # 5 ]
Question #3 : Age >= 94 ----> Question # 4 or Question # !1 ]
Question #4 : Age >= 96 ----> Question # !1 or Question # !0 ]
Question #5 : SES >= 4.0 ----> Question # 6 or Question # 7 ]
Question #6 : Age >= 90 ----> Question # !1 or Question # !0 ]
Question #7 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #8 : SES >= 3.0 ----> Question # 9 or Question # 12 ]
Question #9 : Age >= 75 ----> Question # 10 or Question # 11 ]
Question #10 : SES >= 4.0 ----> Question # !1 or Question # !1 ]
Question #11 : Age >= 68 ----> Question # !0 or Question # !1 ]
Question #12 : Age >= 85 ----> Question # !0 or Question # 13 ]
Question #13 : Age >= 83 ----> Question # !1 or Question # !0 ]
Question #14 : Age >= 64 ----> Question # 15 or Question # !0 ]
Question #15 : Age >= 65 ----> Question # !0 or Question # 16 ]
Question #16 : SES >= 4.0 ----> Question # !0 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 12 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 8 ]
Question #2 : MMSE >= 30.0 ----> Question # 3 or Question # 5 ]
Question #3 : Educ >= 5.0 ----> Question # !0 or Question # 4 ]
Question #4 : Educ >= 2.0 ----> Question # !0 or Question # !0 ]
Question #5 : Educ >= 4.0 ----> Question # 6 or Question # 7 ]
Question #6 : Educ >= 5.0 ----> Question # !0 or Question # !0 ]
Question #7 : Educ >= 2.0 ----> Question # !0 or Question # !0 ]
Question #8 : Educ >= 5.0 ----> Question # !1 or Question # 9 ]
Question #9 : Educ >= 3.0 ----> Question # 10 or Question # 11 ]
Question #10 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #11 : Educ >= 2.0 ----> Question # !1 or Question # !1 ]
Question #12 : MMSE >= 26.0 ----> Question # 13 or Question # !1 ]
Question #13 : Educ >= 2.0 ----> Question # 14 or Question # 17 ]
Question #14 : Educ >= 5.0 ----> Question # 15 or Question # 16 ]
Question #15 : MMSE >= 27.0 ----> Question # !1 or Question # !1 ]
Question #16 : Educ >= 4.0 ----> Question # !1 or Question # !1 ]
Question #17 : MMSE >= 27.0 ----> Question # !0 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 9 ]
Question #1 : Age >= 66 ----> Question # 2 or Question # !0 ]
Question #2 : MMSE >= 29.0 ----> Question # 3 or Question # 6 ]
Question #3 : Age >= 71 ----> Question # 4 or Question # 5 ]
Question #4 : Age >= 81 ----> Question # !0 or Question # !0 ]
Question #5 : Age >= 70 ----> Question # !1 or Question # !0 ]
Question #6 : Age >= 81 ----> Question # 7 or Question # 8 ]
Question #7 : Age >= 88 ----> Question # !0 or Question # !0 ]
Question #8 : Age >= 73 ----> Question # !1 or Question # !0 ]
Question #9 : Age >= 69 ----> Question # 10 or Question # !0 ]
Question #10 : MMSE >= 26.0 ----> Question # 11 or Question # !1 ]
Question #11 : Age >= 89 ----> Question # !0 or Question # 12 ]
Question #12 : Age >= 70 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 12 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 9 ]
Question #2 : M/F >= 1 ----> Question # 3 or Question # 6 ]
Question #3 : MMSE >= 30.0 ----> Question # 4 or Question # 5 ]
Question #4 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #5 : Educ >= 2.0 ----> Question # !0 or Question # !0 ]
Question #6 : Educ >= 4.0 ----> Question # 7 or Question # 8 ]
Question #7 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #8 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #9 : Educ >= 5.0 ----> Question # !1 or Question # 10 ]
Question #10 : M/F >= 1 ----> Question # 11 or Question # !0 ]
Question #11 : Educ >= 3.0 ----> Question # !0 or Question # !1 ]
Question #12 : MMSE >= 25.0 ----> Question # 13 or Question # !1 ]
Question #13 : Educ >= 4.0 ----> Question # 14 or Question # 16 ]
Question #14 : Educ >= 5.0 ----> Question # !1 or Question # 15 ]
Question #15 : MMSE >= 27.0 ----> Question # !1 or Question # !1 ]
Question #16 : MMSE >= 26.0 ----> Question # 17 or Question # !0 ]
Question #17 : M/F >= 1 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : Age >= 66 ----> Question # 1 or Question # 13 ]
Question #1 : Age >= 85 ----> Question # 2 or Question # 7 ]
Question #2 : SES >= 4.0 ----> Question # 3 or Question # 5 ]
Question #3 : Age >= 87 ----> Question # 4 or Question # !1 ]
Question #4 : Age >= 90 ----> Question # !1 or Question # !0 ]
Question #5 : Age >= 96 ----> Question # !1 or Question # 6 ]
Question #6 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #7 : Age >= 72 ----> Question # 8 or Question # 11 ]
Question #8 : M/F >= 1 ----> Question # 9 or Question # 10 ]
Question #9 : SES >= 3.0 ----> Question # !1 or Question # !0 ]
Question #10 : Age >= 77 ----> Question # !1 or Question # !1 ]
Question #11 : Age >= 71 ----> Question # !0 or Question # 12 ]
Question #12 : SES >= 2.0 ----> Question # !1 or Question # !0 ]
Question #13 : Age >= 64 ----> Question # 14 or Question # !0 ]
Question #14 : M/F >= 1 ----> Question # !0 or Question # 15 ]
Question #15 : SES >= 4.0 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 12 ]
Question #1 : Educ >= 5.0 ----> Question # 2 or Question # 5 ]
Question #2 : MMSE >= 30.0 ----> Question # !0 or Question # 3 ]
Question #3 : M/F >= 1 ----> Question # 4 or Question # !0 ]
Question #4 : MMSE >= 29.0 ----> Question # !0 or Question # !0 ]
Question #5 : M/F >= 1 ----> Question # 6 or Question # 9 ]
Question #6 : MMSE >= 29.0 ----> Question # 7 or Question # 8 ]
Question #7 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #8 : Educ >= 4.0 ----> Question # !1 or Question # !0 ]
Question #9 : MMSE >= 30.0 ----> Question # 10 or Question # 11 ]
Question #10 : Educ >= 3.0 ----> Question # !1 or Question # !0 ]
Question #11 : MMSE >= 29.0 ----> Question # !0 or Question # !0 ]
Question #12 : MMSE >= 26.0 ----> Question # 13 or Question # !1 ]
Question #13 : Educ >= 3.0 ----> Question # 14 or Question # 17 ]
Question #14 : M/F >= 1 ----> Question # 15 or Question # 16 ]
Question #15 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #16 : Educ >= 5.0 ----> Question # !1 or Question # !0 ]
Question #17 : Educ >= 2.0 ----> Question # 18 or Question # 19 ]
Question #18 : M/F >= 1 ----> Question # !1 or Question # !1 ]
Question #19 : M/F >= 1 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : Age >= 66 ----> Question # 1 or Question # !0 ]
Question #1 : Age >= 85 ----> Question # 2 or Question # 6 ]
Question #2 : Educ >= 2.0 ----> Question # 3 or Question # !1 ]
Question #3 : Educ >= 5.0 ----> Question # 4 or Question # 5 ]
Question #4 : Age >= 92 ----> Question # !1 or Question # !0 ]
Question #5 : Age >= 88 ----> Question # !0 or Question # !0 ]
Question #6 : Educ >= 5.0 ----> Question # 7 or Question # 10 ]
Question #7 : Age >= 77 ----> Question # 8 or Question # 9 ]
Question #8 : Age >= 82 ----> Question # !0 or Question # !1 ]
Question #9 : Age >= 74 ----> Question # !0 or Question # !0 ]
Question #10 : Age >= 67 ----> Question # 11 or Question # !1 ]
Question #11 : Educ >= 3.0 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 7 ]
Question #1 : Age >= 66 ----> Question # 2 or Question # !0 ]
Question #2 : Age >= 69 ----> Question # 3 or Question # 6 ]
Question #3 : MMSE >= 30.0 ----> Question # 4 or Question # 5 ]
Question #4 : Age >= 76 ----> Question # !0 or Question # !0 ]
Question #5 : Age >= 71 ----> Question # !0 or Question # !1 ]
Question #6 : SES >= 3.0 ----> Question # !1 or Question # !0 ]
Question #7 : MMSE >= 25.0 ----> Question # 8 or Question # !1 ]
Question #8 : Age >= 70 ----> Question # 9 or Question # !0 ]
Question #9 : SES >= 5.0 ----> Question # !0 or Question # 10 ]
Question #10 : Age >= 85 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : MMSE >= 25.0 ----> Question # 1 or Question # !1 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 5 ]
Question #2 : Age >= 66 ----> Question # 3 or Question # !0 ]
Question #3 : Age >= 67 ----> Question # 4 or Question # !1 ]
Question #4 : Age >= 86 ----> Question # !0 or Question # !0 ]
Question #5 : Age >= 89 ----> Question # !0 or Question # 6 ]
Question #6 : MMSE >= 28.0 ----> Question # 7 or Question # 8 ]
Question #7 : Age >= 80 ----> Question # !0 or Question # !0 ]
Question #8 : Age >= 70 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 8 ]
Question #1 : Age >= 66 ----> Question # 2 or Question # !0 ]
Question #2 : MMSE >= 29.0 ----> Question # 3 or Question # 6 ]
Question #3 : Age >= 71 ----> Question # 4 or Question # 5 ]
Question #4 : Age >= 85 ----> Question # !0 or Question # !0 ]
Question #5 : Educ >= 2.0 ----> Question # !0 or Question # !1 ]
Question #6 : Educ >= 5.0 ----> Question # !1 or Question # 7 ]
Question #7 : Age >= 76 ----> Question # !0 or Question # !1 ]
Question #8 : MMSE >= 26.0 ----> Question # 9 or Question # !1 ]
Question #9 : Age >= 70 ----> Question # 10 or Question # !0 ]
Question #10 : Age >= 85 ----> Question # 11 or Question # 12 ]
Question #11 : Educ >= 4.0 ----> Question # !0 or Question # !1 ]
Question #12 : Educ >= 2.0 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : Educ >= 4.0 ----> Question # 1 or Question # 4 ]
Question #1 : M/F >= 1 ----> Question # 2 or Question # 3 ]
Question #2 : Educ >= 5.0 ----> Question # !0 or Question # !0 ]
Question #3 : Educ >= 5.0 ----> Question # !0 or Question # !0 ]
Question #4 : Educ >= 2.0 ----> Question # 5 or Question # 8 ]
Question #5 : M/F >= 1 ----> Question # 6 or Question # 7 ]
Question #6 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]
Question #7 : Educ >= 3.0 ----> Question # !0 or Question # !1 ]
Question #8 : M/F >= 1 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 12 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 8 ]
Question #2 : M/F >= 1 ----> Question # 3 or Question # 5 ]
Question #3 : SES >= 4.0 ----> Question # !0 or Question # 4 ]
Question #4 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #5 : SES >= 2.0 ----> Question # 6 or Question # 7 ]
Question #6 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #7 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #8 : SES >= 4.0 ----> Question # 9 or Question # 10 ]
Question #9 : M/F >= 1 ----> Question # !1 or Question # !0 ]
Question #10 : M/F >= 1 ----> Question # 11 or Question # !1 ]
Question #11 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #12 : MMSE >= 25.0 ----> Question # 13 or Question # !1 ]
Question #13 : M/F >= 1 ----> Question # 14 or Question # 17 ]
Question #14 : SES >= 2.0 ----> Question # 15 or Question # 16 ]
Question #15 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #16 : MMSE >= 27.0 ----> Question # !1 or Question # !1 ]
Question #17 : MMSE >= 26.0 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 10 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 7 ]
Question #2 : SES >= 4.0 ----> Question # 3 or Question # 4 ]
Question #3 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #4 : SES >= 3.0 ----> Question # 5 or Question # 6 ]
Question #5 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #6 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #7 : SES >= 2.0 ----> Question # 8 or Question # !0 ]
Question #8 : SES >= 4.0 ----> Question # !0 or Question # 9 ]
Question #9 : SES >= 3.0 ----> Question # !1 or Question # !0 ]
Question #10 : MMSE >= 25.0 ----> Question # 11 or Question # !1 ]
Question #11 : SES >= 5.0 ----> Question # !0 or Question # 12 ]
Question #12 : SES >= 4.0 ----> Question # 13 or Question # 14 ]
Question #13 : MMSE >= 27.0 ----> Question # !1 or Question # !1 ]
Question #14 : SES >= 2.0 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : Age >= 71 ----> Question # 1 or Question # 11 ]
Question #1 : Educ >= 2.0 ----> Question # 2 or Question # 8 ]
Question #2 : Age >= 88 ----> Question # 3 or Question # 5 ]
Question #3 : Age >= 90 ----> Question # 4 or Question # !0 ]
Question #4 : Educ >= 5.0 ----> Question # !0 or Question # !0 ]
Question #5 : Age >= 77 ----> Question # 6 or Question # 7 ]
Question #6 : Age >= 78 ----> Question # !1 or Question # !1 ]
Question #7 : SES >= 2.0 ----> Question # !0 or Question # !1 ]
Question #8 : SES >= 4.0 ----> Question # 9 or Question # !0 ]
Question #9 : Age >= 83 ----> Question # !1 or Question # 10 ]
Question #10 : Age >= 81 ----> Question # !0 or Question # !1 ]
Question #11 : Age >= 66 ----> Question # 12 or Question # !0 ]
Question #12 : Educ >= 3.0 ----> Question # 13 or Question # 16 ]
Question #13 : Educ >= 4.0 ----> Question # 14 or Question # 15 ]
Question #14 : Educ >= 5.0 ----> Question # !0 or Question # !1 ]
Question #15 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #16 : Age >= 69 ----> Question # 17 or Question # !1 ]
Question #17 : SES >= 4.0 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : Age >= 69 ----> Question # 1 or Question # 14 ]
Question #1 : Educ >= 4.0 ----> Question # 2 or Question # 8 ]
Question #2 : Age >= 74 ----> Question # 3 or Question # 6 ]
Question #3 : Age >= 92 ----> Question # 4 or Question # 5 ]
Question #4 : Age >= 94 ----> Question # !0 or Question # !1 ]
Question #5 : Age >= 82 ----> Question # !0 or Question # !0 ]
Question #6 : Age >= 70 ----> Question # 7 or Question # !0 ]
Question #7 : Age >= 73 ----> Question # !1 or Question # !1 ]
Question #8 : Age >= 89 ----> Question # 9 or Question # 11 ]
Question #9 : Educ >= 3.0 ----> Question # !0 or Question # 10 ]
Question #10 : Age >= 90 ----> Question # !0 or Question # !0 ]
Question #11 : Age >= 75 ----> Question # 12 or Question # 13 ]
Question #12 : Age >= 81 ----> Question # !1 or Question # !1 ]
Question #13 : Age >= 71 ----> Question # !0 or Question # !1 ]
Question #14 : Educ >= 2.0 ----> Question # 15 or Question # !1 ]
Question #15 : Age >= 64 ----> Question # 16 or Question # !0 ]
Question #16 : Educ >= 4.0 ----> Question # 17 or Question # !0 ]
Question #17 : Age >= 67 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : Age >= 66 ----> Question # 1 or Question # 13 ]
Question #1 : Age >= 85 ----> Question # 2 or Question # 6 ]
Question #2 : Educ >= 2.0 ----> Question # 3 or Question # 5 ]
Question #3 : Age >= 96 ----> Question # !1 or Question # 4 ]
Question #4 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #5 : Age >= 90 ----> Question # !0 or Question # !1 ]
Question #6 : Educ >= 5.0 ----> Question # 7 or Question # 10 ]
Question #7 : Age >= 80 ----> Question # 8 or Question # 9 ]
Question #8 : Age >= 82 ----> Question # !0 or Question # !1 ]
Question #9 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #10 : Age >= 78 ----> Question # 11 or Question # 12 ]
Question #11 : M/F >= 1 ----> Question # !1 or Question # !1 ]
Question #12 : Educ >= 4.0 ----> Question # !1 or Question # !0 ]
Question #13 : Age >= 64 ----> Question # 14 or Question # !0 ]
Question #14 : Educ >= 5.0 ----> Question # 15 or Question # !0 ]
Question #15 : M/F >= 1 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : Educ >= 5.0 ----> Question # 1 or Question # 6 ]
Question #1 : SES >= 2.0 ----> Question # 2 or Question # 5 ]
Question #2 : M/F >= 1 ----> Question # 3 or Question # 4 ]
Question #3 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #4 : SES >= 3.0 ----> Question # !1 or Question # !0 ]
Question #5 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #6 : Educ >= 2.0 ----> Question # 7 or Question # 14 ]
Question #7 : SES >= 3.0 ----> Question # 8 or Question # 11 ]
Question #8 : Educ >= 3.0 ----> Question # 9 or Question # 10 ]
Question #9 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #10 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #11 : Educ >= 4.0 ----> Question # 12 or Question # 13 ]
Question #12 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #13 : M/F >= 1 ----> Question # !1 or Question # !1 ]
Question #14 : SES >= 4.0 ----> Question # 15 or Question # !0 ]
Question #15 : SES >= 5.0 ----> Question # !0 or Question # 16 ]
Question #16 : M/F >= 1 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : Age >= 64 ----> Question # 1 or Question # !0 ]
Question #1 : Age >= 84 ----> Question # 2 or Question # 7 ]
Question #2 : Educ >= 2.0 ----> Question # 3 or Question # 6 ]
Question #3 : Age >= 92 ----> Question # 4 or Question # 5 ]
Question #4 : Age >= 93 ----> Question # !0 or Question # !1 ]
Question #5 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]
Question #6 : Age >= 90 ----> Question # !0 or Question # !1 ]
Question #7 : Age >= 72 ----> Question # 8 or Question # 11 ]
Question #8 : Educ >= 4.0 ----> Question # 9 or Question # 10 ]
Question #9 : Age >= 74 ----> Question # !0 or Question # !1 ]
Question #10 : Educ >= 3.0 ----> Question # !1 or Question # !0 ]
Question #11 : Educ >= 4.0 ----> Question # 12 or Question # 13 ]
Question #12 : Age >= 70 ----> Question # !1 or Question # !0 ]
Question #13 : Age >= 70 ----> Question # !0 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 6 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 5 ]
Question #2 : M/F >= 1 ----> Question # 3 or Question # 4 ]
Question #3 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #4 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #5 : M/F >= 1 ----> Question # !1 or Question # !0 ]
Question #6 : MMSE >= 25.0 ----> Question # 7 or Question # !1 ]
Question #7 : MMSE >= 26.0 ----> Question # 8 or Question # !0 ]
Question #8 : M/F >= 1 ----> Question # 9 or Question # 10 ]
Question #9 : MMSE >= 27.0 ----> Question # !1 or Question # !1 ]
Question #10 : MMSE >= 27.0 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 9 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 6 ]
Question #2 : Educ >= 5.0 ----> Question # !0 or Question # 3 ]
Question #3 : SES >= 4.0 ----> Question # 4 or Question # 5 ]
Question #4 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]
Question #5 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]
Question #6 : SES >= 4.0 ----> Question # !0 or Question # 7 ]
Question #7 : Educ >= 3.0 ----> Question # 8 or Question # !1 ]
Question #8 : Educ >= 5.0 ----> Question # !1 or Question # !0 ]
Question #9 : MMSE >= 25.0 ----> Question # 10 or Question # !1 ]
Question #10 : MMSE >= 27.0 ----> Question # 11 or Question # 14 ]
Question #11 : Educ >= 4.0 ----> Question # 12 or Question # 13 ]
Question #12 : Educ >= 5.0 ----> Question # !1 or Question # !1 ]
Question #13 : SES >= 4.0 ----> Question # !1 or Question # !1 ]
Question #14 : Educ >= 5.0 ----> Question # !1 or Question # 15 ]
Question #15 : Educ >= 4.0 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 9 ]
Question #1 : SES >= 2.0 ----> Question # 2 or Question # 7 ]
Question #2 : MMSE >= 30.0 ----> Question # 3 or Question # 5 ]
Question #3 : SES >= 3.0 ----> Question # 4 or Question # !0 ]
Question #4 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #5 : SES >= 4.0 ----> Question # !0 or Question # 6 ]
Question #6 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #7 : MMSE >= 29.0 ----> Question # 8 or Question # !0 ]
Question #8 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #9 : MMSE >= 25.0 ----> Question # 10 or Question # !1 ]
Question #10 : SES >= 4.0 ----> Question # 11 or Question # 12 ]
Question #11 : SES >= 5.0 ----> Question # !0 or Question # !0 ]
Question #12 : MMSE >= 27.0 ----> Question # 13 or Question # 14 ]
Question #13 : SES >= 3.0 ----> Question # !1 or Question # !1 ]
Question #14 : SES >= 3.0 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 6 ]
Question #1 : Age >= 66 ----> Question # 2 or Question # !0 ]
Question #2 : Age >= 87 ----> Question # !0 or Question # 3 ]
Question #3 : Age >= 76 ----> Question # 4 or Question # 5 ]
Question #4 : SES >= 4.0 ----> Question # !0 or Question # !1 ]
Question #5 : MMSE >= 29.0 ----> Question # !0 or Question # !1 ]
Question #6 : Age >= 67 ----> Question # 7 or Question # !0 ]
Question #7 : SES >= 5.0 ----> Question # !0 or Question # 8 ]
Question #8 : Age >= 89 ----> Question # 9 or Question # 10 ]
Question #9 : Age >= 96 ----> Question # !1 or Question # !0 ]
Question #10 : MMSE >= 26.0 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : M/F >= 1 ----> Question # 1 or Question # 5 ]
Question #1 : Educ >= 2.0 ----> Question # 2 or Question # !1 ]
Question #2 : Educ >= 4.0 ----> Question # 3 or Question # 4 ]
Question #3 : Educ >= 5.0 ----> Question # !0 or Question # !0 ]
Question #4 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]
Question #5 : Educ >= 5.0 ----> Question # !1 or Question # 6 ]
Question #6 : Educ >= 3.0 ----> Question # 7 or Question # 8 ]
Question #7 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #8 : Educ >= 2.0 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 9 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 6 ]
Question #2 : Educ >= 5.0 ----> Question # !0 or Question # 3 ]
Question #3 : Educ >= 2.0 ----> Question # 4 or Question # 5 ]
Question #4 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #5 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #6 : Educ >= 2.0 ----> Question # 7 or Question # !0 ]
Question #7 : Educ >= 3.0 ----> Question # 8 or Question # !0 ]
Question #8 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #9 : MMSE >= 25.0 ----> Question # 10 or Question # !1 ]
Question #10 : Educ >= 5.0 ----> Question # !1 or Question # 11 ]
Question #11 : Educ >= 3.0 ----> Question # 12 or Question # 13 ]
Question #12 : MMSE >= 27.0 ----> Question # !1 or Question # !0 ]
Question #13 : MMSE >= 26.0 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 6 ]
Question #1 : Age >= 66 ----> Question # 2 or Question # !0 ]
Question #2 : Age >= 67 ----> Question # 3 or Question # !1 ]
Question #3 : Age >= 81 ----> Question # 4 or Question # 5 ]
Question #4 : Age >= 86 ----> Question # !0 or Question # !0 ]
Question #5 : Age >= 75 ----> Question # !0 or Question # !0 ]
Question #6 : Age >= 71 ----> Question # 7 or Question # 10 ]
Question #7 : Age >= 80 ----> Question # 8 or Question # !1 ]
Question #8 : MMSE >= 25.0 ----> Question # 9 or Question # !1 ]
Question #9 : Age >= 81 ----> Question # !1 or Question # !0 ]
Question #10 : Age >= 68 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : Educ >= 3.0 ----> Question # 1 or Question # 12 ]
Question #1 : M/F >= 1 ----> Question # 2 or Question # 6 ]
Question #2 : Educ >= 5.0 ----> Question # 3 or Question # 4 ]
Question #3 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #4 : SES >= 4.0 ----> Question # !0 or Question # 5 ]
Question #5 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #6 : SES >= 3.0 ----> Question # 7 or Question # 9 ]
Question #7 : Educ >= 5.0 ----> Question # !1 or Question # 8 ]
Question #8 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #9 : Educ >= 5.0 ----> Question # 10 or Question # 11 ]
Question #10 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #11 : SES >= 2.0 ----> Question # !1 or Question # !1 ]
Question #12 : SES >= 3.0 ----> Question # 13 or Question # 19 ]
Question #13 : SES >= 4.0 ----> Question # 14 or Question # 17 ]
Question #14 : M/F >= 1 ----> Question # 15 or Question # 16 ]
Question #15 : Educ >= 2.0 ----> Question # !0 or Question # !1 ]
Question #16 : Educ >= 2.0 ----> Question # !1 or Question # !1 ]
Question #17 : Educ >= 2.0 ----> Question # 18 or Question # !0 ]
Question #18 : M/F >= 1 ----> Question # !0 or Question # !1 ]
Question #19 : M/F >= 1 ----> Question # 20 or Question # !1 ]
Question #20 : Educ >= 2.0 ----> Question # !0 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 6 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 5 ]
Question #2 : MMSE >= 30.0 ----> Question # 3 or Question # 4 ]
Question #3 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #4 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #5 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #6 : MMSE >= 25.0 ----> Question # 7 or Question # !1 ]
Question #7 : MMSE >= 27.0 ----> Question # 8 or Question # 9 ]
Question #8 : M/F >= 1 ----> Question # !1 or Question # !1 ]
Question #9 : MMSE >= 26.0 ----> Question # 10 or Question # 11 ]
Question #10 : M/F >= 1 ----> Question # !1 or Question # !1 ]
Question #11 : M/F >= 1 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 9 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 6 ]
Question #2 : Educ >= 5.0 ----> Question # !0 or Question # 3 ]
Question #3 : MMSE >= 30.0 ----> Question # 4 or Question # 5 ]
Question #4 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]
Question #5 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]
Question #6 : Educ >= 2.0 ----> Question # 7 or Question # !1 ]
Question #7 : Educ >= 3.0 ----> Question # 8 or Question # !1 ]
Question #8 : Educ >= 5.0 ----> Question # !0 or Question # !0 ]
Question #9 : MMSE >= 26.0 ----> Question # 10 or Question # !1 ]
Question #10 : Educ >= 5.0 ----> Question # 11 or Question # 12 ]
Question #11 : MMSE >= 27.0 ----> Question # !1 or Question # !1 ]
Question #12 : MMSE >= 27.0 ----> Question # 13 or Question # 14 ]
Question #13 : Educ >= 4.0 ----> Question # !1 or Question # !1 ]
Question #14 : Educ >= 4.0 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : Educ >= 2.0 ----> Question # 1 or Question # 8 ]
Question #1 : M/F >= 1 ----> Question # 2 or Question # 5 ]
Question #2 : Educ >= 3.0 ----> Question # 3 or Question # !0 ]
Question #3 : Educ >= 5.0 ----> Question # !0 or Question # 4 ]
Question #4 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #5 : Educ >= 5.0 ----> Question # !1 or Question # 6 ]
Question #6 : Educ >= 3.0 ----> Question # 7 or Question # !0 ]
Question #7 : Educ >= 4.0 ----> Question # !0 or Question # !1 ]
Question #8 : M/F >= 1 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : Educ >= 2.0 ----> Question # 1 or Question # 13 ]
Question #1 : Educ >= 5.0 ----> Question # 2 or Question # 6 ]
Question #2 : M/F >= 1 ----> Question # 3 or Question # 5 ]
Question #3 : SES >= 2.0 ----> Question # 4 or Question # !0 ]
Question #4 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #5 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #6 : M/F >= 1 ----> Question # 7 or Question # 10 ]
Question #7 : Educ >= 4.0 ----> Question # 8 or Question # 9 ]
Question #8 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #9 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #10 : SES >= 4.0 ----> Question # 11 or Question # 12 ]
Question #11 : Educ >= 4.0 ----> Question # !1 or Question # !0 ]
Question #12 : Educ >= 3.0 ----> Question # !0 or Question # !1 ]
Question #13 : SES >= 4.0 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 12 ]
Question #1 : MMSE >= 30.0 ----> Question # 2 or Question # 5 ]
Question #2 : Educ >= 5.0 ----> Question # !0 or Question # 3 ]
Question #3 : SES >= 2.0 ----> Question # 4 or Question # !1 ]
Question #4 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #5 : SES >= 3.0 ----> Question # 6 or Question # 9 ]
Question #6 : SES >= 4.0 ----> Question # 7 or Question # 8 ]
Question #7 : Educ >= 2.0 ----> Question # !0 or Question # !1 ]
Question #8 : Educ >= 3.0 ----> Question # !0 or Question # !1 ]
Question #9 : Educ >= 5.0 ----> Question # 10 or Question # 11 ]
Question #10 : MMSE >= 29.0 ----> Question # !0 or Question # !1 ]
Question #11 : MMSE >= 29.0 ----> Question # !0 or Question # !0 ]
Question #12 : MMSE >= 25.0 ----> Question # 13 or Question # !1 ]
Question #13 : Educ >= 2.0 ----> Question # 14 or Question # !0 ]
Question #14 : Educ >= 4.0 ----> Question # 15 or Question # 16 ]
Question #15 : MMSE >= 27.0 ----> Question # !1 or Question # !1 ]
Question #16 : MMSE >= 26.0 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 11 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 7 ]
Question #2 : Educ >= 5.0 ----> Question # 3 or Question # 4 ]
Question #3 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #4 : MMSE >= 30.0 ----> Question # 5 or Question # 6 ]
Question #5 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]
Question #6 : Educ >= 2.0 ----> Question # !0 or Question # !0 ]
Question #7 : Educ >= 5.0 ----> Question # !1 or Question # 8 ]
Question #8 : Educ >= 3.0 ----> Question # 9 or Question # 10 ]
Question #9 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #10 : Educ >= 2.0 ----> Question # !1 or Question # !1 ]
Question #11 : MMSE >= 26.0 ----> Question # 12 or Question # !1 ]
Question #12 : MMSE >= 27.0 ----> Question # 13 or Question # 15 ]
Question #13 : Educ >= 2.0 ----> Question # 14 or Question # !1 ]
Question #14 : Educ >= 4.0 ----> Question # !1 or Question # !0 ]
Question #15 : Educ >= 3.0 ----> Question # 16 or Question # !0 ]
Question #16 : Educ >= 5.0 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 6 ]
Question #1 : Age >= 66 ----> Question # 2 or Question # !0 ]
Question #2 : Age >= 67 ----> Question # 3 or Question # !1 ]
Question #3 : MMSE >= 29.0 ----> Question # 4 or Question # 5 ]
Question #4 : Educ >= 5.0 ----> Question # !0 or Question # !0 ]
Question #5 : Age >= 80 ----> Question # !0 or Question # !1 ]
Question #6 : MMSE >= 25.0 ----> Question # 7 or Question # !1 ]
Question #7 : Age >= 70 ----> Question # 8 or Question # !0 ]
Question #8 : Age >= 85 ----> Question # 9 or Question # 10 ]
Question #9 : MMSE >= 27.0 ----> Question # !1 or Question # !0 ]
Question #10 : Educ >= 2.0 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : SES >= 4.0 ----> Question # 1 or Question # 3 ]
Question #1 : M/F >= 1 ----> Question # 2 or Question # !1 ]
Question #2 : SES >= 5.0 ----> Question # !1 or Question # !1 ]
Question #3 : SES >= 2.0 ----> Question # 4 or Question # 7 ]
Question #4 : M/F >= 1 ----> Question # 5 or Question # 6 ]
Question #5 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #6 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #7 : M/F >= 1 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 11 ]
Question #1 : MMSE >= 30.0 ----> Question # 2 or Question # 5 ]
Question #2 : M/F >= 1 ----> Question # !0 or Question # 3 ]
Question #3 : SES >= 4.0 ----> Question # !1 or Question # 4 ]
Question #4 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #5 : SES >= 4.0 ----> Question # 6 or Question # 8 ]
Question #6 : M/F >= 1 ----> Question # 7 or Question # !0 ]
Question #7 : MMSE >= 29.0 ----> Question # !0 or Question # !0 ]
Question #8 : MMSE >= 29.0 ----> Question # 9 or Question # 10 ]
Question #9 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #10 : SES >= 2.0 ----> Question # !1 or Question # !0 ]
Question #11 : MMSE >= 26.0 ----> Question # 12 or Question # !1 ]
Question #12 : MMSE >= 27.0 ----> Question # 13 or Question # 14 ]
Question #13 : SES >= 4.0 ----> Question # !0 or Question # !1 ]
Question #14 : SES >= 4.0 ----> Question # !1 or Question # 15 ]
Question #15 : SES >= 2.0 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : Educ >= 3.0 ----> Question # 1 or Question # 6 ]
Question #1 : M/F >= 1 ----> Question # 2 or Question # 4 ]
Question #2 : Educ >= 4.0 ----> Question # 3 or Question # !0 ]
Question #3 : Educ >= 5.0 ----> Question # !0 or Question # !0 ]
Question #4 : Educ >= 4.0 ----> Question # 5 or Question # !0 ]
Question #5 : Educ >= 5.0 ----> Question # !1 or Question # !1 ]
Question #6 : M/F >= 1 ----> Question # 7 or Question # 8 ]
Question #7 : Educ >= 2.0 ----> Question # !1 or Question # !1 ]
Question #8 : Educ >= 2.0 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 10 ]
Question #1 : MMSE >= 30.0 ----> Question # 2 or Question # 5 ]
Question #2 : SES >= 2.0 ----> Question # 3 or Question # !0 ]
Question #3 : SES >= 3.0 ----> Question # 4 or Question # !0 ]
Question #4 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #5 : SES >= 4.0 ----> Question # 6 or Question # 7 ]
Question #6 : MMSE >= 29.0 ----> Question # !0 or Question # !0 ]
Question #7 : SES >= 2.0 ----> Question # 8 or Question # 9 ]
Question #8 : MMSE >= 29.0 ----> Question # !0 or Question # !0 ]
Question #9 : MMSE >= 29.0 ----> Question # !0 or Question # !0 ]
Question #10 : MMSE >= 25.0 ----> Question # 11 or Question # !1 ]
Question #11 : SES >= 3.0 ----> Question # 12 or Question # 14 ]
Question #12 : MMSE >= 26.0 ----> Question # 13 or Question # !0 ]
Question #13 : SES >= 5.0 ----> Question # !0 or Question # !0 ]
Question #14 : SES >= 2.0 ----> Question # !1 or Question # 15 ]
Question #15 : MMSE >= 26.0 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : Age >= 66 ----> Question # 1 or Question # 11 ]
Question #1 : Age >= 89 ----> Question # 2 or Question # 5 ]
Question #2 : M/F >= 1 ----> Question # 3 or Question # !1 ]
Question #3 : Age >= 92 ----> Question # 4 or Question # !0 ]
Question #4 : Age >= 93 ----> Question # !0 or Question # !1 ]
Question #5 : M/F >= 1 ----> Question # 6 or Question # 9 ]
Question #6 : SES >= 3.0 ----> Question # 7 or Question # 8 ]
Question #7 : Age >= 75 ----> Question # !1 or Question # !0 ]
Question #8 : Age >= 71 ----> Question # !0 or Question # !0 ]
Question #9 : Age >= 87 ----> Question # !0 or Question # 10 ]
Question #10 : Age >= 81 ----> Question # !1 or Question # !1 ]
Question #11 : Age >= 64 ----> Question # 12 or Question # !0 ]
Question #12 : M/F >= 1 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : Age >= 64 ----> Question # 1 or Question # !0 ]
Question #1 : Age >= 88 ----> Question # 2 or Question # 5 ]
Question #2 : Age >= 96 ----> Question # !1 or Question # 3 ]
Question #3 : SES >= 4.0 ----> Question # !1 or Question # 4 ]
Question #4 : Age >= 90 ----> Question # !0 or Question # !0 ]
Question #5 : Age >= 77 ----> Question # 6 or Question # 9 ]
Question #6 : SES >= 3.0 ----> Question # 7 or Question # 8 ]
Question #7 : Age >= 84 ----> Question # !1 or Question # !1 ]
Question #8 : Age >= 83 ----> Question # !1 or Question # !0 ]
Question #9 : Age >= 76 ----> Question # !0 or Question # 10 ]
Question #10 : Age >= 67 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : M/F >= 1 ----> Question # 1 or Question # 5 ]
Question #1 : SES >= 3.0 ----> Question # 2 or Question # 4 ]
Question #2 : SES >= 5.0 ----> Question # !0 or Question # 3 ]
Question #3 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #4 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #5 : SES >= 3.0 ----> Question # 6 or Question # 7 ]
Question #6 : SES >= 4.0 ----> Question # !0 or Question # !1 ]
Question #7 : SES >= 2.0 ----> Question # !0 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 13 ]
Question #1 : MMSE >= 30.0 ----> Question # 2 or Question # 7 ]
Question #2 : M/F >= 1 ----> Question # 3 or Question # 5 ]
Question #3 : SES >= 3.0 ----> Question # !0 or Question # 4 ]
Question #4 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #5 : SES >= 2.0 ----> Question # 6 or Question # !0 ]
Question #6 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #7 : M/F >= 1 ----> Question # 8 or Question # 11 ]
Question #8 : SES >= 3.0 ----> Question # 9 or Question # 10 ]
Question #9 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #10 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #11 : SES >= 4.0 ----> Question # !0 or Question # 12 ]
Question #12 : MMSE >= 29.0 ----> Question # !0 or Question # !1 ]
Question #13 : MMSE >= 26.0 ----> Question # 14 or Question # !1 ]
Question #14 : SES >= 5.0 ----> Question # !0 or Question # 15 ]
Question #15 : MMSE >= 27.0 ----> Question # 16 or Question # 17 ]
Question #16 : SES >= 4.0 ----> Question # !1 or Question # !1 ]
Question #17 : SES >= 2.0 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : SES >= 4.0 ----> Question # 1 or Question # 3 ]
Question #1 : M/F >= 1 ----> Question # 2 or Question # !0 ]
Question #2 : SES >= 5.0 ----> Question # !1 or Question # !1 ]
Question #3 : M/F >= 1 ----> Question # 4 or Question # 6 ]
Question #4 : SES >= 3.0 ----> Question # !0 or Question # 5 ]
Question #5 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #6 : SES >= 3.0 ----> Question # !0 or Question # 7 ]
Question #7 : SES >= 2.0 ----> Question # !0 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 8 ]
Question #1 : Age >= 66 ----> Question # 2 or Question # !0 ]
Question #2 : Age >= 74 ----> Question # 3 or Question # 5 ]
Question #3 : Age >= 85 ----> Question # !0 or Question # 4 ]
Question #4 : Age >= 75 ----> Question # !0 or Question # !0 ]
Question #5 : MMSE >= 30.0 ----> Question # 6 or Question # 7 ]
Question #6 : Age >= 67 ----> Question # !0 or Question # !1 ]
Question #7 : MMSE >= 29.0 ----> Question # !1 or Question # !1 ]
Question #8 : MMSE >= 26.0 ----> Question # 9 or Question # !1 ]
Question #9 : Age >= 70 ----> Question # 10 or Question # !0 ]
Question #10 : SES >= 5.0 ----> Question # !0 or Question # 11 ]
Question #11 : Age >= 78 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 14 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 9 ]
Question #2 : M/F >= 1 ----> Question # 3 or Question # 6 ]
Question #3 : MMSE >= 30.0 ----> Question # 4 or Question # 5 ]
Question #4 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #5 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #6 : SES >= 2.0 ----> Question # 7 or Question # 8 ]
Question #7 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #8 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #9 : SES >= 4.0 ----> Question # 10 or Question # 11 ]
Question #10 : M/F >= 1 ----> Question # !1 or Question # !0 ]
Question #11 : SES >= 2.0 ----> Question # 12 or Question # 13 ]
Question #12 : M/F >= 1 ----> Question # !1 or Question # !1 ]
Question #13 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #14 : MMSE >= 26.0 ----> Question # 15 or Question # !1 ]
Question #15 : MMSE >= 27.0 ----> Question # 16 or Question # 18 ]
Question #16 : SES >= 4.0 ----> Question # 17 or Question # !1 ]
Question #17 : M/F >= 1 ----> Question # !0 or Question # !1 ]
Question #18 : SES >= 4.0 ----> Question # !1 or Question # 19 ]
Question #19 : SES >= 3.0 ----> Question # !0 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 8 ]
Question #1 : Age >= 66 ----> Question # 2 or Question # !0 ]
Question #2 : Age >= 82 ----> Question # 3 or Question # 5 ]
Question #3 : Educ >= 4.0 ----> Question # !0 or Question # 4 ]
Question #4 : Age >= 88 ----> Question # !0 or Question # !0 ]
Question #5 : MMSE >= 30.0 ----> Question # 6 or Question # 7 ]
Question #6 : Age >= 67 ----> Question # !0 or Question # !1 ]
Question #7 : Educ >= 3.0 ----> Question # !0 or Question # !1 ]
Question #8 : MMSE >= 25.0 ----> Question # 9 or Question # !1 ]
Question #9 : Age >= 71 ----> Question # 10 or Question # !0 ]
Question #10 : Age >= 85 ----> Question # 11 or Question # 12 ]
Question #11 : Age >= 92 ----> Question # !1 or Question # !0 ]
Question #12 : Educ >= 2.0 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : MMSE >= 27.0 ----> Question # 1 or Question # 11 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 5 ]
Question #2 : Age >= 66 ----> Question # 3 or Question # !0 ]
Question #3 : Age >= 85 ----> Question # !0 or Question # 4 ]
Question #4 : Age >= 84 ----> Question # !1 or Question # !0 ]
Question #5 : Age >= 76 ----> Question # 6 or Question # 8 ]
Question #6 : MMSE >= 28.0 ----> Question # !0 or Question # 7 ]
Question #7 : Age >= 89 ----> Question # !0 or Question # !1 ]
Question #8 : Age >= 73 ----> Question # 9 or Question # 10 ]
Question #9 : Age >= 75 ----> Question # !1 or Question # !1 ]
Question #10 : Age >= 72 ----> Question # !0 or Question # !1 ]
Question #11 : MMSE >= 25.0 ----> Question # 12 or Question # !1 ]
Question #12 : Age >= 74 ----> Question # 13 or Question # !1 ]
Question #13 : Age >= 88 ----> Question # 14 or Question # 15 ]
Question #14 : MMSE >= 26.0 ----> Question # !1 or Question # !0 ]
Question #15 : Age >= 85 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 11 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 8 ]
Question #2 : MMSE >= 30.0 ----> Question # 3 or Question # 5 ]
Question #3 : SES >= 2.0 ----> Question # 4 or Question # !0 ]
Question #4 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #5 : SES >= 3.0 ----> Question # 6 or Question # 7 ]
Question #6 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #7 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #8 : SES >= 3.0 ----> Question # 9 or Question # 10 ]
Question #9 : SES >= 4.0 ----> Question # !0 or Question # !1 ]
Question #10 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #11 : MMSE >= 25.0 ----> Question # 12 or Question # !1 ]
Question #12 : SES >= 4.0 ----> Question # 13 or Question # 14 ]
Question #13 : MMSE >= 27.0 ----> Question # !1 or Question # !1 ]
Question #14 : SES >= 2.0 ----> Question # 15 or Question # 16 ]
Question #15 : MMSE >= 26.0 ----> Question # !1 or Question # !0 ]
Question #16 : MMSE >= 26.0 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 6 ]
Question #1 : Educ >= 2.0 ----> Question # 2 or Question # 5 ]
Question #2 : Age >= 66 ----> Question # 3 or Question # !0 ]
Question #3 : Age >= 67 ----> Question # 4 or Question # !1 ]
Question #4 : Age >= 81 ----> Question # !0 or Question # !0 ]
Question #5 : Age >= 81 ----> Question # !0 or Question # !1 ]
Question #6 : MMSE >= 26.0 ----> Question # 7 or Question # !1 ]
Question #7 : Age >= 70 ----> Question # 8 or Question # !0 ]
Question #8 : Educ >= 5.0 ----> Question # !1 or Question # 9 ]
Question #9 : Age >= 89 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 15 ]
Question #1 : SES >= 3.0 ----> Question # 2 or Question # 8 ]
Question #2 : SES >= 4.0 ----> Question # 3 or Question # 5 ]
Question #3 : M/F >= 1 ----> Question # 4 or Question # !0 ]
Question #4 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #5 : MMSE >= 30.0 ----> Question # 6 or Question # 7 ]
Question #6 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #7 : M/F >= 1 ----> Question # !0 or Question # !1 ]
Question #8 : M/F >= 1 ----> Question # 9 or Question # 12 ]
Question #9 : MMSE >= 29.0 ----> Question # 10 or Question # 11 ]
Question #10 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #11 : SES >= 2.0 ----> Question # !0 or Question # !1 ]
Question #12 : SES >= 2.0 ----> Question # 13 or Question # 14 ]
Question #13 : MMSE >= 29.0 ----> Question # !0 or Question # !1 ]
Question #14 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #15 : MMSE >= 25.0 ----> Question # 16 or Question # !1 ]
Question #16 : SES >= 4.0 ----> Question # 17 or Question # 19 ]
Question #17 : MMSE >= 27.0 ----> Question # 18 or Question # !1 ]
Question #18 : M/F >= 1 ----> Question # !1 or Question # !1 ]
Question #19 : SES >= 3.0 ----> Question # 20 or Question # 21 ]
Question #20 : MMSE >= 27.0 ----> Question # !1 or Question # !0 ]
Question #21 : M/F >= 1 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : MMSE >= 29.0 ----> Question # 1 or Question # 8 ]
Question #1 : SES >= 4.0 ----> Question # 2 or Question # 3 ]
Question #2 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #3 : SES >= 3.0 ----> Question # 4 or Question # 5 ]
Question #4 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #5 : SES >= 2.0 ----> Question # 6 or Question # 7 ]
Question #6 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #7 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #8 : MMSE >= 25.0 ----> Question # 9 or Question # !1 ]
Question #9 : SES >= 3.0 ----> Question # 10 or Question # 13 ]
Question #10 : MMSE >= 27.0 ----> Question # 11 or Question # 12 ]
Question #11 : SES >= 5.0 ----> Question # !0 or Question # !1 ]
Question #12 : SES >= 4.0 ----> Question # !1 or Question # !0 ]
Question #13 : MMSE >= 28.0 ----> Question # 14 or Question # 15 ]
Question #14 : SES >= 2.0 ----> Question # !1 or Question # !1 ]
Question #15 : SES >= 2.0 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 6 ]
Question #1 : MMSE >= 30.0 ----> Question # 2 or Question # 3 ]
Question #2 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #3 : M/F >= 1 ----> Question # 4 or Question # 5 ]
Question #4 : MMSE >= 29.0 ----> Question # !0 or Question # !0 ]
Question #5 : MMSE >= 29.0 ----> Question # !0 or Question # !0 ]
Question #6 : MMSE >= 25.0 ----> Question # 7 or Question # !1 ]
Question #7 : MMSE >= 27.0 ----> Question # 8 or Question # 9 ]
Question #8 : M/F >= 1 ----> Question # !1 or Question # !1 ]
Question #9 : M/F >= 1 ----> Question # 10 or Question # 11 ]
Question #10 : MMSE >= 26.0 ----> Question # !1 or Question # !1 ]
Question #11 : MMSE >= 26.0 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : Age >= 64 ----> Question # 1 or Question # !0 ]
Question #1 : M/F >= 1 ----> Question # 2 or Question # 8 ]
Question #2 : Educ >= 2.0 ----> Question # 3 or Question # 6 ]
Question #3 : Age >= 72 ----> Question # 4 or Question # 5 ]
Question #4 : Age >= 80 ----> Question # !0 or Question # !1 ]
Question #5 : Educ >= 3.0 ----> Question # !0 or Question # !1 ]
Question #6 : Age >= 83 ----> Question # !1 or Question # 7 ]
Question #7 : Age >= 80 ----> Question # !0 or Question # !1 ]
Question #8 : Educ >= 4.0 ----> Question # 9 or Question # 11 ]
Question #9 : Age >= 84 ----> Question # !0 or Question # 10 ]
Question #10 : Educ >= 5.0 ----> Question # !1 or Question # !1 ]
Question #11 : Age >= 82 ----> Question # 12 or Question # 13 ]
Question #12 : Age >= 90 ----> Question # !1 or Question # !1 ]
Question #13 : Age >= 72 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : MMSE >= 25.0 ----> Question # 1 or Question # !1 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 5 ]
Question #2 : M/F >= 1 ----> Question # 3 or Question # 4 ]
Question #3 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #4 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #5 : MMSE >= 27.0 ----> Question # 6 or Question # 9 ]
Question #6 : M/F >= 1 ----> Question # 7 or Question # 8 ]
Question #7 : MMSE >= 28.0 ----> Question # !0 or Question # !0 ]
Question #8 : MMSE >= 28.0 ----> Question # !0 or Question # !1 ]
Question #9 : M/F >= 1 ----> Question # 10 or Question # 11 ]
Question #10 : MMSE >= 26.0 ----> Question # !1 or Question # !1 ]
Question #11 : MMSE >= 26.0 ----> Question # !0 or Question # !0 ]""",

                """
Question #0 : MMSE >= 27.0 ----> Question # 1 or Question # 9 ]
Question #1 : Age >= 66 ----> Question # 2 or Question # !0 ]
Question #2 : Age >= 71 ----> Question # 3 or Question # 6 ]
Question #3 : MMSE >= 29.0 ----> Question # 4 or Question # 5 ]
Question #4 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #5 : Age >= 89 ----> Question # !0 or Question # !0 ]
Question #6 : Age >= 67 ----> Question # 7 or Question # 8 ]
Question #7 : Age >= 68 ----> Question # !1 or Question # !0 ]
Question #8 : MMSE >= 30.0 ----> Question # !1 or Question # !0 ]
Question #9 : MMSE >= 26.0 ----> Question # 10 or Question # !1 ]
Question #10 : Age >= 70 ----> Question # 11 or Question # !0 ]
Question #11 : Age >= 85 ----> Question # 12 or Question # 13 ]
Question #12 : Age >= 88 ----> Question # !1 or Question # !0 ]
Question #13 : Age >= 75 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : Age >= 67 ----> Question # 1 or Question # 15 ]
Question #1 : Age >= 81 ----> Question # 2 or Question # 8 ]
Question #2 : SES >= 4.0 ----> Question # 3 or Question # 5 ]
Question #3 : Age >= 86 ----> Question # 4 or Question # !0 ]
Question #4 : Educ >= 2.0 ----> Question # !0 or Question # !1 ]
Question #5 : Age >= 92 ----> Question # 6 or Question # 7 ]
Question #6 : Educ >= 4.0 ----> Question # !1 or Question # !0 ]
Question #7 : Age >= 85 ----> Question # !0 or Question # !0 ]
Question #8 : Age >= 76 ----> Question # 9 or Question # 12 ]
Question #9 : Educ >= 2.0 ----> Question # 10 or Question # 11 ]
Question #10 : SES >= 3.0 ----> Question # !1 or Question # !1 ]
Question #11 : Age >= 78 ----> Question # !1 or Question # !0 ]
Question #12 : Educ >= 5.0 ----> Question # 13 or Question # 14 ]
Question #13 : Age >= 74 ----> Question # !0 or Question # !0 ]
Question #14 : Educ >= 4.0 ----> Question # !1 or Question # !0 ]
Question #15 : Age >= 64 ----> Question # 16 or Question # !0 ]
Question #16 : Age >= 65 ----> Question # !0 or Question # 17 ]
Question #17 : Educ >= 5.0 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : Age >= 66 ----> Question # 1 or Question # 11 ]
Question #1 : Educ >= 5.0 ----> Question # 2 or Question # 5 ]
Question #2 : Age >= 92 ----> Question # !1 or Question # 3 ]
Question #3 : Age >= 82 ----> Question # !0 or Question # 4 ]
Question #4 : Age >= 77 ----> Question # !0 or Question # !0 ]
Question #5 : Age >= 88 ----> Question # 6 or Question # 8 ]
Question #6 : SES >= 4.0 ----> Question # !1 or Question # 7 ]
Question #7 : Age >= 90 ----> Question # !0 or Question # !0 ]
Question #8 : Age >= 69 ----> Question # 9 or Question # 10 ]
Question #9 : Age >= 71 ----> Question # !1 or Question # !1 ]
Question #10 : Age >= 68 ----> Question # !0 or Question # !1 ]
Question #11 : Age >= 64 ----> Question # 12 or Question # !0 ]
Question #12 : Educ >= 5.0 ----> Question # 13 or Question # !0 ]
Question #13 : Age >= 65 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : Educ >= 5.0 ----> Question # 1 or Question # 2 ]
Question #1 : M/F >= 1 ----> Question # !0 or Question # !0 ]
Question #2 : M/F >= 1 ----> Question # 3 or Question # 6 ]
Question #3 : Educ >= 2.0 ----> Question # 4 or Question # !1 ]
Question #4 : Educ >= 4.0 ----> Question # !0 or Question # 5 ]
Question #5 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]
Question #6 : Educ >= 2.0 ----> Question # 7 or Question # !0 ]
Question #7 : Educ >= 4.0 ----> Question # !1 or Question # 8 ]
Question #8 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 14 ]
Question #1 : M/F >= 1 ----> Question # 2 or Question # 8 ]
Question #2 : Educ >= 3.0 ----> Question # 3 or Question # 5 ]
Question #3 : MMSE >= 30.0 ----> Question # !0 or Question # 4 ]
Question #4 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #5 : Educ >= 2.0 ----> Question # 6 or Question # 7 ]
Question #6 : MMSE >= 29.0 ----> Question # !0 or Question # !0 ]
Question #7 : MMSE >= 30.0 ----> Question # !0 or Question # !1 ]
Question #8 : Educ >= 4.0 ----> Question # 9 or Question # 12 ]
Question #9 : MMSE >= 30.0 ----> Question # 10 or Question # 11 ]
Question #10 : Educ >= 5.0 ----> Question # !0 or Question # !0 ]
Question #11 : Educ >= 5.0 ----> Question # !1 or Question # !0 ]
Question #12 : MMSE >= 29.0 ----> Question # 13 or Question # !0 ]
Question #13 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #14 : MMSE >= 25.0 ----> Question # 15 or Question # !1 ]
Question #15 : Educ >= 5.0 ----> Question # 16 or Question # 18 ]
Question #16 : MMSE >= 27.0 ----> Question # 17 or Question # !1 ]
Question #17 : M/F >= 1 ----> Question # !0 or Question # !1 ]
Question #18 : MMSE >= 27.0 ----> Question # 19 or Question # 20 ]
Question #19 : Educ >= 2.0 ----> Question # !1 or Question # !1 ]
Question #20 : M/F >= 1 ----> Question # !0 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 13 ]
Question #1 : MMSE >= 29.0 ----> Question # 2 or Question # 9 ]
Question #2 : M/F >= 1 ----> Question # 3 or Question # 6 ]
Question #3 : SES >= 3.0 ----> Question # 4 or Question # 5 ]
Question #4 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #5 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #6 : SES >= 2.0 ----> Question # 7 or Question # 8 ]
Question #7 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #8 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #9 : SES >= 4.0 ----> Question # 10 or Question # 11 ]
Question #10 : M/F >= 1 ----> Question # !1 or Question # !0 ]
Question #11 : SES >= 2.0 ----> Question # 12 or Question # !0 ]
Question #12 : M/F >= 1 ----> Question # !1 or Question # !1 ]
Question #13 : MMSE >= 26.0 ----> Question # 14 or Question # !1 ]
Question #14 : SES >= 3.0 ----> Question # 15 or Question # 18 ]
Question #15 : MMSE >= 27.0 ----> Question # 16 or Question # 17 ]
Question #16 : M/F >= 1 ----> Question # !0 or Question # !1 ]
Question #17 : SES >= 4.0 ----> Question # !1 or Question # !0 ]
Question #18 : M/F >= 1 ----> Question # !1 or Question # 19 ]
Question #19 : SES >= 2.0 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : MMSE >= 29.0 ----> Question # 1 or Question # 4 ]
Question #1 : M/F >= 1 ----> Question # 2 or Question # 3 ]
Question #2 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #3 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #4 : MMSE >= 25.0 ----> Question # 5 or Question # !1 ]
Question #5 : M/F >= 1 ----> Question # 6 or Question # 8 ]
Question #6 : MMSE >= 28.0 ----> Question # !1 or Question # 7 ]
Question #7 : MMSE >= 26.0 ----> Question # !0 or Question # !1 ]
Question #8 : MMSE >= 26.0 ----> Question # 9 or Question # !0 ]
Question #9 : MMSE >= 28.0 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : MMSE >= 27.0 ----> Question # 1 or Question # 12 ]
Question #1 : M/F >= 1 ----> Question # 2 or Question # 7 ]
Question #2 : MMSE >= 29.0 ----> Question # 3 or Question # 5 ]
Question #3 : SES >= 3.0 ----> Question # 4 or Question # !0 ]
Question #4 : MMSE >= 30.0 ----> Question # !0 or Question # !0 ]
Question #5 : SES >= 5.0 ----> Question # !0 or Question # 6 ]
Question #6 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #7 : MMSE >= 28.0 ----> Question # 8 or Question # 11 ]
Question #8 : MMSE >= 30.0 ----> Question # 9 or Question # 10 ]
Question #9 : SES >= 2.0 ----> Question # !0 or Question # !1 ]
Question #10 : SES >= 4.0 ----> Question # !0 or Question # !0 ]
Question #11 : SES >= 3.0 ----> Question # !1 or Question # !1 ]
Question #12 : MMSE >= 25.0 ----> Question # 13 or Question # !1 ]
Question #13 : SES >= 3.0 ----> Question # 14 or Question # 15 ]
Question #14 : SES >= 4.0 ----> Question # !1 or Question # !0 ]
Question #15 : SES >= 2.0 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : Educ >= 2.0 ----> Question # 1 or Question # 12 ]
Question #1 : M/F >= 1 ----> Question # 2 or Question # 7 ]
Question #2 : SES >= 4.0 ----> Question # 3 or Question # 4 ]
Question #3 : Educ >= 3.0 ----> Question # !0 or Question # !1 ]
Question #4 : Educ >= 4.0 ----> Question # 5 or Question # 6 ]
Question #5 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #6 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #7 : SES >= 4.0 ----> Question # 8 or Question # 9 ]
Question #8 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]
Question #9 : Educ >= 3.0 ----> Question # 10 or Question # 11 ]
Question #10 : Educ >= 4.0 ----> Question # !0 or Question # !1 ]
Question #11 : SES >= 3.0 ----> Question # !1 or Question # !1 ]
Question #12 : SES >= 4.0 ----> Question # 13 or Question # !0 ]
Question #13 : SES >= 5.0 ----> Question # !0 or Question # 14 ]
Question #14 : M/F >= 1 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : Educ >= 2.0 ----> Question # 1 or Question # 8 ]
Question #1 : M/F >= 1 ----> Question # 2 or Question # 5 ]
Question #2 : Educ >= 4.0 ----> Question # 3 or Question # 4 ]
Question #3 : Educ >= 5.0 ----> Question # !0 or Question # !0 ]
Question #4 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]
Question #5 : Educ >= 3.0 ----> Question # 6 or Question # !1 ]
Question #6 : Educ >= 5.0 ----> Question # !0 or Question # 7 ]
Question #7 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #8 : M/F >= 1 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : Educ >= 3.0 ----> Question # 1 or Question # 11 ]
Question #1 : M/F >= 1 ----> Question # 2 or Question # 6 ]
Question #2 : Educ >= 5.0 ----> Question # 3 or Question # 4 ]
Question #3 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #4 : SES >= 4.0 ----> Question # !0 or Question # 5 ]
Question #5 : Educ >= 4.0 ----> Question # !0 or Question # !0 ]
Question #6 : SES >= 2.0 ----> Question # 7 or Question # 10 ]
Question #7 : SES >= 3.0 ----> Question # 8 or Question # 9 ]
Question #8 : Educ >= 4.0 ----> Question # !1 or Question # !0 ]
Question #9 : Educ >= 4.0 ----> Question # !0 or Question # !1 ]
Question #10 : Educ >= 5.0 ----> Question # !0 or Question # !1 ]
Question #11 : M/F >= 1 ----> Question # 12 or Question # 16 ]
Question #12 : SES >= 4.0 ----> Question # 13 or Question # 14 ]
Question #13 : Educ >= 2.0 ----> Question # !1 or Question # !1 ]
Question #14 : Educ >= 2.0 ----> Question # 15 or Question # !0 ]
Question #15 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #16 : SES >= 4.0 ----> Question # 17 or Question # 18 ]
Question #17 : Educ >= 2.0 ----> Question # !0 or Question # !0 ]
Question #18 : Educ >= 2.0 ----> Question # 19 or Question # !0 ]
Question #19 : SES >= 3.0 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : M/F >= 1 ----> Question # 1 or Question # 5 ]
Question #1 : SES >= 3.0 ----> Question # 2 or Question # 4 ]
Question #2 : SES >= 5.0 ----> Question # !0 or Question # 3 ]
Question #3 : SES >= 4.0 ----> Question # !1 or Question # !0 ]
Question #4 : SES >= 2.0 ----> Question # !0 or Question # !0 ]
Question #5 : SES >= 2.0 ----> Question # 6 or Question # !1 ]
Question #6 : SES >= 4.0 ----> Question # !1 or Question # 7 ]
Question #7 : SES >= 3.0 ----> Question # !1 or Question # !1 ]""",

                """
Question #0 : Age >= 64 ----> Question # 1 or Question # !0 ]
Question #1 : Age >= 69 ----> Question # 2 or Question # 9 ]
Question #2 : Age >= 89 ----> Question # 3 or Question # 6 ]
Question #3 : Age >= 92 ----> Question # 4 or Question # 5 ]
Question #4 : Age >= 93 ----> Question # !0 or Question # !1 ]
Question #5 : SES >= 4.0 ----> Question # !1 or Question # !0 ]
Question #6 : SES >= 3.0 ----> Question # 7 or Question # 8 ]
Question #7 : Age >= 71 ----> Question # !1 or Question # !1 ]
Question #8 : Age >= 75 ----> Question # !1 or Question # !0 ]
Question #9 : Age >= 67 ----> Question # 10 or Question # 11 ]
Question #10 : SES >= 3.0 ----> Question # !0 or Question # !0 ]
Question #11 : Age >= 66 ----> Question # !1 or Question # 12 ]
Question #12 : Age >= 65 ----> Question # !0 or Question # !1 ]""",

                """
Question #0 : Age >= 64 ----> Question # 1 or Question # !0 ]
Question #1 : Educ >= 3.0 ----> Question # 2 or Question # 8 ]
Question #2 : Age >= 85 ----> Question # 3 or Question # 5 ]
Question #3 : Age >= 92 ----> Question # 4 or Question # !0 ]
Question #4 : Age >= 93 ----> Question # !0 or Question # !1 ]
Question #5 : Age >= 71 ----> Question # 6 or Question # 7 ]
Question #6 : SES >= 4.0 ----> Question # !0 or Question # !1 ]
Question #7 : Age >= 65 ----> Question # !0 or Question # !1 ]
Question #8 : Age >= 75 ----> Question # 9 or Question # 12 ]
Question #9 : Age >= 80 ----> Question # 10 or Question # 11 ]
Question #10 : Age >= 82 ----> Question # !1 or Question # !0 ]
Question #11 : Age >= 78 ----> Question # !1 or Question # !1 ]
Question #12 : Age >= 67 ----> Question # 13 or Question # 14 ]
Question #13 : SES >= 4.0 ----> Question # !1 or Question # !0 ]
Question #14 : Age >= 66 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : MMSE >= 28.0 ----> Question # 1 or Question # 7 ]
Question #1 : Age >= 66 ----> Question # 2 or Question # !0 ]
Question #2 : Age >= 67 ----> Question # 3 or Question # 6 ]
Question #3 : MMSE >= 30.0 ----> Question # 4 or Question # 5 ]
Question #4 : Age >= 69 ----> Question # !0 or Question # !0 ]
Question #5 : Educ >= 3.0 ----> Question # !0 or Question # !0 ]
Question #6 : Educ >= 5.0 ----> Question # !0 or Question # !1 ]
Question #7 : Age >= 64 ----> Question # 8 or Question # !0 ]
Question #8 : MMSE >= 25.0 ----> Question # 9 or Question # !1 ]
Question #9 : Age >= 89 ----> Question # !0 or Question # 10 ]
Question #10 : Age >= 70 ----> Question # !1 or Question # !0 ]""",

                """
Question #0 : Age >= 69 ----> Question # 1 or Question # 11 ]
Question #1 : Age >= 89 ----> Question # 2 or Question # 4 ]
Question #2 : Age >= 92 ----> Question # 3 or Question # !0 ]
Question #3 : Age >= 94 ----> Question # !0 or Question # !1 ]
Question #4 : Educ >= 5.0 ----> Question # 5 or Question # 8 ]
Question #5 : M/F >= 1 ----> Question # 6 or Question # 7 ]
Question #6 : Age >= 74 ----> Question # !0 or Question # !0 ]
Question #7 : Age >= 77 ----> Question # !1 or Question # !0 ]
Question #8 : Age >= 75 ----> Question # 9 or Question # 10 ]
Question #9 : Educ >= 3.0 ----> Question # !0 or Question # !1 ]
Question #10 : Educ >= 4.0 ----> Question # !1 or Question # !0 ]
Question #11 : Educ >= 2.0 ----> Question # 12 or Question # !1 ]
Question #12 : Age >= 66 ----> Question # 13 or Question # !0 ]
Question #13 : Age >= 67 ----> Question # !0 or Question # 14 ]
Question #14 : Educ >= 5.0 ----> Question # !0 or Question # !1 ]"""
        };
        return strForest;
    }

    // Converts strings into decision, predicts based on votes.
    static String initTree(HashMap<String, String> features){
        String[] strForest = getStringTree();

        Tree[] forest = new Tree[101];
        for (int i = 0; i < forest.length; i++){
            forest[i] = new Tree(features, strForest[i]);
        }

        ArrayList<Boolean> forestVoting = new ArrayList<>();

        for (Tree tree : forest){
            forestVoting.add(tree.alzPredict(0));
        }
        int trueValue = 0;
        int falseValue = 0;

        for (boolean prediction : forestVoting){
            if (prediction){
                trueValue++;
            }
            else {
                falseValue++;
            }
        }

        return (trueValue > falseValue) ? "im " + trueValue/101.0 * 100 + "% sure you're NON-DEMENTED" : "im " + falseValue/101.0 * 100 + "% sure you're DEMENTED";

    }

    // Calls initTree
    static String alziheimersPredict(String age, String gender, String EDUC, String SES, String MMSE){
        HashMap<String, String> features = new HashMap<>();
        features.put("Age", age); // Age is locked on mean training data age
        features.put("MMSE", MMSE);
        features.put("SES", SES);
        features.put("Educ", EDUC);
        features.put("M/F", gender);

        return initTree(features);
    }

}
