package fr.ip.model.util;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;


public class QuestionBank {

    private ArrayList<Question> questions;
    private Level level;
    private File folder;

    public QuestionBank(Level level) {
        SETTINGS settings = new SETTINGS();

        if(settings.isSetting("ANY_LEVEL_OVERRIDE"))
            this.level = (boolean) settings.getSetting("ANY_LEVEL_OVERRIDE").value() ? Level.ANY : level;
        else {
            System.out.println("");
        }
        try {
            this.folder = new File((String) settings.getSetting("PATH_TO_QUESTIONS_FOLDER").value());
        } catch (NullPointerException e) {
            new Message(MessageType.ERROR, "Le dossier des questions est introuvable, existe-t-il ?").print();
            System.exit(2);
        }
        File[] fileList = folder.listFiles();
        if(fileList == null) {
            new Message(MessageType.ERROR, "Aucun fichier dans le dossier questions.").print();
            System.exit(3);
        }
    }

    public QuestionBank() {
        this(Level.ANY);
    }

    private ArrayList<Question> parse(File[] fs) {
        ArrayList<Question> list = new ArrayList<>();
        ArrayList<Question> parsed;

        for(File f : fs) {
            parsed = parse(f);
            if(parsed != null) {
                list.addAll(parsed);
                parsed = null;
            }
        }

        return list;
    }

    private ArrayList<Question> parse(File f) {
        Scanner sc;
        String canonicalPath;

        try {
            canonicalPath = f.getCanonicalPath();
            new Message(MessageType.INFORMATION, "Opening" + canonicalPath).print();
            sc = new Scanner(f);
        } catch (IOException e) {
            new Message(MessageType.ERROR, "Wrong file path").print();
            return null;
        }

        ArrayList<Question> qs = new ArrayList<>();
        Scanner line;
        String sLine;
        Question q = null;
        boolean valid = true;
        Question.QuestionType questionType;
        int lineNb = 0;

        questionType = parseQuestionType(sc, lineNb, canonicalPath);
        if(questionType == Question.QuestionType.NONE)
            return null;

        while(sc.hasNextLine()) {


            sLine = sc.nextLine();

            if(!valid && StringUtil.isEmpty(sLine))
                valid = true;
            else
                continue;
            if(StringUtil.removeWhiteSpace(sLine).startsWith("**"))
                continue; // Checks if comment line.
            if(StringUtil.removeWhiteSpace(sLine).startsWith("[") && !isRightLevel(levelFromLine(sLine))) {
                valid = false;
                continue;
            }



            if(q != null)
                qs.add(q);

        }

        return qs;
    }

    private Question.QuestionType parseQuestionType(Scanner sc, int lineNb, String path) {
        Scanner line;
        String sLine;
        Question.QuestionType questionType;

        while(sc.hasNextLine()) {
            sLine = sc.nextLine();
            lineNb ++;
            if(StringUtil.removeWhiteSpace(sLine).startsWith("!!")) {
                line = new Scanner(StringUtil.removeWhiteSpace(sLine));
                line.useDelimiter("!!");
                questionType = Question.QuestionType.questionTypeFromString(line.next());

                if(questionType != Question.QuestionType.NONE) {
                    new Message(MessageType.INFORMATION, path + " : " + questionType.toString() + " questions file.").print();
                    return questionType;
                } else
                    new Message(MessageType.WARNING, "Question type not recognized at line " + lineNb + ".").print();
            }
        }

        new Message(MessageType.ERROR, path + " : No question type for file.").print();
        return Question.QuestionType.NONE;
    }

    private Level levelFromLine(String line) {
        String sLevel = StringUtil.removeWhiteSpace(line);
        sLevel = sLevel.substring(1, sLevel.length() - 2);

        new Message(MessageType.INFORMATION, "Ligne de niveau minimisée: " + sLevel).print();

        return Level.levelFromString(sLevel);
    }

    public boolean isRightLevel(Level l) {
        if(l == null) return false;
        if(this.level == Level.ANY || l == Level.ANY) return true;
        return this.level == l;
    }


}
