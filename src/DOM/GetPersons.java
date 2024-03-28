package DOM;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import java.io.IOException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GetPersons {
    public static void main(String[] args){
        String fileName = "persons.xml";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // falls Namensräume im XML verwendet werden
        factory.setValidating(false); // Validierung hier nicht benötigt

        try{
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(fileName);
            NodeList personList = document.getElementsByTagName("person");

            // Ergebnisse ausgeben
            for(int i = 0; i < personList.getLength(); i++){
                Node personNode = personList.item(i);
                // Hier können SIe weitere Informationen aus dem personNode extrahieren
                System.out.println("Person Element gefunden: " + personNode.getNodeName());
            }
        } catch(ParserConfigurationException| SAXException| IOException e){
            System.out.println(">>> Fehler: " + e);
            System.exit(1);
        }
    }
}
