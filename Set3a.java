import java.util.Iterator;

import components.binarytree.BinaryTree;
import components.set.Set;
import components.set.SetSecondary;

/**
 * {@code Set} represented as a {@code BinaryTree} (maintained as a binary
 * search tree) of elements with implementations of primary methods.
 *
 * @param <T>
 *            type of {@code Set} elements
 * @mathdefinitions <pre>
 * IS_BST(
 *   tree: binary tree of T
 *  ): boolean satisfies
 *  [tree satisfies the binary search tree properties as described in the
 *   slides with the ordering reported by compareTo for T, including that
 *   it has no duplicate labels]
 * </pre>
 * @convention IS_BST($this.tree)
 * @correspondence this = labels($this.tree)
 */
public class Set3a<T extends Comparable<T>> extends SetSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private BinaryTree<T> tree;

    /**
     * Returns whether {@code x} is in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be searched for
     * @return true if t contains x, false otherwise
     * @requires IS_BST(t)
     * @ensures isInTree = (x is in labels(t))
     */
    private static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        //creates a boolean check variable
        boolean isInTree = false;
        //if tree is not empty
        if (t.size() != 0) {
            //create two empty trees
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();
            //disassemble tree into left and right and root
            T root = t.disassemble(left, right);
            //if x is root change boolean variable to true
            //if in the left subtree check recursively if x in that tree
            //if in the right subtree check recursively if x in that tree
            if (x.equals(root)) {
                isInTree = true;
            } else if (x.compareTo(root) < 0) {
                isInTree = isInTree(left, x);

            } else {
                isInTree = isInTree(right, x);

            }
            //assemble back tree from left right and root
            t.assemble(root, left, right);
        }
        //return boolean variable
        return isInTree;
    }

    /**
     * Inserts {@code x} in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be inserted
     * @aliases reference {@code x}
     * @updates t
     * @requires IS_BST(t) and x is not in labels(t)
     * @ensures IS_BST(t) and labels(t) = labels(#t) union {x}
     */
    private static <T extends Comparable<T>> void insertInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        int comp = 0;
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        if (t.size() > 1) {
            T root = t.disassemble(left, right);
            comp = x.compareTo(root);
            if (comp > 0) {
                insertInTree(right, x);
            } else if (comp < 0) {
                insertInTree(left, x);
            }
            t.assemble(root, left, right);
        } else {
            T root = t.disassemble(left, right);
            comp = x.compareTo(root);
            if (comp > 0) {
                right.replaceRoot(x);
            } else if (comp < 0) {
                left.replaceRoot(x);
            }
            t.assemble(root, left, right);
        }
        

    }

    /**
     * Removes and returns the smallest (left-most) label in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove the label
     * @return the smallest label in the given {@code BinaryTree}
     * @updates t
     * @requires IS_BST(t) and |t| > 0
     * @ensures <pre>
     * IS_BST(t)  and  removeSmallest = [the smallest label in #t]  and
     *  labels(t) = labels(#t) \ {removeSmallest}
     * </pre>
     */
    private static <T> T removeSmallest(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";

       //set the smallest to the root
        T small = t.root();
        //create two empty trees
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        //disassemble tree into left and right and root
        T root = t.disassemble(left, right);
        //if left is not empty, then go into that tree recursively until
        //it founds the left most root/node
        if (left.size() != 0) {
            small = removeSmallest(left);
            //assemble back tree from left right and root
            t.assemble(root, left, right);
        } else {
            //if nothing in left transfer from right
            t.transferFrom(right);
        }
        //return the smallest
        return small;
    }

    /**
     * Finds label {@code x} in {@code t}, removes it from {@code t}, and
     * returns it.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove label {@code x}
     * @param x
     *            the label to be removed
     * @return the removed label
     * @updates t
     * @requires IS_BST(t) and x is in labels(t)
     * @ensures <pre>
     * IS_BST(t)  and  removeFromTree = x  and
     *  labels(t) = labels(#t) \ {x}
     * </pre>
     */
    private static <T extends Comparable<T>> T removeFromTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        int comp = x.compareTo(t.root());
        T remove = t.root();
        T root = t.root();
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        if (comp < 0) {
            root = t.disassemble(left, right);
            remove = removeFromTree(left, x);
            t.assemble(root, left, right);
        } else if (comp > 0) {
            root = t.disassemble(left, right);
            remove = removeFromTree(right, x);
            t.assemble(root, left, right);
        } else if (comp == 0) {
            root = t.disassemble(left, right);
            remove = root;
            if (left.size() > 0 && right.size() > 0) {
                root = removeSmallest(right);
                t.assemble(root, left, right);
            } else if (left.size() > 0 && right.size() == 0) {
                t.transferFrom(left);
            } else if (left.size() == 0 && right.size() > 0) {
                t.transferFrom(right);
            }

        }

        return remove;
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        // TODO - fill in body p

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Set3a() {

        this.createNewRep();

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Set<T> newInstance() {
        try {
            return this.getClass().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Set<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Set3a<?> : ""
                + "Violation of: source is of dynamic type Set3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set3a<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Set3a<T> localSource = (Set3a<T>) source;
        this.tree = localSource.tree;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert !this.contains(x) : "Violation of: x is not in this";

        //use insertInTree for this method
        insertInTree(this.tree, x);

    }

    @Override
    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.contains(x) : "Violation of: x is in this";

        return removeFromTree(this.tree, x);
    }

    @Override
    public final T removeAny() {
        assert this.size() > 0 : "Violation of: this /= empty_set";

        //use removeSmallest for this method
        return removeSmallest(this.tree);
    }

    @Override
    public final boolean contains(T x) {
        assert x != null : "Violation of: x is not null";

        return isInTree(this.tree, x);
    }

    @Override
    public final int size() {

        return this.tree.size();
    }

    @Override
    public final Iterator<T> iterator() {
        return this.tree.iterator();
    }

}
