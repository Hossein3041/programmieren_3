package main.java.org.example;

// Importe von Stax
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Marshaller;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.Characters;
//import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.io.File;

// Importe von JAXB
//import jakarta.xml.bind.JAXBContext;
//import jakarta.xml.bind.Marshaller;


import main.java.org.example.daten.ItemsType;
import main.java.org.example.daten.ItemType;
import main.java.org.example.daten.ObjectFactory;
import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;

// Zum Validieren des XML-Schemas
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.XMLConstants;

public class RssFilterStax {

    public static void main(String[] args) throws Exception{

        // Hierbei werden die Argumente an den Laufzeitparameter übergeben
        if(args.length == 0){ // Falls kein Argument
            System.out.println("Keine Argumente übergeben!");
        } else if(args.length > 1){ // Falls Argument mehr als 1
            System.out.println("Zu viele Argumente übergeben!");
        }  else { // Argument ist gleicht 1, also den Rest des Codes weiterlesen
             try {
                // Ausgabedatei in die Variable einpacken
                File ausgabeFile = new File("Artikel.xml");

                // Überprüfen, ob das Treffer-Dokument bereits existiert. Falls ja, dann das Dokument löschen
                if (ausgabeFile.exists()) {
                    if (!ausgabeFile.delete()) { // durch .delete()-Funktion, die ausgabeFile löschen. Falls durch ! nicht gelöscht, dann Fehler geben
                        System.out.println("Fehler: Vorhandenes Dokument konnte nicht gelöscht werden!");
                        return; // Wenn das Dokument nicht gelöscht werden könnte, das Programm beenden!
                    }
                }

                // JAXB-Kontext und Marshaller initialisieren
                JAXBContext context = JAXBContext.newInstance(ItemsType.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.setProperty("org.glassfish.jaxb.namespacePrefixMapper", new NamespacePrefixMapper(){
                    @Override
                    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix){ // expliziten Namensraum in den Treffer-Dokument hinzufügen
                        return "stax"; // als stax
                    }
                });

                // Laden und Setzen des Schemas für die Validierung
                SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                try{
                    // Das XML-Schema nehmen und automatisch validieren
                    InputStream xsdStream = RssFilterStax.class.getResourceAsStream("/item.xsd"); // Das Ziel war es, item.xsd in den jar-Datei miteinzunehmen. Dafür müsste die Datei in den resource-Verzeichnis rein. Um nach resource-Verzeichnis, und dann nach item.xsd zuzugreifen, durch Klassenname.class.Ressource
                    if (xsdStream == null) { // Falls der Inhalt Leer ist, dann Fehlermeldung geben
                        throw new FileNotFoundException("Cannot find 'item.xsd' in classpath");
                    }

                    // Hier wird ein StreamSoure-Objekt erstellt, basierend auf dem InputStream des XML-Schemas.
                    // Dies wird verwendet, um eine Datenquelle für das Schema zu erstellen.
                    StreamSource schemaFile = new StreamSource(xsdStream);

                    // Aus StreamSource, ein Schema-Objekt erstellen.
                    // Schema-Objekt wird verwendet, um XMl-Daten gegen XML-Schema zu validieren und diese miteinander zu verglechen
                    Schema schema = sf.newSchema(schemaFile);

                    // Schema in JAXB Marshaller setzen, um die XML-Daten gegen den Schema zu validieren, während des Generierens
                    marshaller.setSchema(schema);
                } catch(Exception e){
                    // Fehlermeldung, falls etwas fehlschlägt
                    e.printStackTrace();
                }

                // Erstellen des ItemsType-Objekts, das alle Items enthält
                ItemsType items = new ItemsType();

                // Hier wird das Argument als URL genommemn.
                URL url = new URL(args[0]);

                // XMLInputFactory erstellen.
                XMLInputFactory factory = XMLInputFactory.newInstance();

                // Eventreader erstellen, durch Argument
                XMLEventReader eventReader = factory.createXMLEventReader(url.openStream());

                // Variable, um zu identifizieren, ob item-Element gefunden wurde
                boolean item = false;
                //boolean description = false;

                 // Variablen aktElement, um das aktuelle Element zu speichern
                String aktElement = "";

                // Variablen, für das Nehmen vom Textinhalt aller relevanten Daten
                String aktTitle = "";
                String aktLink = "";
                String aktDescription = "";
                String aktPubDate = "";

                // Array für das Testen der Begriffe
                String[] wt = {"Android", "Java", "Welt", "Mensch", "Berlin"};

                // Solange event vorhanden ist
                while (eventReader.hasNext()) {

                    // Den nächsten Event nehmen
                    XMLEvent event = eventReader.nextEvent();

                    if (event.isStartElement()) {
                        StartElement startElement = event.asStartElement();
                        String elementName = startElement.getName().getLocalPart();

                        // Item true, falls item gefunden
                        if (elementName.equals("item")) {
                            item = true;
                            aktTitle = "";
                            aktLink = "";
                            aktDescription = "";
                            aktPubDate = "";
                        }

                        // Falls item gefunden, aktElement setzen als das aktuell gefundene Element
                        if (item) {
                            aktElement = elementName;
                        }
                    }

                    if (event.isCharacters() && item) {
                        Characters characters = event.asCharacters();
                        String text = characters.getData();

                        // Den Textinhalt aller relevanten Angaben initialisieren
                        if (aktElement.equals("title")) {
                            aktTitle += text;
                        }

                        if (aktElement.equals("link")) {
                            aktLink += text;
                        }

                        if (aktElement.equals("description")) {
                            aktDescription += text;
                        }

                        if (aktElement.equals("pubDate")) {
                            aktPubDate += text;
                        }
                    }

                    if (event.isEndElement()) {
                        EndElement endElement = event.asEndElement();
                        String elementName = endElement.getName().getLocalPart();

                        if (elementName.equals("item") && item) {

                            // item zurücksetzen
                            item = false;
                            for (int i = 0; i < wt.length; i++) {
                                String wort = wt[i];
                                // Die Wörter im Array, mit den Wörten in der Datei vergleichen
                                if (aktDescription.toLowerCase().contains(wort.toLowerCase())) {
                                    // Erstellen eines neuen ItemType-Objekts
                                    ItemType gefundenesItem = new ItemType();

                                    // Initialisieren der Inhalte der relevanten Angaben
                                    gefundenesItem.setTitle(aktTitle);
                                    gefundenesItem.setLink(aktLink);
                                    gefundenesItem.setDescription(aktDescription);
                                    gefundenesItem.setPubDate(aktPubDate); // Sicher stellen, dass das Format des Datums korrekt ist

                                    // Hinzufügen des ItemType-Objekts zum ItemsType-Onjekt
                                    items.getItem().add(gefundenesItem);
                                }
                            }
                        }

                        if (elementName.equals(aktElement)) {
                            // Aktuelles Element zurücksetzen
                            aktElement = "";
                        }
                    }

                }

                // Hier wird geprüft, ob die Liste der items nicht leer ist
                if (!items.getItem().isEmpty()) {
                    // XML-Datei, als Treffer-Dokument wird erstellt
                    try (FileOutputStream fos = new FileOutputStream("Artikel.xml")) {

                        // Objekt-Factory-Instans wird erstelllt. Das ist nötig, um neue Instanzen von JAXB-Elementen zu erstellen
                        ObjectFactory objectFactory = new ObjectFactory();

                        // Die Methode erstellt JAXB-Element, - beinhaltet JAXB-Annonationen
                        JAXBElement<ItemsType> jaxbElement = objectFactory.createItems(items);

                        // Marshaller verwenden, um JAXB-ELement in XML-Darstellung zu überführen, - geschrieben in Artikel.xml
                        marshaller.marshal(jaxbElement, fos);
                        // NAChricht, falls erfolgreich
                        System.out.println("Treffer-Dokument erfolgreich erstellt!");
                    } catch (Exception e) { // Sonst fehlermeldung
                        e.printStackTrace();
                    }
                } else { // Nachricht, falls Rss keine gefragten Items hat
                    System.out.println("Keine gefragten Items gefunden!");
                }

             } catch (Exception e) { // Fehlermeldung, mit Nachricht, falls Link nicht bearbeitet ewrden konnte.
                 System.out.println("Fehler beim Verarbeiten des Links: " + e.getMessage());
             }
        }

    }
}