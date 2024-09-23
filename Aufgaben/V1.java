import java.util.*;


public class V1 extends AbstractList {

	private final ArrayList<Object> list;

	V1() {
		super();
		list = new ArrayList<>();
	}

	@Override
	public Object get(int index) {
		try {
			return list.get(index);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Index: " + index + " existiert nicht.");
		}
		return null;

	}


	public Object set(int index, Object element) {
		while (index >= list.size()) {
			list.add(null);
		}
		return list.set(index, element);


	}



	@Override
	public int size() {
		return list.size();
	}

	@Override
	public void sort(Comparator c) {
		list.sort(c);
	}

	@Override
	public Object remove(int index) {
		return list.remove(index);
	}


	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	public static void main(String[] args) {

		V1 list = new V1();

		list.set(0,"Server:");
		list.set(1,"hallo");
		list.set(2,"das");
		list.set(3,"ist");
		list.set(4,"ein");
		list.set(5,"V1");

		System.out.println(list.get(2));

		list.sort(Comparator.reverseOrder());
		list.stream().forEach(l -> System.out.println(l));
		list.remove(2);
		System.out.println(list.get(2));
		list.clear();
		System.out.println(list.get(2));

	}
}


