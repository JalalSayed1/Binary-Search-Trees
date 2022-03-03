import java.util.NoSuchElementException;

public class BST {

    private Node root; // root of BST

    private static class Node {
        private int key; // sorted by key
        private Node left, right, p;
        private int size; // number of nodes

        public Node(int key, int size) {
            this.key = key;
            this.size = size;
            this.left = null;
            this.right = null;
            this.p = null;
        }
    }

    public void BST() {
        root = null;
    }

    /**
     * Find and return a pointer to the node with key k in tree.
     * 
     * @param x
     *            Pointer to the root of the tree
     * @param k
     *            Node key to search for
     * @return Pointer to node with key k if exists, null if it does not
     */
    public static Node search(Node x, int k) { // WORKS
        if (x == null || k == x.key) {
            return x;
        }

        if (k < x.key) {
            return search(x.left, k);
        } else {
            return search(x.right, k);
        }
    }

    /**
     * Find the min value in tree
     * 
     * @param x
     *            Pointer to the root of the tree
     * @return Leftmost node in tree
     */
    public static Node min(Node x) { // WORKS
        // min value at the most left:
        while (x.left != null) {
            x = x.left;
        }

        return x;
    }

    /**
     * Find the max value in tree
     * 
     * @param x
     *            Pointer to the root of the tree
     * @return Rightmost node in tree
     */
    public static Node max(Node x) { // WORKS
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    /**
     * Insert node z into an appropriate position in tree T.
     * Runs in O(h) on tree with height h.
     * 
     * @param T
     *            A Binary Search Tree to insert z into
     * @param z
     *            Node to be inserted
     */
    public static void insert(BST T, Node z) { // WORKS
        Node y = null;
        Node x = T.root;

        while (x != null) {
            y = x;
            if (z.key < x.key) {
                x = x.left;

            } else {
                x = x.right;
            }
        }

        z.p = y;
        if (y == null) {
            T.root = z;

        } else if (z.key < y.key) {
            y.left = z;

        } else {
            y.right = z;
        }
    }

    public static void transplant(BST T, Node u, Node v) { // If delete works, this WORKS
        if (u.p == null) {
            T.root = v;

        } else if (u == u.p.left) {
            u.p.left = v;

        } else {
            u.p.right = v;
        }

        if (v != null) {
            v.p = u.p;
        }

    }

    /**
     * Delete node z from tree T.
     * Runs in O(h) on tree with height h.
     * 
     * @param T
     *            A Binary Search Tree to delete z from
     * @param z
     *            Node to be deleted
     */
    public static void delete(BST T, Node z) { // WORKS
        if (z.left == null) {
            transplant(T, z, z.right);
        } else if (z.right == null) {
            transplant(T, z, z.left);
        } else {
            Node y = min(z.right);

            if (y.p != z) {
                transplant(T, y, y.right);
                y.right = z.right;
                y.right.p = y;
            }

            transplant(T, z, y);
            y.left = z.left;
            y.left.p = y;
        }
    }

    public static int size(Node x) { // WORKS
        if (x == null) {
            return 0;
        }
        return size(x.left) + size(x.right) + 1;
    }

    /**
     * To print elements in order from node x.
     * To print all elements, call inorder(T.root); where T is a BST.
     * 
     * @param x
     *            Pointer to node where elts under this node will be printed inorder
     */
    public static void inorder(Node x) {

        if (x != null) {
            inorder(x.left);
            System.out.println(x.key);
            inorder(x.right);
        }
    }

    /**
     * To print elements in order from node x.
     * To print all elements, call inorder(T.root); where T is a BST.
     * 
     * @param x
     *            Pointer to node where elts under this node will be printed preorder
     */
    public static void preorder(Node x) {
        if (x != null) {
            System.out.println(x.key);
            preorder(x.left);
            preorder(x.right);
        }
    }

    /**
     * To print elements in order from node x.
     * To print all elements, call inorder(T.root); where T is a BST.
     * 
     * @param x
     *            Pointer to node where elts under this node will be printed postorder
     */
    public static void postorder(Node x) {
        if (x != null) {
            postorder(x.left);
            postorder(x.right);
            System.out.println(x.key);
        }
    }

    /**
     * To verify whether a binary tree satisfies the BST property.
     * 
     * @param T
     * @return
     */
    public static boolean checkBST(BST T) {
        // if left node is smaller than the node and right node is greater than the
        // node is not enough as the BST constraints apply to the whole left and right
        // subtrees

        // The root of the three is the only node whose parent is NIL
        // Node x is a leaf when x.left = x.right = NIL
        // If T.root = NIL, the tree is empty
        // A node r (the root), its left and right subtrees are also binary trees
        // min value on the left most position
        // max value on the right most position
        // .size ?
        // ! do we allow duplicates?

        boolean wellFormed = true;
        Node min = min(T.root);
        Node max = max(T.root);
        Node x = T.root;

        // root's parent must be null:
        if (T.root.p != null) {
            wellFormed = false;
            return wellFormed;
        }

        // check min value is on the rightmost position:
        while (x != null && x != min) {
            if (x.left.key < min.key) {
                wellFormed = false;
                return wellFormed;

            }
            x = x.left;
        }

        x = T.root; // again, to check for max
        // check max value is on the rightmost position:
        while (x != null && x != max) {
            if (x.right.key > max.key) {
                wellFormed = false;
                return wellFormed;

            }
            x = x.right;
        }

        return wellFormed;
    }

    /**
     * Left rotate a Binary tree T around node x.
     */
    public static void leftRotate(BST T, Node x) { // WORKS
        Node y = x.right;
        x.right = y.left;

        if (y.left != null) {
            y.left.p = x;
        }

        y.p = x.p;
        if (x.p == null) {
            T.root = y;
        } else if (x == x.p.left) {
            x.p.left = y;
        } else {
            x.p.right = y;
        }
        y.left = x;
        x.p = y;
    }

    /**
     * Right rotate Binary tree T around node x.
     */
    public static void rightRotate(BST T, Node x) { // WORKS
        Node y = x.left;
        x.left = y.right;

        if (y.right != null) {
            y.right.p = x;
        }

        y.p = x.p;
        if (x.p == null) {
            T.root = y;
        } else if (x == x.p.right) {
            x.p.right = y;
        } else {
            x.p.left = y;
        }
        y.right = x;
        x.p = y;
    }

    /**
     * Insert new nodes at the tree's root for fast access later.
     * 
     * @param T
     *            Binary Search Tree to add z to it.
     * @param z
     *            The new node to be added to T.
     */
    public static void insertRoot(BST T, Node z) {
        // * way 1 (my way):
        // z.p = null;
        // if (T.root.key > z.key) {
        // z.right = T.root;
        // } else {
        // z.left = T.root;
        // }
        // T.root.p = z;
        // T.root = T.root.p;

        // * way 2:
        // z.p = T.root;
        // z.left = T.root.left;
        // z.right = T.root.right;

        // if (T.root.key > z.key) {
        // T.root.left = z;
        // T.root.right = null;
        // // bring z up (to be the root) by rotating around its parent:
        // while (z != null) {
        // rightRotate(T, z.p); // O(1)
        // z = z.p; // eventually z.p will be null (root node)
        // }

        // z = T.root.left;

        // z = leftmost node
        // while (z != null) {
        // z = z.left;
        // }

        // while (z != null) {
        // leftRotate(T, z);
        // z = z.left;
        // }

        // }else {
        // T.root.left = null;
        // T.root.right = z;
        // // bring z up (to be the root) by rotating around its parent:
        // while (z.p != null) {
        // leftRotate(T, z.p); // O(1)
        // z = z.p;
        // }

        // z = T.root.right;
        // while (z != null) {
        // rightRotate(T, z);
        // z = z.right;
        // }

        // }

        // * way 3:
        insert(T, z);
        if (T.root.key > z.key) {
            while (z != null) {
                rightRotate(T, z.p); // O(1)
                z = z.p; // eventually z.p will be null (root node)
            }
        } else {
            while (z != null) {
                leftRotate(T, z.p); // O(1)
                z = z.p; // eventually z.p will be null (root node)
            }
        }
    }

    public static Node searchFreqUsed(BST T, int k) {
        Node z = search(T.root, k);
        delete(T, z);
        insertRoot(T, z);
        return z;
    }

    public static void main(String[] args) {
        BST T = new BST();

        Node z1 = new Node(4, 4);
        Node z2 = new Node(3, 4);
        Node z3 = new Node(2, 4);
        // Node z4 = new Node(1, 4);
        insert(T, z1);
        insert(T, z2);
        insert(T, z3);
        // insert(T, z4);

        Node x;
        x = search(T.root, 2); // left
        x = search(T.root, 4); // right
        x = search(T.root, 3); // root

        Node minMax;
        minMax = min(T.root);
        minMax = max(T.root);

        int size;
        size = size(T.root);

        // delete(T, z1);
        // delete(T, z3);
        // delete(T, z2);

        // inorder(T.root);
        // preorder(T.root);
        // postorder(T.root);

        checkBST(T);

        rightRotate(T, z2);
        // ! calling leftRotate (or rightRotate) on an unbalanced tree to the left (or the right) returns a null pointer exception
        // leftRotate(T, z2);

        Node z5 = new Node(1, 4);
        insertRoot(T, z5); // ! no duplicates
        search(T.root, 1); // O(1) to retrieve root

        // Boolean c = checkBST(T);
        Node r = searchFreqUsed(T, 3);
    }

}
