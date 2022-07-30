import java.util.function.Consumer;
import java.util.function.Function;

// Heavily referenced this to better understand with R/B trees are.
// definitely harder than AVL!
//https://brilliant.org/wiki/red-black-tree/#operations
public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;


    /* Creates an empty RedBlackTree. */
    public RedBlackTree() {
        root = null;
    }

    /* Creates a RedBlackTree from a given 2-3 TREE. */
    public RedBlackTree(TwoThreeTree<T> tree) {
        Node<T> ttTreeRoot = tree.root;
        root = buildRedBlackTree(ttTreeRoot);
    }

    /* Builds a RedBlackTree that has isometry with given 2-3 tree rooted at
       given node R, and returns the root node. */
    RBTreeNode<T> buildRedBlackTree(Node<T> r) {
        if (r == null) {
            return null;
        }

        if (r.getItemCount() == 1) {
            RBTreeNode<T> left = buildRedBlackTree(r.getChildAt(0));
            RBTreeNode<T> right = buildRedBlackTree(r.getChildAt(1));
            return new RBTreeNode<>(true, r.getItemAt(0), left, right);
        } else {
            RBTreeNode<T> left = buildRedBlackTree(r.getChildAt(0));
            RBTreeNode<T> middle = buildRedBlackTree(r.getChildAt(1));
            RBTreeNode<T> right = buildRedBlackTree(r.getChildAt(2));
            RBTreeNode<T> rootRed = new RBTreeNode<>(false, r.getItemAt(0), left, middle);
            RBTreeNode<T> rootBlack = new RBTreeNode<>(true, r.getItemAt(1), rootRed, right);
            return rootBlack;
        }
    }

    void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;

        if (node.left != null) {
            node.left.isBlack = !node.left.isBlack;
        }

        if (node.right != null) {
            node.right.isBlack = !node.right.isBlack;
        }
    }

    RBTreeNode<T> rotateLeft(RBTreeNode node) {
        RBTreeNode<T> tmp = node.right;
        node.right = tmp.left;
        tmp.left = node;
        tmp.isBlack = tmp.left.isBlack;
        tmp.left.isBlack = false;
//        node.isBlack = !node.isBlack;
        return tmp;
    }

    RBTreeNode<T> rotateRight(RBTreeNode node) {
        RBTreeNode<T> tmp = node.left;
        node.left = tmp.right;
        tmp.right = node;
        tmp.isBlack = node.right.isBlack;
        tmp.right.isBlack = false;
//        node.isBlack = !node.isBlack;
        return tmp;
    }

    public void insert(T item) {
        RBTreeNode<T> r = new RBTreeNode<>(false, item, null, null);
        if (root == null) {
            r.isBlack = true;
            root = r;
        } else {
            insert(root, r.item);
        }
    }

    /* Inserts the given node into this Red Black Tree*/
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        if (node == null) {
            return new RBTreeNode<>(false, item);
        }
        // Handle normal binary search tree insertion.
        int comp = item.compareTo(node.item);
        if (comp == 0) {
            return node; // do nothing.
        } else if (comp < 0) {
            node.left = insert(node.left, item);
        } else {
            node.right = insert(node.right, item);
        }

        return recursiveHelper(node, (n) -> {
            if (isRed(n.right) && !isRed(n.left)) {
                n = rotateLeft(n);
            }
            if (n.left == null) {
                return n;
            }
            if (isRed(n.left) & isRed(n.left.left)) {
                n = rotateRight(n);
            }
            if (isRed(n.left) && isRed((n.right))) {
                flipColors(n);
            }
            return n;
        });

//        recursiveHelperTwo(node, (n) -> {
//            if (n != null) {
//                if (isRed(n.right) && !isRed(n.left)) {
//                    n = rotateLeft(n);
//                }
//                if (n.left != null) {
//                    if (isRed(n.left) & isRed(n.left.left)) {
//                        n = rotateRight(n);
//                    }
//                    if (isRed(n.left) && isRed((n.right))) {
//                        flipColors(n);
//                    }
//                }
//            }
//        });
    }

    private RBTreeNode<T> recursiveHelper(RBTreeNode<T> node, Function<RBTreeNode<T>, RBTreeNode<T>> fn) {
        if (node.left == null || node.right == null) {
            if (node.left != null) {
                return new RBTreeNode<>(node.isBlack, node.item, node.left, null);
            } else if (node.right != null) {
                return new RBTreeNode<>(node.isBlack, node.item, null, node.right);
            } else {
                return new RBTreeNode<>(node.isBlack, node.item);
            }
        }
        RBTreeNode<T> left = recursiveHelper(fn.apply(node.left), fn);
        RBTreeNode<T> right = recursiveHelper(fn.apply(node.right), fn);
        return new RBTreeNode<T>(node.isBlack, node.item, left, right);
    }

//    private void recursiveHelperTwo(RBTreeNode<T> node, Consumer<RBTreeNode<T>> fn) {
//        if (node == null) {
//            return;
//        }
//        fn.accept(node.left);
//        fn.accept(node.right);
//        recursiveHelperTwo(node.left, fn);
//        recursiveHelperTwo(node.right, fn);
//    }

    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;
        RBTreeNode<T> parent;

        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

}
