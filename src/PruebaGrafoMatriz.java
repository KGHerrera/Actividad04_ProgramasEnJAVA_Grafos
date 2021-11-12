import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/*
 * Fancy That PDF. 2021. ESTRUCTURA DE DATOS EN C LUIS JOYANES AGUILAR PDF. 
 * [online] Available at: <https://fancythatcakes.net/estructura-de-datos-en-c-luis-joyanes-aguilar-50/> 
 * 
 */

class Vertice{
	String nombre;
	
	int numVertice;
	
	public Vertice(String x) {
		nombre = x;
		numVertice = -1;
	}
	
	public String nomVertice() {
		return nombre;
	}
	
	public boolean equals(Vertice n) { // Devuelve identificador del vertice
		return nombre.equals(n.nombre);
	}
	
	public void asigVert(int n) { // establece el numero de vertices
		numVertice = n;
	}
	
	public String toString() {
		return nombre + " (" + numVertice + ")";
	}
	
}

class GrafoMatriz{
	int numVerts;
	static int MaxVerts = 20;
	Vertice [] verts;
	int [][] matAd;
	
	public GrafoMatriz(int mx) {
		matAd = new int [mx][mx];
		verts = new Vertice[mx];
		for(int i = 0; i < mx; i++)
			for (int j = 0; i< mx; i ++)
				matAd[i][j] = 0;
		numVerts = 0;
	}
	
	public void nuevoVertice(String nom) {
		boolean esta = numVertice(nom) >= 0;
		if(!esta) {
			Vertice v = new Vertice(nom);
			v.asigVert(numVerts);
			verts[numVerts++] = v;
			System.out.println("\nSe agrego el vertice");
		}
	}
	
	public int numVertice(String vs) {
		Vertice v = new Vertice(vs);
		boolean encontrado = false;
		int i = 0;
		for(; (i < numVerts) && !encontrado;) {
			encontrado = verts[i].equals(v);
			if(!encontrado) i++;
		}
		return (i < numVerts) ? i : -1;
	}
	
	public void nuevoArco(String a, String b) throws Exception {
		int va, vb;
		va = numVertice(a);
		vb = numVertice(b);
		if (va < 0 || vb < 0) throw new Exception ("V�rtice no existe");
		matAd[va][vb] = 1;
		System.out.println("\nSe creo el arco");
	}
	
	public void nuevoArco(int va, int vb)throws Exception {
		if (va < 0 || vb < 0) throw new Exception ("V�rtice no existe");
		matAd[va][vb] = 1;
	}
	
	// Determina si dos vertices, v1 y v2, forman un arco
	public boolean adyacente(String a, String b)throws Exception {
		int va, vb;
		va = numVertice(a);
		vb = numVertice(b);
		if (va < 0 || vb < 0) throw new Exception ("V�rtice no existe");
		return matAd[va][vb] == 1;
	}
	
	public boolean adyacente(int va, int vb)throws Exception {
		if (va < 0 || vb < 0) throw new Exception ("V�rtice no existe");
		return matAd[va][vb] == 1;
	}
	
	public void eliminaArista(String a, String b) {
		int v1 = numVertice(a);
		int v2 = numVertice(b);
		if (v1 >= verts.length || v2 >= verts.length) {
			throw new ArrayIndexOutOfBoundsException("Vertices inv�lidos, fuera de rango" + "nRango de vertices: 0 - " + (verts.length - 1));
		} else if (matAd[v1][v2] == 0) {
			System.err.println("La arista NO existe");
		} else {
			matAd[v1][v2] = 0;
			System.out.println("\nSe elimino la arista");
		}
	}
	
	/*
	public void imprimirMatriz() {
		System.out.println();
		for (int x=0; x < matAd.length; x++) {
			  System.out.print("| ");
			  for (int y=0; y < matAd[x].length; y++) {
			    System.out.print (matAd[x][y]);
			    if (y!=matAd[x].length-1) System.out.print("\t");
			  }
			  System.out.println(" |");
			}
	}
	*/
	

	public void eliminarVertice(String nom) {
		int v1 =  numVertice(nom);
	    if (v1 > numVerts || v1 < 0) {
	       System.out.println("\nVertice no encontrado");
	    } else {
	       for (int i=1; i < verts.length; i++) {
	          matAd [v1][i] = 0;
	       }
	       for (int i=1; i< matAd.length; i++) {
	          matAd [i][v1] = 0;
	       }
	       System.out.println("\nVertice eliminado");
	    }
	 }
	
}

class RecorridoGrafo{
	private static final int clave = 9999;
	
	public static int[] recorridoAnchura(GrafoMatriz g, String org) throws Exception {
		int w,v;
		int[] m;
		
		//Vertice inicial
		v = g.numVertice(org);
		
		if(v < 0) throw new Exception("Vertice origen no existe");
		
		Queue<Integer> cola = new LinkedList<>();
		m = new int[g.numVerts];
		
		for(int i = 0; i < g.numVerts; i++) {
			m[i] = clave;
		}
		
		m[v] = 0;
		cola.add(v);
		
		while(!cola.isEmpty()) {
			w = cola.remove();
			System.out.println("Vertice " + g.verts[w] + "=> visitado");
			
			for(int u = 0; u < g.numVerts; u++) {
				if((g.matAd[w][u] == 1) && (m[u] == clave)) {
					m[u] = m[w] + 1;
					cola.add(u);
				}
			}
		}
		return m;
	}
	
	public static int[] recorridoProfundidad(GrafoMatriz g, String org) throws Exception {
		int w,v;
		int[] m;
		
		//Vertice inicial
		v = g.numVertice(org);
		
		if(v < 0) throw new Exception("Vertice origen no existe");
		
		Stack<Integer> pila = new Stack<>();
		m = new int[g.numVerts];
		
		for(int i = 0; i < g.numVerts; i++) {
			m[i] = clave;
		}
		
		m[v] = 0;
		pila.push(v);
		
		while(!pila.isEmpty()) {
			w = pila.pop();
			System.out.println("Vertice " + g.verts[w] + "=> visitado");
			
			for(int u = 0; u < g.numVerts; u++) {
				if((g.matAd[w][u] == 1) && (m[u] == clave)) {
					m[u] = m[w] + 1;
					pila.add(u);
				}
			}
		}
		return m;
	}
	
}

public class PruebaGrafoMatriz {
	public static void main(String[] args) {
		GrafoMatriz matriz = new GrafoMatriz(100);
		int opcion = 0;
		Scanner entrada = new Scanner(System.in);
		
		do {
			System.out.println("\nElige una de las siguientes opciones");
			System.out.println("1) Agregar nuevo vertice");
			System.out.println("2) Agregar nuevo arco");
			System.out.println("3) Eliminar Vertice");
			System.out.println("4) Eliminar Arco");
			
			System.out.println("5) Recorrer en anchura");
			System.out.println("6) Recorrer en profundidad");
			System.out.println("7) Salir");
			System.out.println("Introduce opcion: ");
			
			opcion = entrada.nextInt();
			
			switch (opcion) {
			case 1:
				System.out.println("\nIntroduce nombre del vertice: ");
				String nom = entrada.next();
				matriz.nuevoVertice(nom);
				break;
				
			case 2:
				System.out.println("Introduce primer vertice: ");
				String v1 = entrada.next();
				System.out.println("segundo vertice: ");
				String v2 = entrada.next();
				try {
					matriz.nuevoArco(v1, v2);
				} catch (Exception e) {
					System.out.println("\nError nombres no encontrados");
					// TODO: handle exception
				}
				break;
				
			case 3:
				System.out.println("\nIntroduce nombre del vertice: ");
				String elim = entrada.next();
				matriz.eliminarVertice(elim);
				break;
			
			case 4:
				System.out.println("Introduce primer vertice: ");
				String v3 = entrada.next();
				System.out.println("segundo vertice: ");
				String v4 = entrada.next();
				try {
					matriz.eliminaArista(v3, v4);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("\nError arista no encontrada");
				}
				break;
			case 5:
				System.out.println("\nIntroduce nombre del vertice a comenzar: ");
				String comienzo = entrada.next();
				System.out.println();
				try {
					RecorridoGrafo.recorridoAnchura(matriz, comienzo);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("\nError");
				}
				
				break;
			case 6:
				System.out.println("\nIntroduce nombre del vertice a comenzar: ");
				String comienzo2 = entrada.next();
				System.out.println();
				try {
					RecorridoGrafo.recorridoProfundidad(matriz, comienzo2);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("\nError no se encontro el vertice");
				}
				break;
				
			case 7:
				System.out.println("\nSaliendo . . . ");
				break;
			default:
				System.out.println("\nOpcion incorrecta");
				break;
			}
			
		} while(opcion != 7);
		
		/*
		matriz.nuevoVertice("A");
		matriz.nuevoVertice("B");
		matriz.nuevoVertice("C");
		matriz.nuevoVertice("D");
		matriz.nuevoVertice("I");
		matriz.nuevoVertice("H");
		matriz.nuevoVertice("R");
		
		try {
			matriz.nuevoArco("D", "B");
			matriz.nuevoArco("D", "C");
			matriz.nuevoArco("B", "H");
			matriz.nuevoArco("C", "R");
			matriz.nuevoArco("R", "H");
			matriz.nuevoArco("H", "D");
			matriz.nuevoArco("H", "I");
			matriz.nuevoArco("H", "A");
			
			RecorridoGrafo.recorridoAnchura(matriz, "D");
			System.out.println();
			
			
			matriz.eliminarVertice("C");
			matriz.nuevoArco("D", "R");
			RecorridoGrafo.recorridoProfundidad(matriz, "D");
			
			matriz.nuevoVertice("Z");
			matriz.nuevoArco("H", "Z");
			System.out.println("Otro");
			RecorridoGrafo.recorridoProfundidad(matriz, "D");
			
		} catch (Exception e) {
			System.out.println(e);
		}
	*/
	
	
	}
}
