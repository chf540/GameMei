
package gamemei.qiyun.com.gamemei.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import gamemei.qiyun.com.gamemei.R;

public class LoadingDialog extends Dialog {

    private View loading_icon;
    private int icon_width;
    private TextView loadTextView;
    private ImageView loadImageView;
    private Context context;
    private OnLoadingCloseClickListener monLoadingClick;// 关闭loading监听

    private Animation animation;

    public LoadingDialog(Context context) {
        super(context, R.style.dialog_normal);
        this.context = context;
        setContentView(R.layout.dialog_loading);
        initView();
    }

    /**
     * 设置是否显示加载进度条的后面的差号
     *
     * @param isTrue
     */
    public void isOpenLoadImageView(boolean isTrue) {
        if (isTrue) {
            loadImageView.setVisibility(View.VISIBLE);
        } else {
            loadImageView.setVisibility(View.GONE);
        }

    }

    private void initView() {
        // View view = LayoutInflater.from(context).
        loadTextView = (TextView) findViewById(R.id.loading_text);
        loadImageView = (ImageView) findViewById(R.id.loading_image_close);
        loading_icon = findViewById(R.id.loading_progressbar);
        loadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                LoadingDialog.this.cancel();
                new Thread() {
                    public void run() {
                        //MABIIRequestManager.cancelAllRequest();
                    }
                }.start();

                if (monLoadingClick != null) {
                    monLoadingClick.onLoadingCloseClick();
                }
            }
        });

        loading_icon.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                icon_width = loading_icon.getWidth();
                if (icon_width > 0) {
                    loading_icon.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    if (isShouldAnimation) {
                        startAnimaion();
                    }
                }
            }
        });
    }

    public void setOnLoadingCloseClickListener(OnLoadingCloseClickListener mOnLoading) {
        this.monLoadingClick = mOnLoading;
    }

    public interface OnLoadingCloseClickListener {
        abstract void onLoadingCloseClick();
    }

    public void setLoadText(String str) {
        if (!TextUtils.isEmpty(str)) {
            loadTextView.setText(str);
        }
    }

    private boolean isShouldAnimation = false;

    @Override
    public void show() {
        super.show();
        if (icon_width > 0) {
            startAnimaion();
            return;
        }
        isShouldAnimation = true;
    }

    /**
     * 旋转动画
     */
    private void startAnimaion() {
        if (animation == null) {
            animation = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);

            animation.setInterpolator(new LinearInterpolator());
            animation.setDuration(800);
            animation.setRepeatCount(Integer.MAX_VALUE);
            animation.setFillAfter(true);
        }
        loading_icon.startAnimation(animation);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (animation != null) {
            animation.cancel();
            animation.reset();
            return;
        }
        isShouldAnimation = false;
    }

    // key down
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return true;
        }
        return false;
    }

    /**
     * logo 转动动画
     */
    public class Rotate3dAnimation extends Animation {
        private final float mFromDegrees;
        private final float mToDegrees;
        private final float mCenterX;
        private final float mCenterY;
        private final float mDepthZ;
        private final boolean mReverse;
        private Camera mCamera;

        /**
         * @param fromDegrees the start angle of the 3D rotation
         * @param toDegrees   the end angle of the 3D rotation
         * @param centerX     the X center of the 3D rotation
         * @param centerY     the Y center of the 3D rotation
         * @param reverse     true if the translation should be reversed, false otherwise
         */
        public Rotate3dAnimation(float fromDegrees, float toDegrees,
                                 float centerX, float centerY, float depthZ, boolean reverse) {
            mFromDegrees = fromDegrees;
            mToDegrees = toDegrees;
            mCenterX = centerX;
            mCenterY = centerY;
            mDepthZ = depthZ;
            mReverse = reverse;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final float fromDegrees = mFromDegrees;
            float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

            final float centerX = mCenterX;
            final float centerY = mCenterY;
            final Camera camera = mCamera;

            final Matrix matrix = t.getMatrix();

            camera.save();
            if (mReverse) {
                camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
            } else {
                camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
            }
            camera.rotateY(degrees);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }
}
