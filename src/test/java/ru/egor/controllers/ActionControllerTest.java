package ru.egor.controllers;

import static org.junit.Assert.assertEquals;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;
import ru.egor.entity.Plate;
import ru.egor.service.ElementService;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

public class ActionControllerTest {
    private final List<Plate> plates = new ArrayList<>();
    Gson gson = new Gson();
    String jsonPlates;

    @Before
    public void initPlates(){
        Plate plate = new Plate();
        Plate plate1 = new Plate();
        plate.setPlateId(100);
        plate.setName("probaName");
        plate.setModel("probaModel");
        plate.setPhoto("probaPhoto");
        plates.add(plate);
//        plate.setPlateId(200);
//        plate.setName("probaName2");
//        plate.setModel("probaModel2");
//        plate.setPhoto("probaPhoto2");
//        plates.add(plate1);
        jsonPlates = gson.toJson(plates);
    }

//    @Test
//    public void downloadTxt() {
//        ElementService elementService = mock(ElementService.class);
//        when(elementService.showPlates()).thenReturn(plates);
//        Gson gson1 = new GsonBuilder().create();
//        ActionController actionController = new ActionController();
//        ReflectionTestUtils.setField(actionController, "elementService", elementService);
//        ExtendedModelMap uiModel = new ExtendedModelMap();
//        uiModel.addAttribute("jsonPlates", actionController.downloadTxt());
//        List<Plate> actualPlates = gson1.fromJson((String) uiModel.get("jsonPlates"), List.class );
//        assertEquals(1, actualPlates.size());
//    }

    @Test
    public void index() {
    }
}
