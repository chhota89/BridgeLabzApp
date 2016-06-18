package com.bridgelabz.app;

import com.bridgelabz.app.model.ORMUser;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by bridgelabz5 on 30/5/16.
 */
public class WriteConfigFile extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[] {ORMUser.class};

    public static void main(String[] args) throws SQLException, IOException {
        writeConfigFile(new File("ormlite_config.txt"),classes);
    }

}