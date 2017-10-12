
public class Main {
	public static void main(String[] args) {
		Equation e = new Equation("x + 5");
		System.out.println();
		for(double x = 0.0; x <= 10.0; x += 1.0) {
			System.out.println("f(" + x + ") = " + e.f(x));
			System.out.println();
		}
		new Window();
	}
}
