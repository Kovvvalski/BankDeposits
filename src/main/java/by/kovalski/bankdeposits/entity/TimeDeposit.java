package by.kovalski.bankdeposits.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;

public class TimeDeposit extends Deposit {
  private LocalDate time;
  private Type type;

  public TimeDeposit(Depositor depositor, String bankName, int amount, int profitability, Type type, LocalDate time) {
    super(depositor, bankName, amount, profitability);
    this.time = time;
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
