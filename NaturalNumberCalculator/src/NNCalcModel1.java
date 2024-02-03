import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Model class.
 *
 * @author Put your name here
 */
public final class NNCalcModel1 implements NNCalcModel {

    /**
     * Model variables.
     */
    private final NaturalNumber top, bottom;

    /**
     * No argument constructor.
     */
    public NNCalcModel1() {
        this.top = new NaturalNumber2();
        this.bottom = new NaturalNumber2();
    }

    /**
     * returns the top operand
     */
    @Override
    public NaturalNumber top() {

        return this.top;
    }

    /**
     * returns the bottom operand
     */
    @Override
    public NaturalNumber bottom() {

        return this.bottom;
    }

}
