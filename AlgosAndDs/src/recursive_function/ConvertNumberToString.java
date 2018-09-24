package recursive_function;
import java.util.Deque;
import java.util.ArrayDeque;

public class ConvertNumberToString {
	
	private static final String [] convertToString = {"0", "1", "2", "3","4","5","6","7","8","9"};
	static Deque<String> digits = new ArrayDeque<>();
	public static String numberToString(int n, int base){
		if(n < base) 
			return convertToString[n];
		
		int newN = n / base;
		int reminder = n%base;
		return numberToString(newN, base) + convertToString[reminder];
	}
	
	public static Deque<String> numberToStringCustomStack(int n, int base){
		if(n < base) {
			digits.push(convertToString[n]);
			printStack();
		}
		else {
			int newN = n / base;
			int reminder = n%base;
			digits.push(convertToString[reminder]);
			printStack();
			numberToStringCustomStack(newN, base);
		}
		return digits;
	}
	
	static public void printStack() {
		System.out.print("Stack: ");
		for(String s : digits)
			System.out.print(s);
		System.out.println();
	}

	public static void main(String[] args) {
		System.out.println(numberToString(123, 10));
		
		Deque<String> myStack = numberToStringCustomStack(123, 2);
		for(String s : myStack) {
			System.out.print(s);
		}
	}
	
}
