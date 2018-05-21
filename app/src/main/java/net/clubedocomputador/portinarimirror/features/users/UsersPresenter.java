package net.clubedocomputador.portinarimirror.features.users;


import net.clubedocomputador.portinarimirror.data.FaceService;
import net.clubedocomputador.portinarimirror.data.LoginService;
import net.clubedocomputador.portinarimirror.data.model.Login;
import net.clubedocomputador.portinarimirror.data.model.User;
import net.clubedocomputador.portinarimirror.features.base.BasePresenter;
import net.clubedocomputador.portinarimirror.injection.ConfigPersistent;
import net.clubedocomputador.portinarimirror.util.rx.scheduler.SchedulerUtils;

import javax.inject.Inject;

@ConfigPersistent
public class UsersPresenter extends BasePresenter<UsersMvpView> {

    private final FaceService faceService;

    @Inject
    public UsersPresenter(FaceService faceService) {
        this.faceService = faceService;
    }

    @Override
    public void attachView(UsersMvpView mvpView) {
        super.attachView(mvpView);
    }


    public void recognise(User user) {
        checkViewAttached();
        getView().showLoading();
        //TODO
        faceService
                .create(user)
                .compose(SchedulerUtils.ioToMain())
                .subscribe(
                        userCreated -> {
                            getView().userCreated(userCreated);
                        },
                        throwable -> {
                            getView().showError(throwable);
                        });
    }
}
