import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/*
 * Fancy That PDF. 2021. ESTRUCTURA DE DATOS EN C LUIS JOYANES AGUILAR PDF. 
 * [online] Available at: <https://fancythatcakes.net/estructura-de-datos-en-c-luis-joyanes-aguilar-50/> 
 * 
 */

class VerticeLista{
	String nombre;
    int numVertice;
    LinkedList<Object> lad;
    
    
    public VerticeLista(String x) {
        nombre = x;
        numVertice = -1;
        lad = new LinkedList<Object>();
    }
    
    // Devuelve verdadero si dos vertices se llaman igual
    public boolean equals(Object d) {
        VerticeLista dos = (VerticeLista) d;
        return nombre.equals(dos.nombre);
    }
    
    // Indica el numero que identificar este vertice
    public void asigVert(int n) {
        numVertice = n;
    }
    
    // Devuelve el nombre del vertice
    public String nomVertice() {
        return nombre;
    }
    
    // Imprime el nombre y numero de vertice
    public String toString() {
        return nombre + " (" + numVertice + ")";
    }
}

class Arco {
    int destino;
    double peso;
    
    public Arco(int d) {
        destino = d;
    }
    
    public Arco(int d, double p) {
        this(d);
        peso = p;
    }
    
    public int getDestino(){
        return destino;
    }
    
    public boolean equals(Object n) {
        Arco a = (Arco)n;
        return destino == a.destino;
    }

	@Override
	public String toString() {
		return "Arco [destino=" + destino + ", peso=" + peso + "]";
	}
    
    
}

class GrafoLista{
	int numVerts;
    static int maxVerts = 20;
    VerticeLista [] tablAdc;
    
    public GrafoLista(int mx) {
        tablAdc = new VerticeLista[mx];
        numVerts = 0;
        maxVerts = mx;
    }
    
    public GrafoLista() {
        this(maxVerts);
    }
    
    public VerticeLista[] vertices(){
        return tablAdc;
    }
    
    // Devuelve la lista de adyacencia del vertice v
    public LinkedList<Object> listaAdyc(int v) throws Exception {
        if (v < 0 || v >= numVerts){
            throw new Exception("Vertice fuera de rango");
        }
        return tablAdc[v].lad;
    }
    
    // Busca y devuelve el nemero de vertice, si no lo encuentra regresa -1
    public int numVertice(String nombre) {
        VerticeLista v = new VerticeLista(nombre);
        boolean encontrado = false;
        int i = 0;
        for (; (i < numVerts) && !encontrado;){
            encontrado = tablAdc[i].equals(v);
            if (!encontrado){
                i++;
            }
        }
        return (i < numVerts) ? i : -1;
    }
    
    // Crea un nuevo vertice
    public void nuevoVertice (String nombre) {
        boolean existe = numVertice(nombre) >= 0;
        if (!existe) {
            VerticeLista v = new VerticeLista(nombre);
            v.asigVert(numVerts);
            tablAdc[numVerts++] = v;
            System.out.println("\nSe agrego el vertice");
        }
    } 
    
    // Comprueba si dos vertices son adyacentes
    boolean adyacente(String a, String b) throws Exception{
        int v1, v2;
        v1 = numVertice(a);
        v2 = numVertice(b);
        if(v1 < 0 || v2 < 0) {
            throw new Exception ("El vertice no existe");
        }
        if (tablAdc[v1].lad.contains(new Arco(v2))) {
            return true;
        } else {
            return false;
        }
    }
    
    // Comprueba si dos vertices son adyacentes por el numero de vertice
    boolean adyacente(int v1, int v2) throws Exception{
        if (tablAdc[v1].lad.contains(new Arco(v2))) {
            return true;
        } else {
            return false;
        }
    }
    
    
    // Crea un nuevo arco
    public void nuevoArco(String a, String b) throws Exception {
        if (!adyacente(a,b)){
            int v1 = numVertice(a);
            int v2 = numVertice(b);
            if(v1 < 0 || v2 < 0) {
                throw new Exception ("El vertice no existe");
            }
            Arco ab = new Arco(v2);
            tablAdc[v1].lad.addFirst(ab);
            System.out.println("\nSe agrego la arista");
        }
    }
    
    // borra un arco ya creado
    public void borrarArco(String a, String b) throws Exception {
        int v1 = numVertice(a);
        int v2 = numVertice(b);
        if(v1 < 0 || v2 < 0) {
            throw new Exception ("El vertice no existe");
        }
        Arco ab = new Arco(v2);
        tablAdc[v1].lad.remove(ab);
        System.out.println("\nArista eliminada");
    }
	
}

class RecorridoLista{
	private static final int clave = 9999;
	
	public static int[] recorridoAnchura(GrafoLista g, String org) throws Exception {
		int v, w;
		Queue<Integer> cola = new LinkedList<>();
		int [] m;
		m = new int[g.numVerts];
		// inicializa los vertices como no marcados
		v = g.numVertice(org);
		if (v < 0) throw new Exception("V�rtice origen no existe");
		for (int i = 0; i < g.numVerts; i++)
			m[i] = clave;
		m[v] = 0; // vertice origen queda marcado
		cola.add(new Integer(v));
		while (!cola.isEmpty()) {
			 Integer cw;
			 cw = (Integer) cola.remove();
			 w = cw.intValue();
			 System.out.println("Vertice " + g.tablAdc[w] + " visitado");
			 // inserta en la pila los adyacentes de w no marcados
			 // recorre la lista con un iterador
			 ListIterator<Object> list = (g.tablAdc[w].lad).listIterator();
			 Arco ck;
			 do {
				int k;
				 	
				try {
					 ck = (Arco) list.next();
				} catch (Exception e) {
					ck = null;
				}
				
				if (ck != null) {
					 k = ck.getDestino(); // vertice adyacente
					 if (m[k] == clave) {
						 cola.add(new Integer(k));
						 m[k] = 1; // vertice queda marcado
					 }
				 }
			} while (ck != null);
		}
		return m;
	}
	
	public static int[] recorridoProfundidad(GrafoLista g, String org) throws Exception {
		int v, w;
		Stack<Integer> pila = new Stack<Integer>();
		int [] m;
		m = new int[g.numVerts];
		// inicializa los vertices como no marcados
		v = g.numVertice(org);
		if (v < 0) throw new Exception("V�rtice origen no existe");
		for (int i = 0; i < g.numVerts; i++)
			m[i] = clave;
		m[v] = 0; // vertice origen queda marcado
		pila.push(new Integer(v));
		while (!pila.isEmpty()) {
			 Integer cw;
			 cw = (Integer) pila.pop();
			 w = cw.intValue();
			 System.out.println("Vertice " + g.tablAdc[w] + " visitado");
			 // inserta en la pila los adyacentes de w no marcados
			 // recorre la lista con un iterador
			 ListIterator<Object> list = (g.tablAdc[w].lad).listIterator();
			 Arco ck;
			 do {
				int k;
				 	
				try {
					 ck = (Arco) list.next();
				} catch (Exception e) {
					ck = null;
				}
				
				if (ck != null) {
					 k = ck.getDestino(); // vertice adyacente
					 if (m[k] == clave) {
						 pila.push(new Integer(k));
						 m[k] = 1; // vertice queda marcado
					 }
				 }
			} while (ck != null);
		}
		return m;
	}
}


public class PruebaGrafoLista {
	public static void main(String[] args) {
		GrafoLista grafo = new GrafoLista(100);
		Scanner entrada = new Scanner(System.in);
		int opcion = 0;
		do {
			System.out.println("\nElige una de las siguientes opciones");
			System.out.println("1) Agregar nuevo vertice");
			System.out.println("2) Agregar nuevo arco");
			System.out.println("3) Eliminar Arco");
			System.out.println("4) Recorrer en anchura");
			System.out.println("5) Recorrer en profundidad");
			System.out.println("6) Salir");
			System.out.println("Introduce opcion: ");
			
			opcion = entrada.nextInt();
			
			switch (opcion) {
			case 1:
				System.out.println("\nIntroduce nombre del vertice: ");
				String nom = entrada.next();
				grafo.nuevoVertice(nom);
				break;
				
			case 2:
				System.out.println("\nIntroduce primer vertice: ");
				String v1 = entrada.next();
				System.out.println("segundo vertice: ");
				String v2 = entrada.next();
				try {
					grafo.nuevoArco(v1, v2);
				} catch (Exception e) {
					System.out.println("\nError nombres no encontrados");
					// TODO: handle exception
				}
				break;
			
			case 3:
				System.out.println("\nIntroduce primer vertice: ");
				String v3 = entrada.next();
				System.out.println("segundo vertice: ");
				String v4 = entrada.next();
				try {
					grafo.borrarArco(v3, v4);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("\nError arista no encontrada");
				}
				break;
			case 4:
				System.out.println("\nIntroduce nombre del vertice a comenzar: ");
				String comienzo = entrada.next();
				System.out.println();
				try {
					RecorridoLista.recorridoAnchura(grafo, comienzo);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("\nError");
				}
				
				break;
			case 5:
				System.out.println("\nIntroduce nombre del vertice a comenzar: ");
				String comienzo2 = entrada.next();
				System.out.println();
				try {
					RecorridoLista.recorridoProfundidad(grafo, comienzo2);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("\nError no se encontro el vertice");
				}
				break;
			case 6:
				System.out.println("\nSaliendo . . . ");
				break;
			default:
				System.out.println("\nOpcion incorrecta");
				break;
			}
			
		} while(opcion != 6);
		/*
		grafo.nuevoVertice("A");
		grafo.nuevoVertice("B");
		grafo.nuevoVertice("C");
		grafo.nuevoVertice("D");
		grafo.nuevoVertice("I");
		grafo.nuevoVertice("H");
		grafo.nuevoVertice("R");
		
		try {
			grafo.nuevoArco("D", "B");
			grafo.nuevoArco("D", "C");
			grafo.nuevoArco("B", "H");
			grafo.nuevoArco("C", "R");
			grafo.nuevoArco("R", "H");
			grafo.nuevoArco("H", "D");
			grafo.nuevoArco("H", "I");
			grafo.nuevoArco("H", "A");
			
			
			RecorridoLista.recorridoProfundidad(grafo, "D");
			System.out.println("EL otro recorrido");
			RecorridoLista.recorridoAnchura(grafo, "D");
			
		} catch(Exception e) {
			System.out.println(e);
		
		}
		*/
		
	}
}
