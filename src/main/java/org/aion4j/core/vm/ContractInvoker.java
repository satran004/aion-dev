package org.aion4j.core.vm;

import java.util.List;
import org.aion4j.core.api.BaseContract;

public interface ContractInvoker {

    public List<Object> call();

    public void deploy(BaseContract contract);

//
//    public TxReceiptBean deployContractByCode(String code, String abiString, String fromAddress,
//        long nrgLimit, long nrgPrice, List<Object> args);
//
//    public List<?> call(TxArgsInput txArgsInput, String abiFunction, List<Output> outputTypes);

}
