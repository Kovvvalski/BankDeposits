package by.kovalski.bankdeposits.entity;

public abstract class Deposit {
  protected Depositor depositor;
  protected String bankName;
  protected int amount;
  protected int profitability;
  protected Type type;

  public Deposit(){
    depositor = new Depositor();
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

  public Type getType() {
    return this.type;
  }
}
