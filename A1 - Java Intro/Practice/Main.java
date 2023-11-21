public class Main {
	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		list.insert("Dad");
		list.insert("Mom");
		list.insert("Stephan");
		list.insert("Darius");
		list.insert("Alaina");
		list.print();
		System.out.println();
		System.out.println(list.search("Stephan"));
		System.out.println(list.search("Darius"));
		System.out.println(list.search("John"));
		System.out.println();
		System.out.println();
		list.prepend("Adam");
		list.print();
		System.out.println();
		System.out.println();
		System.out.println(list.size());
		System.out.println();
		System.out.println();
		list.print_Every_Other_Node();
		System.out.println();
		System.out.println();
		list.insert_AtLocation("Bob", 2);
		list.print();
		System.out.println();
		list.delete("Adam");
		list.print();
		System.out.println();
		list.randInsert("Henry");
		list.print();
		System.out.println();
		System.out.println(list.SecondToLast());
		System.out.println();
		System.out.println(list.findAllWith('o'));
	}
}