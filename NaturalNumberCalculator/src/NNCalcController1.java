import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Controller class.
 *
 * @author Put your name here
 */
public final class NNCalcController1 implements NNCalcController {

    /**
     * Model object.
     */
    private final NNCalcModel model;

    /**
     * View object.
     */
    private final NNCalcView view;

    /**
     * Useful constants.
     */
    private static final NaturalNumber TWO = new NaturalNumber2(2),
            INT_LIMIT = new NaturalNumber2(Integer.MAX_VALUE);

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateViewToMatchModel(NNCalcModel model,
            NNCalcView view) {

        NaturalNumber top = model.top();
        NaturalNumber bottom = model.bottom();

        /**
         * check to see if division is allowed (no dividing by 0)
         */
        if (bottom.isZero()) {
            view.updateDivideAllowed(false);
        } else {
            view.updateDivideAllowed(true);
        }

        /**
         * check to see if subtraction is allowed (since naturalnumbers can't be
         * negative, the second number must be less than the first)
         */

        if (bottom.compareTo(top) <= 0) {
            view.updateSubtractAllowed(true);
        } else {
            view.updateSubtractAllowed(false);
        }

        /**
         * check for if the root function is possible, (root has to be greater
         * than 2 but less than the max int value)
         */

        if (bottom.compareTo(TWO) >= 0 && bottom.compareTo(INT_LIMIT) <= 0) {
            view.updateRootAllowed(true);
        } else {
            view.updateRootAllowed(false);
        }

        /**
         * check for if the power function is legal (the power has to be equal
         * to or less than the max int value since the naturalnumber power
         * function uses an integer for the power input
         */

        if (bottom.compareTo(INT_LIMIT) <= 0) {
            view.updatePowerAllowed(true);
        } else {
            view.updatePowerAllowed(false);
        }

        view.updateTopDisplay(top);
        view.updateBottomDisplay(bottom);
    }

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public NNCalcController1(NNCalcModel model, NNCalcView view) {

        this.model = model;
        this.view = view;

    }

    @Override
    public void processClearEvent() {
        /*
         * get the value of the bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        /*
         * clear the bottom
         */
        bottom.clear();
        /*
         * update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSwapEvent() {
        /*
         * get top and bottom values from model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * swap top and bottom using a temp value
         */
        NaturalNumber temp = top.newInstance();
        temp.transferFrom(top);
        top.transferFrom(bottom);
        bottom.transferFrom(temp);
        /*
         * update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processEnterEvent() {
        /**
         * obtain top and bottom values from the model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        /**
         * copy the input to the output
         */
        top.copyFrom(bottom);
        /**
         * update the view to match output
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddEvent() {
        /**
         * obtain top and bottom values from the model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        /**
         * add the value of the top to the bottom and clear the top
         */
        bottom.add(top);
        top.clear();
        /**
         * update the view to match output
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processSubtractEvent() {
        /**
         * obtain top and bottom values from the model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        /**
         * subtract the bottom from the top, transfer top to bottom
         */
        top.subtract(bottom);
        bottom.transferFrom(top);
        /**
         * update the view to match output
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processMultiplyEvent() {
        /**
         * obtain top and bottom values from the model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        /**
         * multiply the top by the bottom and transfer the result to the bottom
         */
        top.multiply(bottom);
        bottom.transferFrom(top);
        /**
         * update the view to match output
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processDivideEvent() {

        /**
         * obtain top and bottom values from the model, as well as a value for
         * the remainder result of division
         */

        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        NaturalNumber remainder = top.divide(bottom);

        /**
         * divide the top by the bottom, set the bottom to the amount of times
         * the top was able to be evenly divided by the bottom, and set the top
         * to the remainder
         */

        bottom.transferFrom(top);
        top.transferFrom(remainder);

        /**
         * update the view to match output
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processPowerEvent() {
        /**
         * obtain top and bottom values from the model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        /**
         * take the top value to the power of the bottom value, and transfer to
         * the bottom
         */

        top.power(bottom.toInt());
        bottom.transferFrom(top);
        /**
         * update the view to match output
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processRootEvent() {

        /**
         * obtain top and bottom values from the model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();

        /**
         * take the root of the top using the int value of the bottom and
         * transfer to the bottom
         */
        top.root(bottom.toInt());
        bottom.transferFrom(top);

        /**
         * update the view to match output
         */

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddNewDigitEvent(int digit) {

        /**
         * obtain bottom value from the model
         */

        NaturalNumber bottom = this.model.bottom();

        /**
         * append the digit to the end of the bottom number
         */
        bottom.multiplyBy10(digit);

        /**
         * update the view to match output
         */
        updateViewToMatchModel(this.model, this.view);

    }

}
