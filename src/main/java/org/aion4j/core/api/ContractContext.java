package org.aion4j.core.api;

import java.io.IOException;
import org.aion.solidity.Abi;
import org.aion.solidity.CompilationResult;
import org.aion.solidity.CompilationResult.Contract;
import org.aion.solidity.Compiler;
import org.aion.solidity.Compiler.Options;
import org.aion4j.core.exception.A4jProcessingException;
import org.aion4j.core.util.FileUtil;
import org.apache.commons.lang3.StringUtils;

public class ContractContext {

    private String name;
    private String abiJson;
    private String sourceFile;
    private String contractAddress;

    private Abi abi;
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String deploy(Object... args) {
        System.out.println("Inside deploy..... " + args);
        return null;
    }

    public void compile() throws IOException {

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
        this.code = contract.bin;
    }

}
