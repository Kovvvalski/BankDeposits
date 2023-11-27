package by.kovalski.bankdeposits.handler;

import by.kovalski.bankdeposits.entity.Deposit;
import by.kovalski.bankdeposits.entity.TimeDeposit;

import java.time.LocalDate;

public enum DepositXmlTag {
  DEPOSITS("deposits") {
    @Override
    public void setField(Deposit deposit, String data) {

    }
  },
  BANK("bank") {
    @Override
    public void setField(Deposit deposit, String data) {

    }
  },
  TYPE("type") {
    @Override
    public void setField(Deposit deposit, String data) {

    }
  },
  TIME_DEPOSIT("time_deposit") {
    @Override
    public void setField(Deposit deposit, String data) {

    }
  },
  DEMAND_DEPOSIT("demand_deposit") {
    @Override
    public void setField(Deposit deposit, String data) {

    }
  },
  COUNTRY("country") {
    @Override
    public void setField(Deposit deposit, String data) {
      deposit.getDepositor().setRegistrationCountry(data);
    }
  },
  DEPOSITOR("depositor") {
    @Override
    public void setField(Deposit deposit, String data) {
      deposit.getDepositor().setName(data);
    }
  },
  ID("id") {
    @Override
    public void setField(Deposit deposit, String data) {
      deposit.getDepositor().setAccountId(data);
    }
  },
  AMOUNT("amount") {
    @Override
    public void setField(Deposit deposit, String data) {
      deposit.setAmount(Integer.parseInt(data));
    }
  },
  PROFITABILITY("profitability") {
    @Override
    public void setField(Deposit deposit, String data) {
      deposit.setProfitability(Integer.parseInt(data));
    }
  },
  TIME("time") {
    @Override
    public void setField(Deposit deposit, String data) {
      TimeDeposit temp = (TimeDeposit) deposit;
      String[] parsed = data.split("-");
      temp.setTime(LocalDate.of(Integer.parseInt(parsed[0]), Integer.parseInt(parsed[1]), Integer.parseInt(parsed[2])));
    }
  };

  private String value;

  DepositXmlTag(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public abstract void setField(Deposit deposit, String data);

}
