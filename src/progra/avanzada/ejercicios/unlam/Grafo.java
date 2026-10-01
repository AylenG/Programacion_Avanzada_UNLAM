package progra.avanzada.ejercicios.unlam;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Grafo {

    public static void main(String[] args) {

        // Ejemplo 1 -> esperado [9]
        char[][] grid1 = {
            { '1', '1', '1', '1', '0' },
            { '1', '1', '0', '1', '0' },
            { '1', '1', '0', '0', '0' },
            { '0', '0', '0', '0', '0' }
        };

        // Ejemplo 2 -> esperado [4, 2, 1]
        char[][] grid2 = {
            { '1', '1', '0', '0', '0' },
            { '1', '1', '0', '0', '0' },
            { '0', '0', '1', '0', '0' },
            { '0', '0', '0', '1', '1' }
        };
        
        char[][] grid = grid2;
        List<Integer> res = new ArrayList<>();

        int m = grid.length;
        int n = grid[0].length;

        imprimirMatriz(grid);

        resolver(res, grid, m, n);
        
        System.out.println("Respuesta: " + res.toString());
    }
    
    private static void imprimirMatriz(char[][] m) {
        for (char[] fila : m) {
            for (char valor : fila) {
                System.out.printf("%3c", valor);
            }
            System.out.println();
        }
    }
    
    private static void resolver(List<Integer> res, char[][] grid, int m, int n) {
        Deque<int[]> pila = new ArrayDeque<>();
        boolean[][] visitado = new boolean[m][n];
        int[] tope;
        int tamanio = 0, i = 0, j = 0;
        
        pila.push(new int[]{0, 0});
        visitado[0][0]=true;
        
        while(!pila.isEmpty()) {
        	tope = pila.pop();
        	
        	if(grid[i][j] == '1') {
        		tamanio ++;
        		
        	}
        	
        	
        }
    	
    }
}
