package yqb.com.zhuhudaliy.util;

/**
 * Created by yangzikang on 2017/8/3.
 */

public class ImageUrlRepair {
    //图片的Url有问题，通过字符串处理返回正确的字符串
    public static String handleImageUrl(String imageUrl) {
        if (imageUrl.length() > 2) {
            imageUrl = imageUrl.substring(2);
            String[] realImageUrl = imageUrl.split("\"");
            imageUrl = realImageUrl[0];
            imageUrl = imageUrl.replace("\\", "");
            return imageUrl;
        } else {
            return imageUrl;
        }
    }
}
