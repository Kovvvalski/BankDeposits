package by.kovalski.bankdeposits.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class DepositsErrorHandler extends DefaultHandler {

  private static final Logger logger = LogManager.getLogger();

  public void warning(SAXParseException e) {
    logger.warn(getLineAddress(e) + "-" + e.getMessage());
  }

  public void error(SAXParseException e) {
    logger.error(getLineAddress(e) + " - " + e.getMessage());
  }

  public void fatalError(SAXParseException e) {
    logger.fatal(getLineAddress(e) + " - " + e.getMessage());
  }

  private String getLineAddress(SAXParseException e) {


    return e.getLineNumber() + " : " + e.getColumnNumber();
  }

}