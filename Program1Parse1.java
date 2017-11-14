import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Prem Methuku, Joe Park
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires [<"INSTRUCTION"> is a proper prefix of tokens]
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to statement string of body of
     *          instruction at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        String getInstruction = tokens.dequeue();
        Reporter.assertElseFatalError(getInstruction.equals("INSTRUCTION"),
                "Error: \"INSTRUCTION\" not found ");

        String getIdentifier = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(getIdentifier),
                "Error: IDENTIFIER not found ");

        String getIs = tokens.dequeue();
        Reporter.assertElseFatalError(getIs.equals("IS"),
                "Error: \"IS\" not found ");

        body.parseBlock(tokens);

        String getEnd = tokens.dequeue();
        Reporter.assertElseFatalError(getEnd.equals("END"),
                "Error: \"END\" not found ");

        String getIdentifier2 = tokens.dequeue();
        Reporter.assertElseFatalError(getIdentifier2.equals(getIdentifier),
                "Error: IDENTIFIER Doesn't match ");

        String end = tokens.dequeue();
        Reporter.assertElseFatalError(end.equals(Tokenizer.END_OF_INPUT),
                "Error: \"### END OF INPUT ###\" not found ");

        return getIdentifier;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        String getProgram = tokens.dequeue();
        Reporter.assertElseFatalError(getProgram.equals("PROGRAM"),
                "Error: \"PROGRAM\" not found ");

        String getIdentifier = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(getIdentifier),
                "Error: IDENTIFIER not found ");

        String getIs = tokens.dequeue();
        Reporter.assertElseFatalError(getIs.equals("IS"),
                "Error: \"IS\" not found ");

        this.replaceName(getIdentifier);

        Map<String, Statement> ctxt = this.newContext();
        while (tokens.front().equals("INSTRUCTION")) {
            Statement body = this.newBody();
            String value = parseInstruction(tokens, body);
            Reporter.assertElseFatalError(!ctxt.hasKey(value),
                    "Error: Instruction is already defined");
            ctxt.add(value, body);
        }
        this.replaceContext(ctxt);

        String getBegin = tokens.dequeue();
        Reporter.assertElseFatalError(getBegin.equals("BEGIN"),
                "Error: \"BEGIN\" not found ");

        Statement pBody = this.newBody();
        pBody.parseBlock(tokens);
        this.replaceBody(pBody);

        String getEnd = tokens.dequeue();
        Reporter.assertElseFatalError(getEnd.equals("END"),
                "Error: \"END\" not found ");

        String getIdentifier2 = tokens.dequeue();
        Reporter.assertElseFatalError(getIdentifier2.equals(getIdentifier),
                "Error: IDENTIFIER Doesn't match ");

        String end = tokens.dequeue();
        Reporter.assertElseFatalError(end.equals(Tokenizer.END_OF_INPUT),
                "Error: \"### END OF INPUT ###\" not found ");
    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
