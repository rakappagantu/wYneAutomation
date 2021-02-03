package com.wYne.automation.general;

import com.wYne.automation.config.ConfigManager;
import com.wYne.automation.exceptions.WyneException;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;


public class DataUtils {







    public void deleteExportedFiles(String filePartialName){
        String downloadPath = ConfigManager.getBundle().getString("downloadfile.path");
        File dir = new File(downloadPath);
        FileFilter fileFilter = new WildcardFileFilter(filePartialName);
        File[] files = dir.listFiles(fileFilter);
        if(files != null) {
            for (File file : files) {
                if (file.isFile())
                    file.delete();
            }
        }
    }




}
