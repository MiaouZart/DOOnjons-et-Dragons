package Equipment;

public abstract class Equipment {
    private final String m_name;

    public Equipment(String name) {
        m_name = name;
    }

    public String getName() {
        return m_name;
    }
}
