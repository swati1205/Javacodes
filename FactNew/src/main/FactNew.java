package main;

public class FactNew {
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Invalid syntax...");
		}
		int n = Integer.parseInt(args[0]);
		int m = fact(n);
		System.out.println(m);

	}

	public static int fact(int f1) {
int f2=f1;
		for (int i = f2 - 1; i > 0; i--) {
			f2 = f2 * i;
		}
		return f2;
	}
}
