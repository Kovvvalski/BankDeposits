package by.kovalski.bankdeposits.main;

import by.kovalski.bankdeposits.adapter.LocalDateAdapter;
import by.kovalski.bankdeposits.adapter.TypeAdapter;
import by.kovalski.bankdeposits.builder.DepositListBuilder;
import by.kovalski.bankdeposits.builder.impl.DepositDomElementBuilder;
import by.kovalski.bankdeposits.builder.impl.DepositSaxBuilder;
import by.kovalski.bankdeposits.builder.impl.DepositStaxBuilder;
import by.kovalski.bankdeposits.builder.impl.DepositStaxEventBuilder;
import by.kovalski.bankdeposits.entity.*;
import by.kovalski.bankdeposits.exception.CustomException;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
  private static final String XSD = "C:/Users/Acer/Desktop/ITA/projects/BankDeposits/src/main/resources/deposits.xsd";
  private static final String XML = "C:/Users/Acer/Desktop/ITA/projects/BankDeposits/src/main/resources/deposits.xml";

  public static void main(String[] args)throws Exception{
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


    System.out.println();
    System.out.println();
    System.out.println();
    builder = new DepositStaxEventBuilder();
    deposits = builder.buildListOfDeposits(XML);
    for (Deposit deposit : deposits)
      System.out.println(deposit);


    System.out.println();
    System.out.println();
    System.out.println();

    try {
      Deposits deposits1 = new Deposits();
      deposits1.setDeposits(deposits);
      Deposits deposits2 = new Deposits();
      JAXBContext context = JAXBContext.newInstance(Deposit.class, Depositor.class, LocalDateAdapter.class, TypeAdapter.class, Deposits.class);
      Marshaller m = context.createMarshaller();
      m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      m.marshal(deposits1,new FileOutputStream("src/main/resources/jaxb_data.xml"));
      Unmarshaller u = context.createUnmarshaller();
      deposits2 = (Deposits) u.unmarshal(new FileInputStream("src/main/resources/jaxb_data.xml"));
      deposits = deposits2.getDeposits();
      for (Deposit deposit : deposits)
        System.out.println(deposit);
    }catch (JAXBException e){
      e.printStackTrace();
    }
  }
}