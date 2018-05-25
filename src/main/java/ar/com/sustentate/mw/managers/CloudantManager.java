package ar.com.sustentate.mw.managers;

import ar.com.sustentate.mw.models.*;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.views.AllDocsRequest;
import com.cloudant.client.api.views.AllDocsRequestBuilder;
import com.cloudant.client.api.views.AllDocsResponse;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CloudantManager {

    @Autowired
    private CloudantClient client;

    public List<EcoTipResponse> getTips(String lastId) throws IOException {

        Database db = client.database("tips", true);
        AllDocsRequestBuilder builder = db.getAllDocsRequestBuilder();
        if (lastId != null) {
            builder.startKey(lastId);
        }
        AllDocsRequest request = builder.includeDocs(true).build();
        AllDocsResponse response = request.getResponse();
        List<EcoTipResponse> tips = response.getDocsAs(EcoTipResponse.class);
        return tips;
    }

    public void saveRecognitionData(VisualRecognitionResult results, File image, ClassificationRequest request) {

        Database db = client.database("recognition", true);
        Map<String, Object> data = new HashMap<>();
        data.put("_id", String.valueOf(System.currentTimeMillis()));
        data.put("user_id", request.getUserId());
        data.put("filename", image.getName());
        data.put("result", results.getRecognitionResult());
        data.put("rates", results.getRates());
        data.put("timestamp", DateTime.now().toString());
        db.post(data);

    }

    public void saveElemento(ElementosModel elementosModel) {
        Database db = client.database("elementos", true);
        Map<String, Object> data = new HashMap<>();
        data.put("_id", String.valueOf(System.currentTimeMillis()));
        data.put("producto", elementosModel.getProducto() );
        data.put("condicion", elementosModel.getCondicion() );
        data.put("comoHacerlo", elementosModel.getComoHacerlo());
        db.post(data);
    }

    public List<ElementosModel> getElementos() throws IOException {
        Database db = client.database("elementos", true);
        AllDocsRequestBuilder builder = db.getAllDocsRequestBuilder();
        AllDocsRequest request = builder.includeDocs(true).build();
        AllDocsResponse response = request.getResponse();
        List<ElementosModel> elementos = response.getDocsAs(ElementosModel.class);
        return elementos;
    }

    public void saveEventos(EventoResponse eventoResponse) {
        Database db = client.database("eventos", true);
        Map<String, Object> data = new HashMap<>();
        data.put("_id", eventoResponse.getImageUrl());
        data.put("title", eventoResponse.getTitle());
        data.put("text", eventoResponse.getText() );
        data.put("place", eventoResponse.getPlace());
        data.put("imageUrl", eventoResponse.getImageUrl());
        data.put("date", eventoResponse.getDate());
        data.put("dateEnd", eventoResponse.getDateEnd());
        data.put("googleId", eventoResponse.getGoogleId());
        db.post(data);
    }

    public List<EventoResponse> getEventos(String lastId) throws IOException {
        Database db = client.database("eventos", true);
        AllDocsRequestBuilder builder = db.getAllDocsRequestBuilder();
        AllDocsRequest request = builder.includeDocs(true).build();
        AllDocsResponse response = request.getResponse();
        List<EventoResponse> eventos = response.getDocsAs(EventoResponse.class);
        return eventos;
    }

   public boolean doesEventExists(long id) {
       Database db = client.database("eventos", true);
       return db.contains(Long.valueOf(id).toString());
   }
}
