package org.aion4j.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.aion.solidity.Abi;
import org.aion.solidity.Abi.Function;
import org.aion4j.core.abi.AbiUtil;
import org.aion4j.core.abi.ContractFunction;
import org.aion4j.core.api.annotation.Contract;
import org.aion4j.core.api.annotation.ContractMethod;
import org.aion4j.core.exception.A4jAnnotationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationHelper {

    private final static Logger logger = LoggerFactory.getLogger(AnnotationHelper.class);

    public static Contract getContructorAnnotation(Class tClass) {

        Annotation contractAnn = tClass.getAnnotation(Contract.class);

        if (contractAnn == null) {
            return null;
        }

        Contract contract = (Contract) contractAnn;

        if(logger.isDebugEnabled()) {
            logger.debug("Abi {}", contract.abi());
           // logger.debug("Address {}", contract.address());
            logger.debug("Source {}", contract.source());
        }
        return contract;

    }

    public static List<MethodWrapper> getContractMethods(Class tClass, Abi abi) {

        Method[] methods = tClass.getMethods();

        if (methods == null) {
            return null;
        }

        List<MethodWrapper> contractMethods = new ArrayList<>();

        for (Method method : methods) {
            ContractMethod contractMethod = (ContractMethod) method.getAnnotation(ContractMethod.class);

            if(contractMethod == null) continue;
            else {

                String abiName = contractMethod.name();
                String[] paramTypes = contractMethod.params();

                Function function = abi.findFunction(f -> AbiUtil.isSameFunction(f, abiName, paramTypes));

                if(function == null) {
                    logger.warn("Unable to find function in the abi definition for method {} ", method.getName());
                } else {
                    MethodWrapper methodWrapper = new MethodWrapper();
                    methodWrapper.setMethod(method);
                    methodWrapper.setAbiFunction(function);

                    contractMethods.add(methodWrapper);
                }
            }
        }

        return contractMethods;
    }
}
