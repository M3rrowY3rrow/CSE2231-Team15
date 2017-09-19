import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, value, hasKey, and size
    // j had add, removeAny(), and hasKey
    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> q = this.constructorTest();
        Map<String, String> qExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    @Test
    public final void testAddToEmpty() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> q = this.createFromArgsTest();
        Map<String, String> qExpected = this.createFromArgsRef("green", "pear");
        /*
         * Call method under test
         */
        q.add("green", "pear");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    @Test
    public final void testAdd1() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> q = this.createFromArgsTest("red", "apple");
        Map<String, String> qExpected = this.createFromArgsRef("red", "apple",
                "green", "pear");
        /*
         * Call method under test
         */
        q.add("green", "pear");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    @Test
    public final void testAdd2() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> q = this.createFromArgsTest("red", "apple");
        Map<String, String> qExpected = this.createFromArgsRef("red", "apple",
                "green", "pear", "purple", "plum");
        /*
         * Call method under test
         */
        q.add("green", "pear");
        q.add("purple", "plum");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    @Test
    public final void testAdd3() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> q = this.createFromArgsTest("red", "apple");
        Map<String, String> qExpected = this.createFromArgsRef("red", "apple",
                "green", "pear", "purple", "plum", "yellow", "banana");
        /*
         * Call method under test
         */
        q.add("green", "pear");
        q.add("purple", "plum");
        q.add("yellow", "banana");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(qExpected, q);
    }

    @Test
    public final void testRemoveAny1() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> q = this.createFromArgsTest("red", "apple", "green",
                "pear", "purple", "plum", "yellow", "banana");
        Map<String, String> qExpected = this.createFromArgsRef("green", "pear",
                "purple", "plum", "yellow", "banana");
        String color = "red";
        String fruit = "apple";
        /*
         * Call method under test
         */
        Pair<String, String> a = q.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(a.key(), color);
        assertEquals(a.value(), fruit);
        assertEquals(qExpected, q);
    }

    @Test
    public final void testRemoveAny2() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> q = this.createFromArgsTest("red", "apple", "green",
                "pear", "purple", "plum", "yellow", "banana");
        Map<String, String> qExpected = this.createFromArgsRef("purple", "plum",
                "yellow", "banana");
        String c1 = "red";
        String f1 = "apple";
        String c2 = "green";
        String f2 = "pear";
        /*
         * Call method under test
         */
        Pair<String, String> a = q.removeAny();
        Pair<String, String> b = q.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(a.key(), c1);
        assertEquals(a.value(), f1);
        assertEquals(b.key(), c2);
        assertEquals(b.value(), f2);
        assertEquals(qExpected, q);
    }

    @Test
    public final void testRemoveAny3() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> q = this.createFromArgsTest("red", "apple", "green",
                "pear", "purple", "plum", "yellow", "banana");
        Map<String, String> qExpected = this.createFromArgsRef("yellow",
                "banana");
        String c1 = "red";
        String f1 = "apple";
        String c2 = "green";
        String f2 = "pear";
        String c3 = "green";
        String f3 = "pear";
        /*
         * Call method under test
         */
        Pair<String, String> a = q.removeAny();
        Pair<String, String> b = q.removeAny();
        Pair<String, String> c = q.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(a.key(), c1);
        assertEquals(a.value(), f1);
        assertEquals(b.key(), c2);
        assertEquals(b.value(), f2);
        assertEquals(c.key(), c3);
        assertEquals(c.value(), f3);
        assertEquals(qExpected, q);
    }

    @Test
    public final void testHasKey1() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> q = this.createFromArgsTest("red", "apple", "green",
                "pear", "purple", "plum", "yellow", "banana");
        Map<String, String> qExpected = this.createFromArgsRef("red", "apple",
                "green", "pear", "purple", "plum", "yellow", "banana");
        boolean a = true;
        /*
         * Call method under test
         */
        boolean b = q.hasKey("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(a, b);
        assertEquals(qExpected, q);
    }

    @Test
    public final void testHasKey2() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> q = this.createFromArgsTest("red", "apple", "green",
                "pear", "purple", "plum", "yellow", "banana");
        Map<String, String> qExpected = this.createFromArgsRef("red", "apple",
                "green", "pear", "purple", "plum", "yellow", "banana");
        boolean a = true;
        boolean a1 = true;
        /*
         * Call method under test
         */
        boolean b = q.hasKey("green");
        boolean b1 = q.hasKey("yellow");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(a, b);
        assertEquals(a1, b1);
        assertEquals(qExpected, q);
    }

    @Test
    public final void testHasKey3() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> q = this.createFromArgsTest("red", "apple", "green",
                "pear", "purple", "plum", "yellow", "banana");
        Map<String, String> qExpected = this.createFromArgsRef("red", "apple",
                "green", "pear", "purple", "plum", "yellow", "banana");
        boolean a = true;
        boolean a1 = true;
        boolean a2 = true;
        /*
         * Call method under test
         */
        boolean b = q.hasKey("green");
        boolean b1 = q.hasKey("yellow");
        boolean b2 = q.hasKey("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(a, b);
        assertEquals(a1, b1);
        assertEquals(a2, b2);
        assertEquals(qExpected, q);
    }

}
