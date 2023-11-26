package by.kovalski.bankdeposits.main;

import by.kovalski.bankdeposits.builder.DepositSaxBuilder;
import by.kovalski.bankdeposits.entity.DemandDeposit;
import by.kovalski.bankdeposits.entity.Deposit;
import by.kovalski.bankdeposits.entity.TimeDeposit;
import by.kovalski.bankdeposits.entity.Type;
import by.kovalski.bankdeposits.exception.CustomException;
import by.kovalski.bankdeposits.handler.DepositXmlTag;
import by.kovalski.bankdeposits.handler.DepositsErrorHandler;
import by.kovalski.bankdeposits.valid.XmlValidator;
import by.kovalski.bankdeposits.valid.impl.XmlValidatorImpl;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntPredicate;

public class Main {
  private static final String XSD = "C:/Users/Acer/Desktop/ITA/projects/BankDeposits/src/main/resources/deposits.xsd";
  private static final String XML = "C:/Users/Acer/Desktop/ITA/projects/BankDeposits/src/main/resources/deposits.xml";

  public static void main(String[] args) throws CustomException {
//    XmlValidator i = XmlValidatorImpl.getInstance();
//    boolean res = i.validateXml(XML, XSD);
//    System.out.println(res);
    DepositSaxBuilder builder = new DepositSaxBuilder();
    builder.buildListOfDeposits(XML);
    builder.clearDeposits();
    builder.buildListOfDeposits(XML);
    List<Deposit> deposits = builder.getDeposits();

    for(Deposit deposit : deposits)
      System.out.println(deposit);
  }
}