package net.clubedocomputador.portinarimirror.features.login;


import javax.inject.Inject;

import net.clubedocomputador.portinarimirror.data.LoginService;
import net.clubedocomputador.portinarimirror.data.model.Login;
import net.clubedocomputador.portinarimirror.features.base.BasePresenter;
import net.clubedocomputador.portinarimirror.injection.ConfigPersistent;
import net.clubedocomputador.portinarimirror.util.rx.scheduler.SchedulerUtils;

@ConfigPersistent
public class LoginPresenter extends BasePresenter<LoginMvpView> {

    private final LoginService service;

    @Inject
    public LoginPresenter(LoginService service) {
        this.service = service;
    }

    @Override
    public void attachView(LoginMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void login(Login login) {
        checkViewAttached();
        getView().showLoading();
        service
                .login(login)
                .compose(SchedulerUtils.ioToMain())
                .subscribe(
                        loggedIn -> {
                            getView().hideLoading();
                            getView().loginSuccess(loggedIn);
                        },
                        throwable -> {
                            getView().hideLoading();
                            getView().showError(throwable);
                        });
    }
}
