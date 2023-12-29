package by.kovalski.bankdeposits.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

@XmlType(name = "Deposit", propOrder = {"depositor", "amount", "profitability"})
@XmlRootElement(name = "deposit")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(TimeDeposit.class)
public class Deposit implements Serializable {
  @XmlAttribute(name = "bank", required = true)
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  private String bankName;
  @XmlElement(required = true)
  private int amount;
  @XmlElement(required = true)
  private Depositor depositor = new Depositor();
  @XmlElement(required = true)
  private int profitability;


  public Deposit() {

  }

  public Deposit(Depositor depositor, String bankName, int amount, int profitability) {
    this.depositor = depositor;
    this.bankName = bankName;
    this.amount = amount;
    this.profitability = profitability;
  }


  public Depositor getDepositor() {
    return depositor;
  }

  public void setDepositor(Depositor depositor) {
    this.depositor = depositor;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public int getProfitability() {
    return profitability;
  }

  public void setProfitability(int profitability) {
    this.profitability = profitability;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Deposit deposit = (Deposit) o;
    return amount == deposit.amount && profitability == deposit.profitability && depositor.equals(deposit.depositor) && Objects.equals(bankName, deposit.bankName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(depositor, bankName, amount, profitability);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Deposit.class.getSimpleName() + "[", "]")
            .add("depositor=" + depositor)
            .add("bankName='" + bankName + "'")
            .add("amount=" + amount)
            .add("profitability=" + profitability)
            .toString();
  }

}
