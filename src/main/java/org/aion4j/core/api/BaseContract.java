package org.aion4j.core.api;

public interface BaseContract {

//    public ContractContext context = null;

//    public void setAbiJson(String abiJson);
//    public String getAbiJson();
//
//    public void setContractAddress(String address);
//    public String getContractAddress();
//
//    public String deploy(Object... args);

    public ContractContext getContext();

    public void setContext(ContractContext context);
}
