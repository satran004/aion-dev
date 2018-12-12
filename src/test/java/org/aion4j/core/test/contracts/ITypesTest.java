package org.aion4j.core.test.contracts;

import groovy.lang.Tuple3;
import java.math.BigInteger;
import java.util.List;
import org.aion4j.core.api.BaseContract;
import org.aion4j.core.api.annotation.Contract;
import org.aion4j.core.api.annotation.ContractMethod;

@Contract(name="TypesTest", abi = "contracts/TypesTest.abi",
          source = "contracts/TypesTest.sol")
public interface ITypesTest extends BaseContract {

    @ContractMethod(name = "set", params = {"uint8", "address", "bool"})
    public void set(BigInteger i, String address, Boolean b);

    @ContractMethod(name = "get", params = {})
    public List<?> get();

    @ContractMethod(name = "getAddress", params = {})
    public byte[] getAddress();

    @ContractMethod(name = "getInt", params = {})
    public BigInteger getInt();

    @ContractMethod(name = "getString", params = {})
    public String getString();
}
