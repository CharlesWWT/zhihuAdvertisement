# zhihuAdvertisement
仿知乎广告
##是在hongyang的基础上修改，添加注释和详解，主要是图片的几个状态的判断，来控制canvas偏移

![image1](https://github.com/MissGongYi/zhihuAdvertisement/blob/master/show/one.gif)
![image2](https://github.com/MissGongYi/zhihuAdvertisement/blob/master/show/two.gif)
## 一：图片视觉效果不动
* 当imageview没有完全显示时，偏移量固定,偏移量为drawable的高度减去imageview控件的高度，此时不进行canvas偏移，视觉效果就是图片跟随手指一起滑动
* 当imageview完全显示时，进行偏移量计算，drawable高度减去imageview高度再减去imageview到屏幕底部的距离，视觉效果为手指滑动，图片不动
* 当drawable图片顶部完全显示时，偏移量为0，偏移活动完成，视觉效果为图片和手指一起滑动
```         
if(dy<=mMinDy) {
            offSet= (int) (mBitmapRectF.height()-mMinDy);
        }else {
            offSet= (int) (mBitmapRectF.height()-mMinDy-(dy-mMinDy));
        }
        if(dy>=mBitmapRectF.height()) {
            offSet=0;
        }
```
也可以将mLinearLayoutManager.getHeight()传过来计算滑动比例，视觉效果对小图更好
## 二：图片从顶部开始随着手指向下滚动
就是hongyang写的那种效果,当图片完全显示时开始从上往下偏移，完全显示完后不进行偏移
* 当imageview没有完全显示时，不进行偏移，drawable一直显示顶部
* 当imageview完全显示时，offset偏移量就是imageview底部到屏幕底部的距离
* 当imageview底部到屏幕底部的距离大于drawable高度时，不进行偏移
```
        mDy = dy - mMinDy; //mDy:图片底部到屏幕底部的距离
        //当控件没有完全显示出来时，不进行偏移
        if (mDy <= 0) {
            offSet = 0;
        }
        //当图片完全显示时
        if(mDy>0) {
            offSet=mDy;
            if(dy>=mBitmapRectF.height()) {
                int temp= (int) mBitmapRectF.height();
                offSet=temp-mMinDy;
            }
        }
```
