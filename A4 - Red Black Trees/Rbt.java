//////////////////////////////////////
// author: Stephan Johnson			//
// date: 10/20/2023					//
// desc: A4 - Red Black Trees		//
//////////////////////////////////////


import java.io.*;
import java.util.*;

// RbtNode class
class RbtNode {
	private int data; // contains the number of the node
	private byte color; // contains the color of the node
	public static final byte CL_RED = 0; // represents the color red
	public static final byte CL_BLACK = 1; // represents the color blue
	private RbtNode left; // pointer to the left RbtNode
	private RbtNode right; // pointer to the Right RbtNode
	private RbtNode parent; // pointer to the Parent RbtNode
	private RbtNode uncle; // pointer to the Uncle RbtNode
	private RbtNode grandparent; // pointer to the Grandparent RbtNode
	
	// RbtNode constructor
	public RbtNode(int data) {
		this.data = data; // initializes the data 
		this.color = CL_RED; // initializes the color to be red
		left = null; // initializes the left RbtNode point to be null
		right = null; // initializes the Right RbtNode point to be null
		parent = null; // initializes the Parent RbtNode point to be null
	}
	
	// Accessor for the data
	public int getData() {
		return this.data;
	}
	
	// Mutator for the data
	public void setData(int data) {
		this.data = data;
	}
	
	// Accessor for the color
	public byte getColor() {
		return this.color;
	}
	
	// Mutator for the color
	public void setColor(byte color) {
		this.color = color;
	}	
	
	// Accessor for the left
	public RbtNode getLeft() {
		return this.left;
	}
	
	// Mutator for the left
	public void setLeft(RbtNode left) {
		this.left = left;
	}
	
	// Accessor for the right
	public RbtNode getRight() {
		return this.right;
	}
	
	// Mutator for the right
	public void setRight(RbtNode right) {
		this.right = right;
	}
	
	// Accessor for the parent
	public RbtNode getParent() {
		return this.parent;
	}
	
	// Mutator for the parent
	public void setParent(RbtNode parent) {
		this.parent = parent;
	}
	
	// Accessor for the uncle
    public RbtNode getUncle() {
		RbtNode grandparent = this.getGrandparent();
		if (grandparent == null) { // if there is no grandparent, then there is no uncle
			return null; 
		}
		if (this.getParent() == grandparent.getLeft()) { // if the parent is to the left of the grandparent, the uncle is to the right of the grandparent
			return grandparent.getRight();
		} else { // if the parent is to the right of the grandparent, the uncle is to the left of the grandparent
			return grandparent.getLeft();
		}
    }
	
	// Accessor for the grandparent
	public RbtNode getGrandparent() { 
        if (this.parent != null) { 
            return this.parent.getParent();
        }
        return null; // if there is no parent, then there is no grandparent
	}

}

class Rbt {
	public RbtNode root; // pointer to the root node
	
	public Rbt() {
		this.root = null; // initializes the root to be null
	}
	
    public void insert(int value) {
        RbtNode newNode = new RbtNode(value); // makes a new RbtNode with the value

        if (root == null) { // if the tree is empty, make the new RbtNode the root and color it black
            root = newNode; // makes the new RbtNode the root 
            root.setColor(RbtNode.CL_BLACK); // sets the color to black
        } else { // if the tree is not empty
            insertHelper(root, newNode); // performs a standard BST insertion, starting with the root
            fixViolations(newNode); // fixes any Red-Black Tree violations after the BST insertion
        }

    }

    private void insertHelper(RbtNode node, RbtNode newNode) {		
        if (newNode.getData() < node.getData()) { // checks to see if the new RbtNode's data is less than the current node's data
            if (node.getLeft() != null) { // checks to see if the left child of the current node exists
                insertHelper(node.getLeft(), newNode); // recursively calls on the left child of the current node
                return;
            } else { // checks to see if the left child of the current node does not exist
				node.setLeft(newNode); // sets the new RbtNode as the left child of the current node
				newNode.setParent(node); // sets the parent of the new RbtNode to the current node
			}
        } else { // checks to see if the new RbtNode's data is more than the current node's data
            if (node.getRight() != null) { // checks to see if the right child of the current node exists
                insertHelper(node.getRight(), newNode); // recursively calls on the right child of the current node
                return; 
            } else { // checks to see if the left child of the current node does not exist
				node.setRight(newNode); // sets the new RbtNode as the right child of the current node
				newNode.setParent(node); // sets the parent of the new RbtNode to the current node
			}
        }
    }

	private void fixViolations(RbtNode node) {
		if (node.getParent() == null) { // Rule 1: If tree is empty, make the current node the root node and set the current node as black - root property
			node.setColor(RbtNode.CL_BLACK); // sets the color to black
		} else if (node.getParent().getColor() == RbtNode.CL_BLACK) {	// Rule 2: checks to see if the parent is black, the tree properties are not violated
			return;
		} else if (node.getParent().getColor() == RbtNode.CL_RED) { // Rule 3: checks to see if the parent is red, check for the uncle's color
			RbtNode uncle = node.getUncle(); // gets the uncle of the node
			if (uncle != null && uncle.getColor() == RbtNode.CL_RED) { // Rule 3A: checks to see if the uncle is red
				node.getParent().setColor(RbtNode.CL_BLACK); // sets the color to black
				uncle.setColor(RbtNode.CL_BLACK); // sets the color to black
				RbtNode grandparent = node.getGrandparent(); // gets the grandparent of the node
				grandparent.setColor(RbtNode.CL_RED); // sets the color to red
				fixViolations(grandparent); // recursively calls on the grandparent
			} else { // Rule 3B: checks to see if the uncle is black or null
				if (node == node.getParent().getRight() && node.getParent() == node.getGrandparent().getLeft()) { // Left Right Case: checks to see if the current node is the right child of its parent, and if the parent is the left child of its grandparent
					rotateLeft(node.getParent()); // performs a left rotation on the current node's parent
					node = node.getLeft(); // updates the current node after the rotation
				} else if (node == node.getParent().getLeft() && node.getParent() == node.getGrandparent().getRight()) { // Right Left Case: checks to see if the current node is the left child of its parent, and if the parent is the right child of its grandparent
					rotateRight(node.getParent()); // performs a right rotation on the current node's parent
					node = node.getRight(); // updates the current node after the rotation
				}
				
				RbtNode parent = node.getParent(); // gets the parent of the node
				RbtNode grandparent = node.getGrandparent(); // gets the grandparent of the node

				parent.setColor(RbtNode.CL_BLACK); // sets the parent's color to black
				grandparent.setColor(RbtNode.CL_RED); // sets the grandparent's color to red

				if (node == parent.getLeft() && parent == grandparent.getLeft()) { // Left Left Case: checks to see if the current node is the left child of its parent and if the parent is the left child of its grandparent
					rotateRight(grandparent); // performs a right rotation on the current node's grandparent
				} else { // Right Right Case: checks to see if the current node is the right child of its parent and if the parent is the right child of its grandparent
					rotateLeft(grandparent); // performs a left rotation on the current node's grandparent
				}
			}
		}
	}

	private void rotateLeft(RbtNode node) { // performs the left rotation
		RbtNode rightChild = node.getRight(); // gets the right child of 'node'
		node.setRight(rightChild.getLeft()); // adjusts the right child of 'node' to be the left child of 'rightChild'
		
		if (rightChild.getLeft() == null) { // checks to see if 'rightChild's left child is null
			rightChild.setParent(node.getParent()); // sets 'rightChild's parent to be 'node's parent
		} else if (rightChild.getLeft() != null) { // checks to see if 'rightChild's left child is not null
			rightChild.getLeft().setParent(node); // sets the left child's parent to be 'node'
		}
		
		if (node.getParent() == null) { // checks to see if 'node' was the root
			root = rightChild; // sets 'rightChild' as the new root
		} else if (node == node.getParent().getLeft()) { // checks to see if 'node' was the left child of its parent
			node.getParent().setLeft(rightChild); // sets 'rightChild' as the new left child of 'node's parent
		} else { // checks to see if 'node' was the right child of its parent, set 'rightChild' as the new right child of 'node's parent
			node.getParent().setRight(rightChild); // sets 'rightChild' as the new right child of 'node's parent
		}

		rightChild.setLeft(node); // sets 'node' as the left child of 'rightChild'
		node.setParent(rightChild); // updates 'node' to have 'rightChild' as its parent
	}

	private void rotateRight(RbtNode node) { // performs the right rotation
		RbtNode leftChild = node.getLeft(); // gets the left child of 'node'
		node.setLeft(leftChild.getRight()); // adjusts the left child of 'node' to be the right child of 'leftChild'
		
		if (leftChild.getRight() == null) { // checks to see if 'leftChild's right child is null
			leftChild.setParent(node.getParent()); // sets 'leftChild's parent to be 'node's parent
		} else if (leftChild.getRight() != null) { // checks to see if 'leftChild's right child is not null
			leftChild.getRight().setParent(node); // sets the right child's parent to be 'node'
		}
		
		if (node.getParent() == null) { // checks to see if 'node' was the root
			root = leftChild; // sets 'leftChild' as the new root
		} else if (node == node.getParent().getRight()) { // checks to see if 'node' was the right child of its parent
			node.getParent().setRight(leftChild); // sets 'leftChild' as the new right child of 'node's parent
		} else { // checks to see if 'node' was the left child of its parent
			node.getParent().setLeft(leftChild); // sets 'leftChild' as the new left child of 'node's parent
		}
		
		leftChild.setRight(node); // sets 'node' as the right child of 'leftChild'
		node.setParent(leftChild); // updates 'node' to have 'leftChild' as its parent
	}

	public boolean search(int value) {
		if (root == null) { // if the tree is empty, then there is nothing to be searched
			return false;
		} else { // if the tree is not empty
			return searchHelper(root, value); // performs a standard BST search, starting with the root
		}
	}

    private boolean searchHelper(RbtNode node, int value) {
        if (node != null) { // if the node does exist
			if (node.getData() == value) { // checks to see if the data of the node matches with the number that the search method is searching for
				return true; // the number that the search method is searching for does exist
			} else if (value < node.getData()) { // checks to see if the number that the search method is searching for is less than the node's data
				return searchHelper(node.getLeft(), value); // recursively calls on the left child of the node
			} else { // checks to see if the number that the search method is searching for is more than the node's data
				return searchHelper(node.getRight(), value); // recursively calls on the right child of the node
			}
        }
		return false; // the number that the search method is searching for does not exist
    }	
	
	public int min() { 
		if (root == null) { // if the tree is empty, then there is no minimum
			return -1;
		} else { // if the tree is not empty
			return minHelper(root); // performs a standard BST search for the minimum, starting with the root
		}
	}
	
    private int minHelper(RbtNode node) {
		while (node.getLeft() != null) { // repeatedly checks to see if the left child of the current node exist
			node = node.getLeft(); // updates the current node
		}
		return node.getData(); // the mimimum value of the tree
    }
	
	public int max() {
		if (root == null) { // if the tree is empty, then there is no maximum
			return -1;
		} else { // if the tree is not empty
			return maxHelper(root); // performs a standard BST search for the maximum, starting with the root
		}
	}
	
    private int maxHelper(RbtNode node) {
		while (node.getRight() != null) { // repeatedly checks to see if the right child of the current node exist
			node = node.getRight(); // updates the current node
		}
		return node.getData(); // the maximum value of the tree
    }
	
	
	public int size() {
		if (root == null) { // if the tree is empty, then the size is zero
			return 0;
		} else { // if the tree is not empty
			return sizeHelper(root); // performs a standard BST inorder traversal, starting with the root
		}
	}
	
	private int sizeHelper(RbtNode node) {
		int size = 0; // size counter
		if (node == null) { // if the current node is empty, then the size is zero
			return 0;
		}

		size += sizeHelper(node.getLeft()); // recursively calls on the left child of the node
		if (node != null) { // checks to see if the current node exist
			size++; // increase the size counter by one
		}
		size += sizeHelper(node.getRight()); // recursively calls on the right child of the node

		return size;
	}
	
    public void delete(int value)
    {
		boolean exist = search(value); // checks to see if the number that the delete method is looking for does or does not exist by performing a standard BST search 
		if (exist == false) { // checks to see if the number that the delete method is looking for does not exist
			return;
		} else { // checks to see if the number that the delete method is looking for does exist
			deleteHelper(root, value); // performs a standard BST deletion, starting with the root
		}
    }	


	private void deleteHelper(RbtNode node, int value) {
        if (value < node.getData()) { // checks to see if the number that the delete method is looking for is less than the current node's data
            if (node.getLeft() != null) { // checks to see if the left child of the current node exists
                deleteHelper(node.getLeft(), value); // recursively calls on the left child of the current node
				return;
			}			
        } else if (value > node.getData()) { // checks to see if the number that the delete method is looking for is more than the current node's data
            if (node.getRight() != null) { // checks to see if the right child of the current node exists
                deleteHelper(node.getRight(), value); // recursively calls on the right child of the current node
				return;
			}
        } else if (value == node.getData()) { // checks to see if the number that the delete method is looking for is equal to the current node's data
			if (node.getLeft() != null && node.getRight() != null) { // checks to see if the current node has two children
				RbtNode successor = Successor(node); // gets the successor node of the current node
				node.setData(successor.getData()); // sets the data of the current node to the data of the successor node
				deleteHelper(node.getRight(), successor.getData()); // recursively calls on the right child of the current node
			} else { // checks to see if the current node has one child or no children
				RbtNode parent = node.getParent(); // gets the current node's parent	
				
				RbtNode child;
				if (node.getLeft() != null) { // checks to see if the left child of the current node exist
					child = node.getLeft(); // initializes the child pointer to the left child of the current node
				} else { // checks to see if the right child of the current node exist
					child = node.getRight(); // initializes the child pointer to the left child of the current node
				}
				
				if (parent == null) { // checks to see if the parent of the current node does not exist
					root = child; // initializes the child pointer as the root
					if (child != null) { // checks to see if the child pointer exist
						child.setParent(null); // sets its parent point to null
					}
				} else { // checks to see if the parent of the current node does exist
					if (node == parent.getLeft()) { // checks to see if the current node is the left child of the parent
						parent.setLeft(child);
					} else { // checks to see if the current node is the right child of the parent
						parent.setRight(child);
					}

					if (child != null) { // checks to see if the child pointer does exist
						child.setParent(parent); // sets the parent of child pointer to the current node's parent
					}
				}

				if (node.getColor() == RbtNode.CL_BLACK) { // checks to see if the color of the current node is black
					fixDeleteViolations(child); // calls fixDeleteViolations after node deletion
				}
			}
		}
	}
	
    private RbtNode Successor(RbtNode node) {
		if (node.getRight() != null) { // checks to see if the right child of the current node exist
			node = node.getRight(); // updates the current node
			
			while (node.getLeft() != null) { // repeatedly checks to see if the left child of the current node exist
				node = node.getLeft(); // updates the current node
			}
		}
		return node;
	}

	private void fixDeleteViolations(RbtNode node) {
		RbtNode sibling;

		while (node != root && (node == null || node.getColor() == RbtNode.CL_BLACK)) { // repeatedly checks to see if the current node is not the root, and if the currrent node does not exist along with having the color black
			if (node == node.getParent().getLeft()) { // checks to see if the current node is the left child of the parent
				sibling = node.getParent().getRight(); // initializes the sibling pointer as the right child of the parent

				if (sibling.getColor() == RbtNode.CL_RED) { // Rule 1: checks to see if the color of the sibling is red
					sibling.setColor(RbtNode.CL_BLACK); // sets the sibling's color to black
					node.getParent().setColor(RbtNode.CL_RED); // sets the current node's parent's color to red
					rotateLeft(node.getParent()); // performs a left rotation on the current node's parent
					sibling = node.getParent().getRight(); // updates the sibling node
				}

				if ((sibling.getLeft() == null || sibling.getLeft().getColor() == RbtNode.CL_BLACK) && (sibling.getRight() == null || sibling.getRight().getColor() == RbtNode.CL_BLACK)) { // Rule 2: checks to see if both of the sibling's children exist as well as being both black
					sibling.setColor(RbtNode.CL_RED); // sets the sibling's color to red
					node = node.getParent(); // updates the parent node
				} else {
					if (sibling.getRight() == null || sibling.getRight().getColor() == RbtNode.CL_BLACK) { // Rule 3: checks to see if the right child of the sibling is black, and if the left child of the sibling is red
						sibling.getLeft().setColor(RbtNode.CL_BLACK); // sets the color of the left child of the sibling to black
						sibling.setColor(RbtNode.CL_RED); // sets the sibling's color to red
						rotateRight(sibling); // performs a right rotation on the sibling
						sibling = node.getParent().getRight(); // updates the sibling node
					}
					
					// Rule 4: checks to see if the right child of the sibling is red
					sibling.setColor(node.getParent().getColor()); // sets the sibling's color to the color of the parent of the current node
					node.getParent().setColor(RbtNode.CL_BLACK); // sets the current node's parent's color to black
					sibling.getRight().setColor(RbtNode.CL_BLACK); // sets the color of the right child of the sibling to black
					rotateLeft(node.getParent()); // performs a left rotation on the parent of the current node
					node = root; // sets the current node as the root
				}
			} else { // checks to see if the current node is the right child of the parent
				sibling = node.getParent().getLeft(); // initializes the sibling pointer as the left child of the parent

				if (sibling.getColor() == RbtNode.CL_RED) { // Rule 1: checks to see if the color of the sibling is red
					sibling.setColor(RbtNode.CL_BLACK); // sets the sibling's color to black
					node.getParent().setColor(RbtNode.CL_RED); // sets the current node's parent's color to red
					rotateRight(node.getParent()); // performs a right rotation on the current node's parent
					sibling = node.getParent().getLeft(); // updates the sibling node
				}

				if ((sibling.getRight() == null || sibling.getRight().getColor() == RbtNode.CL_BLACK) && (sibling.getLeft() == null || sibling.getLeft().getColor() == RbtNode.CL_BLACK)) { // Rule 2: checks to see if both of the sibling's children exist as well as being both black
					sibling.setColor(RbtNode.CL_RED); // sets the sibling's color to red
					node = node.getParent(); // updates the parent node
				} else {
					if (sibling.getLeft() == null || sibling.getLeft().getColor() == RbtNode.CL_BLACK) { // Rule 3: checks to see if the left child of the sibling is black, and if the right child of the sibling is red
						sibling.getRight().setColor(RbtNode.CL_BLACK); // sets the color of the right child of the sibling to black
						sibling.setColor(RbtNode.CL_RED); // sets the sibling's color to red
						rotateLeft(sibling); // performs a left rotation on the sibling
						sibling = node.getParent().getLeft(); // updates the sibling node
					}
					
					// Rule 4: checks to see if the left child of the sibling is red
					sibling.setColor(node.getParent().getColor()); // sets the sibling's color to the color of the parent of the current node
					node.getParent().setColor(RbtNode.CL_BLACK); // sets the current node's parent's color to black
					sibling.getLeft().setColor(RbtNode.CL_BLACK); // sets the color of the left child of the sibling to black
					rotateRight(node.getParent()); // performs a right rotation on the parent of the current node
					node = root; // sets the current node as the root
				}
			}
		}

		if (node != null) { // checks to see if the current node exist
			node.setColor(RbtNode.CL_BLACK); // sets the color of the current node to black
		}
	}

    public String inorder() {
        StringBuilder result = new StringBuilder(); // contains the string to display all of the tree's data
        inorderHelper(root, result); // performs a standard BST inorder traversal, starting with the root
        return result.toString().trim();
    }

    private void inorderHelper(RbtNode node, StringBuilder result) {
        if (node != null) { // checks to see if the current node exist
            inorderHelper(node.getLeft(), result); // recursively calls on the left child of the node
			result.append(node.getData() + " "); // add the current node's data into the string
            inorderHelper(node.getRight(), result); // recursively calls on the right child of the node
        }
    }
}