package net.weirun.gallerylibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.nfc.FormatException;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.weirun.gallerylibrary.MyGalleryAdapter;
import net.weirun.gallerylibrary.OnPagerItemClickListener;
import net.weirun.gallerylibrary.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JiangYongHao on 2016/7/6.
 */
public class GalleryPagerView extends RelativeLayout implements ViewPager.OnPageChangeListener {

    private static final String TAG = "GalleryPagerView";

    private ViewPager mViewPager;

    private LinearLayout mPointLayout;

    private MyGalleryAdapter mAdapter;

    private Context mContext;

    private int timeCount;

    private OnPagerItemClickListener itemClickListener;

    /**
     * 是否自动切换
     */
    private boolean isAuto = true;

    /**
     * 轮播图的点
     */
    private int mPointId;
    private int mTimerPosition = 0;

    private int mViewPosition = 0;

    /**
     * 加载图片的位图:图片宽度
     */
    private int mImageWith;
    /**
     * 加载图片的高度：
     */
    private int mImageHeight;

    /**
     * 轮播图的宽高比例
     */
    private float mImageProportion;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mTimerPosition++;
//            Log.d(TAG, "handleMessage: -------------------------------------------" + timeCount);

            if (mTimerPosition == timeCount) {
//                Log.d(TAG, "handleMessage: = ");
                int index = mViewPager.getCurrentItem();
                index = index % mGallery.size() + 1;

                mViewPager.setCurrentItem(index, true);
                mTimerPosition = 0;
            }
        }
    };

    private Timer mTimer;
    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            if (mGallery != null && mGallery.size() > 1 && isAuto) {
                handler.sendEmptyMessage(0);
//                Log.d(TAG, "run: ------------------------------------------");
            }
        }
    };


    private List<View> mGallery = new ArrayList<View>();

    public GalleryPagerView(Context context) {
        super(context);
        init(context);
    }


    public GalleryPagerView(Context context, AttributeSet attrs) throws FormatException {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GalleryPagerView);

        timeCount = array.getInteger(R.styleable.GalleryPagerView_gallery_switching_time, 5);
        mImageProportion = array.getFloat(R.styleable.GalleryPagerView_gallery_with_height_proportion, 0f);
        mPointId = array.getResourceId(R.styleable.GalleryPagerView_gallery_gallery_point, R.drawable.point_selector);
        isAuto = array.getBoolean(R.styleable.GalleryPagerView_gallery_is_auto_switch, true);

        if (timeCount <= 0) {
            throw new FormatException("轮播图切换时间不能小于0秒");
        }

        if (mImageProportion < 0) {
            throw new FormatException("比例大小不能小于0");
        }
//        Log.d(TAG, "GalleryPagerView: timeCount = " + timeCount);

        if (timeCount < 3) {
            timeCount = 3;
        } else if (timeCount > 30) {
            timeCount = 30;
        }

//        Log.d(TAG, "GalleryPagerView: timeCount = " + mPointId);
        init(context);
    }


    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        mContext = context;
        View view = inflate(context, R.layout.layout, this);
        mViewPager = (ViewPager) view.findViewById(R.id.main_view_pager);
        mPointLayout = (LinearLayout) view.findViewById(R.id.points);
        mViewPager.addOnPageChangeListener(this);
    }


    private int mCurrentPosition;
    private List<String> mGalleryPaths;

    /**
     * 设点点击监听
     *
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnPagerItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 是否自动切换
     *
     * @param isAuto
     */
    public void setIsAutoSwitch(boolean isAuto) {
        this.isAuto = isAuto;
    }


    /**
     * 设置加载图片的宽高度
     *
     * @param with
     * @param height
     */
    public void setImageSize(int with, int height) {
        this.mImageHeight = height;
        this.mImageWith = with;
    }


    /**
     * 设置图片
     *
     * @param galleryPaths
     */
    public void setGallery(List<String> galleryPaths) {
        mGallery.removeAll(mGallery);
        if (mPointLayout.getChildCount() > 0)
            mPointLayout.removeAllViews();
        mGalleryPaths = galleryPaths;
        if (galleryPaths != null && galleryPaths.size() > 0) {
            if (galleryPaths.size() > 1) {
                ImageView firstView = createImageView(galleryPaths.get(galleryPaths.size() - 1));
                mGallery.add(firstView);
                for (String url : galleryPaths) {
                    if (url != null) {
                        ImageView imageView = createImageView(url);
                        if (imageView != null) {
                            mGallery.add(imageView);
                            addPoint();
                        }
                    }
                }
                ImageView lastView = createImageView(galleryPaths.get(0));
                mGallery.add(lastView);
                mPointLayout.setVisibility(VISIBLE);
                mPointLayout.getChildAt(0).setSelected(true);
                mCurrentPosition = 1;
                initTimer();

            } else if (galleryPaths.size() == 1) {
                ImageView imageView = createImageView(galleryPaths.get(0));
                mPointLayout.setVisibility(GONE);
                if (imageView != null) {
                    mGallery.add(imageView);
                }
            }

        }
        mAdapter = new MyGalleryAdapter(mGallery);
        mViewPager.setAdapter(mAdapter);
        if (mGallery.size() > 1)
            mViewPager.setCurrentItem(1);
        mViewPager.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "" + mViewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "onClick: -----mViewPager----");
            }

        });
    }

    /**
     * 初始化定时器
     */
    private void initTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
            mTimer.schedule(mTimerTask, 1000, 1000);
        } else {
            mTimerPosition = 0;
        }
    }


    /**
     * 添加点
     */
    private void addPoint() {
        ImageView pointView = new ImageView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(20, 20);
        pointView.setPadding(3, 3, 3, 3);
        pointView.setLayoutParams(params);
        pointView.setImageResource(mPointId);
        pointView.setSelected(false);
        mPointLayout.addView(pointView);
    }

    private ImageView createImageView(String url) {
        if (url != null) {
            ImageView imageView = new ImageView(mContext);
            if (mImageHeight > 0 && mImageWith > 0) {
                Glide.with(mContext).load(url).centerCrop().override(mImageWith, mImageHeight).into(imageView);
            } else {
                Glide.with(mContext).load(url).centerCrop().into(imageView);
            }
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(mContext, "" + mViewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: -----mViewPager----");
                    if (itemClickListener != null) {
                        itemClickListener.onPagerItemClick(mCurrentPosition);
                    }
                }
            });
            return imageView;
        }
        return null;
    }

    /**
     * 设置轮播图的宽高比例
     *
     * @param scale
     */
    public void setScale(float scale) throws Exception {
        if (scale <= 0) {
            throw new Exception("轮播图比例不能小于或者等于0");
        }
        mImageProportion = scale;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        Log.d(TAG, "onPageScrolled:  -----------------------------" + position);
    }

    @Override
    public void onPageSelected(int position) {
        mViewPosition = position;
        if (position == 0) {
            position = mPointLayout.getChildCount() - 1;
        } else {
            position = (position - 1) % (mGallery.size() - 2);
        }
        if (position != mCurrentPosition) {
            mPointLayout.getChildAt(position).setSelected(true);
            mPointLayout.getChildAt(mCurrentPosition).setSelected(false);
            Log.d(TAG, "onPageSelected: position         = " + position);
            Log.d(TAG, "onPageSelected: mCurrentPosition = " + mCurrentPosition);
            mCurrentPosition = position;
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            mTimerPosition = 0;
            if (mGallery != null && mGallery.size() > 1) {
                if (mViewPosition < 1) {
//                    mCurrentPosition = mGalleryPaths.size();
                    mViewPager.setCurrentItem(mGallery.size() - 2, false);
                } else if (mViewPosition > mGallery.size() - 2) {
//                    mCurrentPosition = 0;
                    mViewPager.setCurrentItem(1, false);
                }
            }
            Log.d(TAG, "onPageSelected: mCurrentPosition 后 = " + mCurrentPosition);
            Log.d(TAG, "============================================================= ");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure: mode = " + MeasureSpec.getMode(widthMeasureSpec));
        Log.d(TAG, "onMeasure: mode = " + MeasureSpec.getMode(heightMeasureSpec));
        Log.d(TAG, "onMeasure: size = " + MeasureSpec.getSize(widthMeasureSpec));
        if (mImageProportion > 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            mImageWith = width;
            mImageHeight = (int) (width * mImageProportion);
            setViewHeight(mImageHeight);

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void setViewHeight(int height) {

        ViewGroup.LayoutParams params = this.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = height;
        this.setLayoutParams(params);
    }

    @Override
    protected void onDetachedFromWindow() {
        cancelTimer();
        Log.d(TAG, "onDetachedFromWindow: ");
        super.onDetachedFromWindow();
    }

    /**
     * 取消定时器
     */
    public void cancelTimer() {
        if (mTimer != null) {
            mTimerTask.cancel();
            mTimer.cancel();
            mTimer = null;
        }
    }

}
