package app.functions;

import java.util.ArrayList;
import java.util.Set;

public class Rule {
        ArrayList<String> antecedent;
        ArrayList<String> consequent;
        Double confiance;


        public Rule(ArrayList<String> antecedent, ArrayList<String> consequent) {
                this.antecedent = antecedent;
                this.consequent = consequent;
        }

        @Override
        public String toString() {
                return "Rule{" +
                        "antecedent=" + antecedent +
                        ", consequent=" + consequent +
                        ", confiance="+ confiance+
                        '}';
        }
}
