import java.lang.*;

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * Based on code provided by Mark Allen Weiss (CS 2420 book author)
 */
public class AVLTree<E extends Comparable<? super E>> {
    /**
     * Construct the tree.
     */
    public AVLTree() {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     *
     * @param value the item to insert.
     */
    public void insert(E value) {
        root = insert(value, root);
    }

    public E deleteMin() {

        if (root == null) {
            return null;
        }

        AvlNode minNode = findMin(root);
        E minValue = minNode.value;

        root = deleteMin(root,null);


        return minValue;

    }


    private AvlNode findMin(AvlNode node1) {
        if (node1 == null) {
            return node1;
        }
        if (node1.left == null) {
            return node1;
        }
        AvlNode minValue = findMin(node1.left);
        return minValue;
    }

    private AvlNode deleteMin(AvlNode node, AvlNode parent) {

        //need a case to get to the min value
        if (node.left == null) {
            // case for if the root is the min
            if (parent == null) {
                return node.right;
            }
            // if the right is not null
            else {
                parent.left = node.right;
                return parent.left;
            }
        }

        parent = node;
        node.left = deleteMin(node.left, parent);
        return balance(node);

    }

    public void remove(E value) {
        remove(value, root);
    }

    private AvlNode remove(E value, AvlNode node) {
        if (node == null) {
            return node;
        }

        int compareResult = value.compareTo(node.value);

        if (compareResult < 0) {
            node.left = remove(value, node.left);
        } else if (compareResult > 0) {
            node.right = remove(value, node.right);
        } else if (node.left != null & node.right != null) {
            node.value = findMin(node.right).value;
            node.right = remove(node.value, node.right);
        } else {
            node = ( node.left != null ) ? node.left : node.right;
        }
        return balance(node);
    }

    /**
     * Find the largest item in the tree.
     *
     * @return the largest item of null if empty.
     */
    public E findMax() {
        if (isEmpty()) {
            throw new RuntimeException();
        }

        return findMax(root).value;
    }

     public boolean isIn(String word) {
        return isIn(word, root);
     }

     private boolean isIn(String word, AvlNode node) {
         while (node != null) {

             int compareResult = word.compareTo(node.value.toString());

             if (compareResult < 0) {
                 node = node.left;
             } else if (compareResult > 0) {
                 node = node.right;
             } else {
                 return true;    // Match
             }
         }

         return false;   // No match

     }

    /**
     * Find an item in the tree.
     *
     * @param value the item to search for.
     * @return true if x is found.
     */
    public boolean contains(E value) {
        return contains(value, root);
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree(String label) {
        System.out.println(label);
        StringBuilder sidewaysTree = new StringBuilder();
        int depth = 0;

        toString(root,sidewaysTree,depth);

        System.out.println(sidewaysTree.toString());
    }

    private void toString(AvlNode node, StringBuilder sidewaysTree, int depth) {
        // base case 1 string to the right
        if (node == null){
            return;
        }

        toString(node.right,sidewaysTree, depth);
        int depth1 = depth(root,node,depth);
        spaces(depth1,sidewaysTree);
        sidewaysTree.append(node.value.toString() +   "(" + node.height + ")\n");
        toString(node.left,sidewaysTree, depth);

    }

    private void spaces(int numOfSpaces, StringBuilder sidewaysTree) {

        //loop through to add the spaces
        for (int i=0; i < numOfSpaces; i++) {
            sidewaysTree.append("  ");
        }
    }

    private int depth(AvlNode node1, AvlNode node2, int depth) {
        if (node1 == null) {
            return 0;
        }
        if (node1 == node2)
            return depth;

        int result = depth(node1.left, node2, depth+1);
        if(result!=0){
            return result;
        }
        result = depth(node1.right,node2,depth+1);
        return result;


    }


    private static final int ALLOWED_IMBALANCE = 1;

    // Assume t is either balanced or within one of being balanced
    private AvlNode balance(AvlNode node) {
        if (node == null) {
            return null;
        }

        if (height(node.left) - height(node.right) > ALLOWED_IMBALANCE) {
            if (height(node.left.left) >= height(node.left.right)) {
                node = rightRotation(node);
            } else {
                node = doubleRightRotation(node);
            }
        } else if (height(node.right) - height(node.left) > ALLOWED_IMBALANCE) {
            if (height(node.right.right) >= height(node.right.left)) {
                node = leftRotation(node);
            } else {
                node = doubleLeftRotation(node);
            }
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return node;
    }

    public void checkBalance() {
        checkBalance(root);
    }

    private int checkBalance(AvlNode node) {
        if (node == null) {
            return -1;
        }

        int heightLeft = checkBalance(node.left);
        int heightRight = checkBalance(node.right);
        if (Math.abs(height(node.left) - height(node.right)) > ALLOWED_IMBALANCE ||
                height(node.left) != heightLeft ||
                height(node.right) != heightRight) {
            System.out.println("\n\n***********************OOPS!!");
        }

        return height(node);
    }


    /**
     * Internal method to insert into a subtree.  Duplicates are allowed
     *
     * @param value the item to insert.
     * @param node  the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode insert(E value, AvlNode node) {
        if (node == null) {
            return new AvlNode(value, null, null);
        }

        int compareResult = value.compareTo(node.value);

        if (compareResult < 0) {
            node.left = insert(value, node.left);
        } else {
            node.right = insert(value, node.right);
        }

        return balance(node);
    }

    /**
     * Internal method to find the largest item in a subtree.
     *
     * @param node the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode findMax(AvlNode node) {
        if (node == null) {
            return null;
        }

        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public E find(E value) {

        return find(root, value).value;

    }

    private AvlNode find(AvlNode node, E value) {
        int compare = value.compareTo(node.value);
        if (compare < 0) {
            node = find(node.left, value);
        }
        if (compare > 0) {
            node = find(node.right, value);
        }

        return node;

    }

    /**
     * Internal method to find an item in a subtree.
     *
     * @param value is item to search for.
     * @param node  the node that roots the tree.
     * @return true if x is found in subtree.
     */
    private boolean contains(E value, AvlNode node) {
        while (node != null) {
            int compareResult = value.compareTo(node.value);

            if (compareResult < 0) {
                node = node.left;
            } else if (compareResult > 0) {
                node = node.right;
            } else {
                return true;    // Match
            }
        }

        return false;   // No match
    }

    /**
     * Return the height of node t, or -1, if null.
     */
    private int height(AvlNode node) {
        if (node == null) {
            return -1;
        }

        return node.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private AvlNode rightRotation(AvlNode node) {
        AvlNode theLeft = node.left;
        node.left = theLeft.right;
        theLeft.right = node;
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        theLeft.height = Math.max(height(theLeft.left), node.height) + 1;
        return theLeft;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private AvlNode leftRotation(AvlNode node) {
        AvlNode theRight = node.right;
        node.right = theRight.left;
        theRight.left = node;
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        theRight.height = Math.max(height(theRight.right), node.height) + 1;
        return theRight;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private AvlNode doubleRightRotation(AvlNode node) {
        node.left = leftRotation(node.left);
        return rightRotation(node);
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private AvlNode doubleLeftRotation(AvlNode node) {
        node.right = rightRotation(node.right);
        return leftRotation(node);
    }

    private class AvlNode {
        AvlNode(E value, AvlNode left, AvlNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
            height = 0;
        }

        E value;      // The data in the node
        AvlNode left;         // Left child
        AvlNode right;        // Right child
        int height;       // Height
    }

    /**
     * The tree root.
     */
    private AvlNode root;
}
