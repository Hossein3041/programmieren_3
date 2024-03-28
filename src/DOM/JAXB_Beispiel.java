package DOM;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.SchemaOutputResolver;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.XMLConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Date;
public class JAXB_Beispiel {
    public static void main(String[] args) {
        try {

            // 1. JAXBContext für das Packet jaxb.script erstellen
            JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.script");

            // 2. Unmarshaller erstellen und XML-Datei einlesen
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            CourseDoc courseDoc = (CourseDoc) unmarshaller.unmarshall(new FileInputStream("script.xml"));

            // 3. Ausgabe der Kapiteldaten:
            printChapterList(courseDoc.getChapter());

            // 4. Modifikation und Hinzufügen eines Kapitels
            modifyChapter(courseDoc);
            addChapter(courseDoc);

            // 5. Validierung beim Marshalling
            Marshaller marshaller = jaxbContext.createMarshaller();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File("schema.xsd"));
            marshaller.setSchema(schema);
            marshaller.marshal(courseDoc, new FileOutputStream("updated_script.xml"));

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    static void printChapterList(List<Chapter> chapterList){
        chapterList.forEach(chapter ->{
            System.out.printf("Chapter: \"%s\", important: \"%s\", interest: \"%s\", sparepages: \"%s\"\n",
                    chapter.getHeading(), chapter.getImportant(), chapter.getInterest(), chapter.getSparePages());
            chapter.getPara().forEach(para -> System.out.printf("Para: \"%s\"\n", para));
        });
    }
        }
    }
}
