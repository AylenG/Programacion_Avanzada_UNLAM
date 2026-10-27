package pa.matrix;

import java.util.ArrayList;

/**
 * Neo debe localizar al Agente Smith en los registros de la Matrix. Los datos
 * están organizados en una matriz de enteros sin elementos repetidos, donde
 * cada fila está ordenada de menor a mayor, y cada columna también está
 * ordenada de menor a mayor.
 * 
 * Esta clase implementa un algoritmo de división y conquista recursivo para
 * buscar un valor objetivo en una matriz N×N ordenada por filas y columnas en
 * tiempo menor a O(n^2).
 * 
 * @author Gomez Aylen Paula
 * @version 1.0
 */
public class Neo {

	public static void main(String[] args) {
		Neo neo = new Neo();
		int[][] m4 = { { 1, 4, 7, 11 }, { 2, 5, 8, 12 }, { 3, 6, 9, 16 }, { 10, 13, 14, 17 } };
		int[][] m5 = { { 1, 4, 7, 11, 15 }, { 2, 5, 8, 12, 19 }, { 3, 6, 9, 16, 22 }, { 10, 13, 14, 17, 24 },
				{ 18, 21, 23, 26, 30 } };

		// matriz 4x4
		probar(neo, m4, 1);  // esperado [1, 1]
		probar(neo, m4, 5);  // esperado [1, 1]
		probar(neo, m4, 3);  // esperado [2, 0]
		probar(neo, m4, 7);  // esperado [2, 0]
		probar(neo, m4, 10);  // esperado [1, 1]
		probar(neo, m4, 11);  // esperado [1, 1]
		probar(neo, m4, 13);  // esperado [1, 1]
		probar(neo, m4, 15);  // esperado [1, 1]
		probar(neo, m4, 17); // esperado [-1, -1]
		probar(neo, m4, 20); // esperado [-1, -1]
		
		// matriz 1x1
//		probar(neo, new int[][] { { 7 } }, 7); // esperado [0, 0]
		
		//matriz 5x5
//		probar(neo, m5, 5);  // esperado [1, 1]
//		probar(neo, m5, 9); // esperado [4, 4]
//		probar(neo, m5, 12); // esperado [4, 4]
//		probar(neo, m5, 10); // esperado [4, 4]
//		probar(neo, m5, 23); // esperado [4, 4]
//		probar(neo, m5, 28); // esperado [4, 4]
//		probar(neo, m5, 30); // esperado [4, 4]
//		probar(neo, m5, 20); // esperado [-1, -1]
	}

	private static void probar(Neo neo, int[][] m, int objetivo) {
		System.out.println(objetivo + " -> " + java.util.Arrays.toString(neo.buscarAgenteSmith(m, objetivo)));
	}
	
	/**
	 * Busca al Agente Smith en la matriz de datos de Matrix usando divide y
	 * vencerás recursivo. La matriz debe estar ordenada: cada fila creciente y cada
	 * columna creciente.
	 * 
	 * @param matrizDatos matriz ordenada que representa los registros de Matrix
	 * @param codigoSmith código único del Agente Smith a buscar
	 * @return array de 2 posiciones [fila, columna] donde está Smith, o [-1, -1] si
	 *         no se encuentra
	 */
	public int[] buscarAgenteSmith(int[][] matrizDatos, int codigoSmith) {
		if(matrizDatos.length == 0) {
			return new int[] { -1, -1 };
		} else {
			return buscarAgenteSmith(0, 0, matrizDatos.length, matrizDatos, codigoSmith);			
		}
	}
	
	public int[] buscarAgenteSmith(int origenFil, int origenCol, int n, int[][] matrizDatos, int codigoSmith) {
		
		if(n == 1) {
			if(matrizDatos[origenFil][origenCol] == codigoSmith) {
				return new int[] { origenFil, origenCol };
			} else {
				return new int[] { -1, -1 };
			}
		}

		int nNuevo = n - n / 2; // mitad redondeada hacia arriba, los cuadrantes se pisan si n es impar
		int ini = n - nNuevo; // donde empieza la segunda mitad
		ArrayList<Integer> cuadrantesCandidatos = new ArrayList<>();
		int[] res = { -1, -1 };
		
		if(matrizDatos[origenFil][origenCol] <= codigoSmith 
				&& matrizDatos[origenFil + nNuevo - 1][origenCol + nNuevo - 1] >= codigoSmith) {
			cuadrantesCandidatos.add(1);
		}
		
		if(matrizDatos[origenFil][origenCol + ini] <= codigoSmith 
				&& matrizDatos[origenFil + nNuevo - 1][origenCol + n - 1] >= codigoSmith) {
			cuadrantesCandidatos.add(2);
		}
		
		if(matrizDatos[origenFil + ini][origenCol] <= codigoSmith 
				&& matrizDatos[origenFil + n - 1][origenCol + nNuevo - 1] >= codigoSmith) {
			cuadrantesCandidatos.add(3);
		} 
		
		if(matrizDatos[origenFil + ini][origenCol + ini] <= codigoSmith 
				&& matrizDatos[origenFil + n - 1][origenCol + n - 1] >= codigoSmith) {
			cuadrantesCandidatos.add(4);
		}
		
		for(int cuadrante: cuadrantesCandidatos) {
			switch(cuadrante) {
				case 1: res = buscarAgenteSmith(origenFil, origenCol, nNuevo, matrizDatos, codigoSmith);
				break;
				case 2: res = buscarAgenteSmith(origenFil, origenCol + ini, nNuevo, matrizDatos, codigoSmith);
				break;
				case 3: res = buscarAgenteSmith(origenFil + ini, origenCol, nNuevo, matrizDatos, codigoSmith);
				break;
				case 4: res = buscarAgenteSmith(origenFil + ini, origenCol + ini, nNuevo, matrizDatos, codigoSmith);
				break;
			}
			
			if(res[0] != -1) {
				break;
			}
		}
		
		return res;
	}
}
