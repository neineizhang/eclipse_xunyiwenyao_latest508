package com.zll.xunyiwenyao.dbitem;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by rxz on 2017/3/21.
 */

public class Utils {

    public static enum SEX {
        MAN, WOMAN
    };

    public static enum DOCTOR_TYPE{
        DOCTOR,  ACCESSOR
    };

    public static enum DEPARTMENT {
        EMPTY, NEIKE, WAIKE, FUCHANKE, ERKE, WUGUANKE, PIFUKE
    }; ///// 直接改这里

    public static String[] DEPARTMENT_ARRAY = new String[]{ "内科", "外科", "妇产科", "儿科", "眼耳鼻咽喉科 ","皮肤病与性病","精神卫生","职业病","医学影像和放射治疗","医学检验、病理","全科医学","急救医学","康复医学","预防保健","特种医学与军事医学","计划生育技术服务","其他专业"};

//    public static enum DEPARTMENT {
//        NEIKE, WAIKE, ERKE, FUCHANKE, WUGUANKE, PIFUKE
//    };
//
//    public static String[] DEPARTMENT_ARRAY = new String[]{"NEI KE", "WAI KE", "ER KE", "FU CHAN KE", "WU GUANKE", "PI FU KE"};

    public static enum STATUS{
        SAVED,COMMITED,APPROVED,REFUSED
    };

    public static enum INSPECTION_STATUS{
        UNCOMMITED, COMMITED
    };

    public static Doctor LOGIN_DOCTOR;

    public static enum REPORT_LEVEL{
        NEW, SEVERE, GENERAL
    };

    public static String PATIENT_NAME ="";
    public static boolean isFromChat = false;

    /**
     * Save image to the SD card
     *
     * @param photoBitmap
     * @param photoName
     * @param path
     */
    public static String savePhoto(Bitmap photoBitmap, String path,
                                   String photoName) {
        String localPath = null;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File photoFile = new File(path, photoName + ".png");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
                            fileOutputStream)) { // ת�����
                        localPath = photoFile.getPath();
                        fileOutputStream.flush();
                    }
                }
            } catch (FileNotFoundException e) {
                photoFile.delete();
                localPath = null;
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                localPath = null;
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                        fileOutputStream = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return localPath;
    }




}
