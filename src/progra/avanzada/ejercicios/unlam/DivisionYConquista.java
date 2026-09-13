package progra.avanzada.ejercicios.unlam;
import java.util.Random;

public class DivisionYConquista {
	
	static int ID = 1;

    public static void main(String[] args) {
        int m = 16; // debe ser potencia de 2

        int[][] tablero = new int[m][m];
        // todas las losas quedan en 0 por defecto al crear el arreglo en Java

        Random random = new Random();
        int filaMaldita = random.nextInt(m);
        int columnaMaldita = random.nextInt(m);
        tablero[filaMaldita][columnaMaldita] = -1; // -1 representa la losa maldita

        construirTablero(tablero, filaMaldita, columnaMaldita);

        imprimirTablero(tablero);
    }

    private static void construirTablero(int[][] tablero, int filaMaldita, int columnaMaldita) {
    	int m = tablero.length;
    	
    	// arranca con el offset 0 ya que es la matriz entera
    	construirTablero(tablero, m, 0, 0, filaMaldita, columnaMaldita);
    }
    
    private static void construirTablero(int[][] tablero, int m, int offFil, int offCol, int filMal, int colMal) {

        if(m == 2) {
        	// pongo a todos menos a la maldita con algun id 
        	        	
        	for (int fila = 0; fila <= 1; fila++) {
        		for (int columna = 0; columna <= 1; columna++) {
        			if(fila == filMal && columna == colMal)
        				continue;
        			else 
        				tablero[offFil + fila][offCol + columna] = ID;
        		}
        	}
        	
        	ID++;
            return;
        }
        
        int mitad = m / 2;
        int cuadrante = 0;
        
        if (filMal < mitad) {
        	// está en el primer o segundo cuadrante
        	if (colMal < mitad) {
        		cuadrante = 1;
        	} else {
        		cuadrante = 2;
        	}
        } else {
        	// está en el tercer o cuarto cuadrante
        	if (colMal < mitad) {
        		cuadrante = 3;
        	} else {
        		cuadrante = 4;
        	}
        }
        
        switch (cuadrante) {
	        case 1: // real en arriba-izquierda
	            int idCentral1 = ID;
	            tablero[mitad - 1 + offFil][mitad + offCol] = idCentral1;     // TR
	            tablero[mitad + offFil][mitad - 1 + offCol] = idCentral1;     // BL
	            tablero[mitad + offFil][mitad + offCol] = idCentral1;         // BR
	            ID++;
	
	            construirTablero(tablero, mitad, offFil, offCol, filMal, colMal); // TL, MALDITA
	            construirTablero(tablero, mitad, offFil, offCol + mitad, mitad - 1, 0); // TR
	            construirTablero(tablero, mitad, offFil + mitad, offCol, 0, mitad - 1); // BL
	            construirTablero(tablero, mitad, offFil + mitad, offCol + mitad, 0, 0); // BR
	            break;
	
	        case 2: // real en arriba-derecha
	            int idCentral2 = ID;
	            tablero[mitad - 1 + offFil][mitad - 1 + offCol] = idCentral2; // TL
	            tablero[mitad + offFil][mitad - 1 + offCol] = idCentral2;     // BL
	            tablero[mitad + offFil][mitad + offCol] = idCentral2;         // BR
	            ID++;
	
	            construirTablero(tablero, mitad, offFil, offCol, mitad - 1, mitad - 1); // TL
	            construirTablero(tablero, mitad, offFil, offCol + mitad, filMal, colMal - mitad); // TR, MALDITA
	            construirTablero(tablero, mitad, offFil + mitad, offCol, 0, mitad - 1); // BL
	            construirTablero(tablero, mitad, offFil + mitad, offCol + mitad, 0, 0); // BR
	            break;
	
	        case 3: // real en abajo-izquierda
	            int idCentral3 = ID;
	            tablero[mitad - 1 + offFil][mitad - 1 + offCol] = idCentral3; // TL
	            tablero[mitad - 1 + offFil][mitad + offCol] = idCentral3;     // TR
	            tablero[mitad + offFil][mitad + offCol] = idCentral3;         // BR
	            ID++;
	
	            construirTablero(tablero, mitad, offFil, offCol, mitad - 1, mitad - 1); // TL
	            construirTablero(tablero, mitad, offFil, offCol + mitad, mitad - 1, 0); // TR
	            construirTablero(tablero, mitad, offFil + mitad, offCol, filMal - mitad, colMal); // BL, MALDITA
	            construirTablero(tablero, mitad, offFil + mitad, offCol + mitad, 0, 0); // BR
	            break;
	
	        case 4: // real en abajo-derecha
	            int idCentral4 = ID;
	            tablero[mitad - 1 + offFil][mitad - 1 + offCol] = idCentral4; // TL
	            tablero[mitad - 1 + offFil][mitad + offCol] = idCentral4;     // TR
	            tablero[mitad + offFil][mitad - 1 + offCol] = idCentral4;     // BL
	            ID++;
	
	            construirTablero(tablero, mitad, offFil, offCol, mitad - 1, mitad - 1); // TL
	            construirTablero(tablero, mitad, offFil, offCol + mitad, mitad - 1, 0); // TR
	            construirTablero(tablero, mitad, offFil + mitad, offCol, 0, mitad - 1); // BL
	            construirTablero(tablero, mitad, offFil + mitad, offCol + mitad, filMal - mitad, colMal - mitad); // BR, MALDITA
	            break;
	    }
    }

    private static void imprimirTablero(int[][] tablero) {
        for (int[] fila : tablero) {
            for (int valor : fila) {
                System.out.printf("%3d", valor);
            }
            System.out.println();
        }
    }

}
