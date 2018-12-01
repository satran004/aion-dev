package org.satran.aion.devtools;

import org.aion.base.type.Address;
import org.aion.mcf.vm.types.DataWord;
import org.aion.vm.ExecutionContext;
import org.apache.commons.lang3.RandomUtils;

public class ExecutionContextBuilder {

    private byte[] txHash = RandomUtils.nextBytes(32);
    private Address origin = Address.wrap(RandomUtils.nextBytes(32));
    private Address caller = origin;
    private Address address = Address.wrap(RandomUtils.nextBytes(32));

    private Address blockCoinbase = Address.wrap(RandomUtils.nextBytes(32));
    private long blockNumber = 1;
    private long blockTimestamp = System.currentTimeMillis() / 1000;
    private long blockNrgLimit = 5000000;
    private DataWord blockDifficulty = new DataWord(0x100000000L);

    private DataWord nrgPrice;
    private long nrgLimit;
    private DataWord callValue;
    private byte[] callData;

    private int depth = 0;
    private int kind = ExecutionContext.CREATE;
    private int flags = 0;

    public ExecutionContext build() {
        ExecutionContext ctx = new ExecutionContext(txHash, address, origin, caller, nrgPrice, nrgLimit, callValue,
            callData, depth, kind, flags, blockCoinbase, blockNumber, blockTimestamp, blockNrgLimit,
            blockDifficulty);
        return ctx;
    }

    public byte[] getTxHash() {
        return txHash;
    }

    public ExecutionContextBuilder txHash(byte[] txHash) {
        this.txHash = txHash;
        return this;
    }

    public Address getOrigin() {
        return origin;
    }

    public ExecutionContextBuilder origin(Address origin) {
        this.origin = origin;
        return this;
    }

    public Address getCaller() {
        return caller;
    }

    public ExecutionContextBuilder caller(Address caller) {
        this.caller = caller;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public ExecutionContextBuilder address(Address address) {
        this.address = address;
        return this;
    }

    public Address getBlockCoinbase() {
        return blockCoinbase;
    }

    public ExecutionContextBuilder blockCoinbase(Address blockCoinbase) {
        this.blockCoinbase = blockCoinbase;
        return this;
    }

    public long getBlockNumber() {
        return blockNumber;
    }

    public ExecutionContextBuilder blockNumber(long blockNumber) {
        this.blockNumber = blockNumber;
        return this;
    }

    public long getBlockTimestamp() {
        return blockTimestamp;
    }

    public ExecutionContextBuilder blockTimestamp(long blockTimestamp) {
        this.blockTimestamp = blockTimestamp;
        return this;
    }

    public long getBlockNrgLimit() {
        return blockNrgLimit;
    }

    public ExecutionContextBuilder blockNrgLimit(long blockNrgLimit) {
        this.blockNrgLimit = blockNrgLimit;
        return this;
    }

    public DataWord getBlockDifficulty() {
        return blockDifficulty;
    }

    public ExecutionContextBuilder blockDifficulty(DataWord blockDifficulty) {
        this.blockDifficulty = blockDifficulty;
        return this;
    }

    public DataWord getNrgPrice() {
        return nrgPrice;
    }

    public ExecutionContextBuilder nrgPrice(DataWord nrgPrice) {
        this.nrgPrice = nrgPrice;
        return this;
    }

    public long getNrgLimit() {
        return nrgLimit;
    }

    public ExecutionContextBuilder nrgLimit(long nrgLimit) {
        this.nrgLimit = nrgLimit;
        return this;
    }

    public DataWord getCallValue() {
        return callValue;
    }

    public ExecutionContextBuilder callValue(DataWord callValue) {
        this.callValue = callValue;
        return this;
    }

    public byte[] getCallData() {
        return callData;
    }

    public ExecutionContextBuilder callData(byte[] callData) {
        this.callData = callData;
        return this;
    }

    public int getDepth() {
        return depth;
    }

    public ExecutionContextBuilder depth(int depth) {
        this.depth = depth;
        return this;
    }

    public int getKind() {
        return kind;
    }

    public ExecutionContextBuilder kind(int kind) {
        this.kind = kind;
        return this;
    }

    public int getFlags() {
        return flags;
    }

    public ExecutionContextBuilder flags(int flags) {
        this.flags = flags;
        return this;
    }
}
