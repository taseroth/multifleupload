package org.faboo.examples.fileupload;

import java.io.File;
import java.io.Serializable;

/**
 * @author Bert.radke <bert.radke@t-systems.com>
 */
public class UploadData implements Serializable {

    private final String name;
    private final String file;

    public UploadData(String name, File tempFile) {
        this.name = name;
        this.file = tempFile.getAbsolutePath();
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return new File(file);
    }
}
