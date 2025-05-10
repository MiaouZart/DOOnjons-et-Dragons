package Donjon;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DonjonTest {

    @Test
    void display() {
        Donjon dn = new Donjon(16);
        dn.Display();
    }
}