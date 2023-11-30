package by.kovalski.bankdeposits.builder.impl;

import by.kovalski.bankdeposits.builder.DepositListBuilder;
import by.kovalski.bankdeposits.entity.Deposit;
import by.kovalski.bankdeposits.entity.Depositor;
import by.kovalski.bankdeposits.entity.TimeDeposit;
import by.kovalski.bankdeposits.entity.Type;
import by.kovalski.bankdeposits.exception.CustomException;
import by.kovalski.bankdeposits.handler.DepositXmlTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.Proxy;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DepositDomElementBuilder implements DepositListBuilder {
  private final static Logger logger = LogManager.getLogger();
  private DocumentBuilder docBuilder;

  public DepositDomElementBuilder() throws CustomException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try {
      docBuilder = factory.newDocumentBuilder();

    } catch (ParserConfigurationException e) {
      logger.error("Error during creating the document builder", e);
      throw new CustomException("Error during creating the document builder", e);
    }
  }

  private Deposit buildDeposit(Element element) {
    Deposit deposit = new Deposit();
    deposit.setBankName(element.getAttribute(DepositXmlTag.BANK.getValue()));
    deposit.setProfitability(Integer.parseInt(element.getElementsByTagName(DepositXmlTag.PROFITABILITY.getValue()).item(0).getTextContent()));
    deposit.setAmount(Integer.parseInt(element.getElementsByTagName(DepositXmlTag.AMOUNT.getValue()).item(0).getTextContent()));
    deposit.getDepositor().setName(element.getElementsByTagName(DepositXmlTag.DEPOSITOR.getValue()).item(0).getTextContent());
    deposit.getDepositor().setRegistrationCountry(element.getElementsByTagName(DepositXmlTag.COUNTRY.getValue()).item(0).getTextContent());
    deposit.getDepositor().setAccountId(element.getElementsByTagName(DepositXmlTag.ID.getValue()).item(0).getTextContent());
    return deposit;
  }

  private Deposit buildTimeDeposit(Element element) {
    Deposit deposit = new TimeDeposit();
    TimeDeposit temp = (TimeDeposit) deposit;
    deposit.setBankName(element.getAttribute(DepositXmlTag.BANK.getValue()));
    deposit.setProfitability(Integer.parseInt(element.getElementsByTagName(DepositXmlTag.PROFITABILITY.getValue()).item(0).getTextContent()));
    deposit.setAmount(Integer.parseInt(element.getElementsByTagName(DepositXmlTag.AMOUNT.getValue()).item(0).getTextContent()));
    deposit.getDepositor().setName(element.getElementsByTagName(DepositXmlTag.DEPOSITOR.getValue()).item(0).getTextContent());
    deposit.getDepositor().setRegistrationCountry(element.getElementsByTagName(DepositXmlTag.COUNTRY.getValue()).item(0).getTextContent());
    deposit.getDepositor().setAccountId(element.getElementsByTagName(DepositXmlTag.ID.getValue()).item(0).getTextContent());
    ((TimeDeposit) deposit).setType(Type.valueOf(element.getAttribute(DepositXmlTag.TYPE.getValue()).toUpperCase()));
    String time = element.getElementsByTagName(DepositXmlTag.TIME.getValue()).item(0).getTextContent();
    String[] yearMonthDay = time.split("-");
    temp.setTime(LocalDate.of(Integer.parseInt(yearMonthDay[0]), Integer.parseInt(yearMonthDay[1]), Integer.parseInt(yearMonthDay[2])));
    return deposit;
  }

  @Override
  public List<Deposit> buildListOfDeposits(String fileName) throws CustomException {
    Document document;
    List<Deposit> out = new ArrayList<>();
    try {
      document = docBuilder.parse(fileName);
      Element root = document.getDocumentElement();
      NodeList deposits = root.getElementsByTagName("deposit");
      for (int i = 0; i < deposits.getLength(); i++) {
        Element element = (Element) deposits.item(i);
        Deposit deposit = buildDeposit(element);
        out.add(deposit);
      }
      deposits = root.getElementsByTagName("time_deposit");
      for (int i = 0; i < deposits.getLength(); i++) {
        Element element = (Element) deposits.item(i);
        Deposit deposit = buildTimeDeposit(element);
        out.add(deposit);
      }
    } catch (IOException | SAXException e) {
      logger.error("Error during opening or parsing file " + fileName, e);
      throw new CustomException("Error during opening or parsing file " + fileName, e);
    }
    return out;
  }
}
