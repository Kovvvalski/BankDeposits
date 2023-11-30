package by.kovalski.bankdeposits.builder.impl;

import by.kovalski.bankdeposits.builder.DepositListBuilder;
import by.kovalski.bankdeposits.entity.Deposit;
import by.kovalski.bankdeposits.exception.CustomException;

import java.util.List;

public class DepositStaxEventBuilder implements DepositListBuilder
{
  @Override
  public List<Deposit> buildListOfDeposits(String fileName) throws CustomException {
    return null;
  }
}
