package net.weirun.gallerylibrary;

import android.support.v4.view.ViewPager;

import net.weirun.gallerylibrary.widget.GalleryPagerView;

/**
 * Created by JiangYongHao on 2016/7/8.
 */
public interface OnPagerItemClickListener {

    /**
     * 点击事件
     * 若果真的自己做的话，也没那么快，像我现在的知识做客户端推送方面的话，还需要充实
     *
     * @param position
     */
    void onPagerItemClick(int position);


}
