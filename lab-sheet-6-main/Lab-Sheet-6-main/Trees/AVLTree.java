class TreeAVL {
    class TreeNode {
        int value, height;
        TreeNode left, right;

        TreeNode(int value) {
            this.value = value;
            this.height = 1;
            left = right = null;
        }
    }

    private TreeNode root;

    // Get the height of the node
    private int getHeight(TreeNode node) {
        return node == null ? 0 : node.height;
    }

    // Get the balance factor
    private int getBalance(TreeNode node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    // Right rotate
    private TreeNode rightRotate(TreeNode y) {
        TreeNode x = y.left;
        TreeNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    // Left rotate
    private TreeNode leftRotate(TreeNode x) {
        TreeNode y = x.right;
        TreeNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;

        return y;
    }

    // Insert a node into AVL tree
    public void insert(int value) {
        root = insertRec(root, value);
    }

    private TreeNode insertRec(TreeNode node, int value) {
        if (node == null) {
            return new TreeNode(value);
        }

        if (value < node.value) {
            node.left = insertRec(node.left, value);
        } else if (value > node.value) {
            node.right = insertRec(node.right, value);
        } else {
            return node; // Duplicate values are not allowed
        }

        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;

        int balance = getBalance(node);

        // Left-left case
        if (balance > 1 && value < node.left.value) {
            return rightRotate(node);
        }

        // Right-right case
        if (balance < -1 && value > node.right.value) {
            return leftRotate(node);
        }

        // Left-right case
        if (balance > 1 && value > node.left.value) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right-left case
        if (balance < -1 && value < node.right.value) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // In-order traversal
    public void inOrder() {
        inOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(TreeNode node) {
        if (node != null) {
            inOrderRec(node.left);
            System.out.print(node.value + " ");
            inOrderRec(node.right);
        }
    }
}

public class AVLTree {
    public static void main(String[] args) {
        TreeAVL avl = new TreeAVL();

        // Insert nodes
        avl.insert(30);
        avl.insert(20);
        avl.insert(10);
       

        System.out.print("Root after balancing:  ");
        avl.inOrder(); 

        
    }
}
