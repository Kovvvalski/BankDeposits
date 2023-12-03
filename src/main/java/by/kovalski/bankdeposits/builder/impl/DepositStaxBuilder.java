package by.kovalski.bankdeposits.builder.impl;

import by.kovalski.bankdeposits.builder.DepositListBuilder;
import by.kovalski.bankdeposits.entity.Deposit;
import by.kovalski.bankdeposits.entity.TimeDeposit;
import by.kovalski.bankdeposits.entity.Type;
import by.kovalski.bankdeposits.exception.CustomException;
import by.kovalski.bankdeposits.handler.DepositXmlTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DepositStaxBuilder implements DepositListBuilder {
  private static final Logger logger = LogManager.getLogger();

  @Override
  public List<Deposit> buildListOfDeposits(String fileName) throws CustomException {
    XMLStreamReader reader;
    String name;
    List<Deposit> out = new ArrayList<>();
    try (FileInputStream inputStream = new FileInputStream(new File(fileName))) {
      reader = XMLInputFactory.newInstance().createXMLStreamReader(inputStream);
      while (reader.hasNext()) {
        int type = reader.next();
        if (type == XMLStreamConstants.START_ELEMENT) {
          name = reader.getLocalName();
          if (name.equals(DepositXmlTag.DEPOSIT.getValue())) {
            out.add(buildDeposit(reader));
          } else if (name.equals(DepositXmlTag.TIME_DEPOSIT.getValue())) {
            out.add(buildTimeDeposit(reader));
          }
        }
      }

    } catch (XMLStreamException | FileNotFoundException e) {
      logger.error("Error during parsing or opening the file", e);
      throw new CustomException("Error during parsing or opening the file", e);
    } catch (IOException e) {
      logger.error("Error during opening the file", e);
      throw new CustomException("Error during opening the file", e);
    }
    return out;
  }

  private Deposit buildDeposit(XMLStreamReader reader) throws XMLStreamException {
    Deposit deposit = new Deposit();
    deposit.setBankName(reader.getAttributeValue(null, DepositXmlTag.BANK.getValue()));
    String name;
    while (reader.hasNext()) {
      int type = reader.next();
      switch (type) {
        case XMLStreamConstants.START_ELEMENT -> {
          name = reader.getLocalName();
          switch (DepositXmlTag.valueOf(name.toUpperCase())) {
            case COUNTRY -> deposit.getDepositor().setRegistrationCountry(getXmlText(reader));
            case DEPOSITOR -> deposit.getDepositor().setName(getXmlText(reader));
            case ID -> deposit.getDepositor().setAccountId(getXmlText(reader));
            case AMOUNT -> deposit.setAmount(Integer.parseInt(getXmlText(reader)));
            case PROFITABILITY -> deposit.setProfitability(Integer.parseInt(getXmlText(reader)));
          }
        }
        case XMLStreamConstants.END_ELEMENT -> {
          name = reader.getLocalName();
          if (DepositXmlTag.valueOf(name.toUpperCase()) == DepositXmlTag.DEPOSIT)
            return deposit;
        }
      }
    }
    throw new XMLStreamException("Unknown name of tag");
  }

  private Deposit buildTimeDeposit(XMLStreamReader reader) throws XMLStreamException {
    Deposit deposit = new TimeDeposit();
    deposit.setBankName(reader.getAttributeValue(null, DepositXmlTag.BANK.getValue()));
    ((TimeDeposit) deposit).setType(Type.valueOf(reader.getAttributeValue(null, DepositXmlTag.TYPE.getValue()).toUpperCase()));

    String name;
    while (reader.hasNext()) {
      int type = reader.next();
      switch (type) {
        case XMLStreamConstants.START_ELEMENT -> {
          name = reader.getLocalName();
          switch (DepositXmlTag.valueOf(name.toUpperCase())) {
            case COUNTRY -> deposit.getDepositor().setRegistrationCountry(getXmlText(reader));
            case DEPOSITOR -> deposit.getDepositor().setName(getXmlText(reader));
            case ID -> deposit.getDepositor().setAccountId(getXmlText(reader));
            case AMOUNT -> deposit.setAmount(Integer.parseInt(getXmlText(reader)));
            case PROFITABILITY -> deposit.setProfitability(Integer.parseInt(getXmlText(reader)));
            case TIME -> {
              String[] date = getXmlText(reader).split("-");
              ((TimeDeposit) deposit).setTime(LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])));
            }
          }
        }
        case XMLStreamConstants.END_ELEMENT -> {
          name = reader.getLocalName();
          if (DepositXmlTag.valueOf(name.toUpperCase()) == DepositXmlTag.TIME_DEPOSIT)
            return deposit;
        }
      }
    }
    throw new XMLStreamException("Unknown name of tag");
  }

  private String getXmlText(XMLStreamReader reader) throws XMLStreamException {
    String text = null;
    if (reader.hasNext()) {
      reader.next();
      text = reader.getText();
    }
    return text;
  }
}


//  private Deposit buildTimeDeposit(XMLStreamReader reader) throws XMLStreamException {
//    Deposit deposit = new TimeDeposit();
//    deposit.setBankName(reader.getAttributeValue(null, DepositXmlTag.BANK.getValue()));
//    ((TimeDeposit) deposit).setType(Type.valueOf(reader.getAttributeValue(null, DepositXmlTag.TYPE.getValue()).toUpperCase()));
//    int type = 1;
//    String name;
//    do{
//      if (type == XMLStreamConstants.START_ELEMENT) {
//        name = reader.getLocalName();
//          switch (DepositXmlTag.valueOf(name.toUpperCase())) {
//            case COUNTRY -> deposit.getDepositor().setRegistrationCountry(reader.getText());
//            case DEPOSITOR -> deposit.getDepositor().setName(reader.getText());
//            case ID -> deposit.getDepositor().setAccountId(reader.getText());
//            case AMOUNT -> deposit.setAmount(Integer.parseInt(reader.getText()));
//            case PROFITABILITY -> deposit.setProfitability(Integer.parseInt(reader.getText()));
//            case TIME -> {
//              String[] date = getXmlText(reader).split("-");
//              ((TimeDeposit) deposit).setTime(LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])));
//            }
//          }
//      }
//      type = reader.next();
//    } while (type!=XMLStreamConstants.END_ELEMENT && type != XMLStreamConstants.CHARACTERS && DepositXmlTag.valueOf(reader.getLocalName().toUpperCase())!=DepositXmlTag.TIME_DEPOSIT);
//      return deposit;
//  }



