package com.zll.xunyiwenyao.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.zll.xunyiwenyao.R;
import com.zll.xunyiwenyao.dbitem.Doctor;
import com.zll.xunyiwenyao.dbitem.Utils;
import com.zll.xunyiwenyao.util.TopBarView;
import com.zll.xunyiwenyao.webservice.DoctorWebService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kejund on 17/4/5.
 */

public class RegisterActivity extends Activity implements TopBarView.onTitleBarClickListener {
    private TopBarView topbar;
    private EditText user_name, real_name, passwrd, passwrd_verify, goodat, profile;

    private RadioGroup sex_rg, type_rg;
    private RadioButton sex_rb1, sex_rb2, type_rb1, type_rb2;
    int sex= Utils.SEX.MAN.ordinal();//0-男，1-女
    int type= Utils.DOCTOR_TYPE.DOCTOR.ordinal();//0-执业医生，1-审核医生

    private int head_image_id = R.drawable.image;
    private ImageView head_image;
    private int license_image_id = R.drawable.image2;
    private ImageView license_image;
    //设置图片参数
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    protected static final int HEAD_SET = 0;
    protected static final int LICENSE_SET = 1;
    private int setFlag=HEAD_SET;


    private Spinner spinner_title, spinner_hospital, spinner_department;
    String title, hospital, department;
    private String arrs_title[], arrs_hospital[], arrs_department[];

    private Button btn_commint, btn_cancel;
    Bitmap head_bitmap = null;
    Bitmap license_bitmap = null;


    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_layout);
        topbar = (TopBarView)findViewById(R.id.topbar);
        topbar.setClickListener(this);

        //头像选择
        head_image = (ImageView) findViewById(R.id.headImage);
        head_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                setFlag=HEAD_SET;
                showChoosePicDialog("选择头像");
            }
        });

        //EditText
        user_name = (EditText)findViewById(R.id.user_name);
        real_name = (EditText)findViewById(R.id.real_name);
        passwrd = (EditText)findViewById(R.id.password_input);
        passwrd_verify = (EditText)findViewById(R.id.password_verify);
        goodat = (EditText)findViewById(R.id.goodat_text);
        profile = (EditText)findViewById(R.id.profile_text);

        //Spinner-title
        spinner_title = (Spinner)findViewById(R.id.spinner_title);
//        arrs_title = getResources().getStringArray(R.array.listTitleArr);
        arrs_title=DoctorWebService.listAllTitle();
        ArrayAdapter<String> arrsTitleAdapter = new ArrayAdapter<String>(
                RegisterActivity.this, android.R.layout.simple_list_item_1,arrs_title);
        spinner_title.setAdapter(arrsTitleAdapter);
        spinner_title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                title = arrs_title[arg2];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        //Spinner-hospial
        spinner_hospital = (Spinner)findViewById(R.id.spinner_hospital);
//        arrs_hospital = getResources().getStringArray(R.array.listHospitalArr);
        arrs_hospital = DoctorWebService.listAllHospital();
        ArrayAdapter<String> arrsHospitalAdapter = new ArrayAdapter<String>(
                RegisterActivity.this, android.R.layout.simple_list_item_1,arrs_hospital);
        spinner_hospital.setAdapter(arrsHospitalAdapter);
        spinner_hospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                hospital = arrs_hospital[arg2];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        //Spinner-department
        spinner_department = (Spinner)findViewById(R.id.spinner_department);
//        arrs_department = getResources().getStringArray(R.array.listDepartmentArr);
        arrs_department = DoctorWebService.listAllDepartment();

        ArrayAdapter<String> arrsDepartmentAdapter = new ArrayAdapter<String>(
                RegisterActivity.this, android.R.layout.simple_list_item_1,arrs_department);
        spinner_department.setAdapter(arrsDepartmentAdapter);
        spinner_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                department = arrs_department[arg2];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        //Radio
        sex_rg = (RadioGroup)findViewById(R.id.sex_rg);
        sex_rb1 = (RadioButton)findViewById(R.id.sex_rb1);
        sex_rb2 = (RadioButton)findViewById(R.id.sex_rb2);
        sex_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                if (arg1 == sex_rb1.getId()) {
                    sex = Utils.SEX.MAN.ordinal();
                } else {
                    sex = Utils.SEX.WOMAN.ordinal();
                }
            }
        });

        type_rg = (RadioGroup)findViewById(R.id.type_rg);
        type_rb1 = (RadioButton)findViewById(R.id.type_rb1);
        type_rb2 = (RadioButton)findViewById(R.id.type_rb2);
        type_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                if (arg1 == type_rb1.getId()) {
                    type = Utils.DOCTOR_TYPE.DOCTOR.ordinal();
                } else {
                    type = Utils.DOCTOR_TYPE.ACCESSOR.ordinal();
                }
            }
        });

        //执业照片
        license_image = (ImageView) findViewById(R.id.licenseImage);
        license_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                setFlag=LICENSE_SET;
                showChoosePicDialog("选择头像");
            }
        });

        //Button
        btn_commint = (Button)findViewById(R.id.button_commit);
        btn_cancel = (Button)findViewById(R.id.button_cancel);
        btn_commint.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                doctorRegister();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }
    @Override
    public void onBackClick() {
        RegisterActivity.this.finish();
    }
    @Override
    public void onRightClick() {
//        Toast.makeText(RegisterActivity.this, "你点击了右侧按钮", Toast.LENGTH_SHORT).show();

    }


    public void doctorRegister(){
        if(user_name.getText().toString().equals("")
                ||real_name.getText().toString().equals("")
                ||passwrd.getText().toString().equals("")
                ||passwrd_verify.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, "您输入的信息不完整！",
                    Toast.LENGTH_SHORT).show();
        }else if(passwrd.getText().toString().equals(passwrd_verify.getText().toString())){
            List<String> usernamelist = new ArrayList<String>();
            usernamelist= DoctorWebService.getAllUsername();
            int valid = 1;
            for(String name:usernamelist){
                if(name.equals(user_name.getText().toString()))
                    valid = 0;
            }
            if(valid==0){
                Toast.makeText(RegisterActivity.this, "用户名已经存在，请重新输入！",
                        Toast.LENGTH_SHORT).show();
            }else if(user_name.getText().toString().length()>=4&&user_name.getText().toString().length()<=20){
                if(!includeChinese(user_name.getText().toString())){
                    Doctor doctor = new Doctor();
                    doctor.setUsername(user_name.getText().toString());
                    doctor.setSex(sex);
                    doctor.setRealName(real_name.getText().toString());
                    doctor.setPasswd(passwrd.getText().toString());
                    doctor.setType(type);
                    doctor.setHospital(hospital);
                    doctor.setTitle(title);
                    doctor.setDepartment(department);
                    doctor.setGoodat(goodat.getText().toString());
                    doctor.setProfile(profile.getText().toString());
                    //                DoctorWebService.addDoctor(doctor);
                    Map<String, String> map = DoctorWebService.registerDoctor(doctor);
                    String result = map.get("result");
                    String message = map.get("message");
                    if(result.equals("1")){//注册成功
                        Toast.makeText(RegisterActivity.this, "注册成功！",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this, message,
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "用户名不能包含中文！",
                            Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(RegisterActivity.this, "用户名的长度为4-20个字符！",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(RegisterActivity.this, "两次输入的密码不一致！",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean includeChinese(String str) {
        boolean flag =false;//默认不为中文
        for (char c : str.toCharArray()){
            if(c >= 0x4E00 &&  c <= 0x9FA5)
                flag = true;// 根据字节码判断
        }
        return flag;
    }

    /**
     * 显示修改头像的对话框，方法2
     */
    protected void showChoosePicDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "image.jpg"));
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }
    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1.5);
        intent.putExtra("aspectY", 2);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 400);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }
    /**
     * 保存裁剪之后的图片数据
     *
     * @param

     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            if(setFlag==HEAD_SET)
                head_image.setImageBitmap(photo);
            else
                license_image.setImageBitmap(photo);
//            uploadPic(photo);
        }
    }
    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        String imagePath = Utils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath+"");
        if(imagePath != null){
            // 拿着imagePath上传了
            // ...
        }
    }


}
