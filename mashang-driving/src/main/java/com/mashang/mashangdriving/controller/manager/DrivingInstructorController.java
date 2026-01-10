package com.mashang.mashangdriving.controller.manager;

import com.mashang.mashangdriving.service.manager.IDrivingInstructorService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/drivingInstructor")
public class DrivingInstructorController {

    @Autowired
    private IDrivingInstructorService drivingInstructorService;

}
