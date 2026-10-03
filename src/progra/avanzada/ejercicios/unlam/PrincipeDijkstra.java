package progra.avanzada.ejercicios.unlam;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import progra.avanzada.estructuras.ColaPrioridad;

public class PrincipeDijkstra {

	record Arista(int destino, int largo) {}

	// Datos de entrada
	static int cantClaros;      // c (vertices)
	static int cantSenderos;    // s (aristas)
	static int cantDragones;    // d
	static int claroPrincesa;  // cf
	static int claroPrincipe;   // cm
	static int[] dragones;      // claros donde estan los dragones
	static List<List<Arista>> grafo; // lista de adyacencia (indices 1..c)

	// Ejemplo del enunciado -> esperado: INTERCEPTADO
	static final String EJEMPLO_1 = """
			9 10 2
			9 1
			8 5
			1 2 3
			1 3 2
			2 3 4
			2 6 1
			3 8 1
			8 6 5
			4 5 2
			3 4 2
			3 6 2
			6 9 3
			""";
	
	// NO HAY CAMINO
	static final String EJEMPLO_2 = """
			9 10 2
			7 1
			8 5
			1 2 3
			1 3 2
			2 3 4
			2 6 1
			3 8 1
			8 6 5
			4 5 2
			3 4 2
			3 6 2
			6 9 3
			""";
	
	// 1 2 4
	static final String EJEMPLO_3 = """
			4 4 1
			4 1
			3
			1 2 2
			2 4 2
			3 2 10
			3 4 10
			""";
	
	//1 2 4
	static final String EJEMPLO_4 = """
			5 5 1
			4 1
			5
			1 2 2
			2 4 2
			1 3 4
			3 4 4
			5 3 10
			""";
	
	// INTERCEPTADO
	static final String EJEMPLO_5 = """
			5 5 1
			4 1
			5
			1 2 2
			2 4 2
			1 3 5
			3 4 5
			5 2 1
			""";

	public static void main(String[] args) throws IOException {
		leerEntrada(new StringReader(EJEMPLO_5));

		String salida = resolver();
		System.out.println(salida);
	}

	static void leerEntrada(Reader reader) throws IOException {
		StreamTokenizer in = new StreamTokenizer(reader);

		cantClaros = siguienteInt(in);
		cantSenderos = siguienteInt(in);
		cantDragones = siguienteInt(in);

		claroPrincesa = siguienteInt(in);
		claroPrincipe = siguienteInt(in);

		dragones = new int[cantDragones];
		for (int i = 0; i < cantDragones; i++) {
			dragones[i] = siguienteInt(in);
		}

		grafo = new ArrayList<>(cantClaros + 1);
		for (int i = 0; i <= cantClaros; i++) {
			grafo.add(new ArrayList<>());
		}

		for (int i = 0; i < cantSenderos; i++) {
			int desde = siguienteInt(in);
			int hasta = siguienteInt(in);
			int largo = siguienteInt(in);

			grafo.get(desde).add(new Arista(hasta, largo));
			grafo.get(hasta).add(new Arista(desde, largo));
		}
	}

	static int siguienteInt(StreamTokenizer in) throws IOException {
		in.nextToken();
		return (int) in.nval;
	}

	static String resolver() {
		if(!ExisteCaminoDFS())
			return "NO HAY CAMINO";
		
		int[] tiempoDragones = distanciaDragonesDijkstra();
		
		return existeCamino(tiempoDragones);
	}
	
	static boolean ExisteCaminoDFS() {
		Deque<Integer> pila = new ArrayDeque<>();
		boolean[] visitado = new boolean[cantClaros + 1];

		pila.push(claroPrincipe);
		visitado[claroPrincipe] = true;

		while (!pila.isEmpty()) {
			int actual = pila.pop();

			if (actual == claroPrincesa) {
				return true;
			}

			for (Arista arista : grafo.get(actual)) {
				int vecino = arista.destino();
				if (!visitado[vecino]) {
					visitado[vecino] = true;
					pila.push(vecino);
				}
			}
		}

		return false;
	}
	
	static int[] distanciaDragonesDijkstra() {
	    int[] Td = new int[cantClaros + 1];
	    Arrays.fill(Td, Integer.MAX_VALUE); 
	    
	    int[] temp_D;
	    boolean[] visitado;
	    ColaPrioridad cola;
	    int dragonActual, minimo, vecino, peso;
	    
	    for(int i = 0; i < cantDragones; i++) {
	        dragonActual = dragones[i];
	        temp_D = new int[cantClaros + 1];
	        visitado = new boolean[cantClaros + 1];
	        cola = new ColaPrioridad();
	        
	        Arrays.fill(temp_D, Integer.MAX_VALUE);
	        
	        temp_D[dragonActual] = 0;
	        cola.agregar(dragonActual, 0);
	        
	        while(!cola.estaVacia()) {
	            minimo = cola.extraerMinimo();
	            
	            if(!visitado[minimo]) {
	                visitado[minimo] = true;
	                
	                for(Arista arista : grafo.get(minimo)) {
	                    vecino = arista.destino();
	                    peso = arista.largo();
	                    
	                    if(!visitado[vecino] && temp_D[minimo] + peso < temp_D[vecino]) {
	                        temp_D[vecino] = temp_D[minimo] + peso;
	                        cola.agregar(vecino, temp_D[vecino]);
	                    }
	                }
	            }
	        }
	        
	        for(int j = 1; j <= cantClaros; j++) {
	            if(temp_D[j] < Td[j]) {
	                Td[j] = temp_D[j];
	            }
	        }
	    }

	    return Td;
	}
	
	static String existeCamino(int[] tiempoDragones) {
		
		int[] tiempoPrincipe = new int[cantClaros + 1];
		int[] predecesoresPrincipe = new int[cantClaros + 1];
	    Arrays.fill(tiempoPrincipe, Integer.MAX_VALUE); 
	    
	    boolean[] visitado;
	    ColaPrioridad cola;
	    int minimo, vecino, peso;
	    
        visitado = new boolean[cantClaros + 1];
        cola = new ColaPrioridad();
        
        tiempoPrincipe[claroPrincipe] = 0;
        cola.agregar(claroPrincipe, 0);
        
        while(!cola.estaVacia()) {
            minimo = cola.extraerMinimo();
            
            if (minimo == claroPrincesa) {
                break;
            }
            
            if(!visitado[minimo]) {
                visitado[minimo] = true;
                
                for(Arista arista : grafo.get(minimo)) {
                    vecino = arista.destino();
                    peso = arista.largo();
                    
                    if(!visitado[vecino] && tiempoPrincipe[minimo] + peso < tiempoPrincipe[vecino] 
                    		&& tiempoPrincipe[minimo] + peso < tiempoDragones[vecino]) {
                    	tiempoPrincipe[vecino] = tiempoPrincipe[minimo] + peso;
                    	predecesoresPrincipe[vecino] = minimo;
                    	cola.agregar(vecino, tiempoPrincipe[vecino]);
                    }
                }
            }
        }
        
        if (tiempoPrincipe[claroPrincesa] == Integer.MAX_VALUE) {
        	return "INTERCEPTADO";
        }
		
        StringBuilder sb = new StringBuilder();
        Deque<Integer> pilaCamino = new ArrayDeque<>();
        int actual = claroPrincesa;
        while(actual != 0) {
        	pilaCamino.push(actual);
        	actual = predecesoresPrincipe[actual];
        }
        
        while (!pilaCamino.isEmpty()) {
            sb.append(pilaCamino.pop());
            if (!pilaCamino.isEmpty()) {
                sb.append(" ");
            }
        }
        
        return sb.toString();
	}
}