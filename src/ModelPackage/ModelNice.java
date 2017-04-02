/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author Thonon
 */
public class ModelNice extends Model
{
    // eventId
    private final ObjectProperty<Long> eventId = new SimpleObjectProperty<>();
    private final ObjectProperty<String> startDate = new SimpleObjectProperty<>();
    private final ObjectProperty<String> startTime = new SimpleObjectProperty<>();
    private final ObjectProperty<String> endDate = new SimpleObjectProperty<>();
    private final ObjectProperty<String> endTime = new SimpleObjectProperty<>();
    private final ObjectProperty<String> eventType = new SimpleObjectProperty<>();
    private final ObjectProperty<String> numCaller = new SimpleObjectProperty<>();
    private final ObjectProperty<String> numCalled = new SimpleObjectProperty<>();
    private final ObjectProperty<String> categorie = new SimpleObjectProperty<>();
    private final ObjectProperty<String> sens = new SimpleObjectProperty<>();
    private final ObjectProperty<String> synopsis = new SimpleObjectProperty<>();
    private final ObjectProperty<String> smsContent = new SimpleObjectProperty<>();

    
    @XmlElement(name="SMS-Content")
    public String getSmsContent() {
        return smsContent.get();
    }

    public void setSmsContent(String value) {
        smsContent.set(value);
    }

    public ObjectProperty smsContentProperty() {
        return smsContent;
    }

    @XmlElement(name="Synopsis")
    public String getSynopsis() {
        return synopsis.get();
    }

    public void setSynopsis(String value) {
        synopsis.set(value);
    }

    public ObjectProperty synopsisProperty() {
        return synopsis;
    }

    // locations
    private List<ModelNiceLocation> oLocations;

    @XmlElement(name="Direction") 
    public String getSens() {
        return sens.get();
    }

    public void setSens(String value) {
        sens.set(value);
    }

    public ObjectProperty sensProperty() {
        return sens;
    }
    

    @XmlElement(name="Relevancy") 
    public String getCategorie() {
        return categorie.get();
    }

    public void setCategorie(String value) {
        categorie.set(value);
    }

    public ObjectProperty categorieProperty() {
        return categorie;
    }
    

    @XmlElement(name="Called-Number") 
    public String getNumCalled() {
        return numCalled.get();
    }

    public void setNumCalled(String value) {
        numCalled.set(value);
    }

    public ObjectProperty numCalledProperty() {
        return numCalled;
    }
    
    @XmlElement(name="Caller-ID") 
    public String getNumCaller() {
        return numCaller.get();
    }

    public void setNumCaller(String value) {
        numCaller.set(value);
    }

    public ObjectProperty numCallerProperty() {
        return numCaller;
    }
    
    @XmlElement(name="Event-Type") 
    public String getEventType() {
        return eventType.get();
    }

    public void setEventType(String value) {
        eventType.set(value);
    }

    public ObjectProperty eventTypeProperty() {
        return eventType;
    }
    

    @XmlElement(name="End-Time") 
    public String getEndTime() {
        return endTime.get();
    }

    public void setEndTime(String value) {
        endTime.set(value);
    }

    public ObjectProperty endTimeProperty() {
        return endTime;
    }
    
    @XmlElement(name="End-Date") 
    public String getEndDate() {
        return endDate.get();
    }

    public void setEndDate(String value) {
        endDate.set(value);
    }

    public ObjectProperty endDateProperty() {
        return endDate;
    }

    @XmlElement(name="Start-Time") 
    public String getStartTime() {
        return startTime.get();
    }

    public void setStartTime(String value) {
        startTime.set(value);
    }

    public ObjectProperty startTimeProperty() {
        return startTime;
    }

    @XmlElement(name="Start-Date") 
    public String getStartDate() {
        return startDate.get();
    }

    public void setStartDate(String value) {
        startDate.set(value);
    }

    public ObjectProperty startDateProperty() {
        return startDate;
    }
  
 
    @XmlElement(name="Event_ID") 
    public Long getEventId() {
        return eventId.get();
    }

    public void setEventId(Long value) {
        eventId.set(value);
    }

    public ObjectProperty eventIdProperty() {
        return eventId;
    }

    @XmlElement(name="Locations") 
    public List<ModelNiceLocation> getoLocations() {
        return oLocations;
    }

    public void setoLocations(List<ModelNiceLocation> oLocations) {
        this.oLocations = oLocations;
    }

   
    
   
    
    
}
