import MultiSet.Multiset;
import org.antlr.v4.runtime.*;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import static MultiSet.ListMultiset.empty;
import static java.lang.Math.log;

public class Test {

    private static Multiset<String> operandSet = empty();
    private static Multiset<String> operatorSet = empty();

    public static void main(String[] args) throws Exception {

        String template = "C:\\Users\\Max\\IntelliJ-Workspace\\Übersetzungstechniken\\HalsteadMetriken\\halsteadTemplate.stg";



        STGroup group = new STGroupFile(args[1]);
        ST operands = group.getInstanceOf("operand");
        ST operators = group.getInstanceOf("operator");
        ST parameters = group.getInstanceOf("parameter");
        ST metriken = group.getInstanceOf("metriken");

        CharStream input = null;
        if ( args.length>0 ) input = new ANTLRFileStream(args[0]);
        else input = new ANTLRInputStream(System.in);

        HalsteadLexer lex = new HalsteadLexer(input);

        Token t = lex.nextToken();


        while ( t==null || t.getType()!= HalsteadLexer.EOF ) {
            if (t.getType() == HalsteadLexer.OPERAND) operandSet = operandSet.insert(t.getText());
            if (t.getType() == HalsteadLexer.OPERATOR) operatorSet = operatorSet.insert(t.getText());
            t = lex.nextToken();
        }

        // OPERANDEN UND DEREN HÄUFIGKEIT
        for (int i = 1; i <= operandSet.distinct(); i++) {
            String[] a = {operandSet.getEntryAtPosition(i).key.toString(), operandSet.getEntryAtPosition(i).value.toString()};
            operands.addAggr("operanden.{operand, amount}", a);
        }

        // OPERATOREN UND DEREN HÄUFIGKEIT
        for (int i = 1; i <= operatorSet.distinct(); i++) {
            String[] a = {operatorSet.getEntryAtPosition(i).key.toString(), operatorSet.getEntryAtPosition(i).value.toString()};
            operators.addAggr("operators.{operators, amount}", a);
        }

        parameters.add("amountOperators", countOperators());
        parameters.add("amountOperands", countOperands());
        parameters.add("amountDiffOperators", amountDiffOperators());
        parameters.add("amountDiffOperands", amountDiffOperands());

        metriken.add("programLength", programLength());
        metriken.add("vocSize", vocSize());
        metriken.add("volume", volume());
        metriken.add("difficulty", difficulty());
        metriken.add("effort", effort());

        System.out.println(operands.render());
        System.out.println(operators.render());
        System.out.println(parameters.render());
        System.out.println(metriken.render());

    }

    private static double effort() {
        return volume() * difficulty();
    }

    // D = ( n1 / 2 ) * ( N2 / n2 )
    private static double difficulty() {
        return (amountDiffOperators() / 2) * (countOperands() / amountDiffOperands());
    }

    private static double volume() {
        return programLength() * (log(vocSize())/log(2));
    }

    private static double vocSize() {
        return amountDiffOperands() + amountDiffOperators();
    }

    private static double programLength() {
        return countOperands() + countOperators();
    }

    private static double amountDiffOperands() {
        return operandSet.distinct();
    }

    private static double amountDiffOperators() {
        return operatorSet.distinct();
    }

    private static double countOperands() {
        return operandSet.size();
    }

    private static double countOperators() {
        return operatorSet.size();
    }
}