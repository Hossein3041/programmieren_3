//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v4.0.4 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
//


package main.java.org.example.daten;

import javax.xml.namespace.QName;
import jakarta.annotation.Generated;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the daten package. 
 * <p>An ObjectFactory allows you to programmatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
@Generated(value = "com.sun.tools.xjc.Driver", comments = "JAXB RI v4.0.4", date = "2024-01-15T14:23:13+01:00")
public class ObjectFactory {

    private static final QName _Items_QNAME = new QName("http://assignment02.webprog.de", "items");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: daten
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ItemsType }
     * 
     * @return
     *     the new instance of {@link ItemsType }
     */
    public ItemsType createItemsType() {
        return new ItemsType();
    }

    /**
     * Create an instance of {@link ItemType }
     * 
     * @return
     *     the new instance of {@link ItemType }
     */
    public ItemType createItemType() {
        return new ItemType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ItemsType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ItemsType }{@code >}
     */
    @XmlElementDecl(namespace = "http://assignment02.webprog.de", name = "items")
    public JAXBElement<ItemsType> createItems(ItemsType value) {
        return new JAXBElement<>(_Items_QNAME, ItemsType.class, null, value);
    }

}
