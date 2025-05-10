package Donjon;

import org.junit.jupiter.api.Test;

class DonjonTest {

    @Test
    void display() {
        Donjon dn = new Donjon(25);
        dn.turn();
    }
}