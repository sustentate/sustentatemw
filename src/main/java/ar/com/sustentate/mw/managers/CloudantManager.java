package ar.com.sustentate.mw.managers;

import ar.com.sustentate.mw.models.VisualRecognitionResult;
import com.cloudant.client.api.CloudantClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class CloudantManager {

    @Autowired
    private CloudantClient client;

    public void saveData(VisualRecognitionResult results, File image) {
        Map<String, Object> data = new HashMap<>();
        return;
    }
}
