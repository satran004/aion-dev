package org.aion4j.core.api;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import org.aion.base.type.Address;
import org.aion.base.util.Hex;
import org.aion.mcf.vm.types.DataWord;
import org.aion.solidity.Abi;
import org.aion.solidity.Abi.Function;
import org.aion.solidity.CompilationResult;
import org.aion.solidity.CompilationResult.Contract;
import org.aion.solidity.Compiler;
import org.aion.solidity.Compiler.Options;
import org.aion.vm.ExecutionContext;
import org.aion.vm.ExecutionResult;
import org.aion4j.core.annotation.AnnotationHelper;
import org.aion4j.core.annotation.MethodWrapper;
import org.aion4j.core.exception.A4jProcessingException;
import org.aion4j.core.util.FileUtil;
import org.aion4j.core.vm.ExecutionContextBuilder;
import org.aion4j.core.vm.fastvm.FastVMContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContractContext {

    private final static Logger logger = LoggerFactory.getLogger(ContractContext.class);

    private String name;
    private String abiJson;
    private String sourceFile;
    private String contractAddress;

    private Abi abi;
    private byte[] code;
    private String deployCode; //deployed code

    private Class contractClazz;
    private List<MethodWrapper> methodWrapperList;

    public ContractContext(Class clazz) {
        this.contractClazz = clazz;
    }

    private VMContext vmContext;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbiJson(String abiJson) {
        this.abiJson = abiJson;
    }

    public String getAbiJson() {
        return abiJson;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public void setContractAddress(String address) {
        this.contractAddress = address;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public Abi getAbi() {
        return abi;
    }

    public void setAbi(Abi abi) {
        this.abi = abi;
    }

    public String getDeployCode() {
        return deployCode;
    }

    public void setDeployCode(String deployCode) {
        this.deployCode = deployCode;
    }

    public byte[] getCode() {
        return code;
    }

    public VMContext initVMContext() {
        //TODO should check environment variable adn create vm context accordingly.. local / remote / fastvm etc.
        this.vmContext = new FastVMContext();
        this.vmContext.init();

        return this.vmContext;
    }

    public VMContext getVmContext() {
        return vmContext;
    }

    public void setVmContext(VMContext vmContext) {
        this.vmContext = vmContext;
    }

    public String deploy(String address, Object... args) {
        setContractAddress(address);
        return vmContext.deployContract(address, code, args);
    }

    public void compile() throws IOException {

        try {
            String content = FileUtil.getFileContent(sourceFile);
            if(StringUtils.isEmpty(content))
                throw new A4jProcessingException(String.format("Empty content in %s file", sourceFile));

            Compiler.Result r = Compiler.getInstance().compile(content.getBytes(), Options.ABI, Options.BIN);
            CompilationResult cr = CompilationResult.parse(r.output);

            Contract contract = cr.contracts.get(name);
            if(contract == null)
                throw new A4jProcessingException("Contract compilation failed.");

            this.abiJson = contract.abi;
            this.abi = Abi.fromJSON(abiJson);

            String deployer = contract.bin;
            String contractCode = deployer.substring(deployer.indexOf("60506040", 1)); //TODO check for constructor args
            this.code =  Hex.decode(contractCode);

            this.methodWrapperList = AnnotationHelper.getContractMethods(contractClazz, this.abi);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private Function getAbiFunction(Method method) {

        if(method == null) {
            logger.error("Method cannot be null. Please check the caller");
            return null;
        }

        //System.out.println("Methodlist wrapper: " + methodWrapperList);
        if(methodWrapperList == null) {
            logger.warn("No methods found for the contract. Please make sure you have compiled the contract {}", contractClazz);
            return null;
        } else {
            for(MethodWrapper methodWrapper: methodWrapperList) { //TODO keep it inside a map instead of list for O(1) while retrieval.
                //if(method.getName().equals(methodWrapper))
                if(method.equals(methodWrapper.getMethod())) {
                    return methodWrapper.getAbiFunction();
                }
            }
            logger.warn("No equivalent abi function found for this method: " + method.getName())   ;
            return null;
        }
    }

    public Object execute(Method method, Object[] args) {

        Function function = getAbiFunction(method);

        if(function == null) {
            throw new A4jProcessingException("No abi function found for the method: " + method.getName()); //TODO
        }

        try {
            byte[] callData = function.encode(args);

            //Build execution context
            ExecutionContext executionContext = new ExecutionContextBuilder()
                .address(Address.wrap(contractAddress))
                .nrgLimit(200000)
                .nrgPrice(DataWord.ONE)
                .callValue(DataWord.ZERO)
                .callData(callData)
                .build();

            ExecutionResult result = this.vmContext.execute(executionContext, this.code);
            byte[] output = result.getOutput();


            if(output != null) {
                List<?> objs = function.decodeResult(output);

                logger.debug("Output >>>>>>>>>> " + objs);
                if(objs != null && objs.size() == 1) {
                    return objs.get(0);
                } else {
                    return objs;
                }

            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error in method executio",  e);
            throw e;
        }

    }
}
