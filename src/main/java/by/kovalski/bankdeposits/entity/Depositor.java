package by.kovalski.bankdeposits.entity;

public class Depositor {
  private String name;
  private String registrationCountry;
  private String accountId;

  public Depositor(String name, String registrationCountry, String accountId) {
    this.name = name;
    this.registrationCountry = registrationCountry;
    this.accountId = accountId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRegistrationCountry() {
    return registrationCountry;
  }

  public void setRegistrationCountry(String registrationCountry) {
    this.registrationCountry = registrationCountry;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }
}
