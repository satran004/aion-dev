package org.aion4j.core.test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import org.aion.base.type.Address;
import org.aion4j.core.api.proxy.ContractFactory;
import org.aion4j.core.test.contracts.ITypesTest;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

public class ContractFactoryTest {

  @Test
  public void testGetContractCall()
      throws InstantiationException, IllegalAccessException, IOException {

    String contractAddress = "a0c8ffc3275734f35f5567552c144bbe4a77e0dba4f75b209ecf9ffe0b2a4646";

    ITypesTest iTypesTest = ContractFactory.get(ITypesTest.class);

    iTypesTest.getContext().compile();
    contractAddress = iTypesTest.getContext().deploy(contractAddress);


    iTypesTest.set(BigInteger.TEN,
        "a01c674112cd58fc29e5d124c1df8a772904776842e35fe57f41814b9e81bb6a", Boolean.TRUE);

    List<?> results = iTypesTest.get();

    System.out.println("results>>>> " + results);

    byte[] addBytes = iTypesTest.getAddress();

    Address address = Address.wrap(addBytes);

    System.out.println("Returned address: " + address);

    BigInteger integer = iTypesTest.getInt();
    System.out.println("Integer: " + integer);


    String str = iTypesTest.getString();
    System.out.println("String: " + str);

    assert iTypesTest.getContext() != null;
  }
}
