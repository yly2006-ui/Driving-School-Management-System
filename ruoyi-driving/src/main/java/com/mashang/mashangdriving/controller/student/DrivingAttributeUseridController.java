package com.mashang.mashangdriving.controller.student;

import com.mashang.mashangdriving.service.student.IDrivingAttributeUseridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drivingAttributeUserid")
public class DrivingAttributeUseridController {

    @Autowired
    private IDrivingAttributeUseridService drivingAttributeUseridService;

}
