//package by.kovalski.bankdeposits.valid;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.xml.sax.SAXException;
//
//import javax.xml.XMLConstants;
//import javax.xml.transform.stream.StreamSource;
//import javax.xml.validation.Schema;
//import javax.xml.validation.SchemaFactory;
//import javax.xml.validation.Validator;
//import java.io.File;
//import java.io.IOException;
//
//public class XmlValidator {
//  private static final Logger logger = LogManager.getLogger();
//
//  public static boolean validateXml(String xmlFile, String xsdFile) {
//    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//    try {
//      Schema schema = factory.newSchema(new File(xsdFile));
//      Validator validator = schema.newValidator();
//      validator.setErrorHandler(new DepositsErrorHandler());
//      validator.validate(new StreamSource(xmlFile));
//    } catch (SAXException | IOException e) {
//      logger.error("XML is not valid", e);
//      return false;
//    }
//    return true;
//  }
//}


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
  private static XmlValidatorImpl Instance;

  private XmlValidatorImpl() {

  }

  public static XmlValidatorImpl getInstance() {
    if (Instance == null)
      Instance = new XmlValidatorImpl();
    return Instance;
  }

  @Override
  public boolean validateXml(String xmlFile, String xsdFile) {
    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    try {
      Schema schema = factory.newSchema(new File(xsdFile));
      Validator validator = schema.newValidator();
      validator.setErrorHandler(new DepositsErrorHandler());
      validator.validate(new StreamSource(xmlFile));
    } catch (SAXException | IOException e) {
      logger.error("XML is not valid", e);
      return false;
    }
    return true;
  }
}