/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Source // Source Source https://www.geeksforgeeks.org/avl-tree-set-2-deletion/
// Class Node  
class AVLNode {
    String key;
    int height;
    AVLNode left, right;

    AVLNode(String d) {
        key = d;
        height = 1;
    }
}

// Class AVL Tree
class SDA1819T3 {

    AVLNode root;
    
    // A utility function to get height of the tree  
    int height(AVLNode N) {
        if (N == null)
            return 0;
        return N.height;
    }

    // A utility function to get maximum of two integers  
    int max(int a, int b) {
        return (a > b)? a : b;
    }

    // A utility function to right rotate subtree rooted with y  
    // See the diagram given above.  
    AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Perform rotation  
        x.right = y;
        y.left = T2;

        // Update heights  
        y.height = max(height(y.left), height(y.right))+1;
        x.height = max(height(x.left), height(x.right))+1;

        // Return new root  
        return x;
    }

    // A utility function to left rotate subtree rooted with x  
    // See the diagram given above.  
    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Perform rotation  
        y.left = x;
        x.right = T2;

        // Update heights  
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        // Return new root  
        return y;
    }

    // Get Balance factor of node N  
    int getBalance(AVLNode N) {
        if (N == null)
            return 0;
        
        return height(N.left) - height(N.right);
    }

    // Source https://www.geeksforgeeks.org/avl-tree-set-1-insertion/
    AVLNode insert(AVLNode node, String key) {
        /* 1. Perform the normal BST rotation */
        if (node == null) {
            return (new AVLNode(key));
        }

        if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = insert(node.right, key);
        } else // Equal keys not allowed
            return node;

        /* 2. Update height of this ancestor node */
        node.height = max(height(node.left), height(node.right)) + 1;

        /* 3. Get the balance factor of this ancestor  
        node to check whether this node became  
        Wunbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases 
        
        //Left Left Case  
        if (balance > 1 && key.compareTo(node.left.key) < 0) {
            return rightRotate(node);
        }

        // Right Right Case  
        if (balance < -1 && key.compareTo(node.right.key) > 0) {
            return leftRotate(node);
        }

        // Left Right Case  
        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case  
        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }
       
    /* Given a non-empty binary search tree, return the  
    node with minimum key value found in that tree.  
    Note that the entire tree does not need to be  
    searched. */
    // Source https://www.geeksforgeeks.org/avl-tree-set-2-deletion/
    AVLNode minValueNodeLeft(AVLNode node) {
        AVLNode current = node;

        /* loop down to find the leftmost leaf */
        while (current.left != null) 
            current = current.left;
        
        return current;
    }
    
    AVLNode minValueNodeRight(AVLNode node) {
        AVLNode current = node;

        /* loop down to find the leftmost leaf */
        while (current.right != null) 
            current = current.right;
        
        return current;
    }
    
    // Source https://www.geeksforgeeks.org/avl-tree-set-2-deletion/
    // Recursive function to delete a node with given key 
    // from subtree with given root. It returns root of 
    // the modified subtree. 
    AVLNode deleteNodeLeft(AVLNode root, String key) {
        
        // STEP 1: PERFORM STANDARD BST DELETE  
        if (root == null) {
            return root;
        }

        // If the key to be deleted is smaller than  
        // the root's key, then it lies in left subtree  
        if (key.compareTo(root.key) < 0) {
            root.left = deleteNodeLeft(root.left, key);
        } 
        
        // If the key to be deleted is greater than the  
        // root's key, then it lies in right subtree  
        else if (key.compareTo(root.key) > 0) {
            root.right = deleteNodeLeft(root.right, key);
        } 

        // if key is same as root's key, then this is the node  
        // to be deleted  
        else {
            // node with only one child or no child  
            if ((root.left == null) || (root.right == null)) {
                AVLNode temp = null;
                if (temp == root.left) {
                    temp = root.right;
                } else {
                    temp = root.left;
                }

                // No child case  
                if (temp == null) {
                    temp = root;
                    root = null;
                } else { // One child case  
                    root = temp; // Copy the contents of  
                }                // the non-empty child  
            } else {

                // node with two children: Get the inorder  
                // successor (smallest in the right subtree)  
                AVLNode temp = minValueNodeLeft(root.right);

                // Copy the inorder successor's data to this node  
                root.key = temp.key;

                // Delete the inorder successor  
                root.right = deleteNodeLeft(root.right, temp.key);
            }
        }

        // If the tree had only one node then return  
        if (root == null) {
            return root;
        }

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE  
        root.height = max(height(root.left), height(root.right)) + 1;

        int balance;
        balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases  
        // Left Left Case  
        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }

        // Left Right Case  
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right Case  
        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }

        // Right Left Case  
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }
    
    AVLNode deleteNodeRight(AVLNode root, String key) {
        if (root == null) {
            return root;
        }

        if (key.compareTo(root.key) < 0) {
            root.left = deleteNodeRight(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            root.right = deleteNodeRight(root.right, key);
        } else {
            if ((root.left == null) || (root.right == null)) {
                AVLNode temp = null;
                if (temp == root.left) {
                    temp = root.right;
                } else {
                    temp = root.left;
                }

                // No child case  
                if (temp == null) {
                    temp = root;
                    root = null;
                } else { 
                    root = temp;   
                }                  
            } else {

                AVLNode temp = minValueNodeRight(root.left); 
                root.key = temp.key;
                root.left = deleteNodeRight(root.left, temp.key);
            }
        }

        if (root == null) {
            return root;
        }

        root.height = max(height(root.left), height(root.right)) + 1;

        int balance;
        balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }
 
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }
 
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }
    

    // A utility function to print preorder traversal of  
    // the tree. 
    // The function also prints height of every node  
    void preOrder(AVLNode node) {
        if (node != null) {
            System.out.print(node.key + ";");
            preOrder(node.left);
            preOrder(node.right);
        }
    }
    
    // Source https://www.geeksforgeeks.org/tree-traversals-inorder-preorder-and-postorder/
    void postOrder(AVLNode node) {
        if (node != null) {
            // first recur on left subtree 
            postOrder(node.left);
            // then recur on right subtree 
            postOrder(node.right);
            // now deal with the node 
            System.out.print(node.key + ";");
        }
    }
    
    // Source https://www.programcreek.com/2014/06/leetcode-count-complete-tree-nodes-java/
    public int countNodes(AVLNode node) {
        if (node == null)
            return 0;
        
        int left = getLeftHeight(node)+1;
        int right = getRightHeight(node)+1;
        
        if(left==right){
            return (2<<(left-1))-1;
        }else{
            return countNodes(node.left)+countNodes(node.right)+1;
        }
    }
    
    public int getLeftHeight(AVLNode n){
        if(n==null) 
            return 0;
 
        int height=0;
        
        while(n.left!=null){
            height++;
            n = n.left;
        }
        return height;
    }
 
    public int getRightHeight(AVLNode n){
        if(n==null) 
            return 0;
 
        int height=0;
        while(n.right!=null){
            height++;
            n = n.right;
        }
        return height;
    }
    
    // Contribute with Ari, Iffa
    public String sibling(AVLNode node, String keys) { 
        AVLNode parent = node;
        while (node != null) {
            if (node.key.compareTo(keys) < 0) {
                if(parent.right != null && parent.right.key.equals(keys) && parent.left != null){
                    return parent.left.key;
                }else if (parent.left != null && parent.left.key.equals(keys) && parent.right != null){
                    return parent.right.key;
                } else if (parent.right == null || parent.left == null){
                    return "TIDAK ADA";
                }
            }else if (node.key.compareTo(keys) > 0){
		parent = node;
		node = node.left;
				
            }else{
		parent = node;
                node = node.right;
            }				
        }
        return "TIDAK ADA";
    }
    
    // Source https://stackoverflow.com/questions/25151991/removing-all-leaves-from-a-binarytree
    public AVLNode removesLeaves(AVLNode node){
        if (node == null){
            return null;
        }
        if (node.left == null && node.right == null){
            System.out.print(node.key + ";");
            node = null;
            return node;
        }
        node.left = removesLeaves(node.left);
        node.right = removesLeaves(node.right);
        return node;
    }
    
     
    // Contribute with Iffa
    public static void main(String[] args) throws IOException {
        SDA1819T3 tree = new SDA1819T3();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int input = Integer.parseInt(reader.readLine());
        
        for (int i = 0; i < input; i++){
            String command[] = reader.readLine().split(" ");
            switch (command[0]) {
                case "GABUNG":
                    tree.root = tree.insert(tree.root,command[1]);
                    break;
                case "CEDERA":
                    tree.root = tree.deleteNodeLeft(tree.root,command[1]);
                    break;
                case "MENYERAH":
                    tree.root = tree.deleteNodeRight(tree.root,command[1]);
                    break;
                case "PRINT":
                    System.out.print("");
                    tree.preOrder(tree.root);
                    System.out.println("");
                    tree.postOrder(tree.root);
                    System.out.println("");
                    break;
                case "PANJANG-TALI":
                    int nodes = tree.countNodes(tree.root);
                    System.out.println(nodes);
                    break;
                case "PARTNER":
                    System.out.println(tree.sibling(tree.root, command[1]));
                    break;
                case "MUSIBAH":
                    System.out.println(tree.removesLeaves(tree.root));
                    break;
            }
        }
    }
}
