package bo.com.spaps.util;

//Librerias de POI requeridas
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor; //Para leer un .doc
import org.apache.poi.xwpf.extractor.XWPFWordExtractor; //Para leer un XWPF Document
import org.apache.poi.xwpf.usermodel.XWPFDocument; //Para instanciar un .docx





//Librerias de JAVA requeridas
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 
 * @author David.Ticlla.Felipe
 *
 */
public class ReadWriteWordFile {

	public static void main(String [] args) {
		//Se crea el objeto File con la ruta del archivo
		//En la ruta recuerden que se debe poner doble barra "\\"
		File archivoDocx = new File("/Users/David.Ticlla.Felipe/Desktop/TestDOCX.docx");
		File archivoDoc  = new File("/Users/David.Ticlla.Felipe/Desktop/TestDOC.doc");
		String textoDeDocx ="";
		String textoDeDoc ="";

		try {
			//Creamos el stream fijense bien los objetos usados
			InputStream entradaArch1 = new FileInputStream(archivoDoc);
			InputStream entradaArch2 = new FileInputStream(archivoDocx);

			//Metodos para leer dependiendo de si es .doc o .docx
			//textoDeDoc = leerDoc(entradaArch1);
			textoDeDocx = leerDocx(entradaArch2);

		} catch(Exception ex) {
			//Manejar Excepcion IO y FileNotFound
		}


		// se imprime
		System.out.println(textoDeDoc);
		System.out.println("==========================================");
		System.out.println(textoDeDocx);
//		String cabeceraDocumento = textoDeDocx;
//		
//		System.out.println("==========================================");
//		char[] l =cabeceraDocumento.toCharArray();
//		for(int i=0 ;i<= cabeceraDocumento.length();i++){
//			int x = i;
//			int x1 = x +1;
//			int x2 = x +2;
//			int x3 = x +3;
//			if(x1<=cabeceraDocumento.length()){
//				String letra = String.valueOf(l[x]);
//				if(letra.equals(".")){
//					if(x3<=cabeceraDocumento.length()){
//						if(String.valueOf(l[x1]).equals(".") && String.valueOf(l[x2]).equals(".")){
//							cabeceraDocumento = cabeceraDocumento.substring(0, x+3);
//							break;
//						}
//					}else{ break; }
//				}
//			}else{ break; }
//		}
		//System.out.println("FILE DOCX: "+cabeceraDocumento);
		//readDoc2();

		//		
		//		
				ClassLoader classloader =
						   org.apache.poi.poifs.filesystem.POIFSFileSystem.class.getClassLoader();
						URL res = classloader.getResource(
						         "org/apache/poi/poifs/filesystem/POIFSFileSystem.class");
						String path = res.getPath();
						System.out.println("Core POI came from " + path);


	} // End main

	public static String leerDoc(InputStream doc) throws IOException  {
		//Creamos el extractor pasandole el stream
		WordExtractor we = new WordExtractor(doc);

		//Regresamos lo leído
		return we.getText();
	}

	public static String leerDocx(InputStream docx) throws IOException {
		//Se crea un documento que la POI entiende pasandole el stream
		//instanciamos el obj para extraer contenido pasando el documento
		//new XWPFDocument
		//
		XWPFDocument fdocx = new XWPFDocument(docx);
		XWPFWordExtractor xwpf_we = new XWPFWordExtractor(fdocx); 

		return xwpf_we.getText();
	}

	//"/Users/David.Ticlla.Felipe/Desktop/TestDOC.doc"
	public static void readDoc2(){
		File docFile = null;
		WordExtractor docExtractor = null ;
		WordExtractor exprExtractor = null ;
		try {
			docFile = new File("/Users/David.Ticlla.Felipe/Desktop/TestDOC.doc");
			//A FileInputStream obtains input bytes from a file. 
			FileInputStream fis=new FileInputStream(docFile.getAbsolutePath());

			//A HWPFDocument used to read document file from FileInputStream
			HWPFDocument doc=new HWPFDocument(fis);

			docExtractor = new WordExtractor(doc);
		} catch(Exception exep) {
			System.out.println(exep.getMessage());
		}
	}
}