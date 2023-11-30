package by.kovalski.bankdeposits.entity;

import java.util.Objects;
import java.util.StringJoiner;

public class Depositor {
  private String accountId;
  private String name;
  private String registrationCountry;

  public Depositor(){

  }

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Depositor depositor = (Depositor) o;
    return name.equals(depositor.name) && accountId.equals(depositor.accountId) && registrationCountry.equals(depositor.registrationCountry);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, name, registrationCountry);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Depositor.class.getSimpleName() + "[", "]")
            .add("accountId='" + accountId + "'")
            .add("name='" + name + "'")
            .add("registrationCountry='" + registrationCountry + "'")
            .toString();
  }
}
