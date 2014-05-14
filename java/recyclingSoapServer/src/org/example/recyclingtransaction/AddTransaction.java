
package org.example.recyclingtransaction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="locationID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="paper" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="plastic" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="glass" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="metal" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "userID",
    "locationID",
    "timestamp",
    "paper",
    "plastic",
    "glass",
    "metal"
})
@XmlRootElement(name = "addTransaction")
public class AddTransaction {

    @XmlElement(required = true)
    protected String userID;
    @XmlElement(required = true)
    protected String locationID;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    protected int paper;
    protected int plastic;
    protected int glass;
    protected int metal;

    /**
     * Gets the value of the userID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the value of the userID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserID(String value) {
        this.userID = value;
    }

    /**
     * Gets the value of the locationID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationID() {
        return locationID;
    }

    /**
     * Sets the value of the locationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationID(String value) {
        this.locationID = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the paper property.
     * 
     */
    public int getPaper() {
        return paper;
    }

    /**
     * Sets the value of the paper property.
     * 
     */
    public void setPaper(int value) {
        this.paper = value;
    }

    /**
     * Gets the value of the plastic property.
     * 
     */
    public int getPlastic() {
        return plastic;
    }

    /**
     * Sets the value of the plastic property.
     * 
     */
    public void setPlastic(int value) {
        this.plastic = value;
    }

    /**
     * Gets the value of the glass property.
     * 
     */
    public int getGlass() {
        return glass;
    }

    /**
     * Sets the value of the glass property.
     * 
     */
    public void setGlass(int value) {
        this.glass = value;
    }

    /**
     * Gets the value of the metal property.
     * 
     */
    public int getMetal() {
        return metal;
    }

    /**
     * Sets the value of the metal property.
     * 
     */
    public void setMetal(int value) {
        this.metal = value;
    }

}
