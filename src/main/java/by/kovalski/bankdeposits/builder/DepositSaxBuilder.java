package by.kovalski.bankdeposits.builder;

import by.kovalski.bankdeposits.entity.Deposit;
import by.kovalski.bankdeposits.exception.CustomException;
import by.kovalski.bankdeposits.handler.DepositHandler;
import by.kovalski.bankdeposits.handler.DepositsErrorHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class DepositSaxBuilder {
  private static final Logger logger = LogManager.getLogger();
  private List<Deposit> deposits;
  private final DepositHandler handler = new DepositHandler();
  private final DepositsErrorHandler errorHandler = new DepositsErrorHandler();
  private XMLReader reader;

  public DepositSaxBuilder() throws CustomException {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    try {
      SAXParser parser = factory.newSAXParser();
      reader = parser.getXMLReader();
    } catch (ParserConfigurationException | SAXException e) {
      logger.error("Error during creating reader/parser", e);
      throw new CustomException("Error during creating reader/parser", e);
    }
    reader.setContentHandler(handler);
    reader.setErrorHandler(errorHandler);
  }

  public List<Deposit> getDeposits() {
    return deposits;
  }

  public void clearDeposits(){
    deposits.clear();
  }

  public void buildListOfDeposits(String fileName) throws CustomException {
    try {
      reader.parse(fileName);
    } catch (IOException | SAXException e) {
      logger.error("Error during opening/parsing file " + fileName, e);
      throw new CustomException("Error during opening/parsing file " + fileName, e);
    }
    deposits = handler.getDeposits();
  }
}
