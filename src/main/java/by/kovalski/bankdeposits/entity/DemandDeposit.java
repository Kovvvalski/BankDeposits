package by.kovalski.bankdeposits.entity;

import java.time.LocalDate;
import java.util.StringJoiner;

public class DemandDeposit extends Deposit{
  public DemandDeposit(Depositor depositor, String bankName, int amount, int profitability) {
    super(depositor, bankName, amount, profitability);
    this.type = Type.DEMAND;
  }

  public DemandDeposit(){
    super();
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", DemandDeposit.class.getSimpleName() + "[", "]")
            .add("depositor=" + depositor)
            .add("bankName='" + bankName + "'")
            .add("amount=" + amount)
            .add("profitability=" + profitability)
            .add("type=" + type)
            .toString();
  }
}
