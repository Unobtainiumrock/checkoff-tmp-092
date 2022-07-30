public class Main {
    public static void main(String[] args) {
        RedBlackTree<Integer> btree = new RedBlackTree<>();
//        RedBlackTree.RBTreeNode<Integer> node = new RedBlackTree.RBTreeNode<>(true, 10, null, null);

        btree.insert(10);
        btree.insert(7);
        btree.insert(15);
        btree.insert(6);
        btree.insert(8);
        btree.insert(14);
        btree.insert(16);
        btree.insert(24);
        btree.insert(13);
        btree.insert(69);
    }
}
