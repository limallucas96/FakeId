package br.com.lucas.fatec;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils {


    public static String parseToFile(Context context, Uri uri) {
        String path;
        File direct = context.getCacheDir();

        if (!direct.exists()) {
            direct.mkdirs();
        }

        File file = new File(direct, "edited.jpeg");
        if (file.exists()) {
            file.delete();
        }

        try {
            InputStream in = context.getContentResolver().openInputStream(uri);
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            path = file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            path = "";
        }
        return path;
    }
}
