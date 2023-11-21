public class LinkedList {
	public Node head;
	public Node tail;
	
	public LinkedList() {
		head = tail = null;
	}
	
	public void append(int data) {
		Node newNode = new Node(data);
		if (head == null) {
			head = newNode;
			tail = newNode;
		}
		else {
			tail.link = newNode;
	//		tail = newNode;
		}
		tail = newNode;
	
	}
	
	public boolean search(int target) {
		boolean result = false;
		Node current = head;
		while (current != null) {
			if (current.data == target) {
				result = true;
				break;
			}
			current = current.link;
		}
		return result;
	}
	
	public void printAll() {
		Node current = head;
		while (current != null) {
			System.out.println(current.data + ", ");
			current = current.link;
		}
	}
}