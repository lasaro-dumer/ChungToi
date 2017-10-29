
package chungtoi.client.proxy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for movePiece complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="movePiece">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="currentPosition" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="direction" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="movement" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="newOrientation" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "movePiece", propOrder = {
    "userId",
    "currentPosition",
    "direction",
    "movement",
    "newOrientation"
})
public class MovePiece {

    protected int userId;
    protected int currentPosition;
    protected int direction;
    protected int movement;
    protected int newOrientation;

    /**
     * Gets the value of the userId property.
     * 
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     */
    public void setUserId(int value) {
        this.userId = value;
    }

    /**
     * Gets the value of the currentPosition property.
     * 
     */
    public int getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Sets the value of the currentPosition property.
     * 
     */
    public void setCurrentPosition(int value) {
        this.currentPosition = value;
    }

    /**
     * Gets the value of the direction property.
     * 
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Sets the value of the direction property.
     * 
     */
    public void setDirection(int value) {
        this.direction = value;
    }

    /**
     * Gets the value of the movement property.
     * 
     */
    public int getMovement() {
        return movement;
    }

    /**
     * Sets the value of the movement property.
     * 
     */
    public void setMovement(int value) {
        this.movement = value;
    }

    /**
     * Gets the value of the newOrientation property.
     * 
     */
    public int getNewOrientation() {
        return newOrientation;
    }

    /**
     * Sets the value of the newOrientation property.
     * 
     */
    public void setNewOrientation(int value) {
        this.newOrientation = value;
    }

}
