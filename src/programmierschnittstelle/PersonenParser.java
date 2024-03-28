package programmierschnittstelle;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class PersonenParser extends DefaultHandler {

    public static void main(String[] args) {
        try {
            // SAXParserFactory erstellen
            SAXParserFactory factory = SAXParserFactory.newInstance();

            // SAXParser erstellen
            SAXParser saxParser = factory.newSAXParser();

            // Handler für SAX-Ereignisse definieren
            DefaultHandler handler = new DefaultHandler() {

                int children = 0;
                boolean vchildren = false;
                boolean person = false;

                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    // Prüfen, ob das aktuelle Element ein "children"-Element ist
                    if (qName.equalsIgnoreCase("children")) {
                        ++children;
                    }
                    if(children > 0){
                        vchildren = true;
                    }

                    // Prüfen, ob sich das "person"-Element innerhalb des "children"-Elements befindet
                    if (vchildren && qName.equalsIgnoreCase("person")) {
                        person = true;

                        for(int i = 0; i < attributes.getLength(); ++i){
                            System.out.println();
                            System.out.println("Attribut: " + attributes.getQName(i) + " = " + attributes.getValue(i) + " ");
                        }
                    }
                }

                public void endElement(String uri, String localName, String qName) throws SAXException {
                    // Falls "children"-Element verlassen, Zustand von "children" zurücksetzen
                    if (qName.equalsIgnoreCase("children")) {
                        --children; // children nicht als boolean machen - p-18 nicht dabei
                        // Wahrscheinlich children einmal als int, dann inkrementieren,
                        // dann children einmal als boolean
                    }

                    if(children <= 0 ){
                        vchildren = false;
                    }

                    // Falls "person"-Element verlassen, Zustand von "person" zurücksetzen
                    if (qName.equalsIgnoreCase("person")) {
                        person = false;
                    }
                }

                public void characters(char[] ch, int start, int length) throws SAXException {
                    // Zeichendaten ausgeben, falls Parser innerhalb von "person" ist, die innerhalb von "children" ist
                    if (vchildren && person) {
                        System.out.println("Daten von Person: " + new String(ch, start, length));
                        // System.out.println();
                    }
                }
            };

            // XML-Dokument parsen
            saxParser.parse(new File("C:\\Users\\hosse\\IdeaProjects\\Java-Projekt\\src\\programmierschnittstelle\\persons.xml"), handler);


        } catch (IOException | SAXException | javax.xml.parsers.ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}