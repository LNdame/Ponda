package cite.ansteph.ponda.model;

import java.io.Serializable;

/**
 * Created by loicstephan on 2018/02/12.
 */

public class Client implements Serializable {
    int id;

    String name ,websiteUrl,telephone,contactPerson,contactPhone,email;

    public Client(){}

    public Client(int id, String name) {
        this.id = id;
        this.name = name;

    }

    //delete after use
    public Client(int id,String name, String contactPerson) {
        this.id = id;
        this.name = name;
        this.contactPerson = contactPerson;
    }

    public Client( String name, String websiteUrl, String telephone, String contactPerson, String contactPhone, String email) {
        this.name = name;
        this.websiteUrl = websiteUrl;
        this.telephone = telephone;
        this.contactPerson = contactPerson;
        this.contactPhone = contactPhone;
        this.email = email;
    }


    public Client(int id, String name, String websiteUrl, String telephone, String contactPerson, String contactPhone, String email) {
        this.id = id;
        this.name = name;
        this.websiteUrl = websiteUrl;
        this.telephone = telephone;
        this.contactPerson = contactPerson;
        this.contactPhone = contactPhone;
        this.email = email;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
