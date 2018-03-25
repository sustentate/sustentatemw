package ar.com.sustentate.mw.managers;

import ar.com.sustentate.mw.models.ClassificationRequest;
import ar.com.sustentate.mw.models.VisualRecognitionResult;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class CloudantManager {

    @Autowired
    private CloudantClient client;

    public void saveData(VisualRecognitionResult results, File image, ClassificationRequest request) {

        Database db = client.database("recognition", true);
        Map<String, Object> data = new HashMap<>();
        data.put("_id", String.valueOf(System.currentTimeMillis()));
        data.put("user_id", request.getUserId());
        data.put("filename", image.getName());
        data.put("result", results.getRecognitionResult());
        data.put("rates", results.getRates());
        db.post(data);
    }
}
