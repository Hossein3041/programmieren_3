package DOM;

import org.w3c.dom.*;
import programmierschnittstelle.PersonenMap;

import javax.xml.parsers.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

class Person_DOM{
    private String id;
    private String friends;
    private String firstname;
    private String lastname;
    private String residence;

    public Person_DOM(String id, String friends, String firstname, String lastname, String residence){
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

    public String getLastname(){ return lastname; }

    public String getResidence(){ return residence; }

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
public class PersonenMap_DOM {
    private TreeMap<String, Person_DOM> personenMap = new TreeMap<>();

    public static void main(String[] args){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("C:\\Users\\hosse\\IdeaProjects\\Java-Projekt\\src\\programmierschnittstelle\\persons.xml"));
            PersonenMap_DOM personenMap = new PersonenMap_DOM();
            personenMap.verarbeiteDokument(document);
            personenMap.personenSortiert();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void verarbeiteDokument(Document document){
        NodeList childrenList = document.getElementsByTagName("children");
        for(int i = 0; i < childrenList.getLength(); i++){
            Node childrenNode = childrenList.item(i);
            if(childrenNode.getNodeType() == Node.ELEMENT_NODE){
                Element childrenElement = (Element) childrenNode;
                NodeList personList = childrenElement.getElementsByTagName("person");
                for(int j = 0; j < personList.getLength(); j++){
                    Node personNode = personList.item(j);

                    if(personNode.getNodeType() == Node.ELEMENT_NODE){
                        Element personElement = (Element) personNode;

                        String id = personElement.getAttribute("id");
                        String friends = personElement.getAttribute("friends");
                        String firstname = getElementValue(personElement, "firstname");
                        String lastname = getElementValue(personElement, "lastname");
                        String residence = getElementValue(personElement, "residence");

                        Person_DOM person = new Person_DOM(id, friends, firstname, lastname, residence);
                        personenMap.put(id, person);
                    }
                }
            }
        }
    }

    private String getElementValue(Element parent, String tagName){
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if(nodeList != null && nodeList.getLength() > 0){
            return nodeList.item(0).getTextContent();
        } else {
            return "nicht vorhanden!";
        }
    }

    public void personenSortiert(){
        List<Person_DOM> sortiertePersonen = new ArrayList<>(personenMap.values());
        sortiertePersonen.sort(Comparator.comparing(Person_DOM::getFirstname));

        for(Person_DOM person : sortiertePersonen){
            System.out.println("ID: " + person.getId());

            if(person.getFriends() != null){
                System.out.println("Friends: " + person.getFriends());
            }

            System.out.println("Firstname: " + person.getFirstname());

            if(person.getFriends() != null){
                System.out.println("Lastname: " + person.getLastname());
            }

            if(person.getFriends() != null){
                System.out.println("Residence: " + person.getResidence());
            }

            System.out.println("--------------");
        }
    }
}
