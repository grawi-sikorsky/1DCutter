package pl.printo3d.waluty.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TransEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String base;
  String target;
  String amountExchanged;
  String date;

  public TransEntity() {
  }


  public TransEntity(String base, String target, String amountExchanged, String date) {
    this.base = base;
    this.target = target;
    this.amountExchanged = amountExchanged;
    this.date = date;
  }


  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getBase() {
    return base;
  }
  public void setBase(String base) {
    this.base = base;
  }
  public String getTarget() {
    return target;
  }
  public void setTarget(String target) {
    this.target = target;
  }
  public String getAmountExchanged() {
    return amountExchanged;
  }
  public void setAmountExchanged(String amountExchanged) {
    this.amountExchanged = amountExchanged;
  }
  public String getDate() {
    return date;
  }
  public void setDate(String date) {
    this.date = date;
  }
}
