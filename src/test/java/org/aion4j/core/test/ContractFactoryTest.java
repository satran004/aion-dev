package org.aion4j.core.test;

import java.math.BigInteger;
import org.aion4j.core.api.proxy.ContractFactory;
import org.aion4j.core.test.contracts.ITypesTest;
import org.junit.Test;

public class ContractFactoryTest {

    @Test
    public void testGetContractCall() throws InstantiationException, IllegalAccessException {
        ITypesTest iTypesTest = ContractFactory.get(ITypesTest.class);

        iTypesTest.set(new BigInteger(String.valueOf(50000L)), "0xadddd", Boolean.TRUE);

        System.out.println(iTypesTest.getContext().getAbiJson());

        System.out.println(iTypesTest.getContext());

        assert iTypesTest.getContext() != null;
    }

}
