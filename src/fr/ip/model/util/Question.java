package fr.ip.model.util;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Question {

    private final String question;
    private final Answer answer;
    private final Level level;
    private final QuestionType questionType;

    public Question(Level level, String question, Answer answer, QuestionType questionType) {
        this.question = question;
        this.answer = answer;
        this.level = level;
        this.questionType = QuestionType.NORMAL;
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

    public QuestionType getQuestionType() {
        return questionType;
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
            super(level, question, answer, QuestionType.MCQ);
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

    public class QuestionNormal extends Question {
        public QuestionNormal(Level level, String question, Answer answer) {
            super(level, question, answer, QuestionType.NORMAL);
        }
    }

    public enum QuestionType {
        NORMAL("Normal"),
        MCQ("MCQ"),
        NONE("None");

        private String name;

        QuestionType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public static QuestionType questionTypeFromString(String type) {
            switch (type.toLowerCase()) {
                case "normal" : return NORMAL;
                case "mcq" : return MCQ;
                case "qcm" : return MCQ;
                default:
                    return NONE;
            }
        }

        public static boolean isQuestionType(String type) {
            switch (type.toLowerCase()) {
                case "normal" : return true;
                case "mcq" : return true;
                case "qcm" : return true;
                default:
                    return false;
            }
        }
    }
}
