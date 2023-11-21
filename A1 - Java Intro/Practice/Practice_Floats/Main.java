public class Main {
	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		list.insert(1.0f);
		list.insert(2.0f);
		list.insert(3.0f);
		list.insert(4.0f);
		list.insert(5.0f);
		list.print();
		System.out.println();
		System.out.println(list.search(1.0f));
		System.out.println(list.search(2.0f));
		System.out.println(list.search(6.0f));
		System.out.println();
		System.out.println();
		list.prepend(8.0f);
		list.print();
		System.out.println();
		System.out.println();
		System.out.println(list.size());
		System.out.println();
		System.out.println();
		list.print_Every_Other_Node();
		System.out.println();
		System.out.println();
		list.insert_AtLocation(7.0f, 2);
		list.print();
		System.out.println();
		list.delete(1.0f);
		list.print();
		System.out.println();
		list.randInsert(9.0f);
		list.print();
		System.out.println();
		System.out.println(list.SecondToLast());
	}
}