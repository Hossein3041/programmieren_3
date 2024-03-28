package programmierschnittstelle;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class VornamenParser extends DefaultHandler{
    public static void main(String[] args){
        try{

            // SAXParserFactory erstellen
            SAXParserFactory factoryName = SAXParserFactory.newInstance();

            // SAXParser erstellen
            SAXParser saxParser = factoryName.newSAXParser();

            // Handler für  SAX-Ereignisse definieren
            DefaultHandler handler = new DefaultHandler(){
                boolean vorname = false;

                // Methode startElement wird aufgerufen, wenn Startelement gefunden wurde.
                // String uri - Angabe für evtl. Namensraum.
                // String localName - Name des Elements ohne Präfix.
                // String qName - Name des Elements mit Präfix.
                // Attributes attribute  - evtl. vorhandenes Attribut.
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

                    // Es wird überprüft, ob das Startelement "firstname" ist.
                    if(qName.equalsIgnoreCase("firstname")){
                        vorname = true;
                    }
                }

                // Benachrichtigung über das Einlesen eines Blocks von Zeichen (character data);
                // die aktuell gelesenen length Zeichen sind im Array ch ab der Position start aufgeführt.
                public void characters(char[] ch, int start, int length) throws SAXException{

                    if(vorname == true){ // Überprüft, ob Parser sich innerhalb des Elements <firstname> befindet

                        // Inhalt des Elements wird ausgegeben:
                        // new String(ch, start, length) - Erstellt String aus char-Array 'ch'
                        // beginnt bei der Position 'start', mit einer Länge von length
                        System.out.println("Vorname ist: " + new String(ch, start, length).trim());

                        // vorname wird auf false gesetzt, um zu zeigen, dass der Parser das Element verlassen hat.
                        vorname = false;
                    }
                }
            };

            saxParser.parse(new File("C:\\Users\\hosse\\IdeaProjects\\Java-Projekt\\src\\programmierschnittstelle\\persons.xml"), handler);

        } catch (IOException | SAXException | javax.xml.parsers.ParserConfigurationException e){
            e.printStackTrace();
        }

    }
}
