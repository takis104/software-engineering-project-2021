package schoolink;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Person {
	private int id=-1;
    private String firstName = null;
    private String lastName = null;
    private String email = null;
    private BooleanProperty selected;

    public Person() {
    }

    public Person(Integer id, String firstName, String lastName, String email) {
    	this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.selected = new SimpleBooleanProperty(false);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Integer getId() {
        return id;
    }

    public void setLastName(Integer id) {
        this.id = id;
    }
       
    public boolean isSelected() {
        return selected.get();
      }
    public void setSelected(boolean selected) {
        this.selected.set(selected);
      }
    public BooleanProperty selectedProperty() {
        return selected;
      }
}
