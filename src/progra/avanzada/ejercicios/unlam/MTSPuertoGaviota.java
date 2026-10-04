package progra.avanzada.ejercicios.unlam;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import progra.avanzada.estructuras.ColaPrioridad;

public class MTSPuertoGaviota {

	// Tipos de conexion -> peso de la arista
	static final int PESO_TUNEL = 0;
	static final int PESO_PUENTE = 1;

	record Arista(int desde, int hasta, int peso) {}

	// Datos de entrada
	static int cantIslas;    // N (vertices)
	static int cantTuneles;  // K
	static int cantPuentes;  // M
	static List<Arista> aristas;
	static List<List<Arista>> grafo;

	// Ejemplo del enunciado -> esperado: 2
	static final String EJEMPLO_1 = """
			6 3 4
			1 2
			2 3
			4 5
			1 3
			3 4
			4 6
			5 6
			""";
	
	// CASO 2: Grafo ya conectado solo por túneles.
	// Hay puentes, pero no necesitamos usar NINGUNO porque los túneles ya conectan todo.
	// Esperado: 0
	static final String EJEMPLO_2 = """
			5 4 3
			1 2
			2 3
			3 4
			4 5
			1 5
			2 4
			1 3
			""";

	// CASO 3: Cero túneles.
	// No hay forma de evitar los puentes. El árbol abarcador necesitará exactamente N-1 puentes.
	// Esperado: 4
	static final String EJEMPLO_3 = """
			5 0 6
			1 2
			2 3
			3 4
			4 5
			5 1
			1 3
			""";

	// CASO 4: Múltiples componentes de túneles.
	// Tenemos 3 "bloques" de túneles: {1,2,3}, {4,5} y {6,7}.
	// Para unir 3 bloques, necesitamos exactamente 2 puentes. Hay puentes redundantes.
	// Esperado: 2
	static final String EJEMPLO_4 = """
			7 4 4
			1 2
			2 3
			4 5
			6 7
			3 4
			5 6
			1 4
			5 7
			""";

	// CASO 5: Caso mínimo. 
	// 2 islas, conectadas por 1 túnel y 1 puente paralelo.
	// Se debe priorizar el túnel.
	// Esperado: 0
	static final String EJEMPLO_5 = """
			2 1 1
			1 2
			1 2
			""";

	// CASO 6: "Estrella" de puentes.
	// La isla 1 está conectada a la 2 por un túnel. 
	// Para llegar a la 3, 4 y 5 dependemos exclusivamente de los puentes que salen de la 1 y de la 2.
	// Los túneles arman el bloque {1,2}. Quedan aisladas {3}, {4}, {5}. (4 componentes en total).
	// Esperado: 3
	static final String EJEMPLO_6 = """
			5 1 6
			1 2
			1 3
			1 4
			2 5
			3 4
			4 5
			3 5
			""";

	// CASO 7: Ciclos de túneles.
	// Los túneles forman un ciclo {1,2,3}. Quedan sueltas {4} y {5}.
	// Hay 3 componentes en total. Se necesitan 2 puentes.
	// Esperado: 2
	static final String EJEMPLO_7 = """
			5 3 4
			1 2
			2 3
			3 1
			3 4
			4 5
			1 5
			2 4
			""";

	public static void main(String[] args) throws IOException {
		leerEntrada(new StringReader(EJEMPLO_7));

		int salida = resolver();
		System.out.println(salida);
	}

	static void leerEntrada(Reader reader) throws IOException {
		StreamTokenizer in = new StreamTokenizer(reader);

		cantIslas = siguienteInt(in);
		cantTuneles = siguienteInt(in);
		cantPuentes = siguienteInt(in);

		aristas = new ArrayList<>(cantTuneles + cantPuentes);
		grafo = new ArrayList<>(cantIslas + 1);
		for (int i = 0; i <= cantIslas; i++) {
			grafo.add(new ArrayList<>());
		}

		for (int i = 0; i < cantTuneles; i++) {
			agregarArista(siguienteInt(in), siguienteInt(in), PESO_TUNEL);
		}

		for (int i = 0; i < cantPuentes; i++) {
			agregarArista(siguienteInt(in), siguienteInt(in), PESO_PUENTE);
		}
	}

	static void agregarArista(int desde, int hasta, int peso) {
		aristas.add(new Arista(desde, hasta, peso));
		grafo.get(desde).add(new Arista(desde, hasta, peso));
		grafo.get(hasta).add(new Arista(hasta, desde, peso));
	}

	static int siguienteInt(StreamTokenizer in) throws IOException {
		in.nextToken();
		return (int) in.nval;
	}

	static int resolver() {
		
		List<Integer> visitados = new ArrayList<Integer>();
		PriorityQueue<Arista> cola = new PriorityQueue<>(
	            (p1, p2) -> Integer.compare(p1.peso, p2.peso));
		
		List<Arista> mst = new ArrayList<Arista>();
		
		int pesoTotal = 0;
		
		visitados.add(1);
		for(Arista arista: grafo.get(1)) {
			cola.add(arista);
		}
		
		while(visitados.size() < grafo.size() && !cola.isEmpty()) {
			Arista minimo = cola.poll();
			
			if(!visitados.contains(minimo.hasta())) {
				visitados.add(minimo.hasta);
				mst.add(minimo);
				pesoTotal += minimo.peso;
				
				for(Arista arista: grafo.get(minimo.hasta)) {
					if (!visitados.contains(arista.hasta)) {
						cola.add(arista);
					}
				}
			}
		}
		
		return pesoTotal;
	}
}
