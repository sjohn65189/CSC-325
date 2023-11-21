class Node {
	private Node[] children;
	private boolean isTerminal;
	
	public Node() {
		children = new Node[Trie.ALPHABET];
		for (int i = 0; i < children.length; i++) {
			children[i] = null;
		}
		isTerminal = false;
	}
	
	public Node[] getChildren() {
		return children;
	}
	
	public void setChildren(Node[] new_children) {
		this.children = new_children;
	}
	
	public boolean getIsTerminal() {
		return isTerminal;
	}
	
	public void setIsTerminal(boolean new_isTerminal) {
		this.isTerminal = new_isTerminal;
	}
}

class Trie {
	private Node root;
	public static final int ALPHABET = 128;
	
	public Trie() {
		this.root = null;
	}
	
	public void insert(String word) {
		if (root == null) {
			root = new Node();
		}
		
		Node curr = root;
		for (int i = 0; i < word.length(); i++) {
			int index = word.charAt(i); 
			
			if (curr.getChildren()[index] == null) {
				Node newNode = new Node();
				curr.getChildren()[index] = newNode;
			}
			curr = curr.getChildren()[index];
		}
		curr.setIsTerminal(true);
	}
	
	public void delete(String word) {
		iterateDelete(root, word);
	}

	private void iterateDelete(Node curr, String word) {		
		if (curr == null) {
			return;
		}
		
		int result = 0;
		for (int i = 0; i < word.length(); i++) {
			int character = word.charAt(i); 
			
			if (curr.getChildren()[character] == null) {
				return;
			} else {
				result++;
				curr = curr.getChildren()[character];
			}
		}	

		if (curr.getIsTerminal()) {
			curr.setIsTerminal(false);
		}
		
	}

	public boolean search(String word) {
		int result = 0;
		
		if (root == null) {
			return false;
		}
		
		Node curr = root;
		for (int l = 0; l < word.length(); l++) {
			for (int i = 0; i < curr.getChildren().length; i++) {
				char character = (char) i;
				if ((curr.getChildren()[i] != null) && (word.charAt(l)) == character) {
					result++;
					curr = curr.getChildren()[i];
				}
			}	
		}
		
		if (result == word.length()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void printAllWords() {
		StringBuilder word = new StringBuilder();
		Print(root, word);
	}
	
	private void Print(Node node, StringBuilder word) {
		if (node == null) {
			return;
		} else {
			for (int i = 0; i < node.getChildren().length; i++) {
				if (node.getChildren()[i] != null) {
					char character = (char) i;
					word.append(character);
					if (node.getChildren()[i].getIsTerminal()) {					
						System.out.print("'" + word.toString() + "', ");
					}
					Print(node.getChildren()[i], word);
					word.setLength(word.length() - 1);
				}
			}
		}
	}
	
	public void printAllWords(String prefix) {
		StringBuilder word = new StringBuilder();
		prefixPrint(prefix);
	}
	
	private void prefixPrint(String prefix) {
		Node curr = root;
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < prefix.length(); i++) {
			int character = prefix.charAt(i); 
			
			if (curr.getChildren()[character] == null) {
				return;
			} else {
				curr = curr.getChildren()[character];
				s.append((char) character);
				if (curr.getIsTerminal()) {
					System.out.print("'" + s.toString() + "', ");
				}
			}
		}
		Print(curr, s);
	}
}

