package net.clubedocomputador.portinarimirror.features.users.widgets;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.clubedocomputador.portinarimirror.R;
import net.clubedocomputador.portinarimirror.features.base.BaseFragment;
import net.clubedocomputador.portinarimirror.features.faces.FaceView;
import net.clubedocomputador.portinarimirror.injection.component.FragmentComponent;

import butterknife.BindView;


/**
 * Created by pedromalta on 28/03/18.
 */

public class NewUserFragment extends BaseFragment implements FaceView.OnPictureTakenListener{

    @BindView(R.id.input_name)
    EditText username;

    @BindView(R.id.input_nickname)
    EditText nickName;

    @BindView(R.id.face_recognition)
    FaceView faceView;

    @BindView(R.id.face)
    ImageView face;

    @Override
    public int getLayout() {
        return R.layout.fragment_new_user;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected void attachView(){
        faceView.setOnPictureTakenListener(this);
    }

    @Override
    protected void detachPresenter(){

    }

    public int getTitle(){
        return R.string.title_bar_new_user;
    }

    @Override
    public void onPictureTaken(byte[] picture) {
        Glide.with(this).load(picture).into(face);
        face.setVisibility(View.VISIBLE);
        faceView.stop();
        faceView.setVisibility(View.INVISIBLE);
    }
}
