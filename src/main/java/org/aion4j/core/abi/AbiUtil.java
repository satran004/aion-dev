package org.aion4j.core.abi;

import java.util.List;
import org.aion.solidity.Abi.Entry.Param;
import org.aion.solidity.Abi.Function;
import org.aion.solidity.SolidityType;

public class AbiUtil {

    public static boolean isSameFunction(Function function, String name, String[] params) {

        String fnName = function.name;

        if(name == null || !name.equals(fnName)) {
            return false;
        }

        List<Param> abiParams = function.inputs;

        if(abiParams.size() != params.length)
            return false;

        if(abiParams.size() == 0) {
            if(params.length == 0)
                return true;
            else
                return false;
        }

        for(int i=0; i<abiParams.size(); i++) {

            SolidityType abiType = abiParams.get(i).type;

            SolidityType inputType = SolidityType.getType(params[i]);

            if(!abiType.getName().equals(inputType.getName())) {
                return false;
            }
        }

        return true;
    }

}
