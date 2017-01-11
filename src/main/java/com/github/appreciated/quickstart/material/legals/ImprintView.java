package com.github.appreciated.quickstart.material.legals;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;

/**
 * Created by appreciated on 11.01.2017.
 */
public class ImprintView extends ImprintDesign {

    public void setName(String name) {
        this.name.setValue(name);
    }

    public void setStreet(String street) {
        this.street.setValue(street);
    }

    public void setAddress(String address) {
        this.address.setValue(address);
    }

    public void setPhone(String phone) {
        this.phone.setValue(phone);
    }

    public void setMail(String mail) {
        this.mail.setValue("<a href=\"mailto:" + mail + "\">" + mail + "</a>");
    }

    public ImprintView withFullAddress(String name, String street, String address) {
        setName(name);
        setStreet(street);
        setAddress(address);
        return this;
    }

    public ImprintView withFullContact(String phone, String mail) {
        setPhone(phone);
        setMail(mail);
        return this;
    }

    public FormLayout getSenderAddress() {
        return senderAddress;
    }

    public Label getName() {
        return name;
    }

    public Label getStreet() {
        return street;
    }

    public Label getAddress() {
        return address;
    }

    public FormLayout getContact() {
        return contact;
    }

    public Label getPhone() {
        return phone;
    }

    public Label getMail() {
        return mail;
    }
}
