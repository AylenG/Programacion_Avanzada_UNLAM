package progra.avanzada.estructuras;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class ColaPrioridad {

	private static final int CAPACIDAD_INICIAL = 16;

	private int[] nodos;
	private long[] prioridades;
	private int tamanio;

	public ColaPrioridad() {
		this(CAPACIDAD_INICIAL);
	}

	public ColaPrioridad(int capacidadInicial) {
		int capacidad = Math.max(1, capacidadInicial);
		nodos = new int[capacidad];
		prioridades = new long[capacidad];
		tamanio = 0;
	}

	public void agregar(int nodo, long prioridad) {
		if (tamanio == nodos.length) {
			agrandar();
		}
		nodos[tamanio] = nodo;
		prioridades[tamanio] = prioridad;
		subir(tamanio);
		tamanio++;
	}

	public int extraerMinimo() {
		if (estaVacia()) {
			throw new NoSuchElementException("La cola de prioridad esta vacia");
		}
		int minimo = nodos[0];
		tamanio--;
		if (tamanio > 0) {
			nodos[0] = nodos[tamanio];
			prioridades[0] = prioridades[tamanio];
			bajar(0);
		}
		return minimo;
	}

	public int verMinimo() {
		if (estaVacia()) {
			throw new NoSuchElementException("La cola de prioridad esta vacia");
		}
		return nodos[0];
	}

	public long prioridadMinima() {
		if (estaVacia()) {
			throw new NoSuchElementException("La cola de prioridad esta vacia");
		}
		return prioridades[0];
	}

	public boolean estaVacia() {
		return tamanio == 0;
	}

	public int tamanio() {
		return tamanio;
	}


	private void subir(int i) {
		while (i > 0) {
			int padre = (i - 1) / 2;
			if (prioridades[i] >= prioridades[padre]) {
				break;
			}
			intercambiar(i, padre);
			i = padre;
		}
	}

	private void bajar(int i) {
		while (true) {
			int izq = 2 * i + 1;
			int der = izq + 1;
			int menor = i;

			if (izq < tamanio && prioridades[izq] < prioridades[menor]) {
				menor = izq;
			}
			if (der < tamanio && prioridades[der] < prioridades[menor]) {
				menor = der;
			}
			if (menor == i) {
				break;
			}
			intercambiar(i, menor);
			i = menor;
		}
	}

	private void intercambiar(int a, int b) {
		int auxNodo = nodos[a];
		nodos[a] = nodos[b];
		nodos[b] = auxNodo;

		long auxPrioridad = prioridades[a];
		prioridades[a] = prioridades[b];
		prioridades[b] = auxPrioridad;
	}

	private void agrandar() {
		int nuevaCapacidad = nodos.length * 2;
		nodos = Arrays.copyOf(nodos, nuevaCapacidad);
		prioridades = Arrays.copyOf(prioridades, nuevaCapacidad);
	}
}
