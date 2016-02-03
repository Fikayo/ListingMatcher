package sortable.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A list of elements which maintains an order. The order is based on a specified comparator or on the natural ordering of the elements.
 * @author fikayo
 * */
public class SortedList<E extends Comparable<E>> implements List<E> {

	private List<E> list;
	private Comparator<? super E> comparator;
	
	public SortedList()	{
		this.list = new ArrayList<E>();
	}
	
	public SortedList(Comparator<? super E> comparator) {
		this();
		this.comparator = comparator;
	}
	
	@Override
	public boolean add(E e) {
		boolean success = this.list.add(e);
		if(comparator != null){
			Collections.sort(this.list, this.comparator);
		} else {
			Collections.sort(this.list);
		}
		
		return success;
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean success = this.list.addAll(c);
		if(comparator != null){
			Collections.sort(this.list, this.comparator);
		} else {
			Collections.sort(this.list);
		}
		
		return success;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		this.list.clear();
	}

	@Override
	public boolean contains(Object o) {
		return this.list.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.list.containsAll(c);
	}

	@Override
	public E get(int index) {
		return this.list.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return this.list.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return this.list.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.list.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return this.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return this.listIterator(index);
	}

	@Override
	public boolean remove(Object o) {
		return this.list.remove(o);
	}

	@Override
	public E remove(int index) {
		return this.remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean success =  this.list.removeAll(c);
		if(comparator != null){
			Collections.sort(this.list, this.comparator);
		} else {
			Collections.sort(this.list);
		}
		
		return success;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean success = this.list.retainAll(c);
		if(comparator != null){
			Collections.sort(this.list, this.comparator);
		} else {
			Collections.sort(this.list);
		}
		
		return success;
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return this.list.size();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return this.list.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return this.list.toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <E> E[] toArray(E[] a) {
		return this.list.toArray(a);
	}

}
