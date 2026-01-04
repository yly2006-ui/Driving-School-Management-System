package com.mashang.mashangdriving.controller.student;

import com.mashang.mashangdriving.service.student.IDrivingAttributeUseridService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/drivingAttributeUserid")
public class DrivingAttributeUseridController {

    @Autowired
    private IDrivingAttributeUseridService drivingAttributeUseridService;

}
