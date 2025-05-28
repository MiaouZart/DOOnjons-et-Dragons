package printer;

public abstract class StandardOut {

    /**
     * Affiche dans la sortie une chaîne de caractère.
     * @param str Chaîne de caractère à afficher.
     */
    public abstract void out(String str);

    /**
     * Affiche dans la sortie une chaîne de caractère avec un retour à la ligne.
     * @param str Chaîne de caractère à afficher
     */
    public abstract void outLn(String str);

    /**
     * Récupère l'entrée.
     * @return Chaîne de caractère récupéré de l'entrée.
     */
    public abstract String in();

    /**
     * Afficher dans sorti, avec un format.
     * @param str Chaîne de caractère avec le format
     * @param obj Objet à afficher dans la sortie formaté.
     */
    public abstract void printf(String str,Object obj);

    /**
     * Afficher dans sorti, avec un format.
     * @param s Chaîne de caractère avec le format
     * @param rows Identification devant la ligne.
     * @param space Caractère pour l'espace.
     */
    public abstract void printf(String s, int rows, String space);
}
