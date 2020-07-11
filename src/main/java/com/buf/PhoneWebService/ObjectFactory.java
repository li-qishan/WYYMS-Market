
package com.buf.PhoneWebService;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the PhoneWebService package. 
 * <p>An ObjectFactory allows you to programatically 
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
public class ObjectFactory {

    private final static QName _SgpmsGetDataResponseReturn_QNAME = new QName("http://ydzyService.webservice.ep", "return");
    private final static QName _SgpmsGetDataParam0_QNAME = new QName("http://ydzyService.webservice.ep", "param0");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: PhoneWebService
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SgpmsGetDataResponse }
     * 
     */
    public SgpmsGetDataResponse createSgpmsGetDataResponse() {
        return new SgpmsGetDataResponse();
    }

    /**
     * Create an instance of {@link SgpmsGetData }
     * 
     */
    public SgpmsGetData createSgpmsGetData() {
        return new SgpmsGetData();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ydzyService.webservice.ep", name = "return", scope = SgpmsGetDataResponse.class)
    public JAXBElement<String> createSgpmsGetDataResponseReturn(String value) {
        return new JAXBElement<String>(_SgpmsGetDataResponseReturn_QNAME, String.class, SgpmsGetDataResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ydzyService.webservice.ep", name = "param0", scope = SgpmsGetData.class)
    public JAXBElement<String> createSgpmsGetDataParam0(String value) {
        return new JAXBElement<String>(_SgpmsGetDataParam0_QNAME, String.class, SgpmsGetData.class, value);
    }

}
