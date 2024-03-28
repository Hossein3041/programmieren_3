package DOM;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import java.io.File;
import java.io.IOException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOM_Beispiel {
    private static NodeList getChapters(Document document){
        return document.getElementsByTagName("chapter");
    }

    public static void main(String[] args){
        String fileName = "script.xml";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);

        try{
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(fileName);
            NodeList chapterList = getChapters(document);

            for(int i = 0; i < chapterList.getLength(); i++){
                Node node = chapterList.item(i);
                System.out.println(node.getAttributes().getNamedItem("heading").getNodeValue());
            }
        } catch(ParserConfigurationException | SAXException| IOException e){
            System.out.println(">>> " + e);
            System.exit(1);
        }
    }
}
