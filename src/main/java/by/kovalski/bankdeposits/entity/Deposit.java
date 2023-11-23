package by.kovalski.bankdeposits.entity;

import java.time.LocalDate;

public class Deposit {
  private String id;
  private Depositor depositor;
  private String bankName;
  private int amount;
  private int profitability;
  private LocalDate time;
  private Type type;

  public Deposit(String id, Depositor depositor, String bankName, int amount, int profitability, LocalDate time, Type type) {
    this.id = id;
    this.depositor = depositor;
    this.bankName = bankName;
    this.amount = amount;
    this.profitability = profitability;
    this.time = time;
    this.type = type;
  }
}
