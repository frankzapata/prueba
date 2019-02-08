import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XmlProyecto {
    Text Texto;
    Document document ;
    String ruta ="\\Hash.xml";
    File file = new File (ruta);
    int longitud = 100;
    String [] listahash = new  String [longitud];
    int  contador =1 ;
   public  XmlProyecto (){

    try {
        if (file.exists()){
            DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
            DocumentBuilder db =dbf.newDocumentBuilder();
            document=db.parse(file);
        } else {
            DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            DOMImplementation domi=db.getDOMImplementation();
            document=domi.createDocument(null, "xml", null);
            document.setXmlVersion("1.0");
    
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

   }

   public void CrearXml(String NombreMaestro , String NombreArchivo, String FechaCreacion ){
       if (contador <= longitud){
           String Indice=funcion_hash();

           Element Hash =document.createElement("Hash");
           Hash.setAttribute("Codigo", Indice);

           Element Nombre=document.createElement("Nombre_Archivo");
           Texto=document.createTextNode(NombreArchivo);
           Nombre.appendChild(Texto);

           Element Maestro =document.createElement("Nombre_Maestro");
           Texto=document.createTextNode(NombreMaestro);
           Maestro.appendChild(Texto);

           Element Fecha =document.createElement("Fecha_creacion");
           Texto=document.createTextNode(FechaCreacion);
           Fecha.appendChild(Texto);

           Hash.appendChild(Maestro);
           Hash.appendChild(Nombre);
           Hash.appendChild(Fecha);

           document.getDocumentElement().appendChild(Hash);
           Generar_Documento();
           
       } else {
            System.out.println( "llego a la max capacidad de hash ");
        
       }
   }
   public void Generar_Documento (){
    try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            Source souce =new DOMSource(document);;
            FileWriter fw = new FileWriter(new File (ruta));
            PrintWriter pw= new PrintWriter(fw);
            Result result = new StreamResult (pw);
            transformer.transform(souce, result);
    }  catch(Exception e){
        e.printStackTrace();
        }
    }

   public String funcion_hash () {
    int Indice_Arreglo=0;
    Boolean bo=false;
    Obtener();
    if (file.exists()){
    int  numero = (int) (Math.random() * 96) ;
    Indice_Arreglo =97%numero;
    while (bo==false){
         for (int temp = 1; temp < contador ; temp ++) {
             String resultado= listahash[temp];
            if ( resultado.equals(Integer.toString(Indice_Arreglo)))
                {
                    bo=true;
                }
        }
         if (bo == true) {
             bo= false ;
             Indice_Arreglo++;
             System.out.print("aumentando contador de hash de xml"); 
         } else {
             bo=true;
         }
    }
    } else {
         Indice_Arreglo = 0;
    }
    return  Integer.toString(Indice_Arreglo) ;
    }
    public void Obtener (){
        NodeList nodoRaiz = document.getDocumentElement().getElementsByTagName("Hash");
          contador =0;
          for (int temp = 0; temp < nodoRaiz.getLength(); temp++) {
              Node nodo = nodoRaiz.item(temp);
              if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                  Element element = (Element) nodo;
                  listahash[temp]= element.getAttribute("Codigo");  
              }
              contador ++;
          }
      }

}