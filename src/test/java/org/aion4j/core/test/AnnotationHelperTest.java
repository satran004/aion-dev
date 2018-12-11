package org.aion4j.core.test;

import java.util.List;
import org.aion.solidity.Abi;
import org.aion4j.core.annotation.AnnotationHelper;
import org.aion4j.core.annotation.MethodWrapper;
import org.aion4j.core.api.annotation.Contract;
import org.aion4j.core.test.contracts.ITypesTest;
import org.aion4j.core.util.FileUtil;
import org.junit.Test;

public class AnnotationHelperTest {

    @Test
    public void contractAnnotationExistsTest() {

        Contract contract = AnnotationHelper.getContructorAnnotation(ITypesTest.class);

        assert contract.abi().equals("contracts/TypesTest.abi");
        assert contract.source().equals("contracts/TypesTest.sol");
    }

    @Test
    public void getContractMethodsTest() {

        String abiStr = FileUtil.getFileContent("contracts/TypesTest.abi");

        Abi abi = Abi.fromJSON(abiStr);

        assert abi != null;

        List<MethodWrapper> methodWrapperList = AnnotationHelper.getContractMethods(ITypesTest.class, abi);

        assert methodWrapperList.size() == 3;
        assert methodWrapperList.get(0).getAbiFunction().name.equals("get");
    }

}
