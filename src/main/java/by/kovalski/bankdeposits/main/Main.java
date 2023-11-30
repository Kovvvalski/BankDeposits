package by.kovalski.bankdeposits.main;

import by.kovalski.bankdeposits.builder.DepositListBuilder;
import by.kovalski.bankdeposits.builder.impl.DepositDomElementBuilder;
import by.kovalski.bankdeposits.builder.impl.DepositSaxBuilder;
import by.kovalski.bankdeposits.builder.impl.DepositStaxBuilder;
import by.kovalski.bankdeposits.entity.Deposit;
import by.kovalski.bankdeposits.entity.TimeDeposit;
import by.kovalski.bankdeposits.exception.CustomException;


import java.util.List;

public class Main {
  private static final String XSD = "C:/Users/Acer/Desktop/ITA/projects/BankDeposits/src/main/resources/deposits.xsd";
  private static final String XML = "C:/Users/Acer/Desktop/ITA/projects/BankDeposits/src/main/resources/deposits.xml";

  public static void main(String[] args) throws CustomException {
    DepositListBuilder builder = new DepositSaxBuilder();
    List<Deposit> deposits = builder.buildListOfDeposits(XML);
    for (Deposit deposit : deposits)
      System.out.println(deposit);
    System.out.println();
    System.out.println();
    System.out.println();
    builder = new DepositStaxBuilder();
    deposits = builder.buildListOfDeposits(XML);
    for (Deposit deposit : deposits)
      System.out.println(deposit);
    System.out.println();
    System.out.println();
    System.out.println();
    builder = new DepositDomElementBuilder();
    deposits = builder.buildListOfDeposits(XML);
    for (Deposit deposit : deposits)
      System.out.println(deposit);


  }
}