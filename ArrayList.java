package project2;

public class ArrayList<T> implements List<T> {
	
	T [] arr;
	int size;
	
	private class ListIterator<T> implements Iterator<T> {
		
		private int nextItem;
		
		public ListIterator() {
			/*
			 * TODO:
			 * start at some arbitrary point: start at 4th item - it = arr.iterator(3)
			 * it.set(newItem) set current item into newItem
			 * it.add(item) in middle of list
			 * r = it.remove() remove from middle of list
			 */
			nextItem = 0;
		}

		@Override
		public boolean hasNext() {
			if (nextItem < size) {
				return true;
			}
			return false;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T next() {
			return (T) arr[nextItem++];
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList() {
		size = 0;
		arr = (T[]) new Object[10];
	}
	
	public Iterator<T> iterator() {
		return new ListIterator<T>();
	}

	@Override
	public void add(T item) throws Exception { // best: O(1), worst: O(n) calls growArr copies everything into arr[2x]
												// worst: O(1+1) / O(1) -- amortized take O(n) scatter over n items so 1, then copy + 1 so O(1+1)
		if (size == arr.length) { // arr is full so double array
			growArray();
		}
		arr[size++] = item;
	}
	
	private void growArray() {  
		@SuppressWarnings("unchecked")
		T [] newArr = (T[]) new Object[arr.length*2];
		for (int i = 0; i < arr.length; i++) {
			newArr[i] = arr[i];
		}
		arr = newArr;
	}

	@Override
	public void add(int pos, T item) throws Exception { // O(n) because if adding to first item, entire array shifts according to for loop
		if (pos < 0 || pos > size - 1) {
			throw new Exception("List index out of bounds");
		}
		if (size == arr.length) {
			growArray();
		}
		for (int i = size; i > pos; i--) { // copy backwards from array 
			arr[i] = arr[i-1];
		}
		arr[pos] = item;
		size++;
	}

	@Override
	public T get(int pos) throws Exception {
		if (pos < 0 || pos > size - 1) {
			throw new Exception("List index out of bounds");
		}
		return arr[pos];
	}

	@Override
	public T remove(int pos) throws Exception { // best case constant: remove last item no shift array, 
		if (pos < 0 || pos > size - 1) {		//worst case linear, remove first item and shift entire array back 1 
			throw new Exception("List index out of bounds");
		}
		
		T item = arr[pos];
		
		for (int i = pos; i < size - 1; i++) {
			arr[i] = arr[i + 1];
		}
		
		size--;
		return item;
	}

	@Override
	public int size() {
		return size;
	}
	
	public void clear() {
		for (int i = 0; i < size; i++) {
			arr[i] = null;
		}
		size = 0;
	}
	
	public String toString() {
		String list = "[";
		for (int i = 0; i < size; i++) {
			if (arr[i+1] == null) {
				list += arr[i];
			} else {
				list += arr[i] + ", ";
			}
		}
		list  += "]";
		return list;
	}
}
