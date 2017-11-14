import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Reporter;
import components.utilities.Tokenizer;
/**
*WHILE true DO
  move
  IF next-is-not-enemy THEN
  END IF
  IF random THEN
    eatcake
    infect
  ELSE
    eatpie
    turnright
    look-for-something
  END IF
  skip
END WHILE

WHILE true DO
  restroombreak
  IF this-is-not-a-valid-condition THEN
  END IF
  IF random THEN
    turnleft
    infect
  ELSE
    go-for-it
    turnright
    look-for-something
  END IF
  skip
END WHILE

WHILE true DO
  move
  IF next-is-not-enemy THEN
  END IF
  IF random THEN
    turnleft
    infect
  ELSE
    go-for-it
    turnright
    look-for-something
  END IF
  skip
END WHILE

PROGRAM Test IS

  INSTRUCTION one IS
     move
     turnleft
  END one
  
  INSTRUCTION two IS
    one
    IF next-is-not-empty THEN
      turnleft
    ELSE
      one
      one
    END IF
  END two

BEGIN
  infect
  WHILE true DO
    two
    IF next-is-empty THEN
      move
    END IF
    two
    one
  END WHILE
END BeachesAreHot

PROGRAM Test IS

  INSTRUCTION one IS
     move
     turnleft
  END one
  
  INSTRUCTION two IS
    one
    IF next-is-not-empty THEN
      turnleft
    ELSE
      one
      one
    END IF
  END three

BEGIN
  infect
  WHILE true DO
    two
    IF next-is-empty THEN
      move
    END IF
    two
    one
  END WHILE
END Test


*/
/**
 * Layered implementation of secondary methods {@code parse} and
 * {@code parseBlock} for {@code Statement}.
 *
 * @author Prem Methuku, Joe Park
 *
 */
public final class Statement1Parse1 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Converts {@code c} into the corresponding {@code Condition}.
     *
     * @param c
     *            the condition to convert
     * @return the {@code Condition} corresponding to {@code c}
     * @requires [c is a condition string]
     * @ensures parseCondition = [Condition corresponding to c]
     */
    private static Condition parseCondition(String c) {
        assert c != null : "Violation of: c is not null";
        assert Tokenizer
                .isCondition(c) : "Violation of: c is a condition string";
        return Condition.valueOf(c.replace('-', '_').toUpperCase());
    }

    /**
     * Parses an IF or IF_ELSE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires [<"IF"> is a proper prefix of tokens]
     * @ensures <pre>
     * if [an if string is a proper prefix of #tokens] then
     *  s = [IF or IF_ELSE Statement corresponding to if string at start of #tokens]  and
     *  #tokens = [if string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseIf(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("IF") : ""
                + "Violation of: <\"IF\"> is proper prefix of tokens";

        s.clear();

        String getIf1 = tokens.dequeue();
        Reporter.assertElseFatalError(getIf1.equals("IF"),
                "Error: \"IF\" not found ");

        String getCond = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isCondition(getCond),
                "Error: CONDITION not found ");

        String getThen = tokens.dequeue();
        Reporter.assertElseFatalError(getThen.equals("THEN"),
                "Error: \"THEN\" not found ");

        Statement block1 = s.newInstance();
        Condition c = parseCondition(getCond);
        block1.parseBlock(tokens);
        if (tokens.front().equals("ELSE")) {
            String getElse = tokens.dequeue();
            Reporter.assertElseFatalError(getElse.equals("ELSE"),
                    "Error: \"ELSE\" not found ");

            Statement block2 = s.newInstance();
            block2.parseBlock(tokens);
            s.assembleIfElse(c, block1, block2);
        } else {
            s.assembleIf(c, block1);
        }

        String getEnd = tokens.dequeue();
        Reporter.assertElseFatalError(getEnd.equals("END"),
                "Error: \"END\" not found ");

        String getIf2 = tokens.dequeue();
        Reporter.assertElseFatalError(getIf2.equals("IF"),
                "Error: \"IF\" not found ");

    }

    /**
     * Parses a WHILE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires [<"WHILE"> is a proper prefix of tokens]
     * @ensures <pre>
     * if [a while string is a proper prefix of #tokens] then
     *  s = [WHILE Statement corresponding to while string at start of #tokens]  and
     *  #tokens = [while string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseWhile(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("WHILE") : ""
                + "Violation of: <\"WHILE\"> is proper prefix of tokens";

        s.clear();

        String getWhile1 = tokens.dequeue();
        Reporter.assertElseFatalError(getWhile1.equals("WHILE"),
                "Error: \"WHILE\" not found ");

        String getCond = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isCondition(getCond),
                "Error: CONDITION not found ");

        String getDo = tokens.dequeue();
        Reporter.assertElseFatalError(getDo.equals("DO"),
                "Error: \"DO\" not found ");

        Statement block = s.newInstance();
        Condition c = parseCondition(getCond);
        block.parseBlock(tokens);
        s.assembleWhile(c, block);

        String getEnd = tokens.dequeue();
        Reporter.assertElseFatalError(getEnd.equals("END"),
                "Error: \"END\" not found ");

        String getWhile2 = tokens.dequeue();
        Reporter.assertElseFatalError(getWhile2.equals("WHILE"),
                "Error: \"WHILE\" not found ");

    }

    /**
     * Parses a CALL statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires [identifier string is a proper prefix of tokens]
     * @ensures <pre>
     * s =
     *   [CALL Statement corresponding to identifier string at start of #tokens]  and
     *  #tokens = [identifier string at start of #tokens] * tokens
     * </pre>
     */
    private static void parseCall(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0
                && Tokenizer.isIdentifier(tokens.front()) : ""
                        + "Violation of: identifier string is proper prefix of tokens";
        
        String getIdentifier = tokens.dequeue();
        boolean isInstr = Tokenizer.isIdentifier(getIdentifier);

        s.clear();
        
        if (isInstr) {
            s.assembleCall(getIdentifier);
        }
        else {
            Reporter.assertElseFatalError(isInstr,
                "Error: CALL to nonexistant instruction ");
        }
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Statement1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        String getFirst = tokens.front();
        if (getFirst.equals("IF")) {
            parseIf(tokens, this);
        } else if (getFirst.equals("WHILE")) {
            parseWhile(tokens, this);
        } else {
            parseCall(tokens, this);
        }

    }

    @Override
    public void parseBlock(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        this.clear();
        int pos = this.lengthOfBlock();

        String getFirst = tokens.front();
        Statement s = this.newInstance();

        if (getFirst.equals("IF")) {
            parseIf(tokens, s);
            this.addToBlock(pos, s);
            this.parseBlock(tokens);
        } else if (getFirst.equals("WHILE")) {
            parseWhile(tokens, s);
            this.addToBlock(pos, s);
            this.parseBlock(tokens);
        } else {
            parseCall(tokens, s);
            this.addToBlock(pos, s);
            this.parseBlock(tokens);
        }

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
        out.print("Enter valid BL statement(s) file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Statement s = new Statement1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        s.parse(tokens); // replace with parseBlock to test other method
        /*
         * Pretty print the statement(s)
         */
        out.println("*** Pretty print of parsed statement(s) ***");
        s.prettyPrint(out, 0);

        in.close();
        out.close();
    }

}
