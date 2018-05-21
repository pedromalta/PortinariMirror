package net.clubedocomputador.portinarimirror.features.faces;

import net.clubedocomputador.portinarimirror.data.model.LoggedIn;
import net.clubedocomputador.portinarimirror.data.model.Login;
import net.clubedocomputador.portinarimirror.features.base.MvpView;

/**
 * Created by pedromalta on 14/03/18.
 */

public interface FaceMvpView extends MvpView {

    void showLoading();

    void hideLoading();

    void recognized(String created);

    void showError(Throwable error);
}
