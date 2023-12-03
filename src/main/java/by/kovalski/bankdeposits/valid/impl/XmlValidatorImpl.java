package by.kovalski.bankdeposits.valid.impl;

import by.kovalski.bankdeposits.handler.DepositsErrorHandler;
import by.kovalski.bankdeposits.valid.XmlValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XmlValidatorImpl implements XmlValidator {
  private static final Logger logger = LogManager.getLogger();
  private static XmlValidatorImpl instance;

  private XmlValidatorImpl() {

  }

  public static XmlValidatorImpl getInstance() {
    if (instance == null)
      instance = new XmlValidatorImpl();
    return instance;
  }

  @Override
  public boolean validateXml(String xmlFile, String xsdFile) {
    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    boolean out = true;
    try {
      Schema schema = factory.newSchema(new File(xsdFile));
      Validator validator = schema.newValidator();
      validator.setErrorHandler(new DepositsErrorHandler());
      validator.validate(new StreamSource(xmlFile));
    } catch (SAXException | IOException e) {
      logger.error("XML is not valid", e);
      out = false;
    }
    return out;
  }
}