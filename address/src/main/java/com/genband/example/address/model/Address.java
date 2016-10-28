package com.genband.example.address.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "my_address")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private String contactName;
  private String addressName;

  /**
   * Default constructor for JPA
   */
  public Address() {

  }

  public Address(String contactName, String addressName) {
    this.contactName = contactName;
    this.addressName = addressName;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public String getAddressName() {
    return addressName;
  }

  public void setAddressName(String addressName) {
    this.addressName = addressName;
  }

  @Override
  public String toString() {
    return id + " " + contactName + " " + addressName;
  }
}
