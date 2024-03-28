package programmierschnittstelle;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// Erstellt Klasse Person, mit den entsprechenden Variablen für die Daten von Personen
class Person {

    // Die Variablen sind private - sie sind nur innerhalb der Klasse zugänglich - von außen direkt nicht zugänglich
    private String id;
    private String friends;
    private String firstname;
    private String lastname;
    private String residence;

    // Konstruktor für die Klasse Person - wird nur aufgerufen, wenn ein neues Objekt der Klasse Person erstellt wird.
    // Beim Erstellen einer neuen Person-Objekt werden alle relevanten Eigenschaften
    // (id, friends, firstname, lastname und residence ) gesetzt.
    // Der Konstruktur nimmt fünf Parameter entgegen - Sie dienen dazu,
    // die Eigenschaften/Variablen der Klasse zu initialisieren.
    public Person(String id, String friends, String firstname, String lastname, String residence) {
        // Die übergebenen Parameterwerte werden verwendent, um die privaten Variablen zu setzten.
        this.id = id;
        this.friends = friends;
        this.firstname = firstname;
        this.lastname = lastname;
        this.residence = residence;
    }

    // Die Methoden unten sind sogenante Getter-Methoden:
    // Die Getter Methoden werden verwendet, um auf die privaten Variablen der Klasse zuzugreifen.
    // Damit sollen die Werte genommen werden, und später ausgegeben werden.
    public String getId() {
        // Beispiel:
        // Durch Methode getId(){}, soll durch return id; der Wert von Variable id genommen werden.
        return id;
    }

    public String getFriends() {
        return friends;
        //return (friends != null) ? friends : "";
    }

    public String getFirstname() {
        return firstname;
        //return (firstname != null) ? firstname : "";
    }

    public String getLastname() {
        return lastname;
        //return (lastname != null) ? lastname : "";
    }

    public String getResidence() {
        return residence;
        //return (residence != null) ? residence : "";
    }


    // Setter-Methoden
    // Setter-Methoden - Dadurch wird das Ändern der Werte der Variablen ermöglicht.
    public void setFirstname(String firstname) {
        this.firstname = firstname;
        //this.firstname = (firstname != null) ? firstname : "";
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
        //this.lastname = (lastname != null) ? lastname : "";
    }

    public void setResidence(String residence) {
        this.residence = residence;
        //this.residence = (residence != null) ? residence : "";
    }
}

public class PersonenMap extends DefaultHandler{
    // Deklariert private Variable namens 'personenMap', genauer gesagt ein TreeMap.
    // Schlüssel davon ist ein String, Wert davin ist vom Typ Person - dient dazu,
    private Map<String, Person> personenMap = new TreeMap<>();

    int children;
    boolean person = false;

    private String aktElement;
    private Person aktPerson;

    public static void main(String[] args){
        try{
            // SAXParserFactory erstellen
            SAXParserFactory factory = SAXParserFactory.newInstance();

            // SAXParser erstellen
            SAXParser saxParser = factory.newSAXParser();

            // Handler für SAX-Ereignisse definieren
            DefaultHandler handler = new PersonenMap(); // Mit Name der Klasse

            // XML-Dokument parsen
            saxParser.parse(new File("C:\\Users\\hosse\\IdeaProjects\\Java-Projekt\\src\\programmierschnittstelle\\persons.xml"), handler);

            ((PersonenMap) handler).personenSortiert();

        } catch (IOException | SAXException | javax.xml.parsers.ParserConfigurationException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        // aktElement, mit Name des aktuellen Elements initialisieren
        aktElement = qName;

        // Prüfen, ob das aktuelle Element eon "children"-Element ist
        if(qName.equalsIgnoreCase("children")){
            ++children;
        }

        // Prüfen, ob sich das "person"-Element innerhalb des "children"-Element befindet
        if(children > 0 && qName.equalsIgnoreCase("person")){
            person = true;
            // Wert von ID-Attribut herausarbeiten
            String id = attributes.getValue("id");

            // Wert von friends-Attribut herausarbeiten
            String friends = attributes.getValue("friends");

            // Neues Person-Objekt erstellen, das dann die Werte in den Konstruktor oben nach Reihenfolge einsetzt
            aktPerson = new Person(id.trim(), friends, null, null, null);

            // Person Objekt, zu TreeMap hinzufügen
            personenMap.put(id, aktPerson);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException{

        // aktElement zurücksetzen
        aktElement = "";

        // Falls ein "children"-Element verlassen, children dekrementieren
        if(qName.equalsIgnoreCase("children")){
            --children;
        }

        if(qName.equalsIgnoreCase("person")){
            person = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException{
        // Falls Person vorhanden, Wert/Textinhalt, in value setzen
        if(children > 0 && person){
            String inhalt = new String(ch, start, length).trim();

            // Falls Inhalt von "firstname"-Element nicht leer ist, soll das in setFirstname hinzugefügt werden:
            if(aktElement.equals("firstname")){
                aktPerson.setFirstname(inhalt);
            }

            // dito für lastname
            if(aktElement.equals("lastname")){
                aktPerson.setLastname(inhalt);
            }

            // dito für residence
            if(aktElement.equals("residence")){
                aktPerson.setResidence(inhalt);
            }

        }
    }
    public void personenSortiert(){
        // Personen nach Vornamen sortieren
        List<Person> sortiertePersonen = new ArrayList<>(personenMap.values());
        sortiertePersonen.sort(Comparator.comparing(Person::getFirstname));

        // Personen nach Vornamen sortiert ausgeben:
        for(Person person : sortiertePersonen){
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