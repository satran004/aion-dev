# aion4j-core

Status : Not Ready

It provides a java client library which can be used to invoke Smart Contract method from java.
The initial version of this library will help developers to unit test their solidity contracts using any existing java testing framework and an embedded FastVM instance. Developers should be able to use their preferred java testing framework. With an embedded Fast VM or dummy blockchain, the developer can test their smart contract in a deterministic way.

- This framework will provide a java interface/POJO based programming model to interact with the smart contract. For each smart contract, an equivalent java interface will be defined with all the required methods of solidity contract. (This interface can be auto-generated through a maven / gradle plugin)
- The framework will dynamically generate an implementation for the interface during runtime (by creating a proxy / delegate through byte code manipulation libraries). 
- Developer can obtain an instance of generated proxy class (by passing the interface) from the framework library and interact with it to deploy/invoke different methods in the smart contract.
- The generated proxy object should be able to convert all input params from java types to solidity types while passing to the actual contract and similarly convert the return objects in solidity to java types.
- In the initial version, the proxy implementation will be supported for an embedded FastVM based dummy blockchain. This can  be used for the smart contract unit testing. So the first phase of this library only aims  to support unit testing for smart contracts.
- In the later version, the proxy implementation will be enhanced to support actual blockchain (remote). So developer can use  same interface based programming model but will be connected to a remote blockchain during runtime.
The implementation (embedded or remote) will be decided through the available configuration parameters during runtime without changing any java code.

The goal is to support the same programming model to interact with both an embedded and actual blockchain deployment.