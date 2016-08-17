package sorting;

import java.util.Random;

public class Quick<T extends Quick.Quickable> {

	private T[] array;
	private Random random;

	public Quick(T[] values) {

		this.array = values;
		random = new Random();
	}

	public <A extends Quick.Arrayable<T>> Quick(A data) {
		this.array = data.array();
		random = new Random();
	}

	public void sort() {

		quickSort(0, array.length - 1);
	}

	public void randomSort() {

		randomizedQuickSort(0, array.length - 1);
	}

	private void quickSort(int p, int r) {
		if (p < r) {
			int q = partition(p, r);
			quickSort(p, q - 1);
			quickSort(q + 1, r);
		}
	}

	private int partition(int p, int r) {

		int x = array[r].value();

		int i = p - 1;

		for (int j = p; j < r; j++) {
			if (array[j].value() < x) {
				i++;
				exchange(i, j);
			}
		}
		exchange(i + 1, r);
		return i + 1;
	}

	private int randomizedPartition(int p, int r) {

		int i = random.nextInt(r - p) + p;

		exchange(p, i);

		return partition(p, r);
	}

	private void randomizedQuickSort(int p, int r) {
		if (p < r) {

			int q = randomizedPartition(p, r);
			randomizedQuickSort(p, q - 1);
			randomizedQuickSort(q + 1, r);
		}
	}

	private void exchange(int index1, int index2) {
		T temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}

	public void print() {
		System.out.print("[");
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ",");
		}
		System.out.println("]");
	}

	public T[] getArray() {
		// TODO Auto-generated method stub
		return this.array;
	}

	// Interfaces
	public static interface Quickable {

		public int value();
	}

	public static interface Arrayable<T> {

		public T[] array();
	}

}
