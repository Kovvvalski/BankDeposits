package by.kovalski.bankdeposits.handler;

public enum DepositXmlTag {
  DEPOSITS("deposits"),
  BANK("bank"),
  TYPE("type"),
  TIME_DEPOSIT("time_deposit"),
  DEMAND_DEPOSIT("demand_deposit"),
  COUNTRY("country"),
  DEPOSITOR("depositor"),
  ID("id"),
  AMOUNT("amount"),
  PROFITABILITY("profitability"),
  TIME("time");

  private String value;

  DepositXmlTag(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

}
