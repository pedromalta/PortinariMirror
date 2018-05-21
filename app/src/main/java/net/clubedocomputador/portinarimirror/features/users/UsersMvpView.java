package net.clubedocomputador.portinarimirror.features.users;

import net.clubedocomputador.portinarimirror.features.base.MvpView;

/**
 * Created by pedromalta on 14/03/18.
 */

public interface UsersMvpView extends MvpView {

    void showLoading();

    void hideLoading();

    void userCreated(String userCreated);

    void showError(Throwable error);
}
