1.	import java.io.BufferedReader;
2.	import java.io.File;
3.	import java.io.FileReader;
4.	import java.io.FileWriter;
5.	import java.io.IOException;
6.	import java.io.PrintWriter;
7.	import java.util.Scanner;
8.	
9.	public class Encriptador {
10.		// Creo una variable alternador para hacer más dificil descifrar el código
11.		private static boolean alternador = false;
12.		// Creo una variable clave para poder cambiarla desde fuera de las funciones
13.		private static int clave = 8;
14.		
15.		// Creo una función para leer ficheros
16.		private static String leerFichero(String nombreFichero) {
17.			String string, result = "";
18.			BufferedReader br;
19.			
20.			try {
21.				// Abrimos el archivo
22.				br = new BufferedReader(new FileReader (new File(nombreFichero + ".txt")));
23.				// Recogemos el contenido del archivo en la variable result
24.				while((string = br.readLine()) != null)
25.					result += string;
26.				br.close();
27.				// Retornamos result
28.				return result;
30.			} catch (Exception e) {
31.				// TODO: handle exception
32.				return "";
33.			}
34.		}
35.		
36.		// Creo una función para escribir un texto en el archivo con el nombre del fichero añadiendo sufijo
37.		private static boolean escribirFichero(String texto, String nombreFichero, boolean cifrado) {
38.			PrintWriter pw;
39.	
40.			if (texto == "" || nombreFichero == "")
41.				return false;
42.			try {
43.				if(cifrado)
44.					pw = new PrintWriter(new FileWriter(nombreFichero + "-CIFRADO.txt"));
45.				else
46.					pw = new PrintWriter(new FileWriter(nombreFichero + "-DESENCRIPTADO.txt"));
47.				pw.println(texto);
48.				// Por último cerramos el PrintWriter
49.				pw.close();
50.				return true;		// Si todo ha salido bien esta función retorna true
51.			} catch (Exception e) {
52.				// TODO: handle exception
53.				return false;
54.			}
55.		}
56.		
57.		private static String cifrar(String nombreFichero) {
58.			int i;
59.			String string, texto, result = "";
60.			char array[];
61.			FileWriter fw;
62.			PrintWriter pw;
63.			
64.			texto = leerFichero(nombreFichero);
65.			if (texto == "")
66.				return texto;
67.			array = texto.toCharArray();	// Transformo el texto dentro del fichero en un array
68.			for (i = 0; i < array.length; i++) {	// Recorro el array de principio a fin
69.				// En cada carácter se irá sumando o restando la clave secreta, alternando la operación
70.				if (alternador) {
71.					if (array[i] != 32)				// Tengo en cuenta los espacios para que aparezcan también
72.						array[i] += clave;
73.					alternador = !alternador;
74.				}
75.				else {
76.					if (array[i] != 32)
77.						array[i] -= clave;
78.					alternador = !alternador;
79.				}
80.				result += array[i];					// Vamos creando un string que recoja el resultado
81.			}
82.			alternador = false;						// Reiniciamos la variable alternador
83.			return result;							// Retornamos el string con el texto cambiado
84.		}
85.		
86.		private static String descifrar(String nombreFichero) {
87.			// Para descifrar la clave lo único que necesitaremos será cambiar el alternador y llamar a la otra función
88.			alternador = true;
89.			return cifrar(nombreFichero);
90.		}
91.		
92.		public static void main(String[] args) {
93.			// TODO Auto-generated method stub
94.			int option = 0;
95.			String fichero;
96.			Scanner entrada = new Scanner(System.in);
97.			
98.			System.out.println(
99.				"|----------------------|\n" +
100.				"| Opción 1:     Cifrar |\n" +
101.				"| Opción 2:  Descifrar |\n" +
102.				"| Opción 3:      Salir |\n" +
103.				"|----------------------|\n"
104.			);
105.			try {
106.				System.out.print("Opción: ");
107.				option = entrada.nextInt();
108.				entrada.nextLine();
109.			} catch (Exception e) {
110.				// TODO: handle exception
111.				entrada.nextLine();
112.			}	
113.			
114.			switch(option) {
115.				case 1:
116.					System.out.println("Introduzca el nombre del fichero:");
117.					fichero = entrada.nextLine();
118.					if (escribirFichero(cifrar(fichero), fichero, true))
119.						System.out.println("Fichero cifrado creado correctamente.");
120.					else
121.						System.err.println("Hubo un error. Inténtelo de nuevo más tarde. Asegúrese que el nombre del fichero es válido.");
122.					break;
123.				case 2:
124.					System.out.println("Introduzca el nombre del fichero:");
125.					fichero = entrada.nextLine();
126.					if (escribirFichero(descifrar(fichero), fichero, false))
127.						System.out.println("Fichero descifrado creado correctamente.");
128.					else
129.						System.err.println("Hubo un error. Inténtelo de nuevo más tarde. Asegúrese que el nombre del fichero es válido.");
130.					break;
131.				case 3:
132.					System.out.println("Programa finalizado.");
133.					System.exit(0);
134.					break;
135.				default:
136.					System.err.println("Opción no válida. Debe poner un número entero del 1 al 3.\n");
137.					break;
138.			}
139.			
140.			Encriptador.main(args);
141.		}
}
