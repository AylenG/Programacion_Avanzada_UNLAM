package progra.avanzada.ejercicios.unlam;

public class StreetNumbers {
	static int cuadratica(int n) {
		int i, j, sumi = 0, sumj = 0;
		for (i = 1; i <= n; i++) {
			for (j = i + 1; j <= n; j++) {
				sumj += j;
			}
			if (sumi == sumj) {
				return i;
			}
			sumj = 0;
			sumi += i;
		}
		return -1;
	}

	static long lineal(long n) {
		long i = 1, j = n, sumi = i, sumj = j; // Inicializacion de las sumas
		// Muevo al siguiente elemento
		i++;
		// Muevo al anteultimo elemento
		j--;
		while (i < j) {
			if (sumi <= sumj) {
				sumi += i;
				i++;
			} else if (sumi > sumj) {
				sumj += j;
				j--;
			}
		}
		if (sumi == sumj && i == j) {
			return i;
		}
		return -1;
	}

	static long constante(long n) {
		double resultado = Math.sqrt((n * (n + 1)) / 2);
		if (resultado == Math.floor(resultado)) {
			return (long) resultado;
		}
		return -1;

	}

	public static void main(String[] args) {
		System.out.print(StreetNumbers.constante(11309768));
	}
}
