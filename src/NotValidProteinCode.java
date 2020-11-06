public class NotValidProteinCode extends Exception {

    public NotValidProteinCode() {
        super("invalid protein, start niet met 'AT' check het bestand");
    }

}
