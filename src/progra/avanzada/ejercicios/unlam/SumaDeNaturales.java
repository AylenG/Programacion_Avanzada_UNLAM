package progra.avanzada.ejercicios.unlam;

public class SumaDeNaturales {

	static int sumaIterativa(int n) {
		int suma = 0;
		for (int i = 1; i <= n; i++) {
			suma += i;
		}
		return suma;
	}
	
	static int sumaRecursiva(int n) {
		if(n == 1) 
			return 1;
		return n + sumaRecursiva(n-1);
	}
	
	public static void main(String[] args) {
		int n = 9;
		
		System.out.println("Suma de primeros " + n + " números naturales I: " + sumaIterativa(n));
		System.out.println("Suma de primeros " + n + " números naturales R: " + sumaRecursiva(n));
	}

}
