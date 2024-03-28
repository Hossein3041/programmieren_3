import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PersonenMapBeispiel {
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                Stack<Integer> personenTiefeStack = new Stack<>();
                Set<Integer> maximaleHierarchie = new HashSet<>();
                boolean hatKinder = false;

                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("person")) {
                        personenTiefeStack.push(personenTiefeStack.isEmpty() ? 1 : personenTiefeStack.peek() + 1);
                        hatKinder = false; // Zurücksetzen für jede neue Person
                    }
                    if (qName.equalsIgnoreCase("children")) {
                        hatKinder = true; // Diese Person hat Kinder
                    }
                }

                public void characters(char[] ch, int start, int length) throws SAXException {
                    if (!personenTiefeStack.isEmpty() && !hatKinder) {
                        int aktuelleTiefe = personenTiefeStack.peek();
                        if (maximaleHierarchie.contains(aktuelleTiefe)) {
                            System.out.println("Person der letzten Hierarchieebene: " + new String(ch, start, length).trim());
                        }
                    }
                }

                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("person")) {
                        int tiefe = personenTiefeStack.pop();
                        if (!hatKinder) { // Wenn keine Kinder, fügen Sie die Tiefe hinzu
                            maximaleHierarchie.add(tiefe);
                        }
                    }
                    if (qName.equalsIgnoreCase("children")) {
                        hatKinder = false; // Zurücksetzen, wenn das children-Element endet
                    }
                }
            };

            saxParser.parse(new File("C:\\Users\\hosse\\IdeaProjects\\Java-Projekt\\src\\programmierschnittstelle\\persons.xml"), handler);

        } catch (IOException | SAXException | javax.xml.parsers.ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
