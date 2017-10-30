
package chungtoi.client.proxy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for preRegistro complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="preRegistro">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="playerOneName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="playerOneId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="playerTwoName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="playerTwoId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "preRegistro", propOrder = {
    "playerOneName",
    "playerOneId",
    "playerTwoName",
    "playerTwoId"
})
public class PreRegistro {

    protected String playerOneName;
    protected int playerOneId;
    protected String playerTwoName;
    protected int playerTwoId;

    /**
     * Gets the value of the playerOneName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlayerOneName() {
        return playerOneName;
    }

    /**
     * Sets the value of the playerOneName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlayerOneName(String value) {
        this.playerOneName = value;
    }

    /**
     * Gets the value of the playerOneId property.
     * 
     */
    public int getPlayerOneId() {
        return playerOneId;
    }

    /**
     * Sets the value of the playerOneId property.
     * 
     */
    public void setPlayerOneId(int value) {
        this.playerOneId = value;
    }

    /**
     * Gets the value of the playerTwoName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlayerTwoName() {
        return playerTwoName;
    }

    /**
     * Sets the value of the playerTwoName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlayerTwoName(String value) {
        this.playerTwoName = value;
    }

    /**
     * Gets the value of the playerTwoId property.
     * 
     */
    public int getPlayerTwoId() {
        return playerTwoId;
    }

    /**
     * Sets the value of the playerTwoId property.
     * 
     */
    public void setPlayerTwoId(int value) {
        this.playerTwoId = value;
    }

}
