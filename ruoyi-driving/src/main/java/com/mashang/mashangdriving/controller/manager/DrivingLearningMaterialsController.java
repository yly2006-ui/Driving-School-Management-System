package com.mashang.mashangdriving.controller.manager;

import com.mashang.mashangdriving.service.manager.IDrivingLearningMaterialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drivingLearningMaterials")
public class DrivingLearningMaterialsController {

    @Autowired
    private IDrivingLearningMaterialsService drivingLearningMaterialsService;

}
