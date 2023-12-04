package by.kovalski.bankdeposits.builder.impl;

import by.kovalski.bankdeposits.adapter.LocalDateAdapter;
import by.kovalski.bankdeposits.adapter.TypeAdapter;
import by.kovalski.bankdeposits.builder.DepositListBuilder;
import by.kovalski.bankdeposits.entity.Deposit;
import by.kovalski.bankdeposits.entity.Depositor;
import by.kovalski.bankdeposits.entity.Deposits;
import by.kovalski.bankdeposits.exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class DepositJaxbBuilder implements DepositListBuilder {
  private static final Logger logger = LogManager.getLogger();

  @Override
  public List<Deposit> buildListOfDeposits(String fileName) throws CustomException {
    List<Deposit> out = null;
    try {
      JAXBContext context = JAXBContext.newInstance(Deposit.class, Depositor.class, LocalDateAdapter.class, TypeAdapter.class, Deposits.class);
      Unmarshaller u = context.createUnmarshaller();
      Deposits deposits = (Deposits) u.unmarshal(new FileInputStream(fileName));
      out = deposits.getDeposits();
    } catch (JAXBException | FileNotFoundException e) {
      logger.error("Cannot find file or unmarshall file", e);
      throw new CustomException("Cannot find file or unmarshall file", e);
    }
    return out;
  }
}
