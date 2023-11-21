public class LinkedList {
	public Node head;
	public Node tail;
	
	public LinkedList() {
		head = null;
		tail = null;
	}
	
	public void insert(float info) {
		Node newNode = new Node(info);
		Node current;
		if (head == null) {
			head = newNode;
			current = newNode;
			tail = newNode;
		} else {
			tail.link = newNode;	
			tail = newNode;
		}
	}
	
	public int search(float num) {
		Node current = head;
		int position = 0;
		while (current != null) {
			if (current.num == num) {
				return position;
			}
			current = current.link;
			position++;
		}
		return -1;
	}
		
	public void print() {
		Node current = head;
		while (current != null) {
			System.out.println(current.num + ", ");
			current = current.link;
		}
	}
	
	public void prepend(float info) {
		Node newNode = new Node(info);
		newNode.link = head;
		head = newNode;
	}

	public void delete(float num) {
		Node current = head;
		Node prev = null;

		// Handle the case when the node to be deleted is the head node
		if (current != null && current.num == num) {
			head = current.link;
			return; // Node deleted, exit the method
		}

		// Search for the node with the specified name
		while (current != null && !(current.num == num)) {
			prev = current;
			current = current.link;
		}

		// If the node with the specified name is found, delete it
		if (current != null) {
			prev.link = current.link;
			current.link = null;
		}
	}

	public void insert_AtLocation(float info, int pos) {
		Node newNode = new Node(info);
		Node current = head;
		Node previous = null;
		int position = 0;

		while (current != null && position != pos) {
			previous = current;
			current = current.link;
			position++;
		}

		if (position == pos) {
			newNode.link = current;
			if (previous == null) {
				// If the new node is being inserted at the head of the list
				head = newNode;
			} else {
				// Otherwise, update the previous node's link to point to the new node
				previous.link = newNode;
			}
		} else {
			// Handle the case where the position is out of bounds
			System.out.println("Position is out of bounds");
		}
	}

	
	public void randInsert(float info) {
		Node newNode = new Node(info);
		Node current = head;
		Node previous = null;
        long currentTimeMillis = System.currentTimeMillis();
        int randomIndex = (int) (currentTimeMillis % (this.size() + 1));
		int position = 0;

		while (current != null && randomIndex != position) {
			previous = current;
			current = current.link;
			position++;
		}

		if (randomIndex == position) {
			newNode.link = current;
			if (previous == null) {
				// If the new node is being inserted at the head of the list
				head = newNode;
			} else {
				// Otherwise, update the previous node's link to point to the new node
				previous.link = newNode;
			}
		} else {
			// Handle the case where the position is out of bounds
			System.out.println("Position is out of bounds");
		}
		
	}
	
	public void print_Every_Other_Node() {
		Node current = head;
		while (current != null) {
			System.out.println(current.num + ", ");
			current = current.link;
			current = current.link;
		}
	}
	
	public int size() {
		Node current = head;
		int sum = 0;
		while (current != null) {
			sum++;
			current = current.link;
		}
		return sum;
	}
	
	public float SecondToLast() {
		Node current = head;
		int position = 0;
		int second_to_last = this.size() - 2;

		while (current != null && position != second_to_last) {
			current = current.link;
			position++;
		}

		if (position == second_to_last) {
			return current.num;
		}
		return -1.0f;
	}
}