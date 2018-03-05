package cite.ansteph.ponda.model;

import java.io.Serializable;

/**
 * Created by loicstephan on 2018/02/12.
 */

public class Attendee implements Serializable {

    int id;
    String firstname,surname,organisation,telephone,cellphone,fax,email;


    public Attendee() {
    }

    public Attendee(int id, String firstname, String surname) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
    }


    public Attendee(String firstname, String surname, String organisation, String telephone, String cellphone, String fax, String email) {
        this.firstname = firstname;
        this.surname = surname;
        this.organisation = organisation;
        this.telephone = telephone;
        this.cellphone = cellphone;
        this.fax = fax;
        this.email = email;
    }

    public Attendee(int id, String firstname, String surname, String organisation, String telephone, String cellphone, String fax, String email) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.organisation = organisation;
        this.telephone = telephone;
        this.cellphone = cellphone;
        this.fax = fax;
        this.email = email;
    }

    public Attendee(int id, String firstname, String surname) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
