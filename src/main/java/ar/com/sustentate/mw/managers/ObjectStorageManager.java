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

    public String saveImage(File image, String bucketName) {
       // List<Bucket> buckets = client.listBuckets();
        client.putObject(bucketName, image.getName(), image);
        return client.getUrl(bucketName, image.getName()).toString();
    }
}
