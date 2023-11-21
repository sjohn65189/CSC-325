public class Main {
	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		list.append(5);
		list.append(4);
		list.append(399);
		System.out.println(list.search(5));
		System.out.println(list.search(4));
		System.out.println(list.search(399));
		System.out.println(list.search(8));
		list.printAll();
	}
}