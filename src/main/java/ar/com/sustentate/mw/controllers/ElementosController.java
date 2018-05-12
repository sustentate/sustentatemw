package ar.com.sustentate.mw.controllers;

import ar.com.sustentate.mw.managers.CloudantManager;
import ar.com.sustentate.mw.managers.ElasticSearchManager;
import ar.com.sustentate.mw.models.ElementosResponse;
import ar.com.sustentate.mw.models.OperationResponse;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.standard.Jsr310DateTimeFormatAnnotationFormatterFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ElementosController {
    @Autowired
    private CloudantManager cloudantManager;

    @Autowired
    private ElasticSearchManager elasticSearchManager;

    @RequestMapping("/elementos")
    public @ResponseBody
    List<ElementosResponse> buscarElementos(@RequestParam(name = "criterio", required = true) String criterio) {
        //TODO: Utilizar la clase elementos
        List<ElementosResponse> elementos = new ArrayList<>();
        ElementosResponse elemento = new ElementosResponse();
        elemento.setComoHacerlo("Con ganas");
        elemento.setCondicon("Reciclable");
        elemento.setFinalDate(DateTime.now().toDate());
        elemento.setId(1);
        elemento.setProducto("Latas");
        elementos.add(elemento);
        return elementos;
    }

    @RequestMapping(value = "/elementos/sync", method = RequestMethod.POST)
    public @ResponseBody
    OperationResponse sync() throws IOException {
        OperationResponse response = new OperationResponse();
        elasticSearchManager.savePrueba();
        response.setStatus("OK");
        return response;
    }

    @RequestMapping ("/prueba")

    public
            void Prueba ()
    {
        ElementosResponse elementosResponse = new ElementosResponse();
        elementosResponse.setId(1);
        elementosResponse.setProducto("Aceite mineral usado");
        elementosResponse.setCondicon("Manejo especial");
        elementosResponse.setComoHacerlo("Depositá el aceite mineral usado en algún envase, recordá que debe estar frío si no se derretirá la botella.");
        elementosResponse.setFinalDate(new Date (2018,05,12));
        cloudantManager.saveElemento(elementosResponse);

    }


}
