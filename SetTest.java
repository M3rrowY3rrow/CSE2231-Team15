import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size
    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Set<String> q = this.constructorTest();
        Set<String> qExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    @Test
    public final void testRemoveToEmpty() {
        /*
         * Set up variables and call method under test
         */
        Set<String> q = this.createFromArgsTest("green");
        Set<String> qExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        String trash = q.remove("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    @Test
    public final void testRemoveFromSize5() {
        /*
         * Set up variables and call method under test
         */
        Set<String> q = this.createFromArgsTest("green", "red", "white", "blue",
                "purple");
        Set<String> qExpected = this.createFromArgsRef("red", "white", "blue",
                "purple");
        /*
         * Call method under test
         */
        String trash = q.remove("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    @Test
    public final void testRemoveFromSize15() {
        /*
         * Set up variables and call method under test
         */
        Set<String> q = this.createFromArgsTest("green", "red", "white", "blue",
                "purple", "orange", "teal", "indigo", "yellow", "black",
                "brown", "pink", "scarlet", "violet", "grey");
        Set<String> qExpected = this.createFromArgsRef("red", "white", "blue",
                "purple", "orange", "teal", "indigo", "yellow", "black",
                "brown", "pink", "scarlet", "violet", "grey");
        /*
         * Call method under test
         */
        String trash = q.remove("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    @Test
    public final void testContainsTrueSize15() {
        /*
         * Set up variables and call method under test
         */
        Set<String> q = this.createFromArgsTest("green", "red", "white", "blue",
                "purple", "orange", "teal", "indigo", "yellow", "black",
                "brown", "pink", "scarlet", "violet", "grey");
        Set<String> qExpected = this.createFromArgsRef("green", "red", "white",
                "blue", "purple", "orange", "teal", "indigo", "yellow", "black",
                "brown", "pink", "scarlet", "violet", "grey");
        boolean in = false;
        /*
         * Call method under test
         */
        in = q.contains("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
        assertEquals(in, true);
    }

    @Test
    public final void testContainsFalseSize15() {
        /*
         * Set up variables and call method under test
         */
        Set<String> q = this.createFromArgsTest("green", "red", "white", "blue",
                "purple", "orange", "teal", "indigo", "yellow", "black",
                "brown", "pink", "scarlet", "violet", "grey");
        Set<String> qExpected = this.createFromArgsRef("green", "red", "white",
                "blue", "purple", "orange", "teal", "indigo", "yellow", "black",
                "brown", "pink", "scarlet", "violet", "grey");
        boolean in = false;
        /*
         * Call method under test
         */
        in = q.contains("emerald");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
        assertEquals(in, false);
    }

    @Test
    public final void testContainsTrueSize5() {
        /*
         * Set up variables and call method under test
         */
        Set<String> q = this.createFromArgsTest("green", "red", "white", "blue",
                "purple");
        Set<String> qExpected = this.createFromArgsRef("green", "red", "white",
                "blue", "purple");
        boolean in = false;
        /*
         * Call method under test
         */
        in = q.contains("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
        assertEquals(in, true);
    }

    @Test
    public final void testContainsFalseSize5() {
        /*
         * Set up variables and call method under test
         */
        Set<String> q = this.createFromArgsTest("green", "red", "white", "blue",
                "purple");
        Set<String> qExpected = this.createFromArgsRef("green", "red", "white",
                "blue", "purple");
        boolean in = false;
        /*
         * Call method under test
         */
        in = q.contains("ruby");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
        assertEquals(in, false);
    }

    /*
     * Test cases for size.
     */
    @Test
    public void testSizeBoundary() {
        /*
         * Set up variables
         */
        Set<String> q = this.constructorTest();
        Set<String> qExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(q.size(), qExpected.size());
    }

    @Test
    public void testSizeRoutine() {
        /*
         * Set up variables
         */
        Set<String> q = this.createFromArgsTest("a", "b", "c", "d");
        Set<String> qExpected = this.createFromArgsRef("a", "b", "c", "d");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(q.size(), qExpected.size());
    }

    /*
     * Test cases for add.
     */

    @Test
    public void testAddBoundary() {
        /*
         * Set up variables
         */
        Set<String> q = this.constructorTest();
        Set<String> qExpected = this.createFromArgsRef("a");
        /*
         * Call method under test
         */
        q.add("a");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    @Test
    public void testAddRoutine() {
        /*
         * Set up variables
         */
        Set<String> q = this.createFromArgsTest("a", "b", "c", "d");
        Set<String> qExpected = this.createFromArgsRef("e", "a", "b", "c", "d");
        /*
         * Call method under test
         */
        q.add("e");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    /*
     * Test cases for removeAny.
     */

    @Test
    public void testRemoveAnyBoundary() {
        /*
         * Set up variables
         */
        Set<String> q = this.createFromArgsTest("a");
        Set<String> qExpected = this.createFromArgsRef("a");
        /*
         * Call method under test
         */
        String item = q.removeAny();
        String itemExp = qExpected.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
        assertEquals(itemExp, item);
    }

    @Test
    public void testRemoveAnyRoutine() {
        /*
         * Set up variables
         */
        Set<String> q = this.createFromArgsTest("a", "b", "c", "d");
        Set<String> qExpected = this.createFromArgsRef("a", "b", "c", "d");
        /*
         * Call method under test
         */
        String item = q.removeAny();
        String itemExp = qExpected.remove(item);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
        assertEquals(itemExp, item);
    }
}
