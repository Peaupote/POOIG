package fr.ip.model.util;

import com.sun.xml.internal.ws.api.message.MessageWritable;
import org.omg.CORBA.Any;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.util.Scanner;


public class QuestionBank {
    public class Question {
        private final String question;
        private final Answer answer;
        private final Level level;

        public Question(Level level, String question, Answer answer) {
            this.question = question;
            this.answer = answer;
            this.level = level;
        }

        public String getQuestion() {
            return question;
        }

        public Answer getAnswer() {
            return answer;
        }

        public Level getLevel() {
            return level;
        }

        public boolean isLevel(Level l) {
            return l == level;
        }
    }

    public class Answer {
        String answer;

        public Answer(String answer) {
            this.answer = answer;
        }
        public Answer(int answer) {
            this.answer = Integer.toString(answer);
        }
        public Answer(Answer a) {
            this.answer = a.getAnswer();
        }

        public String getAnswer() {
            return answer;
        }

        public boolean isCorrect(Answer a) {
            return a.getAnswer().toLowerCase().equals(answer.toLowerCase());
        }
    }

    public class QuestionMC extends Question implements Iterable<Tuple<Answer, String>>{
        private ArrayList<Tuple<Answer, String>> as;

        public QuestionMC(Level level, String question, ArrayList<Tuple<Answer, String>> as, Answer answer) {
            super(level, question, answer);
            this.as = as;
        }

        public ArrayList<Tuple<Answer, String>> getAnswersArray() {
            return as;
        }

        @Override
        public Iterator<Tuple<Answer, String>> iterator() {
            return as.iterator();
        }
    }

    private ArrayList<Question> questions;
    private Level level;
    private File folder;

    public QuestionBank(Level level) {
        if(SETTINGS.instance.isSetting("ANY_LEVEL_OVERRIDE"))
            this.level = (boolean) SETTINGS.instance.getSetting("ANY_LEVEL_OVERRIDE").value ? Level.ANY : level;
        else {
            System.out.println("");
        }
        try {
            this.folder = new File((String) SETTINGS.instance.getSetting("PATH_TO_QUESTIONS_FOLDER").value);
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
            if(parsed != null)
                list.addAll(parsed);
            parsed = null;
        }

        return list;
    }

    private ArrayList<Question> parse(File f) {
        Scanner sc;

        try {
            new Message(MessageType.INFORMATION, "Opening" + f.getCanonicalPath()).print();
            sc = new Scanner(f);
        } catch (IOException e) {
            new Message(MessageType.ERROR, "Wrong file path").print();
        } catch (FileNotFoundException e) {
            try {
                new Message(MessageType.WARNING, "File" + f.getCanonicalPath() + "not found").print();
            } catch (IOException err) {
                new Message(MessageType.ERROR, "Wrong file path").print();
            return null;
        }

        ArrayList<Question> qs = new ArrayList<>();
        Scanner line;
        String sLine;
        Question q;
        boolean valid = true;
        int questionType = -1;

        while(sc.hasNextLine()) {
            while(questionType == -1) {
                
            }

            sLine = sc.nextLine();
            if(!valid && StringUtil.isEmpty(sLine))
                valid = true;
            else
                continue;
            if(StringUtil.removeWhiteSpace(sLine).startsWith("**"))
                continue; // Checks if comment line.
            if(StringUtil.removeWhiteSpace(sLine).startsWith("[") && !isLevel(levelFromLine(sLine))) {
                valid = false;
                continue;
            }
            if(q != null)
                qs.add(q);

        }

        return qs;
    }

    private Level levelFromLine(String line) {
        String sLevel = StringUtil.removeWhiteSpace(line);
        sLevel = sLevel.substring(1, sLevel.length() - 2);

        new Message(MessageType.INFORMATION, "Ligne de niveau minimisée: " + sLevel).print();

        Level out;

        switch (sLevel) {
            case "Débutant":
                out = Level.BEGINNER;
                break;
            case "Intermédiaire":
                out = Level.INTERMEDIATE;
                break;
            case "Confirmé":
                out = Level.EXPERIENCED;
                break;
            case "Expert":
                out = Level.EXPERT;
                break;
            case "Mixte":
                out = Level.ANY;
                break;
            default:
                new Message(MessageType.WARNING, "Niveau de question non spécifié ou incorrect.").print();
                out = null;
        }

        return out;
    }

    public boolean isLevel(Level l) {
        if(this.level == Level.ANY || l == Level.ANY) return true;
        return this.level == l;
    }


}
