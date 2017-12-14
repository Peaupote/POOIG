package fr.ip.model.util;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class QuestionBank {

    private ArrayList<Question> questions;
    private Level level;
    private File folder;

    public QuestionBank(Level level) {
        Settings settings = new Settings();

        if(settings.isSetting("ANY_LEVEL_OVERRIDE"))
            this.level = (boolean) settings.getSetting("ANY_LEVEL_OVERRIDE").value() ? Level.ANY : level;
        else {
            System.out.println("");
        }
        try {
            this.folder = new File((String) settings.getSetting("PATH_TO_QUESTIONS_FOLDER").value());
        } catch (NullPointerException e) {
            Facade.show(new Message("Le dossier des questions est introuvable, existe-t-il ?", JOptionPane.ERROR_MESSAGE));
            System.exit(2);
        }
        File[] fileList = folder.listFiles();
        if(fileList == null) {
            Facade.show(new Message("Aucun fichier dans le dossier questions.", JOptionPane.ERROR_MESSAGE));
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
            Facade.show(new Message("Opening" + canonicalPath, JOptionPane.INFORMATION_MESSAGE));
            sc = new Scanner(f);
        } catch (IOException e) {
            Facade.show(new Message("Wrong file path", JOptionPane.ERROR_MESSAGE));
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

            if(!valid && sLine.isEmpty())
                valid = true;
            else
                continue;
            if(sLine.trim().startsWith("**"))
                continue; // Checks if comment line.
            if(sLine.trim().startsWith("[") && !isRightLevel(levelFromLine(sLine))) {
                valid = false;
                continue;
            }

            //

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
            if(sLine.trim().startsWith("!!")) {
                line = new Scanner(sLine.trim());
                line.useDelimiter("!!");
                questionType = Question.QuestionType.questionTypeFromString(line.next());

                if(questionType != Question.QuestionType.NONE) {
                    Facade.show(new Message(path + " : " + questionType.toString() + " questions file.", JOptionPane.INFORMATION_MESSAGE));
                    return questionType;
                } else
                    Facade.show(new Message("Question type not recognized at line " + lineNb + ".", JOptionPane.WARNING_MESSAGE));
            }
        }

        Facade.show(new Message(path + " : No question type for file.", JOptionPane.INFORMATION_MESSAGE));
        return Question.QuestionType.NONE;
    }

    private Level levelFromLine(String line) {
        String sLevel = line.trim();
        sLevel = sLevel.substring(1, sLevel.length() - 2);

        Facade.show(new Message("Ligne de niveau minimisée: " + sLevel, JOptionPane.INFORMATION_MESSAGE));

        return Level.levelFromString(sLevel);
    }

    public boolean isRightLevel(Level l) {
        if(l == null) return false;
        if(this.level == Level.ANY || l == Level.ANY) return true;
        return this.level == l;
    }


}
