package progra.avanzada.ejercicios.unlam;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
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
        
     // Ejemplo 2 -> esperado [5, 2, 2, 1]
        char[][] grid3 = {
            { '1', '1', '1', '0', '1' },
            { '1', '1', '0', '0', '1' },
            { '0', '0', '0', '0', '0' },
            { '0', '1', '0', '1', '1' }
        };
        
        char[][] grid = grid3;
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
        int tamanio = 0, f, c;
        
        for(int i=0; i < m; i++) {
            for(int j=0; j < n; j++) {

                if(visitado[i][j]) continue;

                if(grid[i][j] == '1' && !visitado[i][j]) {
                    pila.push(new int[]{i, j});
                    visitado[i][j] = true;
                }
                
                while(!pila.isEmpty()) {
                    tope = pila.pop();
                    tamanio++;
                    f = tope[0];
                    c = tope[1];
                    
                    if(f - 1 >= 0 && grid[f-1][c] == '1' && !visitado[f-1][c]) {
                        pila.push(new int[]{f-1, c});
                        visitado[f-1][c] = true;
                    }
                    if(f + 1 < m && grid[f+1][c] == '1' && !visitado[f+1][c]) {
                        pila.push(new int[]{f+1, c});
                        visitado[f+1][c] = true;
                    }
                    if(c - 1 >= 0 && grid[f][c-1] == '1' && !visitado[f][c-1]) {
                        pila.push(new int[]{f, c-1});
                        visitado[f][c-1] = true;
                    }
                    if(c + 1 < n && grid[f][c+1] == '1' && !visitado[f][c+1]) {
                        pila.push(new int[]{f, c+1});
                        visitado[f][c+1] = true;
                    }
                }

                if(tamanio > 0) {
                    res.add(tamanio);
                    tamanio = 0;
                }
            }
        }
        
        res.sort(Comparator.reverseOrder());
    }
}
