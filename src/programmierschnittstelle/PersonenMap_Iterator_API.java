package programmierschnittstelle;


// Ab hier, alls für SAX-Parser
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.namespace.QName;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// Ab hier alles für Iterator-API
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;

class Person3{
    private String id;
    private String friends;
    private String firstname;
    private String lastname;
    private String residence;

    public Person3(String id, String friends, String firstname, String lastname, String residence){
        this.id = id;
        this.friends = friends;
        this.firstname = firstname;
        this.lastname = lastname;
        this.residence = residence;
    }

    public String getId(){
        return id;
    }

    public String getFriends(){
        return friends;
    }

    public String getFirstname(){
        return firstname;
    }

    public String getLastname(){
        return lastname;
    }

    public String getResidence(){
        return residence;
    }

    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public void setResidence(String residence){
        this.residence = residence;
    }
}

public class PersonenMap_Iterator_API extends DefaultHandler{

    private Map<String, Person3> personenMap = new TreeMap<>();

    int children;
    boolean person = false;
    private String aktElement;
    private Person3 aktPerson;

    public static void main(String[] args){

        try{
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileInputStream("C:\\Users\\hosse\\IdeaProjects\\Java-Projekt\\src\\programmierschnittstelle\\persons.xml"));

            PersonenMap_Iterator_API personenMap = new PersonenMap_Iterator_API();
            personenMap.parseXML(eventReader);
            personenMap.personenSortiert();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void parseXML(XMLEventReader eventReader) throws XMLStreamException {
        while(eventReader.hasNext()){
            XMLEvent event = eventReader.nextEvent();

            if(event.isStartElement()){
                StartElement startElement = event.asStartElement();
                aktElement = startElement.getName().getLocalPart();
                String qName = startElement.getName().getLocalPart();

                if(qName.equals("children")){
                    ++children;
                }

                if(children > 0 && qName.equals("person")){
                    person = true;
                    String id = startElement.getAttributeByName(new QName("id")).getValue();

                    String friends = null;
                    if (startElement.getAttributeByName(new QName("friends")) != null) {
                        friends = startElement.getAttributeByName(new QName("friends")).getValue();
                    }
                    aktPerson = new Person3(id, friends, null, null, null);
                    personenMap.put(id, aktPerson);
                }
            }

            if(event.isEndElement()){
                aktElement = "";
                EndElement endElement = event.asEndElement();
                String qName = endElement.getName().getLocalPart();

                if(qName.equals("children")){
                    --children;
                }

                if(qName.equals("person")){
                    person = false;
                }
            }

            if(event.isCharacters() && children > 0 && person){
                Characters characters = event.asCharacters();
                String inhalt = characters.getData().trim();

                if(!inhalt.isEmpty()){

                    if(aktElement.equals("firstname")){
                        aktPerson.setFirstname(inhalt);
                    }

                    if(aktElement.equals("lastname")){
                        aktPerson.setLastname(inhalt);
                    }

                    if(aktElement.equals("residence")){
                        aktPerson.setResidence(inhalt);
                    }

                }
            }
        }
    }

    public void personenSortiert(){
        List<Person3> sortiertePersonen = new ArrayList<>(personenMap.values());
        sortiertePersonen.sort(Comparator.comparing(Person3::getFirstname));

        for(Person3 person : sortiertePersonen){
            System.out.println("ID: " + person.getId());

            if(person.getFriends() != null){
                System.out.println("Friends: " + person.getFriends());
            }

            System.out.println("Firstname: " + person.getFirstname());

            if(person.getLastname() != null){
                System.out.println("Lastname: " + person.getLastname());
            }

            if(person.getResidence() != null){
                System.out.println("Residence: " + person.getResidence());
            }

            System.out.println("--------------");
        }
    }
}




































