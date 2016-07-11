package net.weirun.gallerylibrary;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by JiangYongHao on 2016/7/6.
 */
public class MyGalleryAdapter extends PagerAdapter {

    /**
     * 轮播图地址
     */
    private List<View> mGallery;

    public MyGalleryAdapter(List<View> mGallery) {
        this.mGallery = mGallery;
    }

    @Override
    public int getCount() {
        if (mGallery != null)
            return mGallery.size();
        return 0;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView(mGallery.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mGallery.get(position);
        container.addView(view);
        return view;
    }
}
