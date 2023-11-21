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
		
		for (int l = 0; l < word.length(); l++) {
			for (int i = 0; i < curr.getChildren().length; i++) {
				char character = (char) i;
				if ((curr.getChildren()[i] != null) && (word.charAt(l)) == character) {
					result++;
					String wordTwo = word.substring(1);
					iterateDelete(curr.getChildren()[i], wordTwo);
				}
			}	
		}
		
		if (result == word.length()) {
			if (curr.getIsTerminal()) {
				boolean hasChildren = false;

				for (int i = 0; i < curr.getChildren().length; i++) {
					if (curr.getChildren()[i] != null) {
						hasChildren = true;
						break;
					}
				}

				if (hasChildren) {
					curr.setIsTerminal(false);
				} else {
					curr = null;
				}
			}
		}
	}


/*
	private void iterateDelete(Node curr, String word) {		
		if (curr == null) {
			return;
		}
		
		int result = 0;
		
		for (int l = 0; l < word.length(); l++) {
			for (int i = 0; i < curr.getChildren().length; i++) {
				char character = (char) i;
				if ((curr.getChildren()[i] != null) && (word.charAt(l)) == character) {
					result++;
					String wordTwo = word.substring(1);
					iterateDelete(curr.getChildren()[i], wordTwo);
				}
			}	
		}
		
		if (result == word.length()) {
			if (curr.getIsTerminal()) {
				for (int i = 0; i < curr.getChildren().length; i++) {
					if (curr.getChildren()[i] != null) {
						curr.setIsTerminal(false);
						break;
					} else {
						curr = null;
						return;
					}
				}
			} else {
				return;
			}
		}
	}
*/

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
		prefixPrint(root, word, prefix);
	}


	private void prefixPrint(Node node, StringBuilder word, String prefix) {
		if (node == null) {
			return;
		}  else {
			for (int i = 0; i < node.getChildren().length; i++) {
				if ((node.getChildren()[i] != null)) {				
					if (prefix.isEmpty() == false) {
						if ((char) i == prefix.charAt(0)) {
							if (node.getChildren()[i].getIsTerminal()) {
								char character = (char) i;
								word.append(character);
								System.out.print("'" + word.toString() + "', ");
							} else {
								char character = (char) i;
								word.append(character);
								String prefixTwo = prefix.isEmpty() ? "" : prefix.substring(1);
								prefixPrint(node.getChildren()[i], word, prefixTwo);
								word.setLength(word.length() - 1);
							}
						}
					} else {
						if (node.getChildren()[i].getIsTerminal()) {
							char character = (char) i;
							word.append(character);
							System.out.print("'" + word.toString() + "', ");
							prefixPrint(node.getChildren()[i], word, prefix);
							word.setLength(word.length() - 1);
						} else {
							char character = (char) i;
							word.append(character);
							prefixPrint(node.getChildren()[i], word, prefix);
							word.setLength(word.length() - 1);
						}
					}					
				}
			}
		}
	}
}


