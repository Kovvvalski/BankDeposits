package by.kovalski.bankdeposits.builder.impl;

import by.kovalski.bankdeposits.builder.DepositListBuilder;
import by.kovalski.bankdeposits.entity.DemandDeposit;
import by.kovalski.bankdeposits.entity.Deposit;
import by.kovalski.bankdeposits.entity.TimeDeposit;
import by.kovalski.bankdeposits.entity.Type;
import by.kovalski.bankdeposits.exception.CustomException;
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

  private Deposit buildDemandDeposit(Element element) {
    Deposit deposit = new DemandDeposit();
    deposit.setBankName(element.getAttribute("bank"));
    deposit.setProfitability(Integer.parseInt(element.getElementsByTagName("profitability").item(0).getTextContent()));
    deposit.setAmount(Integer.parseInt(element.getElementsByTagName("amount").item(0).getTextContent()));
    deposit.getDepositor().setName(element.getElementsByTagName("depositor").item(0).getTextContent());
    deposit.getDepositor().setRegistrationCountry(element.getElementsByTagName("country").item(0).getTextContent());
    deposit.getDepositor().setAccountId(element.getElementsByTagName("id").item(0).getTextContent());
    return deposit;
  }

  private Deposit buildTimeDeposit(Element element) {
    Deposit deposit = new TimeDeposit();
    TimeDeposit temp = (TimeDeposit) deposit;
    temp.setBankName(element.getAttribute("bank"));
    temp.setType(Type.valueOf(element.getAttribute("type").toUpperCase()));
    temp.setProfitability(Integer.parseInt(element.getElementsByTagName("profitability").item(0).getTextContent()));
    temp.setAmount(Integer.parseInt(element.getElementsByTagName("amount").item(0).getTextContent()));
    temp.getDepositor().setName(element.getElementsByTagName("depositor").item(0).getTextContent());
    temp.getDepositor().setRegistrationCountry(element.getElementsByTagName("country").item(0).getTextContent());
    temp.getDepositor().setAccountId(element.getElementsByTagName("id").item(0).getTextContent());
    String time = element.getElementsByTagName("time").item(0).getTextContent();
    String[] ymd = time.split("-");
    temp.setTime(LocalDate.of(Integer.parseInt(ymd[0]), Integer.parseInt(ymd[1]), Integer.parseInt(ymd[2])));
    return deposit;
  }

  @Override
  public List<Deposit> buildListOfDeposits(String fileName) throws CustomException {
    Document document;
    List<Deposit> out = new ArrayList<>();
    try {
      document = docBuilder.parse(fileName);
      Element root = document.getDocumentElement();
      NodeList deposits = root.getElementsByTagName("demand_deposit");
      for (int i = 0; i < deposits.getLength(); i++) {
        Element element = (Element) deposits.item(i);
        Deposit deposit = buildDemandDeposit(element);
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
