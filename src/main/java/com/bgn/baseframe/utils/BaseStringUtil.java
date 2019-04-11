package com.bgn.baseframe.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;

import com.orhanobut.logger.Logger;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 作者：wl on 2017/9/15 11:24
 * 邮箱：wanglun@stosz.com
 */
public class BaseStringUtil {

    public final static String FORMAT_ONE = "##,###,##0.00";
    public final static String FORMAT_TWO = "#,###,###";
    public final static String FORMAT_THREE = "##,###,###.##";
    public final static String FORMAT_FLOAT_PRICE = "0.00";
    /**
     * 正则表达式:验证密码
     */
    public static final String REGEX_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
    /**
     * 邮箱
     */
    public static final String REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static final String USER_NAME = "^[\\u2E80-\\uFE4F][\\u2E80-\\uFE4F\\·]{0,8}[\\u2E80-\\uFE4F]$";

    public static final String ADDRESS = "^[\\u2E80-\\uFE4Fa-zA-Z0-9\\s\\-/()（）&,.，·#，。；;:：！!？?|｜、''“”’‘㙍]{0,}$";
    //特殊字符
    public static final String REGEX_STR = "[`~@#$%^&*+=|{}'',\\[\\]<>/~！@#￥%……&*（）——+|{}【】‘]";

    public static final String REGEX_STR_ACVICE = "[`~@#$%^&*+=|{}'',\\[\\]<>/~！@#￥%……&*（）——+|{}【】‘]";

    /**
     * 判断两个字符串是否相同
     *
     * @param s1
     * @param s2
     * @return Matcher
     */
    public static boolean isEquals(String s1, String s2) {
        if (isEmpty(s1)) {
            s1 = "";
        }

        if (isEmpty(s2)) {
            s2 = "";
        }
        return s1.equals(s2);
    }


    /**
     * 判断字符串是否为null
     *
     * @param text 字符串
     * @return true:为null  false:不为null
     */
    public static boolean isEmpty(String text) {

        return TextUtils.isEmpty(text) || "null".equals(text);
    }

    /**
     * 校验特俗字符
     *
     * @param str
     * @return 校验通过返回true，否则返回false
     */
    public static boolean hasSpecialCharacter(String str) throws PatternSyntaxException {
        String regEx = "[`~@#$%^&*+=|{}'',\\[\\]<>/~！@#￥%……&*（）——+|{}【】‘]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


    /**
     * 校验手机
     *
     * @param mobiles
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("(1)\\d{10}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();
    }


    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验邮箱
     *
     * @param mail
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isValidEmail(String mail) {
        return Pattern.matches(REGEX_EMAIL, mail);
    }

    /**
     * 替换手机号中间的4位
     *
     * @param phoneNumber 手机号
     * @param replace     替换后的内容
     * @return
     */
    public static String replacePhone(String phoneNumber, String replace) {
        if (!isEmpty(phoneNumber) && phoneNumber.length() == 11) {
            String start = phoneNumber.substring(0, 3);
            String end = phoneNumber.substring(7);
            return start + replace + end;
        }
        return phoneNumber;
    }


    public static String getPrioceFormatString(int value) {
        double price = (double) (value / 100d);
        DecimalFormat myFormatter = new DecimalFormat(FORMAT_FLOAT_PRICE);
        String output = myFormatter.format(price);
        return output;
    }

    public static String getPrioceFormatString(String value) {
        double price = (double) (Integer.parseInt(value) / 100d);
        DecimalFormat myFormatter = new DecimalFormat(FORMAT_FLOAT_PRICE);
        String output = myFormatter.format(price);
        return output;
    }


    public static SpannableString addDeleteLineToText(String text) {
        SpannableString spannableString = new SpannableString(text);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 将字符串数字转为int类型
     *
     * @param number 需要转换的字符串
     * @return 如果转换失败则返回-1
     */
    public static int parseInt(String number) {
        int i = -1;
        if (!isEmpty(number)) {
            try {
                i = Integer.parseInt(number);
            } catch (NumberFormatException e) {
                Logger.e(e, "字符串转换为 int 异常");
            }
        }

        return i;
    }

    /**
     * 将字符串数字转为float类型
     *
     * @param number 需要转换的字符串
     * @return 如果转换失败则返回-1
     */
    public static float parseFloat(String number) {
        float i = -1;
        if (!isEmpty(number)) {
            try {
                i = Float.parseFloat(number);
            } catch (NumberFormatException e) {
                Logger.e(e, "字符串转换为 float 异常");
            }
        }

        return i;
    }

    /**
     * 将字符串数字转为double类型
     *
     * @param number 需要转换的字符串
     * @return 如果转换失败则返回-1
     */
    public static double parseDouble(String number) {
        double i = -1;
        if (!isEmpty(number)) {
            try {
                i = Double.parseDouble(number);
            } catch (NumberFormatException e) {
                Logger.e(e, "字符串转换为 Double 异常");
            }
        }

        return i;
    }

    /**
     * 驼峰转下划线(简单写法，效率低于)
     */
    public static String humpToLine(String str) {
        if (!TextUtils.isEmpty(str)) {
            return str.replaceAll("[A-Z]", "_$0").toLowerCase();
        }
        return str;
    }

    /**
     * @param place   每间隔几位添加空格
     * @param content 需要添加空格的字符串
     * @return 添加空格之后的字符串
     */
    public static String split(int place, String content) {
        if (!TextUtils.isEmpty(content)) {
            content = content.replaceAll(" ", "");

            StringBuilder str = new StringBuilder(content);

            int i = str.length() / place;
            int j = str.length() % place;

            for (int x = (j == 0 ? i - 1 : i); x > 0; x--) {
                str = str.insert(x * place, " ");
            }

            return str.toString();
        }

        return content;
    }

    /**
     * 针对TextView显示中文中出现的排版错乱问题，通过调用此方法得以解决
     *
     * @param str
     * @return 返回全部为全角字符的字符串
     */
    public static String toDBC(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }

        }
        return new String(c);
    }

    public static String warpNullString(String data) {
        if (data == null) {
            return "";
        } else {
            return data.trim();
        }
    }

    public static String getSignString(String userid, String ts, String token, String... pars) {
        String outPutString = "";
        Queue queue = new PriorityQueue();//小到大的排序的队列实现
        queue.add(userid);
        queue.add(ts);
        queue.add(token);
        if (pars != null && pars.length > 0) {
            for (int i = 0; i < pars.length; i++) {
                pars[i] = warpNullString(pars[i]);
            }
            List list = Arrays.asList(pars);
            queue.addAll(list);
        }
        while (!queue.isEmpty()) {
            outPutString += queue.poll();
        }
        Logger.d("TEST:token:" + token);
        Logger.d("TEST:outPutString:" + outPutString);
        return MD5AndSHA1Util.getSHA1(outPutString);
    }

    public static String getSignString(String token, Map<String, String> dataMap) {
        String outPutString = "";
        Queue queue = new PriorityQueue();//小到大的排序的队列实现

        for (String vlues : dataMap.values()) {
            queue.add(warpNullString(vlues));
        }
        queue.add(token);

        while (!queue.isEmpty()) {
            outPutString += queue.poll();
        }
        Logger.d("TEST:token:" + token);
        Logger.d("TEST:outPutString:" + outPutString);
        return MD5AndSHA1Util.getSHA1(outPutString);
    }


    public static String firstCharUpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


    public static String getRandomLetter() {
        Random random = new Random();
        int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写还是小写
        char letter = (char) (choice + random.nextInt(26));
        String letterString = String.valueOf(letter);
        return letterString;
    }

    public static int getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return Color.rgb(r, g, b);
    }


    public static String getAtributeSting(List<Map<String, String>> datas) {
        String result = "";
        if (datas == null || datas.size() == 0) {
            return result;
        }

        for (Map<String, String> map : datas) {
            for (String value : map.values()) {
                result += value + ",";
            }
        }
        if (!TextUtils.isEmpty(result)) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }
}
