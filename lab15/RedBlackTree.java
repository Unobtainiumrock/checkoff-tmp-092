//import java.util.ArrayList;
//import java.util.List;

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

        RBTreeNode<T> left = buildRedBlackTree(r.getChildAt(0));
        RBTreeNode<T> right = buildRedBlackTree(r.getChildAt(1));

        if (r.getItemCount() == 1) {
            return new RBTreeNode<>(true, r.getItemAt(0), left, right);
        } else {
            RBTreeNode<T> middle = buildRedBlackTree(r.getChildAt(2));
            RBTreeNode<T> rootRed = new RBTreeNode<>(false, r.getItemAt(0), left, middle);
            RBTreeNode<T> rootBlack = new RBTreeNode<>(true, r.getItemAt(1), rootRed, right);
            return rootBlack;
        }
    }

    /* Flips the color of NODE and its children. Assume that NODE has both left
       and right children. */
    void flipColors(RBTreeNode<T> node) {

        node.isBlack = !node.isBlack;

        if (node.left != null) {
            node.left.isBlack = !node.left.isBlack;
            flipColors(node.left);
        }

        if (node.right != null) {
            node.right.isBlack = !node.right.isBlack;
            flipColors(node.right);
        }
    }

    /* Rotates the given node NODE to the right. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
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
        return new RBTreeNode<>(69, null);
    }

    /* Rotates the given node NODE to the left. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
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
        return new RBTreeNode<>(69, null);
    }

    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }

    /* Inserts the given node into this Red Black Tree*/
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
//        // Insert (return) new red leaf node.
//        if (node == null) {
//            return new RBTreeNode<>(false, item);
//        }
//
//        // Handle normal binary search tree insertion.
//        int comp = item.compareTo(node.item);
//        if (comp == 0) {
//            return node; // do nothing.
//        } else if (comp < 0) {
//            node.left = insert(node.left, item);
//        } else {
//            node.right = insert(node.right, item);
//        }
//
////        "right-leaning red" structures
////         "Middle of three"
//        // handle "smallest of three" structure
//
//        // handle "largest of three" structure
//

        // Ignore the node that they give us and make a copy of it's properties
        // over to a new node for which we can establish a parent-child relationship.
        RBTreeNode<T> copyButWithParents = new RBTreeNode<>(false,
                node.item, node.left, node.right, null);

        RBTreeNode<T> oneBefore = null;
        RBTreeNode<T> addLocation = this.root;

        while(addLocation != null) {
            oneBefore = addLocation;
            int comp = copyButWithParents.item.compareTo(addLocation.item);
            if (comp < 0) {
                addLocation = addLocation.left;
            } else if (comp > 0) {
                addLocation = addLocation.right;
            }
        }

        copyButWithParents.parent = oneBefore;

        // Adds to correct location, account for case that it's the first node.
        if (oneBefore == null) {
            this.root = copyButWithParents;
        } else if(copyButWithParents.item.compareTo(oneBefore.item) < 0) {
            oneBefore.left = copyButWithParents;
        } else {
            oneBefore.right = copyButWithParents;
        }

        // Handle the cases for balance violations.
//        copyButWithParents
        RBTreeNode<T> tmp;

        while (!(copyButWithParents.parent.isBlack)) {
            if (copyButWithParents.parent.item.compareTo(copyButWithParents.parent.parent.right.item) == 0) {
                // parent's sibling
                tmp = copyButWithParents.parent.parent.left;
                if (!(tmp.isBlack)) {
                    tmp.isBlack = true;
                    copyButWithParents.parent.isBlack = true; // *
                    copyButWithParents.parent.parent.isBlack = false; // *
                    copyButWithParents = copyButWithParents.parent.parent;
                } else {
                    if (copyButWithParents.item.compareTo(copyButWithParents.parent.left.item) == 0) {
                        copyButWithParents = copyButWithParents.parent;
                        // rotate right(copyButWithParents)
                    }
                    copyButWithParents.parent.isBlack = true; // *
                    copyButWithParents.parent.parent.isBlack = false; // *
                    //rotate left(copyButWithParents.parent.parent)

                    //* try to refactor later..
                }
            } else {
                tmp = copyButWithParents.parent.parent.right;
                if (!(tmp.isBlack)) {
                    tmp.isBlack = true;
                    copyButWithParents.parent.isBlack = true;
                    copyButWithParents.parent.parent.isBlack = false;
                    copyButWithParents = copyButWithParents.parent.parent;
                } else {
                    if (copyButWithParents.item.compareTo(copyButWithParents.parent.right.item) == 0) {
                        copyButWithParents = copyButWithParents.parent;
                        // rotate left(copyButWithParents)
                    }
                    copyButWithParents.parent.isBlack = true;
                    copyButWithParents.parent.parent.isBlack =  false;
                    // right rotate(copyButWithParents.parent.parent)
                }
            }
            if (copyButWithParents.equals(root)) {
                break;
            }
        }
        this.root.isBlack = false;
        return this.root;
    }


    /* Returns whether the given node NODE is red. Null nodes (children of leaf
       nodes are automatically considered black. */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;
        RBTreeNode<T> parent;


        RBTreeNode(int stupid, T item) {
            this.item = item;
        }
        /* Creates a RBTreeNode with item ITEM and color depending on ISBLACK
           value. */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /* Creates a RBTreeNode with item ITEM, color depending on ISBLACK
           value, left child LEFT, and right child RIGHT. */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }

        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right, RBTreeNode<T> parent) {
            this(isBlack, item, left, right);
            this.parent = parent;
        }
    }

}
