package ar.com.sustentate.mw.managers;

import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.model.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class ObjectStorageManager {
    @Autowired
    private AmazonS3 client;

    public void saveImage(File image) {
       // List<Bucket> buckets = client.listBuckets();
        client.putObject("recognition", image.getName(), image);
    }
}
