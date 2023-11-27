package by.kovalski.bankdeposits.builder;

import by.kovalski.bankdeposits.entity.Deposit;
import by.kovalski.bankdeposits.exception.CustomException;

import java.util.List;
@FunctionalInterface
public interface DepositListBuilder {
  List<Deposit> buildListOfDeposits(String fileName) throws CustomException;
}
