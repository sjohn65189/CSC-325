import java.io.*;
import java.util.*;

public class TrieMain {
	public static void main(String[] args) {
		Trie trie = new Trie();
		trie.insert("banana");
		trie.insert("bandana");
		trie.insert("bandaid");
		trie.insert("bandage");
		trie.insert("letter");
		trie.insert("lettuce");
		trie.insert("let");
		trie.insert("tool");
		trie.insert("toy");
		trie.insert("toilet");
	
		boolean result = true; 
		while (result) {
			System.out.print("\033[H\033[2J"); // Clear the screen 
			
			System.out.println("{add WORD}: Add a word");
			System.out.println("{printAll}: Print all words");
			System.out.println("{startsWith PREFIX}: Print words that start with");
			System.out.println("{del WORD}: Add a word");
			System.out.println("{search WORD}: Match a specific word");
			System.out.println("{quit}: Quit");
			System.out.print("? ");
			
			Scanner myObj = new Scanner(System.in); 
			String line = myObj.nextLine(); 
			String[] characters = line.split(" ");		
			
			switch (characters[0]) {
				case "add": // Character Array's format: [{operation_input} {character_input}] 
					trie.insert(characters[1]);
					result = true; 
					break;
				case "printAll": // Character Array's format: [{operation_input}] 
					System.out.print("All words: ");
					trie.printAllWords();
					System.out.println();
					myObj.nextLine();
					result = true; 
					break;
				case "startsWith": 
					String start = String.format("All words that start with '%s': ", characters[1]);
					System.out.print(start);
					trie.printAllWords(characters[1]);
					System.out.println();
					myObj.nextLine();
					result = true; 
					break;
				case "del":
					trie.delete(characters[1]);
					result = true; 
					break;
				case "search": 
					String searchTrue = String.format("word '%s' was found in the trie ", characters[1]);
					String searchFalse = String.format("word '%s' was not found in the trie: ", characters[1]);

					if ((trie.search(characters[1])) == true) {
						System.out.print(searchTrue);
						System.out.println();
					} else {
						System.out.print(searchFalse);
						System.out.println();
					}
					myObj.nextLine();
					result = true; 
					break;
				case "quit": 
					result = false; 
					break;

			}
		}
/*
		} else if (args[0].equals("memuse")) {
			List<String> words = new ArrayList<>();
			
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNext()){
				words.add(scanner.next());
			}
			scanner.close();

			// replace 900 with whatever the max y value is that you want to
			// display. This is in MB so 900 would be 900MB
			Display.setYMax(900);
			// replace 15 with whatever you want the y increment to be. For 
			// example, at 15 you will see 0mb, 15mb, 30mb, 45mb, 60mb, and so on 
			// on the y axis, up to the y max
			Display.setYInc(15);
			
			Runtime rt = Runtime.getRuntime(); // do this once
			long heapMemoryInBytes; // create this once
			// now do this EVERY iteration as you add more words to the trie
//			heapMemoryInBytes = rt.totalMemory() - rt.freeMemory();
			
			int wordCount = 0;
			
			Trie trie = new Trie();
			for (String word : words) {
				trie.insert(word);
				heapMemoryInBytes = rt.totalMemory() - rt.freeMemory();
				wordCount++;
				Display.show(trie, heapMemoryInBytes, wordCount);
			}
			
			trie = null;
			rt.gc();
			
			HashSet<String> hashSet = new HashSet<>();
			for (String word : words) {
				hashSet.add(word);
				heapMemoryInBytes = rt.totalMemory() - rt.freeMemory();
				wordCount++;
				Display.show(hashSet, heapMemoryInBytes, wordCount);
			}
		}
	}

*/
	}
}

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
/*	
	public boolean search(String word) {
	}
*/	
	public void printAllWords() {
		StringBuilder word = new StringBuilder();
		InOrder(root, word);
	}
	
	private void InOrder(Node node, StringBuilder word) {
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
					InOrder(node.getChildren()[i], word);
					word.setLength(word.length() - 1);
				}
			}
		}
	}
	
	
	public void printAllWords(String prefix) {
		StringBuilder word = new StringBuilder();
		InOrderPrefix(root, word, prefix);
	}


	private void InOrderPrefix(Node node, StringBuilder word, String prefix) {
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
								String prefixTwo = prefix.substring(1);
								InOrderPrefix(node.getChildren()[i], word, prefixTwo);
								word.setLength(word.length() - 1);
							}
						}
					} else {
						if (node.getChildren()[i].getIsTerminal()) {
							char character = (char) i;
							word.append(character);
							System.out.print("'" + word.toString() + "', ");
						} else {
							char character = (char) i;
							word.append(character);
							String prefixTwo = prefix.substring(1);
							InOrderPrefix(node.getChildren()[i], word, prefixTwo);
							word.setLength(word.length() - 1);
						}
					}					
				}
			}
		}
	}

}

