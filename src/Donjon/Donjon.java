package Donjon;

public class Donjon {

    private int m_donjonSize;
    private String[][] m_donjonGrid;

    public  Donjon(int size){
        if(size<15||size>25){
            throw new IllegalArgumentException("Grid Size is to small or to large");
        }
        m_donjonSize = size;
        m_donjonGrid = new String[m_donjonSize][m_donjonSize];
        for (int i = 0; i < m_donjonSize; i++) {
            for (int j = 0; j < m_donjonSize; j++) {
                m_donjonGrid[i][j] = " . ";
            }
        }
    }


    public void Display(){
        System.out.println("");
        for(String[] row : m_donjonGrid){
            for(String cell: row){
                System.out.print(cell+" | ");
            }
            System.out.println();
        }
    }


}
