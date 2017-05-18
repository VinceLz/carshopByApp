package com.car.contractcar.myapplication.common.ui.cityList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

/**
 * @author Joshua �÷��� DBHelper dbHelper = new DBHelper(this);
 *         dbHelper.createDataBase(); SQLiteDatabase db =
 *         dbHelper.getWritableDatabase(); Cursor cursor = db.query()
 *         db.execSQL(sqlString); ע�⣺execSQL��֧�ִ�;�Ķ���SQL��䣬ֻ��һ��һ����ִ�У����˺ܾò�����
 *         ��execSQL��Դ��ע�� (Multiple statements separated by ;s are not
 *         supported.) ����assets�µ����ݿ��ļ�ֱ�Ӹ��Ƶ�DB_PATH�������ݿ��ļ���С������1M����
 *         ����г���1M�Ĵ��ļ�������Ҫ�ȷָ�ΪN��С�ļ���Ȼ��ʹ��copyBigDatabase()�滻copyDatabase()
 */
public class DBHelper extends SQLiteOpenHelper {
    // �û����ݿ��ļ��İ汾
    private static final int DB_VERSION = 3;
    // ���ݿ��ļ�Ŀ����·��ΪϵͳĬ��λ�ã�com.droid ����İ���
    private static String DB_PATH;
    /*
     * //�����������ݿ��ļ������SD���Ļ� private static String DB_PATH =
     * android.os.Environment.getExternalStorageDirectory().getAbsolutePath() +
     * "/arthurcn/drivertest/packfiles/";
     */
    private static String DB_NAME = "meituan_cities.db";
    private static String ASSETS_NAME = "meituan_cities.db";


    public SQLiteDatabase myDataBase = null;
    private final Context myContext;

    public SQLiteDatabase getMyDataBase() {
        return myDataBase;
    }

    public void setMyDataBase(SQLiteDatabase myDataBase) {
        this.myDataBase = myDataBase;
    }

    /**
     * ������ݿ��ļ��ϴ�ʹ��FileSplit�ָ�ΪС��1M��С�ļ� �����зָ�Ϊ hello.db.101 hello.db.102
     * hello.db.103
     */
    // ��һ���ļ�����׺
    private static final int ASSETS_SUFFIX_BEGIN = 101;
    // ���һ���ļ�����׺
    private static final int ASSETS_SUFFIX_END = 103;

    /**
     * ��SQLiteOpenHelper�����൱�У������иù��캯��
     *
     * @param context �����Ķ���
     * @param name    ���ݿ�����
     * @param factory һ�㶼��null
     * @param version ��ǰ���ݿ�İ汾��ֵ���������������ǵ�����״̬
     */
    public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
        // ����ͨ��super���ø��൱�еĹ��캯��
        super(context, name, null, version);
        this.myContext = context;
        DB_PATH = DB_PATH = myContext.getFilesDir().getAbsolutePath().replace("files", "databases") + File.separator;
    }

    public DBHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public DBHelper(Context context, String name) {
        this(context, name, DB_VERSION);
    }

    public DBHelper(Context context) {
        this(context, DB_PATH + DB_NAME);
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            // ���ݿ��Ѵ��ڣ�do nothing.
            String myPath = DB_PATH + DB_NAME;
            myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } else {
            // �������ݿ�
            try {
                Log.d("----", DB_PATH + "");
                File dir = new File(DB_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File dbf = new File(DB_PATH + DB_NAME);
                if (dbf.exists()) {
                    dbf.delete();
                }
                copyDataBase();
                myDataBase = SQLiteDatabase.openOrCreateDatabase(dbf, null);
            } catch (IOException e) {
                throw new Error("���ݿⴴ��ʧ��");
            }
        }
    }

    // ������ݿ��Ƿ���Ч
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        String myPath = DB_PATH + DB_NAME;
        try {
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // database does't exist yet.
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {
        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(ASSETS_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    // ����assets�µĴ����ݿ��ļ�ʱ�����
    @SuppressWarnings("unused")
    private void copyBigDataBase() throws IOException {
        InputStream myInput;
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        for (int i = ASSETS_SUFFIX_BEGIN; i < ASSETS_SUFFIX_END + 1; i++) {
            myInput = myContext.getAssets().open(ASSETS_NAME + "." + i);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myInput.close();
        }
        myOutput.close();
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    /**
     * �ú������ڵ�һ�δ�����ʱ��ִ�У� ʵ�����ǵ�һ�εõ�SQLiteDatabase�����ʱ��Ż�����������
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    /**
     * ���ݿ��ṹ�б仯ʱ����
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}