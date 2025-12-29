package com.mashang.mashangdriving.controller.manager;

import com.mashang.mashangdriving.service.manager.IDrivingLearningMaterialsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/drivingLearningMaterials")
public class DrivingLearningMaterialsController {

    @Autowired
    private IDrivingLearningMaterialsService drivingLearningMaterialsService;

}
