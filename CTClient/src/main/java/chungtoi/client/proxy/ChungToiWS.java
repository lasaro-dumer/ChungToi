
package chungtoi.client.proxy;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "ChungToiWS", targetNamespace = "http://server.chungtoi/", wsdlLocation = "http://localhost:8080/ctwebservice/ChungToiWS?wsdl")
public class ChungToiWS
    extends Service
{

    private final static URL CHUNGTOIWS_WSDL_LOCATION;
    private final static WebServiceException CHUNGTOIWS_EXCEPTION;
    private final static QName CHUNGTOIWS_QNAME = new QName("http://server.chungtoi/", "ChungToiWS");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/ctwebservice/ChungToiWS?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CHUNGTOIWS_WSDL_LOCATION = url;
        CHUNGTOIWS_EXCEPTION = e;
    }

    public ChungToiWS() {
        super(__getWsdlLocation(), CHUNGTOIWS_QNAME);
    }

    public ChungToiWS(WebServiceFeature... features) {
        super(__getWsdlLocation(), CHUNGTOIWS_QNAME, features);
    }

    public ChungToiWS(URL wsdlLocation) {
        super(wsdlLocation, CHUNGTOIWS_QNAME);
    }

    public ChungToiWS(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CHUNGTOIWS_QNAME, features);
    }

    public ChungToiWS(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ChungToiWS(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ChungToi
     */
    @WebEndpoint(name = "ChungToiPort")
    public ChungToi getChungToiPort() {
        return super.getPort(new QName("http://server.chungtoi/", "ChungToiPort"), ChungToi.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ChungToi
     */
    @WebEndpoint(name = "ChungToiPort")
    public ChungToi getChungToiPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://server.chungtoi/", "ChungToiPort"), ChungToi.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CHUNGTOIWS_EXCEPTION!= null) {
            throw CHUNGTOIWS_EXCEPTION;
        }
        return CHUNGTOIWS_WSDL_LOCATION;
    }

}
