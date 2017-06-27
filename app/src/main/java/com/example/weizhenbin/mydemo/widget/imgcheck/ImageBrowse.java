package com.example.weizhenbin.mydemo.widget.imgcheck;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.opengl.GLES10;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weizhenbin.mydemo.presenter.ImageCheckControl;
import com.weizhenbin.show.R;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by weizhenbin on 2017/1/12.
 * 图片浏览工具
 * <p>
 * 1 支持放大缩小 边界检测 回弹效果  （网上找的TouchImageView可实现）
 * 2 支持viewpager 解决滑动冲突
 * 3 支持长按 单击
 * 4 解决开启硬件加速以后的GPU绘制限制
 */
public class ImageBrowse extends Fragment implements ViewPager.OnPageChangeListener {

    private ExtendedViewPager extendedViewPager;
    private View contentView;
    private ImageListAdapter imageListAdapter;
    private List<ImageCheckControl.ImageInfo> srcUrls = new ArrayList<>();
    private BrowseOnClickListener onClickListener;
    private BrowseOnLongClickListener onLongClickListener;
    private BrowseScrollListener browseScrollListener;
    private int itemCount = 0;
    private int currentPosition = 0;
    private TextView tvPoint;
    private int maxTexture = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.imagebrowse_layout, null);
        extendedViewPager = (ExtendedViewPager) contentView.findViewById(R.id.view_pager);
        tvPoint = (TextView) contentView.findViewById(R.id.tv_point);
        imageListAdapter = new ImageListAdapter(srcUrls);
        extendedViewPager.setAdapter(imageListAdapter);
        extendedViewPager.addOnPageChangeListener(this);
        maxTexture = getGLESTextureLimitEqualAboveLollipop();
        return contentView;
    }

    public boolean isNeedCloseHardwareAcceleration() {
        int[] maxSize = new int[1];
        GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxSize, 0);
        return false;
    }

    /**
     * 计算硬件加速的限制
     */
    private int getGLESTextureLimitEqualAboveLollipop() {
        try {
            EGL10 egl = (EGL10) EGLContext.getEGL();
            EGLDisplay dpy = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            int[] vers = new int[2];
            egl.eglInitialize(dpy, vers);
            int[] configAttr = {
                    EGL10.EGL_COLOR_BUFFER_TYPE, EGL10.EGL_RGB_BUFFER,
                    EGL10.EGL_LEVEL, 0,
                    EGL10.EGL_SURFACE_TYPE, EGL10.EGL_PBUFFER_BIT,
                    EGL10.EGL_NONE
            };
            EGLConfig[] configs = new EGLConfig[1];
            int[] numConfig = new int[1];
            egl.eglChooseConfig(dpy, configAttr, configs, 1, numConfig);
            if (numConfig[0] == 0) {// TROUBLE! No config found.
            }
            EGLConfig config = configs[0];
            int[] surfAttr = {
                    EGL10.EGL_WIDTH, 64,
                    EGL10.EGL_HEIGHT, 64,
                    EGL10.EGL_NONE
            };
            EGLSurface surf = egl.eglCreatePbufferSurface(dpy, config, surfAttr);
            final int EGL_CONTEXT_CLIENT_VERSION = 0x3098;  // missing in EGL10
            int[] ctxAttrib = {
                    EGL_CONTEXT_CLIENT_VERSION, 1,
                    EGL10.EGL_NONE
            };
            EGLContext ctx = egl.eglCreateContext(dpy, config, EGL10.EGL_NO_CONTEXT, ctxAttrib);
            egl.eglMakeCurrent(dpy, surf, surf, ctx);
            int[] maxSize = new int[1];
            GLES10.glGetIntegerv(GLES10.GL_MAX_TEXTURE_SIZE, maxSize, 0);
            egl.eglMakeCurrent(dpy, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE,
                    EGL10.EGL_NO_CONTEXT);
            egl.eglDestroySurface(dpy, surf);
            egl.eglDestroyContext(dpy, ctx);
            egl.eglTerminate(dpy);
            return maxSize[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void showImageBrowse(List<ImageCheckControl.ImageInfo> urls) {
        if (urls != null && !urls.isEmpty()) {
            srcUrls.clear();
            addImageUrls(urls, 0);
        }
    }

    public void addImageUrls(List<ImageCheckControl.ImageInfo> urls, int index) {
        if (urls != null && !urls.isEmpty()) {
            this.srcUrls.addAll(index, urls);
            imageListAdapter.notifyDataSetChanged();
            itemCount = srcUrls.size();
            tvPoint.setText((index + 1) + "/" + itemCount);
        }
    }

    public void setPageCurrentPosition(int position){
        if(srcUrls!=null&&position<=srcUrls.size()){
            extendedViewPager.setCurrentItem(position);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        tvPoint.setText((currentPosition + 1) + "/" + itemCount);
        if (browseScrollListener != null) {
            if (currentPosition == 0) {
                browseScrollListener.onFirstPosition();
            }
            if (currentPosition == itemCount - 1) {
                browseScrollListener.onLastPosition(currentPosition);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class ImageListAdapter extends PagerAdapter {


        private BrowseOnClickListener onClickListener;
        private BrowseOnLongClickListener onLongClickListener;
        private List<ImageCheckControl.ImageInfo> urls;

        public ImageListAdapter(List<ImageCheckControl.ImageInfo> urls) {
            this.urls = urls;
        }

        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            ImageItemView img = new ImageItemView(container.getContext());
            img.setBackgroundColor(Color.BLACK);
            img.setImageUrl(urls.get(position));
            container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            img.setOnClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v, position);
                    }
                }
            });
            img.setOnLongClick(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onLongClickListener != null) {
                        onLongClickListener.onLongClick(v, position);
                        return true;
                    }
                    return false;
                }
            });
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        public void setOnClickListener(BrowseOnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        public void setOnLongClickListener(BrowseOnLongClickListener onLongClickListener) {
            this.onLongClickListener = onLongClickListener;
        }
    }

    public void setOnClickListener(BrowseOnClickListener onClickListener) {
        if (imageListAdapter != null) {
            imageListAdapter.setOnClickListener(onClickListener);
        }
    }

    public void setOnLongClickListener(BrowseOnLongClickListener onLongClickListener) {
        if (imageListAdapter != null) {
            imageListAdapter.setOnLongClickListener(onLongClickListener);
        }
    }

    public void setBrowseScrollListener(BrowseScrollListener browseScrollListener) {
        this.browseScrollListener = browseScrollListener;
    }

    /**
     * 点击监听
     */
    public interface BrowseOnClickListener {
        void onClick(View view, int position);
    }

    /**
     * 长按监听
     */
    public interface BrowseOnLongClickListener {
        void onLongClick(View view, int position);
    }

    /**
     * 滑动监听 滑到开头和结尾
     */
    public interface BrowseScrollListener {
        void onFirstPosition();

        void onLastPosition(int position);
    }

    private static Bitmap compress(Bitmap srcBmp, int maxTexture) {
        if (srcBmp != null) {
            int w = srcBmp.getWidth();
            int h = srcBmp.getHeight();
            if (maxTexture <= 0) {
                return srcBmp;
            }
            if (w > maxTexture || h > maxTexture) {
                float multiple = 1;
                //压缩
                Matrix matrix = new Matrix();
                if (w > maxTexture && h < maxTexture) {
                    //宽度超过了
                    multiple = (float) maxTexture / w;
                } else if (w < maxTexture && h > maxTexture) {
                    //高度超过了
                    multiple = (float) maxTexture / h;
                } else {
                    //宽高都超过了
                    multiple = (float) maxTexture / w > (float) maxTexture / h ? (float) maxTexture / w : (float) maxTexture / h;
                }
                // 缩放图片动作
                matrix.postScale(multiple, multiple);
                Bitmap bitmap = Bitmap.createBitmap(srcBmp, 0, 0, w,
                        h, matrix, true);
                return bitmap;
            }
            return srcBmp;
        }
        return srcBmp;
    }
}
