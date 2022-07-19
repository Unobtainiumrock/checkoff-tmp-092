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
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    RBTreeNode<T> rotateLeft(RBTreeNode node) {
        RBTreeNode<T> tmp = node.right;
        tmp.isBlack = node.isBlack;
        node.isBlack = false;
        node.right = tmp.left;
        tmp.left = node;
        return tmp;
    }

    RBTreeNode<T> rotateRight(RBTreeNode node) {
        RBTreeNode<T> tmp = node.left;
        tmp.isBlack = node.isBlack;
        node.isBlack = false;
        node.left = tmp.right;
        tmp.right = node;
        return tmp;
    }

    /* Rotates the given node NODE to the right. Returns the new root node of
       this subtree. */
    RBTreeNode<T> RR(RBTreeNode<T> node) {
        RBTreeNode<T> tmp = node.left;
        node.left = tmp.right;
        if (tmp.right != null) {
            tmp.right.parent = node;
        }

        tmp.parent = node.parent;

        if (node.parent == null) {
            this.root = tmp;
        } else if (node.item.compareTo(node.parent.right.item) == 0) {
            node.parent.right = tmp;
        } else {
            node.parent.left = tmp;
        }

        tmp.right = node;
        node.parent = tmp;

        // Just to meet their recursive output requirements
//        return new RBTreeNode<>(69, null);
        return node.parent;
    }

    /* Rotates the given node NODE to the left. Returns the new root node of
       this subtree. */
    RBTreeNode<T> RL(RBTreeNode<T> node) {
        RBTreeNode<T> tmp = node.right;
        node.right = tmp.left;
        if (tmp.left != null) {
            tmp.left.parent = node;
        }

        tmp.parent = node.parent;

        if (node.parent == null) {
            this.root = tmp;
        } else if (node.item.compareTo(node.parent.left.item) == 0) {
            node.parent.left = tmp;
        } else {
            node.parent.right = tmp;
        }

        tmp.left = node;
        node.parent = tmp;

        // Just to meet their recursive output requirements
//        return new RBTreeNode<>(69, null);
        return node.parent;
    }

    public void insert(T item) {
//        RBTreeNode<T> r = new RBTreeNode<>(false, item, null, null);
//        if (root == null) {
//            r.isBlack = true;
//            root = r;
//        } else {
//            insert(r, r.item);
//        }
        root = insert(root, item);
        root.isBlack = true;
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
            if (isRed(n.right)) {
                n = rotateLeft(n);
            } else if (isRed(n.left) && isRed(n.left.left)) {
                n = rotateRight(n);
            } else if (isRed(n.left) && isRed(n.left.right)) { // change check maybe.
                n = rotateLeft(n.left);
                n = rotateRight(n);
            }
            flipColors(n);
            return n;
        });
    }

    private RBTreeNode<T> recursiveHelper(RBTreeNode<T> node, Function<RBTreeNode<T>, RBTreeNode<T>> fn) {
        RBTreeNode<T> left = recursiveHelper(fn.apply(node.left), fn);
        RBTreeNode<T> right = recursiveHelper(fn.apply(node.right), fn);
        return new RBTreeNode<T>(true, null, left, right);
    }

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

//        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
//                   RBTreeNode<T> right, RBTreeNode<T> parent) {
//            this(isBlack, item, left, right);
//            this.parent = parent;
//        }
    }

}
