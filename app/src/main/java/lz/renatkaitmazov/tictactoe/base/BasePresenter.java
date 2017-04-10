package lz.renatkaitmazov.tictactoe.base;

/**
 * @author Renat Kaitmazov
 */

public abstract class BasePresenter<V extends MvpView> implements Presenter<V>{

    /** Instance variables **/
    protected V view;

    /** MvpView implementation **/

    @Override
    public void bind(V view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        view = null;
    }
}
