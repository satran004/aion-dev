pragma solidity ^0.4.15;

contract TypesTest {
    uint8 i;
    bytes32 b;
    address a;
    string s;
    bool bl;

    function TypesTest() {
        s = "hello";
    }

    function set1(uint8 _i, address _a, bool _bl) public {
        i = _i;
        a = _a;
        bl = _bl;
    }

    function set(uint8 _i, address _a, bool _bl) public {
        i = _i;
        a = _a;
        bl = _bl;
    }

    function get() public constant returns (uint8, address,  bool) {
        return (i, a, bl);
    }

    function getAddress() public constant returns (address) {
        return a;
    }

    function getInt() public constant returns(uint8) {
        return i;
    }

    function getString() public constant returns(string) {
        return s;
    }
}