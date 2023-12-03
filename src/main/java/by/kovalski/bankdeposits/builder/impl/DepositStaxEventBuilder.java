package by.kovalski.bankdeposits.builder.impl;

import by.kovalski.bankdeposits.builder.DepositListBuilder;
import by.kovalski.bankdeposits.entity.Deposit;
import by.kovalski.bankdeposits.entity.TimeDeposit;
import by.kovalski.bankdeposits.entity.Type;
import by.kovalski.bankdeposits.exception.CustomException;
import by.kovalski.bankdeposits.handler.DepositXmlTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DepositStaxEventBuilder implements DepositListBuilder {
  private static final Logger logger = LogManager.getLogger();

  @Override
  public List<Deposit> buildListOfDeposits(String fileName) throws CustomException {
    List<Deposit> out = new ArrayList<>();

    Deposit deposit = null;
    XMLInputFactory inputFactory = XMLInputFactory.newInstance();
    try {
      XMLEventReader reader = inputFactory.createXMLEventReader(new FileInputStream(fileName));
      while (reader.hasNext()) {
        XMLEvent event = reader.nextEvent();
        if (event.isStartElement()) {
          StartElement startElement = event.asStartElement();
          if (startElement.getName().getLocalPart().equals(DepositXmlTag.DEPOSIT.getValue())) {
            deposit = new Deposit();
            Attribute bankName = startElement.getAttributeByName(new QName(DepositXmlTag.BANK.getValue()));
            deposit.setBankName(bankName.getValue());
          } else if (startElement.getName().getLocalPart().equals(DepositXmlTag.TIME_DEPOSIT.getValue())) {
            deposit = new TimeDeposit();
            Attribute attribute = startElement.getAttributeByName(new QName(DepositXmlTag.BANK.getValue()));
            deposit.setBankName(attribute.getValue());
            attribute = startElement.getAttributeByName(new QName(DepositXmlTag.TYPE.getValue()));
            ((TimeDeposit) deposit).setType(Type.valueOf(attribute.getValue().toUpperCase()));
          } else if (startElement.getName().getLocalPart().equals(DepositXmlTag.COUNTRY.getValue())) {
            event = reader.nextEvent();
            deposit.getDepositor().setRegistrationCountry(event.asCharacters().getData());
          } else if (startElement.getName().getLocalPart().equals(DepositXmlTag.DEPOSITOR.getValue())) {
            event = reader.nextEvent();
            deposit.getDepositor().setName(event.asCharacters().getData());
          } else if (startElement.getName().getLocalPart().equals(DepositXmlTag.ID.getValue())) {
            event = reader.nextEvent();
            deposit.getDepositor().setAccountId(event.asCharacters().getData());
          } else if (startElement.getName().getLocalPart().equals(DepositXmlTag.AMOUNT.getValue())) {
            event = reader.nextEvent();
            deposit.setAmount(Integer.parseInt(event.asCharacters().getData()));
          } else if (startElement.getName().getLocalPart().equals(DepositXmlTag.PROFITABILITY.getValue())) {
            event = reader.nextEvent();
            deposit.setProfitability(Integer.parseInt(event.asCharacters().getData()));
          } else if (startElement.getName().getLocalPart().equals(DepositXmlTag.TIME.getValue())) {
            event = reader.nextEvent();
            String[] yearMonthDay = event.asCharacters().getData().split("-");
            ((TimeDeposit) deposit).setTime(LocalDate.of(Integer.parseInt(yearMonthDay[0]), Integer.parseInt(yearMonthDay[1]), Integer.parseInt(yearMonthDay[2])));
          }
        }
        if(event.isEndElement()){
          EndElement endElement = event.asEndElement();
          String name = endElement.getName().getLocalPart();
          if(name.equals(DepositXmlTag.TIME_DEPOSIT.getValue())||name.equals(DepositXmlTag.DEPOSIT.getValue())){
            out.add(deposit);
          }
        }
      }
    } catch (FileNotFoundException | XMLStreamException e) {
      logger.error("File is not found or can not be parsed", e);
      throw new CustomException("File is not found or can not be parsed", e);
    }
    return out;
  }
}
