package fr.ip.model.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;


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

    public enum Level {
        BEGINNER("Débutant"),
        INTERMEDIATE("Intermédiaire"),
        EXPERT("Expert"),
        ANY("Mixte");

        private String name;

        Level(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
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
        this.level = level;
        this.folder =
    }

    public QuestionBank() {
        this(Level.ANY);
    }
}
