class BSearchTree {
    class TreeNode {
        int value;
        TreeNode left, right;

        TreeNode(int value) {
            this.value = value;
            left = right = null;
        }
    }

    private TreeNode root;

    // Insert a node
    public void insert(int value) {
        root = insertRec(root, value);
    }

    private TreeNode insertRec(TreeNode root, int value) {
        if (root == null) {
            root = new TreeNode(value);
            return root;
        }
        if (value < root.value) {
            root.left = insertRec(root.left, value);
        } else if (value > root.value) {
            root.right = insertRec(root.right, value);
        }
        return root;
    }

    // Search for a value
    public boolean search(int value) {
        return searchRec(root, value);
    }

    private boolean searchRec(TreeNode root, int value) {
        if (root == null) {
            return false;
        }
        if (value < root.value) {
            return searchRec(root.left, value);
        } else if (value > root.value) {
            return searchRec(root.right, value);
        } else {
            return true; // Value found
        }
    }

    // Delete a node
    public void delete(int value) {
        root = deleteRec(root, value);
    }

    private TreeNode deleteRec(TreeNode root, int value) {
        if (root == null)
            return root;

        if (value < root.value) {
            root.left = deleteRec(root.left, value);
        } else if (value > root.value) {
            root.right = deleteRec(root.right, value);
        } else {
            // Node with only one child or no child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Node with two children
            root.value = minValue(root.right);
            root.right = deleteRec(root.right, root.value);
        }
        return root;
    }

    private int minValue(TreeNode root) {
        int minValue = root.value;
        while (root.left != null) {
            minValue = root.left.value;
            root = root.left;
        }
        return minValue;
    }

    // In-order traversal
    public void inOrder() {
        inOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(TreeNode root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.print(root.value + " ");
            inOrderRec(root.right);
        }
    }
}

public class BinarySearchTree {
    public static void main(String[] args) {
        BSearchTree bst = new BSearchTree();

        // Insert nodes
        bst.insert(5);
        bst.insert(10);
        bst.insert(15);
        

        System.out.print("In-order traversal: ");
        bst.inOrder(); // Output: 5 10 15 

        System.out.println("Searching for 15: " + bst.search(15)); // Output: true
        System.out.println("Searching for 25: " + bst.search(25)); // Output: false

        bst.delete(20); // Deleting node with value 20
        System.out.print("In-order traversal after deletion: ");
        bst.inOrder(); // Output: 5 10 15 
    }
}
