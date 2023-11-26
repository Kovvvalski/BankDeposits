package by.kovalski.bankdeposits.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.StringJoiner;

public class TimeDeposit extends Deposit {
  private final static Logger logger = LogManager.getLogger();
  private static final Type DEFAULT_TYPE = Type.ACCUMULATIVE;
  private LocalDate time;

  public TimeDeposit(Depositor depositor, String bankName, int amount, int profitability, Type type, LocalDate time) {
    super(depositor, bankName, amount, profitability);
    this.time = time;
    setType(type);
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

  public void setType(Type type) {
    if (type == Type.DEMAND)
      logger.error("Type DEMAND is not available in this class");
    this.type = (type == Type.DEMAND) ? DEFAULT_TYPE : type;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", TimeDeposit.class.getSimpleName() + "[", "]")
            .add("time=" + time)
            .add("depositor=" + depositor)
            .add("bankName='" + bankName + "'")
            .add("amount=" + amount)
            .add("profitability=" + profitability)
            .add("type=" + type)
            .toString();
  }
}
