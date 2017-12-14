package fr.ip.model.util;

import java.util.ArrayList;
import java.util.function.Predicate;

public abstract class Question implements Predicate<String> {

    private final String question;
    private final String answer;

    @Override
    public boolean test(String s) {
        return answer.equals(s);
    }

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public class QuestionMC extends Question {
        private ArrayList<String> as;

        public QuestionMC(String question, int answer, ArrayList<String> as) {
            super(question, as.get(answer));
            this.as = as;
        }

    }
}
