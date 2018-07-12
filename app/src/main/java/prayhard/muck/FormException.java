package prayhard.muck;

public class FormException extends Exception {

    public FormException(){
        super("Form Incomplete, Body or Title Must be complete");
    }
}
