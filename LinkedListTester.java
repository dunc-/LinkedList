/*
 * Test results: 
 * 1. Adding to the end: ArrayList and LinkedList were about the same in terms of time, as both operations are O(1)
 *    and the timing data grew in a linear pattern.
 * 2. Adding to the front: LinkedList was faster, using a method that runs in O(1) time, whereas ArrayList's method
 *    ran in O(N) time because the data had to be shifted down in the storage container.
 * 3. Removing from the front: LinkedList was faster, running in O(1) time, whereas ArrayList ran in O(n) time because
 *    the data had to be shifted forward in the storage container.
 * 4. Getting random: ArrayList was faster in O(1) time, whereas LinkedList ran in O(N) time because of the need to go
 *    through each individual element using a for loop until the specified position is reached.
 * 5. Getting all using iterator: ArrayList was faster, but both ArrayList and LinkedList ran in O(N) time where
 *    N was the number of elements in the array.
 * 6. Getting all using get method: ArrayList was faster, running in O(N) time, whereas LinkedList ran in O(N^2) time
 *    due to the lack in internal storage container. The get() method has to use a for loop to go until the
 *    desired position is reached.
 */

import java.util.Iterator;

public class LinkedListTester {

	public static void main(String[] args) {

		LinkedList<String> list = new LinkedList<String>();
		
		//
		// Tests for add()
		//
		
		// Test 1
		list.add("B");
		checkEqualsString("[B]", list, 1);
		
		// Test 2
		list.add("ZZZ");
		checkEqualsString("[B, ZZZ]", list, 2);
		
		
		// Test 3
		list.add("CS");
		checkEqualsString("[B, ZZZ, CS]", list, 3);
		
		//
		// Tests for insert()
		//
		
		// Test 4
		list.insert(0, "insert");
		checkEqualsString("[insert, B, ZZZ, CS]", list, 4);
		
		// Test 5
		list.insert(2, "third");
		checkEqualsString("[insert, B, third, ZZZ, CS]", list, 5);
		
		// Test 6
		list.insert(5, "end");
		checkEqualsString("[insert, B, third, ZZZ, CS, end]", list, 6);
		
		//
		// Tests for set()
		//
		
		// Test 7
		list.set(0, "first");
		checkEqualsString("[first, B, third, ZZZ, CS, end]", list, 7);
		
		// Test 8
		list.set(3, "fourth");
		checkEqualsString("[first, B, third, fourth, CS, end]", list, 8);
		
		// Test 9
		list.set(5, "last");
		checkEqualsString("[first, B, third, fourth, CS, last]", list, 9);
		
		//
		// Tests for get()
		//
		
		// Test 10
		String s = list.get(0);
		checkEqualsStringTwo("first", s, 10);
		
		// Test 11
		s = list.get(1);
		checkEqualsStringTwo("B", s, 11);
		
		// Test 12
		s = list.get(5);
		checkEqualsStringTwo("last", s, 12);
		
		//
		// Tests for remove(int pos)
		//
		
		// Test 13
		list.remove(0);
		checkEqualsString("[B, third, fourth, CS, last]", list, 13);
		
		// Test 14
		list.remove(1);
		checkEqualsString("[B, fourth, CS, last]", list, 14);
		
		// Test 15
		list.remove(1);
		checkEqualsString("[B, CS, last]", list, 15);
		
		//
		// Tests for remove(E obj)
		//
		
		// Test 16
		list.remove("B");
		checkEqualsString("[CS, last]", list, 16);
		
		// Test 17
		list.remove("CS");
		checkEqualsString("[last]", list, 17);
		
		// Test 18
		list.remove("notThere");
		checkEqualsString("[last]", list, 18);
		
		//
		// Tests for getSubList()
		//
		
		list.insert(0, "first");
		list.insert(1, "middle");
		
		// Test 19
		LinkedList<String> list2 = (LinkedList<String>) list.getSubList(0, list.size());
		checkEqualsString("[first, middle, last]", list2, 19);
		
		// Test 20
		list2 = (LinkedList<String>) list.getSubList(0, 2);
		checkEqualsString("[first, middle]", list2, 20);
		
		// Test 21
		list2 = (LinkedList<String>) list.getSubList(1, 1);
		checkEqualsString("[]", list2, 21);
		
		//
		// Tests for size()
		//
		
		// Test 22
		checkEqualsInt(3, list.size(), 22);
		
		// Test 23
		checkEqualsInt(0, list2.size(), 23);
		
		list.add("dummy");
		
		// Test 24
		checkEqualsInt(4, list.size(), 24);
		
		//
		// Tests for indexOf(E item)
		//
		
		// Test 25
		checkEqualsInt(0, list.indexOf("first"), 25);
		
		// Test 26
		checkEqualsInt(1, list.indexOf("middle"), 26);
		
		// Test 27
		checkEqualsInt(-1, list.indexOf("moodle"), 27);
		
		//
		// Tests for indexOf(E item, int pos)
		//
		
		// Test 28
		checkEqualsInt(0, list.indexOf("first", 0), 28);
		
		// Test 29
		checkEqualsInt(-1, list.indexOf("middle", 2), 29);
		
		// Test 30
		checkEqualsInt(3, list.indexOf("dummy", 2), 30);
		
		//
		// Tests for makeEmpty()
		//
		
		// Test 31
		list2.makeEmpty();
		checkEqualsString("[]", list2, 31);
		
		// Test 32
		list2.makeEmpty();
		checkEqualsString("[]", list2, 32);
		
		// Test 33
		list.makeEmpty();
		checkEqualsString("[]", list, 33);
		
		//
		// Tests for iterator()
		//
		
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		
		// Test 34
		Iterator<String> listIterator = list.iterator();
		checkEqualsBoolean(true, listIterator.hasNext(), 34);
		
		// Test 35
		listIterator.next();
		listIterator.remove();
		checkEqualsString("[B, C, D, E]", list, 35);
		
		// Test 36
		listIterator.next();
		listIterator.next();
		listIterator.remove();
		checkEqualsString("[B, D, E]", list, 36);
		
		//
		// Tests for removeRange()
		//
		
		list.insert(0, "A");
		list.insert(2, "C");
		list.add("F");
		
		// Test 37
		list.removeRange(0, 1);
		checkEqualsString("[B, C, D, E, F]", list, 37);
		
		// Test 38
		list.removeRange(1, 2);
		checkEqualsString("[B, D, E, F]", list, 38);
		
		// Test 39
		list.removeRange(1, 3);
		checkEqualsString("[B, F]", list, 39);
		
		//
		// Tests for addFirst
		//
		
		// Test 40
		list.addFirst("A");
		checkEqualsString("[A, B, F]", list, 40);
		
		// Test 41
		list.addFirst("AA");
		checkEqualsString("[AA, A, B, F]", list, 41);
		
		// Test 42
		list.addFirst("AAA");
		checkEqualsString("[AAA, AA, A, B, F]", list, 42);
		
		//
		// Tests for addLast
		//
		
		// Test 43
		list.addLast("G");
		checkEqualsString("[AAA, AA, A, B, F, G]", list, 43);
		
		// Test 44
		list.addLast("GG");
		checkEqualsString("[AAA, AA, A, B, F, G, GG]", list, 44);
		
		// Test 45
		list.addLast("GGG");
		checkEqualsString("[AAA, AA, A, B, F, G, GG, GGG]", list, 45);
		
		//
		// Tests for removeLast()
		//
		
		// Test 46
		list.removeLast();
		checkEqualsString("[AAA, AA, A, B, F, G, GG]", list, 46);
		
		// Test 47
		list.removeLast();
		checkEqualsString("[AAA, AA, A, B, F, G]", list, 47);
		
		// Test 48
		list.removeLast();
		checkEqualsString("[AAA, AA, A, B, F]", list, 48);
		
		//
		// Tests for removeFirst()
		//
		
		// Test 49
		list.removeFirst();
		checkEqualsString("[AA, A, B, F]", list, 49);
		
		// Test 50
		list.removeFirst();
		checkEqualsString("[A, B, F]", list, 50);
		
		// Test 51
		list.removeFirst();
		checkEqualsString("[B, F]", list, 51);
		
		//
		// Tests for toString()
		//
		
		// Test 52
		s = list.toString();
		checkEqualsStringTwo("[B, F]", s, 52);
		
		// Test 53
		s = list2.toString();
		checkEqualsStringTwo("[]", s, 53);
		
		list.add("A");
		
		// Test 54
		s = list.toString();
		checkEqualsStringTwo("[B, F, A]", s, 54);
		
		//
		// Tests for equals()
		//
		
		// Test 55
		LinkedList<String> test1 = new LinkedList<String>();
		LinkedList<Character> test2 = new LinkedList<Character>();
		if (test1.equals(test2)) System.out.println("Passed test 55");
		else System.out.println("FAILED test 55");
		
		// Test 56
		if (!list.equals(list2)) System.out.println("Passed test 56");
		else System.out.println("FAILED test 56");
		
		list2.add("B");
		list2.add("F");
		list2.add("A");
		
		// Test 57
		if (list.equals(list2)) System.out.println("Passed test 57");
		else System.out.println("FAILED test 57");
	}
	
	private static void checkEqualsString(String s, LinkedList<?> list, int num) {
		if (list.toString().equals(s)) System.out.println("Passed test " + num);
		else System.out.println("FAILED test " + num);
	}
	
	private static void checkEqualsStringTwo(String s1, String s2, int num) {
		if (s1.toString().equals(s2)) System.out.println("Passed test " + num);
		else System.out.println("FAILED test " + num);
	}
	
	private static void checkEqualsInt(int i1, int i2, int num) {
		if (i1 == i2) System.out.println("Passed test " + num);
		else System.out.println("FAILED test " + num);
	}
	
	private static void checkEqualsBoolean(boolean b1, boolean b2, int num) {
		if (b1 == b2) System.out.println("Passed test " + num);
		else System.out.println("FAILED test " + num);
	}
}
