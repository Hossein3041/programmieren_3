package Notizen_StaX;

// Aufgabe: Mittels der Iterator-API von StAX sollen alle in einem XML-Dokument
// auftretenden Ereignistypen ausgegeben werden.

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Iterator_API_Kopie{
    public static void main(String[] args){
        try{
            // get XML input Factory
            XMLInputFactory factory = XMLInputFactory.newInstance();

            // create XML Event reader
            XMLEventReader eventReader = factory.createXMLEventReader(new FileInputStream("C:\\Users\\hosse\\IdeaProjects\\Java-Projekt\\src\\Notizen_StaX\\persons.xml"));


            while(eventReader.hasNext()){
                XMLEvent currentEvent = eventReader.nextEvent();
                if(currentEvent.isStartElement()){
                    StartElement startElement = currentEvent.asStartElement();
                    System.out.printf("<%s>\n", startElement.getName());
                    startElement.getAttributes().forEachRemaining(
                            attribute -> System.out.printf(" %s: %s\n",
                                    attribute.getName(), attribute.getValue())
                    );
                }
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (XMLStreamException e){
            e.printStackTrace();
        }
    }
}