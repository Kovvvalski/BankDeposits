package by.kovalski.bankdeposits.entity;

import by.kovalski.bankdeposits.adapter.LocalDateAdapter;
import by.kovalski.bankdeposits.adapter.TypeAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;
@XmlRootElement(name = "time_deposit")
@XmlType(name = "TimeDeposit", propOrder = {"time"})
@XmlAccessorType(XmlAccessType.FIELD)
public class TimeDeposit extends Deposit {
  @XmlJavaTypeAdapter(LocalDateAdapter.class)
  @XmlElement(name = "time")
  private LocalDate time;
  @XmlAttribute(required = true)
  @XmlJavaTypeAdapter(TypeAdapter.class)
  private Type type;

  public TimeDeposit(Depositor depositor, String bankName, int amount, int profitability, Type type, LocalDate time) {
    super(depositor, bankName, amount, profitability);
    this.time = time;
    this.type = type;
  }

  public TimeDeposit() {
    super();
  }

  public LocalDate getTime() {
    return time;
  }

  public void setTime(LocalDate time) {
    this.time = time;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TimeDeposit that = (TimeDeposit) o;
    return super.equals(that) && time.equals(that.time) && type.equals(that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), time, type);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", TimeDeposit.class.getSimpleName() + "[", "]")
            .add("time=" + time)
            .add("depositor=" + super.getDepositor())
            .add("bankName='" + super.getBankName() + "'")
            .add("amount=" + super.getAmount())
            .add("profitability=" + super.getProfitability())
            .add("type=" + type.name())
            .toString();
  }
}
