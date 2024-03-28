package Notizen_StaX;

import javax.xml.stream.*;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/* Aufgabe: Mithilfe der Cursor-API von StAX soll der Cursor über alle Ereignisse in einem XML-Dokument bewegt werden;
die Nummern der Ereignisse, die Namen aller XML-Elemente sowie die enthaltenen Attribute ausgegeben werden.

 */

public class Curser_API {
    public static void main(String[] args){
        try{
            // get XML input factory
            XMLInputFactory factory = XMLInputFactory.newInstance();
            // create XML Stream reader
            XMLStreamReader strmReader = factory.createXMLStreamReader(new FileInputStream("C:\\Users\\hosse\\IdeaProjects\\Java-Projekt\\src\\Notizen_StaX\\persons.xml"));


            // first event is always START_DOCUMENT
            int eventType = strmReader.getEventType();
            assert eventType == XMLEvent.START_DOCUMENT : "type of first event must be START_DOCUMENT";

            // list all events
            /*
            while(strmReader.hasNext()){
                // print event type
                eventType = strmReader.next();
                System.out.printf("got event %s\n", eventType);

                // print element names and attributes
                if(eventType == XMLEvent.START_ELEMENT){
                    if(strmReader.isStartElement()){
                        String elementName = strmReader.getLocalName();
                        int attributeCount = strmReader.getAttributeCount();
                        System.out.printf("<%s> %s\n", elementName,
                                (attributeCount > 0 ? "with attributes" : ""));
                        for(int i = 0; i < attributeCount; i += 1){
                            strmReader.getAttributeName(i);
                            strmReader.getAttributeValue(i);
                        }
                    }
                }
            }

             */

            // Es sollen nur die Attribute der chapter-Elemente ausgegeben werden
            /*
            while(strmReader.hasNext()){
                strmReader.next();
                // find a chapter element and print its attributes
                if(strmReader.isStartElement() && strmReader.getLocalName().equals("person")){
                    int attributeCount = strmReader.getAttributeCount();
                    System.out.printf("<%s>\n", strmReader.getLocalName());
                    for(int i = 0; i < attributeCount; i += 1){
                        System.out.printf(" %s: %s\n",
                                strmReader.getAttributeName(i),
                                strmReader.getAttributeValue(i));
                    }
                }
            }

            while(strmReader.hasNext()){
                strmReader.next();
                // find a chapter element and print its attributes
                if(strmReader.isStartElement() && strmReader.getLocalName().equals("person")){
                    int attributeCount = strmReader.getAttributeCount();
                    System.out.printf("<%s>\n", strmReader.getLocalName());
                    for(int i = 0; i < attributeCount; i += 1){
                        System.out.printf(" %s: %s\n",
                                strmReader.getAttributeName(i),
                                strmReader.getAttributeValue(i));
                    }
                }
            }

             */

            /*
            while (strmReader.hasNext()) {
                strmReader.next();
                // find a chapter element and print its attributes
                if (strmReader.isStartElement() &&
                        strmReader.getLocalName().equals("person")) {
                    int attributeCount = strmReader.getAttributeCount();
                    System.out.printf("<%s>\n", strmReader.getLocalName());
                    for (int i = 0; i < attributeCount; i += 1) {
                        System.out.printf(" %s: %s\n",
                                strmReader.getAttributeName(i),
                                strmReader.getAttributeValue(i));
                    }
                }
            }

             */

            XMLStreamReader filteredStrmReader = factory.createFilteredReader(strmReader,
                    new StreamFilter(){
                        public boolean accept(XMLStreamReader r){
                            return r.isStartElement() && r.getLocalName().equals("person");
                        }
                    });

            // list all events using filtered reader
            while(filteredStrmReader.hasNext()){
                filteredStrmReader.next();
                if(filteredStrmReader.isStartElement()){
                    int attributeCount = filteredStrmReader.getAttributeCount();
                    System.out.printf("<%s>\n", filteredStrmReader.getLocalName());
                    for(int i = 0; i < attributeCount; i++){
                        System.out.printf(" %s: %s\n",
                                filteredStrmReader.getAttributeName(i),
                                filteredStrmReader.getAttributeValue(i));
                    }
                }
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (XMLStreamException e){
            e.printStackTrace();
        }
    }
}
