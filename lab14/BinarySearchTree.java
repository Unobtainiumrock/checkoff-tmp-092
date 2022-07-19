import java.util.function.Consumer;

public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> {

    /* Creates an empty BST. Super() calls the constructor for BinaryTree (not in scope). */
    public BinarySearchTree() {
        super();
    }

    /* Creates a BST with root as ROOT. */
    public BinarySearchTree(TreeNode root) {
        super(root);
    }

    /* Returns true if the BST contains the given KEY. */
    public boolean contains(T key) {
        boolean[] out = { false };
        DFSInOrder(root, (node) -> {
            if (node.item.compareTo(key) == 0) {
                out[0] = true;
                return;
            }
        });
        return out[0];
    }

    private void DFSInOrder(TreeNode node, Consumer<TreeNode> fn) {
        fn.accept(node);
        if (node.left != null) {
            DFSInOrder(node.left, fn);
        }
        if (node.right != null) {
            DFSInOrder(node.right, fn);
        }
    }

    /* Adds a node for KEY iff KEY isn't in the BST already. */
    public void add(T key) {
        // Naive approach: Check entire tree, then do insertion.
        if(!contains(key)) {
            TreeNode toAdd = new TreeNode(key);
            // TreeNode parent;
            // TreeNode curr;
            boolean[] once = {false};

            DFSInOrder(root, (node) -> {
                // curr = new TreeNode(node.item);
                // curr.left = node.left;
                // curr.right = node.right;
                int compareResult = toAdd.item.compareTo(node.item);
                if (compareResult != 0) {
                    if (compareResult > 0) {
                        // Insert on the right side and handle re-linking
                    } else {
                        // Insert on the left side and handle re-linking
                    }
                }
                // parent = curr;
            });
        }
        // if (!contains(key)) {
        //     TreeNode toAdd = new TreeNode(key);
        //     TreeNode current = root;
            
        //     // Go left
        //     if (toAdd.item.compareTo(current.item) < 0) {
        //         if (current.left != null) {
                    
        //         }           
        //     }

        //     // Go right

        // }
    }

    /* Deletes a node from the BST. 
     * Even though you do not have to implement delete, you 
     * should read through and understand the basic steps.
    */
    public T delete(T key) {
        TreeNode parent = null;
        TreeNode curr = root;
        TreeNode delNode = null;
        TreeNode replacement = null;
        boolean rightSide = false;

        while (curr != null && !curr.item.equals(key)) {
            if (curr.item.compareTo(key) > 0) {
                parent = curr;
                curr = curr.left;
                rightSide = false;
            } else {
                parent = curr;
                curr = curr.right;
                rightSide = true;
            }
        }
        delNode = curr;
        if (curr == null) {
            return null;
        }

        if (delNode.right == null) {
            if (root == delNode) {
                root = root.left;
            } else {
                if (rightSide) {
                    parent.right = delNode.left;
                } else {
                    parent.left = delNode.left;
                }
            }
        } else {
            curr = delNode.right;
            replacement = curr.left;
            if (replacement == null) {
                replacement = curr;
            } else {
                while (replacement.left != null) {
                    curr = replacement;
                    replacement = replacement.left;
                }
                curr.left = replacement.right;
                replacement.right = delNode.right;
            }
            replacement.left = delNode.left;
            if (root == delNode) {
                root = replacement;
            } else {
                if (rightSide) {
                    parent.right = replacement;
                } else {
                    parent.left = replacement;
                }
            }
        }
        return delNode.item;
    }
}
