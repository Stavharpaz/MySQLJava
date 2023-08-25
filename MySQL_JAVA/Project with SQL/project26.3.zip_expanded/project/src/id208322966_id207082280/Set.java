package id208322966_id207082280;

@SuppressWarnings("unchecked")
public class Set<T> {
	T[] values;
	int logSize;
	
	
	public Set() {
		values = (T[])new Object[1];
		logSize = 0;
	}
	
	public int size() {
		return logSize;
	}
	
	public boolean add(T t) {
		if(this.contains(t))
			return false;
		if(logSize == values.length)
			this.enlargeArray();
		values[logSize++] = t;
		return true;
	}
	
	public boolean set( int index, T t ) {
		if( index > values.length || index < 0)
			return false;
		values[index] = t;
		return true;
	}
	
	public T get( int i ) {
		return values[i];
	}
	
	public boolean contains(T t) {
		for (int i = 0; i < logSize; i++) {
			if(t.equals(values[i]))
				return true;
		}
		return false;
	}
	
	private void enlargeArray() {
		T[] tmp = (T[]) new Object[values.length * 2];
		for (int i = 0; i < values.length; i++) {
			tmp[i] = values[i];
		}
		values = tmp;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer("Set:\n");
		for (int i = 0; i < logSize; i++) {
			sb.append(values[i].toString() + "\n");
		}
		return sb.toString();
	}
}
