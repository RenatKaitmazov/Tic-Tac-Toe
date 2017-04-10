package lz.renatkaitmazov.tictactoe.base;

/**
 * @author Renat Kaitmazov
 */

public interface Presenter<V extends MvpView> {
    void bind(V view);
    void unbind();
}
