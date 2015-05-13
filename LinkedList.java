import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E> implements IList<E> {

	private int size; // to store the size of the list
	private DoubleListNode<E> head; // dummy node for the front of the list
	private DoubleListNode<E> tail; // dummy node for the end of the list

	public LinkedList() { // Worst Case Big O: O(1)
		size = 0;
		head = new DoubleListNode<E>();
		tail = new DoubleListNode<E>();
		head.setNext(tail); // creates a list with nothing in it and the two placeholders linked
		tail.setPrev(head); // see above comment
	}

	private class LLIterator implements Iterator<E> { // Worst Case All Methods' Big O: O(1)
		
		private DoubleListNode<E> current; // the node the iterator is currently at
		private DoubleListNode<E> last; // the node last accessed with the next() method
		private int index; // position of the iterator

		public LLIterator() {
			current = head.getNext(); // set the current node to the first one in the list
			last = null; // will be set to a node when next() is run
			index = 0;
		}

		public boolean hasNext(){ return index < size; }

		public E next() {
			if (!hasNext()) // if there are no elements left in the list
				throw new NoSuchElementException("There is no next element.");
			last = current; // for the remove method
			E result = current.getData();
			current = current.getNext();
			index++;
			return result;
		}

		public void remove(){
			if (last == null)
				throw new IllegalStateException("No element has been accessed since last call to remove");
			DoubleListNode<E> a = last.getPrev();
			DoubleListNode<E> b = last.getNext();
			a.setNext(b);
			b.setPrev(a);
			size--;
			if (current == last)
				current = b;
			else
				index--;
			last = null; // remove has been run, can't run again until next is run at least once 
		}
	}


	public void add(E item) { // Worst Case Big O: O(1)
		if (item == null)
			throw new IllegalArgumentException("Item is null.");
		DoubleListNode<E> insert = new DoubleListNode<E>(tail.getPrev(), item, tail);
		tail.getPrev().setNext(insert);
		tail.setPrev(insert);
		size++;
	}


	public void insert(int pos, E item) { // Worst Case Big O: O(N)
		if (pos < 0 || pos > size || item == null)
			throw new IllegalArgumentException("Either your position is out of bounds or the item is null.");
		if (pos == size) // if the node being added is at the end
			add(item);
		else if (pos == 0) // if the node being added is at the beginning
			addFirst(item);
		else {
			DoubleListNode<E> temp = getNodeAtPos(pos-1);
			DoubleListNode<E> insert = new DoubleListNode<E>(temp, item, temp.getNext());
			temp.getNext().setPrev(insert);
			temp.setNext(insert);
			size++;
		}
	}


	public E set(int pos, E item) { // Worst Case Big O: O(N)
		if (pos < 0 || pos >= size || item == null)
			throw new IllegalArgumentException("Either your position is out of bounds or the item is null.");
		DoubleListNode<E> adjust = getNodeAtPos(pos);
		E result = adjust.getData(); // for returning
		adjust.setData(item);
		return result;
	}


	public E get(int pos) { // Wost Case Big O: O(N)
		if (pos < 0 || pos >= size)
			throw new IllegalArgumentException("The given position is out of bounds");
		return getNodeAtPos(pos).getData();
	}


	public E remove(int pos) { // Worst Case Big O: O(N)
		if (pos < 0 || pos >= size)
			throw new IllegalArgumentException("The given position is out of bounds");
		if (pos == 0) { // if removing from the front
			DoubleListNode<E> temp = head.getNext(); // Use the front dummy node
			head.setNext(temp.getNext());
			temp.getNext().setPrev(head);
			size--;
			return temp.getData();
		}
		else if (pos == size-1) { // if removing from the end
			DoubleListNode<E> temp = tail.getPrev(); // Use the end dummy node
			tail.setPrev(temp.getPrev());
			temp.getPrev().setNext(tail);
			size--;
			return temp.getData();
		}
		else { // if removing from anywhere else
			DoubleListNode<E> temp = getNodeAtPos(pos); // Use the node at the given position
			temp.getPrev().setNext(temp.getNext());
			temp.getNext().setPrev(temp.getPrev());
			size--;
			return temp.getData();
		}
	}


	public boolean remove(E obj) { // Worst Case Big O: O(N^2)
		if (obj == null)
			throw new IllegalArgumentException("The object is null.");
		boolean test = false; // To see if the object has been found
		DoubleListNode<E> toBeRemoved = new DoubleListNode<E>(); // The node that will be removed
		int i = 0;
		while (!test && i<size) { // two checks to ensure no out of bounds error
			DoubleListNode<E> temp = getNodeAtPos(i);
			if (temp.getData().equals(obj)) { // if they both contain the same object
				test = true; // to break out of while loop
				toBeRemoved = temp;
			}
			i++;
		}
		if (test) { // if an object was found
			toBeRemoved.getPrev().setNext(toBeRemoved.getNext());
			toBeRemoved.getNext().setPrev(toBeRemoved.getPrev());
			size--;
			return true;
		}
		return false;
	}


	public IList<E> getSubList(int start, int stop) { // Worst Case Big O: O(N^2)
		if (start < 0 || start >= size || stop < 0 || stop > size)
			throw new IllegalArgumentException("The given position is out of bounds");
		LinkedList<E> result = new LinkedList<E>(); // to be returned
		for (int i=start; i<stop; i++)
			result.add(getNodeAtPos(i).getData()); // add the data of the node, not the node itself
		return result;
	}


	public int size() { // Worst Case Big O: O(1)
		return size;
	}


	public int indexOf(E item) { // Worst Case Big O: O(N^2)
		if (item == null)
			throw new IllegalArgumentException("Item is null");
		int result = -1; // if item is not in the list, -1 will be returned
		boolean check = false; // to see if the first piece of data equal to item has been found
		for (int i=0; i<size && !check; i++) {
			if (getNodeAtPos(i).getData().equals(item)) { // if the item is found
				result = i; // assign result its position
				check = true;
			}
		}
		return result;
	}


	public int indexOf(E item, int pos) { // Worst Case Big O: O(N^2)
		if (pos < 0 || pos >= size || item == null)
			throw new IllegalArgumentException("Either your position is out of bounds or the item is null.");
		int result = -1; // will return -1 if the item is not found
		boolean check = false; // to see if the first piece of data equal to item has been found
		for (int i=pos; i<size && !check; i++) {
			if (getNodeAtPos(i).getData().equals(item)) {
				result = i;
				check = true;
			}
		}
		return result;
	}


	public void makeEmpty() { // Worst Case Big O: O(1)
		head.setNext(tail);
		tail.setPrev(head);
		size = 0;
	}


	public Iterator<E> iterator() { // Worst Case Big O: O(1)
		return new LLIterator();
	}


	public void removeRange(int start, int stop) { // Worst Case Big O: O(N^2)
		if (start < 0 || start >= size || stop < 0 || stop > size)
			throw new IllegalArgumentException("The given parameters are out of bounds.");
		for (int i=start; i<stop; i++)
			remove(start);
	}

	/**
	 * add item to the front of the list.
	 * <br>pre: item != null
	 * <br>post: size() = old size() + 1, get(0) = item
	 * @param item the data to add to the front of this list
	 */
	public void addFirst(E item){ // Worst Case Big O: O(1)
		if (size == 0)
			add(item);
		else {
			DoubleListNode<E> insert = new DoubleListNode<E>(head, item, head.getNext());
			head.getNext().setPrev(insert);
			head.setNext(insert);
			size++;
		}
	}


	/**
	 * add item to the end of the list.
	 * <br>pre: item != null
	 * <br>post: size() = old size() + 1, get(size() -1) = item
	 * @param item the data to add to the end of this list
	 */
	public void addLast(E item){ // Worst Case Big O: O(1)
		add(item);
	}


	/**
	 * remove and return the first element of this list.
	 * <br>pre: size() > 0
	 * <br>post: size() = old size() - 1
	 * @return the old first element of this list
	 */
	public E removeFirst(){	 // Worst Case Big O: O(N)
		E result = remove(0); // to be returned
		return result;
	}


	/**
	 * remove and return the last element of this list.
	 * <br>pre: size() > 0
	 * <br>post: size() = old size() - 1
	 * @return the old last element of this list
	 */
	public E removeLast(){ // Worst Case Big O: O(N)
		E result = remove(size-1); // to be returned
		return result;
	}


	public String toString(){ // Worst Case Big O: O(N^2)
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i=0; i<size; i++) {
			if (i == size-1)
				sb.append(getNodeAtPos(i).getData());
			else
				sb.append(getNodeAtPos(i).getData() + ", ");
		}
		sb.append("]");
		return sb.toString();
	}


	/**
	 * Check if this list is equal to another Object.
	 * Follow the contract of IList
	 * <br>pre: none
	 * @return true if other is a non null IList object
	 * with the same elements as this LinkedList in the same
	 * order.
	 */
	public boolean equals(Object other){ // Worst Case Big O: O(N^2)
		if (this == other)
			return true;
		if (!(other instanceof IList<?>))
			return false;
		if (size != ((LinkedList<?>)other).size)
			return false;
		for (int i=0; i<size; i++) {
			if (!(getNodeAtPos(i).getData().equals(((LinkedList<?>)other).getNodeAtPos(i).getData())))
				return false;
		}
		return true;
	}


	/**
	 * Gets the node at the given position by iterating through the list
	 * pre: 0 <= pos < size
	 * @return the node at the given position
	 */
	private DoubleListNode<E> getNodeAtPos(int pos) { // Worst Case Big O: O(N)
		if (pos < 0 || pos >= size)
			throw new IllegalArgumentException("The given position is out of bounds.");
		DoubleListNode<E> temp = head;
		temp = temp.getNext();
		for (int i=0; i<pos; i++)
			temp = temp.getNext();
		return temp;
	}
}
