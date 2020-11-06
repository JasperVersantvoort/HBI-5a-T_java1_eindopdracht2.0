/**
 * @author Jasper Versantvoort
 * studentennummer: 634664
 * datum: 06-11-2020
 */


public class NotValidProteinCode extends Exception {
    /**
     * functie: wanneer bestand niet met 'AT' begint zal deze exception worden aangeroepen.
     */

    public NotValidProteinCode() {
        super("invalid protein, start niet met 'AT' check het bestand");
    }

}
